package com.ajou.hertz.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ajou.hertz.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	Optional<User> findByKakaoUid(String kakaoUid);

	Optional<User> findByPhone(String phone);

	boolean existsByEmail(String email);

	boolean existsByKakaoUid(String kakaoUid);

	boolean existsByPhone(String phone);
}
