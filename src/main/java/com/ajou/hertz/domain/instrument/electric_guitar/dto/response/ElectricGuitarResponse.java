package com.ajou.hertz.domain.instrument.electric_guitar.dto.response;

import java.util.List;

import com.ajou.hertz.common.dto.response.AddressResponse;
import com.ajou.hertz.domain.instrument.dto.response.InstrumentImageResponse;
import com.ajou.hertz.domain.instrument.dto.response.InstrumentResponse;
import com.ajou.hertz.domain.instrument.electric_guitar.constant.ElectricGuitarBrand;
import com.ajou.hertz.domain.instrument.electric_guitar.constant.ElectricGuitarModel;
import com.ajou.hertz.domain.instrument.constant.GuitarColor;
import com.ajou.hertz.domain.instrument.constant.InstrumentCategory;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.electric_guitar.dto.ElectricGuitarDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ElectricGuitarResponse extends InstrumentResponse {

	@Schema(description = "브랜드")
	private ElectricGuitarBrand brand;

	@Schema(description = "모델")
	private ElectricGuitarModel model;

	@Schema(description = "생상연도", example = "2014")
	private Short productionYear;

	@Schema(description = "색상")
	private GuitarColor color;

	private ElectricGuitarResponse(
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
		ElectricGuitarBrand brand,
		ElectricGuitarModel model,
		Short productionYear,
		GuitarColor color
	) {
		super(
			id, sellerId, InstrumentCategory.ELECTRIC_GUITAR, title, progressStatus, tradeAddress,
			qualityStatus, price, hasAnomaly, description, images, hashtags
		);
		this.brand = brand;
		this.model = model;
		this.productionYear = productionYear;
		this.color = color;
	}

	public ElectricGuitarResponse(ElectricGuitarDto electricGuitarDto) {
		super(electricGuitarDto);
		this.brand = electricGuitarDto.getBrand();
		this.model = electricGuitarDto.getModel();
		this.productionYear = electricGuitarDto.getProductionYear();
		this.color = electricGuitarDto.getColor();
	}
}
