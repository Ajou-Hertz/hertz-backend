package com.ajou.hertz.domain.instrument.dto;

import java.util.List;

import com.ajou.hertz.common.dto.AddressDto;
import com.ajou.hertz.domain.instrument.constant.AudioEquipmentType;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.entity.AudioEquipment;
import com.ajou.hertz.domain.user.dto.UserDto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AudioEquipmentDto extends InstrumentDto {

	private AudioEquipmentType type;

	private AudioEquipmentDto(
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
		AudioEquipmentType type
	) {
		super(
			id, seller, title, progressStatus, tradeAddress, qualityStatus,
			price, hasAnomaly, description, images, hashtags
		);
		this.type = type;
	}

	public static AudioEquipmentDto from(AudioEquipment audioEquipment) {
		InstrumentDto instrumentDto = InstrumentDto.from(audioEquipment);
		return new AudioEquipmentDto(
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
			audioEquipment.getType()
		);
	}
}