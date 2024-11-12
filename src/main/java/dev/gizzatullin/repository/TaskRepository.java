package dev.gizzatullin.repository;

import dev.gizzatullin.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByTitle(String title);

    Optional<Task> findByDescription(String description);
}
