package com.ajou.hertz.domain.administrative_area.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ajou.hertz.domain.administrative_area.entity.AdministrativeAreaSgg;

public interface AdministrativeSggRepository extends JpaRepository<AdministrativeAreaSgg, Long> {
	List<AdministrativeAreaSgg> findAllBySido_Id(Long sidoId);
}
