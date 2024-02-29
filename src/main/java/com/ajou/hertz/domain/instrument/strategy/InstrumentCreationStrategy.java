package com.ajou.hertz.domain.instrument.strategy;

import com.ajou.hertz.domain.instrument.dto.request.CreateNewInstrumentRequest;
import com.ajou.hertz.domain.instrument.entity.Instrument;
import com.ajou.hertz.domain.user.entity.User;

public interface InstrumentCreationStrategy<T extends Instrument, U extends CreateNewInstrumentRequest<T>> {

	T createInstrument(User seller, U request);
}
