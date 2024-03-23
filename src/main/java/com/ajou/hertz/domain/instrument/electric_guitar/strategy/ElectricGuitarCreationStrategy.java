package com.ajou.hertz.domain.instrument.electric_guitar.strategy;

import com.ajou.hertz.domain.instrument.electric_guitar.dto.request.CreateNewElectricGuitarRequest;
import com.ajou.hertz.domain.instrument.electric_guitar.entity.ElectricGuitar;
import com.ajou.hertz.domain.instrument.strategy.InstrumentCreationStrategy;
import com.ajou.hertz.domain.user.entity.User;

public class ElectricGuitarCreationStrategy
	implements InstrumentCreationStrategy<ElectricGuitar, CreateNewElectricGuitarRequest> {

	@Override
	public ElectricGuitar createInstrument(User seller, CreateNewElectricGuitarRequest request) {
		return request.toEntity(seller);
	}
}
