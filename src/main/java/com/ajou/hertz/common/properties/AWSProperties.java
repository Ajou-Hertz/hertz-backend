package com.ajou.hertz.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("aws")
public record AWSProperties(
	S3 s3,
	CloudFront cloudFront
) {

	public record S3(
		String accessKey,
		String secretKey,
		String bucketName
	) {
	}

	public record CloudFront(String baseUrl) {
	}
}
