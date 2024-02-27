package com.ajou.hertz.domain.instrument.dto.response;

import java.util.List;

import com.ajou.hertz.common.dto.response.AddressResponse;
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
public class InstrumentResponse {

	@Schema(description = "Id of instrument(electric guitar)", example = "2")
	private Long id;

	@Schema(description = "Id of seller", example = "1")
	private Long sellerId;

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

	public static InstrumentResponse from(InstrumentDto instrumentDto) {
		return new InstrumentResponse(
			instrumentDto.getId(),
			instrumentDto.getSeller().getId(),
			instrumentDto.getTitle(),
			instrumentDto.getProgressStatus(),
			AddressResponse.from(instrumentDto.getTradeAddress()),
			instrumentDto.getQualityStatus(),
			instrumentDto.getPrice(),
			instrumentDto.getHasAnomaly(),
			instrumentDto.getDescription(),
			instrumentDto.getImages()
				.stream()
				.map(InstrumentImageResponse::from)
				.toList(),
			instrumentDto.getHashtags()
		);
	}
}
