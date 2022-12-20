/**
 * @author 강경석
 * reserve 테이블 - backoffice 테이블 조인 뷰(USER_RESERVE_VIEW)
 * 유저 마일리지 상세페이지 데이터 VO 
 */


package com.rence.user.model;

import org.springframework.data.annotation.Immutable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Immutable
@AllArgsConstructor
@NoArgsConstructor
public class MyPageReserveListDto {
	
	
	private String user_no; 

	private String reserve_no;
	
	private String reserve_sdate;
	
	private String reserve_edate;
	
	private String company_name;
	
	private String roadname_address;
	
	private String backoffice_image;
	
	private String payment_total;
	
}//end class
