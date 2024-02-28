package com.ajou.hertz.domain.administrative_area.controller;

import static com.ajou.hertz.common.constant.GlobalConstants.*;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ajou.hertz.domain.administrative_area.dto.AdministrativeAreaDto;
import com.ajou.hertz.domain.administrative_area.dto.response.AdministrativeAreaListResponse;
import com.ajou.hertz.domain.administrative_area.dto.response.AdministrativeAreaResponse;
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
	public AdministrativeAreaListResponse getSidoListV1_1() {
		List<AdministrativeAreaResponse> sidoList = administrativeSidoRepository
			.findAll()
			.stream()
			.map(AdministrativeAreaDto::from)
			.map(AdministrativeAreaResponse::from)
			.toList();
		return new AdministrativeAreaListResponse(sidoList);
	}

	@Operation(summary = "행정구역 시군구 조회", description = "행정구역 시군구를 조회합니다.")
	@GetMapping(value = "/sgg", headers = API_MINOR_VERSION_HEADER_NAME + "=" + 1)
	public AdministrativeAreaListResponse getSggListV1_1(
		@Parameter(description = "시도 id를 입력하면 됩니다", example = "1") @RequestParam Long sidoId
	) {
		List<AdministrativeAreaResponse> sggList = administrativeSggRepository
			.findAllBySido_Id(sidoId)
			.stream()
			.map(AdministrativeAreaDto::from)
			.map(AdministrativeAreaResponse::from)
			.toList();
		return new AdministrativeAreaListResponse(sggList);
	}

	@Operation(summary = "행정구역 읍면동 조회", description = "행정구역 읍면동을 조회합니다.")
	@GetMapping(value = "/emd", headers = API_MINOR_VERSION_HEADER_NAME + "=" + 1)
	public AdministrativeAreaListResponse getEmdListV1_1(
		@Parameter(description = "시군구 id를 입력하면 됩니다", example = "1") @RequestParam Long sggId
	) {
		List<AdministrativeAreaResponse> emdList = administrativeEmdRepository
			.findAllBySgg_Id(sggId)
			.stream()
			.map(AdministrativeAreaDto::from)
			.map(AdministrativeAreaResponse::from)
			.toList();
		return new AdministrativeAreaListResponse(emdList);
	}
}