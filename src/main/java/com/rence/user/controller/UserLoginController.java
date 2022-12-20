package com.rence.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rence.security.jwt.JwtFilter;
import com.rence.security.jwt.LoginDTO;
import com.rence.security.jwt.TokenDTO;
import com.rence.security.jwt.TokenProvider;
import com.rence.user.dao.UserDAO;
import com.rence.user.model.UserDto;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "유저 로그인 컨트롤러")
@RequestMapping("/rence")
@RestController
public class UserLoginController {

//	@Autowired
//	UserDAO dao;
//
//	@Autowired
//	UserLoginService Service;
//
//	private final TokenProvider tokenProvider;
//	private final AuthenticationManagerBuilder authenticationManagerBuilder;
//
//	public UserLoginController(TokenProvider tokenProvider,
//			AuthenticationManagerBuilder authenticationManagerBuilder) {
//		this.tokenProvider = tokenProvider;
//		this.authenticationManagerBuilder = authenticationManagerBuilder;
//	}
//
//	@PostMapping("/loginOK")
//	public Map<String, Object> authorize(@Valid @RequestBody LoginDTO loginDto, HttpServletResponse response) {
//
//		log.info("login info{}", loginDto);
//		
//		Map<String, Object> map = new HashMap<>();
//
//		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//				loginDto.getUsername(), loginDto.getPassword());
//		log.info("authenticationToken info{}", authenticationToken);
//
//		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//		log.info("authentication info{}", authentication);
//
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//
//		String jwt = tokenProvider.createToken(authentication);
//		log.info("jwt info{}", jwt);
//
//		if (jwt!=null) {
//			
//			HttpHeaders httpHeaders = new HttpHeaders();
//			httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
//			
//			map.put("jwt", new ResponseEntity<>(new TokenDTO(jwt), httpHeaders, HttpStatus.OK));
//			
//			UserDto udto = dao.user_login_info(loginDto.getUsername());
//			
//			
//			ResponseCookie cookie = ResponseCookie.from("user_no", udto.getUser_no())
//		            .sameSite("None")
//		            .secure(true)
//		            .path("/rence").maxAge(60 * 30).httpOnly(true)
//		            .build();
//			ResponseCookie cookie2 = ResponseCookie.from("user_image", udto.getUser_image())
//					.sameSite("None")
//					.secure(true)
//					.path("/backoffice").maxAge(60 * 30).httpOnly(true)
//					.build();
//			response.setHeader("backoffice_no", cookie.getValue());
//			response.addHeader("host_image", cookie2.getValue());
//			
//			Cookie cookie_no = new Cookie("backoffice_no", udto.getUser_no());
//			Cookie cookie_profile = new Cookie("host_image", udto.getUser_image());
//			response.addCookie(cookie_no);
//			response.addCookie(cookie_profile);
//			
//			map.put("result", "1");
//			log.info("successed...");
//			
//		}else {
//			log.info("jwt failed...");
//			map.put("result", "0");
//		}
//
//		return map;
//	}
//
//	@GetMapping("/logoutOK")
//	public Map<String, Object> logout(@RequestBody @Valid TokenDTO requestTokenDto, HttpServletResponse response, HttpServletRequest request ) {
//
//		int result = Service.logout(requestTokenDto.getToken());
//
//		Map<String, Object> map = new HashMap<>();
//
//		if (result == 1) {
//			
//			// 헤더에 담겼을때
//			ResponseCookie cookie = ResponseCookie.from("user_no", null)
//		            .sameSite("None")
//		            .secure(true)
//		            .path("/rence")
//		            .build();
//			ResponseCookie cookie2 = ResponseCookie.from("user_image", null)
//					.sameSite("None")
//					.secure(true)
//					.path("/rence")
//					.build();
//			response.setHeader("user_no", cookie.getValue());
//			response.addHeader("user_image", cookie2.getValue());
//			
//			// 쿠키에 담겼을때
//			Cookie[] cookies = request.getCookies();
//			if (cookies != null) {
//
//				for (int i = 0; i < cookies.length; i++) {
//
//					cookies[i].setMaxAge(0);
//
//					response.addCookie(cookies[i]);
//
//				}
//
//			}
//			
//			map.put("result", "1");
//		}
//
//		return map;
//	}

}