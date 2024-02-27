package com.ajou.hertz.domain.administrative_area.dto;

import com.ajou.hertz.domain.administrative_area.entity.AdministrativeAreaEmd;
import com.ajou.hertz.domain.administrative_area.entity.AdministrativeAreaSgg;
import com.ajou.hertz.domain.administrative_area.entity.AdministrativeAreaSido;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AdministrativeAreaDto {

	private Long id;
	private String name;

	public static AdministrativeAreaDto fromSido(AdministrativeAreaSido sido) {
		return new AdministrativeAreaDto(sido.getId(), sido.getName());
	}

	public static AdministrativeAreaDto fromSgg(AdministrativeAreaSgg sgg) {
		return new AdministrativeAreaDto(sgg.getId(), sgg.getName());
	}

	public static AdministrativeAreaDto fromEmd(AdministrativeAreaEmd emd) {
		return new AdministrativeAreaDto(emd.getId(), emd.getName());
	}
}
