package dev.gizzatullin.mapper;

import dev.gizzatullin.dto.TaskRequestDto;
import dev.gizzatullin.dto.TaskResponseFullDto;
import dev.gizzatullin.model.Task;

import java.util.List;
import java.util.stream.Collectors;

public class TaskMapper {

    public static TaskResponseFullDto toFullDto(Task task) {
        if (task == null) {
            return null;
        }

        return TaskResponseFullDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .build();
    }

    public static Task toEntity(TaskRequestDto taskRequestDto) {
        if (taskRequestDto == null) {
            return null;
        }

        return Task.builder()
                .title(taskRequestDto.getTitle())
                .description(taskRequestDto.getDescription())
                .status(taskRequestDto.getStatus())
                .build();
    }

    public static List<TaskResponseFullDto> toFullDtoList(List<Task> tasks) {
        return tasks.stream()
                .map(TaskMapper::toFullDto)
                .collect(Collectors.toList());
    }
}
