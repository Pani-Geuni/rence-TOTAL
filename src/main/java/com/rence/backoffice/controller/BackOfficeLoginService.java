package com.rence.backoffice.controller;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rence.security.jwt.RedisConfig;

//import com.rence.security.jwt.RedisUtil;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BackOfficeLoginService {

//	private final RedisUtil redisUtil;
	private final RedisConfig redisUtil;

	public int logout(String accessToken) {
		redisUtil.redisTemplate().opsForValue().set(accessToken, "accessToken", 1800);
		return 1;
	}
}
