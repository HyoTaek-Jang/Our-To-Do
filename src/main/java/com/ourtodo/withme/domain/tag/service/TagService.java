package com.ourtodo.withme.domain.tag.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ourtodo.withme.domain.tag.db.domain.Tag;
import com.ourtodo.withme.domain.tag.db.repository.TagRepository;
import com.ourtodo.withme.domain.user.db.domain.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagService {
	private final TagRepository tagRepository;

	@Transactional
	public Tag addTag(Member member, String name, String color) {
		Tag tag = new Tag(name, color, member);
		return tagRepository.save(tag);
	}
}
