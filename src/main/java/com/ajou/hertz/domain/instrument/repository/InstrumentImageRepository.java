package com.ajou.hertz.domain.instrument.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ajou.hertz.domain.instrument.entity.InstrumentImage;

public interface InstrumentImageRepository extends JpaRepository<InstrumentImage, Long> {
}
