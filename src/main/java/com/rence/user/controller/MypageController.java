/**
 * @author 강경석
 * 마이페이지에 관련된 전반적 기술을 처리하는 컨트롤러
 * 마이페이지의 페이징
 * 회원 탈퇴
 *
 */
package com.rence.user.controller;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rence.user.model.MyPageReserveListDto;
import com.rence.user.model.UserDto;
import com.rence.user.model.UserMypageDto;
import com.rence.user.service.UserFileuploadService;
import com.rence.user.service.UserMypageSerivice;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "마이페이지 컨트롤러")
@Slf4j
@RestController
@RequestMapping("/rence")
public class MypageController {

	@Autowired
	UserMypageSerivice service;

	@Autowired
	UserFileuploadService fileuploadService;

	@Autowired
	HttpSession session;

	// 자동 개행 및 줄 바꿈 (new Gson은 일자로 나옴)
	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	// **********************
	// 마이페이지
	// **********************
	@ApiOperation(value = "마이페이지", notes = "마이페이지 입니다.")
	@GetMapping("/go_my_page")
	public String go_my_page(HttpServletRequest request, HttpServletResponse response) {
		log.info("go_my_page()...");
		
		UserMypageDto umdto = service.user_mypage_select(request, response);

		
		String jsonObject = gson.toJson(umdto);
		return jsonObject;
	}

	// **********************
	// 마이페이지 - 비밀번호 수정
	// **********************
	@ApiOperation(value = "비밀번호 수정", notes = "비밀번호 수정입니다.")
	@PostMapping("/user_pw_updateOK")
	@ResponseBody
	public String user_pw_upddateOK(UserDto udto) {

		log.info("user_pw_upddateOK()...");
		log.info("result: {}", udto);

		Map<String, String> map = service.user_pw_updateOK(udto);


		String jsonObject = gson.toJson(map);
		return jsonObject;
	}

	// **********************
	// 마이페이지 - 비밀번호 수정 - 현재 비밀번호 확인(본인인증)
	// **********************
	@ApiOperation(value = "현재 비밀번호 확인(본인인증)", notes = "현재 비밀번호 확인(본인인증) 입니다.")
	@PostMapping("/check_now_pw")
	public String check_now_pw(UserDto udto) {
		log.info("check_now_pw()...");
		log.info("request: {}", udto);

		Map<String, String> map = service.check_now_pw(udto);
		String jsonObject = gson.toJson(map);

		return jsonObject;
	}

	// **********************
	// 마이페이지 -프로필 수정 AWS적용
	// **********************
	@ApiOperation(value = "프로필 수정", notes = "프로필 수정 입니다.")
	@RequestMapping(value = "/user_img_updateOK", method = RequestMethod.POST)
	public String user_img_updateOK(UserDto udto, HttpServletRequest request, MultipartHttpServletRequest mtfRequest,
			@RequestParam(value = "multipartFile") MultipartFile multipartFile_user) {
		log.info("user_img_updateOK()...");
		log.info("result: {}", udto);
		
		Map<String, String> map = service.user_img_updateOK(udto, request, mtfRequest,multipartFile_user);
		
		String jsonObject = gson.toJson(map);
		return jsonObject;
	}

	// **********************
	// 회원탈퇴
	// **********************
	@ApiOperation(value = "회원탈퇴", notes = "회원탈퇴 입니다.")
	@RequestMapping(value = "/secedeOK", method = RequestMethod.POST)
	@ResponseBody
	public String user_secedeOK(UserDto udto, HttpServletRequest request, HttpServletResponse response) {
		log.info("user_secedeOK()...");
		log.info("result: {}", udto);

		Map<String, String> map = service.user_secedeOK(udto, session, request, response );
		
		String jsonObject = gson.toJson(map);
		return jsonObject;
	}

	
	// **********************
	// 예약 리스트 이동
	// **********************
	@ApiOperation(value = "예약리스트", notes = "예약리스트 페이지입니다.")
	@GetMapping("/reserve_list")
	public String reserve_list_rsu(String time_point, String user_no,
			@RequestParam(value = "page", defaultValue = "1") Integer page) {

		log.info("reserve_list");
		log.info("current page: {}", page);
		
		
		Map<String, Object> map = service.reserve_list_rsu(time_point, user_no, page);
		
		String jsonObject = gson.toJson(map);
		return jsonObject;
	}

	// **********************
	// 마일리지 리스트
	// **********************
	@ApiOperation(value = "마일리지 리스트", notes = "마일리지 리스트 페이지입니다.")
	@GetMapping("/mileage")
	public String go_mileage(UserDto udto, Model model, HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "1") Integer page) {
	
		log.info("go_mileage()...");
		log.info("UserDto(사용자 고유번호): {}", udto);
		log.info("current page: {}", page);
		
		Map<String, Object> map = service.mileage_list_page(udto, page);
		


		String jsonObject = gson.toJson(map);
		return jsonObject;


	}

	// **********************	// 마일리지 리스트 - searchKey	// **********************	@ApiOperation(value = "마일리지 조건리스트", notes = "마일리지 조건리스트 페이지입니다.")	@GetMapping("/mileage_search_list")	public String go_mileage_search_list(UserDto udto, Model model, HttpServletRequest request, String searchKey,			@RequestParam(value = "page", defaultValue = "1") Integer page) {
		
		log.info("go_mileage()...");
		log.info("UserDto(사용자 고유번호): {}", udto);
		log.info("current page: {}", page);
		
		Map<String, Object> map = service.mileage_list_page_searchKey(udto, page,searchKey);
		


		String jsonObject = gson.toJson(map);
		return jsonObject;
	}
	// **********************
	// 후기 리스트 이동
	// **********************
	@ApiOperation(value = "후기 리스트", notes = "후기 리스트 입니다.")
	@GetMapping("/review_list")
	public String review_list(String user_no, Model model,
			@RequestParam(value = "page", defaultValue = "1") Integer page) {
		
		log.info("review_list()...");
		log.info("user_no: " + user_no);
		Map<String, Object> map = service.review_list_page(user_no, page);
		
		String jsonObject = gson.toJson(map);
		return jsonObject;
	
	}

	// **********************
	// 마이페이지 - 문의 리스트
	// **********************
	@ApiOperation(value = "문의 리스트", notes = "문의 리스트 페이지입니다.")
	@GetMapping("/question_list")
	public String question_list(String user_no, Model model,
			@RequestParam(value = "page", defaultValue = "1") Integer page) {
		
		log.info("question_list()...");
		log.info("user_no: " + user_no);
		Map<String, Object> map = service.question_list_page(user_no, page);
		
		String jsonObject = gson.toJson(map);
		return jsonObject;
	
	}

}// end class
