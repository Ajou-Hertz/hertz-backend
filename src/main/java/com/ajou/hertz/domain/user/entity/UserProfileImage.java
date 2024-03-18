package com.ajou.hertz.domain.user.entity;

import com.ajou.hertz.common.entity.FileEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class UserProfileImage extends FileEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_profile_image_id", nullable = false)
	private Long id;

	@JoinColumn(name = "user_id", nullable = false)
	@OneToOne(fetch = FetchType.LAZY)
	private User user;

	private UserProfileImage(Long id, User user, String originalName, String storedName, String url) {
		super(originalName, storedName, url);
		this.id = id;
		this.user = user;
	}

	public static UserProfileImage of(String profileImageUrl) {
		return new UserProfileImage(null, null, null, null, profileImageUrl);
	}
}
