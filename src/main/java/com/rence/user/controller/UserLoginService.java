package com.rence.user.controller;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rence.security.jwt.RedisConfig;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserLoginService {

	private final RedisConfig redisUtil;

	public int logout(String accessToken) {
		redisUtil.redisTemplate().opsForValue().set(accessToken, "accessToken", 1800);
		return 1;
	}

}// end class
