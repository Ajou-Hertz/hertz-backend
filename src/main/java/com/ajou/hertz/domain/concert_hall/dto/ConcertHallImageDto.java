package com.ajou.hertz.domain.concert_hall.dto;

import com.ajou.hertz.domain.concert_hall.entity.ConcertHallImage;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ConcertHallImageDto {
	private Long id;
	private String name;
	private String url;

	public static ConcertHallImageDto from(ConcertHallImage concertHallImage) {
		return new ConcertHallImageDto(
			concertHallImage.getId(),
			concertHallImage.getOriginalName(),
			concertHallImage.getUrl()
		);
	}
}
