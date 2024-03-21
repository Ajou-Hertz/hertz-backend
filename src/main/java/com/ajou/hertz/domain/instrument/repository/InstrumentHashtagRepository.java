package com.ajou.hertz.domain.instrument.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ajou.hertz.domain.instrument.entity.Instrument;
import com.ajou.hertz.domain.instrument.entity.InstrumentHashtag;

public interface InstrumentHashtagRepository extends JpaRepository<InstrumentHashtag, Long> {

	void deleteAllByInstrument(Instrument instrument);
}
