package com.ourtodo.withme.domain.tag.dto.response;

import java.util.List;

import com.ourtodo.withme.domain.tag.dto.TagDto;
import com.ourtodo.withme.global.dto.BaseResponse;

import lombok.Getter;

@Getter
public class FindTagListResponse extends BaseResponse {
	List<TagDto> tagList;

	public FindTagListResponse(String msg, List<TagDto> tagList) {
		super(msg);
		this.tagList = tagList;
	}
}
