package com.ourtodo.withme.domain.todo.controller;

import static com.ourtodo.withme.domain.todo.constants.ToDoControllerConstants.*;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourtodo.withme.domain.todo.db.domain.ToDo;
import com.ourtodo.withme.domain.todo.dto.request.AddToDoRequest;
import com.ourtodo.withme.domain.todo.dto.request.UpdateToDoRequest;
import com.ourtodo.withme.domain.todo.service.ToDoService;
import com.ourtodo.withme.global.dto.BaseResponse;
import com.ourtodo.withme.global.security.util.SecurityUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class ToDoController {

	private final ToDoService toDoService;

	@PostMapping
	public ResponseEntity<? extends BaseResponse> addTodo(@Valid @RequestBody AddToDoRequest addToDoRequest) {
		Long currentMemberId = SecurityUtil.getCurrentMemberId();
		toDoService.addTodo(currentMemberId, addToDoRequest.getContent(), addToDoRequest.getTagId());
		return ResponseEntity.status(201).body(new BaseResponse(SUCCESS_ADD_TODO));
	}

	@PutMapping
	public ResponseEntity<? extends BaseResponse> updateTodo(@Valid @RequestBody UpdateToDoRequest updateToDoRequest) {
		toDoService.updateTodo(updateToDoRequest.getTodoId(), updateToDoRequest.getTagId(),
			updateToDoRequest.getContent());
		return ResponseEntity.status(201).body(new BaseResponse(SUCCESS_UPDATE_TODO));
	}
}

