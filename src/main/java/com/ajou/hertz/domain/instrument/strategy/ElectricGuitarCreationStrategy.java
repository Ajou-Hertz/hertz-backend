package com.ajou.hertz.domain.instrument.strategy;

import com.ajou.hertz.domain.instrument.dto.request.CreateNewElectricGuitarRequest;
import com.ajou.hertz.domain.instrument.entity.ElectricGuitar;
import com.ajou.hertz.domain.user.entity.User;

public class ElectricGuitarCreationStrategy
	implements InstrumentCreationStrategy<ElectricGuitar, CreateNewElectricGuitarRequest> {

	@Override
	public ElectricGuitar createInstrument(User seller, CreateNewElectricGuitarRequest request) {
		return request.toEntity(seller);
	}
}
