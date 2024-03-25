package com.ajou.hertz.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ajou.hertz.domain.user.entity.UserProfileImage;

public interface UserProfileImageRepository extends JpaRepository<UserProfileImage, Long> {
	Optional<UserProfileImage> findByUser_Id(Long userId);
}
