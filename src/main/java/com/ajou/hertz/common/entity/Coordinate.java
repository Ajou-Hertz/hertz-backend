package com.ajou.hertz.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Embeddable
public class Coordinate {

	@Column(nullable = false)
	private String lat;

	@Column(nullable = false)
	private String lng;

}
