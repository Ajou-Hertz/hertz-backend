package com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.strategy;

import com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.dto.request.CreateNewAcousticAndClassicGuitarRequest;
import com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.entity.AcousticAndClassicGuitar;
import com.ajou.hertz.domain.instrument.strategy.InstrumentCreationStrategy;
import com.ajou.hertz.domain.user.entity.User;

public class AcousticAndClassicGuitarCreationStrategy
	implements InstrumentCreationStrategy<AcousticAndClassicGuitar, CreateNewAcousticAndClassicGuitarRequest> {

	@Override
	public AcousticAndClassicGuitar createInstrument(User seller, CreateNewAcousticAndClassicGuitarRequest request) {
		return request.toEntity(seller);
	}
}
