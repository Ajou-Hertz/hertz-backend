package com.ajou.hertz.common.dto.request;

import com.ajou.hertz.common.entity.Address;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Getter
public class AddressRequest {

	@Schema(description = "시/도", example = "서울특별시")
	@NotBlank
	private String sido;

	@Schema(description = "시/군/구", example = "강남구")
	@NotBlank
	private String sgg;

	@Schema(description = "읍/면/동", example = "청담동")
	@NotBlank
	private String emd;

	public Address toEntity() {
		return new Address(sido, sgg, emd);
	}
}
