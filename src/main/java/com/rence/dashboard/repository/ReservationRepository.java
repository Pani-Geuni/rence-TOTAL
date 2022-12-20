/**
 * 
 * @author 최진실
 *
 */
package com.rence.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rence.dashboard.model.ReservationViewEntity;

public interface ReservationRepository extends JpaRepository<ReservationViewEntity, Object>{

	// 예약자 리스트
	@Query(nativeQuery = true, value = "select * from RESERVATION_VIEW where backoffice_no=?1 and ( ((reserve_stime > To_date(?2,'YYYY-MM-DD HH24:MI:SS') and reserve_stime < To_date(?3,'YYYY-MM-DD HH24:MI:SS') )or( reserve_etime > To_date(?2,'YYYY-MM-DD HH24:MI:SS') and reserve_etime < To_date(?3,'YYYY-MM-DD HH24:MI:SS')))"
			+ "or (reserve_stime <= To_date(?2,'YYYY-MM-DD HH24:MI:SS') and reserve_etime >= To_date(?3,'YYYY-MM-DD HH24:MI:SS')) ) and room_no=?4 and rownum between ?5 and ?6")
	public List<ReservationViewEntity> backoffice_reservation_list(String backoffice_no, String reserve_stime, String reserve_etime,
			String room_no, Integer min, Integer max);

	// 예약자 갯수
	@Query(nativeQuery = true, value = "select count(*) from (select * from RESERVATION_VIEW where backoffice_no=?1 and ((reserve_stime between To_date(?2,'YYYY-MM-DD HH24:MI:SS') and To_date(?3,'YYYY-MM-DD HH24:MI:SS'))or(reserve_etime between To_date(?2,'YYYY-MM-DD HH24:MI:SS') and To_date(?3,'YYYY-MM-DD HH24:MI:SS'))) and room_no=?4)")
	public int backoffice_reservation_list_cnt(String backoffice_no, String reserve_stime,
			String reserve_etime, String room_no);

}

//****[RESERVATION_VIEW] view code****
//*
//select u.user_no, user_name, user_email, user_tel ,reserve_stime, reserve_etime, rv.backoffice_no, rv.room_no, rv.reserve_state, rv.reserve_no from userinfo u, reserveinfo rv where u.user_no = rv.user_no and reserve_state in ('in_use', 'begin') order by reserve_stime desc;
//*
//***************************************
