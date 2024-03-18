package com.ajou.hertz.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UpdateProfileImageUrlRequest {

	@Schema(description = "프로필 이미지", example = "https://user-default-profile-image")
	@NotBlank
	private String profileImageUrl;

}
