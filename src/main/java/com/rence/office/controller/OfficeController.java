package com.rence.office.controller;

import java.text.ParseException;
import java.util.Map;

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
import com.rence.office.model.Comment_Dto;
import com.rence.office.model.OfficePaymentDto;
import com.rence.office.model.OfficeReserveDto;
import com.rence.office.service.OfficeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "오피스 컨트롤러")
@RequestMapping("/office")
public class OfficeController {

	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Autowired
	OfficeService service;

	@Autowired
	HttpSession session;

	/*
	 * 오피스(공간) 상세 페이지
	 */
	@ApiOperation(value = "공간 소개 페이지 로드 (데스크,회의실)", notes = "데스크 / 회의실 공간 소개 페이지 로드하는 컨트롤러")
	@GetMapping(value = "/space_introduce")
	public String space_intruduce(BackOfficeDTO bdto, String introduce_menu,
			@RequestParam(value = "page", defaultValue = "1") Integer page) {
		log.info("space_intruduce()....");
		Map<String, Object> map = service.space_intruducePage(bdto, introduce_menu, page);

		// 분기결과 map gson으로 json변환
		String json = gson.toJson(map);

		return json;
	}

	@GetMapping("/introduce_q_paging")
	public String introduce_q_paging(BackOfficeDTO bdto,
			@RequestParam(value = "page", defaultValue = "1") Integer page) {
		log.info("introduce_q_paging()....");

		Map<String, Object> map = service.introduce_q_paging(bdto, page);

		String introduce_q_paging = gson.toJson(map);
		return introduce_q_paging;
	}

	@GetMapping(value = "/introduce_r_paging")
	public String introduce_r_paging(BackOfficeDTO bdto, @RequestParam(value = "page", defaultValue = "1") Integer page) {
		log.info("introduce_r_paging()....");

		Map<String, Object> map = service.introduce_r_paging(bdto, page);

		String introduce_r_paging = gson.toJson(map);
		return introduce_r_paging;
	}

	// ***************
	// 예약 가능 여부 확인
	// ***************
	@SuppressWarnings("unlikely-arg-type")
	@ApiOperation(value = "예약 가능 확인 여부 (데스크,회의실)", notes = "선택한 공간과 시간에 예약이 존재하는지 확인하는 컨트롤러")
	@GetMapping(value = "/reserve_check")
	public String reserve_check_rsu(String backoffice_no, String room_no, String reserve_date) {
		log.info("reserve_check_rsu()...");

		Map<String, Object> map = service.reserve_check(backoffice_no, room_no, reserve_date);

		String json = gson.toJson(map);
		return json;
	}

	@SuppressWarnings("unlikely-arg-type")
	@ApiOperation(value = "예약 가능 확인 여부 (오피스)", notes = "선택한 공간과 시간에 예약이 존재하는지 확인하는 컨트롤러")
	@GetMapping(value = "/office_reserve_check")
	public String office_reserve_check_rsu(String backoffice_no, String room_no, String reserve_stime,
			String reserve_etime) throws ParseException {
		log.info("office_reserve_check_rsu()...");
		log.info("{} {} {} {}", backoffice_no, room_no, reserve_stime, reserve_etime);

		Map<String, String> map = service.office_reserve_check_rsu(backoffice_no, room_no, reserve_stime,
				reserve_etime);

		String json = gson.toJson(map);
		return json;
	}

	// *******
	// 예약 하기
	// *******
	@ApiOperation(value = "예약 (데스크,회의실)", notes = "예약을 신청한 뒤, 결과값에 따라 결제 페이지로 이동")
	@GetMapping(value = "/reserve")
	public String reserve_rsu(OfficeReserveDto rdto) throws ParseException {
		log.info("reserve_rsu()...");

		Map<String, String> map = service.reserve_rsu(rdto);

		String json = gson.toJson(map);
		return json;
	}

	// **********************
	// space introduce OFFICE
	// **********************
	@ApiOperation(value = "공간 소개 페이지 로드 (오피스)", notes = "오피스 공간 소개 페이지 로드하는 컨트롤러")
	@GetMapping(value = "/space_introduce_office")
	public String space_intruduce_office(BackOfficeDTO bdto, String introduce_menu,
			@RequestParam(value = "page", defaultValue = "1") Integer page) {

		log.info("space_intruduce_office()...");

		Map<String, Object> map = service.space_intruduce_office(bdto, introduce_menu, page);

		String json = gson.toJson(map);
		return json;
	}

	// **********************
	// 공간 결제 페이지
	// **********************
	@ApiOperation(value = "결제 페이지 로드 컨트롤러", notes = "예약 및 결제하는 페이지 데이터 불러오는 컨트롤러")
	@GetMapping(value = "/payment")
	public String space_payment_rsu(OfficeReserveDto rdto) throws ParseException {
		log.info("space_payment_rsu().....");

		Map<String, Object> map = service.space_payment_rsu(rdto);

		String json = gson.toJson(map);
		return json;
	}

	@ApiOperation(value = "결제 컨트롤러", notes = "예약 및 결제하는 페이지 결제 로직 컨트롤러")
	@PostMapping(value = "/paymentOK")
	public String reserve_paymentOK_rsu(OfficePaymentDto pdto) {
		log.info("reserve_paymentOK_rsu().....");
		Map<String, String> map = service.space_payment_rsu(pdto);

		String json = gson.toJson(map);
		return json;

	}

	@ApiOperation(value = "문의 추가 컨트롤러", notes = "문의 추가 로직 컨트롤러")
	@GetMapping(value = "/insert_question")
	public String insert_question(Comment_Dto dto) {

		log.info("insert_question().....");
		Map<String, String> map = service.insert_question(dto);

		String json = gson.toJson(map);
		return json;
	}

	// 리스트 페이지
	@ApiOperation(value = "리스트 페이지 로드 컨트롤러", notes = "타입에 따른 리스트 페이지를 로드하는 컨트롤러")
	@GetMapping(value = "/list_page")
	public String list_page(String type, Integer page, String condition) {
		log.info("list_page()...");

		Map<String, Object> map = service.list_page(type, page, condition);

		String json = gson.toJson(map);
		return json;

	}

	// 리스트 페이지 페이징
	@ApiOperation(value = "리스트 페이지 페이징 컨트롤러", notes = "타입에 따른 리스트 페이지를 페이징하는 컨트롤러")
	@GetMapping(value = "/list_paging")
	public String list_paging(String type, Integer page, String condition) {
		log.info("list_paging()...");
		log.info("{}...{}...{}", type, page, condition);

		Map<String, Object> map = service.list_paging(type, page, condition);

		Gson gson = new Gson();
		String json = gson.toJson(map);

		return json;
	}

	// 검색 리스트 페이지 페이징
	@ApiOperation(value = "검색 리스트 페이지 페이징 컨트롤러", notes = "검색 리스트 페이지를 페이징하는 컨트롤러")
	@GetMapping(value = "/search_list_paging")
	public String search_list_paging(String type, String location, String searchWord, String condition, Integer page) {
		log.info("search_list_paging()...");
		log.info("search_list()...");

		Map<String, Object> map = service.search_list_paging(type, location, searchWord, condition, page);

		Gson gson = new Gson();
		String json = gson.toJson(map);

		return json;
	}
}// end class