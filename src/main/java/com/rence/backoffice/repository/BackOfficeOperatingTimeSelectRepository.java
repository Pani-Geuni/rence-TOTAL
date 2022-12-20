/**
 * 
 * @author 최진실
 *
 */
package com.rence.backoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rence.backoffice.model.BackOfficeEntity;
import com.rence.backoffice.model.BackOfficeOperatingTimeEntity;
import com.rence.backoffice.model.BackOfficeOperatingTimeDTO;

public interface BackOfficeOperatingTimeSelectRepository extends JpaRepository<BackOfficeOperatingTimeEntity, Object> {

	@Query(nativeQuery = true, value = "select opetime_no,"
			+ "mon_stime,mon_etime,tue_stime,tue_etime,"
			+ "wed_stime,wed_etime,"
			+ "thu_stime,thu_etime,fri_stime,fri_etime,"
			+ "sat_stime,sat_etime,sun_stime,sun_etime,"
			+ "backoffice_no, mon_dayoff, tue_dayoff, wed_dayoff, thu_dayoff, fri_dayoff, sat_dayoff, sun_dayoff"
			+ " from backofficeoperatingtime where backoffice_no=?1")
	public BackOfficeOperatingTimeEntity backoffice_setting_selectOne_operatingtime(String backoffice_no);

	
}
