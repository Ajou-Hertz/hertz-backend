package com.ajou.hertz.domain.administrative_area.dto.response;

import java.util.List;

import com.ajou.hertz.domain.administrative_area.dto.AdministrativeAreaDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdministrativeAreaSidoResponse {
	private final List<AdministrativeAreaDto> sidoList;

}