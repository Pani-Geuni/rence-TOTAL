/**
 * @author 전판근
 */

package com.rence.office.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class OfficeReserveDto {
	
	private String reserve_no;
	
	private String reserve_stime;
	
	private String reserve_etime;
	
	private String reserve_sdate;
	
	private String reserve_edate;
	
	private String reserve_state;
	
	private String room_no;
	
	private String user_no;
	
	private String backoffice_no;
	
	private String room_type;

}
