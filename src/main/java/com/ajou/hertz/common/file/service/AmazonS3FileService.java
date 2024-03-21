package com.ajou.hertz.common.file.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ajou.hertz.common.exception.MultipartFileNotReadableException;
import com.ajou.hertz.common.file.dto.FileDto;
import com.ajou.hertz.common.properties.AWSProperties;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AmazonS3FileService implements FileService {

	private final AmazonS3 s3Client;
	private final AWSProperties awsProperties;

	/**
	 * MultipartFile을 전달받아 S3 bucket에 업로드한다.
	 *
	 * @param multipartFile 업로드할 파일
	 * @param uploadPath    파일을 업로드할 경로 (예. example/, user/ 등)
	 * @return AWS S3에 저장된 파일 정보
	 */
	@Override
	public FileDto uploadFile(MultipartFile multipartFile, String uploadPath) {
		String originalFileName = multipartFile.getOriginalFilename();
		if (!StringUtils.hasText(originalFileName)) {
			originalFileName = "";
		}
		String storeFileName = createFileNameToStore(originalFileName, uploadPath);
		ObjectMetadata objectMetadata = createMetadataWithContentInfo(multipartFile);
		InputStream inputStream = getInputStreamFromMultipartFile(multipartFile);

		s3Client.putObject(new PutObjectRequest(
			awsProperties.s3().bucketName(),
			storeFileName,
			inputStream,
			objectMetadata
		).withCannedAcl(CannedAccessControlList.PublicRead));
		String storedFileUrl = awsProperties.cloudFront().baseUrl() + storeFileName;

		return FileDto.create(originalFileName, storeFileName, storedFileUrl);
	}

	// TODO: 추후 서버 성능 테스트와 함께 @Async 사용한 비동기 로직으로 변경 검토

	/**
	 * 파일(MultipartFile)들을 전달받아 모두 s3 bucket에 업로드한다.
	 *
	 * @param multipartFiles 업로드할 파일들
	 * @param uploadPath     파일들을 업로드할 경로
	 * @return AWS S3에 저장된 파일들의 정보가 담긴 dto list
	 */
	@Override
	public List<FileDto> uploadFiles(Iterable<MultipartFile> multipartFiles, String uploadPath) {
		List<FileDto> result = new ArrayList<>();
		multipartFiles.forEach(multipartFile -> {
			FileDto fileUploaded = uploadFile(multipartFile, uploadPath);
			result.add(fileUploaded);
		});
		return result;
	}

	/**
	 * S3 bucket에서 파일들을 삭제한다.
	 *
	 * @param storedFileNames 삭제할 파일들의 이름 목록 (keys of bucket object)
	 */
	@Override
	public void deleteAll(Collection<String> storedFileNames) {
		storedFileNames.forEach(storedFileName -> s3Client.deleteObject(
			new DeleteObjectRequest(awsProperties.s3().bucketName(), storedFileName)
		));
	}

	/**
	 * S3 Bucket에 업로드할 고유한 파일 이름을 생성한다.
	 *
	 * @param originalFilename 파일의 원래 이름.
	 * @param uploadPath       업로드 경로
	 * @return 생성된 고유한 파일 이름
	 */
	private String createFileNameToStore(String originalFilename, String uploadPath) {
		// Extract file extension
		int pos = originalFilename.lastIndexOf(".");
		String extension = originalFilename.substring(pos + 1);
		String uuid = UUID.randomUUID().toString();
		return uploadPath + uuid + "." + extension;
	}

	/**
	 * MultipartFile에 대한 ObjectMetadata를 생성한다.
	 *
	 * @param multipartFile MultipartFile
	 * @return multipartFile에 대해 생성된 ObjectMetadata
	 */
	private ObjectMetadata createMetadataWithContentInfo(MultipartFile multipartFile) {
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(multipartFile.getContentType());
		objectMetadata.setContentLength(multipartFile.getSize());
		return objectMetadata;
	}

	/**
	 * 파일(MultipartFile)에서 InputStream을 읽어온다.
	 *
	 * @param multipartFile InputStream을 읽고자 하는 파일
	 * @return 전달받은 MultipartFile에서 읽어온 InputStream
	 * @throws MultipartFileNotReadableException MultipartFile에서 InputStream을 읽어오는 중 문제가 발생한 경우 (in case of access errors (if the temporary store fails))
	 */
	private static InputStream getInputStreamFromMultipartFile(MultipartFile multipartFile) {
		try {
			return multipartFile.getInputStream();
		} catch (IOException ex) {
			throw new MultipartFileNotReadableException(ex);
		}
	}
}
