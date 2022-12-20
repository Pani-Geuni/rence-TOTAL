/**
 * 
 * @author 최진실
 *
 */
package com.rence.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rence.dashboard.model.CommentListQViewDTO;
import com.rence.dashboard.model.CommentListQViewEntity;

public interface CommentQListRepository extends JpaRepository<CommentListQViewEntity, Object>{

	// 공간 - 문의 리스트
	@Query(nativeQuery = true, value = "select * from (select rownum as rnum,C.* from(select comment_no, user_no, comment_content, comment_date, room_name, comment_state, room_no, backoffice_no FROM COMMENTLIST_Q_VIEW WHERE BACKOFFICE_NO=?1 order by comment_date desc )C ) where rnum between ?2 and ?3")
	public List<CommentListQViewEntity> select_all_q(String backoffice_no,Integer start_row, Integer end_row);
	
	// 문의 리스트 갯수
	@Query(nativeQuery = true, value = "select count(*) from (select rownum as rnum, comment_no, user_no, comment_content, comment_date, room_name, comment_state, room_no, backoffice_no FROM COMMENTLIST_Q_VIEW WHERE BACKOFFICE_NO=?1)")
	public long select_all_q_cnt(String backoffice_no);


}

//****[COMMENTLIST_Q_VIEW] view code****
//select comment_no, user_no, comment_content, TO_CHAR(comment_date, 'YYYY-MM-DD HH24:MI:SS') as comment_date, room_name, comment_state, c.room_no, C.BACKOFFICE_NO
//from comments c, roominfo rm 
//where c.room_no=rm.room_no and mother_no is null;

