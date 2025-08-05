package com.company.workforcemgmt.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class AddCommentRequest {
    @NotBlank(message = "Comment content is required")
    private String content;
    
    @NotBlank(message = "User ID is required")
    private String userId;
    
    @NotBlank(message = "User name is required")
    private String userName;
}