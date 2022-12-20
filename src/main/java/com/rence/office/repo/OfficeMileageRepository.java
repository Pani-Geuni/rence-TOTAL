package com.rence.office.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.rence.office.model.OfficeMileageDto;
import com.rence.office.model.OfficeMileageEntity;

public interface OfficeMileageRepository extends JpaRepository<OfficeMileageEntity, Object> {

	
	@Query(nativeQuery = true,
			value ="select * from ("
					+ "select *"
					+ "from mileage "
					+ "where user_no=?1 "
					+ "order by mileage_no desc "
					+ ") where rownum=1")
	public OfficeMileageEntity select_one_recent_mileage(String user_no);
	
	@Query(nativeQuery = true, value = "select * from mileage where payment_no=?1")
	public List<OfficeMileageEntity> select_all_mileage_cancel(String payment_no);

	@Query(nativeQuery = true, value = "select * from mileage where payment_no=?1 and mileage_state=?2")
	public OfficeMileageEntity select_one_mileage_cancel(String payment_no, String mileage_state);


	@Modifying
	@Transactional
	@Query(nativeQuery = true,
			value = "insert into "
					+ "mileage(mileage_no, mileage_total, mileage_state, user_no, mileage_change, payment_no) "
					+ "values('M'||seq_mileage.nextval, :#{#dto?.mileage_total}, :#{#dto?.mileage_state}, :#{#dto?.user_no}, :#{#dto?.mileage_change}, :#{#dto?.payment_no})")
	public int insert_mileage_changed(@Param("dto") OfficeMileageDto dto);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "update mileage set mileage_state='C' where mileage_no=?1")
	public int update_mileage_state(String mileage_no);
	


//	
//	@Modifying
//	@Transactional
//	@Query(nativeQuery = true, value = "delete from mileage where mileage_no=?1")
//	public void delete_mileage_cancel(String mileage_no);
//	
//	

	
}//end class
