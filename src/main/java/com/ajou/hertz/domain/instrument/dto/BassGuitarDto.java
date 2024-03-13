package com.ajou.hertz.domain.instrument.dto;

import java.util.List;

import com.ajou.hertz.common.dto.AddressDto;
import com.ajou.hertz.domain.instrument.constant.BassGuitarBrand;
import com.ajou.hertz.domain.instrument.constant.BassGuitarPickUp;
import com.ajou.hertz.domain.instrument.constant.BassGuitarPreAmplifier;
import com.ajou.hertz.domain.instrument.constant.GuitarColor;
import com.ajou.hertz.domain.instrument.constant.InstrumentCategory;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.entity.BassGuitar;
import com.ajou.hertz.domain.user.dto.UserDto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BassGuitarDto extends InstrumentDto {

	private BassGuitarBrand brand;
	private BassGuitarPickUp pickUp;
	private BassGuitarPreAmplifier preAmplifier;
	private GuitarColor color;

	private BassGuitarDto(
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
		BassGuitarBrand brand,
		BassGuitarPickUp pickUp,
		BassGuitarPreAmplifier preAmplifier,
		GuitarColor color
	) {
		super(
			id, seller, InstrumentCategory.BASS_GUITAR, title, progressStatus, tradeAddress,
			qualityStatus, price, hasAnomaly, description, images, hashtags
		);
		this.brand = brand;
		this.pickUp = pickUp;
		this.preAmplifier = preAmplifier;
		this.color = color;
	}

	private BassGuitarDto(BassGuitar bassGuitar) {
		super(bassGuitar);
		this.brand = bassGuitar.getBrand();
		this.pickUp = bassGuitar.getPickUp();
		this.preAmplifier = bassGuitar.getPreAmplifier();
		this.color = bassGuitar.getColor();
	}

	public static BassGuitarDto from(BassGuitar bassGuitar) {
		return new BassGuitarDto(bassGuitar);
	}
}
