package com.ukids.todo.service;

import com.ukids.todo.dto.request.TodoCreateDto;
import com.ukids.todo.dto.response.TodoResponseDto;
import com.ukids.todo.entity.Member;
import com.ukids.todo.entity.Todo;
import com.ukids.todo.repository.MemberRepository;
import com.ukids.todo.repository.TodoRepository;
import com.ukids.todo.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    public Long createTodo(TodoCreateDto todoCreateDto, String username) {

        Member member = memberRepository.findMemberByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("없는 user입니다."));

        Todo todo = Todo.builder()
                .title(todoCreateDto.getTitle())
                .description(todoCreateDto.getDescription())
                .createdAt(todoCreateDto.getCreatedAt())
                .isComplete(false)
                .member(member)
                .build();

        todoRepository.save(todo);

        return todo.getId();
    }
    public TodoResponseDto findTodoById(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new IllegalArgumentException("없는 todo입니다."));

        return new TodoResponseDto(todo);
    }

    public List<TodoResponseDto> findAllTodo(String username) {

        List<Todo> todos = todoRepository.findAllByMember_Username(username);
        return todos.stream()
                .map(t -> new TodoResponseDto(t))
                .collect(Collectors.toList());
    }

    public void deleteTodo(Long todoId) {
        todoRepository.deleteById(todoId);
    }

    public TodoResponseDto updateStatement(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new IllegalArgumentException("없는 todo입니다."));
        todo.update();
        return new TodoResponseDto(todo);
    }


}

