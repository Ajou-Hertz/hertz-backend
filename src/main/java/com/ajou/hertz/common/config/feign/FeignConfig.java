package com.ajou.hertz.common.config.feign;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "com.ajou.hertz")
@Configuration
public class FeignConfig {
}
