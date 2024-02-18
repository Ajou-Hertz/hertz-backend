package com.ajou.hertz.common.kakao.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ajou.hertz.common.config.feign.KakaoFeignConfig;
import com.ajou.hertz.common.kakao.dto.request.IssueKakaoAccessTokenRequest;
import com.ajou.hertz.common.kakao.dto.response.KakaoTokenResponse;

@FeignClient(
	name = "kakaoAuthClient",
	url = "https://kauth.kakao.com",
	configuration = KakaoFeignConfig.class
)
public interface KakaoAuthClient {

	@PostMapping(
		value = "/oauth/token",
		headers = "Content-type=application/x-www-form-urlencoded;charset=utf-8"
	)
	KakaoTokenResponse issueAccessToken(@RequestBody IssueKakaoAccessTokenRequest issueTokenRequest);
}
