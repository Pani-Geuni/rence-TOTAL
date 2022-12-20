/**
 * 
 * @author 최진실
 *
 */
package com.rence.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rence.dashboard.model.RoomSummaryViewEntity;

public interface RoomSummaryRepository extends JpaRepository<RoomSummaryViewEntity, Object>{ // 공간 요약

	@Query(nativeQuery = true, value = "select  "
			+ "NVL((select round(avg(review_point),1) from review group by backoffice_no having backoffice_no=?1),0) review_point, "
			+ "NVL((select count(comment_no) from comments where user_no is not null group by backoffice_no having backoffice_no=?1),0) comment_no, "
			+ "NVL((select count(review_no) from review group by backoffice_no having backoffice_no=?1),0) review_no, "
			+ "NVL((select count(reserve_no) from reserveinfo where reserve_state!='false' group by backoffice_no having backoffice_no=?1),0) reserve_no "
			+ "from dual")
	public RoomSummaryViewEntity select_room_summary(String backoffice_no);

}