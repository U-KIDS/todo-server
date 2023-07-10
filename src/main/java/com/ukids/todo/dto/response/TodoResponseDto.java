package com.ukids.todo.dto.response;

import com.ukids.todo.entity.Todo;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Data
public class TodoResponseDto {
    private Long todoId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private Boolean isComplete;

    public TodoResponseDto(Todo todo) {
        todoId = todo.getId();
        title = todo.getTitle();
        description = todo.getDescription();
        createdAt = todo.getCreatedAt();
        isComplete = todo.getIsComplete();
    }
}
