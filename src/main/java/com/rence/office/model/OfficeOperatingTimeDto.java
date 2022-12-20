/**
 * @author 전판근
 */

package com.rence.office.model;

import java.io.Serializable;

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
public class OfficeOperatingTimeDto implements Serializable {

	private String backoffice_no;
	
	private String mon_stime;
	private String mon_etime;
	private String tue_stime;
	private String tue_etime;
	private String wed_stime;
	private String wed_etime;
	private String thu_stime;
	private String thu_etime;
	private String fri_stime;
	private String fri_etime;
	private String sat_stime;
	private String sat_etime;
	private String sun_stime;
	private String sun_etime;

	private String mon_dayoff;
	private String tue_dayoff;
	private String wed_dayoff;
	private String thu_dayoff;
	private String fri_dayoff;
	private String sat_dayoff;
	private String sun_dayoff;
	


	
}//end class
