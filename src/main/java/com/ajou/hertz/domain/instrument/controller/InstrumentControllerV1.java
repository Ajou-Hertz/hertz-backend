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
import com.ajou.hertz.domain.instrument.dto.BassGuitarDto;
import com.ajou.hertz.domain.instrument.dto.ElectricGuitarDto;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewAcousticAndClassicGuitarRequest;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewBassGuitarRequest;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewElectricGuitarRequest;
import com.ajou.hertz.domain.instrument.dto.response.AcousticAndClassicGuitarResponse;
import com.ajou.hertz.domain.instrument.dto.response.BassGuitarResponse;
import com.ajou.hertz.domain.instrument.dto.response.ElectricGuitarResponse;
import com.ajou.hertz.domain.instrument.service.InstrumentCommandService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "중고 악기 관련 API")
@RequiredArgsConstructor
@RequestMapping("/v1/instruments")
@RestController
public class InstrumentControllerV1 {

	private final InstrumentCommandService instrumentCommandService;

	@Operation(
		summary = "일렉기타 매물 등록",
		description = """
			<p>일렉기타 매물을 등록합니다.
			<p>요청 시 <strong>multipart/form-data</strong> content-type으로 요쳥해야 합니다.
			"""
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
			"""
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
			"""
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
		AcousticAndClassicGuitarDto acousticAndClassicGuitar = instrumentCommandService.createNewAcousticAndClassicGuitar(
			userPrincipal.getUserId(),
			acousticAndClassicGuitarRequest
		);
		return ResponseEntity
			.created(URI.create("/instruments/" + acousticAndClassicGuitar.getId()))
			.body(AcousticAndClassicGuitarResponse.from(acousticAndClassicGuitar));
	}
}
