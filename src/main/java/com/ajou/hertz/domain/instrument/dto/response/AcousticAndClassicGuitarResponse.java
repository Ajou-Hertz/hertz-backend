package com.ajou.hertz.domain.instrument.dto.response;

import java.util.List;

import com.ajou.hertz.common.dto.response.AddressResponse;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassicGuitarBrand;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassicGuitarModel;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassicGuitarPickUp;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassicGuitarWood;
import com.ajou.hertz.domain.instrument.constant.InstrumentCategory;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.dto.AcousticAndClassicGuitarDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AcousticAndClassicGuitarResponse extends InstrumentResponse {

	@Schema(description = "브랜드")
	private AcousticAndClassicGuitarBrand brand;

	@Schema(description = "모델")
	private AcousticAndClassicGuitarModel model;

	@Schema(description = "목재")
	private AcousticAndClassicGuitarWood wood;

	@Schema(description = "픽업 종류")
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
			id, sellerId, InstrumentCategory.ACOUSTIC_AND_CLASSIC_GUITAR, title, progressStatus, tradeAddress,
			qualityStatus, price, hasAnomaly, description, images, hashtags
		);
		this.brand = brand;
		this.model = model;
		this.wood = wood;
		this.pickUp = pickUp;
	}

	public AcousticAndClassicGuitarResponse(AcousticAndClassicGuitarDto acousticAndClassicGuitarDto) {
		super(acousticAndClassicGuitarDto);
		this.brand = acousticAndClassicGuitarDto.getBrand();
		this.model = acousticAndClassicGuitarDto.getModel();
		this.wood = acousticAndClassicGuitarDto.getWood();
		this.pickUp = acousticAndClassicGuitarDto.getPickUp();
	}
}
