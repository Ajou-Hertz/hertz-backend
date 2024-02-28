package com.ajou.hertz.domain.instrument.dto;

import java.util.List;

import com.ajou.hertz.common.dto.AddressDto;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassicGuitarBrand;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassicGuitarModel;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassicGuitarPickUp;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassicGuitarWood;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.entity.AcousticAndClassicGuitar;
import com.ajou.hertz.domain.user.dto.UserDto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AcousticAndClassicGuitarDto extends InstrumentDto {

	private AcousticAndClassicGuitarBrand brand;
	private AcousticAndClassicGuitarModel model;
	private AcousticAndClassicGuitarWood wood;
	private AcousticAndClassicGuitarPickUp pickUp;

	private AcousticAndClassicGuitarDto(
		Long id,
		UserDto seller,
		String title,
		InstrumentProgressStatus progressStatus,
		AddressDto tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		List<InstrumentImageDto> images,
		List<String> hashtags,
		AcousticAndClassicGuitarBrand brand,
		AcousticAndClassicGuitarModel model,
		AcousticAndClassicGuitarWood wood,
		AcousticAndClassicGuitarPickUp pickUp
	) {
		super(
			id, seller, title, progressStatus, tradeAddress, qualityStatus,
			price, hasAnomaly, description, images, hashtags
		);
		this.brand = brand;
		this.model = model;
		this.wood = wood;
		this.pickUp = pickUp;
	}

	public static AcousticAndClassicGuitarDto from(AcousticAndClassicGuitar acousticAndClassicGuitar) {
		InstrumentDto instrumentDto = InstrumentDto.from(acousticAndClassicGuitar);
		return new AcousticAndClassicGuitarDto(
			instrumentDto.getId(),
			instrumentDto.getSeller(),
			instrumentDto.getTitle(),
			instrumentDto.getProgressStatus(),
			instrumentDto.getTradeAddress(),
			instrumentDto.getQualityStatus(),
			instrumentDto.getPrice(),
			instrumentDto.getHasAnomaly(),
			instrumentDto.getDescription(),
			instrumentDto.getImages(),
			instrumentDto.getHashtags(),
			acousticAndClassicGuitar.getBrand(),
			acousticAndClassicGuitar.getModel(),
			acousticAndClassicGuitar.getWood(),
			acousticAndClassicGuitar.getPickUp()
		);
	}
}