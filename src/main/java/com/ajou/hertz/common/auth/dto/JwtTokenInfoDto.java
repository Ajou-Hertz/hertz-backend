package com.ajou.hertz.common.auth.dto;

import java.time.LocalDateTime;

public record JwtTokenInfoDto(
	String token,
	LocalDateTime expiresAt
) {
}
