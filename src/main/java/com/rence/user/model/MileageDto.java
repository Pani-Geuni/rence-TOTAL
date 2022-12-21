package com.rence.user.model;

import org.springframework.data.annotation.Immutable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Immutable
@AllArgsConstructor
@NoArgsConstructor
public class MileageDto {
	
	private String mileage_no;
	
	private Integer  mileage_total;
	
	private String mileage_state;
	
	private String user_no;
	
	private Integer mileage_change;
	
	private String payment_no;
	
}//end class

