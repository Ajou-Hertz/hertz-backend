package com.ajou.hertz.domain.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ajou.hertz.common.file.dto.FileDto;
import com.ajou.hertz.common.file.service.FileService;
import com.ajou.hertz.domain.user.dto.UserDto;
import com.ajou.hertz.domain.user.entity.User;
import com.ajou.hertz.domain.user.entity.UserProfileImage;
import com.ajou.hertz.domain.user.repository.UserProfileImageRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserProfileImageCommandService {

	private final UserQueryService userQueryService;
	private final FileService fileService;
	private final UserProfileImageRepository userProfileImageRepository;

	/**
	 * 유저의 프로필 이미지를 업데이트한다.
	 *
	 * @param userId 유저 id
	 * @param newProfileImage 새로운 프로필 이미지
	 *
	 * @return 업데이트된 유저 정보
	 */
	public UserDto updateProfileImage(Long userId, MultipartFile newProfileImage) {

		User user = userQueryService.getById(userId);
		Optional<UserProfileImage> optionalOldProfileImage = userProfileImageRepository.findById(userId);

		String uploadPath = "user-profile-images/";
		FileDto uploadedFile = fileService.uploadFile(newProfileImage, uploadPath);
		String newProfileImageUrl = uploadedFile.getUrl();
		if (optionalOldProfileImage.isPresent()) {
			UserProfileImage oldProfileImage = optionalOldProfileImage.get();
			userProfileImageRepository.delete(oldProfileImage);
			userProfileImageRepository.flush();
			fileService.deleteFile(oldProfileImage.getStoredName());
		}

		UserProfileImage newUserProfileImage = UserProfileImage.create(user, uploadedFile.getOriginalName(),
			uploadedFile.getStoredName(), newProfileImageUrl);
		userProfileImageRepository.save(newUserProfileImage);

		user.changeProfileImageUrl(newProfileImageUrl);
		return UserDto.from(user);
	}
}
