package com.ajou.hertz.common.config;

import static org.springframework.http.HttpMethod.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ajou.hertz.common.auth.JwtAccessDeniedHandler;
import com.ajou.hertz.common.auth.JwtAuthenticationEntryPoint;
import com.ajou.hertz.common.auth.JwtAuthenticationFilter;
import com.ajou.hertz.common.auth.JwtExceptionFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final JwtExceptionFilter jwtExceptionFilter;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	private static final String[] AUTH_WHITE_PATHS = {
		"/swagger-ui/**",
		"/v3/api-docs/**"
	};

	private static final Map<String, HttpMethod> AUTH_WHITE_LIST = new HashMap<>();

	static {
		AUTH_WHITE_LIST.put("/api/auth/login", POST);
		AUTH_WHITE_LIST.put("/api/auth/kakao/login", POST);
		AUTH_WHITE_LIST.put("/api/users", POST);
		AUTH_WHITE_LIST.put("/api/users/existence", GET);
		AUTH_WHITE_LIST.put("/api/users/email", GET);
		AUTH_WHITE_LIST.put("/api/administrative-areas/sido", GET);
		AUTH_WHITE_LIST.put("/api/administrative-areas/sgg", GET);
		AUTH_WHITE_LIST.put("/api/administrative-areas/emd", GET);
		AUTH_WHITE_LIST.put("/api/instruments", GET);
		AUTH_WHITE_LIST.put("/api/instruments/electric-guitars", GET);
		AUTH_WHITE_LIST.put("/api/instruments/bass-guitars", GET);
		AUTH_WHITE_LIST.put("/api/instruments/acoustic-and-classic-guitars", GET);
		AUTH_WHITE_LIST.put("/api/instruments/effectors", GET);
		AUTH_WHITE_LIST.put("/api/instruments/amplifiers", GET);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
			.csrf(CsrfConfigurer::disable)
			.httpBasic(HttpBasicConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> {
				auth.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll();
				auth.requestMatchers(AUTH_WHITE_PATHS).permitAll();
				AUTH_WHITE_LIST.forEach(
					(path, httpMethod) -> auth.requestMatchers(httpMethod, path).permitAll()
				);
				auth.anyRequest().authenticated();
			})
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(jwtExceptionFilter, jwtAuthenticationFilter.getClass())
			.exceptionHandling(exceptionHandlingConfigurer -> exceptionHandlingConfigurer
				.accessDeniedHandler(jwtAccessDeniedHandler)
				.authenticationEntryPoint(jwtAuthenticationEntryPoint))
			.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
