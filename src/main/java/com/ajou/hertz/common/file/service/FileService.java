package com.ajou.hertz.common.file.service;

import java.util.Collection;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ajou.hertz.common.file.dto.FileDto;

public interface FileService {

	/**
	 * 파일(MultipartFile)을 전달받아 업로드한다.
	 *
	 * @param multipartFile 업로드할 파일
	 * @param uploadPath    파일을 업로드할 경로
	 * @return 저장된 파일들의 정보가 담긴 dto
	 */
	FileDto uploadFile(MultipartFile multipartFile, String uploadPath);

	/**
	 * 파일(MultipartFile)들을 전달받아 모두 업로드한다.
	 *
	 * @param multipartFiles 업로드할 파일들
	 * @param uploadPath    파일들을 업로드할 경로
	 * @return 저장된 파일들의 정보가 담긴 dto list
	 */
	List<FileDto> uploadFiles(Iterable<MultipartFile> multipartFiles, String uploadPath);

	/**
	 * 파일들을 삭제한다.
	 *
	 * @param storedFileNames 삭제할 파일들의 이름 목록
	 */
	void deleteAll(Collection<String> storedFileNames);

	/**
	 * 파일을 삭제한다.
	 *
	 * @param storedFileName 삭제할 파일의 이름
	 */
	void deleteFile(String storedFileName);
}
