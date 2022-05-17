package com.ourtodo.withme.domain.tag.controller;

import static com.ourtodo.withme.domain.tag.constants.TagConstants.*;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourtodo.withme.domain.tag.dto.TagDto;
import com.ourtodo.withme.domain.tag.dto.request.AddTagRequest;
import com.ourtodo.withme.domain.tag.dto.response.FindTagListResponse;
import com.ourtodo.withme.domain.tag.service.TagService;
import com.ourtodo.withme.domain.user.db.domain.Member;
import com.ourtodo.withme.domain.user.service.AuthService;
import com.ourtodo.withme.domain.user.service.MemberService;
import com.ourtodo.withme.global.dto.BaseResponse;
import com.ourtodo.withme.global.security.util.SecurityUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tag")
public class TagController {
	private final TagService tagService;
	private final MemberService memberService;

	@PostMapping
	public ResponseEntity<? extends BaseResponse> addTag(@Valid @RequestBody AddTagRequest addTagRequest) {
		Long currentMemberId = SecurityUtil.getCurrentMemberId();
		Member memberById = memberService.findMemberById(currentMemberId);
		tagService.addTag(memberById, addTagRequest.getName(), addTagRequest.getColor());
		return ResponseEntity.status(201).body(new BaseResponse(SUCCESS_CREATE_TAG));
	}

	@GetMapping("/list")
	public ResponseEntity<? extends BaseResponse> findTagList() {
		Long currentMemberId = SecurityUtil.getCurrentMemberId();
		Member memberById = memberService.findMemberById(currentMemberId);
		List<TagDto> tagDtoList = tagService.findTagList(memberById)
			.stream()
			.map(TagDto::new)
			.collect(Collectors.toList());
		return ResponseEntity.status(200).body(new FindTagListResponse(SUCCESS_FIND_TAGS, tagDtoList));
	}
}
