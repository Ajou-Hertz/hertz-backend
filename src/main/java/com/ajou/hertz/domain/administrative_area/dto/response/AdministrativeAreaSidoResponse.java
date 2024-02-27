package com.ajou.hertz.domain.administrative_area.dto.response;

import java.util.List;

import com.ajou.hertz.domain.administrative_area.dto.AdministrativeAreaDto;

import lombok.Getter;

@Getter
public class AdministrativeAreaSidoResponse {
	private final List<AdministrativeAreaDto> SidoList;

	public AdministrativeAreaSidoResponse(List<AdministrativeAreaDto> SidoList) {
		this.SidoList = SidoList;
	}
}