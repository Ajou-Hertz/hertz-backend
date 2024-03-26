package com.ajou.hertz.domain.instrument.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.dto.request.AcousticAndClassicGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.entity.AcousticAndClassicGuitar;
import com.ajou.hertz.domain.instrument.amplifier.dto.request.AmplifierFilterConditions;
import com.ajou.hertz.domain.instrument.amplifier.entity.Amplifier;
import com.ajou.hertz.domain.instrument.audio_equipment.dto.request.AudioEquipmentFilterConditions;
import com.ajou.hertz.domain.instrument.audio_equipment.entity.AudioEquipment;
import com.ajou.hertz.domain.instrument.bass_guitar.dto.request.BassGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.bass_guitar.entity.BassGuitar;
import com.ajou.hertz.domain.instrument.constant.InstrumentSortOption;
import com.ajou.hertz.domain.instrument.effector.dto.request.EffectorFilterConditions;
import com.ajou.hertz.domain.instrument.effector.entity.Effector;
import com.ajou.hertz.domain.instrument.electric_guitar.dto.request.ElectricGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.electric_guitar.entity.ElectricGuitar;

public interface InstrumentRepositoryCustom {

	Optional<ElectricGuitar> findElectricGuitarById(Long id);

	Optional<BassGuitar> findBassGuitarById(Long id);

	Optional<AcousticAndClassicGuitar> findAcousticAndClassicGuitarById(Long id);

	Optional<Effector> findEffectorById(Long id);

	Optional<Amplifier> findAmplifierById(Long id);

	Optional<AudioEquipment> findAudioEquipmentById(Long id);

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
