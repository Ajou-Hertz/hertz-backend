package com.ajou.hertz.domain.instrument.electric_guitar.dto;

import java.util.List;

import com.ajou.hertz.common.dto.AddressDto;
import com.ajou.hertz.domain.instrument.dto.InstrumentDto;
import com.ajou.hertz.domain.instrument.dto.InstrumentImageDto;
import com.ajou.hertz.domain.instrument.electric_guitar.constant.ElectricGuitarBrand;
import com.ajou.hertz.domain.instrument.electric_guitar.constant.ElectricGuitarModel;
import com.ajou.hertz.domain.instrument.constant.GuitarColor;
import com.ajou.hertz.domain.instrument.constant.InstrumentCategory;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.electric_guitar.entity.ElectricGuitar;
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
		List<InstrumentImageDto> images,
		List<String> hashtags,
		ElectricGuitarBrand brand,
		ElectricGuitarModel model,
		Short productionYear,
		GuitarColor color
	) {
		super(
			id, seller, InstrumentCategory.ELECTRIC_GUITAR, title, progressStatus, tradeAddress,
			qualityStatus, price, hasAnomaly, description, images, hashtags
		);
		this.brand = brand;
		this.model = model;
		this.productionYear = productionYear;
		this.color = color;
	}

	public ElectricGuitarDto(ElectricGuitar electricGuitar) {
		super(electricGuitar);
		this.brand = electricGuitar.getBrand();
		this.model = electricGuitar.getModel();
		this.productionYear = electricGuitar.getProductionYear();
		this.color = electricGuitar.getColor();
	}
}
