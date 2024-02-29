package com.ajou.hertz.domain.instrument.strategy;

import com.ajou.hertz.domain.instrument.dto.request.CreateNewAudioEquipmentRequest;
import com.ajou.hertz.domain.instrument.entity.AudioEquipment;
import com.ajou.hertz.domain.user.entity.User;

public class AudioEquipmentCreationStrategy
	implements InstrumentCreationStrategy<AudioEquipment, CreateNewAudioEquipmentRequest> {

	@Override
	public AudioEquipment createInstrument(User seller, CreateNewAudioEquipmentRequest request) {
		return request.toEntity(seller);
	}
}
