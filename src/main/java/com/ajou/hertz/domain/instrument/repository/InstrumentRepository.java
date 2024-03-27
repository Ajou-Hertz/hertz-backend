package com.ajou.hertz.domain.instrument.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.entity.Instrument;

public interface InstrumentRepository extends JpaRepository<Instrument, Long>, InstrumentRepositoryCustom {

	int countBySellerIdAndProgressStatus(Long sellerid, InstrumentProgressStatus progressStatus);

}
