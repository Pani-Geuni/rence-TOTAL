/**
 * @author 강경석
 */
package com.rence.user.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rence.backoffice.model.AuthDTO;
import com.rence.user.model.EmailVO;
import com.rence.user.model.UserDto;


public interface UserService {

	//로그인
	Map<String, String> user_loginOK(String username, HttpServletResponse response, HttpSession session);
	
	Map<String, String> user_insertOK(UserDto udto);

	String user_EmailCheckOK(UserDto udto, AuthDTO avo, EmailVO evo);
	
	//이메일 인증 로직
	String user_AuthOK(String user_email, String email_code);

	//회원가입 아이디 중복체크
	String idCheckOK(UserDto udto);
	
	

	//아이디 찾기
	String user_find_Id(UserDto udto, EmailVO evo);

	//비밀번호 찾기
	String user_find_pw(UserDto udto, EmailVO evo);

	

}//end class
