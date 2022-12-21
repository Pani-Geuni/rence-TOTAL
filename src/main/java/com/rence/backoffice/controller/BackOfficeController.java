/**
 * 
 * @author 최진실
 *
 */
package com.rence.backoffice.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rence.backoffice.model.AuthDTO;
import com.rence.backoffice.model.BackOfficeDTO;
import com.rence.backoffice.model.BackOfficeOperatingTimeDTO;
import com.rence.backoffice.model.EmailVO;
import com.rence.backoffice.service.BackOfficeFileService;
import com.rence.backoffice.service.BackOfficeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "백오피스 컨트롤러")
@RequestMapping("/backoffice")
public class BackOfficeController {

	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Autowired
	BackOfficeService service;

	@Autowired
	BackOfficeFileService fileService;

	@Autowired
	HttpSession session;
	
	/**
	 * 로그인 여부
	 */
	@ApiOperation(value = "대쉬보드 로그인 여부", notes = "대쉬보드 로그인 여부")
	@GetMapping("/loginCheck")
	public String dashboard_login_check() {

		Map<String, Object> map = service.login_check();

		String json = gson.toJson(map);

		return json;
	}
	
	/**
	 * 백오피스 신청 처리
	 * 
	 * @throws ParseException
	 */
	@ApiOperation(value = "호스트 신청 처리", notes = "호스트 신청 처리입니다.")
	@PostMapping("/insertOK")
	public String backoffice_insertOK(BackOfficeDTO vo, BackOfficeOperatingTimeDTO ovo, MultipartHttpServletRequest mtfRequest, @RequestParam(value = "multipartFile_room" , required = false) MultipartFile multipartFile_room) throws ParseException {

		vo = fileService.backoffice_image_upload(vo, mtfRequest, multipartFile_room);

		Map<String, String> map = service.insertOK(vo, ovo);

		String json = gson.toJson(map);

		return json;
	}

	/**
	 * 이메일 인증번호 요청
	 */
	@ApiOperation(value = "이메일 인증번호 요청", notes = "호스트 신청시, 이메일 인증번호 요청 페이지입니다.")
	@GetMapping("/auth")
	public String backoffice_auth(AuthDTO avo, BackOfficeDTO bvo, EmailVO evo) {

		Map<String, String> map = service.backoffice_auth(avo, bvo, evo);

		String json = gson.toJson(map);

		return json;
	}

	/**
	 * 이메일 인증번호 확인
	 */
	@ApiOperation(value = "이메일 인증번호 확인", notes = "호스트 신청시, 이메일 인증번호 확인입니다.")
	@PostMapping("/authOK")
	@Transactional
	public String backoffice_authOK(String backoffice_email, String auth_code) {

		Map<String, String> map = service.backoffice_authOK(backoffice_email, auth_code);

		String json = gson.toJson(map);

		return json;
	}

	/**
	 * 로그인 성공 처리
	 * @throws UnsupportedEncodingException 
	 */
	@ApiOperation(value = "로그인 성공", notes = "로그인 성공")
	@PostMapping("/loginSuccess")
	public String backoffice_loginOK(@RequestParam String username, HttpServletResponse response) throws UnsupportedEncodingException {

		Map<String, String> map = service.backoffice_loginOK(username, response);

		String json = gson.toJson(map);

		return json;
	}

	/**
	 * 로그인 실패 처리
	 */
	@ApiOperation(value = "로그인 실패", notes = "로그인 실패")
	@PostMapping("/loginFail")
	public String backoffice_loginfail(HttpServletResponse response) {

		Map<String, String> map = new HashMap<String, String>();

		map.put("result", "0");

		String json = gson.toJson(map);

		return json;
	}

	/**
	 * 로그아웃 처리
	 */
	@ApiOperation(value = "로그아웃", notes = "로그아웃")
	@GetMapping("/logoutOK")
	public String backoffice_logoutOK(HttpServletRequest request, HttpServletResponse response) {

		Map<String, String> map = service.backoffice_logoutOK(request,response);
		
		String json = gson.toJson(map);

		return json;
	}

	/**
	 * 비밀번호 초기화(찾기), 이메일로 임시 비밀번호 전송
	 */
	@ApiOperation(value = "비밀번호 찾기", notes = "비밀번호 찾기시, 이메일로 임시 비밀번호 전송")
	@GetMapping("/reset_pw")
	public String backoffice_reset_pw(BackOfficeDTO bvo, EmailVO evo) {

		Map<String, String> map = service.backoffice_reset_pw(bvo, evo);

		String json = gson.toJson(map);

		return json;
	}

	/**
	 * 비밀번호 초기화 페이지
	 */
	@ApiOperation(value = "비밀번호 초기화", notes = "호스트 비밀번호 변경, 이메일로 전송된 비밀번호 재설정")
	@GetMapping("/setting_pw")
	public String backoffice_setting_pw(BackOfficeDTO bvo) {

		Map<String, String> map = new HashMap<String, String>();

		map.put("vo", bvo.getBackoffice_no());

		String json = gson.toJson(map);

		return json;
	}

	/**
	 * 비밀번호 초기화 완료
	 * @throws UnsupportedEncodingException 
	 */
	@ApiOperation(value = "비밀번호 초기화 처리", notes = "호스트 비밀번호 변경, 이메일로 전송된 비밀번호 재설정")
	@PostMapping("/settingOK_pw")
	public String backoffice_settingOK_pw(BackOfficeDTO bvo, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		Map<String, String> map = service.backoffice_settingOK_pw(bvo, request, response);

		String json = gson.toJson(map);

		return json;
	}

}
