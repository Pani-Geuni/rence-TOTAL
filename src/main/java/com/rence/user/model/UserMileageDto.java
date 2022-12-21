/**
 * @author 강경석
 * 
 */

package com.rence.user.model;

import java.sql.Date;

import org.springframework.data.annotation.Immutable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Immutable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMileageDto {
	
	
	private String user_no; // 
	
	private Integer mileage_total;
	
	private String no; // mileage_no
	
	private String state; //mileage_state
	
	private String mileage; //mileage_change
	
	private String room; //company_name;
	
	private String date; // payment_date
}//end class
