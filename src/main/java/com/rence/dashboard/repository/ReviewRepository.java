/**
 * 
 * @author 최진실
 *
 */
package com.rence.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rence.dashboard.model.ReviewListViewEntity;

public interface ReviewRepository extends JpaRepository<ReviewListViewEntity, Object>{

	@Query(nativeQuery = true, value = "select * from REVIEW_LIST_B_VIEW where backoffice_no=?1 and ROWNUM  between ?2 and ?3")
	public List<ReviewListViewEntity> backoffice_review_selectAll(String backoffice_no, Integer start_row, Integer end_row);

	// 후기 리스트 갯수
	@Query(nativeQuery = true, value = " select count(*) from REVIEW_LIST_B_VIEW where backoffice_no=?1")
	public long backoffice_review_selectAll_cnt(String backoffice_no);

}

//****[REVIEW_LIST_B_VIEW] view code****
//*
//select review_no, review_content, review_point, TO_CHAR(review_date, 'YYYY-MM-DD HH24:MI:SS') as review_date, rv.user_no, user_image, substr(user_name,1,1)||lpad('*',length(user_name)-1,'*') as user_name, backoffice_no 
//from review rv, userinfo u where rv.user_no=u.user_no order by review_date desc
//*
//***************************************
