package com.ajou.hertz.unit.domain.user.service;

import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.ajou.hertz.common.file.dto.FileDto;
import com.ajou.hertz.common.file.service.FileService;
import com.ajou.hertz.common.properties.HertzProperties;
import com.ajou.hertz.domain.user.constant.Gender;
import com.ajou.hertz.domain.user.constant.RoleType;
import com.ajou.hertz.domain.user.entity.User;
import com.ajou.hertz.domain.user.entity.UserProfileImage;
import com.ajou.hertz.domain.user.repository.UserProfileImageRepository;
import com.ajou.hertz.domain.user.repository.UserRepository;
import com.ajou.hertz.domain.user.service.UserProfileImageCommandService;
import com.ajou.hertz.domain.user.service.UserQueryService;
import com.ajou.hertz.util.ReflectionUtils;

@DisplayName("[Unit] Service(Command) - User Profile Image")
@ExtendWith(MockitoExtension.class)
public class UserProfileImageCommandServiceTest {
	@InjectMocks
	private UserProfileImageCommandService sut;

	@Mock
	private UserQueryService userQueryService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserProfileImageRepository userProfileImageRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private HertzProperties hertzProperties;

	@Mock
	private FileService fileService;

	@BeforeTestMethod
	public void setUp() {
		given(hertzProperties.userDefaultProfileImageUrl()).willReturn("https://user-default-profile-image");
	}

	@Test
	void 기본_프로필_이미지인_경우_유저의_프로필_이미지를_최초로_업데이트한다() throws Exception {
		// given
		Long userId = 1L;
		User user = createUser(userId, "password", "kakaoUid");
		given(userQueryService.getById(userId)).willReturn(user);

		MultipartFile newProfileImage = new MockMultipartFile(
			"profileImage",
			"newProfile.jpg",
			"image/jpeg",
			"new image content".getBytes()
		);

		String uploadPath = "user-profile-images/";
		FileDto uploadedFile = createFileDto();
		given(fileService.uploadFile(newProfileImage, uploadPath)).willReturn(uploadedFile);

		//  when
		sut.uploadProfileImage(userId, newProfileImage);

		// then
		then(userQueryService).should().getById(userId);
		then(fileService).should().uploadFile(newProfileImage, uploadPath);
		then(userProfileImageRepository).should().save(any(UserProfileImage.class));
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	@Test
	void 기존_프로필_이미지가_존재하는_경우_이미지를_삭제하고_다시_이미지를_저장한다() throws Exception {
		// given
		Long userId = 1L;
		User user = createUser(userId, "password", "kakaoUid");
		given(userQueryService.getById(userId)).willReturn(user);

		UserProfileImage oldProfileImage = createUserProfileImage(user, "oldOriginalName.jpg", "oldStoredName.jpg",
			"https://example.com/old-image-url");
		given(userProfileImageRepository.findById(userId)).willReturn(Optional.of(oldProfileImage));

		MultipartFile newProfileImage = new MockMultipartFile(
			"profileImage",
			"newProfile.jpg",
			"image/jpeg",
			"new image content".getBytes()
		);

		String uploadPath = "user-profile-images/";
		FileDto uploadedFile = createFileDto();

		given(fileService.uploadFile(newProfileImage, uploadPath)).willReturn(uploadedFile);

		// when
		sut.uploadProfileImage(userId, newProfileImage);

		// then
		then(userQueryService).should().getById(userId);
		then(userProfileImageRepository).should().findById(userId);
		then(userProfileImageRepository).should().delete(oldProfileImage);
		then(fileService).should().deleteFile(oldProfileImage.getStoredName());
		then(fileService).should().uploadFile(newProfileImage, uploadPath);
		then(userProfileImageRepository).should().save(any(UserProfileImage.class));
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	private void verifyEveryMocksShouldHaveNoMoreInteractions() {
		then(userQueryService).shouldHaveNoMoreInteractions();
		then(userRepository).shouldHaveNoMoreInteractions();
		then(passwordEncoder).shouldHaveNoMoreInteractions();
		then(fileService).shouldHaveNoMoreInteractions();
	}

	private static User createUser(Long id, String password, String kakaoUid, Gender gender) throws Exception {
		return ReflectionUtils.createUser(
			id,
			Set.of(RoleType.USER),
			"test@test.com",
			password,
			kakaoUid,
			"https://user-default-profile-image-url",
			LocalDate.of(2024, 1, 1),
			gender,
			"010-1234-5678",
			"https://contactLink"
		);
	}

	private static User createUser(Long id, String password, String kakaoUid) throws Exception {
		return createUser(id, password, kakaoUid, Gender.ETC);
	}

	private static UserProfileImage createUserProfileImage(User user, String originalName, String storedName,
		String url) {
		return UserProfileImage.create(user, originalName, storedName, url);
	}

	private FileDto createFileDto() throws Exception {
		return ReflectionUtils.createFileDto(
			"test.jpg",
			"test-stored.jpg",
			"https://example.com/user-profile-images/storedFileName.jpg");
	}

}
