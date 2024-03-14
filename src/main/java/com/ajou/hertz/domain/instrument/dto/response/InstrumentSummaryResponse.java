package com.ajou.hertz.domain.instrument.dto.response;

import com.ajou.hertz.common.dto.response.AddressResponse;
import com.ajou.hertz.domain.instrument.constant.InstrumentCategory;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class InstrumentSummaryResponse {

	@Schema(description = "Id of instrument(electric guitar)", example = "2")
	private Long id;

	@Schema(description = "종류")
	private InstrumentCategory category;

	@Schema(description = "제목", example = "펜더 로드원 텔레캐스터")
	private String title;

	@Schema(description = "매물 진행 상태")
	private InstrumentProgressStatus progressStatus;

	@Schema(description = "거래 장소")
	private AddressResponse tradeAddress;

	@Schema(description = "가격", example = "527000")
	private Integer price;

	@Schema(description = "악기 썸네일 이미지")
	private InstrumentImageResponse thumbnailImage;
}
