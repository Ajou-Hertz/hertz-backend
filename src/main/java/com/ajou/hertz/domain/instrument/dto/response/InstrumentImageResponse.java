package com.ajou.hertz.domain.instrument.dto.response;

import com.ajou.hertz.domain.instrument.dto.InstrumentImageDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class InstrumentImageResponse {

	@Schema(description = "Id of instrument image", example = "3")
	private Long id;

	@Schema(description = "이미지 이름", example = "fender-guitar.jpg")
	private String name;

	@Schema(description = "이미지 url", example = "https://instrument-image-url")
	private String url;

	public static InstrumentImageResponse from(InstrumentImageDto instrumentImageDto) {
		return new InstrumentImageResponse(
			instrumentImageDto.getId(),
			instrumentImageDto.getName(),
			instrumentImageDto.getUrl()
		);
	}
}
