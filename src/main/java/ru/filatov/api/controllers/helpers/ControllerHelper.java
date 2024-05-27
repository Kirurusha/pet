package ru.filatov.api.controllers.helpers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.filatov.exceptions.NotFoundException;
import ru.filatov.store.entities.ProjectEntity;
import ru.filatov.store.repositories.ProjectRepository;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
@Transactional
public class ControllerHelper {
    ProjectRepository projectRepository;
    public ProjectEntity getProjectOrThrowException(Long projectId) {
        return projectRepository
                .findById(projectId)
                .orElseThrow(() ->
                        new NotFoundException(String.format("Project with id \"%s\" doesn't exists", projectId)));
    }


}
