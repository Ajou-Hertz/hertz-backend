package com.ajou.hertz.domain.user.dto.response;

import java.time.LocalDateTime;

import com.ajou.hertz.domain.user.dto.UserDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SellerInfoResponse {

	@Schema(description = "Id of user", example = "1")
	private Long id;

	@Schema(description = "이메일", example = "test@gmail.com")
	private String email;

	@Schema(description = "프로필 이미지 url", example = "https://user-profile-image")
	private String profileImage;

	@Schema(description = "연락 수단", example = "https://contack-link")
	private String contactLink;

	@Schema(description = "가입일", example = "2024.01.01")
	private LocalDateTime createdAt;

	@Schema(description = "판매중인 물품의 수", example = "10")
	private long sellingItemCount;

	@Schema(description = "판매완료한 물품의 수", example = "10")
	private long soldItemCount;

	public static SellerInfoResponse from(UserDto userDto, long sellingItemCount, long soldItemCount) {
		return new SellerInfoResponse(
			userDto.getId(),
			userDto.getEmail(),
			userDto.getProfileImageUrl(),
			userDto.getContactLink(),
			userDto.getCreatedAt(),
			sellingItemCount,
			soldItemCount
		);
	}
}
