package com.ajou.hertz.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

import com.ajou.hertz.global.config.SecurityConfig;

@Import(SecurityConfig.class)
@TestConfiguration
public class TestSecurityConfig {
}
