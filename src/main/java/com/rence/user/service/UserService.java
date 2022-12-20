/**
 * @author 강경석
 */
package com.rence.user.service;

import java.util.Map;

import com.rence.backoffice.model.AuthDTO;
import com.rence.user.model.EmailVO;
import com.rence.user.model.UserDto;


public interface UserService {

	Map<String, String> user_insertOK(UserDto udto);

	String user_EmailCheckOK(UserDto udto, AuthDTO avo, EmailVO evo);
	
	//이메일 인증 로직
	String user_AuthOK(String user_email, String email_code);

	//회원가입 아이디 중복체크
	String idCheckOK(UserDto udto);
	
	//로그인 성공
	UserDto user_login_info(String username);

	//아이디 찾기
	String user_find_Id(UserDto udto, EmailVO evo);

	//비밀번호 찾기
	String user_find_pw(UserDto udto, EmailVO evo);

}//end class
