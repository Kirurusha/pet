package ru.filatov.api.factories;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.filatov.api.dto.ProjectDto;
import ru.filatov.api.dto.TaskStateDto;
import ru.filatov.store.entities.ProjectEntity;
import ru.filatov.store.entities.TaskStateEntity;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component

public class TaskStateDtoFactory {

    TaskDtoFactory taskDtoFactory;

    public static TaskStateDto makeTaskStateDto(TaskStateEntity entity) {
        return TaskStateDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .ordinal(entity.getOrdinal())
                .tasks(
                        entity
                                .getTasks()
                                .stream()
                                .map(taskDtoFactory::mateTaskDto)
                                .collect(Collectors.toList())

                )
                .build();
    }

}
