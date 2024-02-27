package com.ajou.hertz.domain.instrument.strategy;

import com.ajou.hertz.domain.instrument.dto.request.CreateNewBassGuitarRequest;
import com.ajou.hertz.domain.instrument.entity.BassGuitar;
import com.ajou.hertz.domain.user.entity.User;

public class BassGuitarCreationStrategy implements InstrumentCreationStrategy<BassGuitar, CreateNewBassGuitarRequest> {

	@Override
	public BassGuitar createInstrument(User seller, CreateNewBassGuitarRequest request) {
		return request.toEntity(seller);
	}
}
