package com.ajou.hertz.domain.user.dto.response;

import java.time.LocalDate;

import com.ajou.hertz.domain.user.constant.Gender;
import com.ajou.hertz.domain.user.dto.UserDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserResponse {

	private Long id;
	private String email;
	private String profileImageUrl;
	private LocalDate birth;
	private Gender gender;
	private String contactLink;

	public static UserResponse from(UserDto userDto) {
		return new UserResponse(
			userDto.getId(),
			userDto.getEmail(),
			userDto.getProfileImageUrl(),
			userDto.getBirth(),
			userDto.getGender(),
			userDto.getContactLink()
		);
	}
}
