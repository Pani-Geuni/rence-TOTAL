package com.rence.backoffice.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rence.backoffice.dao.BackOfficeDAO;
import com.rence.backoffice.model.BackOfficeDTO;
import com.rence.security.BackOfficeUserDetails;
import com.rence.security.jwt.JwtFilter;
import com.rence.security.jwt.LoginDTO;
import com.rence.security.jwt.TokenDTO;
import com.rence.security.jwt.TokenProvider;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "백오피스 로그인 컨트롤러")
@RequestMapping("/backoffice")
@RestController
public class BackOfficeLoginController {

	@Autowired
	BackOfficeDAO dao;

	@Autowired
	BackOfficeLoginService BoService;

	private final TokenProvider tokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	public BackOfficeLoginController(TokenProvider tokenProvider,
			AuthenticationManagerBuilder authenticationManagerBuilder) {
		this.tokenProvider = tokenProvider;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
	}

	@PostMapping("/log-in")
	public Map<String, Object> authorize(@Valid @RequestBody LoginDTO loginDto, HttpServletResponse response) {

		log.info("login info{}", loginDto);
		
		Map<String, Object> map = new HashMap<>();

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginDto.getUsername(), loginDto.getPassword());
		log.info("authenticationToken info{}", authenticationToken);
		
//		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
//		new org.springframework.security.core.userdetails.User(loginDto.getUsername(),
//				loginDto.getPassword(),
//	              grantedAuthorities);

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		log.info("authentication info{}", authentication);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.createToken(authentication);
		log.info("jwt info{}", jwt);

		if (jwt!=null) {
			
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
			
			map.put("jwt", new ResponseEntity<>(new TokenDTO(jwt), httpHeaders, HttpStatus.OK));
			
			BackOfficeDTO bvo = dao.backoffice_login_info(loginDto.getUsername());
			
//			ResponseCookie backoffice_no = ResponseCookie.from("backoffice_no",bvo.getBackoffice_no() ).path("/backoffice").maxAge(60 * 30).httpOnly(true).build();
//			ResponseCookie host_image = ResponseCookie.from("host_image",bvo.getHost_image() ).path("/backoffice").maxAge(60 * 30).httpOnly(true).build();
//			
//			log.info("backoffice_no::{}",backoffice_no);
//			log.info("host_image::{}",host_image);
			
//			ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, backoffice_no.toString())
//			.header(HttpHeaders.SET_COOKIE, host_image.toString());
			
//			response.setHeader(HttpHeaders.SET_COOKIE, String.format("%s; Secure; %s", backoffice_no, "SameSite=None"));
			
			ResponseCookie cookie = ResponseCookie.from("backoffice_no", bvo.getBackoffice_no())
		            .sameSite("None")
		            .secure(true)
		            .path("/backoffice").maxAge(60 * 30).httpOnly(true)
		            .build();
			ResponseCookie cookie2 = ResponseCookie.from("host_image", bvo.getHost_image())
					.sameSite("None")
					.secure(true)
					.path("/backoffice").maxAge(60 * 30).httpOnly(true)
					.build();
//			response.setHeader("backoffice_no", cookie.getValue());
//			response.addHeader("host_image", cookie2.getValue());
			response.addHeader("Set-Cookie", cookie.toString());
			response.addHeader("Set-Cookie", cookie2.toString());
			
			Cookie cookie_no = new Cookie("backoffice_no", bvo.getBackoffice_no());
			Cookie cookie_profile = new Cookie("host_image", bvo.getHost_image());
			response.addCookie(cookie_no);
			response.addCookie(cookie_profile);
			
			
			map.put("result", "1");
			log.info("successed...");
			
		}else {
			log.info("jwt failed...");
			map.put("result", "0");
		}

		return map;
	}

	@GetMapping("/log-out")
	public Map<String, Object> logout(@RequestBody @Valid TokenDTO requestTokenDto, HttpServletResponse response, HttpServletRequest request ) {

		int result = BoService.logout(requestTokenDto.getToken());

		Map<String, Object> map = new HashMap<>();

		if (result == 1) {
//			ResponseCookie backoffice_no = ResponseCookie.from("backoffice_no", null).path("/backoffice").build();
//			ResponseCookie host_image = ResponseCookie.from("host_image", null).path("/backoffice").build();
			
//			ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, backoffice_no.toString())
//	         .header(HttpHeaders.SET_COOKIE, host_image.toString());
			
			// 헤더에 담겼을때
			ResponseCookie cookie = ResponseCookie.from("backoffice_no", null)
		            .sameSite("None")
		            .secure(true)
		            .path("/backoffice")
		            .build();
			ResponseCookie cookie2 = ResponseCookie.from("host_image", null)
					.sameSite("None")
					.secure(true)
					.path("/backoffice")
					.build();
			response.setHeader("backoffice_no", cookie.getValue());
			response.addHeader("host_image", cookie2.getValue());
			
			// 쿠키에 담겼을때
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {

				for (int i = 0; i < cookies.length; i++) {

					cookies[i].setMaxAge(0);

					response.addCookie(cookies[i]);

				}

			}
			
			map.put("result", "1");
		}

		return map;
	}

}
