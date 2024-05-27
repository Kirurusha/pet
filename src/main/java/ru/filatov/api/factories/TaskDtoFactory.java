package ru.filatov.api.factories;

import org.springframework.stereotype.Component;
import ru.filatov.api.dto.TaskDto;
import ru.filatov.store.entities.TaskEntity;
@Component
public class TaskDtoFactory {

    public TaskDto mateTaskDto(TaskEntity entity) {
        return TaskDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .description(entity.getDescription())
                .build();
    }
}
