package com.ajou.hertz.common.kakao.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class KakaoTokenResponse {

	@JsonProperty("token_type")
	private String tokenType;

	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("expires_in")
	private Integer expiresIn;

	@JsonProperty("refresh_token")
	private String refreshToken;

	@JsonProperty("refresh_token_expires_in")
	private Integer refreshTokenExpiresIn;
}
