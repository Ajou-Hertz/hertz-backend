package com.ajou.hertz.domain.instrument.dto;

import java.util.List;

import com.ajou.hertz.common.dto.AddressDto;
import com.ajou.hertz.domain.instrument.constant.EffectorFeature;
import com.ajou.hertz.domain.instrument.constant.EffectorType;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.entity.Effector;
import com.ajou.hertz.domain.user.dto.UserDto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class EffectorDto extends InstrumentDto {

	private EffectorType type;
	private EffectorFeature feature;

	private EffectorDto(
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
		EffectorType type,
		EffectorFeature feature
	) {
		super(
			id, seller, title, progressStatus, tradeAddress, qualityStatus,
			price, hasAnomaly, description, images, hashtags
		);
		this.type = type;
		this.feature = feature;
	}

	public static EffectorDto from(Effector effector) {
		InstrumentDto instrumentDto = InstrumentDto.from(effector);
		return new EffectorDto(
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
			effector.getType(),
			effector.getFeature()
		);
	}
}
