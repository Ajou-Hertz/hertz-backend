package com.ajou.hertz.domain.instrument.entity;

import com.ajou.hertz.common.entity.TimeTrackedBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "instrument_brand")
public class InstrumentBrand extends TimeTrackedBaseEntity {

	@Id
	@Column(nullable = false, updatable = false)
	private Long id;

	@Column(nullable = false)
	private String name;

}
