package com.ajou.hertz.common.dto;

import com.ajou.hertz.common.entity.Address;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AddressDto {

	private String sido;
	private String sgg;
	private String emd;

	public static AddressDto from(Address address) {
		return new AddressDto(address.getSido(), address.getSgg(), address.getEmd());
	}
}
