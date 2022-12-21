package com.rence.user.dao;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rence.office.model.OfficeMileageDto;
import com.rence.office.model.OfficeMileageEntity;
import com.rence.office.model.OfficePaymentDto;
import com.rence.office.model.OfficePaymentEntity;
import com.rence.office.repo.OfficeMileageRepository;
import com.rence.office.repo.OfficePaymentRepository;
import com.rence.office.repo.OfficeReserveRepository;
import com.rence.user.model.ReserveInfo_ViewDto;
import com.rence.user.model.ReserveInfo_ViewEntity;
import com.rence.user.model.ReserveMileageDto;
import com.rence.user.model.ReserveMileageEntity;
import com.rence.user.model.ReviewDto;
import com.rence.user.model.UserEntity;
import com.rence.user.model.UserInfoDto;
import com.rence.user.repository.MypageMenuRepository;
import com.rence.user.repository.ReserveMileageRepository;
import com.rence.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class MypageMenuDAOImpl implements MypageMenuDAO {

	@Autowired
	ModelMapper modelmapper;

	@Autowired
	MypageMenuRepository menuRepository;

	@Autowired
	ReserveMileageRepository reserve_mileage_repository;

	@Autowired
	OfficePaymentRepository payment_repository;

	@Autowired
	OfficeReserveRepository reserve_repository;

	@Autowired
	OfficeMileageRepository mileage_repository;
	
	@Autowired
	UserRepository userRepository;
	

	/** ******************* **/
	/** 예약 상세 페이지 SECTION **/
	/** ******************* **/

	// 예약 상세정보
	@Override
	public ReserveInfo_ViewDto select_one_reserve_info(String reserve_no) {
		log.info("select_one_reserve_info()...");
		log.info("reserve_no: {}", reserve_no);

		ReserveInfo_ViewEntity entity = menuRepository.select_one_reserve_info(reserve_no);

		ReserveInfo_ViewDto dto = null;
		if (entity != null) {

			dto = modelmapper.map(entity, ReserveInfo_ViewDto.class);
		}

		return dto;
	}

	// 예약 마일리지 정보
	@Override
	public ReserveMileageDto select_one_reserve_mileage(String reserve_no) {
		log.info("select_one_reserve_mileage()...");
		log.info("reserve_no: {}", reserve_no);

		ReserveMileageEntity entity = reserve_mileage_repository.select_one_reserve_mileage(reserve_no);

		ReserveMileageDto dto = null;
		if (entity != null) {
			dto = modelmapper.map(entity, ReserveMileageDto.class);
		}
		return dto;
	}

	@Override
	public OfficePaymentDto select_one_cancel_payment(String reserve_no) {
		log.info("select_one_cancel_payment()...");
		log.info("reserve_no: {}", reserve_no);

		OfficePaymentEntity entity = payment_repository.select_one_cancel_payment(reserve_no);
		OfficePaymentDto dto = null;
		if (entity != null) {
			dto = modelmapper.map(entity, OfficePaymentDto.class);
		}
		return dto;
	}

	@Override
	public UserInfoDto select_one_user_info(String user_no) {
		log.info("select_one_user_info()...");

		// Base64 디코더 작업으로 userNo정보 가져오기
		byte[] decodedBytes = Base64.getDecoder().decode(user_no);
		user_no = new String(decodedBytes);
		log.info("reserve_no: {}", user_no);
		
		UserEntity entity = userRepository.select_one_user_info(user_no);
		UserInfoDto dto = new UserInfoDto();
		dto.setUser_no(entity.getUser_no());
		dto.setUser_name(entity.getUser_name());
		dto.setUser_email(entity.getUser_email());
		dto.setUser_tel(entity.getUser_tel());
		
		return dto;
	}

	@Override
	public int update_reserve_cancel(String reserve_no, String user_no) {
		log.info("update_reserve_cancel()...");
		log.info("reserve_no: {}", reserve_no);
		log.info("reserve_no: {}", user_no);

		int result = reserve_repository.update_reserve_cancel(reserve_no, user_no);

		return result;
	}

	@Override
	public List<OfficeMileageDto> select_all_mileage_cancel(String payment_no) {
		log.info("select_all_mileage_cancel()...");

		List<OfficeMileageEntity> entity_list = mileage_repository.select_all_mileage_cancel(payment_no);

		List<OfficeMileageDto> vos = null;
		if (entity_list != null) {
			vos = entity_list.stream().map(source -> modelmapper.map(source, OfficeMileageDto.class))
					.collect(Collectors.toList());
		}

		return vos;
	}

	@Override
	public OfficeMileageDto select_one_mileage_cancel(String payment_no, String mileage_state) {
		log.info("select_one_mileage_cancel()...");

		OfficeMileageEntity entity = mileage_repository.select_one_mileage_cancel(payment_no, mileage_state);

		OfficeMileageDto dto = null;
		if (entity != null) {
			dto = modelmapper.map(entity, OfficeMileageDto.class);
		}

		return dto;
	}

	@Override
	public int insert_mileage_changed(OfficeMileageDto dto) {
		log.info("insert_mileage_changed()...");

		int result = mileage_repository.insert_mileage_changed(dto);

		return result;
	}

	@Override
	public int update_mileage_state(String mileage_no) {
		int result = mileage_repository.update_mileage_state(mileage_no);

		return result;
	}

	@Override
	public int update_payment_cancel(String reserve_no, Integer cancel_amount) {
		int result = payment_repository.update_payment_cancel(reserve_no, cancel_amount);

		return result;
	}

	/** ******************* **/
	/** 후기 내역 페이지 SECTION **/
	/** ******************* **/

	@Override
	public int is_write_review(String room_no, String backoffice_no) {
		int result = menuRepository.is_write_review(room_no, backoffice_no);

		return result;
	}

	@Override
	public int insert_reviewOK(ReviewDto dto) {
		log.info("insert_reviewOK | {}", dto);
		int result = 0;

		try {
			menuRepository.insert_review(dto.getReview_point(), dto);
		} catch (Exception e) {
			result = -1;
		}
		return result;
	}

	@Override
	public int delete_review(String review_no) {
		int result = menuRepository.delete_review(review_no);

		return result;
	}

	/** ******************* **/
	/** 문의 내역 페이지 SECTION **/
	/** ******************* **/
	@Override
	public int delete_comment(String comment_no) {
		int result = menuRepository.delete_comment(comment_no);

		return result;
	}

}// end class
