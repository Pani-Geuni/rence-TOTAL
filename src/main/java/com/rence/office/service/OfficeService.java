package com.rence.office.service;

import java.text.ParseException;
import java.util.Map;

import com.rence.backoffice.model.BackOfficeDTO;
import com.rence.office.model.Comment_Dto;
import com.rence.office.model.OfficePaymentDto;
import com.rence.office.model.OfficeReserveDto;

public interface OfficeService {

	// 오피스(공간) 상세 페이지
	Map<String, Object> space_intruducePage(BackOfficeDTO bdto, String introduce_menu, Integer page);

	// 질문 페이징
	Map<String, Object> introduce_q_paging(BackOfficeDTO bdto, Integer page);

	// 문의 페이징
	Map<String, Object> introduce_r_paging(BackOfficeDTO bdto, Integer page);

	// 예약 가능여부 체크
	Map<String, Object> reserve_check(String backoffice_no, String room_no, String reserve_date);

	// 예약 가능 확인 여부 (오피스)
	Map<String, String> office_reserve_check_rsu(String backoffice_no, String room_no, String reserve_stime,
			String reserve_etime) throws ParseException;

	// 예약 하기
	Map<String, String> reserve_rsu(OfficeReserveDto rdto) throws ParseException;

	// space introduce OFFICE
	Map<String, Object> space_intruduce_office(BackOfficeDTO bdto, String introduce_menu, Integer page);

	// 공간 결제 페이지
	Map<String, Object> space_payment_rsu(OfficeReserveDto rdto) throws ParseException;

	// **********************
	// 공간 결제 페이지
	// **********************
	// 결제 컨트롤러
	Map<String, String> space_payment_rsu(OfficePaymentDto pdto);

	// 문의 추가 컨트롤러
	Map<String, String> insert_question(Comment_Dto dto);

	// 리스트 페이지
	Map<String, Object> list_page(String type, Integer page, String condition);

	// 리스트 페이지 페이징
	Map<String, Object> list_paging(String type, Integer page, String condition);

	// 검색 리스트 페이지 페이징
	Map<String, Object> search_list_paging(String type, String location, String searchWord, String condition,
			Integer page);

}
