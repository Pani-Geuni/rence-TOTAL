package com.rence.user.dao;

import com.rence.backoffice.model.AuthDTO;
import com.rence.user.model.UserDto;

public interface UserDAO {

	//회원가입 - 이메일 중복체크
	UserDto emailCheckOK(UserDto udto);

	//회원가입 - 이메일 중복체크 중복시도 체크
	int user_auth_selectCnt(AuthDTO avo);

	//회원가입 - 이메일 인증번호 auth테이블에 저장
	AuthDTO user_auth_insert(AuthDTO avo);
	
	//회원가입 - 이메일 인증번호 확인
	AuthDTO user_authOK_select(String user_email, String email_code);
	
	//회원가입 - 인증을 완료후 auth테이블에서 인증정보 컬럼삭제
	int user_auth_delete(String user_email, String email_code);
	
	//회원가입 - 아이디 중복체크
	int idCheckOK(String user_id);

	// 회원가입 - 회원정보 입력
	int user_insertOK(UserDto udto);

	// 회원가입 - 마일리지테이블에 초기마일리지 세팅을 위해 유저번호 불러오기
	String user_select_userno();

	// 회원가입 - 마일리지 초기값 입력(상태 T, 0)
	int user_mileage_zero_insert(String user_no);

	//로그인 성공
	UserDto user_login_info(String username);

	//아이디 찾기 - 유저 이메일 정보 불러오기
	UserDto user_email_select(UserDto udto);

	//비밀번호 찾기 - 유저 아이디 이메일 정보 불러오기
	UserDto user_id_email_select(UserDto udto);

	//비밀번호 찾기 - 비밀번호 초기화후 저장
	int user_pw_init(UserDto udto2);

	

	

	

}// end class
