package com.ajou.hertz.domain.instrument.dto.response;

import java.util.List;

import com.ajou.hertz.common.dto.response.AddressResponse;
import com.ajou.hertz.domain.instrument.constant.ElectricGuitarBrand;
import com.ajou.hertz.domain.instrument.constant.ElectricGuitarModel;
import com.ajou.hertz.domain.instrument.constant.GuitarColor;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.dto.ElectricGuitarDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ElectricGuitarResponse {

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

	@Schema(description = "브랜드")
	private ElectricGuitarBrand brand;

	@Schema(description = "모델")
	private ElectricGuitarModel model;

	@Schema(description = "생상연도", example = "2014")
	private Short productionYear;

	@Schema(description = "색상")
	private GuitarColor color;

	@Schema(description = "악기 이미지")
	private List<InstrumentImageResponse> images;

	@Schema(description = "해시태그", example = "[\"펜더\", \"Fender\"]")
	private List<String> hashtags;

	public static ElectricGuitarResponse from(ElectricGuitarDto electricGuitarDto) {
		return new ElectricGuitarResponse(
			electricGuitarDto.getId(),
			electricGuitarDto.getSeller().getId(),
			electricGuitarDto.getTitle(),
			electricGuitarDto.getProgressStatus(),
			AddressResponse.from(electricGuitarDto.getTradeAddress()),
			electricGuitarDto.getQualityStatus(),
			electricGuitarDto.getPrice(),
			electricGuitarDto.getHasAnomaly(),
			electricGuitarDto.getDescription(),
			electricGuitarDto.getBrand(),
			electricGuitarDto.getModel(),
			electricGuitarDto.getProductionYear(),
			electricGuitarDto.getColor(),
			electricGuitarDto.getImages()
				.stream()
				.map(InstrumentImageResponse::from)
				.toList(),
			electricGuitarDto.getHashtags()
		);
	}
}
