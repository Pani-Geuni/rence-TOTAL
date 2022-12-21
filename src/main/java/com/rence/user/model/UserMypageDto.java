/**
 * @author 강경석
 * userinfo 테이블 - mileage 테이블 조인 뷰(user_mypage_view)
 * 유저 마이페이지필요 데이터 VO
 *  View - Spring JPA 사용 참조링크 : https://joomn11.tistory.com/107
 * 
 */

package com.rence.user.model;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

import org.springframework.data.annotation.Immutable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Immutable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMypageDto {
	private String user_no; //사용자 고유번호
	
	private String user_image; //프로필 이미지

	private String user_id; //아이디
	
	private String user_name; //실명
	
	private String user_email; //이메일
	
	private String user_tel; //전화번호
	
	private String user_birth; //생년월일
	
	private String mileage_total; // 총 마일리지


	

	
	

}//end class
