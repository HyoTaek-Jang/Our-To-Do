package com.ourtodo.withme.domain.todo.service;

import static com.ourtodo.withme.domain.tag.constants.TagConstants.*;
import static com.ourtodo.withme.domain.tag.constants.TagValidationConstants.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ourtodo.withme.domain.tag.db.domain.Tag;
import com.ourtodo.withme.domain.tag.db.repository.TagRepository;
import com.ourtodo.withme.domain.todo.db.domain.ToDo;
import com.ourtodo.withme.domain.todo.db.repository.ToDoRepository;
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

		Long lastOrder = toDoRepository.findLastOrderByTag(tag.getId());

		return toDoRepository.save(new ToDo(content, false, tag, lastOrder + 1));
	}
}
