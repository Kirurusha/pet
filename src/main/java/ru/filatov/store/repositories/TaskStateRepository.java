package ru.filatov.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.filatov.store.entities.TaskStateEntity;

public interface TaskStateRepository extends JpaRepository<TaskStateEntity, Long> {
}
