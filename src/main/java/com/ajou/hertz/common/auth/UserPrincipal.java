package com.ajou.hertz.common.auth;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ajou.hertz.domain.user.constant.RoleType;
import com.ajou.hertz.domain.user.dto.UserDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserPrincipal implements UserDetails {

	private UserDto userDto;

	public Long getUserId() {
		return userDto.getId();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return userDto
			.getRoleTypes()
			.stream()
			.map(RoleType::getRoleName)
			.map(SimpleGrantedAuthority::new)
			.collect(Collectors.toUnmodifiableSet());
	}

	@Override
	public String getUsername() {
		return String.valueOf(getUserId());
	}

	@Override
	public String getPassword() {
		return userDto.getPassword();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
