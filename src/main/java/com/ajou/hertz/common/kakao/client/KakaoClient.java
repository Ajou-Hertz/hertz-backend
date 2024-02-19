package com.ajou.hertz.common.kakao.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.ajou.hertz.common.config.feign.KakaoFeignConfig;
import com.ajou.hertz.common.kakao.dto.response.KakaoUserInfoResponse;

@FeignClient(
	name = "kakaoClient",
	url = "https://kapi.kakao.com",
	configuration = KakaoFeignConfig.class
)
public interface KakaoClient {

	@GetMapping(
		value = "/v2/user/me",
		headers = "Content-type=application/x-www-form-urlencoded;charset=utf-8"
	)
	KakaoUserInfoResponse getUserInfo(
		@RequestHeader("Authorization") String authorizationHeader,
		@RequestParam("secure_resource") Boolean secureResource
	);
}
