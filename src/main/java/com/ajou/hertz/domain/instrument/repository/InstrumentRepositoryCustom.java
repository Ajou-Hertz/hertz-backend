package com.ajou.hertz.domain.instrument.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ajou.hertz.domain.instrument.entity.ElectricGuitar;

public interface InstrumentRepositoryCustom {

	Page<ElectricGuitar> findElectricGuitars(Pageable pageable);
}
