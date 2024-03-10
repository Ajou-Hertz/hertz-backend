package com.ajou.hertz.domain.instrument.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ajou.hertz.domain.instrument.constant.InstrumentSortOption;
import com.ajou.hertz.domain.instrument.dto.AcousticAndClassicGuitarDto;
import com.ajou.hertz.domain.instrument.dto.AmplifierDto;
import com.ajou.hertz.domain.instrument.dto.AudioEquipmentDto;
import com.ajou.hertz.domain.instrument.dto.BassGuitarDto;
import com.ajou.hertz.domain.instrument.dto.EffectorDto;
import com.ajou.hertz.domain.instrument.dto.ElectricGuitarDto;
import com.ajou.hertz.domain.instrument.dto.InstrumentDto;
import com.ajou.hertz.domain.instrument.repository.InstrumentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class InstrumentQueryService {

	private final InstrumentRepository instrumentRepository;

	public Page<InstrumentDto> findInstruments(int page, int pageSize, InstrumentSortOption sort) {
		return instrumentRepository
			.findAll(PageRequest.of(page, pageSize, sort.toSort()))
			.map(InstrumentDto::from);
	}

	public Page<ElectricGuitarDto> findElectricGuitars(int page, int pageSize, InstrumentSortOption sort) {
		return instrumentRepository
			.findElectricGuitars(page, pageSize, sort)
			.map(ElectricGuitarDto::from);
	}

	public Page<BassGuitarDto> findBassGuitars(int page, int pageSize, InstrumentSortOption sort) {
		return instrumentRepository
			.findBassGuitars(page, pageSize, sort)
			.map(BassGuitarDto::from);
	}

	public Page<AcousticAndClassicGuitarDto> findAcousticAndClassicGuitars(
		int page, int pageSize, InstrumentSortOption sort
	) {
		return instrumentRepository
			.findAcousticAndClassicGuitars(page, pageSize, sort)
			.map(AcousticAndClassicGuitarDto::from);
	}

	public Page<EffectorDto> findEffectors(int page, int pageSize, InstrumentSortOption sort) {
		return instrumentRepository
			.findEffectors(page, pageSize, sort)
			.map(EffectorDto::from);
	}

	public Page<AmplifierDto> findAmplifiers(int page, int pageSize, InstrumentSortOption sort) {
		return instrumentRepository
			.findAmplifiers(page, pageSize, sort)
			.map(AmplifierDto::from);
	}

	public Page<AudioEquipmentDto> findAudioEquipments(int page, int pageSize, InstrumentSortOption sort) {
		return instrumentRepository
			.findAudioEquipments(page, pageSize, sort)
			.map(AudioEquipmentDto::from);
	}
}
