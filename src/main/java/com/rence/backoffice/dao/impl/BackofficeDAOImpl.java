/**
 * 
 * @author 최진실
 *
 */
package com.rence.backoffice.dao.impl;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import com.rence.backoffice.common.OperatingTime;
import com.rence.backoffice.dao.BackOfficeDAO;
import com.rence.backoffice.model.AuthEntity;
import com.rence.backoffice.model.AuthDTO;
import com.rence.backoffice.model.BackOfficeEntity;
import com.rence.backoffice.model.BackOfficeOperatingTimeEntity;
import com.rence.backoffice.model.BackOfficeOperatingTimeDTO;
import com.rence.backoffice.model.BackOfficeDTO;
import com.rence.backoffice.repository.AuthRepository;
import com.rence.backoffice.repository.BackOfficeOperatingTimeRepository;
import com.rence.backoffice.repository.BackOfficeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class BackofficeDAOImpl implements BackOfficeDAO {

	@Autowired
	OperatingTime operatingTime;

	@Autowired
	BackOfficeRepository repository;

	@Autowired
	BackOfficeOperatingTimeRepository oper_repository;

	@Autowired
	AuthRepository auth_repository;

	@Autowired
	ModelMapper modelMapper;

	/**
	 * 백오피스 신청 - 업체 정보
	 */
	@Override
	public BackOfficeDTO backoffice_insertOK(BackOfficeDTO vo) {
//		vo.setBackoffice_state("W");
//		vo.setApply_date(new Date());
//
//		BackOfficeVO bvo2 = new BackOfficeVO();
//
//		int flag = repository.insert_backoffice(vo, vo.getApply_date());
//
//		if (flag == 1) {
//			bvo2 = repository.select_backoffice_no(vo.getBackoffice_email());
//		}
//		return bvo2;

		vo.setBackoffice_state("W");
		vo.setApply_date(new Date());
		vo.setHost_image("img_host_001.jpg");

//		ModelMapper modelMapper = new ModelMapper();
		BackOfficeEntity bvo = new BackOfficeEntity();
		BackOfficeDTO bvo2 = new BackOfficeDTO();

		bvo = modelMapper.map(vo, BackOfficeEntity.class);

		int flag = repository.insert_backoffice(bvo, bvo.getApply_date());

		if (flag == 1) {
			bvo = repository.select_backoffice_no(bvo.getBackoffice_email());
			bvo2 = modelMapper.map(bvo, BackOfficeDTO.class);
		}
		return bvo2;

	}

	/**
	 * 백오피스 신청 - 운영 시간
	 */
	@Override
	public int backoffice_operating_insertOK(BackOfficeOperatingTimeDTO ovo) {
		BackOfficeOperatingTimeEntity ovo2 = operatingTime.operatingTime(ovo);
		return oper_repository.insert_operating_time(ovo2);
	}

	/**
	 * 이메일 인증번호 요청 - 이메일 중복 체크
	 */
	@Override
	public BackOfficeDTO backoffice_email_check(BackOfficeDTO bvo) {

		BackOfficeEntity be = repository.backoffice_email_check(bvo.getBackoffice_email());
		if (be != null) {
			bvo = modelMapper.map(be, BackOfficeDTO.class);
		} else {
			bvo = null;
		}

		return bvo;
	}

	/**
	 * 이메일 인증번호 요청 - 인증 번호 재전송 가능 여부
	 */
	@Override
	public AuthDTO backoffice_auth_overlap(BackOfficeDTO bvo) {

		AuthDTO avo = new AuthDTO();

		AuthEntity ae = auth_repository.backoffice_auth_overlap(bvo.getBackoffice_email());
		if (ae != null) {
			avo = modelMapper.map(ae, AuthDTO.class);
		} else {
			avo = null;
		}

		return avo;
	}

	/**
	 * 이메일 인증번호 요청
	 */
	@Override
	public AuthDTO backoffice_auth_insert(AuthDTO avo) {

		avo.setAuth_stime(new Date());

		AuthEntity ae = modelMapper.map(avo, AuthEntity.class);

		int result = auth_repository.insert_auth_info(ae, ae.getAuth_stime());

		AuthDTO avo2 = new AuthDTO();
		if (result != 0) {
			ae = auth_repository.findbyAuth(avo.getUser_email());
			avo2 = modelMapper.map(ae, AuthDTO.class);
		}

		return avo2;
	}

	/**
	 * 
	 * AOP ... 이메일 인증번호 요청 - 인증 번호 유효 시간 초과 시, 삭제
	 * 
	 */
	public void auth_auto_delete(String user_email) {
		log.info("auth_auto_delete()....");
		auth_repository.auth_auto_delete(user_email);
	}

	/**
	 * 이메일 인증 확인
	 * 
	 */
	@Override
	public AuthDTO backoffice_authOK_select(String backoffice_email, String auth_code) {

		AuthDTO avo = new AuthDTO();

		AuthEntity ae = auth_repository.findbyAuthOK(backoffice_email, auth_code);

		if (ae != null) {
			avo = modelMapper.map(ae, AuthDTO.class);
		} else {
			avo = null;
		}

		return avo;
	}

	/**
	 * 이메일 인증 확인 - 인증된 번호 삭제
	 * 
	 */
	@Override
	public void backoffice_auth_delete(AuthDTO avo2) {
		auth_repository.deleteByAuth_no(avo2.getAuth_no());
	}

	/**
	 * 로그인 성공 후 정보 가져오기
	 * 
	 */
	@Override
	public BackOfficeDTO backoffice_login_info(String username) {

		BackOfficeDTO bvo = new BackOfficeDTO();

		BackOfficeEntity be = repository.backoffice_login_info(username);

		if (be != null) {
			bvo = modelMapper.map(be, BackOfficeDTO.class);
		} else {
			bvo = null;
		}

		return bvo;
	}

	/**
	 * 비밀번호 초기화(찾기) - 기존 정보 유무
	 */
	@Override
	public BackOfficeDTO backoffice_id_email_select(BackOfficeDTO bvo) {

		BackOfficeEntity be = repository.select_backoffice_by_id_email(bvo.getBackoffice_id(), bvo.getBackoffice_email());

		if (be != null) {
			bvo = modelMapper.map(be, BackOfficeDTO.class);
		} else {
			bvo = null;
		}

		return bvo;
	}

	/**
	 * 비밀번호 초기화(찾기) - 임시 비밀번호 저장
	 */
	@Override
	public int backoffice_resetOK_pw(BackOfficeDTO bvo2) {
		return repository.update_backoffice_temp_pw(bvo2.getBackoffice_pw(), bvo2.getBackoffice_no());

	}

	/**
	 * 비밀번호 초기화 완료
	 */
	@Override
	public int backoffice_settingOK_pw(BackOfficeDTO bvo) {
		Decoder decoder = Base64.getDecoder();
		byte[] decodedBytes2 = decoder.decode(bvo.getBackoffice_no());
		bvo.setBackoffice_no(new String(decodedBytes2));
		return repository.update_backoffice_temp_pw(bvo.getBackoffice_pw(), bvo.getBackoffice_no());
	}

}
