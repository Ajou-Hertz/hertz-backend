package com.ajou.hertz.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@MappedSuperclass
public abstract class FileEntity extends BaseEntity {

	@Column(nullable = false)
	private String originalName;

	@Column(nullable = false)
	private String storedName;

	@Column(nullable = false)
	private String url;
}
