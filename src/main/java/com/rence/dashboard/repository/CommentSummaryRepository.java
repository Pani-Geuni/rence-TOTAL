/**
 * 
 * @author 최진실
 *
 */
package com.rence.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rence.dashboard.model.CommentSummaryViewDTO;
import com.rence.dashboard.model.CommentSummaryViewEntity;

public interface CommentSummaryRepository extends JpaRepository<CommentSummaryViewEntity, Object>{ // 문의 요약

	@Query(nativeQuery = true, value = "select * from COMMENTS_SUMMARY_VIEW where backoffice_no=?1")
	public List<CommentSummaryViewEntity> comment_summary_selectAll(String backoffice_no);

}

//****[CommentSummaryViewEntity] view code****
//select c.comment_no, rm.room_name,TO_CHAR(c.comment_date, 'YYYY-MM-DD HH24:MI:SS') as comment_date, c.comment_content, c.backoffice_no
//from comments c, roominfo rm 
//where c.room_no=rm.room_no and c.host_no is null and comment_no not in (select mother_no from comments where mother_no is not null) 
//order by comment_date desc;