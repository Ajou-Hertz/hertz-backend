package com.ajou.hertz.domain.instrument.controller;

import static com.ajou.hertz.common.constant.GlobalConstants.*;

import java.net.URI;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajou.hertz.common.auth.UserPrincipal;
import com.ajou.hertz.domain.instrument.dto.AcousticAndClassicGuitarDto;
import com.ajou.hertz.domain.instrument.dto.AmplifierDto;
import com.ajou.hertz.domain.instrument.dto.AudioEquipmentDto;
import com.ajou.hertz.domain.instrument.dto.BassGuitarDto;
import com.ajou.hertz.domain.instrument.dto.EffectorDto;
import com.ajou.hertz.domain.instrument.dto.ElectricGuitarDto;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewAcousticAndClassicGuitarRequest;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewAmplifierRequest;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewAudioEquipmentRequest;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewBassGuitarRequest;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewEffectorRequest;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewElectricGuitarRequest;
import com.ajou.hertz.domain.instrument.dto.response.AcousticAndClassicGuitarResponse;
import com.ajou.hertz.domain.instrument.dto.response.AmplifierResponse;
import com.ajou.hertz.domain.instrument.dto.response.AudioEquipmentResponse;
import com.ajou.hertz.domain.instrument.dto.response.BassGuitarResponse;
import com.ajou.hertz.domain.instrument.dto.response.EffectorResponse;
import com.ajou.hertz.domain.instrument.dto.response.ElectricGuitarResponse;
import com.ajou.hertz.domain.instrument.service.InstrumentCommandService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "중고 악기 관련 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/instruments")
@RestController
public class InstrumentControllerV1 {

	private final InstrumentCommandService instrumentCommandService;

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
		headers = API_MINOR_VERSION_HEADER_NAME + "=" + 1,
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE
	)
	public ResponseEntity<ElectricGuitarResponse> createNewElectricGuitarV1_1(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@ParameterObject @ModelAttribute @Valid CreateNewElectricGuitarRequest createNewElectricGuitarRequest
	) {
		ElectricGuitarDto electricGuitar = instrumentCommandService.createNewElectricGuitar(
			userPrincipal.getUserId(),
			createNewElectricGuitarRequest
		);
		return ResponseEntity
			.created(URI.create("/instruments/" + electricGuitar.getId()))
			.body(ElectricGuitarResponse.from(electricGuitar));
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
		headers = API_MINOR_VERSION_HEADER_NAME + "=" + 1,
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE
	)
	public ResponseEntity<BassGuitarResponse> createNewBassGuitarV1_1(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@ParameterObject @ModelAttribute @Valid CreateNewBassGuitarRequest createNewBassGuitarRequest
	) {
		BassGuitarDto bassGuitar = instrumentCommandService.createNewBassGuitar(
			userPrincipal.getUserId(),
			createNewBassGuitarRequest
		);
		return ResponseEntity
			.created(URI.create("/instruments/" + bassGuitar.getId()))
			.body(BassGuitarResponse.from(bassGuitar));
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
		headers = API_MINOR_VERSION_HEADER_NAME + "=" + 1,
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE
	)
	public ResponseEntity<AcousticAndClassicGuitarResponse> createNewAcousticAndClassicGuitarV1_1(
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
			.body(AcousticAndClassicGuitarResponse.from(acousticAndClassicGuitar));
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
		headers = API_MINOR_VERSION_HEADER_NAME + "=" + 1,
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE
	)
	public ResponseEntity<EffectorResponse> createNewEffectorV1_1(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@ParameterObject @ModelAttribute @Valid CreateNewEffectorRequest createNewEffectorRequest
	) {
		EffectorDto effector = instrumentCommandService.createNewEffector(
			userPrincipal.getUserId(),
			createNewEffectorRequest
		);
		return ResponseEntity
			.created(URI.create("/instruments/" + effector.getId()))
			.body(EffectorResponse.from(effector));
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
		headers = API_MINOR_VERSION_HEADER_NAME + "=" + 1,
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE
	)
	public ResponseEntity<AmplifierResponse> createNewAmplifierV1_1(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@ParameterObject @ModelAttribute @Valid CreateNewAmplifierRequest createNewAmplifierRequest
	) {
		AmplifierDto amplifier = instrumentCommandService.createNewAmplifier(
			userPrincipal.getUserId(),
			createNewAmplifierRequest
		);
		return ResponseEntity
			.created(URI.create("/instruments/" + amplifier.getId()))
			.body(AmplifierResponse.from(amplifier));
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
		headers = API_MINOR_VERSION_HEADER_NAME + "=" + 1,
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE
	)
	public ResponseEntity<AudioEquipmentResponse> createNewAudioEquipmentV1_1(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@ParameterObject @ModelAttribute @Valid CreateNewAudioEquipmentRequest createNewAudioEquipmentRequest
	) {
		AudioEquipmentDto audioEquipment = instrumentCommandService.createNewAudioEquipment(
			userPrincipal.getUserId(),
			createNewAudioEquipmentRequest
		);
		return ResponseEntity
			.created(URI.create("/instruments/" + audioEquipment.getId()))
			.body(AudioEquipmentResponse.from(audioEquipment));
	}
}
