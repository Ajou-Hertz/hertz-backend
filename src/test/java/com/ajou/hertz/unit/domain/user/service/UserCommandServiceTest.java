package com.ajou.hertz.unit.domain.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ajou.hertz.domain.user.constant.Gender;
import com.ajou.hertz.domain.user.constant.RoleType;
import com.ajou.hertz.domain.user.dto.UserDto;
import com.ajou.hertz.domain.user.dto.request.SignUpRequest;
import com.ajou.hertz.domain.user.entity.User;
import com.ajou.hertz.domain.user.exception.UserEmailDuplicationException;
import com.ajou.hertz.domain.user.repository.UserRepository;
import com.ajou.hertz.domain.user.service.UserCommandService;
import com.ajou.hertz.domain.user.service.UserQueryService;

@DisplayName("[Unit] Service(Command) - User")
@ExtendWith(MockitoExtension.class)
class UserCommandServiceTest {

	@InjectMocks
	private UserCommandService sut;

	@Mock
	private UserQueryService userQueryService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Test
	void 주어진_회원_정보로_신규_회원을_등록한다() throws Exception {
		// given
		long userId = 1L;
		SignUpRequest signUpRequest = createSignUpRequest();
		String passwordEncoded = "$2a$abc123";
		User expectedResult = createUser(userId, passwordEncoded);
		given(userQueryService.existsByEmail(signUpRequest.getEmail())).willReturn(false);
		given(passwordEncoder.encode(signUpRequest.getPassword())).willReturn(passwordEncoded);
		given(userRepository.save(any(User.class))).willReturn(expectedResult);

		// when
		UserDto actualResult = sut.createNewUser(signUpRequest);

		// then
		then(userQueryService).should().existsByEmail(signUpRequest.getEmail());
		then(passwordEncoder).should().encode(signUpRequest.getPassword());
		then(userRepository).should().save(any(User.class));
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(actualResult.getId()).isEqualTo(expectedResult.getId());
	}

	@Test
	void 주어진_회원_정보로_신규_회원을_등록한다_이미_사용_중인_이메일이라면_예외가_발생한다() throws Exception {
		// given
		String email = "test@test.com";
		SignUpRequest signUpRequest = createSignUpRequest(email);
		given(userQueryService.existsByEmail(email)).willReturn(true);

		// when
		Throwable t = catchThrowable(() -> sut.createNewUser(signUpRequest));

		// then
		then(userQueryService).should().existsByEmail(email);
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(t).isInstanceOf(UserEmailDuplicationException.class);
	}

	private void verifyEveryMocksShouldHaveNoMoreInteractions() {
		then(userQueryService).shouldHaveNoMoreInteractions();
		then(userRepository).shouldHaveNoMoreInteractions();
		then(passwordEncoder).shouldHaveNoMoreInteractions();
	}

	private User createUser(Long id, String password) throws Exception {
		Constructor<User> userConstructor = User.class.getDeclaredConstructor(
			Long.class, Set.class, String.class, String.class, String.class,
			LocalDate.class, Gender.class, String.class, String.class
		);
		userConstructor.setAccessible(true);
		return userConstructor.newInstance(
			id,
			Set.of(RoleType.USER),
			"test@test.com",
			password,
			"kakao-user-id",
			LocalDate.of(2024, 1, 1),
			Gender.ETC,
			"010-1234-5678",
			null
		);
	}

	private SignUpRequest createSignUpRequest(String email) throws Exception {
		Constructor<SignUpRequest> signUpRequestConstructor = SignUpRequest.class.getDeclaredConstructor(
			String.class, String.class, LocalDate.class, Gender.class, String.class
		);
		signUpRequestConstructor.setAccessible(true);
		return signUpRequestConstructor.newInstance(
			email,
			"1q2w3e4r!",
			LocalDate.of(2024, 1, 1),
			Gender.ETC,
			"01012345678"
		);
	}

	private SignUpRequest createSignUpRequest() throws Exception {
		return createSignUpRequest("test@test.com");
	}
}