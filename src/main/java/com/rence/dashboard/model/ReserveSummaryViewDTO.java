/**
 * 
 * @author 최진실
 *
 */
package com.rence.dashboard.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveSummaryViewDTO implements Serializable{
	
	private String reserve_no;
	
	private String reserve_sdate;
	
	private String reserve_edate;
	
	private String room_name;
	
	private String user_name;
	
	private String actual_payment; //int
	
	private String reserve_state;
	

}
