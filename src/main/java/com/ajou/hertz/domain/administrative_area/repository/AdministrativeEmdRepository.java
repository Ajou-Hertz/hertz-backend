package com.ajou.hertz.domain.administrative_area.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ajou.hertz.domain.administrative_area.entity.AdministrativeAreaEmd;

public interface AdministrativeEmdRepository extends JpaRepository<AdministrativeAreaEmd, Long> {
	List<AdministrativeAreaEmd> findBySgg_Id(Long sggId);
}
