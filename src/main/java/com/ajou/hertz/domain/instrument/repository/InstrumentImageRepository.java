package com.ajou.hertz.domain.instrument.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ajou.hertz.domain.instrument.entity.InstrumentImage;

public interface InstrumentImageRepository extends JpaRepository<InstrumentImage, Long> {

	List<InstrumentImage> findAllByInstrument_Id(Long instrumentId);
}
