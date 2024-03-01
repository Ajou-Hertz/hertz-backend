package com.ajou.hertz.domain.instrument.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ajou.hertz.domain.instrument.dto.InstrumentDto;
import com.ajou.hertz.domain.instrument.repository.InstrumentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class InstrumentQueryService {

	private final InstrumentRepository instrumentRepository;

	public Page<InstrumentDto> findInstruments(Pageable pageable) {
		return instrumentRepository
			.findAll(pageable)
			.map(InstrumentDto::from);
	}
}
