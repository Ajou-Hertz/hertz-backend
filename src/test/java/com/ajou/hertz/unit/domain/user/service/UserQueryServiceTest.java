package com.ajou.hertz.unit.domain.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}