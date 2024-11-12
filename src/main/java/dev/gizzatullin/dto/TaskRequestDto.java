package dev.gizzatullin.dto;

import dev.gizzatullin.model.TaskStatus;
import dev.gizzatullin.validator.Marker;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequestDto {

    @NotBlank(
            groups = {Marker.OnCreate.class},
            message = "Название задачи не может быть пустым при создании"
    )
    @Size(max = 100, message = "Название задачи не может быть больше 100 символов")
    private String title;

    @NotBlank(
            groups = {Marker.OnCreate.class},
            message = "Описание задачи не может быть пустым при создании"
    )
    @Size(max = 150, message = "Описание не может быть больше 150 символов")
    private String description;

    @NotNull(
            groups = {Marker.OnCreate.class},
            message = "Статус задачи не может быть пустым при создании"
    )
    private TaskStatus status;
}
