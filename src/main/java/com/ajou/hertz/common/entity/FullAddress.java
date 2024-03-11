package com.ajou.hertz.common.entity;

import java.util.Arrays;

import com.ajou.hertz.common.exception.constant.CustomExceptionType;
import com.ajou.hertz.common.exception.full_address.InvalidAddressFormatException;

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

	public static FullAddress of(String fullAddress, String detailAddress) throws InvalidAddressFormatException {

		String[] parsedAddress = fullAddress.split(" ");

		if (parsedAddress.length < 2) {
			throw new InvalidAddressFormatException(CustomExceptionType.INVALID_ADDRESS_FORMAT);
		}

		String sido = parsedAddress[0];
		StringBuilder sggBuilder = new StringBuilder();
		String lotNumberAddress = null;
		String roadAddress = null;

		int i = Arrays.stream(parsedAddress)
			.skip(1)
			.takeWhile(part -> !part.matches(".*[동면읍소로길]$"))
			.peek(part -> sggBuilder.append(part).append(" "))
			.toArray().length + 1;

		String sgg = sggBuilder.toString().trim();

		if (fullAddress.matches(".+[로길].+")) {
			roadAddress = String.join(" ", Arrays.copyOfRange(parsedAddress, i, parsedAddress.length));
		} else {
			lotNumberAddress = String.join(" ", Arrays.copyOfRange(parsedAddress, i, parsedAddress.length));
		}

		return new FullAddress(sido, sgg, lotNumberAddress, roadAddress, detailAddress);
	}

}