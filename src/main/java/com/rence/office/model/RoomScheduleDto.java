package com.rence.office.model;

import javax.persistence.Column;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomScheduleDto {

	private String schedule_no;
	
	private String not_sdate;
	
	private String not_edate;
	
	private String not_stime;
	
	private String not_etime;
	
	private String room_no;
	
	private String backoffice_no;
}//end class
