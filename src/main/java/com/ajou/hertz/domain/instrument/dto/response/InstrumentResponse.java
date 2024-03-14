package com.ajou.hertz.domain.instrument.dto.response;

import java.util.List;

import com.ajou.hertz.common.dto.response.AddressResponse;
import com.ajou.hertz.domain.instrument.constant.InstrumentCategory;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.dto.InstrumentDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class InstrumentResponse {

	@Schema(description = "Id of instrument", example = "2")
	private Long id;

	@Schema(description = "Id of seller", example = "1")
	private Long sellerId;

	@Schema(description = "종류")
	private InstrumentCategory category;

	@Schema(description = "제목", example = "펜더 로드원 텔레캐스터")
	private String title;

	@Schema(description = "매물 진행 상태")
	private InstrumentProgressStatus progressStatus;

	@Schema(description = "거래 장소")
	private AddressResponse tradeAddress;

	@Schema(description = "악기 상태. 1~5의 값을 갖습니다.", example = "3")
	private Short qualityStatus;

	@Schema(description = "가격", example = "527000")
	private Integer price;

	@Schema(description = "특이사항 유무", example = "true")
	private Boolean hasAnomaly;

	@Schema(description = "특이사항 및 상세 설명. 내용이 없을 경우에는 빈 문자열로 요청하면 됩니다.", example = "14년 시리얼 펜더 로드원 50 텔레입니다. 기존 ...")
	private String description;

	@Schema(description = "악기 이미지")
	private List<InstrumentImageResponse> images;

	@Schema(description = "해시태그", example = "[\"펜더\", \"Fender\"]")
	private List<String> hashtags;

	protected InstrumentResponse(InstrumentDto instrumentDto) {
		this.id = instrumentDto.getId();
		this.sellerId = instrumentDto.getSeller().getId();
		this.category = instrumentDto.getCategory();
		this.title = instrumentDto.getTitle();
		this.progressStatus = instrumentDto.getProgressStatus();
		this.tradeAddress = AddressResponse.from(instrumentDto.getTradeAddress());
		this.qualityStatus = instrumentDto.getQualityStatus();
		this.price = instrumentDto.getPrice();
		this.hasAnomaly = instrumentDto.getHasAnomaly();
		this.description = instrumentDto.getDescription();
		this.images = instrumentDto.getImages().stream()
			.map(InstrumentImageResponse::from)
			.toList();
		this.hashtags = instrumentDto.getHashtags();
	}
}
