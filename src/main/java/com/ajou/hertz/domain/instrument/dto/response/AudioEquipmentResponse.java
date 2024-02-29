package com.ajou.hertz.domain.instrument.dto.response;

import java.util.List;

import com.ajou.hertz.common.dto.response.AddressResponse;
import com.ajou.hertz.domain.instrument.constant.AudioEquipmentType;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.dto.AudioEquipmentDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AudioEquipmentResponse extends InstrumentResponse {

	@Schema(description = "음향 장비 종류")
	private AudioEquipmentType type;

	private AudioEquipmentResponse(
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
		AudioEquipmentType type
	) {
		super(
			id, sellerId, title, progressStatus, tradeAddress, qualityStatus,
			price, hasAnomaly, description, images, hashtags
		);
		this.type = type;
	}

	public static AudioEquipmentResponse from(AudioEquipmentDto audioEquipmentDto) {
		InstrumentResponse instrumentResponse = InstrumentResponse.from(audioEquipmentDto);
		return new AudioEquipmentResponse(
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
			audioEquipmentDto.getType()
		);
	}
}
