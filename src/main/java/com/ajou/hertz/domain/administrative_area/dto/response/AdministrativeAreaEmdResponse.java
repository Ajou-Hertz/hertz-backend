package com.ajou.hertz.domain.administrative_area.dto.response;

import java.util.List;

import com.ajou.hertz.domain.administrative_area.dto.AdministrativeAreaDto;

import lombok.Getter;

@Getter
public class AdministrativeAreaEmdResponse {
	private final List<AdministrativeAreaDto> EmdList;

	public AdministrativeAreaEmdResponse(List<AdministrativeAreaDto> EmdList) {
		this.EmdList = EmdList;
	}
}