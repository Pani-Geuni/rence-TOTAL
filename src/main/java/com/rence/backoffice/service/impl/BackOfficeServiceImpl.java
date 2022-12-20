/**
 * 
 * @author 최진실
 *
 */
package com.rence.backoffice.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rence.backoffice.common.BackOfficeSendEmail;
import com.rence.backoffice.dao.BackOfficeDAO;
import com.rence.backoffice.model.AuthDTO;
import com.rence.backoffice.model.BackOfficeDTO;
import com.rence.backoffice.model.BackOfficeOperatingTimeDTO;
import com.rence.backoffice.model.EmailVO;
import com.rence.backoffice.service.BackOfficeService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BackOfficeServiceImpl implements BackOfficeService {
	@Autowired
	BackOfficeDAO dao;

	@Autowired
	BackOfficeSendEmail authSendEmail;

	@Override
	public Map<String, Object> login_check(HttpSession session) {

		Map<String, Object> map = new HashMap<String, Object>();

		if (session.getAttribute("backoffice_id") != null) {
			map.put("result", "1");
		} else {
			map.put("result", "0");
		}

		return map;
	}

	/**
	 * 백오피스 신청 처리
	 */
	@Override
	public Map<String, String> insertOK(BackOfficeDTO vo, BackOfficeOperatingTimeDTO ovo) {

		Map<String, String> map = new HashMap<String, String>();

		// 백오피스 insert
		BackOfficeDTO bvo2 = dao.backoffice_insertOK(vo);

		// 운영시간 insert
		ovo.setBackoffice_no(bvo2.getBackoffice_no());
		int result = dao.backoffice_operating_insertOK(ovo);

		if (result == 1) {
			map.put("result", "1");
		} else {
			map.put("result", "0");
		}

		return map;

	}

	/**
	 * 이메일 인증번호 요청
	 */
	@Override
	public Map<String, String> backoffice_auth(AuthDTO avo, BackOfficeDTO bvo, EmailVO evo) {
		Map<String, String> map = new HashMap<String, String>();

		// 이메일 중복 체크
		BackOfficeDTO emailCheck = dao.backoffice_email_check(bvo);
		AuthDTO avo_check = dao.backoffice_auth_overlap(bvo);

		if (emailCheck == null || emailCheck.getBackoffice_state().equals("X")
				|| emailCheck.getBackoffice_state().equals("N")) {
			if (avo_check == null) {

				avo.setUser_email(bvo.getBackoffice_email());

				// 이메일 전송
				avo = authSendEmail.sendEmail(avo, evo);
				if (avo != null) {

					// avo2 = auth 테이블에 정보 저장 후, select 해온 결과값
					AuthDTO avo2 = dao.backoffice_auth_insert(avo);
					log.info("successed...");

					map.put("result", "1");
					map.put("auth_code", avo2.getAuth_code());
					map.put("backoffice_email", avo2.getUser_email());
					map.put("auth_no", avo2.getAuth_no());

				} else { // 전송 실패
					log.info("send failed...");
					map.put("result", "0");
				}
			} else { // 재전송 실패
				log.info("re-send failed...3");
				map.put("result", "3");
			}
		} else { // 중복체크 실패
			log.info("over failed...");
			map.put("result", "0");
		}

		return map;
	}

	/**
	 * 이메일 인증번호 확인
	 */
	@Override
	public Map<String, String> backoffice_authOK(String backoffice_email, String auth_code) {

		AuthDTO avo = dao.backoffice_authOK_select(backoffice_email, auth_code);

		Map<String, String> map = new HashMap<String, String>();

		if (avo != null) {
			log.info("successed...");
			map.put("result", "1");
			dao.backoffice_auth_delete(avo);

		} else {
			log.info("failed...");
			map.put("result", "0");
		}

		return map;
	}

	/**
	 * 로그인 성공 처리
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@Override
	public Map<String, String> backoffice_loginOK(String username, HttpSession session, HttpServletResponse response)
			throws UnsupportedEncodingException {

		BackOfficeDTO bvo = dao.backoffice_login_info(username);

		Map<String, String> map = new HashMap<String, String>();

		/* base64 encoding */
		Encoder encoder = Base64.getEncoder(); // 쿠키 정보 암호화

		String backoffice_no = encoder.encodeToString(bvo.getBackoffice_no().getBytes("UTF-8"));
		String host_image = encoder.encodeToString(bvo.getHost_image().getBytes("UTF-8"));

		session.setAttribute("backoffice_id", bvo.getBackoffice_id());

		log.info("backoffice_no : {}", backoffice_no);
		log.info("host_image : {}", host_image);

		Cookie cookie_no = new Cookie("backoffice_no", backoffice_no);
//		cookie_no.setMaxAge(-1);
		cookie_no.setPath("/");
		Cookie cookie_profile = new Cookie("host_image", host_image);
//		cookie_profile.setMaxAge(-1);
		cookie_profile.setPath("/");
		response.addCookie(cookie_no);
		response.addCookie(cookie_profile);

		map.put("result", "1");
		log.info("successed...");

		return map;
	}

	/**
	 * 로그아웃 성공 처리
	 */
	@Override
	public Map<String, String> backoffice_logoutOK(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		Map<String, String> map = new HashMap<String, String>();

		session.removeAttribute("backoffice_id");
		
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {

			for (int i = 0; i < cookies.length; i++) {

				cookies[i].setMaxAge(0);

				response.addCookie(cookies[i]);

			}

		}
		map.put("result", "1");

		return map;
	}

	/**
	 * 비밀번호 초기화(찾기), 이메일로 임시 비밀번호 전송
	 */
	@Override
	public Map<String, String> backoffice_reset_pw(BackOfficeDTO bvo, EmailVO evo) {
		Map<String, String> map = new HashMap<String, String>();

		BackOfficeDTO bvo2 = dao.backoffice_id_email_select(bvo);

		if (bvo2 != null) {
			bvo2 = authSendEmail.findPw(bvo2, evo);

			if (bvo2 != null) {
				int flag = dao.backoffice_resetOK_pw(bvo2);
				if (flag == 1) {
					map.put("result", "1");
				} else {
					log.info("save failed...");
					map.put("result", "0");
				}
			} else {
				log.info("update failed...");
				map.put("result", "0");
			}
		} else {
			log.info("send failed...");
			map.put("result", "0");
		}

		return map;
	}

	/**
	 * 비밀번호 초기화 완료
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@Override
	public Map<String, String> backoffice_settingOK_pw(BackOfficeDTO bvo, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws UnsupportedEncodingException {

		/* base64 decoding */
		Decoder decoder = Base64.getDecoder();

		Cookie[] cookies = request.getCookies();
		String backoffice_no = "";
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("backoffice_no")) {
					backoffice_no = cookie.getValue();
					backoffice_no = new String(decoder.decode(backoffice_no.getBytes("UTF-8")));
					log.info("backoffice_no :: {}", backoffice_no);
				}
			}
		}

		bvo.setBackoffice_pw(new BCryptPasswordEncoder().encode(bvo.getBackoffice_pw()));
		int result = dao.backoffice_settingOK_pw(bvo);

		Map<String, String> map = new HashMap<String, String>();

		if (result == 1) {
			if (session.getAttribute("backoffice_id") != null) {
				if (backoffice_no.equals(bvo.getBackoffice_no())) {
					// HOST 로그인 session이 존재할 때
					// Host 환경설정 > 비밀번호 수정
					log.info("succeed...setting");
					map.put("result", "1");
				}
			} else {
				// 가입 신청이 완료되어
				// 신청자의 메일에서 링크 페이지를 열고 수정 했을 때
				log.info("succeed...landing");
				map.put("result", "2");
			}
		} else if (result == 0) {
			log.info("fail...");
			map.put("result", "0");
		}

		return map;
	}

	/**
	 * 
	 * AOP ... 이메일 인증 번호 삭제
	 * 
	 */
	@Override
	public void auth_auto_delete(String user_email) {
		dao.auth_auto_delete(user_email);
	}

}
