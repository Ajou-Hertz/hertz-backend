package com.ajou.hertz.domain.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ajou.hertz.domain.user.dto.UserDto;
import com.ajou.hertz.domain.user.entity.User;
import com.ajou.hertz.domain.user.exception.UserNotFoundByEmailException;
import com.ajou.hertz.domain.user.exception.UserNotFoundByIdException;
import com.ajou.hertz.domain.user.exception.UserNotFoundByPhoneException;
import com.ajou.hertz.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserQueryService {

	private final UserRepository userRepository;

	/**
	 * 유저 id user entity를 조회한다.
	 *
	 * @param id 조회하고자 하는 user의 id
	 * @return 조회한 user entity
	 * @throws UserNotFoundByIdException 일치하는 유저를 찾지 못한 경우
	 */
	private User getById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundByIdException(id));
	}

	/**
	 * Email로 user entity를 조회한다.
	 *
	 * @param email 조회하고자 하는 user의 email
	 * @return 조회한 user entity
	 * @throws UserNotFoundByEmailException 일치하는 유저를 찾지 못한 경우
	 */
	private User getByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundByEmailException(email));
	}

	public Optional<UserDto> findDtoByKakaoUid(String kakaoUid) {
		return userRepository.findByKakaoUid(kakaoUid).map(UserDto::from);
	}

	/**
	 * 유저 id로 유저를 조회한다.
	 *
	 * @param id 조회하고자 하는 user의 id
	 * @return 조회한 유저 정보가 담긴 dto
	 * @throws UserNotFoundByIdException 일치하는 유저를 찾지 못한 경우
	 */
	public UserDto getDtoById(Long id) {
		return UserDto.from(getById(id));
	}

	/**
	 * Email로 user 정보를 조회한다.
	 *
	 * @param email 조회하고자 하는 user의 email
	 * @return 조회한 유저 정보가 담긴 dto
	 * @throws UserNotFoundByEmailException 일치하는 유저를 찾지 못한 경우
	 */
	public UserDto getDtoByEmail(String email) {
		return UserDto.from(getByEmail(email));
	}

	/**
	 * 전화번호로 user 정보를 조회한다.
	 *
	 * @param phone 조회하고자 하는 user의 전화번호
	 * @return 조회한 유저 정보가 담긴 dto
	 * @throws UserNotFoundByPhoneException 일치하는 유저를 찾지 못한 경우
	 */
	public UserDto getDtoByPhone(String phone) {
		User user = userRepository.findByPhone(phone).orElseThrow(() -> new UserNotFoundByPhoneException(phone));
		return UserDto.from(user);
	}

	/**
	 * 전달된 email을 사용 중인 회원의 존재 여부를 조회한다.
	 *
	 * @param email email
	 * @return 전달된 email을 사용 중인 회원의 존재 여부
	 */
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	/**
	 * 전달된 kakaoUid를 사용 중인 회원의 존재 여부를 조회한다.
	 *
	 * @param kakaoUid kakao user id
	 * @return 전달된 kakaoUid를 사용 중인 회원의 존재 여부
	 */
	public boolean existsByKakaoUid(String kakaoUid) {
		return userRepository.existsByKakaoUid(kakaoUid);
	}

	/**
	 * 전달된 phone를 사용 중인 회원의 존재 여부를 조회한다.
	 *
	 * @param phone phone number
	 * @return 전달된 phone를 사용 중인 회원의 존재 여부
	 */
	public boolean existsByPhone(String phone) {
		return userRepository.existsByPhone(phone);
	}
}
