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
import com.ajou.hertz.domain.instrument.electric_guitar.dto.ElectricGuitarDto;
import com.ajou.hertz.domain.instrument.dto.InstrumentDto;
import com.ajou.hertz.domain.instrument.dto.request.AcousticAndClassicGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.AmplifierFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.AudioEquipmentFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.BassGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.EffectorFilterConditions;
import com.ajou.hertz.domain.instrument.electric_guitar.dto.request.ElectricGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.entity.Instrument;
import com.ajou.hertz.domain.instrument.exception.InstrumentNotFoundByIdException;
import com.ajou.hertz.domain.instrument.mapper.InstrumentMapper;
import com.ajou.hertz.domain.instrument.repository.InstrumentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class InstrumentQueryService {

	private final InstrumentRepository instrumentRepository;

	/**
	 * Id로 악기를 조회한다.
	 *
	 * @param id 조회하고자 하는 Instrument entity의 id
	 * @return 조회된 Instrument entity
	 */
	public Instrument getInstrumentById(Long id) {
		return instrumentRepository.findById(id)
			.orElseThrow(() -> new InstrumentNotFoundByIdException(id));
	}

	/**
	 * Id로 악기를 조회한다.
	 *
	 * @param id 조회하고자 하는 악기의 id
	 * @return 조회된 Instrument dto
	 */
	public InstrumentDto getInstrumentDtoById(Long id) {
		Instrument instrument = getInstrumentById(id);
		return InstrumentMapper.toDto(instrument);
	}

	/**
	 * 악기 목록을 조회한다.
	 *
	 * @param page     페이지 번호
	 * @param pageSize 페이지 크기
	 * @param sort     정렬 기준
	 * @return 조회된 악기 dto 목록
	 */
	public Page<InstrumentDto> findInstrumentDtos(int page, int pageSize, InstrumentSortOption sort) {
		return instrumentRepository
			.findAll(PageRequest.of(page, pageSize, sort.toSort()))
			.map(InstrumentMapper::toDto);
	}

	/**
	 * 일렉 기타 목록을 조회한다.
	 *
	 * @param page     페이지 번호
	 * @param pageSize 페이지 크기
	 * @param sort     정렬 기준
	 * @return 조회된 일렉 기타 dto 목록
	 */
	public Page<ElectricGuitarDto> findElectricGuitarDtos(
		int page,
		int pageSize,
		InstrumentSortOption sort,
		ElectricGuitarFilterConditions filterConditions
	) {
		return instrumentRepository
			.findElectricGuitars(page, pageSize, sort, filterConditions)
			.map(InstrumentMapper::toElectricGuitarDto);
	}

	/**
	 * 베이스 기타 목록을 조회한다.
	 *
	 * @param page     페이지 번호
	 * @param pageSize 페이지 크기
	 * @param sort     정렬 기준
	 * @return 조회된 베이스 기타 dto 목록
	 */
	public Page<BassGuitarDto> findBassGuitarDtos(
		int page,
		int pageSize,
		InstrumentSortOption sort,
		BassGuitarFilterConditions filterConditions
	) {
		return instrumentRepository
			.findBassGuitars(page, pageSize, sort, filterConditions)
			.map(InstrumentMapper::toBassGuitarDto);
	}

	/**
	 * 어쿠스틱&클래식 기타 목록을 조회한다.
	 *
	 * @param page     페이지 번호
	 * @param pageSize 페이지 크기
	 * @param sort     정렬 기준
	 * @return 조회된 어쿠스틱&클래식 기타 dto 목록
	 */
	public Page<AcousticAndClassicGuitarDto> findAcousticAndClassicGuitarDtos(
		int page,
		int pageSize,
		InstrumentSortOption sort,
		AcousticAndClassicGuitarFilterConditions filterConditions
	) {
		return instrumentRepository
			.findAcousticAndClassicGuitars(page, pageSize, sort, filterConditions)
			.map(InstrumentMapper::toAcousticAndClassicGuitarDto);
	}

	/**
	 * 이펙터 목록을 조회한다.
	 *
	 * @param page     페이지 번호
	 * @param pageSize 페이지 크기
	 * @param sort     정렬 기준
	 * @return 조회된 이펙터 dto 목록
	 */
	public Page<EffectorDto> findEffectorDtos(
		int page,
		int pageSize,
		InstrumentSortOption sort,
		EffectorFilterConditions filterConditions
	) {
		return instrumentRepository
			.findEffectors(page, pageSize, sort, filterConditions)
			.map(InstrumentMapper::toEffectorDto);
	}

	/**
	 * 앰프 목록을 조회한다.
	 *
	 * @param page     페이지 번호
	 * @param pageSize 페이지 크기
	 * @param sort     정렬 기준
	 * @return 조회된 앰프 dto 목록
	 */
	public Page<AmplifierDto> findAmplifierDtos(
		int page,
		int pageSize,
		InstrumentSortOption sort,
		AmplifierFilterConditions filterConditions
	) {
		return instrumentRepository
			.findAmplifiers(page, pageSize, sort, filterConditions)
			.map(InstrumentMapper::toAmplifierDto);
	}

	/**
	 * 음향 장비 목록을 조회한다.
	 *
	 * @param page     페이지 번호
	 * @param pageSize 페이지 크기
	 * @param sort     정렬 기준
	 * @return 조회된 음향 장비 dto 목록
	 */
	public Page<AudioEquipmentDto> findAudioEquipmentDtos(
		int page,
		int pageSize,
		InstrumentSortOption sort,
		AudioEquipmentFilterConditions filterConditions
	) {
		return instrumentRepository
			.findAudioEquipments(page, pageSize, sort, filterConditions)
			.map(InstrumentMapper::toAmplifierDto);
	}
}
