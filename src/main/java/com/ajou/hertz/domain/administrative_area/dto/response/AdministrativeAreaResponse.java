package com.ajou.hertz.domain.administrative_area.dto.response;

import com.ajou.hertz.domain.administrative_area.dto.AdministrativeAreaDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AdministrativeAreaResponse {

	@Schema(description = "Id of administrative area", example = "11")
	private Long id;

	@Schema(description = "행정구역 이름")
	private String name;

	public static AdministrativeAreaResponse from(AdministrativeAreaDto administrativeAreaDto) {
		return new AdministrativeAreaResponse(
			administrativeAreaDto.getId(),
			administrativeAreaDto.getName()
		);
	}
}
