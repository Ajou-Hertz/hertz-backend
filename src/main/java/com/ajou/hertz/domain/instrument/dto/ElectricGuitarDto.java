package com.ajou.hertz.domain.instrument.dto;

import java.util.List;

import com.ajou.hertz.common.dto.AddressDto;
import com.ajou.hertz.domain.instrument.constant.ElectricGuitarBrand;
import com.ajou.hertz.domain.instrument.constant.ElectricGuitarModel;
import com.ajou.hertz.domain.instrument.constant.GuitarColor;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.entity.ElectricGuitar;
import com.ajou.hertz.domain.user.dto.UserDto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ElectricGuitarDto extends InstrumentDto {

	private ElectricGuitarBrand brand;
	private ElectricGuitarModel model;
	private Short productionYear;
	private GuitarColor color;

	private ElectricGuitarDto(
		Long id,
		UserDto seller,
		String title,
		InstrumentProgressStatus progressStatus,
		AddressDto tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		ElectricGuitarBrand brand,
		ElectricGuitarModel model,
		Short productionYear,
		GuitarColor color,
		List<InstrumentImageDto> images,
		List<String> hashtags
	) {
		super(
			id, seller, title, progressStatus, tradeAddress, qualityStatus,
			price, hasAnomaly, description, images, hashtags
		);
		this.brand = brand;
		this.model = model;
		this.productionYear = productionYear;
		this.color = color;
	}

	public static ElectricGuitarDto from(ElectricGuitar electricGuitar) {
		InstrumentDto instrumentDto = InstrumentDto.from(electricGuitar);
		return new ElectricGuitarDto(
			instrumentDto.getId(),
			instrumentDto.getSeller(),
			instrumentDto.getTitle(),
			instrumentDto.getProgressStatus(),
			instrumentDto.getTradeAddress(),
			instrumentDto.getQualityStatus(),
			instrumentDto.getPrice(),
			instrumentDto.getHasAnomaly(),
			instrumentDto.getDescription(),
			electricGuitar.getBrand(),
			electricGuitar.getModel(),
			electricGuitar.getProductionYear(),
			electricGuitar.getColor(),
			instrumentDto.getImages(),
			instrumentDto.getHashtags()
		);
	}
}
