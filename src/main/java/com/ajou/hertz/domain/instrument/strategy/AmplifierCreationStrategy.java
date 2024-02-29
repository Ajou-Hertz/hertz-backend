package com.ajou.hertz.domain.instrument.strategy;

import com.ajou.hertz.domain.instrument.dto.request.CreateNewAmplifierRequest;
import com.ajou.hertz.domain.instrument.entity.Amplifier;
import com.ajou.hertz.domain.user.entity.User;

public class AmplifierCreationStrategy implements InstrumentCreationStrategy<Amplifier, CreateNewAmplifierRequest> {

	@Override
	public Amplifier createInstrument(User seller, CreateNewAmplifierRequest request) {
		return request.toEntity(seller);
	}
}
