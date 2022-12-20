package com.rence.office.dao;

import java.util.List;

import com.rence.office.model.Comment_Dto;
import com.rence.office.model.ListViewDto;
import com.rence.office.model.OfficeInfo_ViewDto;
import com.rence.office.model.OfficeMileageDto;
import com.rence.office.model.OfficeOperatingTimeDto_date;
import com.rence.office.model.OfficePaymentDto;
import com.rence.office.model.OfficeQuestionDto;
import com.rence.office.model.OfficeReserveDto;
import com.rence.office.model.OfficeReserveDto_date;
import com.rence.office.model.OfficeReview_ViewDto;
import com.rence.office.model.OfficeRoomDto;
import com.rence.office.model.PaymentInfoDto;
import com.rence.office.model.RoomScheduleDto;

public interface officeDAO {

	OfficeInfo_ViewDto select_one_office_info(String backoffice_no);

	//backoffice 운영 시간
	OfficeOperatingTimeDto_date select_one_operating_time(String backoffice_no);

	// backoffice 운영 공간(Room)
	List<OfficeRoomDto> select_all_room(String backoffice_no);

	long total_rowCount_question_all(String backoffice_no);

	List<OfficeQuestionDto> select_all_comment(String backoffice_no, Integer page);

	OfficeQuestionDto select_one_answer(String comment_no);

	long total_rowCount_review_all(String backoffice_no);

	List<OfficeReview_ViewDto> select_all_review(String backoffice_no, Integer page);

	List<OfficeReserveDto> check_reserve(String backoffice_no, String room_no, String reserve_date);

	List<RoomScheduleDto> select_all_room_schedule(String backoffice_no, String room_no, String reserve_date);

	List<RoomScheduleDto> select_all_room_schedule_dayoff(String backoffice_no, String room_no);

	List<OfficeReserveDto> check_reserve_office(String backoffice_no, String room_no);

	int confirm_reserve(OfficeReserveDto_date date_dto);

	String select_one_last_reserve(String user_no);

	PaymentInfoDto select_one_final_payment_info(String reserve_no);

	int insert_paymentOK(OfficePaymentDto pdto);

	int update_reserve_state(String reserve_no);

	OfficeMileageDto select_one_recent_mileage(String user_no);

	OfficePaymentDto select_one_recent_payment(String user_no);

	int insert_mileage_changed(OfficeMileageDto mdto2);

	int insert_question(Comment_Dto dto);

	int list_totalCnt(String string);

	List<ListViewDto> select_all_list(String type, String condition, int i, int j);

	List<ListViewDto> search_list(String type, String location, String searchWord, String condition, int i, int j);

}
