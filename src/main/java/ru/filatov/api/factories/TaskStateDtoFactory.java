package ru.filatov.api.factories;

import org.springframework.stereotype.Component;
import ru.filatov.api.dto.ProjectDto;
import ru.filatov.api.dto.TaskStateDto;
import ru.filatov.store.entities.ProjectEntity;
import ru.filatov.store.entities.TaskStateEntity;

@Component
public class TaskStateDtoFactory {

    public TaskStateDto makeProjectDto(TaskStateEntity entity) {
        return TaskStateDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .ordinal(entity.getOrdinal())
                .build();
    }
}
