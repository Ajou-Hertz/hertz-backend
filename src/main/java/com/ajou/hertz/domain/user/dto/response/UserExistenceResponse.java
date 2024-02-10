package com.ajou.hertz.domain.user.dto.response;

import org.springframework.lang.NonNull;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserExistenceResponse {

	private Boolean isExist;

	public UserExistenceResponse(@NonNull Boolean isExist) {
		this.isExist = isExist;
	}
}
