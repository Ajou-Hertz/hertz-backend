package com.ajou.hertz.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("kakao")
public record KakaoProperties(
	String restApiKey,
	String clientSecret
) {
}
