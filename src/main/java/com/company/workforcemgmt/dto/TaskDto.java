package com.company.workforcemgmt.dto;

import com.company.workforcemgmt.model.Priority;
import com.company.workforcemgmt.model.Status;
import com.company.workforcemgmt.model.ActivityLog;
import com.company.workforcemgmt.model.Comment;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TaskDto {
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
}