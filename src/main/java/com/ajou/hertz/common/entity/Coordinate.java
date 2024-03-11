package com.ajou.hertz.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Embeddable
public class Coordinate {

	@Column(nullable = false)
	private String latitude;

	@Column(nullable = false)
	private String longitude;

	public static Coordinate of(String latitude, String longitude) {
		return new Coordinate(latitude, longitude);
	}

}
