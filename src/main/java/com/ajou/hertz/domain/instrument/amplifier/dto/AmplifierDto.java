package com.ajou.hertz.domain.instrument.amplifier.dto;

import java.util.List;

import com.ajou.hertz.common.dto.AddressDto;
import com.ajou.hertz.domain.instrument.amplifier.constant.AmplifierBrand;
import com.ajou.hertz.domain.instrument.amplifier.constant.AmplifierType;
import com.ajou.hertz.domain.instrument.amplifier.constant.AmplifierUsage;
import com.ajou.hertz.domain.instrument.constant.InstrumentCategory;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.dto.InstrumentDto;
import com.ajou.hertz.domain.instrument.dto.InstrumentImageDto;
import com.ajou.hertz.domain.instrument.amplifier.entity.Amplifier;
import com.ajou.hertz.domain.user.dto.UserDto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AmplifierDto extends InstrumentDto {

	private AmplifierType type;
	private AmplifierBrand brand;
	private AmplifierUsage usage;

	private AmplifierDto(
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
		AmplifierType type,
		AmplifierBrand brand,
		AmplifierUsage usage
	) {
		super(
			id, seller, InstrumentCategory.AMPLIFIER, title, progressStatus, tradeAddress,
			qualityStatus, price, hasAnomaly, description, images, hashtags
		);
		this.type = type;
		this.brand = brand;
		this.usage = usage;
	}

	public AmplifierDto(Amplifier amplifier) {
		super(amplifier);
		this.type = amplifier.getType();
		this.brand = amplifier.getBrand();
		this.usage = amplifier.getUsage();
	}
}
