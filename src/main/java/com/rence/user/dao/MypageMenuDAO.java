package com.rence.user.dao;

import java.util.List;

import com.rence.office.model.OfficeMileageDto;
import com.rence.office.model.OfficePaymentDto;
import com.rence.user.controller.UserInfoDto;
import com.rence.user.model.ReserveInfo_ViewDto;
import com.rence.user.model.ReserveMileageDto;
import com.rence.user.model.ReviewDto;

public interface MypageMenuDAO {

	
	ReserveInfo_ViewDto select_one_reserve_info(String reserve_no);

	ReserveMileageDto select_one_reserve_mileage(String reserve_no);

	OfficePaymentDto select_one_cancel_payment(String reserve_no);

	UserInfoDto select_one_user_info(String user_no);

	int update_reserve_cancel(String reserve_no, String user_no);

	List<OfficeMileageDto> select_all_mileage_cancel(String payment_no);

	OfficeMileageDto select_one_mileage_cancel(String payment_no, String string);

	int insert_mileage_changed(OfficeMileageDto temp_vo);

	int update_mileage_state(String mileage_no);

	int update_payment_cancel(String reserve_no, Integer update_cancel_amount);

	int is_write_review(String room_no, String backoffice_no);

	int insert_reviewOK(ReviewDto dto);

	int delete_review(String review_no);

	int delete_comment(String comment_no);

	
}//end class
