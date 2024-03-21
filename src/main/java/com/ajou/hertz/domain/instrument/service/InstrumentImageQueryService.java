package com.ajou.hertz.domain.instrument.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ajou.hertz.domain.instrument.entity.InstrumentImage;
import com.ajou.hertz.domain.instrument.repository.InstrumentImageRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class InstrumentImageQueryService {

	private final InstrumentImageRepository instrumentImageRepository;

	/**
	 * 악기 id를 전달받고, 해당하는 악기의 이미지를 전부 조회한다.
	 *
	 * @param instrumentId 이미지를 조회하고자 하는 악기의 id
	 * @return 조회된 Instrument image entity list
	 */
	public List<InstrumentImage> findAllByInstrumentId(Long instrumentId) {
		return instrumentImageRepository.findAllByInstrument_Id(instrumentId);
	}
}
