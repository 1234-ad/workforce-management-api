package com.company.workforcemgmt.service;

import com.company.workforcemgmt.dto.*;
import com.company.workforcemgmt.mapper.TaskMapper;
import com.company.workforcemgmt.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService {
    
    private final Map<String, Task> tasks = new HashMap<>();
    private final TaskMapper taskMapper;
    
    @Autowired
    public TaskService(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
        initializeSampleData();
    }
    
    private void initializeSampleData() {
        // Create sample tasks for demonstration
        Task task1 = new Task("Complete customer onboarding", "Onboard new customer ABC Corp", 
                             LocalDate.now(), LocalDate.now().plusDays(3), 
                             "staff1", "John Doe", "manager1");
        task1.setCustomerRef("ABC-001");
        task1.addActivityLog(new ActivityLog(task1.getId(), "manager1", "CREATED", "Task created"));
        tasks.put(task1.getId(), task1);
        
        Task task2 = new Task("Follow up on sales lead", "Contact potential client XYZ Inc", 
                             LocalDate.now().minusDays(2), LocalDate.now().plusDays(1), 
                             "staff2", "Jane Smith", "manager1");
        task2.setCustomerRef("XYZ-002");
        task2.addActivityLog(new ActivityLog(task2.getId(), "manager1", "CREATED", "Task created"));
        tasks.put(task2.getId(), task2);
    }
    
    public TaskDto createTask(CreateTaskRequest request, String createdBy) {
        Task task = new Task(request.getTitle(), request.getDescription(), 
                           request.getStartDate(), request.getDueDate(),
                           request.getAssignedStaffId(), request.getAssignedStaffName(), createdBy);
        
        if (request.getPriority() != null) {
            task.setPriority(request.getPriority());
        }
        
        if (request.getCustomerRef() != null) {
            task.setCustomerRef(request.getCustomerRef());
        }
        
        // Add creation activity log
        task.addActivityLog(new ActivityLog(task.getId(), createdBy, "CREATED", 
                                          "Task created and assigned to " + request.getAssignedStaffName()));
        
        tasks.put(task.getId(), task);
        return taskMapper.toDto(task);
    }
    
    public List<TaskDto> getAllTasks() {
        return tasks.values().stream()
                .filter(task -> task.getStatus() != Status.CANCELLED) // Bug fix: exclude cancelled tasks
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public TaskDto getTaskById(String id) {
        Task task = tasks.get(id);
        if (task == null) {
            throw new RuntimeException("Task not found with id: " + id);
        }
        return taskMapper.toDto(task);
    }
    
    public List<TaskDto> getTasksByStaff(String staffId) {
        return tasks.values().stream()
                .filter(task -> task.getAssignedStaffId().equals(staffId))
                .filter(task -> task.getStatus() != Status.CANCELLED) // Bug fix: exclude cancelled tasks
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }
    
    // Enhanced date-based task fetching (Feature 1: Smart Daily Task View)
    public List<TaskDto> getTasksByDateRange(LocalDate startDate, LocalDate endDate) {
        return tasks.values().stream()
                .filter(task -> task.getStatus() != Status.CANCELLED) // Exclude cancelled tasks
                .filter(task -> {
                    // Include tasks that started within the range
                    boolean startedInRange = !task.getStartDate().isBefore(startDate) && 
                                           !task.getStartDate().isAfter(endDate);
                    
                    // Include active tasks that started before the range but are still open
                    boolean activeFromBefore = task.getStartDate().isBefore(startDate) && 
                                             task.getStatus() == Status.ACTIVE;
                    
                    return startedInRange || activeFromBefore;
                })
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public List<TaskDto> getTasksByPriority(Priority priority) {
        return tasks.values().stream()
                .filter(task -> task.getPriority() == priority)
                .filter(task -> task.getStatus() != Status.CANCELLED) // Exclude cancelled tasks
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public TaskDto updateTaskPriority(String taskId, Priority priority, String updatedBy) {
        Task task = tasks.get(taskId);
        if (task == null) {
            throw new RuntimeException("Task not found with id: " + taskId);
        }
        
        Priority oldPriority = task.getPriority();
        task.setPriority(priority);
        task.setUpdatedAt(LocalDateTime.now());
        
        // Add activity log
        task.addActivityLog(new ActivityLog(taskId, updatedBy, "PRIORITY_CHANGED", 
                                          "Priority changed from " + oldPriority + " to " + priority));
        
        return taskMapper.toDto(task);
    }
    
    public TaskDto addComment(String taskId, AddCommentRequest request) {
        Task task = tasks.get(taskId);
        if (task == null) {
            throw new RuntimeException("Task not found with id: " + taskId);
        }
        
        Comment comment = new Comment(taskId, request.getUserId(), 
                                    request.getUserName(), request.getContent());
        task.addComment(comment);
        task.setUpdatedAt(LocalDateTime.now());
        
        // Add activity log
        task.addActivityLog(new ActivityLog(taskId, request.getUserId(), "COMMENT_ADDED", 
                                          "Comment added by " + request.getUserName()));
        
        return taskMapper.toDto(task);
    }
    
    public TaskDto updateTaskStatus(String taskId, Status status, String updatedBy) {
        Task task = tasks.get(taskId);
        if (task == null) {
            throw new RuntimeException("Task not found with id: " + taskId);
        }
        
        Status oldStatus = task.getStatus();
        task.setStatus(status);
        task.setUpdatedAt(LocalDateTime.now());
        
        // Add activity log
        task.addActivityLog(new ActivityLog(taskId, updatedBy, "STATUS_CHANGED", 
                                          "Status changed from " + oldStatus + " to " + status));
        
        return taskMapper.toDto(task);
    }
    
    // Bug fix: Proper task reassignment with cancellation of old task
    public TaskDto assignTaskByRef(String customerRef, AssignTaskRequest request, String assignedBy) {
        // Find existing task by customer reference
        Task existingTask = tasks.values().stream()
                .filter(task -> customerRef.equals(task.getCustomerRef()))
                .filter(task -> task.getStatus() == Status.ACTIVE)
                .findFirst()
                .orElse(null);
        
        if (existingTask != null) {
            // Cancel the existing task
            existingTask.setStatus(Status.CANCELLED);
            existingTask.setUpdatedAt(LocalDateTime.now());
            existingTask.addActivityLog(new ActivityLog(existingTask.getId(), assignedBy, "CANCELLED", 
                                                      "Task cancelled due to reassignment"));
        }
        
        // Create new task for the new staff member
        Task newTask = new Task(existingTask != null ? existingTask.getTitle() : "Reassigned Task", 
                               existingTask != null ? existingTask.getDescription() : "Task reassigned from customer reference",
                               LocalDate.now(), 
                               existingTask != null ? existingTask.getDueDate() : LocalDate.now().plusDays(7),
                               request.getNewStaffId(), request.getNewStaffName(), assignedBy);
        
        newTask.setCustomerRef(customerRef);
        if (existingTask != null) {
            newTask.setPriority(existingTask.getPriority());
        }
        
        // Add activity logs
        newTask.addActivityLog(new ActivityLog(newTask.getId(), assignedBy, "CREATED", 
                                             "Task created through reassignment"));
        newTask.addActivityLog(new ActivityLog(newTask.getId(), assignedBy, "ASSIGNED", 
                                             "Task assigned to " + request.getNewStaffName()));
        
        tasks.put(newTask.getId(), newTask);
        return taskMapper.toDto(newTask);
    }
    
    public void deleteTask(String taskId) {
        if (!tasks.containsKey(taskId)) {
            throw new RuntimeException("Task not found with id: " + taskId);
        }
        tasks.remove(taskId);
    }
}