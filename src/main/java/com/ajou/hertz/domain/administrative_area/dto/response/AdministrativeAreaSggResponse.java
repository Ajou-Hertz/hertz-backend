package com.ajou.hertz.domain.administrative_area.dto.response;

import java.util.List;

import com.ajou.hertz.domain.administrative_area.dto.AdministrativeAreaDto;

import lombok.Getter;

@Getter
public class AdministrativeAreaSggResponse {
	private final List<AdministrativeAreaDto> SggList;

	public AdministrativeAreaSggResponse(List<AdministrativeAreaDto> SggList) {
		this.SggList = SggList;
	}
}