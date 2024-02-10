package com.ajou.hertz.domain.user.dto;

import java.time.LocalDate;

import com.ajou.hertz.domain.user.constant.Gender;
import com.ajou.hertz.domain.user.entity.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserDto {

	private Long id;
	private String email;
	private String password;
	private String kakaoUid;
	private LocalDate birth;
	private Gender gender;
	private String phone;
	private String contactLink;

	public static UserDto from(User user) {
		return new UserDto(
			user.getId(),
			user.getEmail(),
			user.getPassword(),
			user.getKakaoUid(),
			user.getBirth(),
			user.getGender(),
			user.getPhone(),
			user.getContactLink()
		);
	}
}
