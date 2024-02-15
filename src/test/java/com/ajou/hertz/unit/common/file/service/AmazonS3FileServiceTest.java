package com.ajou.hertz.unit.common.file.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.ajou.hertz.common.exception.MultipartFileNotReadableException;
import com.ajou.hertz.common.file.dto.FileDto;
import com.ajou.hertz.common.file.service.AmazonS3FileService;
import com.ajou.hertz.common.properties.AWSProperties;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

@DisplayName("[Unit] Service - Amazon S3 File")
@ExtendWith(MockitoExtension.class)
class AmazonS3FileServiceTest {

	@InjectMocks
	private AmazonS3FileService sut;

	@Mock
	private AmazonS3 s3Client;

	@Mock
	private AWSProperties awsProperties;
	@Mock
	private AWSProperties.S3 s3;
	@Mock
	private AWSProperties.CloudFront cloudFront;

	private void setUpMockProperties() {
		given(awsProperties.s3()).willReturn(s3);
		given(awsProperties.cloudFront()).willReturn(cloudFront);
		given(awsProperties.s3().bucketName()).willReturn("aws-s3-bucket-name");
		given(awsProperties.cloudFront().baseUrl()).willReturn("aws-cloud-front-base-url/");
	}

	@Test
	void 파일이_주어지면_파일을_S3에_업로드한다() {
		// given
		setUpMockProperties();
		MultipartFile multipartFile = createMockMultipartFile(null);
		given(s3Client.putObject(any(PutObjectRequest.class))).willReturn(new PutObjectResult());

		// when
		sut.uploadFile(multipartFile, "test/");

		// then
		then(s3Client).should().putObject(any(PutObjectRequest.class));
		then(s3Client).shouldHaveNoMoreInteractions();
	}

	@Test
	void 파일이_주어지면_파일을_S3에_업로드한다_이때_파일의_input_stream_access_과정에서_문제가_생기면_예외가_발생한다() throws Exception {
		// given
		MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
		when(multipartFile.getInputStream()).thenThrow(IOException.class);

		// when
		Throwable t = catchThrowable(() -> sut.uploadFile(multipartFile, "test/"));

		// then
		assertThat(t).isInstanceOf(MultipartFileNotReadableException.class);
		then(s3Client).shouldHaveNoInteractions();
	}

	@Test
	void 여러_개의_파일들이_주어지면_전부_S3에_업로드한다() {
		// given
		setUpMockProperties();
		List<MultipartFile> multipartFiles = List.of(
			createMockMultipartFile("1"),
			createMockMultipartFile("2"),
			createMockMultipartFile("3")
		);
		given(s3Client.putObject(any(PutObjectRequest.class))).willReturn(new PutObjectResult());

		// when
		List<FileDto> results = sut.uploadFiles(multipartFiles, "test/");

		// then
		verify(s3Client, times(multipartFiles.size())).putObject(any(PutObjectRequest.class));
		then(s3Client).shouldHaveNoMoreInteractions();
		assertThat(results.size()).isEqualTo(multipartFiles.size());
		for (int i = 0; i < results.size(); i++) {
			MultipartFile multipartFile = multipartFiles.get(i);
			FileDto result = results.get(i);
			assertThat(result.getOriginalName()).isEqualTo(multipartFile.getOriginalFilename());
		}
	}

	private MockMultipartFile createMockMultipartFile(String fileName) {
		return new MockMultipartFile(
			"test",
			fileName,
			MediaType.IMAGE_PNG_VALUE,
			(byte[])null
		);
	}
}