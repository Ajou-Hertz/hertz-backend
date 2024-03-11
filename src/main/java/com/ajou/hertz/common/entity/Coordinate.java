package com.ajou.hertz.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class Coordinate {

	@Column(nullable = false)
	private Double latitude;

	@Column(nullable = false)
	private Double longitude;

	public static Coordinate of(double latitude, double longitude) {
		return new Coordinate(latitude, longitude);
	}

}
