package com.ajou.hertz.domain.user.entity;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.lang.NonNull;

import com.ajou.hertz.common.entity.TimeTrackedBaseEntity;
import com.ajou.hertz.domain.user.constant.Gender;
import com.ajou.hertz.domain.user.constant.RoleType;
import com.ajou.hertz.domain.user.converter.RoleTypesConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(
	name = "users",
	indexes = {
		@Index(name = "idx__user__email", columnList = "email"),
		@Index(name = "idx__user__kakao_uid", columnList = "kakaoUid"),
		@Index(name = "idx__user__phone", columnList = "phone")
	}
)
@Entity
public class User extends TimeTrackedBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", nullable = false, updatable = false)
	private Long id;

	@Column(nullable = false)
	@Convert(converter = RoleTypesConverter.class)
	private Set<RoleType> roleTypes;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(unique = true)
	private String kakaoUid;

	@Column(nullable = false)
	private String profileImageUrl;

	private LocalDate birth;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Column(unique = true)
	private String phone;

	private String contactLink;

	@NonNull
	public static User create(
		@NonNull String email,
		@NonNull String password,
		@NonNull String profileImageUrl,
		LocalDate birth,
		Gender gender,
		String phone
	) {
		return new User(
			null, Set.of(RoleType.USER), email, password, null,
			profileImageUrl, birth, gender, phone, null
		);
	}

	@NonNull
	public static User create(
		@NonNull String email,
		@NonNull String password,
		@NonNull String kakaoUid,
		@NonNull String profileImageUrl,
		LocalDate birth,
		Gender gender,
		String phone
	) {
		return new User(
			null, Set.of(RoleType.USER), email, password, kakaoUid,
			profileImageUrl, birth, gender, phone, null
		);
	}

	public void changeProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public void changeContactLink(String contactLink) {
		this.contactLink = contactLink;
	}

	public void changePassword(String password) {
		this.password = password;
	}

}
