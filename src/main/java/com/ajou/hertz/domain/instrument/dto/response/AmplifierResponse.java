package com.ajou.hertz.domain.instrument.dto.response;

import java.util.List;

import com.ajou.hertz.common.dto.response.AddressResponse;
import com.ajou.hertz.domain.instrument.constant.AmplifierBrand;
import com.ajou.hertz.domain.instrument.constant.AmplifierType;
import com.ajou.hertz.domain.instrument.constant.AmplifierUsage;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.dto.AmplifierDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AmplifierResponse extends InstrumentResponse {

	@Schema(description = "앰프 종류")
	private AmplifierType type;

	@Schema(description = "앰프 브랜드")
	private AmplifierBrand brand;

	@Schema(description = "앰프 용도")
	private AmplifierUsage usage;

	private AmplifierResponse(
		Long id,
		Long sellerId,
		String title,
		InstrumentProgressStatus progressStatus,
		AddressResponse tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		List<InstrumentImageResponse> images,
		List<String> hashtags,
		AmplifierType type,
		AmplifierBrand brand,
		AmplifierUsage usage
	) {
		super(
			id, sellerId, title, progressStatus, tradeAddress, qualityStatus,
			price, hasAnomaly, description, images, hashtags
		);
		this.type = type;
		this.brand = brand;
		this.usage = usage;
	}

	public static AmplifierResponse from(AmplifierDto amplifierDto) {
		InstrumentResponse instrumentResponse = InstrumentResponse.from(amplifierDto);
		return new AmplifierResponse(
			instrumentResponse.getId(),
			instrumentResponse.getSellerId(),
			instrumentResponse.getTitle(),
			instrumentResponse.getProgressStatus(),
			instrumentResponse.getTradeAddress(),
			instrumentResponse.getQualityStatus(),
			instrumentResponse.getPrice(),
			instrumentResponse.getHasAnomaly(),
			instrumentResponse.getDescription(),
			instrumentResponse.getImages(),
			instrumentResponse.getHashtags(),
			amplifierDto.getType(),
			amplifierDto.getBrand(),
			amplifierDto.getUsage()
		);
	}
}
