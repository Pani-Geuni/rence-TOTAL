package com.rence.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rence.user.model.MileageEntity;
import com.rence.user.model.UserMileageEntity;

public interface MileageRepository extends JpaRepository<MileageEntity, Object> {

	// 마이페이지 총 마일리지
	//@Query(nativeQuery = true, value = "select * from( select * from USER_DETAIL_MILEAGE_VIEW where (state ='T' or state ='F')  and user_no=?1 order by no desc)WHERE ROWNUM between 1 and 1")
	@Query(nativeQuery = true, value = "select * from( select * from mileage where MILEAGE_STATE != 'w' and user_no=?1 order by MILEAGE_NO desc)WHERE ROWNUM between 1 and 1")
	public MileageEntity totalMileage_selectOne(String user_no);

}//end class
