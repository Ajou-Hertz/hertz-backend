package com.ajou.hertz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.PropertySource;

@ConfigurationPropertiesScan
@PropertySource("classpath:/env.properties")
@SpringBootApplication
public class HertzApplication {

    public static void main(String[] args) {
        SpringApplication.run(HertzApplication.class, args);
    }

}
