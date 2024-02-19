package com.ajou.hertz.common.config.feign;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.http.HttpStatus;

import com.ajou.hertz.common.exception.IOProcessingException;
import com.ajou.hertz.common.kakao.dto.response.KakaoErrorResponse;
import com.ajou.hertz.common.kakao.exception.KakaoClientException;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response;
import feign.codec.ErrorDecoder;

public class KakaoFeignErrorDecoder implements ErrorDecoder {

	private final ObjectMapper mapper;

	public KakaoFeignErrorDecoder() {
		this.mapper = new ObjectMapper();
	}

	@Override
	public Exception decode(String methodKey, Response response) {
		try (InputStream responseBody = response.body().asInputStream()) {
			return new KakaoClientException(
				HttpStatus.valueOf(response.status()),
				mapper.readValue(responseBody, KakaoErrorResponse.class)
			);
		} catch (IOException e) {
			return new IOProcessingException(e);
		}
	}
}
