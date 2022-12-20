/**
 * @author 최진실
 */
package com.rence.master.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rence.backoffice.model.BackOfficeDTO;
import com.rence.backoffice.model.BackOfficeEntity;
import com.rence.backoffice.model.BackOfficeListDTO;
import com.rence.backoffice.model.BackOfficeListEntity;
import com.rence.backoffice.repository.BackOfficeListRepository;
import com.rence.backoffice.repository.BackOfficeRepository;
import com.rence.dashboard.model.RoomDTO;
import com.rence.master.dao.MasterDAO;
import com.rence.master.repository.MasterRepository;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class MasterDAOImpl implements MasterDAO{
	
	@Autowired
	MasterRepository m_repository;

	@Autowired
	BackOfficeRepository b_repository;
	
	@Autowired
	BackOfficeListRepository b_list_repository;
	
	@Autowired
	ModelMapper modelMapper;

	/**
	 * 마스터 메인 페이지 (호스트 가입 신청 리스트)
	 */
	@Override
	public List<BackOfficeListDTO> backoffice_applyList_selectAll(Integer page) {
		log.info("backoffice_applyList_selectAll().....");
		log.info("currentpage:{}", page);

		Integer row_count = 15;
		Integer start_row = (page - 1) * row_count + 1;
		Integer end_row = page * row_count;
		
		List<BackOfficeListEntity> be =  b_list_repository.selectAll_backoffice_apply(start_row, end_row);
		List<BackOfficeListDTO> bvos = new ArrayList<BackOfficeListDTO>();
		if (be!=null) {
			bvos = be.stream().map(bvo -> modelMapper.map(bvo, BackOfficeListDTO.class)).collect(Collectors.toList());
		}else {
			bvos=null;
		}

		return bvos;
	}

	/**
	 * 마스터 메인 페이지 (호스트 가입 신청 리스트 - 승인)
	 * 
	 */
	@Override
	public int backoffice_grant(BackOfficeDTO bvo) {
		log.info("backoffice_grant().....");
		return b_repository.update_backoffice_state_y(bvo.getBackoffice_no(),bvo.getBackoffice_email());
	}

	/**
	 * 마스터 메인 페이지 (호스트 가입 신청 리스트 - 거절)
	 */
	@Override
	public int backoffice_refuse(BackOfficeDTO bvo) {
		return b_repository.update_backoffice_state_N(bvo.getBackoffice_no(),bvo.getBackoffice_email());
	}

	/**
	 * 마스터 - 호스트 탈퇴 신청 리스트
	 */
	@Override
	public List<BackOfficeListDTO> backoffice_endList_selectAll(Integer page) {
		log.info("backoffice_applyList_selectAll().....");
		log.info("currentpage:{}", page);

		Integer row_count = 15;
		Integer start_row = (page - 1) * row_count + 1;
		Integer end_row = page * row_count;

		List<BackOfficeListEntity> be =  b_list_repository.selectAll_backoffice_end(start_row, end_row);
		
		List<BackOfficeListDTO> bvos = new ArrayList<BackOfficeListDTO>();
		if (be!=null) {
			bvos = be.stream().map(bvo -> modelMapper.map(bvo, BackOfficeListDTO.class)).collect(Collectors.toList());
		}else {
			bvos=null;
		}

		return bvos;
	}

	/**
	 * 마스터 - 호스트 탈퇴 승인
	 */
	@Override
	public int backoffice_revoke(BackOfficeDTO bvo) {
		return b_repository.update_backoffice_state_X(bvo.getBackoffice_no(),bvo.getBackoffice_email());
	}

	/**
	 * 백오피스 가입, 탈퇴 상세 페이지
	 */
	@Override
	public BackOfficeDTO master_backoffice_detail_selectOne(BackOfficeDTO bvo) {
		
		BackOfficeEntity be = b_repository.selectOne_backoffice_detail_m(bvo.getBackoffice_no());
		bvo = modelMapper.map(be, BackOfficeDTO.class);
		
		return bvo;
	}
	
}
