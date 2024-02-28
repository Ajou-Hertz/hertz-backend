package com.ajou.hertz.domain.instrument.strategy;

import com.ajou.hertz.domain.instrument.dto.request.CreateNewEffectorRequest;
import com.ajou.hertz.domain.instrument.entity.Effector;
import com.ajou.hertz.domain.user.entity.User;

public class EffectorCreationStrategy implements InstrumentCreationStrategy<Effector, CreateNewEffectorRequest> {

	@Override
	public Effector createInstrument(User seller, CreateNewEffectorRequest request) {
		return request.toEntity(seller);
	}
}
