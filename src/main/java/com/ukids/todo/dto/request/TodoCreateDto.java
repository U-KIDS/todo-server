package com.ukids.todo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TodoCreateDto {
    private String title;
    private String description;
    private LocalDateTime createdAt;
}
