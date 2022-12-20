/**
 * 
 * @author 최진실
 *
 */
package com.rence.dashboard.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTO implements Serializable{

	private String schedule_no;
	
	private Date not_stime;
	
	private Date not_etime;
	
	private String room_no;
	
	private String backoffice_no;
	
	private String room_name;

	private String sdate;

	private String edate;

	private String stime;

	private String etime;

	private String schedule_type;
	
}
