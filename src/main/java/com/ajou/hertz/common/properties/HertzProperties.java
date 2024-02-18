package com.ajou.hertz.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("hertz")
public record HertzProperties(String userDefaultProfileImageUrl) {
}
