package com.ajou.hertz.domain.instrument.dto;

import com.ajou.hertz.domain.instrument.entity.InstrumentImage;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class InstrumentImageDto {

	private Long id;
	private String name;
	private String url;

	public static InstrumentImageDto from(InstrumentImage instrumentImage) {
		return new InstrumentImageDto(
			instrumentImage.getId(),
			instrumentImage.getOriginalName(),
			instrumentImage.getUrl()
		);
	}
}
