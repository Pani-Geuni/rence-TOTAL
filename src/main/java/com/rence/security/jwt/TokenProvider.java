package com.rence.security.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.rence.security.BackOfficeUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TokenProvider implements InitializingBean {

	
	private static final String AUTHORITIES_KEY = "auth";
	
	@Value("${jwt.secret}")
	private final String secret;
	
	@Value("${jwt.token-validity-in-seconds}")
	private final long tokenValidityInMilliseconds;
	
	private Key key;
	
	private final RedisConfig redisUtil;

	public TokenProvider(@Value("${jwt.secret}") String secret,
			@Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds, RedisConfig redisUtil) {
		this.secret = secret;
		this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
		this.redisUtil = redisUtil;

		log.info("TokenProvider : {}", this.secret);
		log.info("TokenProvider : {}", this.tokenValidityInMilliseconds);
	}

	@Override
	public void afterPropertiesSet() {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		log.info("afterPRopertiesSet keyByges : {}", keyBytes);
		log.info("hmacSHaKeyfor (keyBytes) :: {}", Keys.hmacShaKeyFor(keyBytes));
		this.key = Keys.hmacShaKeyFor(keyBytes);

		log.info("afterPRopertiesSet key : {}", this.key);
	}

	public String createToken(Authentication authentication) {
		String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		long now = (new Date()).getTime();
		Date validity = new Date(now + this.tokenValidityInMilliseconds);

		return Jwts.builder().setSubject(authentication.getName()).claim(AUTHORITIES_KEY, authorities)
				.signWith(SignatureAlgorithm.HS512, key).setExpiration(validity).compact();
	}

	public Authentication getAuthentication(String token) {
		Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();

		Collection<? extends GrantedAuthority> authorities = Arrays
				.stream(claims.get(AUTHORITIES_KEY).toString().split(",")).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

		User principal = new User(claims.getSubject(), "", authorities);

		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}

	public boolean validateToken(String token) {
//      try {
//         Jwts.parser().setSigningKey(key).parseClaimsJws(token);
//         return true;
//      } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
//         log.info("잘못된 JWT 서명입니다.");
//      } catch (ExpiredJwtException e) {
//         log.info("만료된 JWT 토큰입니다.");
//      } catch (UnsupportedJwtException e) {
//         log.info("지원되지 않는 JWT 토큰입니다.");
//      } catch (IllegalArgumentException e) {
//         log.info("JWT 토큰이 잘못되었습니다.");
//      }
//      return false;

		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			log.info("validate 들어옴");
			if (redisUtil.redisTemplate().hasKey(token)) {
				throw new UnauthorizedException("이미 탈퇴한 회원입니다");
			}
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			log.info("잘못된 JWT 서명입니다.");
		} catch (ExpiredJwtException e) {
			log.info("만료된 JWT 토큰입니다.");
		} catch (UnsupportedJwtException e) {
			log.info("지원되지 않는 JWT 토큰입니다.");
		} catch (IllegalArgumentException e) {
			log.info("JWT 토큰이 잘못되었습니다.");
		} catch (UnauthorizedException e) {
			log.info("이미 탈퇴한 회원입니다.");
		}
		return false;
	}

//	public ResponseCookie generateJwtCookie(BackOfficeUserDetails userPrincipal) {
//		String jwt = generateTokenFromUsername(userPrincipal.getUsername());
//		return generateCookie(jwtCookie, jwt, "/backoffice");
//	}
//	
//	public String generateTokenFromUsername(String username) {
//		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
//				.setExpiration(new Date((new Date()).getTime() + tokenValidityInMilliseconds))
//				.signWith(SignatureAlgorithm.HS512, secret).compact();
//	}
//
//	private ResponseCookie generateCookie(String name, String value, String path) {
//		ResponseCookie cookie = ResponseCookie.from(name, value).path(path).maxAge(24 * 60 * 60).httpOnly(true).build();
//		return cookie;
//	}
}