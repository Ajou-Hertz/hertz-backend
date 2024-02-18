package com.ajou.hertz.domain.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ajou.hertz.common.properties.HertzProperties;
import com.ajou.hertz.domain.user.dto.UserDto;
import com.ajou.hertz.domain.user.dto.request.SignUpRequest;
import com.ajou.hertz.domain.user.entity.User;
import com.ajou.hertz.domain.user.exception.UserEmailDuplicationException;
import com.ajou.hertz.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserCommandService {

	private final UserQueryService userQueryService;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final HertzProperties hertzProperties;

	/**
	 * 새로운 회원을 등록한다.
	 *
	 * @param signUpRequest 회원 등록을 위한 정보가 담긴 dto
	 * @return 새로 등록된 회원 정보가 담긴 dto
	 * @throws UserEmailDuplicationException 전달된 이메일이 중복된 이메일인 경우. 즉, 다른 사용자가 이미 같은 이메일을 사용하고 있는 경우.
	 */
	public UserDto createNewUser(SignUpRequest signUpRequest) {
		String email = signUpRequest.getEmail();
		if (userQueryService.existsByEmail(email)) {
			throw new UserEmailDuplicationException(email);
		}

		User userSaved = userRepository.save(
			User.create(
				email,
				passwordEncoder.encode(signUpRequest.getPassword()),
				hertzProperties.userDefaultProfileImageUrl(),
				signUpRequest.getBirth(),
				signUpRequest.getGender(),
				signUpRequest.getPhone()
			)
		);
		return UserDto.from(userSaved);
	}
}
