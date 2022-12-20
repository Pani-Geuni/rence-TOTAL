/**
 * @author 전판근
 */

package com.rence.office.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInfoDto {
	
	
	private String reserve_no;
	
	
	private String reserve_stime;
	

	private String reserve_etime;
	
	private String room_no;
	
	
	private String user_no;
	

	private String backoffice_no;
	
	
	private String room_name;
	
	
	private String room_type;
	
	private int room_price;
	
	private String user_name;
	
	private String user_tel;
	
	private String user_email;
	
	private String backoffice_image;
	
	private String company_name;
	
	private String owner_name;
	
	private String roadname_address;
	
	private String detail_address;
	
	private String backoffice_tel;
	
	private String backoffice_email;
	
	private String mileage_total;

}
