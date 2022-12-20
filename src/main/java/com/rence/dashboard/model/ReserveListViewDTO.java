/**
 * 
 * @author 최진실
 *
 */
package com.rence.dashboard.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveListViewDTO {
	
	private String reserve_no;
	
	private String reserve_sdate;
	
	private String reserve_edate;
	
	private String reserve_state;
	
	private String user_no;
	
	private String backoffice_no;
	
	private String room_name;
	
	private String user_name;
	
	private String user_email;
	
	private String user_tel;
	
	private String actual_payment;
	
	private String payment_state;

}
