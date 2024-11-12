package dev.gizzatullin.controller;

import dev.gizzatullin.AbstractContainerBaseTest;
import dev.gizzatullin.model.Task;
import dev.gizzatullin.model.TaskStatus;
import dev.gizzatullin.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TaskControllerTest extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    private int firstTaskId;

    private int lastTaskId;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();

        Task task1 = Task.builder()
                .title("task1")
                .description("description1")
                .status(TaskStatus.NEW)
                .build();

        Task task2 = Task.builder()
                .title("task2")
                .description("description2")
                .status(TaskStatus.NEW)
                .build();

        Task task3 = Task.builder()
                .title("task3")
                .description("description3")
                .status(TaskStatus.NEW)
                .build();

        taskRepository.saveAll(Arrays.asList(task1, task2, task3));

        firstTaskId = task1.getId().intValue();
        lastTaskId = task3.getId().intValue();

    }

    @Test
    @DisplayName("Создание задачи должно вернуть 200 код и эту задачу")
    void createTask() throws Exception {
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"new task\",\"description\": \"new description\",\"status\": \"NEW\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(lastTaskId + 1)))
                .andExpect(jsonPath("$.title", is("new task")))
                .andExpect(jsonPath("$.description", is("new description")))
                .andExpect(jsonPath("$.status", is("NEW")));
    }

    @Test
    @DisplayName("Поиск задачи должен вернуть 200 код и эту задачу")
    void getExistingTask() throws Exception {
        mockMvc.perform(get("/tasks/" + firstTaskId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(firstTaskId)));
    }

    @Test
    @DisplayName("Поиск несуществующей задачи должен вернуть 404 код и выбросить исключение с описанием ошибки")
    void getNotFoundTask() throws Exception {
        mockMvc.perform(get("/tasks/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("Task with id: 999 not found")));
    }

    @Test
    @DisplayName("Изменение заголовка задачи должно вернуть 200 код и эту задачу с обновленным заголовком")
    void updateTaskTitle() throws Exception {
        mockMvc.perform(put("/tasks/" + firstTaskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"updated title\",\"description\": \"description1\",\"status\": \"IN_PROGRESS\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("updated title"));
    }

    @Test
    @DisplayName("Изменение несуществующей задачи должно вернуть 404 код и выбросить исключение с описанием ошибки")
    void updateNotFoundTaskTitle() throws Exception {
        mockMvc.perform(put("/tasks/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"updated title\",\"description\": \"description1\",\"status\": \"IN_PROGRESS\"}"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("Task with id: 999 not found")));
    }

    @Test
    @DisplayName("Удаление задачи должно вернуть 200 код")
    void deleteExistingTask() throws Exception {
        mockMvc.perform(delete("/tasks/" + firstTaskId))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Удаление несуществующей задачи должно вернуть 404 код и выбросить исключение с описанием ошибки")
    void deleteNotFoundTask() throws Exception {
        mockMvc.perform(delete("/tasks/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("Task with id: 999 not found")));
    }

    @Test
    @DisplayName("Получение всех задач должно вернуть 200 код и список задач")
    void getAll() throws Exception {
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(firstTaskId)))
                .andExpect(jsonPath("$[0].title", is("task1")))
                .andExpect(jsonPath("$[0].description", is("description1")))
                .andExpect(jsonPath("$[0].status", is("NEW")));
    }
}