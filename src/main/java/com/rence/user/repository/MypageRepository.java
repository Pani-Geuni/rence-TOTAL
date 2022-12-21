package com.rence.user.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.rence.user.model.UserMypageEntity;

public interface MypageRepository extends JpaRepository<UserMypageEntity, Object> {

	// 마이페이지 정보 불러오기
//	@Query(nativeQuery = true, value = "select * from (select * from USER_MYPAGE_VIEW where user_no = ?1 order by mileage_no desc)WHERE ROWNUM between 1 and 1")
	@Query(nativeQuery = true, value = "select user_no,user_name,user_image,user_id,user_email,user_tel,TO_CHAR(user_birth, 'YYYY/MM/DD') as user_birth,mileage_total from (select user_no,user_name,user_image,user_id,user_email,user_tel, user_birth,mileage_total from USER_MYPAGE_VIEW where user_no = ?1 order by mileage_no desc) WHERE ROWNUM between 1 and 1")
	UserMypageEntity user_mypage_select(String user_no);

	// 프로필수정
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "update userinfo set user_image=?1 where user_no = ?2")
	int user_img_updateOK(String user_image, String user_no);

	// 회원탈퇴에 따른 회원상태 수정
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "update userinfo set user_state='N' where user_no = ?1")
	int user_secedeOK(String user_no);

}// end class
