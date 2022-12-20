package com.rence.user.dao;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.rence.backoffice.model.AuthDTO;
import com.rence.backoffice.model.AuthEntity;
import com.rence.backoffice.model.BackOfficeDTO;
import com.rence.backoffice.model.BackOfficeEntity;
import com.rence.user.model.UserDto;
import com.rence.user.model.UserEntity;
import com.rence.user.repository.UserAuthRepository;
import com.rence.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class UserDAOImpl implements UserDAO {
	
	@Autowired
	UserRepository repository;
	
	@Autowired
	UserAuthRepository authRepository;
	
	@Autowired
	ModelMapper modelMapper = new ModelMapper();
	
	//	로그인 (성공)
	@Override
	public UserDto user_login_info(String username) {
		log.info("user_login_info()....");

		UserEntity userEntity =  repository.user_login_info(username);

		UserDto udto = modelMapper.map(userEntity,UserDto.class);
		
		return udto;
	}
	

	//이메일 중복체크
	@Override
	public UserDto emailCheckOK(UserDto udto) {
		log.info("emailCheckOK()....");
		log.info("udto: {}", udto);
		ModelMapper modelMapper = new ModelMapper();
		
		UserEntity userEntity = repository.emailCheckOK(udto.getUser_email());
		log.info("============userEntity===========: {}", userEntity);
		UserDto udto2 = new UserDto();
		
		if(userEntity != null) {
			udto2 = modelMapper.map(userEntity,UserDto.class);
		}
		else {
			udto2 = null;
		}
		
		return udto2;
	}
	
	// 이메일 중복체크 중복시도 체크
	@Override
	public int user_auth_selectCnt(AuthDTO adto) {
		log.info("user_auth_selectCnt()....");
		log.info("adto: {}", adto);

		return authRepository.user_auth_selectCnt(adto.getUser_email());
	}
	
	// 이메일 인증번호 auth테이블에 저장
	@Override
	public AuthDTO user_auth_insert(AuthDTO adto) {
		log.info("user_auth_insert()....");
		log.info("adto: {}", adto);
		int result = authRepository.user_auth_insert(adto.getUser_email(), adto.getAuth_code());
		log.info("result: {}", result);
		log.info("adto(이후): {}", adto);
		AuthDTO adto2 = new AuthDTO();
		
		if (result == 1) {
			log.info("===adto===: {}", adto);
			AuthEntity authEntity = authRepository.auth_select(adto.getUser_email());
			adto2 = modelMapper.map(authEntity,AuthDTO.class);
			log.info("adto2: {}", adto2);
		}

		return adto2;
	}
	
	// 이메일 인증번호 확인
	@Override
	public AuthDTO user_authOK_select(String user_email, String email_code) {
		log.info("user_authOK_select()....");
		log.info("user_email: {}", user_email);
		log.info("email_code(인증코드): {}", email_code);
		
		AuthEntity authEntity = authRepository.user_authOK_select(user_email, email_code);
		
		AuthDTO adto = modelMapper.map(authEntity,AuthDTO.class);
		
		return adto;
	}
	
	// 인증을 완료후 auth테이블에서 인증정보 컬럼삭제
	@Override
	public int user_auth_delete(String user_email, String email_code) {
		log.info("user_auth_delete()....");
		log.info("user_email: {}", user_email);
		log.info("email_code(인증코드): {}", email_code);
		
		return authRepository.user_auth_delete(user_email, email_code);
	}
	
	//아이디 중복체크
	@Override
	public int idCheckOK(String user_id) {
		log.info("idCheckOK()....");
		log.info("user_id: {}", user_id);
		
		
		int idCheck_cnt  = repository.idCheckOK(user_id);

		log.info("idCheck_cnt: {}", idCheck_cnt);

		return idCheck_cnt;
	}

	//회원가입 - 회원정보 입력
	@Override
	public int user_insertOK(UserDto udto) {
		// 비밀번호 암호화
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		udto.setUser_pw(encoder.encode(udto.getPassword()));
		
		return repository.user_insertOK(udto.getUser_id(), udto.getUser_pw(), udto.getUser_name(), udto.getUser_email(),
				udto.getUser_tel(), udto.getUser_birth());
	}
	
	//회원가입 - 마일리지테이블에 초기마일리지 세팅을 위해 유저번호 불러오기
	@Override
	public String user_select_userno() {
		log.info("user_insertOK()....");

		return repository.user_select_userno();
	}
	
	
	// 회원가입 - 마일리지 초기값 입력(상태 T, 0)
	@Override
	public int user_mileage_zero_insert(String user_no) {
		log.info("user_mileage_zero_insert()....");
		log.info("user_no: {}", user_no);
		return repository.user_mileage_zero_insert(user_no);
	}


	//아이디 찾기 - 이메일 정보 불러오기
	@Override
	public UserDto user_email_select(UserDto udto) {
		log.info("user_email_select()....");
		log.info("udto: {}", udto);
		
		UserDto udto2 = null;
		
		UserEntity userEntity = new UserEntity();
		userEntity =  repository.user_email_select(udto.getUser_email());

		if(userEntity != null) {
			udto2 = modelMapper.map(userEntity,UserDto.class);
		}

		return udto2;
	}

	//비밀번호 찾기 - 유저 아이디 이메일 정보 불러오기
	@Override
	public UserDto user_id_email_select(UserDto udto) {
		log.info("user_id_email_select()....");
		log.info("udto: {}", udto);
		
		
		UserEntity userEntity = repository.user_id_email_select(udto.getUser_id(), udto.getUser_email());

		UserDto udto2 = null;
		if(userEntity != null) {
			udto2 = modelMapper.map(userEntity,UserDto.class);
		}
		
		return udto2;
	}

	//비밀번호 찾기 - 비밀번호 초기화후 저장
	@Override
	public int user_pw_init(UserDto udto) {
		log.info("user_pw_init()....");
		log.info("udto: {}", udto);
		
		return repository.user_pw_init(udto.getUser_pw(), udto.getUser_id());
	}
	
	
	

}//end class
