package com.ajou.hertz.domain.administrative_area.entity;

import com.ajou.hertz.common.entity.TimeTrackedBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class AdministrativeAreaSgg extends TimeTrackedBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "administrative_area_sgg_id", nullable = false, updatable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "administrative_area_sido_id")
	private AdministrativeAreaSido sido;

	@Column(nullable = false)
	private String name;

	public AdministrativeAreaSgg(Long id, AdministrativeAreaSido sido, String name) {
		this.id = id;
		this.sido = sido;
		this.name = name;
	}
}
