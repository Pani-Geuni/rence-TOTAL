package com.rence.office.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rence.office.model.OfficeInfo_ViewEntity;

public interface OfficeDetailInfoRepository extends JpaRepository<OfficeInfo_ViewEntity, Object> {
	@Query(nativeQuery = true, value =
			"select * from office_info_view "
			+ "where backoffice_no=?1")
	public OfficeInfo_ViewEntity select_one_office_info(String backoffice_no);
	
}//end class
