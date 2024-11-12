package dev.gizzatullin.service;

import dev.gizzatullin.AbstractContainerBaseTest;
import dev.gizzatullin.dto.TaskRequestDto;
import dev.gizzatullin.dto.TaskResponseFullDto;
import dev.gizzatullin.exception.TaskNotFoundException;
import dev.gizzatullin.model.TaskStatus;
import dev.gizzatullin.repository.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TaskServiceSpringBootTest extends AbstractContainerBaseTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    private TaskRequestDto taskRequestDto;

    private TaskResponseFullDto createdTaskDto;

    @BeforeEach
    void setUp() {
        taskRequestDto = TaskRequestDto.builder()
                .title("new title")
                .description("new description")
                .status(TaskStatus.NEW)
                .build();

        taskRepository.deleteAll();
        createdTaskDto = taskService.create(taskRequestDto);
    }

    @Test
    @DisplayName(value = "Создание задачи возвращает TaskResponseFullDto с верными полями")
    void createTask() {
        assertNotNull(createdTaskDto);
        assertNotNull(createdTaskDto.getId());
        assertEquals(taskRequestDto.getTitle(), createdTaskDto.getTitle());
        assertEquals(taskRequestDto.getDescription(), createdTaskDto.getDescription());
        assertEquals(taskRequestDto.getStatus(), createdTaskDto.getStatus());

    }

    @Test
    @DisplayName(value = "Поиске несуществующей задачи должен выбросить исключение TaskNotFoundException")
    void getNotFoundTask() {
        long id = 999L;

        Assertions.assertThrows(TaskNotFoundException.class, () -> {
            taskService.get(id);
        });
    }

    @Test
    @DisplayName(value = "Получение существующей задачи должно возвращать TaskResponseFullDto с верными полями")
    void getExistingTask() {
        Long taskId = createdTaskDto.getId();

        TaskResponseFullDto taskFullDto = taskService.get(taskId);
        assertNotNull(taskFullDto);
        assertEquals(taskId, taskFullDto.getId());
        assertEquals(taskRequestDto.getTitle(), taskFullDto.getTitle());
        assertEquals(taskRequestDto.getDescription(), taskFullDto.getDescription());
        assertEquals(taskRequestDto.getStatus(), taskFullDto.getStatus());
    }

    @Test
    @DisplayName(value = "Обновления задачи должен возвращать TaskResponseFullDto с верными обновленными полями")
    void updateTaskTitle() {

        assertThat(taskService.getAll()).hasSize(1);

        TaskRequestDto taskUpdateRequestDto = TaskRequestDto.builder()
                .title("updated title")
                .description("")
                .status(null)
                .build();
        TaskResponseFullDto taskFullDtoUpdated = taskService.update(createdTaskDto.getId(), taskUpdateRequestDto);

        assertNotNull(taskFullDtoUpdated);

        assertThat(taskService.getAll()).hasSize(1);
        assertEquals(createdTaskDto.getId(), taskFullDtoUpdated.getId());
        assertEquals("updated title", taskFullDtoUpdated.getTitle());
        assertEquals(createdTaskDto.getDescription(), createdTaskDto.getDescription());
        assertEquals(createdTaskDto.getStatus(), createdTaskDto.getStatus());
    }

    @Test
    @DisplayName(value = "Обновлении несуществующей задачи должно выбросить исключение TaskNotFoundException")
    void updateNotFoundTaskTitle() {
        TaskRequestDto taskInvalidUpdateRequestDto = TaskRequestDto.builder()
                .title("updated title")
                .description("")
                .status(null)
                .build();

        Long id = 999L;

        Assertions.assertThrows(TaskNotFoundException.class, () -> {
            taskService.update(id, taskInvalidUpdateRequestDto);
        });
    }

    @Test
    @DisplayName(value = "Удаление задачи должно действительно происходить и приводить к уменьшению списка задач")
    void deleteExistingTask() {
        assertThat(taskService.getAll()).hasSize(1);

        taskService.delete(createdTaskDto.getId());

        assertThat(taskService.getAll()).hasSize(0);
    }

    @Test
    @DisplayName(value = "Удаление несуществующей задачи должно выбросить исключение TaskNotFoundException")
    void deleteNotFoundTask() {
        Long id = 999L;

        Assertions.assertThrows(TaskNotFoundException.class, () -> {
            taskService.delete(id);
        });
    }

    @Test
    @DisplayName(value = "Получение всех задач должно вернуть список задач, размер которого соответствует количеству задач")
    void getAll() {
        TaskRequestDto taskRequestDto2 = TaskRequestDto.builder()
                .title("new title2")
                .description("new description2")
                .status(TaskStatus.NEW)
                .build();

        TaskRequestDto taskRequestDto3 = TaskRequestDto.builder()
                .title("new title3")
                .description("new description3")
                .status(TaskStatus.NEW)
                .build();

        taskService.create(taskRequestDto2);
        TaskResponseFullDto taskFullDto3 = taskService.create(taskRequestDto3);

        assertThat(taskService.getAll()).hasSize(3);

        assertNotNull(taskFullDto3);
        assertEquals(taskRequestDto3.getTitle(), taskFullDto3.getTitle());
        assertEquals(taskRequestDto3.getDescription(), taskFullDto3.getDescription());
        assertEquals(taskRequestDto3.getStatus(), taskFullDto3.getStatus());
    }
}