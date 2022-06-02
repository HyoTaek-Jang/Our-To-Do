package com.ourtodo.withme.domain.todo.service;

import static com.ourtodo.withme.domain.tag.constants.TagConstants.*;
import static com.ourtodo.withme.domain.tag.constants.TagValidationConstants.*;
import static com.ourtodo.withme.domain.todo.constants.ToDoValidationConstants.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ourtodo.withme.domain.tag.db.domain.Tag;
import com.ourtodo.withme.domain.tag.db.repository.TagRepository;
import com.ourtodo.withme.domain.todo.db.domain.ToDo;
import com.ourtodo.withme.domain.todo.db.repository.ToDoRepository;
import com.ourtodo.withme.domain.todo.dto.request.UpdateToDoRequest;
import com.ourtodo.withme.domain.user.db.domain.Member;
import com.ourtodo.withme.domain.user.db.repository.MemberRepository;
import com.ourtodo.withme.global.exception.custom.BaseException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ToDoService {

	private final ToDoRepository toDoRepository;
	private final TagRepository tagRepository;
	private final MemberRepository memberRepository;

	@Transactional
	public ToDo addTodo(Long memberId, String content, Long tagId) {
		Tag tag;
		if (tagId != null) {
			tag = tagRepository.findById(tagId).orElseThrow(() -> new BaseException(NOT_EXIST_TAG, 400));
		} else {
			Member member = memberRepository.findById(memberId).orElse(null);
			tag = tagRepository.findByMemberAndName(member, COMMON_TAG);
		}

		Long lastOrder = toDoRepository.findLastOrderByTag(tag);
		return toDoRepository.save(new ToDo(content, false, tag, lastOrder + 1));
	}

	@Transactional
	public void updateTodo(Long memberId, UpdateToDoRequest updateToDoRequest) {
		ToDo toDo = toDoRepository.findById(updateToDoRequest.getTodoId()).orElseThrow(() -> new BaseException(NOT_EXIST_TODO, 400));
		Tag tag = tagRepository.findById(updateToDoRequest.getTagId()).orElseThrow(() -> new BaseException(NOT_EXIST_TAG, 400));
		Member member = memberRepository.findById(memberId).orElse(null);

		if (!(toDo.getTag().equals(tag) && tag.getMember().equals(member))) {
			throw new BaseException(NOT_MATCH_TODO, 400);
		}

		toDo.updateTag(tag);
		toDo.updateContent(updateToDoRequest.getContent());

		toDoRepository.save(toDo);
	}

	@Transactional
	public void deleteTodo(Long memberId, Long todoId) {
		ToDo toDo = checkOwnTodo(memberId, todoId);
		toDoRepository.delete(toDo);
	}

	@Transactional
	public void toggleIsCompletedTodo(Long memberId, Long todoId) {
		ToDo toDo = checkOwnTodo(memberId, todoId);
		toDo.toggleIsCompleted();
		toDoRepository.save(toDo);
	}

	private ToDo checkOwnTodo(Long memberId, Long todoId) {
		ToDo toDo = toDoRepository.findById(todoId).orElseThrow(() -> new BaseException(NOT_EXIST_TODO, 400));
		Member member = memberRepository.findById(memberId).orElse(null);

		if (!toDo.getTag().getMember().equals(member)) {
			throw new BaseException(NOT_MATCH_TODO, 400);
		}
		return toDo;
	}
}
