/**
 * 
 * @author 최진실
 *
 */package com.rence.dashboard.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rence.dashboard.model.CommentListAViewEntity;
import com.rence.dashboard.model.CommentListQViewDTO;

public interface CommentAListRepository extends JpaRepository<CommentListAViewEntity, Object>{

	// 공간 답변
	@Query(nativeQuery = true, value = "SELECT * FROM COMMENTLIST_A_VIEW WHERE BACKOFFICE_NO=?1 AND MOTHER_NO=?2")
	public List<CommentListAViewEntity> select_all_a(String backoffice_no, String mother_no);

}

//****[COMMENTLIST_A_VIEW] view code****
//select comment_no, comment_content, TO_CHAR(comment_date, 'YYYY-MM-DD HH24:MI:SS') as comment_date, backoffice_no, mother_no
//from comments
