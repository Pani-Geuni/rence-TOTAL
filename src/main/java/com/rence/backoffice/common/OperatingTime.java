
/**
 * 
 * @author 최진실
 * 운영시간 타입 변환
 *
 */
package com.rence.backoffice.common;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.rence.backoffice.model.BackOfficeOperatingTimeEntity;
import com.rence.backoffice.model.BackOfficeOperatingTimeDTO;

@Component
public class OperatingTime {
	
	public BackOfficeOperatingTimeEntity operatingTime(BackOfficeOperatingTimeDTO ovo) {
		
		BackOfficeOperatingTimeEntity ovo2 = new BackOfficeOperatingTimeEntity();
		
		CustomDateFormatter cdf = new CustomDateFormatter();
		
		ovo2.setBackoffice_no(ovo.getBackoffice_no());
		
		if (ovo.getMon_stime()!=null) {
			Date mon_stime = cdf.hourFormmatter(ovo.getMon_stime());
			ovo2.setMon_stime(mon_stime);
		}
		if(ovo.getMon_etime()!=null) {
			Date mon_etime = cdf.hourFormmatter(ovo.getMon_etime());
			ovo2.setMon_etime(mon_etime);
		}
		if (ovo.getTue_stime()!=null) {
			Date tue_stime = cdf.hourFormmatter(ovo.getTue_stime());
			ovo2.setTue_stime(tue_stime);
		}
		if (ovo.getTue_etime()!=null) {
			Date tue_etime = cdf.hourFormmatter(ovo.getTue_etime());
			ovo2.setTue_etime(tue_etime);
		}
		if (ovo.getWed_stime()!=null) {
			Date wed_stime = cdf.hourFormmatter(ovo.getWed_stime());
			ovo2.setWed_stime(wed_stime);
		}
		if (ovo.getWed_etime()!=null) {
			Date wed_etime = cdf.hourFormmatter(ovo.getWed_etime());
			ovo2.setWed_etime(wed_etime);
		}
		if (ovo.getThu_stime()!=null) {
			Date thu_stime = cdf.hourFormmatter(ovo.getThu_stime());
			ovo2.setThu_stime(thu_stime);
		}
		if (ovo.getThu_etime()!=null) {
			Date thu_etime = cdf.hourFormmatter(ovo.getThu_etime());
			ovo2.setThu_etime(thu_etime);
		}
		if (ovo.getFri_stime()!=null) {
			Date fri_stime = cdf.hourFormmatter(ovo.getFri_stime());
			ovo2.setFri_stime(fri_stime);
		}
		if (ovo.getFri_etime()!=null) {
			Date fri_etime = cdf.hourFormmatter(ovo.getFri_etime());
			ovo2.setFri_etime(fri_etime);
		}
		if (ovo.getSat_stime()!=null) {
			Date sat_stime = cdf.hourFormmatter(ovo.getSat_stime());
			ovo2.setSat_stime(sat_stime);
		}
		if (ovo.getSat_etime()!=null) {
			Date sat_etime = cdf.hourFormmatter(ovo.getSat_etime());
			ovo2.setSat_etime(sat_etime);
		}
		if (ovo.getSun_stime()!=null) {
			Date sun_stime = cdf.hourFormmatter(ovo.getSun_stime());
			ovo2.setSun_stime(sun_stime);
		}
		if(ovo.getSun_etime()!=null) {
			Date sun_etime = cdf.hourFormmatter(ovo.getSun_etime());
			ovo2.setSun_etime(sun_etime);
		}
		
		ovo2.setMon_dayoff(ovo.getMon_dayoff());
		if (ovo2.getMon_dayoff()==null) {
			ovo2.setMon_dayoff("F");
		}
		ovo2.setTue_dayoff(ovo.getTue_dayoff());
		if (ovo2.getTue_dayoff()==null) {
			ovo2.setTue_dayoff("F");
		}
		ovo2.setWed_dayoff(ovo.getWed_dayoff());
		if (ovo2.getWed_dayoff()==null) {
			ovo2.setWed_dayoff("F");
		}
		ovo2.setThu_dayoff(ovo.getThu_dayoff());
		if (ovo2.getThu_dayoff()==null) {
			ovo2.setThu_dayoff("F");
		}
		ovo2.setFri_dayoff(ovo.getFri_dayoff());
		if (ovo2.getFri_dayoff()==null) {
			ovo2.setFri_dayoff("F");
		}
		ovo2.setSat_dayoff(ovo.getSat_dayoff());
		if (ovo2.getSat_dayoff()==null) {
			ovo2.setSat_dayoff("F");
		}
		ovo2.setSun_dayoff(ovo.getSun_dayoff());
		if (ovo2.getSun_dayoff()==null) {
			ovo2.setSun_dayoff("F");
		}
		 
		return ovo2;
		
	}
	
	public BackOfficeOperatingTimeDTO operatingTimeEtoD(BackOfficeOperatingTimeEntity ovo) {
		
		BackOfficeOperatingTimeDTO ovo2 = new BackOfficeOperatingTimeDTO();
		
		CustomDateFormatter cdf = new CustomDateFormatter();
		
		ovo2.setBackoffice_no(ovo.getBackoffice_no());
		
		if (ovo.getMon_stime()!=null) {
			String mon_stime = cdf.makeStringHourFormatter(ovo.getMon_stime());
			ovo2.setMon_stime(mon_stime);
		}
		if(ovo.getMon_etime()!=null) {
			String mon_etime = cdf.makeStringHourFormatter(ovo.getMon_etime());
			ovo2.setMon_etime(mon_etime);
		}
		if (ovo.getTue_stime()!=null) {
			String tue_stime = cdf.makeStringHourFormatter(ovo.getTue_stime());
			ovo2.setTue_stime(tue_stime);
		}
		if (ovo.getTue_etime()!=null) {
			String tue_etime = cdf.makeStringHourFormatter(ovo.getTue_etime());
			ovo2.setTue_etime(tue_etime);
		}
		if (ovo.getWed_stime()!=null) {
			String wed_stime = cdf.makeStringHourFormatter(ovo.getWed_stime());
			ovo2.setWed_stime(wed_stime);
		}
		if (ovo.getWed_etime()!=null) {
			String wed_etime = cdf.makeStringHourFormatter(ovo.getWed_etime());
			ovo2.setWed_etime(wed_etime);
		}
		if (ovo.getThu_stime()!=null) {
			String thu_stime = cdf.makeStringHourFormatter(ovo.getThu_stime());
			ovo2.setThu_stime(thu_stime);
		}
		if (ovo.getThu_etime()!=null) {
			String thu_etime = cdf.makeStringHourFormatter(ovo.getThu_etime());
			ovo2.setThu_etime(thu_etime);
		}
		if (ovo.getFri_stime()!=null) {
			String fri_stime = cdf.makeStringHourFormatter(ovo.getFri_stime());
			ovo2.setFri_stime(fri_stime);
		}
		if (ovo.getFri_etime()!=null) {
			String fri_etime = cdf.makeStringHourFormatter(ovo.getFri_etime());
			ovo2.setFri_etime(fri_etime);
		}
		if (ovo.getSat_stime()!=null) {
			String sat_stime = cdf.makeStringHourFormatter(ovo.getSat_stime());
			ovo2.setSat_stime(sat_stime);
		}
		if (ovo.getSat_etime()!=null) {
			String sat_etime = cdf.makeStringHourFormatter(ovo.getSat_etime());
			ovo2.setSat_etime(sat_etime);
		}
		if (ovo.getSun_stime()!=null) {
			String sun_stime = cdf.makeStringHourFormatter(ovo.getSun_stime());
			ovo2.setSun_stime(sun_stime);
		}
		if(ovo.getSun_etime()!=null) {
			String sun_etime = cdf.makeStringHourFormatter(ovo.getSun_etime());
			ovo2.setSun_etime(sun_etime);
		}
		
		ovo2.setMon_dayoff(ovo.getMon_dayoff());
		ovo2.setTue_dayoff(ovo.getTue_dayoff());
		ovo2.setWed_dayoff(ovo.getWed_dayoff());
		ovo2.setThu_dayoff(ovo.getThu_dayoff());
		ovo2.setFri_dayoff(ovo.getFri_dayoff());
		ovo2.setSat_dayoff(ovo.getSat_dayoff());
		ovo2.setSun_dayoff(ovo.getSun_dayoff());
		
		return ovo2;
		
	}
}

