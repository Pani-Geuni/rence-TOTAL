/**
 * 
 * @author 최진실
 *
 */
package com.rence.dashboard.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.rence.backoffice.model.BackOfficeDTO;
import com.rence.backoffice.model.BackOfficeOperatingTimeDTO;
import com.rence.dashboard.model.CommentDTO;
import com.rence.dashboard.model.ReserveUpdateDTO;
import com.rence.dashboard.model.RoomDTO;

public interface DashboardService {
	
	public Map<String, Object> dashboard_main(String backoffice_no);

	public Map<String, Object> dashboard_room_list(String backoffice_no, Integer page);

	public Map<String, Object> backoffice_insert_room(String backoffice_no);

	public Map<String, String> backoffice_insertOK_room(RoomDTO rvo, String backoffice_no);

	public Map<String, Object> backoffice_update_room(String backoffice_no, String room_no);

	public Map<String, String> backoffice_updateOK_room(String backoffice_no, RoomDTO rvo);

	public Map<String, String> backoffice_deleteOK_room(String backoffice_no, String room_no);

	public Map<String, Object> dashboard_qna(String backoffice_no, Integer page);

	public Map<String, Object> backoffice_insert_comment(String backoffice_no, String room_no, String comment_no);

	public Map<String, String> backoffice_insertOK_comment(String backoffice_no, CommentDTO cvo,
			String comment_no);

	public Map<String, String> backoffice_deleteOK_comment(String backoffice_no, String comment_no, String mother_no);

	public Map<String, Object> dashboard_review(String backoffice_no, Integer page);

	public Map<String, Object> dashboard_reserve(String backoffice_no, String reserve_state, Integer page);

	public Map<String, Object> dashboard_reserve_search(String backoffice_no, String searchword, String reserve_state,
			Integer page);

	public Map<String, Object> dashboard_sales_day(String backoffice_no, String sales_date, Integer page);

	public Map<String, String> backoffice_updateOK_sales(String backoffice_no, String room_no, String payment_no);

	public Map<String, Object> backoffice_settings(BackOfficeDTO bvo);

	public Map<String, String> backoffice_update_pw(BackOfficeDTO bvo);

	public Map<String, String> backoffice_setting_delete_rsu(BackOfficeDTO bvo);

	public Map<String, Object> backoffice_update_host(BackOfficeDTO bvo);

	public BackOfficeDTO backoffice_setting_selectOne(BackOfficeDTO bvo);

	public Map<String, String> backoffice_updateOK_host(BackOfficeDTO bvo, BackOfficeOperatingTimeDTO ovo);

	public Map<String, Object> backoffice_schedule_research(String backoffice_no, String not_sdate, String not_edate,
			String not_stime, String not_etime, String off_type, Integer page);

	public Map<String, Object> backoffice_schedule_research_paging(String backoffice_no, String not_sdate,
			String not_edate, String not_stime, String not_etime, String off_type, Integer page);

	public Map<String, String> backoffice_schedule_research_paging(String backoffice_no, String not_sdate,
			String not_edate, String not_stime, String not_etime, String room_no, String off_type);

	public Map<String, Object> backoffice_reservation(String backoffice_no, String room_no, String not_sdate,
			String not_edate, String not_stime, String not_etime, String off_type, Integer page);

	public Map<String, Object> backoffice_reservation_paging(String backoffice_no, String room_no, String not_sdate,
			String not_edate, String not_stime, String not_etime, String off_type, Integer page);

	public Map<String, Object> backoffice_schedule_calendar(String backoffice_no);

	public Map<String, Object> backoffice_schedule_cancel(String backoffice_no, String schedule_no);

	public void reserve_state_auto_update();

	public ReserveUpdateDTO select_one_false_reserve(String reserve_stime, String reserve_etime, String room_no);

	public void reserve_auto_delete(String reserve_no);


}
