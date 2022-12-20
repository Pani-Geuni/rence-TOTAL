/**
 * @author 전판근
 */
package com.rence.backoffice.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rence.office.model.OfficeOperatingTimeDto;
import com.rence.office.model.OfficeOperatingTimeDto_date;

//import com.rence.office.model.OfficeOperatingTimeVO;
//import com.rence.office.model.OfficeOperatingTimeVO_date;

public class CustomDateFormatter {
	private static final Logger logger = LoggerFactory.getLogger(CustomDateFormatter.class);

	public CustomDateFormatter() {
		// TODO Auto-generated constructor stub
	}

	// st = String Time
	// dt = Date Time

	// String으로 받은 시간 값 (HH:mm 형식)을
	// Date로 형변환 시켜주는 함수.
	// 단, 이 함수는 HH:mm 형식만 됨.
	public Date hourFormmatter(String st) {

		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		Date dt = null; // simple date

		try {
			// String st를 Date dt로 변환.
			dt = df.parse(st);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dt;
	}

	public String makeStringHourFormatter(Date time) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

		String operating_time = formatter.format(time);

		return operating_time;
	}

	public OfficeOperatingTimeDto showStringOfficeOperating(OfficeOperatingTimeDto_date dotdto) {
		OfficeOperatingTimeDto otdto = new OfficeOperatingTimeDto();

		otdto.setMon_dayoff(dotdto.getMon_dayoff());
		otdto.setTue_dayoff(dotdto.getTue_dayoff());
		otdto.setWed_dayoff(dotdto.getWed_dayoff());
		otdto.setThu_dayoff(dotdto.getThu_dayoff());
		otdto.setFri_dayoff(dotdto.getFri_dayoff());
		otdto.setSat_dayoff(dotdto.getSat_dayoff());
		otdto.setSun_dayoff(dotdto.getSun_dayoff());

		if (dotdto.getSun_dayoff().equals("F")) {
			otdto.setSun_stime(makeStringHourFormatter(dotdto.getSun_stime()));
			otdto.setSun_etime(makeStringHourFormatter(dotdto.getSun_etime()));
		}
		if (dotdto.getMon_dayoff().equals("F")) {
			otdto.setMon_stime(makeStringHourFormatter(dotdto.getMon_stime()));
			otdto.setMon_etime(makeStringHourFormatter(dotdto.getMon_etime()));
		}
		if (dotdto.getTue_dayoff().equals("F")) {
			otdto.setTue_stime(makeStringHourFormatter(dotdto.getTue_stime()));
			otdto.setTue_etime(makeStringHourFormatter(dotdto.getTue_etime()));
		}
		if (dotdto.getWed_dayoff().equals("F")) {
			otdto.setWed_stime(makeStringHourFormatter(dotdto.getWed_stime()));
			otdto.setWed_etime(makeStringHourFormatter(dotdto.getWed_etime()));
		}
		if (dotdto.getThu_dayoff().equals("F")) {
			otdto.setThu_stime(makeStringHourFormatter(dotdto.getThu_stime()));
			otdto.setThu_etime(makeStringHourFormatter(dotdto.getThu_etime()));
		}
		if (dotdto.getFri_dayoff().equals("F")) {
			otdto.setFri_stime(makeStringHourFormatter(dotdto.getFri_stime()));
			otdto.setFri_etime(makeStringHourFormatter(dotdto.getFri_etime()));
		}
		if (dotdto.getSat_dayoff().equals("F")) {
			otdto.setSat_stime(makeStringHourFormatter(dotdto.getSat_stime()));
			otdto.setSat_etime(makeStringHourFormatter(dotdto.getSat_etime()));
		}

		return otdto;
	}

}
