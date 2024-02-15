package com.ajou.hertz.common.config;

import static org.springframework.http.HttpMethod.*;

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

@Configuration
public class SecurityConfig {

	private static final String[] AUTH_WHITE_PATHS = {
		"/swagger-ui/**",
		"/v3/api-docs/**"
	};

	private static final Map<String, HttpMethod> AUTH_WHITE_LIST = Map.of(
		"/v*/users", POST,
		"/v*/users/existence", GET
	);

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
			.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}