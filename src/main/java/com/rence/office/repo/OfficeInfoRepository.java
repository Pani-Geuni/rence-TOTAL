/**
 * @author 김예은
*/
package com.rence.office.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rence.office.model.Comment_Dto;
import com.rence.office.model.Comment_EntityVO;
import com.rence.office.model.ListViewEntity;

public interface OfficeInfoRepository extends JpaRepository<ListViewEntity, Object> {

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = 
			"insert into comments(comment_no, mother_no, comment_content, comment_date, room_no, backoffice_no, user_no, host_no, is_secret) "
			+ "		values('C'||SEQ_COMMENTS.nextval, null, :#{#vo2?.comment_content}, current_date, :#{#vo2?.room_no}, :#{#vo2?.backoffice_no}, :#{#vo2?.user_no}, null, :#{#vo2?.is_secret})")
	public int insert_question(@Param("vo2") Comment_Dto vo2);

}