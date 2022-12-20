/**
 * @author 전판근, 최진실
 */

package com.rence.master.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rence.backoffice.model.BackOfficeDTO;
import com.rence.backoffice.model.EmailVO;
import com.rence.master.common.MasterSendEmail;
import com.rence.master.model.MasterEntity;
import com.rence.master.service.MasterService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "마스터 컨트롤러")
@RestController
@RequestMapping("/master")
public class MasterController {

	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Autowired
	MasterService service;

	@Autowired
	MasterSendEmail sendEmail;

	@Autowired
	HttpSession session;

	/**
	 * 로그인 성공 처리
	 */
	@ApiOperation(value = "로그인 성공", notes = "로그인 성공")
	@PostMapping("/loginSuccess")
	public String master_loginOK(MasterEntity mvo, HttpServletResponse response) {

		Map<String, String> map = new HashMap<String, String>();

		map.put("result", "1");

		String json = gson.toJson(map);

		return json;
	}

	/**
	 * 로그인 실패 처리
	 */
	@ApiOperation(value = "로그인 실패", notes = "로그인 실패")
	@PostMapping("/loginFail")
	public String master_loginfail(HttpServletResponse response) {

		Map<String, String> map = new HashMap<String, String>();

		map.put("result", "0");

		String json = gson.toJson(map);

		return json;
	}
	
	/**
	 * 로그아웃 성공 처리
	 */
	@ApiOperation(value = "로그아웃 성공", notes = "로그아웃 성공")
	@PostMapping("/logoutOK")
	public String master_logoutOK() {

		Map<String, String> map = new HashMap<String, String>();

		map.put("result", "1");

		String json = gson.toJson(map);

		return json;
	}

	/**
	 * 마스터 메인 페이지 (호스트 가입 신청 리스트)
	 */
	@ApiOperation(value = "마스터 메인/호스트 가입 신청 리스트", notes = "호스트 가입 신청 리스트")
	@GetMapping("/main")
	public String master_main(@RequestParam(value = "page", defaultValue = "1") Integer page) {

		Map<String, Object> map = service.backoffice_applyList_selectAll(page);

		String json = gson.toJson(map);

		return json;
	}

	/**
	 * 마스터 메인 페이지 (호스트 가입 신청 리스트 - 승인)
	 * 
	 */
	@ApiOperation(value = "마스터 메인/호스트 가입 신청 승인 처리", notes = "호스트 가입 신청 승인 처리")
	@PostMapping("/grant")
	public String master_grant(BackOfficeDTO bvo, EmailVO evo) throws UnsupportedEncodingException {

		Map<String, String> map = service.master_grant(bvo,evo);

		String json = gson.toJson(map);

		return json;
	}

	/**
	 * 마스터 메인 페이지 (호스트 가입 신청 리스트 - 거절)
	 */
	@ApiOperation(value = "마스터 메인/호스트 가입 신청 거절 처리", notes = "호스트 가입 신청 거절 처리")
	@PostMapping("/refuse")
	public String master_refuse(BackOfficeDTO bvo, EmailVO evo) {
		
		Map<String, String> map = service.master_refuse(bvo,evo);

		String json = gson.toJson(map);

		return json;
	}

	/**
	 * 마스터 - 호스트 탈퇴 신청 리스트
	 */
	@ApiOperation(value = "마스터 - 호스트 탈퇴 신청 처리", notes = "호스트 탈퇴 신청 리스트")
	@GetMapping("/backoffice_end")
	public String backoffice_end(@RequestParam(value = "page", defaultValue = "1") Integer page) {

		Map<String, Object> map = service.backoffice_end(page);

		String json = gson.toJson(map);

		return json;

	}

	/**
	 * 마스터 - 호스트 탈퇴 승인
	 */
	@ApiOperation(value = "마스터 - 호스트 탈퇴 승인", notes = "호스트 탈퇴 승인")
	@PostMapping("/revoke")
	public String master_revoke(BackOfficeDTO bvo, EmailVO evo) {
		
		Map<String, String> map = service.master_revoke(bvo,evo);

		String json = gson.toJson(map);

		return json;
	}

	/**
	 * 백오피스 가입 상세 페이지
	 */
	@ApiOperation(value = "백오피스 가입 상세 페이지", notes = "백오피스 가입 상세 페이지")
	@GetMapping("/backoffice_apply_detail")
	public String master_backoffice_apply_detail(BackOfficeDTO bvo, String page) {
		
		Map<String, Object> map = service.master_backoffice_detail_selectOne(bvo, page);

		String json = gson.toJson(map);

		return json;
	}

	/**
	 * 백오피스 탈퇴 상세 페이지
	 */
	@ApiOperation(value = "백오피스 탈퇴 상세 페이지", notes = "백오피스 탈퇴 상세 페이지")
	@GetMapping("/backoffice_end_detail")
	public String master_backoffice_end_detail(BackOfficeDTO bvo, String page) {
		
		Map<String, Object> map = service.master_backoffice_detail_selectOne(bvo, page);

		String json = gson.toJson(map);

		return json;
	}

}
