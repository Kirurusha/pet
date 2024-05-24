package ru.filatov.api.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.filatov.api.dto.AckDto;
import ru.filatov.api.dto.ProjectDto;
import ru.filatov.api.factories.ProjectDtoFactory;
import ru.filatov.exceptions.BadRequestException;
import ru.filatov.exceptions.NotFoundException;
import ru.filatov.store.entities.ProjectEntity;
import ru.filatov.store.repositories.ProjectRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public static final String DELETE_PROJECT = "/api/projects/{project_id}";


    public static final String CREATE_OR_UPDATE_PROJECT = "/api/projects";

    @GetMapping(FETCH_PROJECTS)
    public List<ProjectDto> fetchProjects(@RequestParam(value = "prefix_name", required = false) Optional<String> optionalPrefixName) {
        optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());
        Stream<ProjectEntity> projectStream = optionalPrefixName
                .map(projectRepository::streamAllByNameStartsWithIgnoreCase)
                .orElseGet(projectRepository::streamAll);


        if (optionalPrefixName.isPresent()) {
            projectStream = projectRepository.streamAllByNameStartsWithIgnoreCase(optionalPrefixName.get());
        } else {
            projectStream = projectRepository.streamAll();
        }
        return projectStream
                .map(projectDtoFactory::makeProjectDto)
                .collect(Collectors.toList());

    }


    @PostMapping(value = CREATE_PROJECT, produces = "application/json")
    @ResponseBody
    public ProjectDto createProject(@RequestParam("project_name") String projectName) {

        if (projectName.isEmpty()) {
            throw new BadRequestException(String.format("Name can't be empty", projectName));
        }
        logger.info("Received request to create project with name: {}", projectName);

        projectRepository
                .findByName(projectName)
                .ifPresent(project -> {
                    throw new BadRequestException(String.format("Project \"%s\" already exists", projectName));
                });
        //throw new BadRequestException(String.format("Project \"%s\" already exists", name));
        ProjectEntity project = projectRepository.saveAndFlush(
                ProjectEntity.builder()
                        .name(projectName)
                        .build()
        );
        //TODO: uncomit and insert entity in method

        return projectDtoFactory.makeProjectDto(project);

    }

    @PostMapping(value = EDIT_PROJECT, produces = "application/json")
    public ProjectDto editProject(@PathVariable("project_id") Long projectId,
                                  @RequestParam("project_name") String projectName) {

        if (projectName.isEmpty()) {
            throw new BadRequestException(String.format("Name can't be empty", projectName));
        }

        ProjectEntity project = getProjectOrThrowException(projectId);

        projectRepository
                .findByName(projectName)
                .filter(anotherProject -> !Objects.equals(anotherProject.getId(), projectId))
                .ifPresent(anotherProject -> {
                    throw new BadRequestException(String.format("Project \"%s\" already exists", projectName));

                });

        project.setName(projectName);

        project = projectRepository.saveAndFlush(project);


        return projectDtoFactory.makeProjectDto(project);

    }

    @DeleteMapping(DELETE_PROJECT)
    public AckDto deleteProject(@PathVariable("project_id") Long projectId) {
        ProjectEntity project = getProjectOrThrowException(projectId);


        return AckDto.makeDefault(true);
    }


    @PutMapping(value = CREATE_OR_UPDATE_PROJECT)
    @ResponseBody
    public ProjectDto createOrUpdateProject(
            @RequestParam(value = "project_id",required = false) Optional<Long> optionalProjectId,
            @RequestParam (value = "project_name",required = false) Optional<String> optionalProjectName) {

        optionalProjectName = optionalProjectName.filter(projectName -> !projectName.trim().isEmpty());


        boolean isCreated = optionalProjectId.isPresent();

        if (isCreated && !optionalProjectId.isPresent()) {
            throw new BadRequestException(String.format("project name can't be empty"));
        }

       final  ProjectEntity project = optionalProjectId
                .map(this::getProjectOrThrowException)
                .orElseGet(() -> ProjectEntity.builder().build());




        optionalProjectName
                .ifPresent(projectName -> {

                    projectRepository
                            .findByName(projectName)
                            .filter(anotherProject -> !Objects.equals(anotherProject.getId(), project.getId()))
                            .ifPresent(anotherProject -> {
                                throw new BadRequestException(
                                        String.format("Project \"%s\" already exists", projectName));

                            });

                    project.setName(projectName);
                });

        final ProjectEntity savedProject = projectRepository.saveAndFlush(project);

        return projectDtoFactory.makeProjectDto(project);

    }


    private ProjectEntity getProjectOrThrowException(Long projectId) {
        return projectRepository
                .findById(projectId)
                .orElseThrow(() ->
                        new NotFoundException(String.format("Project with id \"%s\" doesn't exists", projectId)));
    }

}
