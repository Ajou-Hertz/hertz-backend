package com.ajou.hertz.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class FullAddress {

	@Column(nullable = false)
	private String sido;

	@Column(nullable = false)
	private String sgg;

	private String lotNumberAddress;

	private String roadAddress;

	@Column(nullable = false)
	private String detailAddress;

	public static FullAddress of(String fulladdress, String detailAddress) {

		String[] parsedAddress = fulladdress.split(" ");

		if (parsedAddress.length < 2) {
			throw new IllegalArgumentException("주소 형식이 올바르지 않습니다.");
		}

		String sido = parsedAddress[0];
		String sgg = parsedAddress[1];

		return new FullAddress(sido, sgg, null, null, detailAddress);
	}
}
