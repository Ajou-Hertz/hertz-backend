package com.ajou.hertz.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserContactLinkResponse {

	@Schema(description = "연락 수단", example = "https://new-contactlink.com")
	private String contactLink;

}
