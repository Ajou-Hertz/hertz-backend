package com.ajou.hertz.common.auth;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.ajou.hertz.common.auth.dto.JwtTokenInfoDto;
import com.ajou.hertz.common.auth.exception.TokenValidateException;
import com.ajou.hertz.domain.user.dto.UserDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

	private final UserDetailsService userDetailsService;

	private static final long MINUTE = 1000 * 60L;
	private static final long HOUR = 60 * MINUTE;
	private static final long ACCESS_TOKEN_EXPIRED_DURATION = 2 * HOUR; // Access token 만료시간: 2시간
	private static final String ROLE_CLAIM_KEY = "role";

	@Value("${jwt.secret-key}")
	private String salt;

	private Key secretKey;

	/**
	 * 객체 초기화, jwt secret key를 Base64로 인코딩
	 */
	@PostConstruct
	protected void init() {
		secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * Jwt access token을 생성하여 반환한다.
	 *
	 * @param userDto 회원 정보가 담긴 dto
	 * @return 생성한 jwt token
	 */
	public JwtTokenInfoDto createAccessToken(UserDto userDto) {
		return createJwtToken(userDto, ACCESS_TOKEN_EXPIRED_DURATION);
	}

	/**
	 * JWT token에서 사용자 정보 조회 후 security login 과정(UsernamePasswordAuthenticationToken)을 수행한다.
	 *
	 * @param token Jwt token
	 * @return Token을 통해 조회한 사용자 정보
	 */
	public Authentication getAuthentication(String token) {
		UserDetails principal = userDetailsService.loadUserByUsername(getUsernameFromToken(token));
		return new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());
	}

	/**
	 * 토큰의 유효성, 만료일자 검증
	 *
	 * @param token 검증하고자 하는 JWT token
	 * @throws TokenValidateException Token 값이 잘못되거나 만료되어 유효하지 않은 경우
	 */
	public void validateToken(String token) {
		try {
			Jwts
				.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token);
		} catch (UnsupportedJwtException ex) {
			throw new TokenValidateException("The claimsJws argument does not represent an Claims JWS", ex);
		} catch (MalformedJwtException ex) {
			throw new TokenValidateException("The claimsJws string is not a valid JWS", ex);
		} catch (SignatureException ex) {
			throw new TokenValidateException("The claimsJws JWS signature validation fails", ex);
		} catch (ExpiredJwtException ex) {
			throw new TokenValidateException(
				"The specified JWT is a Claims JWT and the Claims has an expiration time before the time this method is invoked.",
				ex
			);
		} catch (IllegalArgumentException ex) {
			throw new TokenValidateException("The claimsJws string is null or empty or only whitespace", ex);
		}
	}

	/**
	 * Subject(socialUid), 로그인 type, token 만료 시간을 전달받아 JWT token을 생성한다.
	 * 현재 access token과 refresh token을 생성할 때 만료 시간 외의 정보는 동일하므로 method를 통일하였다.
	 *
	 * @param userDto            회원 정보가 담긴 dto
	 * @param tokenExpiredDuration Token 만료 시간
	 * @return 생성된 jwt token과 만료 시각이 포함된 <code>JwtTokenInfoDto</code> 객체
	 */
	private JwtTokenInfoDto createJwtToken(UserDto userDto, Long tokenExpiredDuration) {
		Date now = new Date();
		Date expiresAt = new Date(now.getTime() + tokenExpiredDuration);
		String token = Jwts.builder()
			.setHeaderParam("typ", "JWT")
			.setSubject(String.valueOf(userDto.getId()))
			.claim(ROLE_CLAIM_KEY, userDto.getRoleTypes())
			.setIssuedAt(now)
			.setExpiration(expiresAt)
			.signWith(secretKey, SignatureAlgorithm.HS256)
			.compact();
		return new JwtTokenInfoDto(token, new Timestamp(expiresAt.getTime()).toLocalDateTime());
	}

	/**
	 * 토큰에서 회원 정보(username)를 추출한다. 이 때 username은 회원의 id(PK) 값.
	 *
	 * @param token Jwt token
	 * @return 추출한 회원 정보(username == email)
	 */
	public String getUsernameFromToken(String token) {
		return getClaimsFromToken(token).getSubject();
	}

	/**
	 * Claims 정보를 추출한다.
	 *
	 * @param token 정보를 추출하고자 하는 jwt token
	 * @return token에서 추출한 Claims 정보
	 */
	private Claims getClaimsFromToken(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}
}
