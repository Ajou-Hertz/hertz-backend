package com.ajou.hertz.domain.user.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.ajou.hertz.domain.user.constant.Gender;
import com.ajou.hertz.domain.user.constant.RoleType;
import com.ajou.hertz.domain.user.entity.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserDto {

	private Long id;
	private Set<RoleType> roleTypes;
	private String email;
	private String password;
	private String kakaoUid;
	private String profileImageUrl;
	private LocalDate birth;
	private Gender gender;
	private String phone;
	private String contactLink;
	private LocalDateTime createdAt;

	public static UserDto from(User user) {
		return new UserDto(
			user.getId(),
			user.getRoleTypes(),
			user.getEmail(),
			user.getPassword(),
			user.getKakaoUid(),
			user.getProfileImageUrl(),
			user.getBirth(),
			user.getGender(),
			user.getPhone(),
			user.getContactLink(),
			user.getCreatedAt()
		);
	}
}
