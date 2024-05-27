package ru.filatov.api.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.filatov.api.controllers.helpers.ControllerHelper;
import ru.filatov.api.dto.ProjectDto;
import ru.filatov.api.dto.TaskStateDto;
import ru.filatov.api.factories.TaskStateDtoFactory;
import ru.filatov.store.entities.ProjectEntity;
import ru.filatov.store.repositories.TaskStateRepository;

import javax.naming.ldap.Control;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RestController
public class TaskStateController {
    TaskStateRepository taskStateRepository;

    TaskStateDtoFactory taskStateDtoFactory;

    ControllerHelper controllerHelper;


    public static final String GET_TASK_STATES = "/api/projects/{project_id}/task-states";
    public static final String ADD_TASK = "/api/task-states/{task_state_id}/tasks";
    public static final String CREATE_TASK_STATE = "/api/projects/{project_id}/task-states";

    public static final String DELETE_PROJECT = "/api/projects/{project_id}";

    @GetMapping(GET_TASK_STATES)
    public List<TaskStateDto> getTaskStates(@PathVariable(name = "project_id") Long  projectId) {

       ProjectEntity project =  controllerHelper.getProjectOrThrowException(projectId);


        return project
                .getTaskStates()
                .stream()
                .map(TaskStateDtoFactory::makeTaskStateDto)
                .collect(Collectors.toList());

    }

    @PostMapping(CREATE_TASK_STATE)
    public TaskStateDto createTaskState() {
        // 32 00
    }


}
