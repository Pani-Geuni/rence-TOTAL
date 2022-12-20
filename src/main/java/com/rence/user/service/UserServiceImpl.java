package com.rence.user.service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rence.backoffice.model.AuthDTO;
import com.rence.user.dao.UserDAO;
import com.rence.user.model.EmailVO;
import com.rence.user.model.UserDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO dao;

	@Autowired
	UserSendEmail authSendEmail;

	@Override
	public Map<String, String> user_loginOK(String username, HttpServletResponse response, HttpSession session) {
		log.info("user_loginOK()...");
		log.info("username: {}", username);

		Map<String, String> map = new HashMap<String, String>();

		// 로그인 성공시 기존의 유저관련쿠키 제거
		Cookie cc = new Cookie("user_no", null); // choiceCookieName(쿠키 이름)에 대한 값을 null로 지정
		Cookie cc2 = new Cookie("user_image", null);
		cc.setMaxAge(0); // 유효시간을 0으로 설정
		cc2.setMaxAge(0);
		response.addCookie(cc); // 응답 헤더에 추가해서 없어지도록 함
		response.addCookie(cc2);

		UserDto udto = dao.user_login_info(username);

		String user_id = udto.getUser_id();

		session.setAttribute("user_id", user_id);

		// 쿠키저장을 위해 유저번호 및 이미지 base64 암호화 처리
		Base64.Encoder encoder = Base64.getEncoder();
		byte[] user_no = udto.getUser_no().getBytes();
		byte[] user_image = ("https://rence.s3.ap-northeast-2.amazonaws.com/user/" + udto.getUser_image()).getBytes();

		String user_no_base64 = encoder.encodeToString(user_no);
		String user_image_base64 = encoder.encodeToString(user_image);

		log.info("user_no_base64: {},  user_image_base64: {}", user_no_base64, user_image_base64);

		Cookie cookie = new Cookie("user_no", user_no_base64); // 고유번호 쿠키 저장
		Cookie cookie2 = new Cookie("user_image", user_image_base64); // 고유번호 쿠키 저장
		cookie.setPath("/");
		cookie2.setPath("/");
		response.addCookie(cookie);
		response.addCookie(cookie2);

		log.info("User Login success.....");
		map.put("result", "1"); // 로그인 성공

		map.put("user_id", user_id);
		return map;
	}

	// 이메일 체크
	@Override
	public String user_EmailCheckOK(UserDto udto, AuthDTO adto, EmailVO evo) {
		log.info("user_EmailCheckOK()....");
		log.info("udto: {}", udto);
		log.info("adto: {}", adto);
		log.info("evo: {}", evo);

		String emailCheck_result = null;

		// 이메일 중복 체크
		UserDto emailCheck = dao.emailCheckOK(udto);
		log.info("emailCheck: {}", emailCheck);

		// 탈퇴한 회원의 이메일로 재가입 가능
		if (emailCheck == null || emailCheck.getUser_state().equalsIgnoreCase("N   ")) {
			adto.setUser_email(udto.getUser_email());
			log.info("adto : {}", adto);
			// 인증 테이블에 인증한 기록이 있는지 확인(카운트) 1이상이면 인증을 시도를 한 상태
			int auth_selectCnt = dao.user_auth_selectCnt(adto);
			// 인증테이블에 데이터가 없을때(첫 시도 or 2분경과로 자동삭제가 된 상태)
			if (auth_selectCnt == 0) {
				// 이메일 전송
				adto = authSendEmail.sendEmail(adto, evo);
				log.info("메일이 전송되었습니다.C_adto: {}", adto);
			}
			if (adto != null) {
				// 인증테이블에 데이터가 있을때(재시도, 2분경과가 되지 않은 상태)
				if (auth_selectCnt > 0) {
					// 인증번호 재전송 시간전에 재요청시
					log.info("user_auth Re-try authentication");

					emailCheck_result = "3";
				} else {
					log.info("auth_selectCnt:{}", auth_selectCnt);
					AuthDTO adto2 = dao.user_auth_insert(adto);
					log.info("user_auth successed...");
					log.info("adto2:{}", adto2);
					emailCheck_result = "1";
				}
			} else {
				log.info("user_auth failed...");
				emailCheck_result = "0";
			}
		}
		// 이메일 중복체크시 이메일이 있을때(회원이 가입이 되어 있는상태) 2반환
		else {
			log.info("user_auth failed...(email check NOT OK)");
			emailCheck_result = "2";
		}

		return emailCheck_result;
	}

	@Override
	public String user_AuthOK(String user_email, String email_code) {
		log.info("user_AuthOK...");
		log.info("user_email: {}", user_email);
		log.info("email_code: {}", email_code);

		String user_auth_result = null;

		AuthDTO adto = dao.user_authOK_select(user_email, email_code);
		log.info("adto: {}", adto);

		if (adto != null) {
			log.info("successed...");
			int del_result = dao.user_auth_delete(user_email, email_code);
			log.info("del_result: ", del_result);

			user_auth_result = "1";
		} else {
			log.info("failed...");
			user_auth_result = "0";
		}

		return user_auth_result;
	}

	@Override
	public String idCheckOK(UserDto udto) {
		log.info("user_idCheckOK()...");
		log.info("입력 아이디: {}", udto.getUser_id());

		String idCheck_result = null;

		log.info("idCheck_result: {}", idCheck_result);
		int idCheck = dao.idCheckOK(udto.getUser_id());

		if (idCheck == 0) {
			idCheck_result = "1"; // 아이디 사용가능("OK")
		} else {
			// udto가 null이 아니면 아이디 존재
			idCheck_result = "0"; // 아이디 존재("NOT OK")
		}

		return idCheck_result;
	}

	// 회원가입
	@Override
	public Map<String, String> user_insertOK(UserDto udto) {
		log.info("user_insertOK()....");
		log.info("udto: {}", udto);

		Map<String, String> map = new HashMap<String, String>();

		int join_result = 0;
		int insert_result = dao.user_insertOK(udto);

		// 회원정보 입력 성공시
		if (insert_result == 1) {
			String user_no = dao.user_select_userno();
			log.info("user_no: {}", user_no);
			int mileage_insert_result = dao.user_mileage_zero_insert(user_no);
			if (mileage_insert_result == 1) {
				join_result = 1;
			}
		} else {
			// 회원가입은 했지만 마일리지 데이터가 안들어갔으므로 실패
			join_result = 0;
		}

		log.info("join_result: {}", join_result);

		if (join_result == 0) {
			// 회원가입실패
			map.put("result", "0");
		} else if (join_result == 1) {
			// 회원가입 성공
			map.put("result", "1");
		}

		return map;
	}

	// 아이디 찾기
	@Override
	public Map<String, String> user_find_Id(UserDto udto, EmailVO evo) {
		log.info("user_find_Id()...");
		log.info("udto: {}", udto);
			
		Map<String, String> map = new HashMap<String, String>();

		UserDto udto2 = dao.user_email_select(udto);
		log.info("udto2: {}", udto2);
		if (udto2 != null) {
			udto2 = authSendEmail.findId(udto2, evo); // 유저의 메일로 아이디 전송

			if (udto2 != null) {
				log.info("user_fine_id successed...");
				map.put("result", "1");

			} else {
				log.info("user_fine_id failed...");
				map.put("result", "0");
			}
		}
		return map;
	}

	// 비밀번호 찾기
	@Override
	public Map<String, String> user_find_pw(UserDto udto, EmailVO evo) {
		log.info("user_find_pw()...");
		log.info("udto: {}", udto);
		
		Map<String, String> map = new HashMap<String, String>();

		String findPw_res = null;

		UserDto udto2 = dao.user_id_email_select(udto); // 아이디 이메일 체크

		if (udto2 != null) {
			// udto2가 null이 아니면(테이블에 데이터가 존재하면) 메일을 통해 수정링크 제공
			udto2 = authSendEmail.findPw(udto2, evo);
			log.info("비밀번호 찾기 메일 전송완료");
			int result = dao.user_pw_init(udto2);
			log.info("비밀번호 초기화 업데이트 완료");

			if (result != 0) {
				log.info("user_fine_pw successed...");
				map.put("result", "1");
			} else {
				log.info("user_fine_pw failed...");
				map.put("result", "0");
			}
		} else {
			map.put("result", "0");
			log.info("No user info...");
		}
		return map;
	}

}// end class
