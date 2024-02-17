package com.ajou.hertz.unit.domain.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ajou.hertz.domain.user.constant.Gender;
import com.ajou.hertz.domain.user.constant.RoleType;
import com.ajou.hertz.domain.user.dto.UserDto;
import com.ajou.hertz.domain.user.entity.User;
import com.ajou.hertz.domain.user.exception.UserNotFoundByEmailException;
import com.ajou.hertz.domain.user.exception.UserNotFoundByIdException;
import com.ajou.hertz.domain.user.repository.UserRepository;
import com.ajou.hertz.domain.user.service.UserQueryService;

@DisplayName("[Unit] Service(Query) - User")
@ExtendWith(MockitoExtension.class)
class UserQueryServiceTest {

	@InjectMocks
	private UserQueryService sut;

	@Mock
	private UserRepository userRepository;

	@Test
	void 유저_id가_주어지고_주어진_id로_유저를_조회하면_조회된_유저_정보가_반환된다() throws Exception {
		// given
		long userId = 1L;
		User expectedResult = createUser(userId);
		given(userRepository.findById(userId)).willReturn(Optional.of(expectedResult));

		// when
		UserDto actualResult = sut.getDtoById(userId);

		// then
		then(userRepository).should().findById(userId);
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(actualResult)
			.hasFieldOrPropertyWithValue("id", expectedResult.getId());
	}

	@Test
	void 유저_id가_주어지고_주어진_id로_유저를_조회한다_만약_일치하는_유저가_없다면_예외가_발생한다() {
		// given
		long userId = 1L;
		given(userRepository.findById(userId)).willReturn(Optional.empty());

		// when
		Throwable t = catchThrowable(() -> sut.getDtoById(userId));

		// then
		then(userRepository).should().findById(userId);
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(t).isInstanceOf(UserNotFoundByIdException.class);
	}

	@Test
	void 이메일이_주어지고_주어진_이메일로_유저를_조회하면_조회된_유저_정보가_반환된다() throws Exception {
		// given
		String email = "test@mail.com";
		User expectedResult = createUser(1L, email);
		given(userRepository.findByEmail(email)).willReturn(Optional.of(expectedResult));

		// when
		UserDto actualResult = sut.getDtoByEmail(email);

		// then
		then(userRepository).should().findByEmail(email);
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(actualResult)
			.hasFieldOrPropertyWithValue("id", expectedResult.getId())
			.hasFieldOrPropertyWithValue("email", expectedResult.getEmail());
	}

	@Test
	void 이메일이_주어지고_주어진_이메일로_유저를_조회한다_만약_일치하는_유저가_없다면_예외가_발생한다() {
		// given
		String email = "test@mail.com";
		given(userRepository.findByEmail(email)).willReturn(Optional.empty());

		// when
		Throwable t = catchThrowable(() -> sut.getDtoByEmail(email));

		// then
		then(userRepository).should().findByEmail(email);
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(t).isInstanceOf(UserNotFoundByEmailException.class);
	}

	@Test
	void 전달된_이메일을_사용_중인_회원의_존재_여부를_조회한다() {
		// given
		String email = "test@test.com";
		boolean expectedResult = true;
		given(userRepository.existsByEmail(email)).willReturn(expectedResult);

		// when
		boolean actualResult = sut.existsByEmail(email);

		// then
		then(userRepository).should().existsByEmail(email);
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(actualResult).isEqualTo(expectedResult);
	}

	private void verifyEveryMocksShouldHaveNoMoreInteractions() {
		then(userRepository).shouldHaveNoMoreInteractions();
	}

	private User createUser(Long id, String email) throws Exception {
		Constructor<User> userConstructor = User.class.getDeclaredConstructor(
			Long.class, Set.class, String.class, String.class, String.class,
			LocalDate.class, Gender.class, String.class, String.class
		);
		userConstructor.setAccessible(true);
		return userConstructor.newInstance(
			id,
			Set.of(RoleType.USER),
			email,
			"password",
			"kakao-user-id",
			LocalDate.of(2024, 1, 1),
			Gender.ETC,
			"010-1234-5678",
			null
		);
	}

	private User createUser(Long id) throws Exception {
		return createUser(id, "test@mail.com");
	}
}