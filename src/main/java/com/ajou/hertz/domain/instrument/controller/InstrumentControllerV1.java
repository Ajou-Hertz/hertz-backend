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
import com.ajou.hertz.domain.instrument.dto.ElectricGuitarDto;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewElectricGuitarRequest;
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
	public ResponseEntity<ElectricGuitarResponse> createNewInstrumentV1_1(
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
}