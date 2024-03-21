package com.ajou.hertz.common.file.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class FileDto {

	private String originalName;
	private String storedName;
	private String url;

	public static FileDto create(
		String originalName,
		String storedName,
		String url
	) {
		return new FileDto(originalName, storedName, url);
	}

	public String getStoredFileUrl() {
		return url + "/" + storedName;
	}

}
