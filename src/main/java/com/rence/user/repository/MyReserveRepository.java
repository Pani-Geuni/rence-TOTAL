/**
* @author 강경석	 
*/

package com.rence.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rence.user.model.MyPageReserveListEntity;


public interface MyReserveRepository extends JpaRepository<MyPageReserveListEntity, Object> {

	// 예약 리스트수-현재(페이징 처리를 위해서)
	@Query(nativeQuery = true, value = "select count(*) from USER_RESERVE_VIEW where user_no = ?1 and payment_total is not null and CURRENT_DATE <= reserve_sdate ")
	public long count_Reserve_now(String user_no);

	// 예약 리스트수-현재(페이징 처리를 위해서)
	@Query(nativeQuery = true, value = "select count(*) from USER_RESERVE_VIEW where user_no = ?1 and payment_total is not null and CURRENT_DATE > reserve_sdate ")
	public long count_Reserve_before(String user_no);


//	// 마이페이지- 현재 예약현황 리스트 - 페이징
	@Query(nativeQuery = true, value = "select * from(select ROWNUM as rn, u.* from (select * from USER_RESERVE_VIEW where user_no = ?1 and payment_total is not null and CURRENT_DATE <= reserve_sdate order by reserve_no desc) u) where rn between ?2 and ?3")
	public List<MyPageReserveListEntity> select_all_now_reserve_list_paging(String user_no, Integer start_row,
			Integer end_row);
	
	// 마이페이지- 과거 예약현황 리스트 - 페이징
	@Query(nativeQuery = true, value = "select * from(select ROWNUM as rn, u.* from (select * from USER_RESERVE_VIEW where user_no = ?1 and payment_total is not null and CURRENT_DATE > reserve_sdate order by reserve_no desc) u) where rn between ?2 and ?3")
	public List<MyPageReserveListEntity> select_all_before_reserve_list_paging(String user_no, Integer start_row,
			Integer end_row);


}// end class
