package ru.filatov.api.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.filatov.api.dto.ProjectDto;
import ru.filatov.api.factories.ProjectDtoFactory;
import ru.filatov.exceptions.BadRequestException;
import ru.filatov.exceptions.NotFoundException;
import ru.filatov.store.entities.ProjectEntity;
import ru.filatov.store.repositories.ProjectRepository;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RestController
public class ProjectController {
    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
     ProjectDtoFactory projectDtoFactory;

     ProjectRepository projectRepository;

    public static final String FETCH_PROJECTS = "/api/projects";
    public static final String CREATE_PROJECT = "/api/projects";
    public static final String EDIT_PROJECT = "/api/projects/{project_id}";

    @PostMapping(FETCH_PROJECTS)
    public List<ProjectDto> fetchProjects(@RequestParam(value = "prefix_name", required = false)) {
    // 3 22 09
    }


    @PostMapping(value = CREATE_PROJECT, produces = "application/json")
    @ResponseBody
    public ProjectDto createProject(@RequestParam String name) {

        if (name.isEmpty()) {
            throw new BadRequestException(String.format("Name can't be empty", name));
        }
        logger.info("Received request to create project with name: {}", name);

        projectRepository
                .findByName(name)
                .ifPresent(project -> {
                    throw new BadRequestException(String.format("Project \"%s\" already exists", name));
                });
        //throw new BadRequestException(String.format("Project \"%s\" already exists", name));
        ProjectEntity project = projectRepository.saveAndFlush(
                ProjectEntity.builder()
                        .name(name)
                        .build()
        );
        //TODO: uncomit and insert entity in method

        return projectDtoFactory.makeProjectDto(project);

    }
    @PostMapping(value = EDIT_PROJECT, produces = "application/json")
    public ProjectDto editProject(@PathVariable("project_id") Long projectId,
                                  @RequestParam String name) {

        if (name.isEmpty()) {
            throw new BadRequestException(String.format("Name can't be empty", name));
        }

        ProjectEntity project = projectRepository
                .findById(projectId)
                .orElseThrow(() ->
                        new NotFoundException(String.format("Project with id \"%s\" doesn't exists", projectId)));

        projectRepository
                .findByName(name)
                .filter(anotherProject -> !Objects.equals(anotherProject.getId(), projectId))
                .ifPresent(anotherProject -> {
                    throw new BadRequestException(String.format("Project \"%s\" already exists", name));

                });

        project.setName(name);

        project = projectRepository.saveAndFlush(project);



        return projectDtoFactory.makeProjectDto(project);

    }








}
