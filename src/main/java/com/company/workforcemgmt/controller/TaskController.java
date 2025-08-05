package com.company.workforcemgmt.controller;

import com.company.workforcemgmt.dto.*;
import com.company.workforcemgmt.model.Priority;
import com.company.workforcemgmt.model.Status;
import com.company.workforcemgmt.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {
    
    private final TaskService taskService;
    
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    
    @PostMapping
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody CreateTaskRequest request,
                                            @RequestHeader(value = "X-User-ID", defaultValue = "system") String userId) {
        TaskDto task = taskService.createTask(request, userId);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        List<TaskDto> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable String id) {
        try {
            TaskDto task = taskService.getTaskById(id);
            return ResponseEntity.ok(task);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/staff/{staffId}")
    public ResponseEntity<List<TaskDto>> getTasksByStaff(@PathVariable String staffId) {
        List<TaskDto> tasks = taskService.getTasksByStaff(staffId);
        return ResponseEntity.ok(tasks);
    }
    
    // Enhanced endpoint for smart daily task view (Feature 1)
    @GetMapping("/date-range")
    public ResponseEntity<List<TaskDto>> getTasksByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<TaskDto> tasks = taskService.getTasksByDateRange(startDate, endDate);
        return ResponseEntity.ok(tasks);
    }
    
    // Feature 2: Get tasks by priority
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<TaskDto>> getTasksByPriority(@PathVariable Priority priority) {
        List<TaskDto> tasks = taskService.getTasksByPriority(priority);
        return ResponseEntity.ok(tasks);
    }
    
    // Feature 2: Update task priority
    @PutMapping("/{id}/priority")
    public ResponseEntity<TaskDto> updateTaskPriority(@PathVariable String id,
                                                     @Valid @RequestBody UpdatePriorityRequest request,
                                                     @RequestHeader(value = "X-User-ID", defaultValue = "system") String userId) {
        try {
            TaskDto task = taskService.updateTaskPriority(id, request.getPriority(), userId);
            return ResponseEntity.ok(task);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Feature 3: Add comment to task
    @PostMapping("/{id}/comments")
    public ResponseEntity<TaskDto> addComment(@PathVariable String id,
                                            @Valid @RequestBody AddCommentRequest request) {
        try {
            TaskDto task = taskService.addComment(id, request);
            return ResponseEntity.ok(task);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<TaskDto> updateTaskStatus(@PathVariable String id,
                                                   @RequestParam Status status,
                                                   @RequestHeader(value = "X-User-ID", defaultValue = "system") String userId) {
        try {
            TaskDto task = taskService.updateTaskStatus(id, status, userId);
            return ResponseEntity.ok(task);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Bug fix: Proper task reassignment endpoint
    @PostMapping("/assign-by-ref/{customerRef}")
    public ResponseEntity<TaskDto> assignTaskByRef(@PathVariable String customerRef,
                                                  @Valid @RequestBody AssignTaskRequest request,
                                                  @RequestHeader(value = "X-User-ID", defaultValue = "system") String userId) {
        TaskDto task = taskService.assignTaskByRef(customerRef, request, userId);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Convenience endpoint for today's tasks (using smart logic)
    @GetMapping("/today")
    public ResponseEntity<List<TaskDto>> getTodaysTasks() {
        LocalDate today = LocalDate.now();
        List<TaskDto> tasks = taskService.getTasksByDateRange(today, today);
        return ResponseEntity.ok(tasks);
    }
}