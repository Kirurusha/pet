package ru.filatov.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.filatov.store.entities.TaskEntity;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
