package com.ajou.hertz.domain.instrument.controller;

import static com.ajou.hertz.common.constant.GlobalConstants.*;

import java.net.URI;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ajou.hertz.common.auth.UserPrincipal;
import com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.dto.AcousticAndClassicGuitarDto;
import com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.dto.request.AcousticAndClassicGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.dto.request.CreateNewAcousticAndClassicGuitarRequest;
import com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.dto.response.AcousticAndClassicGuitarResponse;
import com.ajou.hertz.domain.instrument.amplifier.dto.AmplifierDto;
import com.ajou.hertz.domain.instrument.amplifier.dto.request.AmplifierFilterConditions;
import com.ajou.hertz.domain.instrument.amplifier.dto.request.CreateNewAmplifierRequest;
import com.ajou.hertz.domain.instrument.amplifier.dto.response.AmplifierResponse;
import com.ajou.hertz.domain.instrument.audio_equipment.dto.AudioEquipmentDto;
import com.ajou.hertz.domain.instrument.audio_equipment.dto.request.AudioEquipmentFilterConditions;
import com.ajou.hertz.domain.instrument.audio_equipment.dto.request.CreateNewAudioEquipmentRequest;
import com.ajou.hertz.domain.instrument.audio_equipment.dto.response.AudioEquipmentResponse;
import com.ajou.hertz.domain.instrument.bass_guitar.dto.BassGuitarDto;
import com.ajou.hertz.domain.instrument.bass_guitar.dto.request.BassGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.bass_guitar.dto.request.CreateNewBassGuitarRequest;
import com.ajou.hertz.domain.instrument.bass_guitar.dto.response.BassGuitarResponse;
import com.ajou.hertz.domain.instrument.constant.InstrumentSortOption;
import com.ajou.hertz.domain.instrument.dto.InstrumentDto;
import com.ajou.hertz.domain.instrument.dto.response.InstrumentResponse;
import com.ajou.hertz.domain.instrument.dto.response.InstrumentSummaryResponse;
import com.ajou.hertz.domain.instrument.effector.dto.EffectorDto;
import com.ajou.hertz.domain.instrument.effector.dto.request.CreateNewEffectorRequest;
import com.ajou.hertz.domain.instrument.effector.dto.request.EffectorFilterConditions;
import com.ajou.hertz.domain.instrument.effector.dto.response.EffectorResponse;
import com.ajou.hertz.domain.instrument.electric_guitar.dto.ElectricGuitarDto;
import com.ajou.hertz.domain.instrument.electric_guitar.dto.request.CreateNewElectricGuitarRequest;
import com.ajou.hertz.domain.instrument.electric_guitar.dto.request.ElectricGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.electric_guitar.dto.request.ElectricGuitarUpdateRequest;
import com.ajou.hertz.domain.instrument.electric_guitar.dto.response.ElectricGuitarResponse;
import com.ajou.hertz.domain.instrument.mapper.InstrumentMapper;
import com.ajou.hertz.domain.instrument.service.InstrumentCommandService;
import com.ajou.hertz.domain.instrument.service.InstrumentQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "중고 악기 관련 API")
@RequiredArgsConstructor
@RequestMapping("/api/instruments")
@RestController
public class InstrumentController {

	private final InstrumentCommandService instrumentCommandService;
	private final InstrumentQueryService instrumentQueryService;

	@Operation(
		summary = "악기 매물 상세 조회",
		description = """
			<p>매물의 상세 정보를 조회합니다.
			<p>악기 종류별로 응답 데이터가 다를 수 있습니다.
			<p>정확한 악기 종류별 응답 데이터는 페이지 하단의 <b>Schemas</b> 항목에서 아래 내용들을 참고해주세요.
			<ul>
				<li>일렉 기타 응답 데이터: <code>ElectricGuitarResponse</code></li>
				<li>베이스 기타 응답 데이터: <code>BassGuitarResponse</code></li>
				<li>어쿠스틱&클래식 기타 응답 데이터: <code>AcousticAndClassicGuitarResponse</code></li>
				<li>이펙터 응답 데이터: <code>EffectorResponse</code></li>
				<li>앰프 응답 데이터: <code>AmplifierResponse</code></li>
				<li>음향 장비 응답 데이터: <code>AudioEquipmentResponse</code></li>
			</ul>
			"""
	)
	@GetMapping(value = "/{instrumentId}", headers = API_VERSION_HEADER_NAME + "=" + 1)
	public InstrumentResponse getInstrumentByIdV1(@PathVariable Long instrumentId) {
		InstrumentDto instrumentDto = instrumentQueryService.getInstrumentDtoById(instrumentId);
		return InstrumentMapper.toResponse(instrumentDto);
	}

	@Operation(
		summary = "전체 악기 매물 목록 조회",
		description = "악기 종류와 상관 없이 전체 매물 목록을 조회합니다."
	)
	@GetMapping(headers = API_VERSION_HEADER_NAME + "=" + 1)
	public Page<InstrumentSummaryResponse> findInstrumentsV1(
		@Parameter(
			description = "페이지 번호. 0부터 시작합니다.",
			example = "0"
		) @RequestParam int page,
		@Parameter(
			description = "페이지 크기. 한 페이지에 포함될 데이터의 개수를 의미합니다.",
			example = "10"
		) @RequestParam int size,
		@Parameter(
			description = "정렬 기준"
		) @RequestParam InstrumentSortOption sort
	) {
		return instrumentQueryService
			.findInstrumentDtos(page, size, sort)
			.map(InstrumentMapper::toInstrumentSummaryResponse);
	}

	@Operation(
		summary = "일렉 기타 매물 목록 조회",
		description = "일렉 기타 매물 목록을 조회합니다."
	)
	@GetMapping(value = "/electric-guitars", headers = API_VERSION_HEADER_NAME + "=" + 1)
	public Page<ElectricGuitarResponse> findElectricGuitarsV1(
		@Parameter(
			description = "페이지 번호. 0부터 시작합니다.",
			example = "0"
		) @RequestParam int page,
		@Parameter(
			description = "페이지 크기. 한 페이지에 포함될 데이터의 개수를 의미합니다.",
			example = "10"
		) @RequestParam int size,
		@Parameter(
			description = "정렬 기준"
		) @RequestParam InstrumentSortOption sort,
		@ParameterObject @Valid @ModelAttribute ElectricGuitarFilterConditions filterConditions
	) {
		return instrumentQueryService
			.findElectricGuitarDtos(page, size, sort, filterConditions)
			.map(InstrumentMapper::toElectricGuitarResponse);
	}

	@Operation(
		summary = "베이스 기타 매물 목록 조회",
		description = "베이스 기타 매물 목록을 조회합니다."
	)
	@GetMapping(value = "/bass-guitars", headers = API_VERSION_HEADER_NAME + "=" + 1)
	public Page<BassGuitarResponse> findBassGuitarsV1(
		@Parameter(
			description = "페이지 번호. 0부터 시작합니다.",
			example = "0"
		) @RequestParam int page,
		@Parameter(
			description = "페이지 크기. 한 페이지에 포함될 데이터의 개수를 의미합니다.",
			example = "10"
		) @RequestParam int size,
		@Parameter(
			description = "정렬 기준"
		) @RequestParam InstrumentSortOption sort,
		@ParameterObject @Valid @ModelAttribute BassGuitarFilterConditions filterConditions
	) {
		return instrumentQueryService
			.findBassGuitarDtos(page, size, sort, filterConditions)
			.map(InstrumentMapper::toBassGuitarResponse);
	}

	@Operation(
		summary = "어쿠스틱&클래식 기타 매물 목록 조회",
		description = "어쿠스틱&클래식 기타 매물 목록을 조회합니다."
	)
	@GetMapping(value = "/acoustic-and-classic-guitars", headers = API_VERSION_HEADER_NAME + "=" + 1)
	public Page<AcousticAndClassicGuitarResponse> findAcousticAndClassicGuitarsV1(
		@Parameter(
			description = "페이지 번호. 0부터 시작합니다.",
			example = "0"
		) @RequestParam int page,
		@Parameter(
			description = "페이지 크기. 한 페이지에 포함될 데이터의 개수를 의미합니다.",
			example = "10"
		) @RequestParam int size,
		@Parameter(
			description = "정렬 기준"
		) @RequestParam InstrumentSortOption sort,
		@ParameterObject @Valid @ModelAttribute AcousticAndClassicGuitarFilterConditions filterConditions
	) {
		return instrumentQueryService
			.findAcousticAndClassicGuitarDtos(page, size, sort, filterConditions)
			.map(InstrumentMapper::toAcousticAndClassicGuitarResponse);
	}

	@Operation(
		summary = "이펙터 매물 목록 조회",
		description = "이펙터 매물 목록을 조회합니다."
	)
	@GetMapping(value = "/effectors", headers = API_VERSION_HEADER_NAME + "=" + 1)
	public Page<EffectorResponse> findEffectorsV1(
		@Parameter(
			description = "페이지 번호. 0부터 시작합니다.",
			example = "0"
		) @RequestParam int page,
		@Parameter(
			description = "페이지 크기. 한 페이지에 포함될 데이터의 개수를 의미합니다.",
			example = "10"
		) @RequestParam int size,
		@Parameter(
			description = "정렬 기준"
		) @RequestParam InstrumentSortOption sort,
		@ParameterObject @Valid @ModelAttribute EffectorFilterConditions filterConditions
	) {
		return instrumentQueryService
			.findEffectorDtos(page, size, sort, filterConditions)
			.map(InstrumentMapper::toEffectorResponse);
	}

	@Operation(
		summary = "앰프 매물 목록 조회",
		description = "앰프 매물 목록을 조회합니다."
	)
	@GetMapping(value = "/amplifiers", headers = API_VERSION_HEADER_NAME + "=" + 1)
	public Page<AmplifierResponse> findAmplifiersV1(
		@Parameter(
			description = "페이지 번호. 0부터 시작합니다.",
			example = "0"
		) @RequestParam int page,
		@Parameter(
			description = "페이지 크기. 한 페이지에 포함될 데이터의 개수를 의미합니다.",
			example = "10"
		) @RequestParam int size,
		@Parameter(
			description = "정렬 기준"
		) @RequestParam InstrumentSortOption sort,
		@ParameterObject @Valid @ModelAttribute AmplifierFilterConditions filterConditions
	) {
		return instrumentQueryService
			.findAmplifierDtos(page, size, sort, filterConditions)
			.map(InstrumentMapper::toAmplifierResponse);
	}

	@Operation(
		summary = "음향 장비 매물 목록 조회",
		description = "음향 장비 매물 목록을 조회합니다."
	)
	@GetMapping(value = "/audio-equipments", headers = API_VERSION_HEADER_NAME + "=" + 1)
	public Page<AudioEquipmentResponse> findAudioEquipmentsV1(
		@Parameter(
			description = "페이지 번호. 0부터 시작합니다.",
			example = "0"
		) @RequestParam int page,
		@Parameter(
			description = "페이지 크기. 한 페이지에 포함될 데이터의 개수를 의미합니다.",
			example = "10"
		) @RequestParam int size,
		@Parameter(
			description = "정렬 기준"
		) @RequestParam InstrumentSortOption sort,
		@ParameterObject @Valid @ModelAttribute AudioEquipmentFilterConditions filterConditions
	) {
		return instrumentQueryService
			.findAudioEquipmentDtos(page, size, sort, filterConditions)
			.map(InstrumentMapper::toAudioEquipmentResponse);
	}

	@Operation(
		summary = "일렉기타 매물 등록",
		description = """
			<p>일렉기타 매물을 등록합니다.
			<p>요청 시 <strong>multipart/form-data</strong> content-type으로 요쳥해야 합니다.
			""",
		security = @SecurityRequirement(name = "access-token")
	)
	@PostMapping(
		value = "/electric-guitars",
		headers = API_VERSION_HEADER_NAME + "=" + 1,
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE
	)
	public ResponseEntity<ElectricGuitarResponse> createNewElectricGuitarV1(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@ParameterObject @ModelAttribute @Valid CreateNewElectricGuitarRequest createNewElectricGuitarRequest
	) {
		ElectricGuitarDto electricGuitar = instrumentCommandService.createNewElectricGuitar(
			userPrincipal.getUserId(),
			createNewElectricGuitarRequest
		);
		return ResponseEntity
			.created(URI.create("/instruments/" + electricGuitar.getId()))
			.body(InstrumentMapper.toElectricGuitarResponse(electricGuitar));
	}

	@Operation(
		summary = "베이스 기타 매물 등록",
		description = """
			<p>베이스 기타 매물을 등록합니다.
			<p>요청 시 <strong>multipart/form-data</strong> content-type으로 요쳥해야 합니다.
			""",
		security = @SecurityRequirement(name = "access-token")
	)
	@PostMapping(
		value = "/bass-guitars",
		headers = API_VERSION_HEADER_NAME + "=" + 1,
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE
	)
	public ResponseEntity<BassGuitarResponse> createNewBassGuitarV1(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@ParameterObject @ModelAttribute @Valid CreateNewBassGuitarRequest createNewBassGuitarRequest
	) {
		BassGuitarDto bassGuitar = instrumentCommandService.createNewBassGuitar(
			userPrincipal.getUserId(),
			createNewBassGuitarRequest
		);
		return ResponseEntity
			.created(URI.create("/instruments/" + bassGuitar.getId()))
			.body(InstrumentMapper.toBassGuitarResponse(bassGuitar));
	}

	@Operation(
		summary = "어쿠스틱&클래식 기타 매물 등록",
		description = """
			<p>어쿠스틱&클래식 기타 매물을 등록합니다.
			<p>요청 시 <strong>multipart/form-data</strong> content-type으로 요쳥해야 합니다.
			""",
		security = @SecurityRequirement(name = "access-token")
	)
	@PostMapping(
		value = "/acoustic-and-classic-guitars",
		headers = API_VERSION_HEADER_NAME + "=" + 1,
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE
	)
	public ResponseEntity<AcousticAndClassicGuitarResponse> createNewAcousticAndClassicGuitarV1(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@ParameterObject @ModelAttribute @Valid CreateNewAcousticAndClassicGuitarRequest acousticAndClassicGuitarRequest
	) {
		AcousticAndClassicGuitarDto acousticAndClassicGuitar =
			instrumentCommandService.createNewAcousticAndClassicGuitar(
				userPrincipal.getUserId(),
				acousticAndClassicGuitarRequest
			);
		return ResponseEntity
			.created(URI.create("/instruments/" + acousticAndClassicGuitar.getId()))
			.body(InstrumentMapper.toAcousticAndClassicGuitarResponse(acousticAndClassicGuitar));
	}

	@Operation(
		summary = "이펙터 매물 등록",
		description = """
			<p>이펙터 매물을 등록합니다.
			<p>요청 시 <strong>multipart/form-data</strong> content-type으로 요쳥해야 합니다.
			""",
		security = @SecurityRequirement(name = "access-token")
	)
	@PostMapping(
		value = "/effectors",
		headers = API_VERSION_HEADER_NAME + "=" + 1,
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE
	)
	public ResponseEntity<EffectorResponse> createNewEffectorV1(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@ParameterObject @ModelAttribute @Valid CreateNewEffectorRequest createNewEffectorRequest
	) {
		EffectorDto effector = instrumentCommandService.createNewEffector(
			userPrincipal.getUserId(),
			createNewEffectorRequest
		);
		return ResponseEntity
			.created(URI.create("/instruments/" + effector.getId()))
			.body(InstrumentMapper.toEffectorResponse(effector));
	}

	@Operation(
		summary = "앰프 매물 등록",
		description = """
			<p>앰프 매물을 등록합니다.
			<p>요청 시 <strong>multipart/form-data</strong> content-type으로 요쳥해야 합니다.
			""",
		security = @SecurityRequirement(name = "access-token")
	)
	@PostMapping(
		value = "/amplifiers",
		headers = API_VERSION_HEADER_NAME + "=" + 1,
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE
	)
	public ResponseEntity<AmplifierResponse> createNewAmplifierV1(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@ParameterObject @ModelAttribute @Valid CreateNewAmplifierRequest createNewAmplifierRequest
	) {
		AmplifierDto amplifier = instrumentCommandService.createNewAmplifier(
			userPrincipal.getUserId(),
			createNewAmplifierRequest
		);
		return ResponseEntity
			.created(URI.create("/instruments/" + amplifier.getId()))
			.body(InstrumentMapper.toAmplifierResponse(amplifier));
	}

	@Operation(
		summary = "음향 장비 매물 등록",
		description = """
			<p>음향 장비 매물을 등록합니다.
			<p>요청 시 <strong>multipart/form-data</strong> content-type으로 요쳥해야 합니다.
			""",
		security = @SecurityRequirement(name = "access-token")
	)
	@PostMapping(
		value = "/audio-equipments",
		headers = API_VERSION_HEADER_NAME + "=" + 1,
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE
	)
	public ResponseEntity<AudioEquipmentResponse> createNewAudioEquipmentV1(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@ParameterObject @ModelAttribute @Valid CreateNewAudioEquipmentRequest createNewAudioEquipmentRequest
	) {
		AudioEquipmentDto audioEquipment = instrumentCommandService.createNewAudioEquipment(
			userPrincipal.getUserId(),
			createNewAudioEquipmentRequest
		);
		return ResponseEntity
			.created(URI.create("/instruments/" + audioEquipment.getId()))
			.body(InstrumentMapper.toAudioEquipmentResponse(audioEquipment));
	}

	@Operation(
		summary = "일렉 기타 매물 수정",
		description = """
			<p>일렉 기타 매물 정보를 수정합니다.
			<p>요청 시 <strong>multipart/form-data</strong> content-type으로 요쳥해야 합니다.
			<p>변경하고자 하는 매물 정보만 request body에 담아 요청하면 됩니다. 요청 시 보내지 않은 필드는 수정하지 않습니다.
			""",
		security = @SecurityRequirement(name = "access-token")
	)
	@PatchMapping("/electric-guitars/{electricGuitarId}")
	public ElectricGuitarResponse updateElectricGuitarV1(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@Parameter(description = "수정하고자 하는 악기 매물의 id", example = "2") @PathVariable Long electricGuitarId,
		@ParameterObject @ModelAttribute @Valid ElectricGuitarUpdateRequest updateRequest
	) {
		ElectricGuitarDto electricGuitarDto = instrumentCommandService.updateElectricGuitar(
			userPrincipal.getUserId(),
			electricGuitarId,
			updateRequest
		);
		return InstrumentMapper.toElectricGuitarResponse(electricGuitarDto);
	}

	@Operation(
		summary = "악기 매물 삭제",
		description = "악기 매물을 삭제합니다. 매물 삭제는 판매자만 할 수 있습니다.",
		security = @SecurityRequirement(name = "access-token")
	)
	@ApiResponses({
		@ApiResponse(responseCode = "204"),
		@ApiResponse(responseCode = "403", description = "[2601] 악기를 삭제하려는 유저가 판매자가 아닌 경우", content = @Content)
	})
	@DeleteMapping(value = "/{instrumentId}", headers = API_VERSION_HEADER_NAME + "=" + 1)
	public ResponseEntity<Void> deleteInstrumentV1(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@Parameter(description = "Id of instrument", example = "2") @PathVariable Long instrumentId
	) {
		instrumentCommandService.deleteInstrumentById(userPrincipal.getUserId(), instrumentId);
		return ResponseEntity.noContent().build();
	}
}
