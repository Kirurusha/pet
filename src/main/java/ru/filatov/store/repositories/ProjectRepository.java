package ru.filatov.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.filatov.store.entities.ProjectEntity;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<ProjectEntity,Long> {
    Optional<ProjectEntity> findByName(String name);
}
