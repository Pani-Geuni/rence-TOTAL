package com.rence.office.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.rence.office.model.OfficeReserveDto_date;
import com.rence.office.model.OfficeReserveEntity_date;

public interface OfficeReserveDateRepository extends JpaRepository<OfficeReserveEntity_date, Object> {

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "insert into "
			+ "reserveinfo(reserve_no, reserve_stime, reserve_etime, reserve_sdate, reserve_edate, reserve_state, room_no, user_no, backoffice_no, room_type) "
			+ "values('RV'||seq_reserve.nextval, :#{#rdto?.reserve_stime}, :#{#rdto?.reserve_etime}, :#{#rdto?.reserve_sdate}, :#{#rdto?.reserve_edate}, 'false', :#{#rdto?.room_no}, :#{#rdto?.user_no}, :#{#rdto?.backoffice_no}, :#{#rdtos?.room_type})")
	public int insert_reserve(@Param("rdto") OfficeReserveDto_date rdto);

	

}//end class
