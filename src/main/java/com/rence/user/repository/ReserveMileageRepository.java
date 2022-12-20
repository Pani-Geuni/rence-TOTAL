package com.rence.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rence.user.model.ReserveMileageEntity;

public interface ReserveMileageRepository extends JpaRepository<ReserveMileageEntity, Object> {

	@Query(nativeQuery = true, value = "select * from reserve_info_payment where reserve_no=?1")
	public ReserveMileageEntity select_one_reserve_mileage(String reserve_no);
	
}//end class
