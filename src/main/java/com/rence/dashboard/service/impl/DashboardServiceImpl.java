/**
 * 
 * @author 최진실
 *
 */
package com.rence.dashboard.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rence.backoffice.model.BackOfficeDTO;
import com.rence.backoffice.model.BackOfficeOperatingTimeDTO;
import com.rence.common.OptionEngToKorMap;
import com.rence.dashboard.dao.DashboardDAO;
import com.rence.dashboard.model.CommentDTO;
import com.rence.dashboard.model.CommentListQViewDTO;
import com.rence.dashboard.model.CommentSummaryViewDTO;
import com.rence.dashboard.model.ReservationViewDTO;
import com.rence.dashboard.model.ReserveListViewDTO;
import com.rence.dashboard.model.ReserveSummaryViewDTO;
import com.rence.dashboard.model.ReserveUpdateDTO;
import com.rence.dashboard.model.ReviewListViewDTO;
import com.rence.dashboard.model.RoomDTO;
import com.rence.dashboard.model.RoomSummaryViewDTO;
import com.rence.dashboard.model.SalesSettlementDetailViewDTO;
import com.rence.dashboard.model.SalesSettlementSummaryViewDTO;
import com.rence.dashboard.model.SalesSettlementViewDTO;
import com.rence.dashboard.model.ScheduleDTO;
import com.rence.dashboard.model.ScheduleListViewDTO;
import com.rence.dashboard.service.DashboardService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	DashboardDAO dao;

	/**
	 * 대쉬보드 메인
	 */
	@Override
	public Map<String, Object> dashboard_main(String backoffice_no) {

		Map<String, Object> map = new HashMap<String, Object>();

		List<ReserveSummaryViewDTO> rvos = dao.reserve_summary_selectAll(backoffice_no);
		log.info("dashboard main rvos : {}", rvos);
		List<CommentSummaryViewDTO> cvos = dao.comment_summary_selectAll(backoffice_no);
		log.info("dashboard main cvos : {}", cvos);
		SalesSettlementSummaryViewDTO svo = dao.payment_summary_selectOne(backoffice_no);
		log.info("dashboard main svo : {}", svo);
		RoomSummaryViewDTO rmvo = dao.room_summary_selectOne(backoffice_no);
		log.info("dashboard main rmvo : {}", rmvo);

		map.put("r_vos", rvos);
		map.put("r_cnt", rvos.size());
		map.put("c_vos", cvos);
		map.put("c_cnt", cvos.size());
		map.put("svo", svo);
		map.put("rmvo", rmvo);

		return map;

	}

	/**
	 * 공간관리 - 공간 리스트
	 */
	@Override
	public Map<String, Object> dashboard_room_list(String backoffice_no, Integer page) {

		/////////////////////// 페이징/////////////////////////////////////////
		Map<String, Object> map = new HashMap<String, Object>();
		// 리스트 수
		long total_rowCount_all = dao.dashboard_room_list_cnt(backoffice_no);
		log.info("total_rowCount_reserve_now: {}", total_rowCount_all);

		// 총 페이징되는 수
		long totalPageCnt = (long) Math.ceil(total_rowCount_all / 12.0);
		log.info("totalPageCnt: {}", totalPageCnt);

		// 현재페이지
		long nowPage = page;

		// 5page씩
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
		////////////////////////////////////////////////////////////////////

		List<RoomDTO> rmvos = dao.dashboard_room_list(backoffice_no, page);
		String unit = "원/시간";
		for (RoomDTO vo : rmvos) {
			if (vo.getRoom_type().equals("office")) {
				unit = "원/개월";
			}
		}

		log.info("unit:{}", unit);
		log.info("rmvos:{}", rmvos);
		map.put("rm_vos", rmvos);
		map.put("unit", unit);

		map.put("page", "room");

		return map;
	}

	/**
	 * 공간관리 - 공간 추가 팝업(백오피스 정보(타입) 가져오기)
	 */
	@Override
	public Map<String, Object> backoffice_insert_room(String backoffice_no) {
		Map<String, Object> map = new HashMap<String, Object>();

		log.info("backoffice_no::{}", backoffice_no);
		BackOfficeDTO bvo = dao.select_one_backoffice_info(backoffice_no);
		log.info("bvo : {}", bvo);

		RoomDTO rmvo = new RoomDTO();

		String type = bvo.getBackoffice_type();
		type = bvo.getBackoffice_type().replace("meeting_room", "meeting_04,meeting_06,meeting_10");
		rmvo.setRoom_type(type);

		List<String> type_list = new ArrayList<String>();

		if (rmvo.getRoom_type() != null) {
			String[] type_split = rmvo.getRoom_type().split(",");

			for (int i = 0; i < type_split.length; i++) {
				type_list.add(type_split[i]);
			}

		} else {
			type_list.add("타입 없음");
		}

		map.put("room_type", type_list);

		return map;
	}

	/**
	 * 공간관리 - 공간 추가 처리
	 */
	@Override
	public Map<String, String> backoffice_insertOK_room(RoomDTO rvo, String backoffice_no) {
		Map<String, String> map = new HashMap<String, String>();

		int result = dao.backoffice_insertOK_room(backoffice_no, rvo);

		if (result == 1) {
			log.info("successed...");
			map.put("result", "1");
		}

		else {
			log.info("failed...");
			map.put("result", "0");
		}

		return map;
	}

	/**
	 * 공간관리 - 공간 수정 팝업
	 */
	@Override
	public Map<String, Object> backoffice_update_room(String backoffice_no, String room_no) {

		Map<String, Object> map = new HashMap<String, Object>();

		BackOfficeDTO bvo = dao.select_one_backoffice_info(backoffice_no);
		RoomDTO rmvo = new RoomDTO();

		String type = bvo.getBackoffice_type().replace("meeting_room", "meeting_04,meeting_06,meeting_10");
		rmvo.setRoom_type(type);

		List<String> type_list = new ArrayList<String>();

		if (rmvo.getRoom_type() != null) {
			String[] type_split = rmvo.getRoom_type().split(",");

			for (int i = 0; i < type_split.length; i++) {
				type_list.add(type_split[i]);
			}

		} else {
			type_list.add("타입 없음");
		}

		rmvo = dao.select_one_room_info(backoffice_no, room_no);
		log.info("rmvo : {}", rmvo);

		map.put("rmvo", rmvo);

		map.put("room_type", type_list);

		log.info("map{}", map);

		return map;
	}

	/**
	 * 공간관리 - 공간 수정
	 */
	@Override
	public Map<String, String> backoffice_updateOK_room(String backoffice_no, RoomDTO rvo) {
		Map<String, String> map = new HashMap<String, String>();

		int result = dao.backoffice_updateOK_room(backoffice_no, rvo);

		if (result == 1) {
			log.info("successed...");
			map.put("result", "1");
		}

		else {
			log.info("failed...");
			map.put("result", "0");
		}

		return map;

	}

	/**
	 * 공간관리 - 공간 삭제
	 */
	@Override
	public Map<String, String> backoffice_deleteOK_room(String backoffice_no, String room_no) {
		Map<String, String> map = new HashMap<String, String>();

		int result = 1;
		try {
			dao.backoffice_deleteOK_room(backoffice_no, room_no);
		} catch (Exception e) {
			result = 0;
		}

		if (result == 1) {
			dao.backoffice_qna_insert(backoffice_no, room_no);

			log.info("successed...");
			map.put("result", "1");
		}

		else {
			log.info("room delete failed...");
			map.put("result", "0"); // 남은 예약이 있을 시
		}

		return map;
	}

	/**
	 * 공간관리 - 문의(리스트)
	 */
	@Override
	public Map<String, Object> dashboard_qna(String backoffice_no, Integer page) {
		/////////////////////// 페이징/////////////////////////////////////////
		Map<String, Object> map = new HashMap<String, Object>();
		// 리스트 수
		long total_rowCount_all = dao.backoffice_qna_selectAll_cnt(backoffice_no);
		log.info("total_rowCount_reserve_now: {}", total_rowCount_all);

		// 총 페이징되는 수
		long totalPageCnt = (long) Math.ceil(total_rowCount_all / 10.0);
		log.info("totalPageCnt: {}", totalPageCnt);

		// 현재페이지
		long nowPage = page;

		// 5page씩 끊으면 끝 페이지 번호( ex, 총 9페이지이고, 현재페이지가 6이면 maxpage = 9)
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
		////////////////////////////////////////////////////////////////////

		List<CommentListQViewDTO> qvos = dao.backoffice_qna_selectAll(backoffice_no, page);
		log.info("qvos : {}", qvos);
		map.put("q_vos", qvos);
		map.put("cnt", qvos.size());

		map.put("page", "qna");

		return map;
	}

	/**
	 * 공간관리 - 답변 작성 팝업
	 */
	@Override
	public Map<String, Object> backoffice_insert_comment(String backoffice_no, String room_no, String comment_no) {
		Map<String, Object> map = new HashMap<String, Object>();

		CommentDTO cvo2 = dao.backoffice_insert_comment(backoffice_no, room_no, comment_no);

		map.put("cvo", cvo2);

		return map;
	}

	/**
	 * 공간관리 - 답변 작성
	 */
	@Override
	public Map<String, String> backoffice_insertOK_comment(String backoffice_no, CommentDTO cvo, String comment_no) {
		Map<String, String> map = new HashMap<String, String>();

		int result = dao.backoffice_insertOK_comment(cvo, backoffice_no, comment_no);

		if (result == 1) {
			// 부모 no state T 변경
			int flag = dao.update_comment_state_T(backoffice_no, comment_no);
			if (flag == 1) {
				log.info("successed...");
				map.put("result", "1");
			} else { // 답변 상태 변경 실패
				log.info("failed...");
				map.put("result", "0");
			}
		} else { // 답변 작성 실패
			log.info("failed...");
			map.put("result", "0");
		}
		return map;
	}

	/**
	 * 공간관리 - 답변 삭제
	 */
	@Override
	public Map<String, String> backoffice_deleteOK_comment(String backoffice_no, String comment_no, String mother_no) {
		Map<String, String> map = new HashMap<String, String>();

		int result = dao.backoffice_deleteOK_comment(backoffice_no, comment_no);

		if (result == 1) {
			// 부모 no state F 변경
			int flag = dao.update_comment_state_F(backoffice_no, mother_no);
			if (flag == 1) {
				log.info("successed...");
				map.put("result", "1");
			} else { // 답변 상태 변경 실패
				log.info("failed...");
				map.put("result", "0");
			}
		} else { // 답변 삭제 실패
			log.info("failed...");
			map.put("result", "0");
		}

		return map;
	}

	/**
	 * 공간관리 - 리뷰 (리스트)
	 */
	@Override
	public Map<String, Object> dashboard_review(String backoffice_no, Integer page) {
		/////////////////////// 페이징/////////////////////////////////////////
		Map<String, Object> map = new HashMap<String, Object>();
		// 리스트 수
		long total_rowCount_all = dao.backoffice_review_selectAll_cnt(backoffice_no);
		log.info("total_rowCount_reserve_now: {}", total_rowCount_all);

		// 총 페이징되는 수
		long totalPageCnt = (long) Math.ceil(total_rowCount_all / 6.0);
		log.info("totalPageCnt: {}", totalPageCnt);

		// 현재페이지
		long nowPage = page;

		// 5page씩 끊으면 끝 페이지 번호
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
		////////////////////////////////////////////////////////////////////

		List<ReviewListViewDTO> rvvos = dao.backoffice_review_selectAll(backoffice_no, page);
		log.info("rvvos : {}", rvvos);
		map.put("rv_vos", rvvos);
		map.put("cnt", rvvos.size());

		map.put("page", "review");
		return map;
	}

	/**
	 * 예약 관리 - 리스트
	 */
	@Override
	public Map<String, Object> dashboard_reserve(String backoffice_no, String reserve_state, Integer page) {
		/////////////////////// 페이징/////////////////////////////////////////
		Map<String, Object> map = new HashMap<String, Object>();

		// 리스트 수
		int total_cnt = dao.backoffice_reserve_selectAll_cnt(backoffice_no, reserve_state);

		List<ReserveListViewDTO> rvos = dao.backoffice_reserve_selectAll(backoffice_no, reserve_state,
				9 * (page - 1) + 1, 9 * (page));
		if (rvos == null) {
			map.put("cnt", 0);
		} else {
			map.put("cnt", rvos.size());
		}

		if (reserve_state.equals("all")) {
			map.put("page", "reserve_all");
		} else if (reserve_state.equals("in_use")) {
			map.put("page", "reserve_inuse");
		} else if (reserve_state.equals("end")) {
			map.put("page", "reserve_end");
		} else if (reserve_state.equals("cancel")) {
			map.put("page", "reserve_cancel");
		}
		map.put("nowCnt", 1);

		// 총 페이징되는 수
		long totalPageCnt = (long) Math.ceil(total_cnt / 9.0);

		// 현재페이지
		long nowPage = page;

		// 5page씩
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

		////////////////////////////////////////////////////////////////////

		map.put("r_vos", rvos);
		map.put("reserve_state", reserve_state);
		return map;
	}

	/**
	 * 예약 관리 - 리스트(검색)
	 */
	@Override
	public Map<String, Object> dashboard_reserve_search(String backoffice_no, String searchword, String reserve_state,
			Integer page) {
		Map<String, Object> map = new HashMap<String, Object>();

		int total_cnt = dao.backoffice_search_reserve_cnt(backoffice_no, searchword, reserve_state);

		List<ReserveListViewDTO> rvos = dao.backoffice_search_reserve(backoffice_no, searchword, reserve_state,
				9 * (page - 1) + 1, 9 * (page));
		if (rvos == null) {
			map.put("cnt", 0);
		} else {
			map.put("cnt", rvos.size());
		}

		if (reserve_state.equals("all")) {
			map.put("page", "reserve_all");
		} else if (reserve_state.equals("in_use")) {
			map.put("page", "reserve_inuse");
		} else if (reserve_state.equals("end")) {
			map.put("page", "reserve_end");
		} else if (reserve_state.equals("cancel")) {
			map.put("page", "reserve_cancel");
		}
		map.put("nowCnt", 1);

		/////////////////////////////// 페이징///////////////////////////////////////////
		// 총 페이징되는 수
		long totalPageCnt = (long) Math.ceil(total_cnt / 9.0);

		// 현재페이지
		long nowPage = page;

		// 5page씩
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

		//////////////////////////////////////////////////////////////////////

		map.put("r_vos", rvos);
		map.put("reserve_state", reserve_state);

		return map;
	}

	/**
	 * 정산 관리 - 리스트
	 */
	@Override
	public Map<String, Object> dashboard_sales_day(String backoffice_no, String sales_date, Integer page) {
		Map<String, Object> map = new HashMap<String, Object>();

		SalesSettlementDetailViewDTO svo = dao.backoffice_sales_selectOne(backoffice_no, sales_date);
		map.put("svo", svo);
		map.put("sales_date", sales_date);

		/////////////////////// 페이징/////////////////////////////////////////
		// 리스트 수
		long total_rowCount_all = dao.backoffice_sales_selectAll_cnt(backoffice_no);
		log.info("total_rowCount_reserve_now: {}", total_rowCount_all);

		// 총 페이징되는 수
		long totalPageCnt = (long) Math.ceil(total_rowCount_all / 8.0);
		log.info("totalPageCnt: {}", totalPageCnt);

		// 현재페이지
		long nowPage = page;

		// 5page씩
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
		////////////////////////////////////////////////////////////////////
		if (sales_date.equals("day")) {
			map.put("page", "sales_day");
		} else if (sales_date.equals("week")) {
			map.put("page", "sales_week");
		} else if (sales_date.equals("month")) {
			map.put("page", "sales_month");
		}

		List<SalesSettlementViewDTO> svos = dao.backoffice_sales_selectAll(backoffice_no, page);
		map.put("s_vos", svos);
		map.put("cnt", svos.size());
		log.info("svos:::{}", svos);

		return map;
	}

	/**
	 * 정산 관리 - 정산 상태 변경
	 */

	@Override
	public Map<String, String> backoffice_updateOK_sales(String backoffice_no, String room_no, String payment_no) {
		Map<String, String> map = new HashMap<String, String>();

		int result = dao.backoffice_updateOK_sales(backoffice_no, room_no, payment_no);
		log.info("result", Integer.toString(result));
		if (result == 1) {
			log.info("successed...");
			map.put("result", "1");
		}

		else {
			log.info("failed...");
			map.put("result", "0");
		}

		return map;
	}

	/**
	 * 환경설정 - 페이지 출력
	 */
	@Override
	public Map<String, Object> backoffice_settings(BackOfficeDTO bvo) {

		Map<String, Object> map = new HashMap<String, Object>();

		BackOfficeDTO bvo2 = dao.backoffice_setting_selectOne(bvo);

		OptionEngToKorMap optionEngToKorMap = new OptionEngToKorMap();

		if (bvo2.getBackoffice_tag() != null) {
			String[] backoffice_tag = bvo2.getBackoffice_tag().split(",");
			for (int i = 0; i < backoffice_tag.length; i++) {
				backoffice_tag[i] = "#" + backoffice_tag[i];
			}
			map.put("backoffice_tag", backoffice_tag);
		} else {
			map.put("backoffice_tag", "-");
		}

		if (bvo2.getBackoffice_option() != null) {
			List<String> backoffice_option = optionEngToKorMap.splitOption(bvo2.getBackoffice_option());
			map.put("backoffice_option", backoffice_option);
		} else {
			map.put("backoffice_option", "-");
		}

		if (bvo2.getBackoffice_around() != null) {
			List<String> backoffice_around = optionEngToKorMap.splitAroundOption(bvo2.getBackoffice_around());
			map.put("backoffice_around", backoffice_around);
		} else {
			map.put("backoffice_around", "-");
		}

		map.put("vo", bvo2);

		return map;
	}

	/**
	 * 환경설정 - 비밀번호 수정
	 */
	@Override
	public Map<String, String> backoffice_update_pw(BackOfficeDTO bvo) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		Map<String, String> map = new HashMap<String, String>();

		// 비밀번호 일치 여부 확인
		BackOfficeDTO bvo2 = dao.backoffice_select_pw(bvo);

		boolean result = encoder.matches(bvo.getBackoffice_pw(), bvo2.getBackoffice_pw());
		log.info("(비밀번호 확인부분)res: {}", result);

		if (result == true) {
			log.info("successed...");
			map.put("result", "1");
		} else {
			log.info("failed...");
			map.put("result", "0");
		}

		return map;
	}

	/**
	 * 환경설정 - 업체 탈퇴 요청
	 */
	@Override
	public Map<String, String> backoffice_setting_delete_rsu(BackOfficeDTO bvo) {

		Map<String, String> map = new HashMap<String, String>();

		int result = dao.backoffice_setting_delete(bvo);

		if (result == 1) {
			int flag = dao.backoffice_room_deleteALL(bvo);
			if (flag == 1) {
				dao.backoffice_qna_insert(bvo.getBackoffice_no());
				log.info("successed...");
				map.put("result", "1");
			} else {
				log.info("failed...");
				map.put("result", "0"); // 남은 예약이 있을 시
			}
		} else {
			log.info("failed...");
			map.put("result", "0"); // 남은 예약이 있을 시
		}

		return map;
	}

	/**
	 * 환경설정 - 정보 변경 (백오피스 정보 가져오기)
	 */
	@Override
	public Map<String, Object> backoffice_update_host(BackOfficeDTO bvo) {

		Map<String, Object> map = new HashMap<String, Object>();

		BackOfficeDTO bvo2 = dao.backoffice_setting_selectOne(bvo);
		log.info("result: {}.", bvo2);
		BackOfficeOperatingTimeDTO ovo = dao.backoffice_setting_selectOne_operatingtime(bvo2.getBackoffice_no());
		log.info("result: {}.", ovo);

		map.put("backoffice_tag", bvo2.getBackoffice_tag());
		map.put("vo", bvo2);
		map.put("ovo", ovo);

		return map;
	}

	/**
	 * 환경설정 - 정보 변경 (백오피스 기존 정보 가져오기-이미지 정보 비교 위함.)
	 */
	@Override
	public BackOfficeDTO backoffice_setting_selectOne(BackOfficeDTO bvo) {
		BackOfficeDTO bvo2 = dao.backoffice_setting_selectOne(bvo);
		return bvo2;
	}

	/**
	 * 환경설정 - 정보 변경 처리
	 */
	@Override
	public Map<String, String> backoffice_updateOK_host(BackOfficeDTO bvo, BackOfficeOperatingTimeDTO ovo) {

		Map<String, String> map = new HashMap<String, String>();

		// 백오피스 업체 정보 업데이트
		int update_host = dao.backoffice_updateOK_host(bvo);

		// 백오피스 운영 시간 업데이트
		ovo.setBackoffice_no(bvo.getBackoffice_no());
		int update_opt = dao.backoffice_updateOK_opt(ovo);
		log.info("update_opt:::{}", update_opt);

		if (update_opt == 1) {
			map.put("result", "1");
		} else {
			map.put("result", "0");
		}

		return map;
	}

	/**
	 * 일정 관리 - 날짜, 시간 선택 후
	 */
	@Override
	public Map<String, Object> backoffice_schedule_research(String backoffice_no, String not_sdate, String not_edate,
			String not_stime, String not_etime, String off_type, Integer page) {
		Map<String, Object> map = new HashMap<String, Object>();

		int total_cnt = dao.backoffice_room_cnt(backoffice_no, not_sdate, not_edate, not_stime, not_etime, off_type);

		map.put("maxCnt", total_cnt);

		int min = 8 * (page - 1) + 1;
		int max = 8 * (page);

		List<ScheduleListViewDTO> schedule = dao.backoffice_schedule_list(backoffice_no, not_sdate, not_edate,
				not_stime, not_etime, off_type, min, max);

		map.put("sc_vos", schedule);
		if (schedule == null) {
			map.put("cnt", 0);
		} else {
			map.put("cnt", schedule.size());
		}

		map.put("page", "schedule");
		map.put("nowCnt", 1);

		return map;
	}

	/**
	 * 일정 관리 - 날짜, 시간 선택 후 *************스크롤 페이징*****************
	 */
	@Override
	public Map<String, Object> backoffice_schedule_research_paging(String backoffice_no, String not_sdate,
			String not_edate, String not_stime, String not_etime, String off_type, Integer page) {
		Map<String, Object> map = new HashMap<String, Object>();

		int min = 8 * (page - 1) + 1;
		int max = 8 * (page);

		List<ScheduleListViewDTO> schedule = dao.backoffice_schedule_list(backoffice_no, not_sdate, not_edate,
				not_stime, not_etime, off_type, min, max);

		map.put("sc_vos", schedule);
		if (schedule == null) {
			map.put("cnt", 0);
		} else {
			map.put("cnt", schedule.size());
		}

		return map;
	}

	/**
	 * 일정 관리 - 날짜, 시간 선택 후, 휴무, 브레이크타임 설정
	 */
	@Override
	public Map<String, String> backoffice_schedule_research_paging(String backoffice_no, String not_sdate,
			String not_edate, String not_stime, String not_etime, String room_no, String off_type) {

		Map<String, String> map = new HashMap<String, String>();

		// 브레이크 타임
		if (off_type.equals("breaktime")) {
			log.info("브레이크 타임");
			not_edate = (not_sdate);
		} else if (off_type.equals("dayoff")) {
			log.info("휴무");
			not_stime = "00:00:00";
			not_etime = "23:59:59";
		}
		log.info("not_sdate : {} ", not_sdate);
		log.info("not_edate : {} ", not_edate);

		String not_s = not_sdate + not_stime;
		String not_e = not_edate + not_etime;
		log.info("not_stime : {} ", not_stime);
		log.info("not_etime : {} ", not_etime);

		int result = dao.backoffice_schedueOK(backoffice_no, not_s, not_e, room_no);

		if (result == 1) {
			log.info("successed...");
			map.put("result", "1");
		} else {
			log.info("falied...");
			map.put("result", "0");
		}

		return map;
	}

	/**
	 * 일정 관리 - 해당 날짜, 시간에 예약자 리스트
	 */
	@Override
	public Map<String, Object> backoffice_reservation(String backoffice_no, String room_no, String not_sdate,
			String not_edate, String not_stime, String not_etime, String off_type, Integer page) {
		Map<String, Object> map = new HashMap<String, Object>();

		int total_cnt = dao.backoffice_reservation_cnt(backoffice_no, not_sdate, not_edate, not_stime, not_etime,
				room_no, off_type);

		if (off_type.equals("breaktime")) {
			log.info("브레이크 타임");
			not_edate = (not_sdate);
		} else if (off_type.equals("dayoff")) {
			log.info("휴무");
			not_stime = "00:00:00";
			not_etime = "23:59:59";
		}

		String reserve_stime = (not_sdate + " " + not_stime);
		log.info("reserve_stime : {} ", reserve_stime);

		String reserve_etime = (not_edate + " " + not_etime);
		log.info("reserve_etime : {} ", reserve_etime);

		List<ReservationViewDTO> rv_vos = dao.backoffice_reservation(backoffice_no, not_sdate, not_edate, not_stime,
				not_etime, room_no, off_type, 8 * (page - 1) + 1, 8 * (page));
		log.info("result: {}.", rv_vos);
		log.info("cnt: {}.", rv_vos.size());

		if (rv_vos == null)
			map.put("cnt", 0);
		else
			map.put("cnt", rv_vos.size());

		map.put("page", "reservation");
		map.put("nowCnt", 1);
		map.put("maxCnt", total_cnt);

		map.put("reserve_stime", reserve_stime);
		map.put("reserve_etime", reserve_etime);
		map.put("rv_vos", rv_vos);

		return map;
	}

	/**
	 * 일정 관리 - 해당 날짜, 시간에 예약자 리스트 *************스크롤 페이징*****************
	 */
	@Override
	public Map<String, Object> backoffice_reservation_paging(String backoffice_no, String room_no, String not_sdate,
			String not_edate, String not_stime, String not_etime, String off_type, Integer page) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (off_type.equals("breaktime")) {
			log.info("브레이크 타임");
			not_edate = (not_sdate);
		} else if (off_type.equals("dayoff")) {
			log.info("휴무");
			not_stime = "00:00:00";
			not_etime = "23:59:59";
		}

		String reserve_stime = (not_sdate + not_stime);
		log.info("reserve_stime : {} ", reserve_stime);

		String reserve_etime = (not_edate + not_etime);
		log.info("reserve_etime : {} ", reserve_etime);

		List<ReservationViewDTO> rv_vos = dao.backoffice_reservation(backoffice_no, not_sdate, not_edate, not_stime,
				not_etime, room_no, off_type, 8 * (page - 1) + 1, 8 * (page));
		log.info("result: {}.", rv_vos);
		log.info("cnt: {}.", rv_vos.size());

		if (rv_vos == null) {
			map.put("cnt", 0);
		} else {
			map.put("cnt", rv_vos.size());
		}

		map.put("nowCnt", 1);
		map.put("r_vos", rv_vos);
		map.put("reserve_stime", reserve_stime);
		map.put("reserve_etime", reserve_etime);

		return map;
	}

	/**
	 * 일정 관리 - 백오피스 휴무 일정
	 */
	@Override
	public Map<String, Object> backoffice_schedule_calendar(String backoffice_no) {
		Map<String, Object> map = new HashMap<String, Object>();

		List<ScheduleDTO> vos = dao.backoffice_schedule_calendar(backoffice_no);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sdate = "";
		String edate = "";
		for (ScheduleDTO vo : vos) {
			sdate = sdf.format(vo.getNot_stime());
			edate = sdf.format(vo.getNot_etime());
			String[] st = sdate.split(" ");
			String[] et = edate.split(" ");
			if (st[0].equals(et[0])) {
				// 브레이크 타임
				vo.setSdate(st[0]);
				vo.setStime(st[1]);
				vo.setEtime(et[1]);
				vo.setSchedule_type("breaktime");
			} else {
				// 휴무
				vo.setSdate(st[0]);
				vo.setEdate(et[0]);
				vo.setSchedule_type("dayoff");
			}
			RoomDTO rvo = dao.backoffice_schedule_calendar_room_name(vo.getRoom_no());
			vo.setRoom_name(rvo.getRoom_name());
		}
		// 현재 달(월)
		LocalDateTime now = LocalDateTime.now();
		int month = now.getMonthValue();

		map.put("vos", vos);
		map.put("month", month);
		map.put("cnt", vos.size());

		return map;
	}

	/**
	 * 일정 관리 - 휴무/브레이크타임 취소
	 */
	@Override
	public Map<String, Object> backoffice_schedule_cancel(String backoffice_no, String schedule_no) {

		Map<String, Object> map = new HashMap<String, Object>();

		int flag = dao.backoffice_schedule_cancel(backoffice_no, schedule_no);

		List<ScheduleDTO> vos = dao.backoffice_schedule_calendar(backoffice_no);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sdate = "";
		String edate = "";
		for (ScheduleDTO vo : vos) {
			sdate = sdf.format(vo.getNot_stime());
			edate = sdf.format(vo.getNot_etime());
			String[] st = sdate.split(" ");
			String[] et = edate.split(" ");
			if (st[0].equals(et[0])) {
				// 브레이크 타임
				vo.setSdate(st[0]);
				vo.setStime(st[1]);
				vo.setEtime(et[1]);
				vo.setSchedule_type("breaktime");
			} else {
				// 휴무
				vo.setSdate(st[0]);
				vo.setEdate(et[0]);
				vo.setSchedule_type("dayoff");
			}
			RoomDTO rvo = dao.backoffice_schedule_calendar_room_name(vo.getRoom_no());
			vo.setRoom_name(rvo.getRoom_name());
		}

		if (flag == 1) {
			log.info("successed...");
			log.info("vos...{}", vos);
			map.put("result", "1");
			map.put("vos", vos);
			map.put("cnt", vos.size());
		} else {
			log.info("falied...");
			map.put("result", "0");
		}

		return map;
	}

	/**
	 * 
	 * AOP ... 예약 상태 업데이트
	 * 
	 */
	@Override
	public void reserve_state_auto_update() {
		dao.reserve_state_auto_update();
	}

	/**
	 * 
	 * AOP ... 예약 상태 false 삭제 - reserve_no
	 * 
	 */
	@Override
	public ReserveUpdateDTO select_one_false_reserve(String reserve_stime, String reserve_etime, String room_no) {
		return dao.select_one_false_reserve(reserve_stime, reserve_etime, room_no);
	}

	/**
	 * 
	 * AOP ... 예약 상태 false 삭제
	 * 
	 */
	@Override
	public void reserve_auto_delete(String reserve_no) {
		dao.reserve_auto_delete(reserve_no);
	}

}
