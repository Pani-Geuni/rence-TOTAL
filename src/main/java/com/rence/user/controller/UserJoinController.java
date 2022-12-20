/**
* @author 강경석
* 회원가입 처리 컨트롤러
*/

package com.rence.user.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rence.backoffice.model.AuthDTO;
import com.rence.user.model.EmailVO;
import com.rence.user.model.UserDto;
import com.rence.user.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "유저회원가입 컨트롤러")
@Slf4j
@RestController
@RequestMapping("/rence")
public class UserJoinController {

	@Autowired
	UserService service;
//	@Autowired
//	UserFileuploadService fileuploadService;
	@Autowired
	ServletContext context;
//	@Autowired
//	UserSendEmail authSendEmail;

	// 자동 개행 및 줄 바꿈 (new Gson으로 하면 일자로 나옴)
	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	// 데이터 표현 타입 설정
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	// **********************
	// 이메일 인증번호 요청
	// 이메일 중복 체크
	// 0: 이메일 인증 실패 1: 이메일 인증성공 2: 중복체크에 걸림(가입된 이메일) 3: 전송후 2분안에 시도시
	// **********************
	@ApiOperation(value = "이메일 인증번호요청", notes = "이메일 인증번호요청 입니다.")
	@PostMapping("/user_auth")
	@Transactional
	public String user_auth(AuthDTO adto, UserDto udto, EmailVO evo) {
		log.info("Welcome user_auth");
		log.info("{}", udto);

		Map<String, String> map = new HashMap<String, String>();
		
		String emailCheck_result = service.user_EmailCheckOK(udto, adto, evo);
		
		map.put("authNum", emailCheck_result);

		
		String jsonObject = gson.toJson(map);
		return jsonObject;
	}

	
	// **********************
	// 이메일 인증번호 확인
	// **********************
	@ApiOperation(value = "이메일 인증번호확인", notes = "이메일 인증번호확인 입니다.")
	@PostMapping("/user_authOK")
//	@Transactional
	public String user_authOK(String user_email, String email_code) {

		log.info("Welcome user_authOK");
		log.info("user_email: {}", user_email);
		log.info("email_code: {}", email_code);
		
		String user_auth_result = service.user_AuthOK(user_email, email_code);
		Map<String, String> map = new HashMap<String, String>();
		

		map.put("result", user_auth_result);

		String jsonObject = gson.toJson(map);
		return jsonObject;
	}

	
	// **********************
	// 아이디 중복 체크
	// **********************
	@ApiOperation(value = "아이디 중복 체크", notes = "아이디 중복 체크 입니다.")
	@PostMapping("/user_idCheckOK")
	@ResponseBody
	public String user_idCheckOK(UserDto udto) {
		log.info("Welcome! user_idCheckOK");
		log.info("result: {}", udto);

		Map<String, String> map = new HashMap<String, String>();

		String idCheck_result = service.idCheckOK(udto);
	
		
		map.put("result", idCheck_result);

		String jsonObject = gson.toJson(map);
		return jsonObject;
	}

	// **********************
	// 회원가입완료
	// **********************
	@ApiOperation(value = "회원가입 성공", notes = "회원가입 성공 입니다.")
	@PostMapping("/joinOK")
	@ResponseBody
	public String user_joinOK(UserDto udto) {
		log.info("Welcome! user_joinOK");
		log.info("result: {}", udto);
		
		// insert(성공시 1)
		Map<String, String> map = service.user_insertOK(udto);
				
		String jsonObject = gson.toJson(map);
		return jsonObject;
	}
}// end class
