package com.ajou.hertz.domain.user.entity;

import java.time.LocalDate;

import org.springframework.lang.NonNull;

import com.ajou.hertz.domain.user.constant.Gender;
import com.ajou.hertz.global.common.entity.TimeTrackedBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users")
@Entity
public class User extends TimeTrackedBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", nullable = false, updatable = false)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(unique = true)
	private String kakaoUid;

	private LocalDate birth;

	@Column(nullable = false)
	private Gender gender;

	@Column(unique = true)
	private String phone;

	private String contactLink;

	@NonNull
	public static User create(
		@NonNull String email,
		@NonNull String password,
		LocalDate birth,
		@NonNull Gender gender
	) {
		return new User(null, email, password, null, birth, gender, null, null);
	}
}
