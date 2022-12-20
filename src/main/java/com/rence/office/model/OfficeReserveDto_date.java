package com.rence.office.model;

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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfficeReserveDto_date {
	
	private String reserve_no;
	
	private Date reserve_stime;
	
	private Date reserve_etime;
	
	private Date reserve_sdate;
	
	private Date reserve_edate;
	
	private String reserve_state;
	
	private String room_no;
	
	private String user_no;
	
	private String backoffice_no;
	
	private String room_type;
	
}//end class
