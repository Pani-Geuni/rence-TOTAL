package com.rence.user.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rence.user.model.ReviewDto;
import com.rence.user.service.MypageMenuSerivice;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/rence")
public class MypageMenuController {

	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Autowired
	HttpSession session;

	@Autowired
	MypageMenuSerivice service;

	/**
	 * 상세 예약 페이지 이동 - 현재
	 */
	@GetMapping(value = "/reserve_info")
	public String reserve_info_rsu(String reserve_no, HttpServletRequest request) {
		log.info("reserve_info_rsu()...");

		Map<String, Object> map = service.reserve_info_rsu(reserve_no, request);

		String json = gson.toJson(map);
		return json;
	}

	/**
	 * 상세 예약 페이지 - 예약 취소
	 */
	@GetMapping(value = "/reserve_cancel")
	public String reserve_cancel_rsu(String reserve_no, String user_no) {
		log.info("reserve_cancel_rsu()...");

		Map<String, String> map = service.reserve_cancel_rsu(reserve_no, user_no);

		String json = gson.toJson(map);
		return json;
	}

	/**
	 * 예약 취소 후 결제 취소
	 * 
	 * @throws IOException
	 */
	@PostMapping(value = "/payment_cancel")
	public String payment_cancel_rsu(String reserve_no, Integer cancel_amount, String reason) throws IOException {
		log.info("payment_cancel_rsu()...");

		Map<String, String> map = service.payment_cancel_rsu(reserve_no, cancel_amount, reason);

		String json = gson.toJson(map);
		return json;
	}

	/**
	 * 상세 예약 페이지 이동 - 과거
	 */
	@GetMapping(value = "/reserved_info")
	public String reserved_info(String reserve_no, HttpServletRequest request) {
		log.info("reserved_info()...");

		Map<String, Object> map = service.reserved_info(reserve_no, request);

		String json = gson.toJson(map);
		return json;
	}

	/**
	 * 후기 추가 컨트롤러
	 */
	@ApiOperation(value = "후기 추가 컨트롤러", notes = "후기 추가 로직 컨트롤러")
	@GetMapping(value = "/insert_review")
	public String insert_review(ReviewDto dto) {
		log.info("insert_review()...");

		Map<String, String> map = service.insert_review(dto);

		String json = gson.toJson(map);
		return json;
	}

	/**
	 * 후기 내역 페이지 - 댓글 삭제
	 */
	@GetMapping(value = "/delete_review")
	public String delete_review(String user_no, String review_no) {
		log.info("delete_review()...");

		Map<String, String> map = service.delete_review(user_no, review_no);
		
		String json = gson.toJson(map);
		return json;
	}

	/**
	 * 문의 리스트 페이지 - 문의 삭제
	 */
	@GetMapping(value = "/delete_comment")
	public String delete_comment(String user_no, String comment_no) {
		log.info("delete_comment()...");

		Map<String, String> map = service.delete_comment(user_no, comment_no);

		String json = gson.toJson(map);
		return json;
	}

}// end class
