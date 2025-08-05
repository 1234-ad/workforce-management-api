package com.company.workforcemgmt.dto;

import com.company.workforcemgmt.model.Priority;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class CreateTaskRequest {
    @NotBlank(message = "Title is required")
    private String title;
    
    private String description;
    
    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    
    @NotNull(message = "Due date is required")
    private LocalDate dueDate;
    
    @NotBlank(message = "Assigned staff ID is required")
    private String assignedStaffId;
    
    @NotBlank(message = "Assigned staff name is required")
    private String assignedStaffName;
    
    private Priority priority = Priority.MEDIUM;
    
    private String customerRef;
}