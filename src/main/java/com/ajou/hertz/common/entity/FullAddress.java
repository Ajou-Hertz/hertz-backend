package com.ajou.hertz.common.entity;

import java.util.Arrays;

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
		StringBuilder sggBuilder = new StringBuilder();
		String lotNumberAddress = null;
		String roadAddress = null;

		int i;
		for (i = 1; i < parsedAddress.length; i++) {
			if (parsedAddress[i].matches(".*[동면읍소로길]$")) {
				break;
			}
			sggBuilder.append(parsedAddress[i]).append(" ");
		}

		String sgg = sggBuilder.toString().trim();

		if (fulladdress.matches(".+[로길].+")) {
			roadAddress = String.join(" ", Arrays.copyOfRange(parsedAddress, i, parsedAddress.length));
		} else {
			lotNumberAddress = String.join(" ", Arrays.copyOfRange(parsedAddress, i, parsedAddress.length));
		}

		return new FullAddress(sido, sgg, lotNumberAddress, roadAddress, detailAddress);
	}

}