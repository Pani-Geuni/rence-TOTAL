/**
 * 
 * @author 최진실
 *
 */
package com.rence.backoffice.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.rence.backoffice.model.BackOfficeOperatingTimeEntity;

public interface BackOfficeOperatingTimeRepository extends JpaRepository<BackOfficeOperatingTimeEntity, Object> {

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value="INSERT INTO backofficeoperatingtime( opetime_no, mon_stime, mon_etime, tue_stime, tue_etime, wed_stime, wed_etime, thu_stime, thu_etime, fri_stime, fri_etime, sat_stime, sat_etime, sun_stime, sun_etime, backoffice_no, mon_dayoff, tue_dayoff, wed_dayoff, thu_dayoff , fri_dayoff, sat_dayoff, sun_dayoff ) "
			+ "values( 'O'||SEQ_BACKOFFICEOPERATINGTIME.nextval, :#{#vo?.mon_stime}, :#{#vo?.mon_etime}, :#{#vo?.tue_stime}, :#{#vo?.tue_etime}, :#{#vo?.wed_stime}, :#{#vo?.wed_etime}, :#{#vo?.thu_stime}, :#{#vo?.thu_etime},"
			+ "	:#{#vo?.fri_stime},:#{#vo?.fri_etime}, :#{#vo?.sat_stime}, :#{#vo?.sat_etime}, :#{#vo?.sun_stime}, :#{#vo?.sun_etime}, "
			+ "	:#{#vo?.backoffice_no}, :#{#vo?.mon_dayoff}, :#{#vo?.tue_dayoff}, :#{#vo?.wed_dayoff}, :#{#vo?.thu_dayoff}, :#{#vo?.fri_dayoff}, :#{#vo?.sat_dayoff}, :#{#vo?.sun_dayoff} )")
	public int insert_operating_time(@Param("vo") BackOfficeOperatingTimeEntity ovo2);
	
	
	// 업체 정보 수정 - 운영시간
		@Modifying
		@Transactional
		@Query(nativeQuery = true, value="update backofficeoperatingtime set mon_stime=:#{#vo?.mon_stime}, mon_etime=:#{#vo?.mon_etime}, tue_stime=:#{#vo?.tue_stime}, tue_etime=:#{#vo?.tue_etime}, wed_stime=:#{#vo?.wed_stime}, wed_etime=:#{#vo?.wed_etime}, thu_stime=:#{#vo?.thu_stime}, thu_etime=:#{#vo?.thu_etime},"
				+ "	fri_stime=:#{#vo?.fri_stime},fri_etime=:#{#vo?.fri_etime}, sat_stime=:#{#vo?.sat_stime}, sat_etime=:#{#vo?.sat_etime}, sun_stime=:#{#vo?.sun_stime}, sun_etime=:#{#vo?.sun_etime}, "
				+ "	mon_dayoff=:#{#vo?.mon_dayoff}, tue_dayoff=:#{#vo?.tue_dayoff}, wed_dayoff=:#{#vo?.wed_dayoff}, thu_dayoff=:#{#vo?.thu_dayoff}, fri_dayoff=:#{#vo?.fri_dayoff}, sat_dayoff=:#{#vo?.sat_dayoff}, sun_dayoff=:#{#vo?.sun_dayoff}  where backoffice_no=:#{#vo?.backoffice_no}")
		public int backoffice_updateOK_opt(@Param("vo") BackOfficeOperatingTimeEntity ovo2);

	
}
