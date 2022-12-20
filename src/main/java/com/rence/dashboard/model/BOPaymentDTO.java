/**
 * 
 * @author 최진실
 *
 */
package com.rence.dashboard.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BOPaymentDTO {

	private String payment_no;
	
	private int payment_total;
	
	private int use_mileage;
	
	private String actual_payment;
	
	private String payment_state;
	
	private Date payment_date;
	
	private String room_no;
	
	private String user_no;
	
	private String reserve_no;
	
	private String sales_state;
	
	private String backoffice_no;
	
	private String cancel_state;
	
	private Integer cancel_amount;
	
	private String imp_uid;
}
