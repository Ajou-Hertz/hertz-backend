package com.ajou.hertz.common.config.feign;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;

import feign.Logger;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.form.spring.SpringFormEncoder;

public class KakaoFeignConfig {

	@Bean
	public Encoder encoder(ObjectFactory<HttpMessageConverters> converters) {
		return new SpringFormEncoder(new SpringEncoder(converters));
	}

	@Bean
	public ErrorDecoder errorDecoder() {
		return new KakaoFeignErrorDecoder();
	}
}
