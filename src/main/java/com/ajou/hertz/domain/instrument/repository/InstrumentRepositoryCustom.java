package com.ajou.hertz.domain.instrument.repository;

import org.springframework.data.domain.Page;

import com.ajou.hertz.domain.instrument.constant.InstrumentSortOption;
import com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.dto.request.AcousticAndClassicGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.AmplifierFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.AudioEquipmentFilterConditions;
import com.ajou.hertz.domain.instrument.bass_guitar.dto.request.BassGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.effector.dto.request.EffectorFilterConditions;
import com.ajou.hertz.domain.instrument.electric_guitar.dto.request.ElectricGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.entity.AcousticAndClassicGuitar;
import com.ajou.hertz.domain.instrument.entity.Amplifier;
import com.ajou.hertz.domain.instrument.entity.AudioEquipment;
import com.ajou.hertz.domain.instrument.bass_guitar.entity.BassGuitar;
import com.ajou.hertz.domain.instrument.effector.entity.Effector;
import com.ajou.hertz.domain.instrument.electric_guitar.entity.ElectricGuitar;

public interface InstrumentRepositoryCustom {

	Page<ElectricGuitar> findElectricGuitars(
		int page, int pageSize, InstrumentSortOption sort, ElectricGuitarFilterConditions filterConditions
	);

	Page<BassGuitar> findBassGuitars(
		int page, int pageSize, InstrumentSortOption sort, BassGuitarFilterConditions filterConditions
	);

	Page<AcousticAndClassicGuitar> findAcousticAndClassicGuitars(
		int page, int pageSize, InstrumentSortOption sort, AcousticAndClassicGuitarFilterConditions filterConditions
	);

	Page<Effector> findEffectors(
		int page, int pageSize, InstrumentSortOption sort, EffectorFilterConditions filterConditions
	);

	Page<Amplifier> findAmplifiers(
		int page, int pageSize, InstrumentSortOption sort, AmplifierFilterConditions filterConditions
	);

	Page<AudioEquipment> findAudioEquipments(
		int page, int pageSize, InstrumentSortOption sort, AudioEquipmentFilterConditions filterConditions
	);
}
