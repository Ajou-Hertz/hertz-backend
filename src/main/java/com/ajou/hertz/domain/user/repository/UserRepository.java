package com.ajou.hertz.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ajou.hertz.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByEmail(String email);
}
