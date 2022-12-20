/**
 * @author 전판근
 */

package com.rence.dashboard.model;

import java.io.Serializable;
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
public class ReserveUpdateDTO implements Serializable {
	
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
	
}
