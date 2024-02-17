package com.ajou.hertz.common.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.ajou.hertz.domain.user.service.UserQueryService;

@Configuration
public class CustomUserDetailsService {

	@Bean
	public UserDetailsService userDetailsService(UserQueryService userQueryService) {
		return username -> new UserPrincipal(
			userQueryService.getDtoById(Long.parseLong(username))
		);
	}
}
