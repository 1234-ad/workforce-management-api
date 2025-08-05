package com.company.workforcemgmt.mapper;

import com.company.workforcemgmt.dto.CreateTaskRequest;
import com.company.workforcemgmt.dto.TaskDto;
import com.company.workforcemgmt.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);
    
    TaskDto toDto(Task task);
    
    List<TaskDto> toDtoList(List<Task> tasks);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "activityLogs", ignore = true)
    @Mapping(target = "comments", ignore = true)
    Task toEntity(CreateTaskRequest request);
}