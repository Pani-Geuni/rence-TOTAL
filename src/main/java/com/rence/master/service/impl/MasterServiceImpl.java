/**
 * @author 최진실
 */
package com.rence.master.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rence.backoffice.model.BackOfficeListDTO;
import com.rence.backoffice.model.BackOfficeDTO;
import com.rence.backoffice.model.EmailVO;
import com.rence.common.OptionEngToKorMap;
import com.rence.master.common.MasterSendEmail;
import com.rence.master.dao.MasterDAO;
import com.rence.master.service.MasterService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MasterServiceImpl implements MasterService {
	
	@Autowired
	MasterDAO dao;
	
	@Autowired
	MasterSendEmail sendEmail;

	/**
	 * 마스터 메인 페이지 (호스트 가입 신청 리스트)
	 */
	@Override
	public Map<String, Object> backoffice_applyList_selectAll(Integer page) {
		Map<String, Object> map = new HashMap<String, Object>();
		log.info("page:{}", page);

		List<BackOfficeListDTO> bvos = dao.backoffice_applyList_selectAll(page);
		log.info("result: {}.", bvos.size());
		log.info("bvos : {}", bvos);
		
		map.put("bvos", bvos);

		return map;
		

	}

	/**
	 * 마스터 메인 페이지 (호스트 가입 신청 리스트 - 승인)
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	@Override
	public Map<String, String> master_grant(BackOfficeDTO bvo, EmailVO evo) throws UnsupportedEncodingException {
		Map<String, String> map = new HashMap<String, String>();

		int flag = dao.backoffice_grant(bvo);
		if (flag == 1) {
			BackOfficeDTO bvo2 = sendEmail.settingPw(bvo, evo);
			if (bvo2 != null) {
				log.info("successed...");
				map.put("result", "1");
			} else { // 이메일 전송 실패
				log.info("failed...");
				map.put("result", "0");
			}
		} else { // 백오피스 승인 실패
			log.info("failed...");
			map.put("result", "0");
		}

		return map;
	}

	/**
	 * 마스터 메인 페이지 (호스트 가입 신청 리스트 - 거절)
	 */
	@Override
	public Map<String, String> master_refuse(BackOfficeDTO bvo, EmailVO evo) {
		Map<String, String> map = new HashMap<String, String>();

		int flag = dao.backoffice_refuse(bvo);
		if (flag == 1) {
			BackOfficeDTO bvo2 = sendEmail.result_refuse(bvo, evo);
			if (bvo2 != null) {
				log.info("successed...");
				map.put("result", "1");
			} else { // 이메일 전송 실패
				log.info("failed...");
				map.put("result", "0");
			}
		} else { // 백오피스 거절 실패
			log.info("failed...");
			map.put("result", "0");
		}
		return map;
	}

	/**
	 * 마스터 - 호스트 탈퇴 신청 리스트
	 */
	@Override
	public Map<String, Object> backoffice_end(Integer page) { 
		Map<String, Object> map = new HashMap<String, Object>();
		log.info("page:{}", page);

		List<BackOfficeListDTO> bvos = dao.backoffice_endList_selectAll(page);
		log.info("result: {}.", bvos.size());
		log.info("bvos : {}", bvos);
		
		map.put("bvos", bvos);

		return map;

	}

	/**
	 * 마스터 - 호스트 탈퇴 승인
	 */
	@Override
	public Map<String, String> master_revoke(BackOfficeDTO bvo, EmailVO evo) {
		Map<String, String> map = new HashMap<String, String>();

		int flag = dao.backoffice_revoke(bvo);
		if (flag == 1) {
			BackOfficeDTO bvo2 = sendEmail.backoffice_revoke(bvo, evo);
			if (bvo2 != null) {
				log.info("successed...");
				map.put("result", "1");
			} else { // 이메일 전송 실패
				log.info("failed...");
				map.put("result", "0");
			}
		} else { // 백오피스 승인 실패
			log.info("failed...");
			map.put("result", "0");
		}

		return map;
	}

	/**
	 * 백오피스 가입 상세, 탈퇴 페이지
	 */
	@Override
	public Map<String, Object> master_backoffice_detail_selectOne(BackOfficeDTO bvo, String page) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		BackOfficeDTO bvo2 = dao.master_backoffice_detail_selectOne(bvo);

		OptionEngToKorMap optionEngToKorMap = new OptionEngToKorMap();

		if (bvo2.getBackoffice_tag() != null) {
			String[] backoffice_tag = bvo2.getBackoffice_tag().split(",");
			for (int i = 0; i < backoffice_tag.length; i++) {
				backoffice_tag[i] = "#" + backoffice_tag[i];
			}
			map.put("backoffice_tag", backoffice_tag);
		} else {
			map.put("backoffice_tag", "-");
		}

		if (bvo2.getBackoffice_option() != null) {
			List<String> backoffice_option = optionEngToKorMap.splitOption(bvo2.getBackoffice_option());
			map.put("backoffice_option", backoffice_option);
		} else {
			map.put("backoffice_option", "-");
		}

		if (bvo2.getBackoffice_around() != null) {
			List<String> backoffice_around = optionEngToKorMap.splitAroundOption(bvo2.getBackoffice_around());
			map.put("backoffice_around", backoffice_around);
		} else {
			map.put("backoffice_around", "-");
		}

		List<String> backoffice_image = optionEngToKorMap.splitImage(bvo2.getBackoffice_image());
		List<String> backoffice_type = (optionEngToKorMap.splitType(bvo2.getBackoffice_type()));

		map.put("backoffice_image", backoffice_image);
		map.put("backoffice_type", backoffice_type);
		map.put("vo", bvo2);
		map.put("page", page);

		return map;
	}

}
