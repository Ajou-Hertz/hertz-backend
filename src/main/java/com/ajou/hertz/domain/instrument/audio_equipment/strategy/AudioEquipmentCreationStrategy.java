package com.ajou.hertz.domain.instrument.audio_equipment.strategy;

import com.ajou.hertz.domain.instrument.audio_equipment.dto.request.CreateNewAudioEquipmentRequest;
import com.ajou.hertz.domain.instrument.audio_equipment.entity.AudioEquipment;
import com.ajou.hertz.domain.instrument.strategy.InstrumentCreationStrategy;
import com.ajou.hertz.domain.user.entity.User;

public class AudioEquipmentCreationStrategy
	implements InstrumentCreationStrategy<AudioEquipment, CreateNewAudioEquipmentRequest> {

	@Override
	public AudioEquipment createInstrument(User seller, CreateNewAudioEquipmentRequest request) {
		return request.toEntity(seller);
	}
}
