package com.ajou.hertz.domain.instrument.repository;

import org.springframework.data.domain.Page;

import com.ajou.hertz.domain.instrument.constant.InstrumentSortOption;
import com.ajou.hertz.domain.instrument.entity.AcousticAndClassicGuitar;
import com.ajou.hertz.domain.instrument.entity.Amplifier;
import com.ajou.hertz.domain.instrument.entity.AudioEquipment;
import com.ajou.hertz.domain.instrument.entity.BassGuitar;
import com.ajou.hertz.domain.instrument.entity.Effector;
import com.ajou.hertz.domain.instrument.entity.ElectricGuitar;

public interface InstrumentRepositoryCustom {

	Page<ElectricGuitar> findElectricGuitars(int page, int pageSize, InstrumentSortOption sort);

	Page<BassGuitar> findBassGuitars(int page, int pageSize, InstrumentSortOption sort);

	Page<AcousticAndClassicGuitar> findAcousticAndClassicGuitars(
		int page, int pageSize, InstrumentSortOption sort
	);

	Page<Effector> findEffectors(int page, int pageSize, InstrumentSortOption sort);

	Page<Amplifier> findAmplifiers(int page, int pageSize, InstrumentSortOption sort);

	Page<AudioEquipment> findAudioEquipments(int page, int pageSize, InstrumentSortOption sort);
}
