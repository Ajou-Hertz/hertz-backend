package com.ajou.hertz.domain.instrument.bass_guitar.strategy;

import com.ajou.hertz.domain.instrument.bass_guitar.dto.request.CreateNewBassGuitarRequest;
import com.ajou.hertz.domain.instrument.bass_guitar.entity.BassGuitar;
import com.ajou.hertz.domain.instrument.strategy.InstrumentCreationStrategy;
import com.ajou.hertz.domain.user.entity.User;

public class BassGuitarCreationStrategy implements InstrumentCreationStrategy<BassGuitar, CreateNewBassGuitarRequest> {

	@Override
	public BassGuitar createInstrument(User seller, CreateNewBassGuitarRequest request) {
		return request.toEntity(seller);
	}
}
