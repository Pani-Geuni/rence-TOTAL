package com.rence.user.dao;

import java.util.List;

import com.rence.user.model.MileageDto;
import com.rence.user.model.MyPageReserveListDto;
import com.rence.user.model.UserDto;
import com.rence.user.model.UserMileageDto;
import com.rence.user.model.UserMypageDto;
import com.rence.user.model.UserQuestionDto;
import com.rence.user.model.UserReview_ViewDto;

public interface UserMypageDAO {

	//마이페이지 정보 불러오기
	UserMypageDto user_mypage_select(String user_no);

	//마이페이지 - 비밀번호 수정
	int user_pw_updateOK(UserDto udto);
	
	//마이페이지 - 비밀번호 수정 - 현재 비밀번호 확인 
	int check_now_pw(UserDto udto);

	//마이페이지 - 프로필 변경내용 저장
	int user_img_updateOK(UserDto udto);

	//마이페이지 - 회원탈퇴
	int user_secedeOK(UserDto udto);
 
	//마이페이지 - 예약리스트 - 총 예약리스트 수
	long total_rowCount_reserve(String user_no, String time_point);

	//마이페이지 - 예약리스트 - 페이징(현재)
	List<MyPageReserveListDto> select_all_now_reserve_list_paging(String user_no, Integer page);

	//마이페이지 - 예약리스트 - 페이징(과거)
	List<MyPageReserveListDto> select_all_before_reserve_list_paging(String user_no, Integer page);

	//마이페이지 - 마일리지 리스트 - 총마일리지
	MileageDto totalMileage_selectOne(UserDto udto);

	//마이페이지 - 마일리지 리스트 - 총 마일리지 리스트 수
	long total_rowCount_mileage_all(UserDto udto);

	//마이페이지 - 마일리지 리스트 - All 마일리지 리스트 
	List<UserMileageDto> user_mileage_selectAll_paging(UserDto udto, Integer page, long total_rowCount_mileage_all);

	//마이페이지 - 마일리지 리스트 - 총 마일리지 리스트 수(조건)
	long total_rowCount_mileage_searchKey(UserDto udto, String searchKey);

	//마이페이지 - 마일리지 리스트 - 적립/사용 마일리지 리스트
	List<UserMileageDto> user_mileage_search_list_paging(UserDto udto, String searchKey, Integer page,
			long total_rowCount_mileage_search);

	//마이페이지 - 리뷰 리스트 - 리뷰 리스트 수
	long total_rowCount_review(String user_no);

	//마이페이지 - 리뷰 리스트 - 리뷰 리스트
	List<UserReview_ViewDto> select_all_review_paging(String user_no, Integer page);

	//마이페이지 - 문의 리스트 - 문의 리스트 수
	long total_rowCount_question(String user_no);

	//마이페이지 - 문의 리스트 - 문의 리스트
	List<UserQuestionDto> select_all_question_paging(String user_no, Integer page);

	// 마이페이지 - 문의내역리스트(답변)
	UserQuestionDto select_one_answer(String comment_no);

	

}//end class
