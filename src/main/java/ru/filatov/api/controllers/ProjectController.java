package ru.filatov.api.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.filatov.api.dto.ProjectDto;
import ru.filatov.api.factories.ProjectDtoFactory;
import ru.filatov.exceptions.BadRequestException;
import ru.filatov.store.repositories.ProjectRepository;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RestController
public class ProjectController {

     ProjectDtoFactory projectDtoFactory;

     ProjectRepository projectRepository;

    public static final String CREATE_PROJECT = "/api/projects";

    @PostMapping(CREATE_PROJECT)
    public ProjectDto createProject(@RequestParam String name) {
            projectRepository
                    .findByName(name)
                    .ifPresent(project -> {
                        throw new BadRequestException(String.format("Project \"%s\" already exists", name));
                    });


        //TODO: uncomit and insert entity in method
        //return projectDtoFactory.makeProjectDto();
        return null;
    }









}
