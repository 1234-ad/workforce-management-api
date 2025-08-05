package com.company.workforcemgmt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private String id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private LocalDate startDate;
    private LocalDate dueDate;
    private String assignedStaffId;
    private String assignedStaffName;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String customerRef;
    private List<ActivityLog> activityLogs;
    private List<Comment> comments;
    
    public Task(String title, String description, LocalDate startDate, LocalDate dueDate, 
                String assignedStaffId, String assignedStaffName, String createdBy) {
        this.id = java.util.UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.status = Status.ACTIVE;
        this.priority = Priority.MEDIUM; // Default priority
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.assignedStaffId = assignedStaffId;
        this.assignedStaffName = assignedStaffName;
        this.createdBy = createdBy;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.activityLogs = new ArrayList<>();
        this.comments = new ArrayList<>();
    }
    
    public void addActivityLog(ActivityLog log) {
        if (this.activityLogs == null) {
            this.activityLogs = new ArrayList<>();
        }
        this.activityLogs.add(log);
    }
    
    public void addComment(Comment comment) {
        if (this.comments == null) {
            this.comments = new ArrayList<>();
        }
        this.comments.add(comment);
    }
}