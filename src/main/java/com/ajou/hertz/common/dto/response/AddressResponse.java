package com.ajou.hertz.common.dto.response;

import com.ajou.hertz.common.dto.AddressDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AddressResponse {

	@Schema(description = "시/도", example = "서울특별시")
	private String sido;

	@Schema(description = "시/군/구", example = "강남구")
	private String sgg;

	@Schema(description = "읍/면/동", example = "청담동")
	private String emd;

	public static AddressResponse from(AddressDto addressDto) {
		return new AddressResponse(addressDto.getSido(), addressDto.getSgg(), addressDto.getEmd());
	}
}
