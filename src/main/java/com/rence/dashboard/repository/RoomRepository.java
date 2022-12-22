/**
 * 
 * @author 최진실
 *
 */
package com.rence.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.rence.dashboard.model.RoomEntity;

public interface RoomRepository extends JpaRepository<RoomEntity, Object> {

	// 공간 관리 - 리스트
	@Query(nativeQuery = true, value = "select * from (select ROWNUM as num, A.* from (select room_no, room_type, room_name, TO_CHAR(room_price) as room_price, backoffice_no, room_state from roominfo where backoffice_no=?1 and room_state!='F' order by room_no desc)A )where num between ?2 and ?3")
	public List<RoomEntity> selectAll_room_list(String backoffice_no, Integer start_row, Integer end_row);

	// ********페이징*********
	@Query(nativeQuery = true, value = "select count(*) from(select room_no, room_type, room_name, TO_CHAR(room_price) as room_price, backoffice_no, room_state from roominfo where backoffice_no=?1 and room_state!='F' order by room_no asc)")
	public long dashboard_room_list_cnt(String backoffice_no);

	// 공간 관리 - 추가
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "INSERT INTO roominfo(room_no, room_name, room_type, backoffice_no, room_price, room_state) VALUES('RM'||SEQ_ROOM.NEXTVAL, :#{#rvo?.room_name}, :#{#rvo?.room_type}, :#{#rvo?.backoffice_no}, :#{#rvo?.room_price}, :#{#rvo?.room_state})")
	public int backoffice_insertOK_room(@Param("rvo") RoomEntity rvo);

	// 공간 수정 - 팝업
	@Query(nativeQuery = true, value = "select * from roominfo where backoffice_no=?1 and room_no=?2")
	public RoomEntity select_one_room_info(String backoffice_no, String room_no);

	// 공간 관리 - 수정
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "update roominfo set room_name=:#{#rvo?.room_name}, room_type=:#{#rvo?.room_type}, room_price=:#{#rvo?.room_price} where backoffice_no=:#{#rvo?.backoffice_no} and room_no=:#{#rvo?.room_no}")
	public int backoffice_updateOK_room(@Param("rvo") RoomEntity rvo);

	// 공간 삭제
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "update roominfo set room_state='F' where backoffice_no=?1 and room_no=?2 and room_no not in (select room_no from reserveinfo where backoffice_no=?1 and room_no=?2 and (reserve_state='begin' or reserve_state='in_use'))")
	public int backoffice_deleteOK_room(String backoffice_no, String room_no);

	
	// 휴무 일정 - 공간 이름
	@Query(nativeQuery = true, value = "select * from roominfo where room_no=?1")
	public RoomEntity backoffice_schedule_calendar_room_name(String room_no);

}
