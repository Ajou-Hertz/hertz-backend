package com.ajou.hertz.domain.administrative_area.controller;

import static com.ajou.hertz.common.constant.GlobalConstants.*;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ajou.hertz.domain.administrative_area.dto.AdministrativeAreaDto;
import com.ajou.hertz.domain.administrative_area.dto.response.AdministrativeAreaEmdResponse;
import com.ajou.hertz.domain.administrative_area.dto.response.AdministrativeAreaSggResponse;
import com.ajou.hertz.domain.administrative_area.dto.response.AdministrativeAreaSidoResponse;
import com.ajou.hertz.domain.administrative_area.entity.AdministrativeAreaEmd;
import com.ajou.hertz.domain.administrative_area.entity.AdministrativeAreaSgg;
import com.ajou.hertz.domain.administrative_area.entity.AdministrativeAreaSido;
import com.ajou.hertz.domain.administrative_area.repository.AdministrativeEmdRepository;
import com.ajou.hertz.domain.administrative_area.repository.AdministrativeSggRepository;
import com.ajou.hertz.domain.administrative_area.repository.AdministrativeSidoRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "행정구역 관련 API")
@RestController
@RequestMapping("/v1/administrative-areas")
@RequiredArgsConstructor
public class AdministrativeAreaControllerV1 {
	private final AdministrativeSidoRepository administrativeSidoRepository;
	private final AdministrativeSggRepository administrativeSggRepository;
	private final AdministrativeEmdRepository administrativeEmdRepository;

	@Operation(summary = "행정구역 시도 조회", description = "행정구역 시도를 조회합니다.")
	@GetMapping(value = "/sido", headers = API_MINOR_VERSION_HEADER_NAME + "=" + 1)
	public AdministrativeAreaSidoResponse getSidoList() {
		List<AdministrativeAreaSido> sidoEntities = administrativeSidoRepository.findAll();
		List<AdministrativeAreaDto> dtos = sidoEntities.stream()
			.map(AdministrativeAreaDto::from)
			.toList();
		return new AdministrativeAreaSidoResponse(dtos);
	}

	@Operation(summary = "행정구역 시군구 조회", description = "행정구역 시군구를 조회합니다.")
	@GetMapping(value = "/sgg", headers = API_MINOR_VERSION_HEADER_NAME + "=" + 1)
	public AdministrativeAreaSggResponse getSggList(
		@Parameter(description = "시도 id를 입력하면 됩니다", example = "1") @RequestParam Long sidoId) {
		List<AdministrativeAreaSgg> sggEntities = administrativeSggRepository.findBySido_Id(sidoId);
		List<AdministrativeAreaDto> dtos = sggEntities.stream()
			.map(AdministrativeAreaDto::from)
			.toList();
		return new AdministrativeAreaSggResponse(dtos);
	}

	@Operation(summary = "행정구역 읍면동 조회", description = "행정구역 읍면동을 조회합니다.")
	@GetMapping(value = "/emd", headers = API_MINOR_VERSION_HEADER_NAME + "=" + 1)
	public AdministrativeAreaEmdResponse getEmdList(
		@Parameter(description = "시군구 id를 입력하면 됩니다", example = "1") @RequestParam Long sggId) {
		List<AdministrativeAreaEmd> emdEntities = administrativeEmdRepository.findBySgg_Id(sggId);
		List<AdministrativeAreaDto> dtos = emdEntities.stream()
			.map(AdministrativeAreaDto::from)
			.toList();
		return new AdministrativeAreaEmdResponse(dtos);
	}
}