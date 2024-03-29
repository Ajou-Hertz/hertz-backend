package com.ajou.hertz.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openApi(@Value("${hertz.app-version}") String appVersion) {
		return new OpenAPI()
			.info(new Info()
				.title("Hertz Server API Docs")
				.description("""
					<h1>API 버전 정책
					<h2>버전 릴리즈 정책
					<p>API에 생기는 변경 사항 중 이전 버전과 호환되지 않는 변경 사항을 배포하면 새로운 버전을 릴리즈합니다.
					이전 버전과 호환되는 변경 사항을 배포할 때는 버전을 새로 릴리즈하지 않습니다.</p>
					<h3>하위 호환을 지원하는 변경 사항
					<ul>
						<li>새로운 API 엔드포인트 추가</li>
						<li>API 요청에 새로운 선택 파라미터 추가</li>
						<li>API 요청에서 사용하는 필수 파라미터를 선택 파라미터로 변경</li>
						<li>API 응답에 새로운 필드 추가</li>
						<li>새로운 ENUM 추가</li>
						<li>에러 메시지 변경</li>
						<li>새로운 에러 코드 추가</li>
					</ul>
					<h3>새로운 버전을 릴리즈 해야 하는 변경 사항
					<ul>
						<li>API 엔드포인트 제거</li>
						<li>API 요청에 새로운 필수 파라미터 추가</li>
						<li>API 요청의 선택 파라미터를 필수 파라미터로 변경</li>
						<li>API 응답에 사용되던 필드 삭제</li>
						<li>API 응답 필드 중 nullable 하지 않았던 필드를 nullable 하게 변경</li>
						<li>API 응답 필드의 데이터 타입 변경</li>
						<li>같은 의미를 나타내는 에러 코드의 변경</li>
						<li>같은 의미를 나타내는 ENUM 코드의 변경 (예: 토스결제 → 토스페이)</li>
					</ul>
					""")
				.version(appVersion))
			.components(new Components().addSecuritySchemes(
				"access-token",
				new SecurityScheme()
					.type(SecurityScheme.Type.HTTP)
					.scheme("Bearer")
					.bearerFormat("JWT")
			));
	}
}

