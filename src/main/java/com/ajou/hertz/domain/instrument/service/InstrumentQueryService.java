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
import com.ajou.hertz.domain.instrument.dto.request.AcousticAndClassicGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.AmplifierFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.AudioEquipmentFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.BassGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.EffectorFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.ElectricGuitarFilterConditions;
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

	public Page<ElectricGuitarDto> findElectricGuitars(
		int page,
		int pageSize,
		InstrumentSortOption sort,
		ElectricGuitarFilterConditions filterConditions
	) {
		return instrumentRepository
			.findElectricGuitars(page, pageSize, sort, filterConditions)
			.map(ElectricGuitarDto::from);
	}

	public Page<BassGuitarDto> findBassGuitars(
		int page,
		int pageSize,
		InstrumentSortOption sort,
		BassGuitarFilterConditions filterConditions
	) {
		return instrumentRepository
			.findBassGuitars(page, pageSize, sort, filterConditions)
			.map(BassGuitarDto::from);
	}

	public Page<AcousticAndClassicGuitarDto> findAcousticAndClassicGuitars(
		int page,
		int pageSize,
		InstrumentSortOption sort,
		AcousticAndClassicGuitarFilterConditions filterConditions
	) {
		return instrumentRepository
			.findAcousticAndClassicGuitars(page, pageSize, sort, filterConditions)
			.map(AcousticAndClassicGuitarDto::from);
	}

	public Page<EffectorDto> findEffectors(
		int page,
		int pageSize,
		InstrumentSortOption sort,
		EffectorFilterConditions filterConditions
	) {
		return instrumentRepository
			.findEffectors(page, pageSize, sort, filterConditions)
			.map(EffectorDto::from);
	}

	public Page<AmplifierDto> findAmplifiers(
		int page,
		int pageSize,
		InstrumentSortOption sort,
		AmplifierFilterConditions filterConditions
	) {
		return instrumentRepository
			.findAmplifiers(page, pageSize, sort, filterConditions)
			.map(AmplifierDto::from);
	}

	public Page<AudioEquipmentDto> findAudioEquipments(
		int page,
		int pageSize,
		InstrumentSortOption sort,
		AudioEquipmentFilterConditions filterConditions
	) {
		return instrumentRepository
			.findAudioEquipments(page, pageSize, sort, filterConditions)
			.map(AudioEquipmentDto::from);
	}
}
