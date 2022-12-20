package com.rence.office.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rence.office.model.Comment_Dto;
import com.rence.office.model.ListViewDto;
import com.rence.office.model.ListViewEntity;
import com.rence.office.model.OfficeInfo_ViewDto;
import com.rence.office.model.OfficeInfo_ViewEntity;
import com.rence.office.model.OfficeMileageDto;
import com.rence.office.model.OfficeMileageEntity;
import com.rence.office.model.OfficeOperatingTimeDto_date;
import com.rence.office.model.OfficeOperatingTimeEntity;
import com.rence.office.model.OfficePaymentDto;
import com.rence.office.model.OfficePaymentEntity;
import com.rence.office.model.OfficeQuestionDto;
import com.rence.office.model.OfficeQuestionEntity;
import com.rence.office.model.OfficeReserveDto;
import com.rence.office.model.OfficeReserveDto_date;
import com.rence.office.model.OfficeReserveEntity;
import com.rence.office.model.OfficeReview_ViewDto;
import com.rence.office.model.OfficeReview_ViewEntity;
import com.rence.office.model.OfficeRoomDto;
import com.rence.office.model.OfficeRoomEntity;
import com.rence.office.model.PaymentInfoDto;
import com.rence.office.model.PaymentInfoEntity;
import com.rence.office.model.RoomScheduleDto;
import com.rence.office.model.RoomScheduleEntity;
import com.rence.office.repo.OfficeDetailInfoRepository;
import com.rence.office.repo.OfficeInfoRepository;
import com.rence.office.repo.OfficeListRepository;
import com.rence.office.repo.OfficeMileageRepository;
import com.rence.office.repo.OfficeOperatingTimeRepository;
import com.rence.office.repo.OfficePaymentInfoRepository;
import com.rence.office.repo.OfficePaymentRepository;
import com.rence.office.repo.OfficeQuestionRepository;
import com.rence.office.repo.OfficeReserveDateRepository;
import com.rence.office.repo.OfficeReserveRepository;
import com.rence.office.repo.OfficeReviewRepository;
import com.rence.office.repo.OfficeRoomRepository;
import com.rence.office.repo.RoomScheduleRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class officeDAOImpl implements officeDAO {

	@Autowired
	ModelMapper modelmapper;

	@Autowired
	OfficeDetailInfoRepository detail_repository;

	@Autowired
	OfficeOperatingTimeRepository operating_repository;

	@Autowired
	OfficeRoomRepository room_repository;

	@Autowired
	OfficeQuestionRepository question_repository;

	@Autowired
	OfficeReviewRepository review_repository;

	@Autowired
	OfficeReserveRepository reserve_repository;

	@Autowired
	RoomScheduleRepository room_schedule_repository;

	@Autowired
	OfficeReserveDateRepository reserve_date_repository;

	@Autowired
	OfficePaymentInfoRepository payment_info_repository;

	@Autowired
	OfficePaymentRepository payment_repository;

	@Autowired
	OfficeMileageRepository mileage_repository;

	@Autowired
	OfficeInfoRepository repository;

	@Autowired
	OfficeListRepository list_repository;

	@Override
	public OfficeInfo_ViewDto select_one_office_info(String backoffice_no) {
		log.info("select_one_office_info()...");
		log.info("backoffice_no: {}", backoffice_no);

		OfficeInfo_ViewEntity entity = detail_repository.select_one_office_info(backoffice_no);
		log.info("entity: {}", entity);

		OfficeInfo_ViewDto dto = modelmapper.map(entity, OfficeInfo_ViewDto.class);

		return dto;
	}

	// backoffice 운영 시간
	@Override
	public OfficeOperatingTimeDto_date select_one_operating_time(String backoffice_no) {
		log.info("select_one_operating_time()...");

		OfficeOperatingTimeEntity entity = operating_repository.select_one_operating_time(backoffice_no);

		OfficeOperatingTimeDto_date dto = modelmapper.map(entity, OfficeOperatingTimeDto_date.class);

		return dto;
	}

	// backoffice 운영 공간(Room)
	@Override
	public List<OfficeRoomDto> select_all_room(String backoffice_no) {
		log.info("select_all_room()...");

		List<OfficeRoomEntity> entity_list = room_repository.select_all_room_info(backoffice_no);

		List<OfficeRoomDto> dtos = null;
		if(entity_list != null) {
			dtos = entity_list.stream().map(source -> modelmapper.map(source, OfficeRoomDto.class))
					.collect(Collectors.toList());
		}

		return dtos;
	}

	@Override
	public long total_rowCount_question_all(String backoffice_no) {
		log.info("total_rowCount_question_all()...");
		return question_repository.total_rowCount_question_all(backoffice_no);
	}

	@Override
	public List<OfficeQuestionDto> select_all_comment(String backoffice_no, Integer page) {
		log.info("select_all_comment()...");

		Integer row_count = 4;
		Integer start_row = (page - 1) * row_count + 1;
		Integer end_row = page * row_count;

		List<OfficeQuestionDto> dtos = null;
		List<OfficeQuestionEntity> entity_list = question_repository.select_all_comment(backoffice_no, start_row,
				end_row);

		if (entity_list != null) {
			dtos = entity_list.stream().map(source -> modelmapper.map(source, OfficeQuestionDto.class))
					.collect(Collectors.toList());
		}

		return dtos;
	}

	@Override
	public OfficeQuestionDto select_one_answer(String mother_no) {
		log.info("select_one_answer()...");
		log.info("mother_no: {}", mother_no);
		OfficeQuestionDto dto = null;
		OfficeQuestionEntity entity = question_repository.select_one_answer(mother_no);

		if (entity != null) {
			dto = modelmapper.map(entity, OfficeQuestionDto.class);
		}

		return dto;
	}

	@Override
	public long total_rowCount_review_all(String backoffice_no) {
		log.info("total_rowCount_review_all()...");
		return review_repository.total_rowCount_review_all(backoffice_no);
	}

	@Override
	public List<OfficeReview_ViewDto> select_all_review(String backoffice_no, Integer page) {
		log.info("select_all_review()...");

		Integer row_count = 4;
		Integer start_row = (page - 1) * row_count + 1;
		Integer end_row = page * row_count;

		List<OfficeReview_ViewEntity> entity_list = review_repository.select_all_review(backoffice_no, start_row,
				end_row);

		List<OfficeReview_ViewDto> dtos = null;
		if (entity_list != null) {
			dtos = entity_list.stream().map(source -> modelmapper.map(source, OfficeReview_ViewDto.class))
					.collect(Collectors.toList());
		}

		return dtos;
	}

	@Override
	public List<OfficeReserveDto> check_reserve(String backoffice_no, String room_no, String reserve_stime) {
		log.info("check_reserve()....");
		log.info("===backoffice_no: {}",backoffice_no );
		log.info("===room_no: {}",room_no );
		log.info("===reserve_stime: {}",reserve_stime );

		List<OfficeReserveEntity> entity_list = reserve_repository.select_all_reserve(backoffice_no, room_no,
				reserve_stime);
		
		log.info("===entity_list: {}",entity_list );

		List<OfficeReserveDto> dtos = null;
		if (entity_list != null) {
			dtos = entity_list.stream().map(source -> modelmapper.map(source, OfficeReserveDto.class))
					.collect(Collectors.toList());
		}

		return dtos;
	}

	@Override
	public List<RoomScheduleDto> select_all_room_schedule(String backoffice_no, String room_no, String reserve_stime) {
		log.info("select_all_room_schedule()...");
		log.info("===backoffice_no: {}",backoffice_no );
		log.info("===room_no: {}",room_no );
		log.info("===reserve_stime: {}",reserve_stime );

		List<RoomScheduleEntity> entity_list = room_schedule_repository.select_all_room_schedule(backoffice_no, room_no,
				reserve_stime);
		log.info("===entity_list: {}",entity_list );
		List<RoomScheduleDto> dtos = null;
		if (entity_list != null) {
			dtos = entity_list.stream().map(source -> modelmapper.map(source, RoomScheduleDto.class))
					.collect(Collectors.toList());
		}
		return dtos;
	}

	@Override
	public List<RoomScheduleDto> select_all_room_schedule_dayoff(String backoffice_no, String room_no) {
		log.info("select_all_room_schedule()...");
		log.info("===backoffice_no: {}",backoffice_no );
		log.info("===room_no: {}",room_no );
		
		
		List<RoomScheduleEntity> entity_list = room_schedule_repository.select_all_room_schedule_dayoff(backoffice_no,
				room_no);
		log.info("===entity_list: {}",entity_list );
		List<RoomScheduleDto> dtos = null;
		if (entity_list != null) {
			dtos = entity_list.stream().map(source -> modelmapper.map(source, RoomScheduleDto.class))
					.collect(Collectors.toList());
		}
		return dtos;
	}

	@Override
	public List<OfficeReserveDto> check_reserve_office(String backoffice_no, String room_no) {
		log.info("check_reserve_office()...");

		List<OfficeReserveEntity> entity_list = reserve_repository.select_all_reserve_office(backoffice_no, room_no);

		List<OfficeReserveDto> dtos = null;
		if (entity_list != null) {
			dtos = entity_list.stream().map(source -> modelmapper.map(source, OfficeReserveDto.class))
					.collect(Collectors.toList());
		}

		return dtos;
	}

	@Override
	public int confirm_reserve(OfficeReserveDto_date rdto) {
		log.info("confirm_reserve()...");
		int result = reserve_date_repository.insert_reserve(rdto);

		return result;
	}

	@Override
	public String select_one_last_reserve(String user_no) {
		log.info("select_one_last_reserve()...");

		OfficeReserveEntity entity = reserve_repository.select_one_reserve_no(user_no);

		OfficeReserveDto dto = null;
		if (entity != null) {
			dto = modelmapper.map(entity, OfficeReserveDto.class);
		}

		String reserve_no = dto.getReserve_no();

		return reserve_no;
	}

	@Override
	public PaymentInfoDto select_one_final_payment_info(String reserve_no) {
		log.info("select_one_final_payment_info()...");

		PaymentInfoEntity entity = payment_info_repository.select_one_final_payment_info(reserve_no);
		PaymentInfoDto dto = null;
		if (entity != null) {
			dto = modelmapper.map(entity, PaymentInfoDto.class);
		}

		return dto;

	}

	@Override
	public int insert_paymentOK(OfficePaymentDto pdto) {
		log.info("insert_paymentOK()...");
		int result = payment_repository.insert_payment_info(pdto);

		return result;
	}

	@Override
	public int update_reserve_state(String reserve_no) {
		log.info("update_reserve_state()...");
		int result = reserve_repository.update_reserve_state(reserve_no);

		return result;
	}

	@Override
	public OfficeMileageDto select_one_recent_mileage(String user_no) {
		log.info("select_one_recent_mileage()...");

		OfficeMileageEntity entity = mileage_repository.select_one_recent_mileage(user_no);
		OfficeMileageDto dto = null;
		if (entity != null) {
			dto = modelmapper.map(entity, OfficeMileageDto.class);
		}

		return dto;
	}

	@Override
	public OfficePaymentDto select_one_recent_payment(String user_no) {
		log.info("select_one_recent_payment()...");

		OfficePaymentEntity entity = payment_repository.select_one_recent_payment(user_no);
		OfficePaymentDto dto = null;
		if (entity != null) {
			dto = modelmapper.map(entity, OfficePaymentDto.class);
		}
		return dto;
	}

	@Override
	public int insert_mileage_changed(OfficeMileageDto mdto) {
		log.info("insert_mileage_changed()...");
		int result = mileage_repository.insert_mileage_changed(mdto);

		return result;
	}

	@Override
	public int insert_question(Comment_Dto dto) {
		log.info("insert_question()...");
		int result = repository.insert_question(dto);

		return result;

	}

	@Override
	public int list_totalCnt(String type) {
		log.info("insert_question()...");
		int cnt = list_repository.list_totalCnt(type);

		return cnt;
	}

	@Override
	public List<ListViewDto> select_all_list(String type, String condition, int min, int max) {
		log.info("insert_question()...");

		List<ListViewEntity> entity_list = null;

		if (condition.equals("date")) {
			entity_list = list_repository.selectAll_orderBy_date("%" + type + "%", min, max);
		} else if (condition.equals("rating")) {
			entity_list = list_repository.selectAll_orderBy_rating("%" + type + "%", min, max);
		} else if (condition.equals("cheap")) {
			entity_list = list_repository.selectAll_orderBy_cheap("%" + type + "%", min, max);
		} else if (condition.equals("expensive")) {
			entity_list = list_repository.selectAll_orderBy_expensive("%" + type + "%", min, max);
		}
		List<ListViewDto> dtos = null;
		if (entity_list != null) {
			dtos = entity_list.stream().map(source -> modelmapper.map(source, ListViewDto.class))
					.collect(Collectors.toList());
		}

		return dtos;
	}

	@Override
	public List<ListViewDto> search_list(String type, String location, String searchWord, String condition, int min,
			int max) {
		log.info("search_list()...");

		List<ListViewEntity> entity_list = null;
		if (condition.equals("date")) {
			entity_list = list_repository.searchAll_orderBy_date("%" + type + "%", "%" + location + "%",
					"%" + searchWord + "%", min, max);
		} else if (condition.equals("rating")) {
			entity_list = list_repository.searchAll_orderBy_rating("%" + type + "%", "%" + location + "%",
					"%" + searchWord + "%", min, max);
		} else if (condition.equals("cheap")) {
			entity_list = list_repository.searchAll_orderBy_cheap("%" + type + "%", "%" + location + "%",
					"%" + searchWord + "%", min, max);
		} else if (condition.equals("expensive")) {
			entity_list = list_repository.searchAll_orderBy_expensive("%" + type + "%", "%" + location + "%",
					"%" + searchWord + "%", min, max);
		}

		List<ListViewDto> dtos = null;
		if (entity_list != null) {
			dtos = entity_list.stream().map(source -> modelmapper.map(source, ListViewDto.class))
					.collect(Collectors.toList());
		}

		return dtos;
	}

}
// end class
