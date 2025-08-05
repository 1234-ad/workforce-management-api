package com.company.workforcemgmt.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class AssignTaskRequest {
    @NotBlank(message = "New staff ID is required")
    private String newStaffId;
    
    @NotBlank(message = "New staff name is required")
    private String newStaffName;
    
    private String customerRef;
}