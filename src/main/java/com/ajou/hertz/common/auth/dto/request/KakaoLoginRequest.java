package com.ajou.hertz.common.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class KakaoLoginRequest {

	@Schema(description = "카카오 '인가 코드 받기'를 통해 얻은 인가 코드", example = "v9rZPkB2nyKTFX26E8ByNzUojqi6w9bQRyfBQCFDzRFhxcoUUSVpI_3HgPIKlwzSAAABjbztS6bkNSpXBP-m7E")
	@NotBlank
	private String authorizationCode;

	@Schema(description = "인가 코드가 리다이렉트된 uri", example = "https://hertz.com/kakao-login-redirection")
	@NotBlank
	private String redirectUri;
}
