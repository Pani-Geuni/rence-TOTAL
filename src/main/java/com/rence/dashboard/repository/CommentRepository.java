/**
 * 
 * @author 최진실
 *
 */
package com.rence.dashboard.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.rence.dashboard.model.CommentEntity;
import com.rence.dashboard.model.CommentDTO;

public interface CommentRepository extends JpaRepository<CommentEntity, Object> {

	// 공간 문의 - 답변 팝업
	@Query(nativeQuery = true, value = "select comment_no,mother_no,comment_state,room_name,comment_content, TO_CHAR(comment_date, 'YYYY-MM-DD HH24:MI:SS') as comment_date, c.room_no, c.backoffice_no, user_no, host_no, writer  from comments c left outer join roominfo rm on c.room_no=rm.room_no where c.backoffice_no=?1 and c.room_no=?2 and comment_no=?3")
	public CommentEntity backoffice_insert_comment(String backoffice_no, String room_no, String comment_no);

	// 공간 문의 - 답변 작성
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "insert into comments(comment_no, room_no, backoffice_no, mother_no, comment_content, comment_date, writer, comment_state, host_no) values ('C'||SEQ_COMMENTS.nextval, :#{#cvo?.room_no}, :#{#cvo?.backoffice_no}, :#{#cvo?.mother_no}, :#{#cvo?.comment_content}, :#{#cvo?.comment_date}, :#{#cvo?.writer}, :#{#cvo?.comment_state}, :#{#cvo?.host_no})")
	public int backoffice_insertOK_comment(@Param("cvo") CommentEntity cvo);

	// 공간 문의 - 문의 상태 'T'변경
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "update comments set comment_state='T' where backoffice_no=?1 and comment_no=?2")
	public int update_comment_state_T(String backoffice_no, String comment_no);

	// 공간 문의 - 답변 삭제
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "DELETE FROM comments where backoffice_no=?1 and comment_no=?2")
	public int backoffice_deleteOK_comment(String backoffice_no, String comment_no);

	// 공간 문의 - 문의 상태 'F'변경
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "update comments set comment_state='F' where backoffice_no=?1 and comment_no=?2")
	public int update_comment_state_F(String backoffice_no, String mother_no);

	// 공간 문의 - 공간 삭제 후, 문의 답변 처리를 위한 문의 리스트
	@Query(nativeQuery = true, value = "select comment_no from comments where comment_state='F' and room_no = ?1")
	public List<String> select_qna_f(String room_no);

	// 공간 문의 - 공간 삭제 후, 문의 답변 처리
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "insert into comments(comment_no, room_no, backoffice_no, mother_no, comment_content, comment_date, writer, comment_state, host_no) "
			+ " values ('C'||SEQ_COMMENTS.nextval, ?2, ?1, ?3, '삭제된 공간입니다.', current_date, '관리자', 'T', ?1)")
	public int backoffice_qna_insert(String backoffice_no, String room_no, String q);

	// 환경 설정 - 호스트 탈퇴 신청 후, 문의 답변 처리를 위한 문의 리스트
	@Query(nativeQuery = true, value = "select comment_no,mother_no,comment_state,room_name,comment_content, TO_CHAR(comment_date, 'YYYY-MM-DD HH24:MI:SS') as comment_date, c.room_no, c.backoffice_no, user_no, host_no, writer   from comments where comment_state='F' and backoffice_no = ?1")
	public List<CommentEntity> select_qna_f_all(String backoffice_no);


}
