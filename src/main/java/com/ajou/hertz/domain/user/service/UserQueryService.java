package com.ajou.hertz.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ajou.hertz.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserQueryService {

	private final UserRepository userRepository;

	/**
	 * 전달된 email을 사용 중인 회원의 존재 여부를 조회한다.
	 *
	 * @param email email
	 * @return 전달된 email을 사용 중인 회원의 존재 여부
	 */
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
}
