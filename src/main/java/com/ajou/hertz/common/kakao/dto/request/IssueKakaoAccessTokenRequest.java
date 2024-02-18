package com.ajou.hertz.common.kakao.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class IssueKakaoAccessTokenRequest {

	private String grant_type;
	private String client_id;
	private String redirect_uri;
	private String code;
	private String client_secret;
}
