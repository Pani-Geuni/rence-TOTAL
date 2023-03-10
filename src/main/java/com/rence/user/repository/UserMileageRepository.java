/**
* @author 강경석
*/
package com.rence.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rence.user.model.MileageEntity;
import com.rence.user.model.UserMileageEntity;

public interface UserMileageRepository extends JpaRepository<UserMileageEntity, Object> {

	
	
	// 마일리지 리스트수-전체(페이징 처리를 위해서) all
	@Query(nativeQuery = true, value = "select count(*) from user_detail_mileage_view"
			+ " where (state = 'T' or state = 'F') and user_no = ?1")
	public long count_allmileage(String user_no);

	
	// 마일리지 리스트수-적립(페이징 처리를 위해서) plus
	@Query(nativeQuery = true, value = "select count(*) from user_detail_mileage_view where user_no = ?1 and state='T'")
	public long count_plusmileage(String user_no);
	
	// 마일리지 리스트수-사용(페이징 처리를 위해서) minus
	@Query(nativeQuery = true, value = "select count(*) from user_detail_mileage_view where user_no = ?1 and state='F'")
	public long count_minusmileage(String user_no);

	
	// 마일리지 리스트 all 페이징
	@Query(nativeQuery = true, 
			value = "select  * from (select ROWNUM as rn, u.* from (select *  from user_detail_mileage_view  where (state = 'T' or state = 'F') and user_no = ?1 order by no desc) u) where rn between ?2 and ?3")
	public List<UserMileageEntity> user_mileage_selectAll_paging(String user_no, Integer start_row, Integer end_row);
	
	
	// user_mileage_search_list paging
	//적립
	@Query(nativeQuery = true, value = "select  * from (select ROWNUM as rn, u.* from (select *  from user_detail_mileage_view  where  user_no = ?1 and state='T' order by no desc) u) where rn between ?2 and ?3")
	public List<UserMileageEntity> mileage_search_list_plus_paging(String user_no, Integer start_row, Integer end_row);
	//사용
	@Query(nativeQuery = true, value = "select  * from (select ROWNUM as rn, u.* from (select *  from user_detail_mileage_view  where  user_no = ?1 and state='F' order by no desc) u) where rn between ?2 and ?3")
	public List<UserMileageEntity> mileage_search_list_minus_paging(String user_no, Integer start_row, Integer end_row);


}// end class
