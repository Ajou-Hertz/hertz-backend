package com.ajou.hertz.common.auth.dto.response;

import java.time.LocalDateTime;

import com.ajou.hertz.common.auth.dto.JwtTokenInfoDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class JwtTokenInfoResponse {

	@Schema(description = "Token value", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZSI6IlJPTEVfVVNFUiIsImxvZ2luVHlwZSI6IktBS0FPIiwiaWF0IjoxNjc3NDg0NzExLCJleHAiOjE2Nzc1Mjc5MTF9.eM2R_mMRqkPUsMmJN_vm2lAsIGownPJZ6Xu47K6ujrI")
	private String token;

	@Schema(description = "Token 만료 시각", example = "2023-02-28T17:13:55.473")
	private LocalDateTime expiresAt;

	public static JwtTokenInfoResponse from(JwtTokenInfoDto jwtTokenInfoDto) {
		return new JwtTokenInfoResponse(
			jwtTokenInfoDto.token(),
			jwtTokenInfoDto.expiresAt()
		);
	}
}