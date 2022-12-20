/**
 * 
 * @author 최진실
 *
 */
package com.rence.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rence.dashboard.model.ScheduleListViewEntity;

public interface ScheduleListRepository extends JpaRepository<ScheduleListViewEntity, Object> {

	// 일정 관리 - 리스트 해당 날짜에 예약이 있는 공간 -> vo에는 reserve_state 없음
	@Query(nativeQuery = true, value = "select B.* from( "
			+ "select ROW_NUMBER() OVER(PARTITION BY room_no ORDER BY room_no ASC ) no, A.* from ( "
			+ "select room_no, backoffice_no, room_type, room_name, reserve_cnt from "
			+ "(select count(reserve_no) OVER(PARTITION BY rm.room_no) as reserve_cnt, rm.room_no,rm.backoffice_no, rm.room_type, room_name, reserve_stime, reserve_etime, reserve_state  from reserveinfo rv , roominfo rm where rv.room_no=rm.room_no and reserve_state in ('begin', 'in_use') "
			+ "and ( ((reserve_stime > To_date(?2,'YYYY-MM-DD HH24:MI:SS') and reserve_stime < To_date(?3,'YYYY-MM-DD HH24:MI:SS') )or( reserve_etime > To_date(?2,'YYYY-MM-DD HH24:MI:SS') and reserve_etime < To_date(?3,'YYYY-MM-DD HH24:MI:SS'))) "
			+ "or (reserve_stime <= To_date(?2,'YYYY-MM-DD HH24:MI:SS') and reserve_etime >= To_date(?3,'YYYY-MM-DD HH24:MI:SS')) ) ) where backoffice_no=?1  "
			+ "union all "
			+ "select room_no, backoffice_no, room_type, room_name, reserve_cnt  from( "
			+ "select rm.room_no, rm.backoffice_no, rm.room_type, rm.room_name, 0 as reserve_cnt  from roominfo rm left outer join reserveinfo rv  "
			+ "on rm.room_no=rv.room_no where rm.room_no in ( "
			+ "select room_no from roominfo where backoffice_no=?1 and room_state!='F' "
			+ "minus "
			+ "select room_no from roomschedule where backoffice_no=?1  and (not_stime <= TO_DATE(?2,'YYYY-MM-DD HH24:MI:SS')) and (not_etime >= TO_DATE(?3,'YYYY-MM-DD HH24:MI:SS'))) ) "
			+ ")A )B where B.no=1 and rownum between ?4 and ?5")
	public List<ScheduleListViewEntity> backoffice_schedule_list(String backoffice_no, String reserve_stime, String reserve_etime, Integer min, Integer max);

	// 일정 갯수
	@Query(nativeQuery = true, value = "select count(*) from( "
			+ "select room_no, backoffice_no, room_type, room_name, reserve_cnt  from( "
			+ "select  ROW_NUMBER() OVER(PARTITION BY rm.room_no ORDER BY rm.room_no ASC ) no, rm.room_no, rm.backoffice_no, rm.room_type, rm.room_name, 0 as reserve_cnt  from roominfo rm left outer join reserveinfo rv  "
			+ "on rm.room_no=rv.room_no where rm.room_no in ( "
			+ "select room_no from roominfo where backoffice_no=?1 "
			+ "minus "
			+ "select room_no from roomschedule where backoffice_no=?1 and (not_stime <= TO_DATE(?2,'YYYY-MM-DD HH24:MI:SS')) and (not_etime >= TO_DATE(?3,'YYYY-MM-DD HH24:MI:SS'))))A where A.no=1)")
	public int backoffice_schedule_list_cnt(String backoffice_no, String reserve_stime, String reserve_etime);

}
