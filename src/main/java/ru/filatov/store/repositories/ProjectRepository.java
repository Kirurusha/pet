package ru.filatov.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.filatov.store.entities.ProjectEntity;

public interface ProjectRepository extends JpaRepository<ProjectEntity,Long> {
}
