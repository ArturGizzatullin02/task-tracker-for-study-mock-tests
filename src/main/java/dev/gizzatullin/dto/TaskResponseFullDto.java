package dev.gizzatullin.dto;

import dev.gizzatullin.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseFullDto {

    private Long id;

    private String title;

    private String description;

    private TaskStatus status;
}
