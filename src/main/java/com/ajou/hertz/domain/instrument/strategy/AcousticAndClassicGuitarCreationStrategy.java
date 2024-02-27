package com.ajou.hertz.domain.instrument.strategy;

import com.ajou.hertz.domain.instrument.dto.request.CreateNewAcousticAndClassicGuitarRequest;
import com.ajou.hertz.domain.instrument.entity.AcousticAndClassicGuitar;
import com.ajou.hertz.domain.user.entity.User;

public class AcousticAndClassicGuitarCreationStrategy
	implements InstrumentCreationStrategy<AcousticAndClassicGuitar, CreateNewAcousticAndClassicGuitarRequest> {

	@Override
	public AcousticAndClassicGuitar createInstrument(User seller, CreateNewAcousticAndClassicGuitarRequest request) {
		return request.toEntity(seller);
	}
}
