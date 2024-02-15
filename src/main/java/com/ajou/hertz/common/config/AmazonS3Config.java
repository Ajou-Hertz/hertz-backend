package com.ajou.hertz.common.config;

import java.util.NoSuchElementException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ajou.hertz.common.properties.AWSProperties;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class AmazonS3Config {

	private final AWSProperties awsProperties;

	@Bean
	public AmazonS3 amazonS3Client() {
		AmazonS3 s3Client = AmazonS3ClientBuilder
			.standard()
			.withCredentials(new AWSStaticCredentialsProvider(
				new BasicAWSCredentials(
					awsProperties.s3().accessKey(),
					awsProperties.s3().secretKey()
				)
			))
			.withRegion(Regions.AP_NORTHEAST_2)
			.build();

		if (!s3Client.doesBucketExistV2(awsProperties.s3().bucketName())) {
			throw new NoSuchElementException("AWS S3 Bucket에 엑세스 할 수 없습니다. 엑세스 정보와 bucket name을 다시 확인해주세요.");
		}

		return s3Client;
	}
}

