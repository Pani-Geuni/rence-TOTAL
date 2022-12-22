package com.rence.office.service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.rence.backoffice.common.CustomDateFormatter;
import com.rence.backoffice.model.BackOfficeDTO;
import com.rence.office.common.OfficeInfoMap;
import com.rence.office.dao.officeDAO;
import com.rence.office.model.Comment_Dto;
import com.rence.office.model.ListViewDto;
import com.rence.office.model.OfficeInfo_ViewDto;
import com.rence.office.model.OfficeMileageDto;
import com.rence.office.model.OfficeOperatingTimeDto;
import com.rence.office.model.OfficeOperatingTimeDto_date;
import com.rence.office.model.OfficePaymentDto;
import com.rence.office.model.OfficeQuestionDto;
import com.rence.office.model.OfficeReserveDto;
import com.rence.office.model.OfficeReserveDto_date;
import com.rence.office.model.OfficeReview_ViewDto;
import com.rence.office.model.OfficeRoomDto;
import com.rence.office.model.PaymentInfoDto;
import com.rence.office.model.RoomScheduleDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OfficeServiceImpl implements OfficeService {

	@Autowired
	HttpSession session;

	@Autowired
	officeDAO dao;

	// 오피스(공간) 상세 페이지
	@Override
	public Map<String, Object> space_intruducePage(BackOfficeDTO bdto, String introduce_menu, Integer page) {

		Map<String, Object> map = new HashMap<String, Object>();

		OptionEngToKorMap_office info_map = new OptionEngToKorMap_office();
		String backoffice_no = bdto.getBackoffice_no();

		// ******************
		// backoffice 기본 정보
		// ******************
		OfficeInfo_ViewDto odto = dao.select_one_office_info(backoffice_no);

		List<String> type_list = new ArrayList<String>();
		List<String> tag_list = new ArrayList<String>();
		String[] img_list = null;
		List<String> option_list = new ArrayList<String>();
		List<String> around_option_list = new ArrayList<String>();

		odto.setAvg_rating(Math.round(odto.getAvg_rating() * 10) / 10.0);

		if (odto.getBackoffice_type() != null) {
			type_list = info_map.splitType(odto.getBackoffice_type());
		} else {
			type_list.add("타입 없음");
		}

		if (odto.getBackoffice_tag() != null) {
			tag_list = info_map.splitTag(odto.getBackoffice_tag());
		} else {
			tag_list.add("태그 없음");
		}

		img_list = info_map.splitImage(odto.getBackoffice_image());

		if (odto.getBackoffice_option() != null) {
			option_list = info_map.splitOption(odto.getBackoffice_option());
		} else {
			option_list.add("옵션 없음");
		}

		if (odto.getBackoffice_around() != null) {
			around_option_list = info_map.splitAroundOption(odto.getBackoffice_around());
		} else {
			around_option_list.add("주변 시설 없음");
		}

		String short_roadname_address = info_map.makeShortAddress(odto.getRoadname_address());

		// ******************
		// backoffice 운영 시간
		// ******************
		OfficeOperatingTimeDto_date dotvo = dao.select_one_operating_time(backoffice_no);

		CustomDateFormatter df = new CustomDateFormatter();
		OfficeOperatingTimeDto otvo = df.showStringOfficeOperating(dotvo);

		// ************************
		// backoffice 운영 공간(Room)
		// ************************
		
		List<OfficeRoomDto> rvos = null;
		rvos = dao.select_all_room(backoffice_no);
		
		if (rvos != null) {
			for (OfficeRoomDto vo : rvos) {
				vo.setRoom_type(info_map.changeType(vo.getRoom_type()));
			}
		}
		

		// **************
		// backoffice 문의
		// **************

		// 페이징 처리 로직
		// 리스트 수
		long total_rowCount_question_all = dao.total_rowCount_question_all(backoffice_no);
		log.info("total_rowCount_question_all: {}", total_rowCount_question_all);

		// 총 페이징 되는 수
		long totalPageCnt = (long) Math.ceil(total_rowCount_question_all / 4.0);
		log.info("totalPageCnt: {}", totalPageCnt);

		long nowPage = page;

		long maxPage = 0;

		if (nowPage % 5 != 0) {
			if (nowPage == totalPageCnt) {
				maxPage = nowPage;
			} else if (((nowPage / 5) + 1) * 5 >= totalPageCnt) {
				maxPage = totalPageCnt;
			} else if (((nowPage / 5) + 1) * 5 < totalPageCnt) {
				maxPage = ((nowPage / 5) + 1) * 5;
			}
		} else if (nowPage % 5 == 0) {
			if (nowPage <= totalPageCnt) {
				maxPage = nowPage;
			}
		}

		map.put("totalPageCnt", totalPageCnt);
		map.put("nowPage", nowPage);
		map.put("maxPage", maxPage);

		// 페이징 처리 계산 로직 끝

		List<OfficeQuestionDto> cvdto = dao.select_all_comment(backoffice_no, page);

		String is_login = (String) session.getAttribute("user_id");
		// String is_login = null;

		if (cvdto != null) {
			for (OfficeQuestionDto vo : cvdto) {

				log.info("is_login :::::::::: {}", is_login);
				log.info("user_no :::::::::: {}", vo.getUser_id());

				OfficeQuestionDto dto2 = dao.select_one_answer(vo.getComment_no());
				if (dto2 != null) {
					if (vo.getUser_id().equals(is_login)) {
						vo.setAnswer_content(dto2.getComment_content());
						vo.setAnswer_date(dto2.getComment_date());
						vo.setComment_state("Y");
					} else {
						if (vo.getIs_secret() == null || vo.getIs_secret().equals("F")) {
							vo.setAnswer_content(dto2.getComment_content());
							vo.setAnswer_date(dto2.getComment_date());
							vo.setComment_state("Y");
						} else {
							vo.setAnswer_content("x");
							vo.setAnswer_date("x");
						}
					}
				} else {
					vo.setComment_state("N");
					vo.setAnswer_content("x");
					vo.setAnswer_date("x");

				}

				// 이름 마스킹
				String originName = vo.getUser_name();
				String firstName = originName.substring(0, 1);
				String midName = originName.substring(1, originName.length() - 1);

				String maskingMidName = "";
				for (int i = 0; i < midName.length(); i++) {
					maskingMidName += "*";
				}

				String lastName = originName.substring(originName.length() - 1, originName.length());

				String maskingName = firstName + maskingMidName + lastName;

				vo.setUser_name(maskingName);
				vo.setUser_image("https://rence.s3.ap-northeast-2.amazonaws.com/user/" + vo.getUser_image());
			}
		}

		// **************
		// backoffice 후기
		// **************

		// 페이징 처리 로직
		// 리스트 수
		long total_rowCount_review_all = dao.total_rowCount_review_all(backoffice_no);

		// 총 페이징 되는 수
		long totalPageCnt2 = (long) Math.ceil(total_rowCount_review_all / 4.0);
		log.info("totalPageCnt: {}", totalPageCnt2);

		long nowPage2 = page;
		log.info("nowPage2: {}", nowPage2);

		long maxPage2 = 0;
		log.info("maxPage2 : {}", maxPage2);

		if (nowPage2 % 5 != 0) {
			if (nowPage2 == totalPageCnt2) {
				maxPage2 = nowPage2;
			} else if (((nowPage2 / 5) + 1) * 5 >= totalPageCnt2) {
				maxPage2 = totalPageCnt2;
			} else if (((nowPage2 / 5) + 1) * 5 < totalPageCnt2) {
				maxPage2 = ((nowPage2 / 5) + 1) * 5;
			}
		} else if (nowPage2 % 5 == 0) {
			if (nowPage2 <= totalPageCnt2) {
				maxPage2 = nowPage2;
			}
		}

		log.info("maxPage2 : {}", maxPage2);

		map.put("totalPageCnt2", totalPageCnt2);
		map.put("nowPage2", nowPage2);
		map.put("maxPage2", maxPage2);
		map.put("page", "space_introduce_detail");

		// 페이징 처리 계산 로직 끝

		List<OfficeReview_ViewDto> revos = dao.select_all_review(backoffice_no, page);

		for (OfficeReview_ViewDto dto : revos) {

			// 이름 마스킹
			String originName = dto.getUser_name();
			String firstName = originName.substring(0, 1);
			String midName = originName.substring(1, originName.length() - 1);

			String maskingMidName = "";
			for (int i = 0; i < midName.length(); i++) {
				maskingMidName += "*";
			}

			String lastName = originName.substring(originName.length() - 1, originName.length());

			String maskingName = firstName + maskingMidName + lastName;

			dto.setUser_name(maskingName);
			dto.setUser_image("https://rence.s3.ap-northeast-2.amazonaws.com/user/" + dto.getUser_image());
		}

		Map<String, Object> res = new HashMap<String, Object>();

		// backoffice 기본 정보
		res.put("res", map);
		res.put("ovo", odto);
		res.put("short_roadname_address", short_roadname_address);
		res.put("type_list", type_list);
		res.put("tag_list", tag_list);
		res.put("img_list", img_list);
		res.put("option_list", option_list);
		res.put("around_option_list", around_option_list);

		res.put("introduce_menu", introduce_menu);

		// backoffice 운영 시간
		res.put("otvo", otvo);

		// backoffice 운영 공간
		res.put("rvos", rvos);

		// backoffice 문의
		res.put("is_login", is_login);
		res.put("cvdto", cvdto);
		res.put("cvdto_cnt", total_rowCount_question_all);

		// backoffice 후기
		res.put("revos", revos);
		res.put("review_cnt", total_rowCount_review_all);

		res.put("page", "space_detail");

		return res;
	}

	// 질문 페이징
	@Override
	public Map<String, Object> introduce_q_paging(BackOfficeDTO bdto, Integer page) {
		log.info("introduce_q_paging()...");

		// **************
		// backoffice 문의 페이징
		// **************
		String backoffice_no = bdto.getBackoffice_no();

		// 페이징 처리 로직
		// 리스트 수
		long total_rowCount_question_all = dao.total_rowCount_question_all(backoffice_no);
		log.info("total_rowCount_question_all: {}", total_rowCount_question_all);

		// 총 페이징 되는 수
		long totalPageCnt = (long) Math.ceil(total_rowCount_question_all / 1.0);
		log.info("totalPageCnt: {}", totalPageCnt);

		long nowPage = page;

		long maxPage = 0;

		if (nowPage % 5 != 0) {
			if (nowPage == totalPageCnt) {
				maxPage = nowPage;
			} else if (((nowPage / 5) + 1) * 5 >= totalPageCnt) {
				maxPage = totalPageCnt;
			} else if (((nowPage / 5) + 1) * 5 < totalPageCnt) {
				maxPage = ((nowPage / 5) + 1) * 5;
			}
		} else if (nowPage % 5 == 0) {
			if (nowPage <= totalPageCnt) {
				maxPage = nowPage;
			}
		}

		log.info("maxPage: " + maxPage);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("totalPageCnt", totalPageCnt);
		map.put("nowPage", nowPage);
		map.put("maxPage", maxPage);

		// 페이징 처리 계산 로직 끝

		List<OfficeQuestionDto> cvdto = dao.select_all_comment(backoffice_no, page);

		String is_login = (String) session.getAttribute("user_id");

		if (cvdto != null) {
			for (OfficeQuestionDto dto : cvdto) {

				log.info("is_login :::::::::: {}", is_login);
				log.info("user_no :::::::::: {}", dto.getUser_id());

				OfficeQuestionDto dto2 = dao.select_one_answer(dto.getComment_no());
				if (dto2 != null) {
					if (dto.getUser_id().equals(is_login)) {
						dto.setAnswer_content(dto2.getComment_content());
						dto.setAnswer_date(dto2.getComment_date());
						dto.setComment_state("Y");
					} else {
						if (dto.getIs_secret() == null || dto.getIs_secret().equals("F")) {
							dto.setAnswer_content(dto2.getComment_content());
							dto.setAnswer_date(dto2.getComment_date());
							dto.setComment_state("Y");
						} else {
							dto.setAnswer_content("x");
							dto.setAnswer_date("x");
						}
					}

				} else {
					dto.setComment_state("N");
					dto.setAnswer_content("x");
					dto.setAnswer_date("x");
				}

				// 이름 마스킹
				String originName = dto.getUser_name();
				String firstName = originName.substring(0, 1);
				String midName = originName.substring(1, originName.length() - 1);

				String maskingMidName = "";
				for (int i = 0; i < midName.length(); i++) {
					maskingMidName += "*";
				}

				String lastName = originName.substring(originName.length() - 1, originName.length());

				String maskingName = firstName + maskingMidName + lastName;

				dto.setUser_name(maskingName);
				dto.setUser_image("https://rence.s3.ap-northeast-2.amazonaws.com/user/" + dto.getUser_image());
			}
		}

		map.put("cvdto", cvdto);
		return map;
	}

	@Override
	public Map<String, Object> introduce_r_paging(BackOfficeDTO bdto, Integer page) {
		log.info("introduce_r_paging()...");

		// **************
		// backoffice 후기
		// **************
		String backoffice_no = bdto.getBackoffice_no();
		Map<String, Object> map = new HashMap<String, Object>();

		// 페이징 처리 로직
		// 리스트 수
		long total_rowCount_review_all2 = dao.total_rowCount_review_all(backoffice_no);

		// 총 페이징 되는 수
		long totalPageCnt2 = (long) Math.ceil(total_rowCount_review_all2 / 4.0);
		log.info("totalPageCnt: {}", totalPageCnt2);

		long nowPage2 = page;
		log.info("nowPage2: {}", nowPage2);

		long maxPage2 = 0;
		log.info("maxPage2 : {}", maxPage2);

		if (nowPage2 % 5 != 0) {
			if (nowPage2 == totalPageCnt2) {
				maxPage2 = nowPage2;
			} else if (((nowPage2 / 5) + 1) * 5 >= totalPageCnt2) {
				maxPage2 = totalPageCnt2;
			} else if (((nowPage2 / 5) + 1) * 5 < totalPageCnt2) {
				maxPage2 = ((nowPage2 / 5) + 1) * 5;
			}
		} else if (nowPage2 % 5 == 0) {
			if (nowPage2 <= totalPageCnt2) {
				maxPage2 = nowPage2;
			}
		}

		log.info("maxPage2 : {}", maxPage2);

		map.put("totalPageCnt2", totalPageCnt2);
		map.put("nowPage2", nowPage2);
		map.put("maxPage2", maxPage2);
		map.put("page", "space_introduce_detail");

		// 페이징 처리 계산 로직 끝

		List<OfficeReview_ViewDto> redtos = dao.select_all_review(backoffice_no, page);

		for (OfficeReview_ViewDto dto : redtos) {

			// 이름 마스킹
			String originName = dto.getUser_name();
			String firstName = originName.substring(0, 1);
			String midName = originName.substring(1, originName.length() - 1);

			String maskingMidName = "";
			for (int i = 0; i < midName.length(); i++) {
				maskingMidName += "*";
			}

			String lastName = originName.substring(originName.length() - 1, originName.length());

			String maskingName = firstName + maskingMidName + lastName;

			dto.setUser_name(maskingName);
			dto.setUser_image("https://rence.s3.ap-northeast-2.amazonaws.com/user/" + dto.getUser_image());
		}

		map.put("redtos", redtos);

		return map;
	}

	@Override
	public Map<String, Object> reserve_check(String backoffice_no, String room_no, String reserve_date) {
		log.info("reserve_check");

		Map<String, Object> map = new HashMap<String, Object>();

		List<OfficeReserveDto> dtos = dao.check_reserve(backoffice_no, room_no, reserve_date);

		List<RoomScheduleDto> rdtos = dao.select_all_room_schedule(backoffice_no, room_no, reserve_date);

		List<RoomScheduleDto> dvos = dao.select_all_room_schedule_dayoff(backoffice_no, room_no);

		List<Integer> already_reserve_list = new ArrayList<>();

		// 예약 시간 설정
		for (OfficeReserveDto dto : dtos) {
			int stime = Integer.parseInt(dto.getReserve_stime());
			int etime = Integer.parseInt(dto.getReserve_etime());

			for (int i = stime; i < etime; i++) {
				if (!Arrays.asList(already_reserve_list).contains(i)) {
					already_reserve_list.add(i);
				}
			}
		}

		// 브레이크 타임 시간 설정
		for (RoomScheduleDto dto : rdtos) {
			String not_stime = dto.getNot_stime();
			String not_etime = dto.getNot_etime();

			String[] split_stime = not_stime.split(" ");
			String[] split_etime = not_etime.split(" ");

			int stime = 0;
			int etime = 0;

			if (split_stime[0].equals(split_etime[0])) {
				String stime_hour = split_stime[1].split(":")[0];
				String etime_hour = split_etime[1].split(":")[0];

				stime = Integer.parseInt(stime_hour);
				etime = Integer.parseInt(etime_hour);
			}

			for (int i = stime; i < etime; i++) {
				if (!Arrays.asList(already_reserve_list).contains(i)) {
					already_reserve_list.add(i);
				}
			}
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

		// 휴무 시간 설정
		for (RoomScheduleDto dto : dvos) {
			String not_sdate = dto.getNot_stime().split(" ")[0].replace("-", "/");
			String not_edate = dto.getNot_etime().split(" ")[0].replace("-", "/");

			if (!not_sdate.equals(not_edate)) {
				LocalDate sdate = LocalDate.parse(not_sdate, formatter);
				LocalDate edate = LocalDate.parse(not_edate, formatter);

				List<LocalDate> temp_date_list = getDatesBetweenTwoDates(sdate, edate);

				for (LocalDate d : temp_date_list) {

					if (d.format(formatter).equals(reserve_date)) {
						for (int i = 0; i < 23; i++) {
							if (!Arrays.asList(already_reserve_list).contains(i)) {
								already_reserve_list.add(i);
							}
						}
					}
				}

			}

		}
		log.info("already_reserve_list: {}",already_reserve_list);
		map.put("reserve_list", already_reserve_list);
		return map;
	}

	public static List<LocalDate> getDatesBetweenTwoDates(LocalDate startDate, LocalDate endDate) {
		int numOfDaysBetween = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
		return IntStream.iterate(0, i -> i + 1).limit(numOfDaysBetween).mapToObj(i -> startDate.plusDays(i))
				.collect(Collectors.toList());
	}

	@Override
	public Map<String, String> office_reserve_check_rsu(String backoffice_no, String room_no, String reserve_stime,
			String reserve_etime) throws ParseException {

		log.info("office_reserve_check_rsu()...");

		Map<String, String> map = new HashMap<String, String>();

		List<OfficeReserveDto> dtos = dao.check_reserve_office(backoffice_no, room_no);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

		log.info("office reserve dtos : {}", dtos);
		Date reserve_sdate = formatter.parse(reserve_stime);
		Date reserve_edate = formatter.parse(reserve_etime);

		int reserve_flag = 0;

		for (OfficeReserveDto dto : dtos) {
			log.info("first dto : {}", dto);
			dto.setReserve_sdate(dto.getReserve_sdate().split(" ")[0]);
			dto.setReserve_sdate(dto.getReserve_sdate().replace("-", "/"));
			dto.setReserve_edate(dto.getReserve_edate().split(" ")[0]);
			dto.setReserve_edate(dto.getReserve_edate().replace("-", "/"));

			Date sdate = formatter.parse(dto.getReserve_sdate());
			Date edate = formatter.parse(dto.getReserve_edate());

			int compareSdate1 = reserve_sdate.compareTo(sdate);
			int compareEdate1 = reserve_edate.compareTo(sdate);
			int compareSdate2 = reserve_sdate.compareTo(edate);
			int compareEdate2 = reserve_edate.compareTo(edate);

			log.info("compare : {} {} {}", reserve_sdate, sdate, edate);
			log.info("compare result : {} {}", compareSdate1, compareEdate1);
			log.info("compare result : {} {}", compareSdate2, compareEdate2);

			if ((compareSdate1 < 0 && compareEdate1 < 0) || (compareSdate2 > 0 && compareEdate2 > 0)) {
				reserve_flag = 1;
			} else {
				reserve_flag = 0;
				break;
			}
		}

		// 예약이 하나도 없을 때
		if (dtos.isEmpty()) {
			reserve_flag = 1;
		}

		if (reserve_flag == 1) {
			map.put("result", "1");
		} else {
			map.put("result", "0");
		}

		log.info("map: {}", map);

		return map;

	}

	@Override
	public Map<String, String> reserve_rsu(OfficeReserveDto rdto) throws ParseException {
		log.info("reserve_rsu()....");

		Map<String, String> map = new HashMap<String, String>();

		OfficeReserveDto_date date_dto = new OfficeReserveDto_date();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date reserve_stime = formatter.parse(rdto.getReserve_stime());
		Date reserve_etime = formatter.parse(rdto.getReserve_etime());

		date_dto.setBackoffice_no(rdto.getBackoffice_no());
		date_dto.setRoom_no(rdto.getRoom_no());
		date_dto.setRoom_type(rdto.getRoom_type());
		date_dto.setUser_no(rdto.getUser_no());
		date_dto.setReserve_stime(reserve_stime);
		date_dto.setReserve_etime(reserve_etime);
		date_dto.setReserve_sdate(reserve_stime);
		date_dto.setReserve_edate(reserve_etime);
		
		log.info("===date_dto: {}", date_dto);
		

		int result = dao.confirm_reserve(date_dto);
		log.info("controller /backoffice/reserve result :: {}", result);

		if (result == 1) {
			String reserve_no = dao.select_one_last_reserve(rdto.getUser_no());

			map.put("result", "1");
			map.put("reserve_no", reserve_no);
		} else {
			map.put("result", "0");
		}
		return map;
	}

	@Override
	public Map<String, Object> space_intruduce_office(BackOfficeDTO bdto, String introduce_menu, Integer page) {
		log.info("space_intruduce_office()....");
		Map<String, Object> map = new HashMap<String, Object>();

		OptionEngToKorMap_office info_map = new OptionEngToKorMap_office();

		String backoffice_no = bdto.getBackoffice_no();

		// ******************
		// backoffice 기본 정보
		// ******************
		OfficeInfo_ViewDto odto = dao.select_one_office_info(backoffice_no);
		List<String> type_list = new ArrayList<String>();
		List<String> tag_list = new ArrayList<String>();
		String[] img_list = null;
		List<String> option_list = new ArrayList<String>();
		List<String> around_option_list = new ArrayList<String>();

		if (odto.getBackoffice_type() != null) {
			type_list = info_map.splitType(odto.getBackoffice_type());
		} else {
			type_list.add("타입 없음");
		}

		if (odto.getBackoffice_tag() != null) {
			tag_list = info_map.splitTag(odto.getBackoffice_tag());
		} else {
			tag_list.add("태그 없음");
		}

		img_list = info_map.splitImage(odto.getBackoffice_image());

		if (odto.getBackoffice_option() != null) {
			option_list = info_map.splitOption(odto.getBackoffice_option());
		} else {
			option_list.add("옵션 없음");
		}

		if (odto.getBackoffice_around() != null) {
			around_option_list = info_map.splitAroundOption(odto.getBackoffice_around());
		} else {
			around_option_list.add("주변 시설 없음");
		}

		String short_roadname_address = info_map.makeShortAddress(odto.getRoadname_address());

		// ******************
		// backoffice 운영 시간
		// ******************
		OfficeOperatingTimeDto_date dotvo = dao.select_one_operating_time(backoffice_no);

		CustomDateFormatter df = new CustomDateFormatter();
		OfficeOperatingTimeDto otvo = df.showStringOfficeOperating(dotvo);

		// ************************
		// backoffice 운영 공간(Room)
		// ************************
		
		List<OfficeRoomDto> rdtos = null;
		rdtos = dao.select_all_room(backoffice_no);
		
		if (rdtos != null) {
			for (OfficeRoomDto vo : rdtos) {
				vo.setRoom_type(info_map.changeType(vo.getRoom_type()));
			}
		}
		log.info("===dtos: {} ====", rdtos);

		// **************
		// backoffice 문의
		// **************

		// 페이징 처리 로직
		// 리스트 수
		long total_rowCount_question_all = dao.total_rowCount_question_all(backoffice_no);
		log.info("total_rowCount_question_all: {}", total_rowCount_question_all);

		// 총 페이징 되는 수
		long totalPageCnt = (long) Math.ceil(total_rowCount_question_all / 4.0);
		log.info("totalPageCnt: {}", totalPageCnt);

		long nowPage = page;

		long maxPage = 0;

		if (nowPage % 5 != 0) {
			if (nowPage == totalPageCnt) {
				maxPage = nowPage;
			} else if (((nowPage / 5) + 1) * 5 >= totalPageCnt) {
				maxPage = totalPageCnt;
			} else if (((nowPage / 5) + 1) * 5 < totalPageCnt) {
				maxPage = ((nowPage / 5) + 1) * 5;
			}
		} else if (nowPage % 5 == 0) {
			if (nowPage <= totalPageCnt) {
				maxPage = nowPage;
			}
		}

		log.info("maxPage: " + maxPage);

		map.put("totalPageCnt", totalPageCnt);
		map.put("nowPage", nowPage);
		map.put("maxPage", maxPage);

		// 페이징 처리 계산 로직 끝

		List<OfficeQuestionDto> cdtos = dao.select_all_comment(backoffice_no, page);

		String is_login = (String) session.getAttribute("user_id");
//		String is_login = null;

		if (cdtos != null) {
			for (OfficeQuestionDto dto : cdtos) {

				log.info("is_login :::::::::: {}", is_login);
				log.info("user_no :::::::::: {}", dto.getUser_id());

				OfficeQuestionDto dto2 = dao.select_one_answer(dto.getComment_no());
				if (dto2 != null) {
					if (dto.getUser_id().equals(is_login)) {
						dto.setAnswer_content(dto2.getComment_content());
						dto.setAnswer_date(dto2.getComment_date());
						dto.setComment_state("Y");
					} else {
						if (dto.getIs_secret() == null || dto.getIs_secret().equals("F")) {
							dto.setAnswer_content(dto2.getComment_content());
							dto.setAnswer_date(dto2.getComment_date());
							dto.setComment_state("Y");
						} else {
							dto.setAnswer_content("x");
							dto.setAnswer_date("x");
						}
					}
				} else {
					dto.setComment_state("N");
					dto.setAnswer_content("x");
					dto.setAnswer_date("x");
				}

				// 이름 마스킹
				String originName = dto.getUser_name();
				String firstName = originName.substring(0, 1);
				String midName = originName.substring(1, originName.length() - 1);

				String maskingMidName = "";
				for (int i = 0; i < midName.length(); i++) {
					maskingMidName += "*";
				}

				String lastName = originName.substring(originName.length() - 1, originName.length());

				String maskingName = firstName + maskingMidName + lastName;

				dto.setUser_name(maskingName);
				dto.setUser_image("https://rence.s3.ap-northeast-2.amazonaws.com/user/" + dto.getUser_image());
			}
		}

		// **************
		// backoffice 후기
		// **************

		// 페이징 처리 로직
		// 리스트 수
		long total_rowCount_review_all = dao.total_rowCount_review_all(backoffice_no);

		// 총 페이징 되는 수
		long totalPageCnt2 = (long) Math.ceil(total_rowCount_review_all / 4.0);

		long nowPage2 = page;

		long maxPage2 = 0;

		if (nowPage2 % 5 != 0) {
			if (nowPage2 == totalPageCnt2) {
				maxPage2 = nowPage2;
			} else if (((nowPage2 / 5) + 1) * 5 >= totalPageCnt2) {
				maxPage2 = totalPageCnt2;
			} else if (((nowPage2 / 5) + 1) * 5 < totalPageCnt2) {
				maxPage2 = ((nowPage2 / 5) + 1) * 5;
			}
		} else if (nowPage2 % 5 == 0) {
			if (nowPage2 <= totalPageCnt2) {
				maxPage2 = nowPage2;
			}
		}

		map.put("totalPageCnt2", totalPageCnt2);
		map.put("nowPage2", nowPage2);
		map.put("maxPage2", maxPage2);
		map.put("page", "space_detail_office");

		log.info("totalPageCnt2 : {}", totalPageCnt2);
		log.info("nowPage2 : {}", nowPage2);
		log.info("maxPage2 : {}", maxPage2);

		// 페이징 처리 계산 로직 끝

		List<OfficeReview_ViewDto> revos = dao.select_all_review(backoffice_no, page);

		for (OfficeReview_ViewDto dto : revos) {

			// 이름 마스킹
			String originName = dto.getUser_name();
			String firstName = originName.substring(0, 1);
			String midName = originName.substring(1, originName.length() - 1);

			String maskingMidName = "";
			for (int i = 0; i < midName.length(); i++) {
				maskingMidName += "*";
			}

			String lastName = originName.substring(originName.length() - 1, originName.length());

			String maskingName = firstName + maskingMidName + lastName;

			dto.setUser_name(maskingName);
			dto.setUser_image("https://rence.s3.ap-northeast-2.amazonaws.com/user/" + dto.getUser_image());
		}

		// backoffice 기본 정보
		map.put("ovo", odto);
		map.put("type_list", type_list);
		map.put("tag_list", tag_list);
		map.put("img_list", img_list);
		map.put("option_list", option_list);
		map.put("around_option_list", around_option_list);
		map.put("short_roadname_address", short_roadname_address);
		map.put("page", "space_detail_office");

		// backoffice 운영 시간
		map.put("otvo", otvo);

		// backoffice 운영 공간
		map.put("rvos", rdtos);

		// backoffice 문의
		map.put("is_login", is_login);
		map.put("cvos", cdtos);
		map.put("cvos_cnt", total_rowCount_question_all);

		// backoffice 후기
		map.put("revos", revos);
		map.put("review_cnt", total_rowCount_review_all);

		return map;
	}

//	공간 결제 페이지
	@Override
	public Map<String, Object> space_payment_rsu(OfficeReserveDto rdto) throws ParseException {
		log.info("space_payment_rsu");

		Map<String, Object> map = new HashMap<String, Object>();

		String reserve_no = rdto.getReserve_no();

		PaymentInfoDto pdto = dao.select_one_final_payment_info(reserve_no);
		OfficeInfoMap info_map = new OfficeInfoMap();

		pdto.setRoom_type(info_map.changeType(pdto.getRoom_type()));
		List<String> splitImage = info_map.splitImage(pdto.getBackoffice_image());
		String room_first_image = splitImage.get(1);
		pdto.setBackoffice_image(room_first_image);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		Date sdate = formatter.parse(pdto.getReserve_stime());
		Date edate = formatter.parse(pdto.getReserve_etime());

		log.info("sdate : {}", sdate);
		log.info("edate : {}", edate);

		// diffTime : type이 desk, meeting 일 때, 시간 차이
		// office 일 때 일수 차이
		long diffTime = 0;
		int payment_all = 0;
		int earned_mileage = 0;

		// 사용자 총 예약 시간
		if (pdto.getRoom_type().equals("오피스")) {
			long diffSec = (edate.getTime() - sdate.getTime()) / 1000;
			// 일 계산
			diffTime = diffSec / (24 * 60 * 60);

			// 개월 계산
			diffTime /= 30;

		} else {
			diffTime = (edate.getTime() - sdate.getTime()) / 3600000;
		}

		// 전체 결제할 금액

		payment_all = (int) diffTime * pdto.getRoom_price();

		earned_mileage = (int) (payment_all * 0.05);
		log.info("diffTime ::::: {}", diffTime);

		map.put("pvo", pdto);
		map.put("payment_all", payment_all);
		map.put("earned_mileage", earned_mileage);

		return map;
	}

	@Override
	public Map<String, String> space_payment_rsu(OfficePaymentDto pdto) {
		log.info("space_payment_rsu");

		Map<String, String> map = new HashMap<String, String>();
		PaymentInfoDto pvo2 = dao.select_one_final_payment_info(pdto.getReserve_no());
		pdto.setRoom_no(pvo2.getRoom_no());
		pdto.setBackoffice_no(pvo2.getBackoffice_no());
		pdto.setSales_state("F");

		if (pdto.getPayment_state().equals("F")) {
			int payment_total = (pdto.getActual_payment() + pdto.getUse_mileage()) * 5;
			pdto.setPayment_total(payment_total);
		}

		if (pdto.getUse_mileage() > 0) {
			pdto.setPayment_total(pdto.getActual_payment() + pdto.getUse_mileage());
		}

		int result_payment = dao.insert_paymentOK(pdto);
		int result_update_reserve_state = dao.update_reserve_state(pdto.getReserve_no());

		OfficeMileageDto mdto = dao.select_one_recent_mileage(pdto.getUser_no());
		int mileage_change = 0;

		OfficeMileageDto mdto2 = new OfficeMileageDto();
		int mileage_total = mdto.getMileage_total();
		int result_mileage = 0; // 마일리지 추가 성공 flag

		if (pdto.getUse_mileage() == 0) {
			mdto2.setMileage_state("W");

			if (pdto.getPayment_state().equals("F")) {
				// 후결제 마일리지 적립 X
				mileage_change = 0;
			} else if (pdto.getPayment_state().equals("T")) {
				// 선결제만 마일리지 적립
				mileage_change = (int) ((pdto.getPayment_total()) * 0.05);
			}

			mdto2.setMileage_total(mileage_total);
			mdto2.setUser_no(pdto.getUser_no());
			mdto2.setMileage_change(mileage_change);

			OfficePaymentDto pvo3 = dao.select_one_recent_payment(pdto.getUser_no());
			mdto2.setPayment_no(pvo3.getPayment_no());

			result_mileage = dao.insert_mileage_changed(mdto2);

		} else {
			// 마일리지 사용
			mdto2.setMileage_state("F");

			mileage_change = pdto.getUse_mileage();
			mileage_total -= mileage_change;

			mdto2.setMileage_total(mileage_total);
			mdto2.setUser_no(pdto.getUser_no());
			mdto2.setMileage_change(mileage_change);

			OfficePaymentDto pdto3 = dao.select_one_recent_payment(pdto.getUser_no());
			mdto2.setPayment_no(pdto3.getPayment_no());

			result_mileage = dao.insert_mileage_changed(mdto2);

			// 마일리지 사용 후 해당 결제 건에 대한 마일리지 적립 로직
			mdto2.setMileage_state("W");

			if (pdto.getPayment_state().equals("F")) {
				mileage_change = 0;
			} else if (pdto.getPayment_state().equals("T")) {
				mileage_change = (int) ((pdto.getPayment_total()) * 0.05);
			}

			mdto2.setMileage_change(mileage_change);

			result_mileage = dao.insert_mileage_changed(mdto2);
		}

		if (result_payment == 1 && result_update_reserve_state == 1 && result_mileage == 1) {
			map.put("result", "1");
		} else {
			map.put("result", "0");
		}
		return map;
	}

	@Override
	public Map<String, String> insert_question(Comment_Dto dto) {
		log.info("insert_question");

		int result = dao.insert_question(dto);

		log.info("insert_question()..");
		log.info("********** request :: {}", dto);

		Map<String, String> map = new HashMap<>();

		// Map -> Json 문자열
		Gson gson = new Gson();
		if (result == 1) {
			map.put("result", "1");
		} else {
			map.put("result", "0");
		}
		return map;
	}

	@Override
	public Map<String, Object> list_page(String type, Integer page, String condition) {
		Map<String, Object> map = new HashMap<String, Object>();

		int total_cnt = dao.list_totalCnt("%" + type + "%");

		List<ListViewDto> list = dao.select_all_list(type, condition, 12 * (page - 1) + 1, 12 * (page));

		if (list == null)
			map.put("cnt", 0);
		else
			map.put("cnt", list.size());

		if (list != null) {
			for (ListViewDto dto : list) {
				DecimalFormat dc = new DecimalFormat("###,###,###,###");
				String ch = dc.format(Integer.parseInt(dto.getMin_room_price()));
				dto.setMin_room_price(ch);
				dto.setAvg_rating(Double.toString((Math.round(Double.parseDouble(dto.getAvg_rating()) * 100) / 100.0)));

				dto.setBackoffice_image(
						"https://rence.s3.ap-northeast-2.amazonaws.com/space/" + dto.getBackoffice_image());

				if (dto.getBackoffice_tag() != null) {
					String[] tags = dto.getBackoffice_tag().split(",");

					int i = 0;
					for (String tag : tags) {
						tag = "#" + tag;
						tags[i] = tag;
						i++;
					}

					dto.setBackoffice_tag(String.join(" ", tags));
				}

				if (dto.getRoadname_address().contains(" ")) {
					String road_name = dto.getRoadname_address().split(" ")[0] + " "
							+ dto.getRoadname_address().split(" ")[1];
					dto.setRoadname_address(road_name);
				}
			}
		}

		map.put("condition", condition);
		map.put("page", "list_page");
		map.put("nowCnt", 1);

		if (total_cnt > 0)
			map.put("maxCnt", total_cnt);
		else
			map.put("maxCnt", 0);

		map.put("list", list);

		return map;
	}

	// 리스트 페이지 페이징
	@Override
	public Map<String, Object> list_paging(String type, Integer page, String condition) {
		log.info("list_paging()....");

		Map<String, Object> map = new HashMap<String, Object>();

		List<ListViewDto> list = dao.select_all_list(type, condition, 12 * (page - 1) + 1, 12 * (page));

		if (list == null)
			map.put("cnt", 0);
		else
			map.put("cnt", list.size());

		if (list != null) {
			for (ListViewDto dto : list) {
				DecimalFormat dc = new DecimalFormat("###,###,###,###");
				String ch = dc.format(Integer.parseInt(dto.getMin_room_price()));
				dto.setMin_room_price(ch);
				dto.setAvg_rating(Double.toString((Math.round(Double.parseDouble(dto.getAvg_rating()) * 100) / 100.0)));

				dto.setBackoffice_image(
						"https://rence.s3.ap-northeast-2.amazonaws.com/space/" + dto.getBackoffice_image());

				if (dto.getBackoffice_tag() != null) {
					String[] tags = dto.getBackoffice_tag().split(",");

					int i = 0;
					for (String tag : tags) {
						tag = "#" + tag;
						tags[i] = tag;
						i++;
					}

					dto.setBackoffice_tag(String.join(" ", tags));
				}

				if (dto.getRoadname_address().contains(" ")) {
					String road_name = dto.getRoadname_address().split(" ")[0] + " "
							+ dto.getRoadname_address().split(" ")[1];
					dto.setRoadname_address(road_name);
				}
			}
		}

		map.put("list", list);
		return map;
	}

	// 검색 리스트 페이지 페이징
	@Override
	public Map<String, Object> search_list_paging(String type, String location, String searchWord, String condition,
			Integer page) {

		log.info("search_list_paging()....");

		Map<String, Object> map = new HashMap<String, Object>();

		List<ListViewDto> list = null;
		list = dao.search_list(type, location, searchWord, condition, 12 * (page - 1) + 1, 12 * (page));

		if (list == null)
			map.put("cnt", 0);
		else
			map.put("cnt", list.size());

		if (list != null) {
			for (ListViewDto dto : list) {
				DecimalFormat dc = new DecimalFormat("###,###,###,###");
				String ch = dc.format(Integer.parseInt(dto.getMin_room_price()));
				dto.setMin_room_price(ch);
				dto.setAvg_rating(Double.toString((Math.round(Double.parseDouble(dto.getAvg_rating()) * 100) / 100.0)));

				dto.setBackoffice_image(
						"https://rence.s3.ap-northeast-2.amazonaws.com/space/" + dto.getBackoffice_image());

				if (dto.getBackoffice_tag() != null) {
					String[] tags = dto.getBackoffice_tag().split(",");

					int i = 0;
					for (String tag : tags) {
						tag = "#" + tag;
						tags[i] = tag;
						i++;
					}

					dto.setBackoffice_tag(String.join(" ", tags));
				}

				if (dto.getRoadname_address().contains(" ")) {
					String road_name = dto.getRoadname_address().split(" ")[0] + " "
							+ dto.getRoadname_address().split(" ")[1];
					dto.setRoadname_address(road_name);
				}
			}
		}

		map.put("list", list);
		return map;
	}

}// end class
