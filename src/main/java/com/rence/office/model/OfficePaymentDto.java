
package com.rence.office.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.springframework.data.annotation.Immutable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Immutable
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class OfficePaymentDto {

	
	private String payment_no;
	
	private int payment_total;
	
	private int use_mileage;
	
	private int actual_payment;
	
	private String payment_state;
	
	private Date payment_date;
	
	private String room_no;
	
	private String user_no;
	
	private String reserve_no;
	
	private String sales_state;
	
	private String backoffice_no;
	
	private String payment_method;
	
	private String cancel_state;
	
	private String cancel_amount;
	
	private String imp_uid;
}//end class
