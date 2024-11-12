package dev.gizzatullin.controller;

import dev.gizzatullin.dto.TaskRequestDto;
import dev.gizzatullin.dto.TaskResponseFullDto;
import dev.gizzatullin.service.TaskService;
import dev.gizzatullin.validator.Marker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/tasks")
@Validated
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @Validated(Marker.OnCreate.class)
    public TaskResponseFullDto create(@RequestBody @Valid TaskRequestDto taskRequestDto) {
        log.info("Creating task: {}", taskRequestDto);
        TaskResponseFullDto task = taskService.create(taskRequestDto);
        log.info("Created task: {}", task);
        return task;
    }

    @GetMapping("/{id}")
    public TaskResponseFullDto get(@PathVariable Long id) {
        log.info("Getting task by id: {}", id);
        TaskResponseFullDto task = taskService.get(id);
        log.info("Got task: {}", task);
        return task;
    }

    @PutMapping("/{id}")
    @Validated(Marker.OnUpdate.class)
    public TaskResponseFullDto update(@PathVariable Long id, @RequestBody @Valid TaskRequestDto taskRequestDto) {
        log.info("Updating task: {}", taskRequestDto);
        TaskResponseFullDto task = taskService.update(id, taskRequestDto);
        log.info("Updated task: {}", task);
        return task;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("Deleting task by id: {}", id);
        taskService.delete(id);
        log.info("Deleted task: {}", id);
    }

    @GetMapping
    public Collection<TaskResponseFullDto> getAll() {
        log.info("Getting all tasks");
        Collection<TaskResponseFullDto> tasks = taskService.getAll();
        log.info("Getting all tasks: {}", tasks);
        return tasks;
    }
}
