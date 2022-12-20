package com.rence.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "로그인유뮤 체크 컨트롤러")
@Slf4j
@RestController
public class LoginCheckController {

	@Autowired
	HttpSession session;

	// 자동 개행 및 줄 바꿈 (new Gson은 일자로 나옴)
	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	// **********************
	// 로그인 여부 체크(세션 체크)
	// **********************
	@ApiOperation(value = "로그인 여부체크", notes = "로그인 여부체크 입니다")
	@GetMapping("/loginCheck")
	public String user_loginCheck() {
		log.info("user_loginCheck()....");

		Map<String, String> map = new HashMap<String, String>();

		log.info("(테스트 로그)session==== {}", session.getAttribute("user_id"));

		if (session.getAttribute("user_id") != null) {
			map.put("result", "1");
		} else {
			map.put("result", "0");
		}
		String jsonObject = gson.toJson(map);

		return jsonObject;
	}

}// end class
