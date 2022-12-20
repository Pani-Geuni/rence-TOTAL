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
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationViewDTO implements Serializable{
	
	private String user_no;
	
	private String user_name;
	
	private String user_email;
	
	private String user_tel;
	
	private String reserve_stime;
	
	private String reserve_etime;
	
	private String backoffice_no;
	
	private String room_no;
	
	private String reserve_no;

}
