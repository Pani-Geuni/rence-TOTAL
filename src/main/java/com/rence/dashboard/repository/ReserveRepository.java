/**
 * 
 * @author 최진실
 *
 */
package com.rence.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rence.dashboard.model.ReserveListViewEntity;


public interface ReserveRepository extends JpaRepository<ReserveListViewEntity, Object> {

	// 예약 관리 - 리스트(전체)
	@Query(nativeQuery = true, value="select reserve_no,reserve_sdate,reserve_edate,reserve_state,user_no,backoffice_no,room_name,user_name,user_email,user_tel,actual_payment,payment_state from reserve_list_b_view where backoffice_no=?1 and reserve_state !='false' and rownum between ?2 and ?3")
	public List<ReserveListViewEntity> backoffice_reserve_selectAll(String backoffice_no, Integer start_row, Integer end_row);
	
	// 예약 관리 - 리스트(이용중) in_use
	@Query(nativeQuery = true, value="select reserve_no,reserve_sdate,reserve_edate,reserve_state,user_no,backoffice_no,room_name,user_name,user_email,user_tel,actual_payment,payment_state from reserve_list_b_view where backoffice_no=?1 and reserve_state in ('begin', 'in_use') and rownum between ?2 and ?3")
	public List<ReserveListViewEntity> backoffice_reserve_selectAll_inuse(String backoffice_no, Integer start_row, Integer end_row);
	
	// 예약 관리 - 리스트(종료) end
	@Query(nativeQuery = true, value="select reserve_no,reserve_sdate,reserve_edate,reserve_state,user_no,backoffice_no,room_name,user_name,user_email,user_tel,actual_payment,payment_state from reserve_list_b_view where backoffice_no=?1 and reserve_state = 'end' and rownum between ?2 and ?3")
	public List<ReserveListViewEntity> backoffice_reserve_selectAll_end(String backoffice_no, Integer start_row, Integer end_row);
	
	// 예약 관리 - 리스트(취소) cancel
	@Query(nativeQuery = true, value="select reserve_no,reserve_sdate,reserve_edate,reserve_state,user_no,backoffice_no,room_name,user_name,user_email,user_tel,actual_payment,payment_state from reserve_list_b_view where backoffice_no=?1 and reserve_state = 'cancel' and rownum between ?2 and ?3")
	public List<ReserveListViewEntity> backoffice_reserve_selectAll_cancel(String backoffice_no, Integer start_row, Integer end_row);

	// 예약 관리 - 리스트(전체 검색) 
	@Query(nativeQuery = true, value="select reserve_no,reserve_sdate,reserve_edate,reserve_state,user_no,backoffice_no,room_name,user_name,user_email,user_tel,actual_payment,payment_state from reserve_list_b_view where backoffice_no=?1 and reserve_state !='false' and user_name like ?4 and rownum between ?2 and ?3")
	public List<ReserveListViewEntity> backoffice_reserve_selectAll_search(String backoffice_no, Integer start_row,
			Integer end_row, String searchword);

	// 예약 관리 - 리스트(이용중 검색) in_use
	@Query(nativeQuery = true, value="select reserve_no,reserve_sdate,reserve_edate,reserve_state,user_no,backoffice_no,room_name,user_name,user_email,user_tel,actual_payment,payment_state from reserve_list_b_view where backoffice_no=?1 and reserve_state in ('begin', 'in_use') and user_name like ?4 and rownum between ?2 and ?3")
	public List<ReserveListViewEntity> backoffice_reserve_selectAll_inuse_search(String backoffice_no, Integer start_row, Integer end_row, String searchword);
	
	// 예약 관리 - 리스트(종료 검색) end
	@Query(nativeQuery = true, value="select reserve_no,reserve_sdate,reserve_edate,reserve_state,user_no,backoffice_no,room_name,user_name,user_email,user_tel,actual_payment,payment_state from reserve_list_b_view where backoffice_no=?1 and reserve_state = 'end' and user_name like ?4 and rownum between ?2 and ?3")
	public List<ReserveListViewEntity> backoffice_reserve_selectAll_end_search(String backoffice_no, Integer start_row, Integer end_row, String searchword);
	
	// 예약 관리 - 리스트(취소 검색) cancel
	@Query(nativeQuery = true, value="select reserve_no,reserve_sdate,reserve_edate,reserve_state,user_no,backoffice_no,room_name,user_name,user_email,user_tel,actual_payment,payment_state from reserve_list_b_view where backoffice_no=?1 and reserve_state = 'cancel' and user_name like ?4 and rownum between ?2 and ?3")
	public List<ReserveListViewEntity> backoffice_reserve_selectAll_cancel_search(String backoffice_no, Integer start_row, Integer end_row, String searchword);

	//*************페이징*****************//
	// 예약 관리 - 리스트(전체) 갯수
	@Query(nativeQuery = true, value="select count(*) from reserve_list_b_view where backoffice_no=?1 and reserve_state!='false'")
	public int backoffice_reserve_selectAll(String backoffice_no);

	// 예약 관리 - 리스트(이용중) in_use
	@Query(nativeQuery = true, value="select count(*) from reserve_list_b_view where backoffice_no='B1001' and reserve_state in ('begin', 'in_use')")
	public int backoffice_reserve_selectAll_inuse(String backoffice_no);

	// 예약 관리 - 리스트(종료) end
	@Query(nativeQuery = true, value="select count(*) from reserve_list_b_view where backoffice_no=?1 and reserve_state = 'end'")
	public int backoffice_reserve_selectAll_end(String backoffice_no);

	// 예약 관리 - 리스트(취소) cancel
	@Query(nativeQuery = true, value="select count(*) from reserve_list_b_view where backoffice_no=?1 and reserve_state='cancel'")
	public int backoffice_reserve_selectAll_cancel(String backoffice_no);

	// 예약 관리 - 리스트(전체 검색) 
	@Query(nativeQuery = true, value="select count(*) from reserve_list_b_view where backoffice_no=?1 and reserve_state!='false' and user_name like ?2")
	public int backoffice_reserve_selectAll_search(String backoffice_no, String string);

	// 예약 관리 - 리스트(이용중 검색) in_use
	@Query(nativeQuery = true, value="select count(*) from reserve_list_b_view where backoffice_no=?1 and reserve_state in ('begin', 'in_use') and user_name like ?2")
	public int backoffice_reserve_selectAll_inuse_search(String backoffice_no, String string);

	// 예약 관리 - 리스트(종료 검색) end
	@Query(nativeQuery = true, value="select count(*) from reserve_list_b_view where backoffice_no=?1 and reserve_state = 'end' and user_name like ?2")
	public int backoffice_reserve_selectAll_end_search(String backoffice_no, String string);

	// 예약 관리 - 리스트(취소 검색) cancel
	@Query(nativeQuery = true, value="select count(*) from reserve_list_b_view where backoffice_no=?1 and reserve_state = 'cancel' and user_name like ?2")
	public int backoffice_reserve_selectAll_cancel_search(String backoffice_no, String string);

	
}

//****[RESERVE_LIST_B_VIEW] view code****
//*
//select rownum as rnum, rv.reserve_no, TO_CHAR(reserve_sdate, 'YYYY-MM-DD HH24:MI:SS') as reserve_sdate, TO_CHAR(reserve_edate, 'YYYY-MM-DD HH24:MI:SS') as reserve_edate, room_name, rv.user_no, user_name, user_tel, user_email, TO_CHAR(actual_payment) as actual_payment, payment_state, reserve_state, rv.backoffice_no
//from reserveinfo rv, roominfo rm, paymentinfo p, userinfo u where rv.room_no=rm.room_no and rv.reserve_no=p.reserve_no and rv.user_no=u.user_no order by reserve_stime desc ;
//*
//***************************************

