/**
 * 
 * @author 최진실
 *
 */
package com.rence.dashboard.dao;

import java.util.List;

import com.rence.backoffice.model.BackOfficeDTO;
import com.rence.backoffice.model.BackOfficeOperatingTimeDTO;
import com.rence.dashboard.model.BOPaymentDTO;
import com.rence.dashboard.model.CommentDTO;
import com.rence.dashboard.model.CommentListQViewDTO;
import com.rence.dashboard.model.CommentSummaryViewDTO;
import com.rence.dashboard.model.ReservationViewDTO;
import com.rence.dashboard.model.ReserveListViewDTO;
import com.rence.dashboard.model.ReserveSummaryViewDTO;
import com.rence.dashboard.model.ReserveUpdateDTO;
import com.rence.dashboard.model.ReviewListViewDTO;
import com.rence.dashboard.model.RoomDTO;
import com.rence.dashboard.model.RoomSummaryViewDTO;
import com.rence.dashboard.model.SalesSettlementDetailViewDTO;
import com.rence.dashboard.model.SalesSettlementSummaryViewDTO;
import com.rence.dashboard.model.SalesSettlementViewDTO;
import com.rence.dashboard.model.ScheduleDTO;
import com.rence.dashboard.model.ScheduleListViewDTO;

public interface DashboardDAO {

	public List<ReserveSummaryViewDTO> reserve_summary_selectAll(String backoffice_no);

	public List<CommentSummaryViewDTO> comment_summary_selectAll(String backoffice_no);

	public SalesSettlementSummaryViewDTO payment_summary_selectOne(String backoffice_no);

	public RoomSummaryViewDTO room_summary_selectOne(String backoffice_no);

	public long dashboard_room_list_cnt(String backoffice_no);

	public List<RoomDTO> dashboard_room_list(String backoffice_no, Integer page);

	public BackOfficeDTO select_one_backoffice_info(String backoffice_no);

	public int backoffice_insertOK_room(String backoffice_no, RoomDTO rvo);

	public RoomDTO select_one_room_info(String backoffice_no, String room_no);

	public int backoffice_updateOK_room(String backoffice_no, RoomDTO rvo);

	public void backoffice_deleteOK_room(String backoffice_no, String room_no);

	public long backoffice_qna_selectAll_cnt(String backoffice_no);

	public List<CommentListQViewDTO> backoffice_qna_selectAll(String backoffice_no, Integer page);

	public CommentDTO backoffice_insert_comment(String backoffice_no, String room_no, String comment_no);

	public int backoffice_insertOK_comment(CommentDTO cvo, String backoffice_no, String comment_no);

	public int update_comment_state_T(String backoffice_no, String comment_no);

	public int backoffice_deleteOK_comment(String backoffice_no, String comment_no);

	public int update_comment_state_F(String backoffice_no, String mother_no);

	public long backoffice_review_selectAll_cnt(String backoffice_no);

	public List<ReviewListViewDTO> backoffice_review_selectAll(String backoffice_no, Integer page);

	public int backoffice_reserve_selectAll_cnt(String backoffice_no, String reserve_state);

	public List<ReserveListViewDTO> backoffice_reserve_selectAll(String backoffice_no, String reserve_state, int start_row, int end_row);

	public int backoffice_search_reserve_cnt(String backoffice_no, String searchword, String reserve_state);

	public List<ReserveListViewDTO> backoffice_search_reserve(String backoffice_no, String searchword,
			String reserve_state, int start_row, int end_row);

	public SalesSettlementDetailViewDTO backoffice_sales_selectOne(String backoffice_no, String sales_date);

	public long backoffice_sales_selectAll_cnt(String backoffice_no);

	public List<SalesSettlementViewDTO> backoffice_sales_selectAll(String backoffice_no, Integer page);

	public int backoffice_updateOK_sales(String backoffice_no, String room_no, String payment_no);

	public BackOfficeDTO backoffice_setting_selectOne(BackOfficeDTO bvo);

	public BackOfficeDTO backoffice_select_pw(BackOfficeDTO bvo);

	public int backoffice_setting_delete(BackOfficeDTO bvo);

	public int backoffice_room_deleteALL(BackOfficeDTO bvo);

	public BackOfficeOperatingTimeDTO backoffice_setting_selectOne_operatingtime(String backoffice_no);

	public int backoffice_updateOK_host(BackOfficeDTO bvo);

	public int backoffice_updateOK_opt(BackOfficeOperatingTimeDTO ovo);

	public List<ScheduleListViewDTO> backoffice_schedule_list(String backoffice_no, String not_sdate, String not_edate,
			String not_stime, String not_etime, String off_type, int min, int max);

	public int backoffice_schedueOK(String backoffice_no, String not_s, String not_e, String room_no);

	public int backoffice_reservation_cnt(String backoffice_no, String not_sdate, String not_edate, String not_stime,
			String not_etime, String room_no, String off_type);

	public List<ReservationViewDTO> backoffice_reservation(String backoffice_no, String not_sdate, String not_edate,
			String not_stime, String not_etime, String room_no, String off_type, int min, int max);

	public BOPaymentDTO backoffice_reservation_cancel(String backoffice_no, String reserve_no, String user_no);

	public BackOfficeDTO backoffice_select_companyname(String backoffice_no);

	public List<ScheduleDTO> backoffice_schedule_calendar(String backoffice_no);

	public RoomDTO backoffice_schedule_calendar_room_name(String room_no);

	public int backoffice_schedule_cancel(String backoffice_no, String schedule_no);

	public void reserve_state_auto_update();

	public ReserveUpdateDTO select_one_false_reserve(String reserve_stime, String reserve_etime, String room_no);

	public void reserve_auto_delete(String reserve_no);

	public int backoffice_room_cnt(String backoffice_no, String not_sdate, String not_edate, String not_stime, String not_etime, String off_type);

	public void backoffice_qna_insert(String backoffice_no, String room_no);

	public void backoffice_qna_insert(String backoffice_no);


}
