package com.company.workforcemgmt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private String id;
    private String taskId;
    private String userId;
    private String userName;
    private String content;
    private LocalDateTime timestamp;
    
    public Comment(String taskId, String userId, String userName, String content) {
        this.id = java.util.UUID.randomUUID().toString();
        this.taskId = taskId;
        this.userId = userId;
        this.userName = userName;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }
}