package com.ajou.hertz.common.entity;

import java.util.Arrays;

import com.ajou.hertz.common.exception.InvalidAddressFormatException;

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
public class FullAddress {

	@Column(nullable = false)
	private String sido;

	@Column(nullable = false)
	private String sgg;

	private String lotNumberAddress;

	private String roadAddress;

	@Column(nullable = false)
	private String detailAddress;

	public static FullAddress of(String fullAddress, String detailAddress) {

		String[] parsedAddress = fullAddress.split(" ");

		if (parsedAddress.length < 2) {
			throw new InvalidAddressFormatException(fullAddress);

		}

		String sido = parsedAddress[0];
		StringBuilder sggBuilder = new StringBuilder();
		String lotNumberAddress = null;
		String roadAddress = null;

		int num;
		for (num = 1; num < parsedAddress.length; num++) {
			if (parsedAddress[num].matches(".*[동면읍소로길]$")) {
				break;
			}
			sggBuilder.append(parsedAddress[num]).append(" ");
		}

		String sgg = sggBuilder.toString().trim();

		if (parsedAddress.length > num) {
			if (fullAddress.matches(".+[로길].+")) {
				roadAddress = String.join(" ", Arrays.copyOfRange(parsedAddress, num, parsedAddress.length));
			} else {
				lotNumberAddress = String.join(" ", Arrays.copyOfRange(parsedAddress, num, parsedAddress.length));
			}
		}

		return new FullAddress(sido, sgg, lotNumberAddress, roadAddress, detailAddress);
	}

}