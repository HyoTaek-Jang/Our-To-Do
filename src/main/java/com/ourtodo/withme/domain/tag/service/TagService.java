package com.ourtodo.withme.domain.tag.service;

import static com.ourtodo.withme.domain.tag.constants.TagValidationConstants.*;
import static com.ourtodo.withme.domain.user.constants.MemberConstants.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ourtodo.withme.domain.tag.db.domain.Tag;
import com.ourtodo.withme.domain.tag.db.repository.TagRepository;
import com.ourtodo.withme.domain.tag.dto.TagDto;
import com.ourtodo.withme.domain.user.db.domain.Member;
import com.ourtodo.withme.domain.user.service.MemberService;
import com.ourtodo.withme.global.exception.custom.BaseException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagService {
	private final TagRepository tagRepository;
	private final MemberService memberService;

	@Transactional
	public Tag addTag(Member member, String name, String color) {
		Tag tag = new Tag(name, color, member, new LinkedList<>());
		return tagRepository.save(tag);
	}

	public List<TagDto> findTagList(Member memberById) {
		return tagRepository.findAllByMember(memberById).stream().map(TagDto::new).collect(Collectors.toList());
	}

	public Tag changeTagName(Long currentMemberId, Long tagId, String name) {
		Tag tag = isMembersTag(currentMemberId, tagId);
		tag.updateName(name);
		return tagRepository.save(tag);
	}

	private Tag isMembersTag(Long currentMemberId, Long tagId) {
		Member memberById = memberService.findMemberById(currentMemberId);
		Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new BaseException(NOT_EXIST_TAG, 409));
		if (!Objects.equals(memberById.getId(), tag.getMember().getId()))
			throw new BaseException(NOT_MATCH_TAG_WITH_MEMBER, 401);
		return tag;
	}

	public Tag changeTagColor(Long currentMemberId, Long tagId, String color) {
		Tag tag = isMembersTag(currentMemberId, tagId);
		tag.updateColor(color);
		return tagRepository.save(tag);
	}

	public List<Tag> findTodosWithTag(Long currentMemberId) {
		Member memberById = memberService.findMemberById(currentMemberId);
		List<Tag> tagsWithTodos = tagRepository.findAllWithTodoByMember(memberById);
		return tagsWithTodos;
	}
}
