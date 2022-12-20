package com.rence.user.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.rence.user.model.ReviewDto;

public interface MypageMenuSerivice {

	// 상세 예약 페이지 이동 - 현재
	Map<String, Object> reserve_info_rsu(String reserve_no, HttpServletRequest request);

	// 상세 예약 페이지 - 예약 취소
	Map<String, String> reserve_cancel_rsu(String reserve_no, String user_no);

	// 예약 취소 후 결제 취소
	Map<String, String> payment_cancel_rsu(String reserve_no, Integer cancel_amount, String reason) throws IOException;

	//상세 예약 페이지 이동 - 과거
	Map<String, Object> reserved_info(String reserve_no, HttpServletRequest request);

	//후기 추가 컨트롤러
	Map<String, String> insert_review(ReviewDto dto);

	//후기 내역 페이지 - 댓글 삭제
	Map<String, String> delete_review(String user_no, String review_no);

	//문의 리스트 페이지 - 문의 삭제
	Map<String, String> delete_comment(String user_no, String comment_no);

}//end class
