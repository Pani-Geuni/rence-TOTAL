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
import org.springframework.transaction.annotation.Transactional;

import com.rence.dashboard.model.ReserveUpdateDTO;
import com.rence.dashboard.model.ReserveUpdateEntity;

public interface ReserveAutoUpdateRepository extends JpaRepository<ReserveUpdateEntity, Object>{ // 예약 상태 업데이트

	@Query(nativeQuery = true, value = "select * from reserveinfo where reserve_state not in ('false','cancel')")
	public List<ReserveUpdateEntity> selectAll_reserve();

			
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "update reserveinfo set reserve_state = 'begin' where reserve_no=?1 and reserve_state not in ('false','cancel')")
	public void update_reserve_state_begin(String reserve_no);
	
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "update reserveinfo set reserve_state = 'in_use' where reserve_no=?1 and reserve_state not in ('false','cancel')")
	public void update_reserve_state_inuse(String reserve_no);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "update reserveinfo set reserve_state = 'end' where reserve_no=?1 and reserve_state not in ('false','cancel')")
	public void update_reserve_state_end(String reserve_no);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "update reserveinfo set reserve_state = 'cancel' where reserve_no=?1")
	public int update_reserve_state_cancel(String reserve_no);
	
	
	// 예약 상태 F가 10분 지난 reserve_no 가져오기
	@Query(nativeQuery = true, value = "select * from reserveinfo where reserve_stime=To_date(?1,'YYYY/MM/DD HH24:MI:SS') and reserve_etime=To_date(?2,'YYYY/MM/DD HH24:MI:SS') and reserve_state='false' and room_no=?3")
	public ReserveUpdateEntity select_one_false_reserve(String reserve_stime, String reserve_etime, String room_no);
	
	// 예약 false 상태 10분 뒤 삭제
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "delete from reserveinfo where reserve_no=?1")
	public void reserve_auto_delete(String reserve_no);


}
