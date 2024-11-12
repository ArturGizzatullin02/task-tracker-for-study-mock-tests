package dev.gizzatullin.service;

import dev.gizzatullin.dto.TaskRequestDto;
import dev.gizzatullin.dto.TaskResponseFullDto;

import java.util.Collection;

public interface TaskService {

    TaskResponseFullDto create(TaskRequestDto taskDto);

    TaskResponseFullDto get(Long id);

    TaskResponseFullDto update(Long id, TaskRequestDto taskDto);

    void delete(Long id);

    Collection<TaskResponseFullDto> getAll();
}
