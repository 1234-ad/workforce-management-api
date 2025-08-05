package com.company.workforcemgmt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityLog {
    private String id;
    private String taskId;
    private String userId;
    private String action;
    private String details;
    private LocalDateTime timestamp;
    
    public ActivityLog(String taskId, String userId, String action, String details) {
        this.id = java.util.UUID.randomUUID().toString();
        this.taskId = taskId;
        this.userId = userId;
        this.action = action;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }
}