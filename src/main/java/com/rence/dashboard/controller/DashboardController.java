/**
 * 
 * @author 최진실
 *
 */
package com.rence.dashboard.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

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
import com.rence.backoffice.model.BackOfficeDTO;
import com.rence.backoffice.model.BackOfficeOperatingTimeDTO;
import com.rence.backoffice.service.BackOfficeFileService;
import com.rence.dashboard.model.CommentDTO;
import com.rence.dashboard.model.RoomDTO;
import com.rence.dashboard.service.DashboardService;
import com.rence.dashboard.service.HostPaymentCancelService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "대쉬보드 컨트롤러")
@RequestMapping("/backoffice/dash")
public class DashboardController {

	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Autowired
	DashboardService service;

	@Autowired
	BackOfficeFileService fileService;

	@Autowired
	HostPaymentCancelService cancelService;

	/**
	 * 대쉬보드 메인
	 */
	@ApiOperation(value = "대쉬보드 메인", notes = "대쉬보드 메인 페이지")
	@GetMapping("/main")
	public String dashboard_main_rsu(String backoffice_no) {

		Map<String, Object> map = service.dashboard_main(backoffice_no);

		String json = gson.toJson(map);

		return json;
	}

	/**
	 * 공간관리 - 리스트
	 */
	@ApiOperation(value = "공간 리스트", notes = "대쉬보드 공간 관리 페이지")
	@GetMapping("/room")
	public String dashboard_room_list(String backoffice_no, @RequestParam(value = "page", defaultValue = "1") Integer page) {

		Map<String, Object> map = service.dashboard_room_list(backoffice_no,page);

		String json = gson.toJson(map);

		return json;

	}

	/**
	 * 공간관리  - 공간 추가 팝업
	 */
	@ApiOperation(value = "공간 추가", notes = "대쉬보드 공간 관리 페이지")
	@GetMapping("/insert_room")
	public String backoffice_insert_room(String backoffice_no) {
		
		Map<String, Object> map = service.backoffice_insert_room(backoffice_no);

		String json = gson.toJson(map);

		return json;

	}

	/**
	 * 공간관리 - 공간 추가
	 */
	@ApiOperation(value = "공간 추가 처리", notes = "대쉬보드 공간 관리 페이지")
	@PostMapping("/insertOK_room")
	public String backoffice_insertOK_room(RoomDTO rvo, String backoffice_no) {
		
		Map<String, String> map = service.backoffice_insertOK_room(rvo, backoffice_no);

		String json = gson.toJson(map);

		return json;

	}

	/**
	 * 공간관리 - 공간 수정 팝업
	 */
	@ApiOperation(value = "공간 수정 팝업", notes = "대쉬보드 공간 관리 페이지")
	@GetMapping("/update_room")
	public String backoffice_update_room(String backoffice_no, String room_no) {

		Map<String, Object> map = service.backoffice_update_room(backoffice_no, room_no);

		String json = gson.toJson(map);

		return json;
		
	}

	/**
	 * 공간관리 - 공간 수정
	 */
	@ApiOperation(value = "공간 수정 처리", notes = "대쉬보드 공간 관리 페이지")
	@PostMapping("/updateOK_room")
	public String backoffice_updateOK_room(RoomDTO rvo, String backoffice_no) {

		Map<String, String> map = service.backoffice_updateOK_room(backoffice_no, rvo);

		String json = gson.toJson(map);

		return json;
		
	}

	/**
	 * 공간관리 - 공간 삭제
	 */
	@ApiOperation(value = "공간 삭제", notes = "대쉬보드 공간 관리 페이지")
	@PostMapping("/deleteOK_room")
	@Transactional
	public String backoffice_deleteOK_room_rsu(String backoffice_no, String room_no) {

		Map<String, String> map = service.backoffice_deleteOK_room(backoffice_no, room_no);

		String json = gson.toJson(map);

		return json;
		
	}

	/**
	 * 공간관리 - 문의(리스트)
	 */
	@ApiOperation(value = "문의 리스트", notes = "대쉬보드 공간 관리 페이지 - 문의")
	@GetMapping("/qna")
	public String dashboard_qna(String backoffice_no, @RequestParam(value = "page", defaultValue = "1") Integer page) {

		Map<String, Object> map = service.dashboard_qna(backoffice_no, page);

		String json = gson.toJson(map);

		return json;
	}

	/**
	 * 공간관리 - 답변 작성 팝업
	 */
	@ApiOperation(value = "답변 작성", notes = "대쉬보드 공간 관리 페이지 - 문의(답변)")
	@GetMapping("/insert_comment")
	public String backoffice_insert_comment(String backoffice_no, String room_no, String comment_no) {

		Map<String, Object> map = service.backoffice_insert_comment(backoffice_no, room_no, comment_no);

		String json = gson.toJson(map);

		return json;
		
	}

	/**
	 * 공간관리 - 답변 작성
	 */
	@ApiOperation(value = "답변 작성 처리", notes = "대쉬보드 공간 관리 페이지 - 문의(답변)")
	@PostMapping("/insertOK_comment")
	public String backoffice_insertOK_comment(String backoffice_no, CommentDTO cvo, String comment_no) {

		Map<String, String> map = service.backoffice_insertOK_comment(backoffice_no, cvo, comment_no);

		String json = gson.toJson(map);

		return json;
		
	}

	/**
	 * 공간관리 - 답변 삭제
	 */
	@ApiOperation(value = "답변 삭제", notes = "대쉬보드 공간 관리 페이지 - 문의(답변)")
	@PostMapping("/deleteOK_comment")
	public String backoffice_deleteOK_comment(String backoffice_no, String comment_no, String mother_no) {

		Map<String, String> map = service.backoffice_deleteOK_comment(backoffice_no, comment_no, mother_no);

		String json = gson.toJson(map);

		return json;
		
	}

	/**
	 * 공간관리 - 리뷰 (리스트)
	 */
	@ApiOperation(value = "리뷰 리스트", notes = "대쉬보드 공간 관리 페이지 - 리뷰")
	@GetMapping("/review")
	public String dashboard_review(String backoffice_no, @RequestParam(value = "page", defaultValue = "1") Integer page) {

		Map<String, Object> map = service.dashboard_review(backoffice_no, page);

		String json = gson.toJson(map);

		return json;
		
	}

	/**
	 * 예약 관리 - 리스트
	 */
	@ApiOperation(value = "예약 리스트", notes = "대쉬보드 예약 관리 페이지 - 리스트")
	@GetMapping("/reserve")
	public String dashboard_reserve_rsu(String backoffice_no, String reserve_state, @RequestParam(value = "page", defaultValue = "1") Integer page) {

		Map<String, Object> map = service.dashboard_reserve(backoffice_no, reserve_state, page);

		String json = gson.toJson(map);

		return json;
	}

	/**
	 * 예약 관리 - 리스트(검색)
	 */
	@ApiOperation(value = "예약 리스트 검색", notes = "대쉬보드 예약 관리 페이지 - 리스트 검색")
	@GetMapping("/search_reserve")
	public String dashboard_reserve_search_rsu(String backoffice_no, String searchword, String reserve_state, @RequestParam(value = "page", defaultValue = "1") Integer page) throws UnsupportedEncodingException {

		Map<String, Object> map = service.dashboard_reserve_search(backoffice_no, searchword, reserve_state, page);

		String json = gson.toJson(map);

		return json;
		
	}

	/**
	 * 정산 관리 - 리스트
	 */
	@ApiOperation(value = "정산 관리 리스트", notes = "대쉬보드 정산 관리 페이지 - 리스트")
	@GetMapping("/day_sales")
	public String dashboard_sales_day_rsu(String backoffice_no, String sales_date, @RequestParam(value = "page", defaultValue = "1") Integer page) {

		Map<String, Object> map = service.dashboard_sales_day(backoffice_no, sales_date, page);

		String json = gson.toJson(map);

		return json;
	}

	/**
	 * 정산 관리 - 정산 상태 변경
	 */
	@ApiOperation(value = "정산 상태 변경", notes = "대쉬보드 정산 관리 페이지 - 정산 상태 변경")
	@PostMapping("/updateOK_sales")
	public String backoffice_updateOK_sales(String backoffice_no, String room_no, String payment_no) {

		Map<String, String> map = service.backoffice_updateOK_sales(backoffice_no, room_no, payment_no);

		String json = gson.toJson(map);

		return json;
		
	}

	/**
	 * 환경설정 - 페이지 출력
	 */
	@ApiOperation(value = "환경설정", notes = "대쉬보드 환경설정 페이지")
	@GetMapping("/settings")
	public String backoffice_settings(BackOfficeDTO bvo) {

		Map<String, Object> map = service.backoffice_settings(bvo);

		String json = gson.toJson(map);

		return json;
		
	}

	/**
	 * 환경설정 - 비밀번호 수정
	 */
	@ApiOperation(value = "비밀번호 변경", notes = "대쉬보드 환경설정 페이지 - 비밀번호 변경")
	@GetMapping("/update_pw")
	public String backoffice_update_pw(BackOfficeDTO bvo) {

		Map<String, String> map = service.backoffice_update_pw(bvo);

		String json = gson.toJson(map);

		return json;
		
	}

	/**
	 * 환경설정 - 업체 탈퇴 요청
	 */
	@ApiOperation(value = "업체 탈퇴 요청", notes = "대쉬보드 환경설정 페이지 - 업체 탈퇴 요청")
	@PostMapping("/setting_delete")
	public String backoffice_setting_delete_rsu(BackOfficeDTO bvo) {

		Map<String, String> map = service.backoffice_setting_delete_rsu(bvo);

		String json = gson.toJson(map);

		return json;
		
	}

	/**
	 * 환경설정 - 정보 변경
	 */
	@ApiOperation(value = "업체 정보 변경 폼", notes = "대쉬보드 환경설정 페이지 - 업체 정보 변경")
	@GetMapping("/update_host")
	public String backoffice_update_host(BackOfficeDTO bvo) {

		Map<String, Object> map = service.backoffice_update_host(bvo);

		String json = gson.toJson(map);

		return json;
		
	}

	/**
	 * 환경설정 - 정보 변경
	 */
	@ApiOperation(value = "업체 정보 변경 처리", notes = "대쉬보드 환경설정 페이지 - 업체 정보 변경")
	@PostMapping("/updateOK_host")
	public String backoffice_updateOK_host(BackOfficeDTO bvo, BackOfficeOperatingTimeDTO ovo, MultipartHttpServletRequest mtfRequest, @RequestParam(value = "multipartFile_room") MultipartFile multipartFile_room) {

		BackOfficeDTO bvo2 = service.backoffice_setting_selectOne(bvo);
		if(!bvo.getBackoffice_image().equals(bvo2.getBackoffice_image())) {
			// 이미지 파일
//			bvo = fileService.backoffice_image_upload(bvo, mtfRequest, multipartFile_room);
			log.info("filupload room:{}", bvo);
		} 
		
		Map<String, String> map = service.backoffice_updateOK_host(bvo,ovo);

		String json = gson.toJson(map);

		return json;
		
	}

	/**
	 * 일정 관리 - 페이지
	 */
	@ApiOperation(value = "일정 관리", notes = "대쉬보드 - 일정 관리")
	@GetMapping("/schedule")
	public String backoffice_schedule_rsu(String backoffice_no) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("backoffice_no", backoffice_no);

		String json = gson.toJson(map);

		return json;

	}

	/**
	 * 일정 관리 - 날짜, 시간 선택 후
	 */
	@ApiOperation(value = "일정 관리", notes = "대쉬보드 - 일정 관리")
	@GetMapping("/schedule_research")
	public String backoffice_schedule_research_rsu(String backoffice_no, String not_sdate, String not_edate,
			String not_stime, String not_etime, String off_type, Integer page) {

		Map<String, Object> map = service.backoffice_schedule_research(backoffice_no, not_sdate, not_edate, not_stime, not_etime, off_type, page);

		String json = gson.toJson(map);

		return json;
		
	}

	/**
	 * 일정 관리 - 날짜, 시간 선택 후 *************스크롤 페이징*****************
	 */
	@ApiOperation(value = "일정 관리", notes = "대쉬보드 - 일정 관리")
	@GetMapping("/schedule_research_paging")
	public String backoffice_schedule_research_paging_rsu(String backoffice_no, String not_sdate, String not_edate, String not_stime, String not_etime, String off_type, Integer page) {

		Map<String, Object> map = service.backoffice_schedule_research_paging(backoffice_no, not_sdate, not_edate, not_stime, not_etime, off_type, page);

		String json = gson.toJson(map);

		return json;
		
	}

	/**
	 * 일정 관리 - 날짜, 시간 선택 후, 휴무, 브레이크타임 설정
	 */
	@ApiOperation(value = "일정 관리 - 휴무, 브레이크타임 설정", notes = "대쉬보드 - 일정 관리")
	@PostMapping("/scheduleOK")
	public String backoffice_scheduleOK_rsu(String backoffice_no, String not_sdate, String not_edate, String not_stime, String not_etime, String room_no, String off_type) throws ParseException {

		Map<String, String> map = service.backoffice_schedule_research_paging(backoffice_no, not_sdate, not_edate, not_stime, not_etime, room_no, off_type);

		String json = gson.toJson(map);

		return json;
		
	}

	/**
	 * 일정 관리 - 해당 날짜, 시간에 예약자 리스트
	 */
	@ApiOperation(value = "예약자 리스트", notes = "대쉬보드 - 예약자 리스트")
	@GetMapping("/reservation")
	public String backoffice_reservation_rsu(String backoffice_no, String room_no, String not_sdate, String not_edate,
			String not_stime, String not_etime, String off_type, Integer page) {

		Map<String, Object> map = service.backoffice_reservation(backoffice_no, room_no, not_sdate, not_edate, not_stime, not_etime, off_type, page);

		String json = gson.toJson(map);

		return json;
		
	}

	/**
	 * 일정 관리 - 해당 날짜, 시간에 예약자 리스트 *************스크롤 페이징**************
	 */
	@ApiOperation(value = "예약자 리스트", notes = "대쉬보드 - 예약자 리스트")
	@GetMapping("/reservation_paging")
	public String backoffice_reservation_paging_rsu(String backoffice_no, String room_no, String not_sdate, String not_edate, String not_stime, String not_etime, String off_type, Integer page) {
		
		Map<String, Object> map = service.backoffice_reservation_paging(backoffice_no, room_no, not_sdate, not_edate, not_stime, not_etime, off_type, page);

		String json = gson.toJson(map);

		return json;
		
	}

	/**
	 * 일정 관리 - 예약취소
	 * 
	 * @throws IOException
	 */
	@ApiOperation(value = "일정 관리 - 예약 취소", notes = "대쉬보드 - 일정 관리")
	@PostMapping("/reservation_cancel")
	public String backoffice_reservation_cancel_rsu(String backoffice_no, String reserve_no, String user_no, String user_email, String reserve_stime, String reserve_etime) throws ParseException, IOException {

		Map<String, Object> map = cancelService.backoffice_reservation_cancel(backoffice_no, reserve_no, user_no, user_email, reserve_stime, reserve_etime);

		String json = gson.toJson(map);

		return json;
		
	}

	/**
	 * 일정 관리 - 백오피스 휴무 일정
	 */
	@ApiOperation(value = "일정 관리 - 휴무 일정", notes = "대쉬보드 - 일정 관리")
	@GetMapping("/schedule_calendar")
	public String backoffice_schedule_calendar(String backoffice_no) throws ParseException {

		Map<String, Object> map = service.backoffice_schedule_calendar(backoffice_no);

		String json = gson.toJson(map);

		return json;
		
	}

	/**
	 * 일정 관리 - 휴무/브레이크타임 취소
	 */
	@ApiOperation(value = "일정 관리 - 일정 취소", notes = "대쉬보드 - 일정 관리")
	@PostMapping("/schedule_cancel")
	public String backoffice_schedule_cancel(String backoffice_no, String schedule_no) throws ParseException {

		Map<String, Object> map = service.backoffice_schedule_cancel(backoffice_no, schedule_no);

		String json = gson.toJson(map);

		return json;
		
	}

}
