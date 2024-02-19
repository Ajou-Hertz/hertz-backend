package com.ajou.hertz.common.kakao.dto.response;

import java.time.LocalDate;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoUserInfoResponse(
	String id,
	@JsonProperty("kakao_account") KakaoAccount kakaoAccount
) {

	public record KakaoAccount(
		@JsonProperty("profile_image_needs_agreement") Boolean profileImageNeedsAgreement,
		Profile profile,

		@JsonProperty("has_email") Boolean hasEmail,
		@JsonProperty("email_needs_agreement") Boolean emailNeedsAgreement,
		@JsonProperty("is_email_valid") Boolean isEmailValid,
		@JsonProperty("is_email_verified") Boolean isEmailVerified,
		String email,

		@JsonProperty("has_phone_number") Boolean hasPhoneNumber,
		@JsonProperty("phone_number_needs_agreement") Boolean phoneNumberNeedsAgreement,
		@JsonProperty("phone_number") String phoneNumber,

		@JsonProperty("has_birthyear") Boolean hasBirthyear,
		@JsonProperty("birthyear_needs_agreement") Boolean birthyearNeedsAgreement,
		String birthyear,

		@JsonProperty("has_birthday") Boolean hasBirthday,
		String birthday,

		@JsonProperty("has_gender") Boolean hasGender,
		@JsonProperty("gender_needs_agreement") Boolean genderNeedsAgreement,
		String gender
	) {

		public record Profile(
			@JsonProperty("profile_image_url") String profileImageUrl,
			@JsonProperty("thumbnail_image_url") String thumbnailImageUrl,
			@JsonProperty("is_default_image") Boolean isDefaultImage
		) {
		}
	}

	public String profileImageUrl() {
		return kakaoAccount.profile.profileImageUrl();
	}

	public String email() {
		return kakaoAccount.email();
	}

	public String gender() {
		return kakaoAccount.gender();
	}

	@Nullable
	public LocalDate birth() {
		String birthyear = kakaoAccount.birthyear();
		String birthday = kakaoAccount.birthday();
		if (!StringUtils.hasText(birthyear) || !StringUtils.hasText(birthday)) {
			return null;
		}
		return LocalDate.of(
			Integer.parseInt(birthyear),
			Integer.parseInt(birthday.substring(0, 2)),
			Integer.parseInt(birthday.substring(2, 4))
		);
	}

	/**
	 * <p>[한국 전화번호 형식 기준]
	 * <p>"+82 10-1234-5678" 형태의 전화번호를 "01012345678" 형태로 변환하여 반환한다.
	 *
	 * @return 변환된 전화번호
	 */
	public String getKoreanFormatPhoneNumber() {
		return kakaoAccount.phoneNumber()
			.replace("+82 ", "0")
			.replace("-", "")
			.trim();
	}
}

