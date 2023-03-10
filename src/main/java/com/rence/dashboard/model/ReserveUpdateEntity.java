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
@Entity
@Table(name="RESERVEINFO")
public class ReserveUpdateEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SEQ_RESERVE")
	@SequenceGenerator(sequenceName = "SEQ_RESERVE",allocationSize = 1,name = "SEQ_RESERVE")
	@Column(name="reserve_no")
	private String reserve_no;
	
	@Column(name="reserve_stime")
	private Date reserve_stime;
	
	@Column(name="reserve_etime")
	private Date reserve_etime;
	
	@Column(name="reserve_sdate")
	private Date reserve_sdate;
	
	@Column(name="reserve_edate")
	private Date reserve_edate;
	
	@Column(name="reserve_state")
	private String reserve_state;
	
	@Column(name="room_no")
	private String room_no;
	
	@Column(name="user_no")
	private String user_no;
	
	@Column(name="backoffice_no")
	private String backoffice_no;
	
	@Column(name="room_type")
	private String room_type;
	
	
}
