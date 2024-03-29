package com.ajou.hertz.domain.instrument.audio_equipment.dto.response;

import java.util.List;

import com.ajou.hertz.common.dto.response.AddressResponse;
import com.ajou.hertz.domain.instrument.audio_equipment.constant.AudioEquipmentType;
import com.ajou.hertz.domain.instrument.constant.InstrumentCategory;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.audio_equipment.dto.AudioEquipmentDto;
import com.ajou.hertz.domain.instrument.dto.response.InstrumentImageResponse;
import com.ajou.hertz.domain.instrument.dto.response.InstrumentResponse;

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
			id, sellerId, InstrumentCategory.AUDIO_EQUIPMENT, title, progressStatus, tradeAddress,
			qualityStatus, price, hasAnomaly, description, images, hashtags
		);
		this.type = type;
	}

	public AudioEquipmentResponse(AudioEquipmentDto audioEquipmentDto) {
		super(audioEquipmentDto);
		this.type = audioEquipmentDto.getType();
	}
}
