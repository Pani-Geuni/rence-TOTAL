
package com.rence.user.model;

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
public class ReserveInfo_ViewDto {
	
	String reserve_no;
	
	String backoffice_no;
	
	String company_name;
	
	String room_no;
	
	String room_type;
	
	String reserve_sdate;
	
	String reserve_edate;
	
	String room_name;
	
	String room_price;
	
	String backoffice_name;
	
	String backoffice_image;
	
	String full_address;
	
	String backoffice_tel;
	
	String backoffice_email;
	
	String payment_total;
	
	String actual_payment;
	
	String mileage_change;
	
}//end class