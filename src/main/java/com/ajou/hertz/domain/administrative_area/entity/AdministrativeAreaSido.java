package com.ajou.hertz.domain.administrative_area.entity;

import com.ajou.hertz.common.entity.TimeTrackedBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "administrative_area_sido")
public class AdministrativeAreaSido extends TimeTrackedBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "administrative_area_sido_id", nullable = false, updatable = false)
	private Long id;

	@Column(nullable = false)
	private String name;

	public AdministrativeAreaSido(Long id, String name) {
		this.id = id;
		this.name = name;
	}

}

