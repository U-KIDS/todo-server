package com.ukids.todo.controller;

import com.ukids.todo.dto.request.TodoCreateDto;
import com.ukids.todo.dto.response.TodoResponseDto;
import com.ukids.todo.entity.Todo;
import com.ukids.todo.service.TodoService;
import com.ukids.todo.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/todo")
@RestController
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<Long> createTodo(@RequestBody TodoCreateDto todoCreateDto) {

        Long todoId = todoService.createTodo(todoCreateDto, SecurityUtil.getCurrentUsername()
                .orElseThrow(() -> new UsernameNotFoundException("로그인된 user가 없습니다.")));

        return ResponseEntity.ok().body(todoId);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TodoResponseDto> detailTodo(@PathVariable Long id) {
        TodoResponseDto todoResponseDto = todoService.findTodoById(id);

        return ResponseEntity.ok().body(todoResponseDto);
    }

    @GetMapping // 모든 todo를 가져오는 경우
    public ResponseEntity
<List<TodoResponseDto>> getTodoList() {
        List<TodoResponseDto> todoList = todoService.findAllTodo(SecurityUtil.getCurrentUsername()
                .orElseThrow(() -> new UsernameNotFoundException("로그인된 user가 없습니다.")));

        return ResponseEntity.ok(todoList);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        
        return ResponseEntity.ok("삭제 완료");
    }

    @PostMapping(value = "/complete/{id}")
    public ResponseEntity<TodoResponseDto> updateTodoStatement(@PathVariable Long id) {
        TodoResponseDto todoResponseDto = todoService.updateStatement(id);

        return ResponseEntity.ok(todoResponseDto);
    }

}
