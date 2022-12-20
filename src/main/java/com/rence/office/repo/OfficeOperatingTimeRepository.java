package com.rence.office.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rence.office.model.OfficeOperatingTimeEntity;

public interface OfficeOperatingTimeRepository extends JpaRepository<OfficeOperatingTimeEntity, Object> {

	
	@Query(nativeQuery = true, value = ""
			+ "select * "
			+ "from backofficeoperatingtime "
			+ "where backoffice_no=?1")
	public OfficeOperatingTimeEntity select_one_operating_time(String backoffice_no);
	
	
}//end class
