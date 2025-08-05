package com.company.workforcemgmt.dto;

import com.company.workforcemgmt.model.Priority;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class UpdatePriorityRequest {
    @NotNull(message = "Priority is required")
    private Priority priority;
}