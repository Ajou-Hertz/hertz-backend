package com.ajou.hertz.domain.administrative_area.dto.response;

import java.util.List;

import com.ajou.hertz.domain.administrative_area.dto.AdministrativeAreaDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdministrativeAreaSggResponse {
	private final List<AdministrativeAreaDto> sggList;
}