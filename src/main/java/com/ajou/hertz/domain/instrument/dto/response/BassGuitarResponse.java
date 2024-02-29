package com.ajou.hertz.domain.instrument.dto.response;

import java.util.List;

import com.ajou.hertz.common.dto.response.AddressResponse;
import com.ajou.hertz.domain.instrument.constant.BassGuitarBrand;
import com.ajou.hertz.domain.instrument.constant.BassGuitarPickUp;
import com.ajou.hertz.domain.instrument.constant.BassGuitarPreAmplifier;
import com.ajou.hertz.domain.instrument.constant.GuitarColor;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.dto.BassGuitarDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BassGuitarResponse extends InstrumentResponse {

	@Schema(description = "브랜드")
	private BassGuitarBrand brand;

	@Schema(description = "픽업 종류")
	private BassGuitarPickUp pickUp;

	@Schema(description = "프리앰프 종류")
	private BassGuitarPreAmplifier preAmplifier;

	@Schema(description = "색상")
	private GuitarColor color;

	private BassGuitarResponse(
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
		BassGuitarBrand brand,
		BassGuitarPickUp pickUp,
		BassGuitarPreAmplifier preAmplifier,
		GuitarColor color
	) {
		super(
			id, sellerId, title, progressStatus, tradeAddress, qualityStatus,
			price, hasAnomaly, description, images, hashtags
		);
		this.brand = brand;
		this.pickUp = pickUp;
		this.preAmplifier = preAmplifier;
		this.color = color;
	}

	public static BassGuitarResponse from(BassGuitarDto bassGuitarDto) {
		InstrumentResponse instrumentResponse = InstrumentResponse.from(bassGuitarDto);
		return new BassGuitarResponse(
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
			bassGuitarDto.getBrand(),
			bassGuitarDto.getPickUp(),
			bassGuitarDto.getPreAmplifier(),
			bassGuitarDto.getColor()
		);
	}
}
