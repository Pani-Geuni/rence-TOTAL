/**
 * @author 전판근
 */

package com.rence.office.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

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
public class OfficeOperatingTimeDto_date {
	
	private String backoffice_no;
	
	private Date mon_stime;
	
	private Date mon_etime;
	
	private Date tue_stime;
	
	private Date tue_etime;
	
	private Date wed_stime;
	
	private Date wed_etime;
	
	private Date thu_stime;
	
	private Date thu_etime;
	
	private Date fri_stime;
	
	private Date fri_etime;
	
	private Date sat_stime;
	
	private Date sat_etime;
	
	private Date sun_stime;
	
	private Date sun_etime;
	
	private String mon_dayoff;
	
	private String tue_dayoff;
	
	private String wed_dayoff;
	
	private String thu_dayoff;
	
	private String fri_dayoff;
	
	private String sat_dayoff;
	
	private String sun_dayoff;
	
}
