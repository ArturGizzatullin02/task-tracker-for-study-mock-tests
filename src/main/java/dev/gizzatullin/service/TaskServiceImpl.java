package dev.gizzatullin.service;

import dev.gizzatullin.dto.TaskRequestDto;
import dev.gizzatullin.dto.TaskResponseFullDto;
import dev.gizzatullin.exception.TaskNotFoundException;
import dev.gizzatullin.mapper.TaskMapper;
import dev.gizzatullin.model.Task;
import dev.gizzatullin.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public TaskResponseFullDto create(TaskRequestDto taskDto) {
        log.info("Creating task: {}", taskDto);
        Task savedTask = taskRepository.save(TaskMapper.toEntity(taskDto));
        log.info("Saved task: {}", savedTask);
        return TaskMapper.toFullDto(savedTask);
    }

    @Override
    public TaskResponseFullDto get(Long id) {
        log.info("Getting task: {}", id);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id: " + id + " not found"));
        log.info("Got task: {}", task);
        return TaskMapper.toFullDto(task);
    }

    @Override
    public TaskResponseFullDto update(Long id, TaskRequestDto taskDto) {
        log.info("Updating task: {}", taskDto);
        Task taskFromRepository = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id: " + id + " not found"));

        if (!taskDto.getTitle().isBlank()) {
            taskFromRepository.setTitle(taskDto.getTitle());
        }
        if (!taskDto.getDescription().isBlank()) {
            taskFromRepository.setDescription(taskDto.getDescription());
        }
        if (taskDto.getStatus() != null) {
            taskFromRepository.setStatus(taskDto.getStatus());
        }

        Task savedTask = taskRepository.save(taskFromRepository);
        log.info("Updated task: {}", savedTask);
        return TaskMapper.toFullDto(savedTask);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting task: {}", id);
        taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id: " + id + " not found"));
        taskRepository.deleteById(id);
        log.info("Deleted task: {}", id);
    }

    @Override
    public Collection<TaskResponseFullDto> getAll() {
        log.info("Getting all tasks");
        List<Task> tasks = taskRepository.findAll();
        log.info("Got all tasks");
        return TaskMapper.toFullDtoList(tasks);
    }
}
