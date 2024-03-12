package com.ajou.hertz.domain.instrument.dto.request;

import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter    // for @ModelAttribute
@Getter
public class InstrumentFilterConditions {

	private InstrumentProgressStatus progress;

	@Schema(description = "거래 지역 - 시/도", example = "서울특별시")
	private String sido;

	@Schema(description = "거래 지역 - 시/군/구", example = "종로구")
	private String sgg;
}
