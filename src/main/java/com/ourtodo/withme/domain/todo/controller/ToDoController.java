package com.ourtodo.withme.domain.todo.controller;

import static com.ourtodo.withme.domain.todo.constants.ToDoControllerConstants.*;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourtodo.withme.domain.tag.db.domain.Tag;
import com.ourtodo.withme.domain.tag.service.TagService;
import com.ourtodo.withme.domain.todo.dto.TodosDto;
import com.ourtodo.withme.domain.todo.dto.request.AddToDoRequest;
import com.ourtodo.withme.domain.todo.dto.request.UpdateToDoRequest;
import com.ourtodo.withme.domain.todo.dto.response.TodosResponse;
import com.ourtodo.withme.domain.todo.service.ToDoService;
import com.ourtodo.withme.global.dto.BaseResponse;
import com.ourtodo.withme.global.security.util.SecurityUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class ToDoController {

	private final ToDoService toDoService;
	private final TagService tagService;

	@PostMapping
	public ResponseEntity<? extends BaseResponse> addTodo(@Valid @RequestBody AddToDoRequest addToDoRequest) {
		Long currentMemberId = SecurityUtil.getCurrentMemberId();
		toDoService.addTodo(currentMemberId, addToDoRequest.getContent(), addToDoRequest.getTagId());
		return ResponseEntity.status(201).body(new BaseResponse(SUCCESS_ADD_TODO));
	}

	@PutMapping
	public ResponseEntity<? extends BaseResponse> updateTodo(@Valid @RequestBody UpdateToDoRequest updateToDoRequest) {
		Long currentMemberId = SecurityUtil.getCurrentMemberId();
		toDoService.updateTodo(currentMemberId, updateToDoRequest);
		return ResponseEntity.status(201).body(new BaseResponse(SUCCESS_UPDATE_TODO));
	}

	@DeleteMapping("/{todoId}")
	public ResponseEntity<? extends BaseResponse> deleteTodo(@PathVariable Long todoId) {
		Long currentMemberId = SecurityUtil.getCurrentMemberId();
		toDoService.deleteTodo(currentMemberId, todoId);
		return ResponseEntity.status(201).body(new BaseResponse(SUCCESS_DELETE_TODO));
	}

	@PostMapping("/toggle/{todoId}")
	public ResponseEntity<? extends BaseResponse> toggleTodo(@PathVariable Long todoId) {
		Long currentMemberId = SecurityUtil.getCurrentMemberId();
		toDoService.toggleIsCompletedTodo(currentMemberId, todoId);
		return ResponseEntity.status(201).body(new BaseResponse(SUCCESS_TOGGLE_TODO));
	}

	@GetMapping
	public ResponseEntity<? extends BaseResponse> findAllTodos() {
		Long currentMemberId = SecurityUtil.getCurrentMemberId();
		List<Tag> todosWithTag = tagService.findTodosWithTag(currentMemberId);
		List<TodosDto> todosDtoList = toDoService.transferTodosDto(todosWithTag);
		return ResponseEntity.status(200).body(new TodosResponse(SUCCESS_FIND_TODOS, todosDtoList));
	}
}

