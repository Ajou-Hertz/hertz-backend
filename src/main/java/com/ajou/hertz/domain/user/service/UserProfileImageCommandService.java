package com.ajou.hertz.domain.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ajou.hertz.common.file.dto.FileDto;
import com.ajou.hertz.common.file.service.FileService;
import com.ajou.hertz.domain.user.entity.User;
import com.ajou.hertz.domain.user.entity.UserProfileImage;
import com.ajou.hertz.domain.user.repository.UserProfileImageRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserProfileImageCommandService {

	private final FileService fileService;
	private final UserProfileImageRepository userProfileImageRepository;

	private static final String USER_PROFILE_IMAGE_UPLOAD_PATH = "user-profile-images/";

	/**
	 * 유저의 프로필 이미지를 업데이트한다.
	 *
	 * @param user 프로필 이미지를 업데이트할 유저
	 * @param newProfileImage 새로운 프로필 이미지
	 *
	 * @return 새로운 프로필 이미지 URL
	 */
	public String updateProfileImage(User user, MultipartFile newProfileImage) {
		deleteOldProfileImage(user.getId());
		UserProfileImage newUserProfileImage = uploadNewProfileImage(user, newProfileImage);
		return newUserProfileImage.getUrl();
	}

	/**
	 * 유저의 프로필 이미지를 삭제한다.
	 *
	 * @param userId 프로필 이미지를 삭제할 유저의 id
	 */
	private void deleteOldProfileImage(Long userId) {
		Optional<UserProfileImage> optionalOldProfileImage = userProfileImageRepository.findByUser_Id(userId);
		if (optionalOldProfileImage.isPresent()) {
			UserProfileImage oldProfileImage = optionalOldProfileImage.get();
			userProfileImageRepository.delete(oldProfileImage);
			userProfileImageRepository.flush();
			fileService.deleteFile(oldProfileImage.getStoredName());
		}
	}

	/**
	 * 새로운 프로필 이미지를 업로드한다.
	 * @param user 프로필 이미지를 업데이트할 유저
	 * @param newProfileImage 새로운 프로필 이미지
	 *
	 * @return 새로운 프로필 이미지 entity
	 */
	private UserProfileImage uploadNewProfileImage(User user, MultipartFile newProfileImage) {
		FileDto uploadedFile = fileService.uploadFile(newProfileImage, USER_PROFILE_IMAGE_UPLOAD_PATH);
		UserProfileImage newUserProfileImage = UserProfileImage.create(
			user,
			uploadedFile.getOriginalName(),
			uploadedFile.getStoredName(),
			uploadedFile.getUrl());
		userProfileImageRepository.save(newUserProfileImage);
		return newUserProfileImage;
	}
}
