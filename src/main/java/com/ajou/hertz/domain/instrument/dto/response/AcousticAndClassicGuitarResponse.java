package com.ajou.hertz.domain.instrument.dto.response;

import java.util.List;

import com.ajou.hertz.common.dto.response.AddressResponse;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassicGuitarBrand;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassicGuitarModel;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassicGuitarPickUp;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassicGuitarWood;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.dto.AcousticAndClassicGuitarDto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AcousticAndClassicGuitarResponse extends InstrumentResponse {

	private AcousticAndClassicGuitarBrand brand;
	private AcousticAndClassicGuitarModel model;
	private AcousticAndClassicGuitarWood wood;
	private AcousticAndClassicGuitarPickUp pickUp;

	private AcousticAndClassicGuitarResponse(
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
		AcousticAndClassicGuitarBrand brand,
		AcousticAndClassicGuitarModel model,
		AcousticAndClassicGuitarWood wood,
		AcousticAndClassicGuitarPickUp pickUp
	) {
		super(
			id, sellerId, title, progressStatus, tradeAddress, qualityStatus,
			price, hasAnomaly, description, images, hashtags
		);
		this.brand = brand;
		this.model = model;
		this.wood = wood;
		this.pickUp = pickUp;
	}

	public static AcousticAndClassicGuitarResponse from(AcousticAndClassicGuitarDto acousticAndClassicGuitarDto) {
		InstrumentResponse instrumentResponse = InstrumentResponse.from(acousticAndClassicGuitarDto);
		return new AcousticAndClassicGuitarResponse(
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
			acousticAndClassicGuitarDto.getBrand(),
			acousticAndClassicGuitarDto.getModel(),
			acousticAndClassicGuitarDto.getWood(),
			acousticAndClassicGuitarDto.getPickUp()
		);
	}
}
