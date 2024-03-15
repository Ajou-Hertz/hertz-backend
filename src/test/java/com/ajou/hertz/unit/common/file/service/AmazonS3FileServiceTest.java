package com.ajou.hertz.unit.common.file.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
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
import com.amazonaws.services.s3.model.DeleteObjectRequest;
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

	@BeforeEach
	void setUpMockProperties() {
		lenient().when(awsProperties.s3()).thenReturn(s3);
		lenient().when(awsProperties.cloudFront()).thenReturn(cloudFront);
		lenient().when(awsProperties.s3().bucketName()).thenReturn("aws-s3-bucket-name");
		lenient().when(awsProperties.cloudFront().baseUrl()).thenReturn("aws-cloud-front-base-url/");
	}

	@Test
	void 파일이_주어지면_파일을_S3에_업로드한다() {
		// given
		MultipartFile multipartFile = createMockMultipartFile(null);
		given(s3Client.putObject(any(PutObjectRequest.class))).willReturn(new PutObjectResult());

		// when
		sut.uploadFile(multipartFile, "test/");

		// then
		then(s3Client).should().putObject(any(PutObjectRequest.class));
		verifyEveryMocksShouldHaveNoMoreInteractions();
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
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	@Test
	void 여러_개의_파일들이_주어지면_전부_S3에_업로드한다() {
		// given
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
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(results.size()).isEqualTo(multipartFiles.size());
		for (int i = 0; i < results.size(); i++) {
			MultipartFile multipartFile = multipartFiles.get(i);
			FileDto result = results.get(i);
			assertThat(result.getOriginalName()).isEqualTo(multipartFile.getOriginalFilename());
		}
	}

	@Test
	void S3에_저장된_파일들의_이름이_주어지고_파일들을_S3_버킷에서_삭제한다() throws Exception {
		// given
		List<String> storedFileNames = List.of(
			"stored-file-name-1",
			"stored-file-name-2",
			"stored-file-name-3",
			"stored-file-name-4"
		);
		willDoNothing().given(s3Client).deleteObject(any(DeleteObjectRequest.class));

		// when
		sut.deleteAll(storedFileNames);

		// then
		verify(s3Client, times(storedFileNames.size())).deleteObject(any(DeleteObjectRequest.class));
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	private void verifyEveryMocksShouldHaveNoMoreInteractions() {
		then(s3Client).shouldHaveNoMoreInteractions();
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