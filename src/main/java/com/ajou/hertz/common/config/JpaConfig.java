package com.ajou.hertz.common.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

	// TODO: 로그인 및 인증/인가 로직 작성 후 변경 필요
	@Bean
	public AuditorAware<Long> auditorAware() {
		return () -> Optional.of(1L);
	}
}
