/**
 * 
 * @author 최진실
 *
 */
package com.rence.dashboard.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rence.backoffice.common.OperatingTime;
import com.rence.backoffice.model.BackOfficeDTO;
import com.rence.backoffice.model.BackOfficeEntity;
import com.rence.backoffice.model.BackOfficeOperatingTimeDTO;
import com.rence.backoffice.model.BackOfficeOperatingTimeEntity;
import com.rence.backoffice.repository.BackOfficeOperatingTimeRepository;
import com.rence.backoffice.repository.BackOfficeOperatingTimeSelectRepository;
import com.rence.backoffice.repository.BackOfficeRepository;
import com.rence.dashboard.dao.DashboardDAO;
import com.rence.dashboard.model.BOMileageEntity;
import com.rence.dashboard.model.BOPaymentDTO;
import com.rence.dashboard.model.BOPaymentEntity;
import com.rence.dashboard.model.CommentDTO;
import com.rence.dashboard.model.CommentEntity;
import com.rence.dashboard.model.CommentListAViewEntity;
import com.rence.dashboard.model.CommentListQViewDTO;
import com.rence.dashboard.model.CommentListQViewEntity;
import com.rence.dashboard.model.CommentSummaryViewDTO;
import com.rence.dashboard.model.CommentSummaryViewEntity;
import com.rence.dashboard.model.ReservationViewDTO;
import com.rence.dashboard.model.ReservationViewEntity;
import com.rence.dashboard.model.ReserveListViewDTO;
import com.rence.dashboard.model.ReserveListViewEntity;
import com.rence.dashboard.model.ReserveSummaryViewDTO;
import com.rence.dashboard.model.ReserveSummaryViewEntity;
import com.rence.dashboard.model.ReserveUpdateDTO;
import com.rence.dashboard.model.ReserveUpdateEntity;
import com.rence.dashboard.model.ReviewListViewDTO;
import com.rence.dashboard.model.ReviewListViewEntity;
import com.rence.dashboard.model.RoomDTO;
import com.rence.dashboard.model.RoomEntity;
import com.rence.dashboard.model.RoomSummaryViewDTO;
import com.rence.dashboard.model.RoomSummaryViewEntity;
import com.rence.dashboard.model.SalesSettlementDetailViewDTO;
import com.rence.dashboard.model.SalesSettlementDetailViewEntity;
import com.rence.dashboard.model.SalesSettlementSummaryViewDTO;
import com.rence.dashboard.model.SalesSettlementSummaryViewEntity;
import com.rence.dashboard.model.SalesSettlementViewDTO;
import com.rence.dashboard.model.SalesSettlementViewEntity;
import com.rence.dashboard.model.ScheduleDTO;
import com.rence.dashboard.model.ScheduleEntity;
import com.rence.dashboard.model.ScheduleListViewDTO;
import com.rence.dashboard.model.ScheduleListViewEntity;
import com.rence.dashboard.repository.CommentAListRepository;
import com.rence.dashboard.repository.CommentQListRepository;
import com.rence.dashboard.repository.CommentRepository;
import com.rence.dashboard.repository.CommentSummaryRepository;
import com.rence.dashboard.repository.PaymentCancelRepository;
import com.rence.dashboard.repository.ReservationRepository;
import com.rence.dashboard.repository.ReserveAutoUpdateRepository;
import com.rence.dashboard.repository.ReserveRepository;
import com.rence.dashboard.repository.ReserveSummaryRepository;
import com.rence.dashboard.repository.ReviewRepository;
import com.rence.dashboard.repository.RoomRepository;
import com.rence.dashboard.repository.RoomSummaryRepository;
import com.rence.dashboard.repository.SalesMileageRepository;
import com.rence.dashboard.repository.SalesSettlementDetailRepository;
import com.rence.dashboard.repository.SalesSettlementRepository;
import com.rence.dashboard.repository.SalesSettlementSummaryRepository;
import com.rence.dashboard.repository.ScheduleListRepository;
import com.rence.dashboard.repository.ScheduleRepository;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class DashboardDAOImpl implements DashboardDAO {

	@Autowired
	ReserveRepository rv_repository;

	@Autowired
	ReserveSummaryRepository r_summary_repository;

	@Autowired
	RoomRepository rm_repository;

	@Autowired
	BackOfficeRepository b_repository;

	@Autowired
	CommentRepository c_repository;

	@Autowired
	CommentSummaryRepository c_summary_repository;

	@Autowired
	ReviewRepository r_repository;

	@Autowired
	SalesSettlementRepository s_repository;

	@Autowired
	BackOfficeOperatingTimeRepository o_repository;

	@Autowired
	CommentQListRepository cq_repository;

	@Autowired
	BackOfficeOperatingTimeSelectRepository bos_repository;

	@Autowired
	ReserveAutoUpdateRepository reserveAutoUpdateRepository;

	@Autowired
	CommentAListRepository ca_repository;

	@Autowired
	RoomSummaryRepository rm_summary_repository;

	@Autowired
	SalesSettlementSummaryRepository ss_summary_repository;

	@Autowired
	SalesSettlementDetailRepository s_detail_repository;

	@Autowired
	ScheduleListRepository sc_repository;

	@Autowired
	ReservationRepository reservation_repository;

	@Autowired
	ScheduleRepository schedule_repository;

	@Autowired
	PaymentCancelRepository p_repository;

	@Autowired
	SalesMileageRepository m_repository;

	@Autowired
	OperatingTime operatingTime;

	@Autowired
	ModelMapper modelMapper;

	/**
	 * 대쉬보드 메인 - 예약 요약
	 */
	@Override
	public List<ReserveSummaryViewDTO> reserve_summary_selectAll(String backoffice_no) {
		
		List<ReserveSummaryViewEntity> rse = r_summary_repository.reserve_summary_selectAll(backoffice_no);

		List<ReserveSummaryViewDTO> rses = rse.stream().map(rvo -> modelMapper.map(rvo, ReserveSummaryViewDTO.class)).collect(Collectors.toList());

		return rses;
	}

	/**
	 * 대쉬보드 메인 - 문의 요약
	 */
	@Override
	public List<CommentSummaryViewDTO> comment_summary_selectAll(String backoffice_no) {

		List<CommentSummaryViewEntity> cse = c_summary_repository.comment_summary_selectAll(backoffice_no);
		
		List<CommentSummaryViewDTO> cses = cse.stream().map(cvo -> modelMapper.map(cvo, CommentSummaryViewDTO.class)).collect(Collectors.toList());

		return cses;
	}

	/**
	 * 대쉬보드 메인 - 정산 요약
	 */
	@Override
	public SalesSettlementSummaryViewDTO payment_summary_selectOne(String backoffice_no) {

		SalesSettlementSummaryViewDTO ss = new SalesSettlementSummaryViewDTO();
		SalesSettlementSummaryViewEntity ss_entity = new SalesSettlementSummaryViewEntity();
		
		ss_entity = ss_summary_repository.select_pay_day_sum(backoffice_no);

		ss = modelMapper.map(ss_entity, SalesSettlementSummaryViewDTO.class);
		
		ss.setSales_income(String.valueOf(ss_entity.getSales_total() - ss_entity.getSales_cancel()));
		
		return ss;
	}

	/**
	 * 대쉬보드 메인 - 공간 요약
	 */
	@Override
	public RoomSummaryViewDTO room_summary_selectOne(String backoffice_no) {

		RoomSummaryViewEntity rse = rm_summary_repository.select_room_summary(backoffice_no);
		RoomSummaryViewDTO rs = modelMapper.map(rse, RoomSummaryViewDTO.class);
		
		rs.setReview_point(String.format("%.1f", rse.getReview_point()));
		
		return rs;
	}

	/**
	 * 공간관리 - 공간 리스트
	 */
	@Override
	public List<RoomDTO> dashboard_room_list(String backoffice_no, Integer page) {

		Integer row_count = 12;
		Integer start_row = (page - 1) * row_count + 1;
		Integer end_row = page * row_count;

		List<RoomEntity> re = rm_repository.selectAll_room_list(backoffice_no, start_row, end_row);
		
		List<RoomDTO> rs = re.stream().map(rvo -> modelMapper.map(rvo, RoomDTO.class)).collect(Collectors.toList());

		return rs;

	}

	/**
	 * 공간관리 - 공간 추가, 수정 팝업(백오피스 정보(타입) 가져오기)
	 */
	@Override
	public BackOfficeDTO select_one_backoffice_info(String backoffice_no) {

		BackOfficeDTO bvo = new BackOfficeDTO();
		
		BackOfficeEntity be = b_repository.select_one_backoffice_info(backoffice_no);
		if (be!=null) {
			bvo = modelMapper.map(be, BackOfficeDTO.class);
		}

		return bvo;
	}

	/**
	 * 공간관리 - 공간 추가 처리
	 */
	@Override
	public int backoffice_insertOK_room(String backoffice_no, RoomDTO rvo) {
		
		RoomEntity re = modelMapper.map(rvo, RoomEntity.class);

		if (rvo.getRoom_type().equals("desk")) {
			re.setRoom_price(10000);
		} else if (rvo.getRoom_type().equals("meeting_04")) {
			re.setRoom_price(20000);
		} else if (rvo.getRoom_type().equals("meeting_06")) {
			re.setRoom_price(30000);
		} else if (rvo.getRoom_type().equals("meeting_10")) {
			re.setRoom_price(50000);
		}
		
		re.setBackoffice_no(backoffice_no);
		re.setRoom_state("T");

		return rm_repository.backoffice_insertOK_room(re);
	}

	/**
	 * 공간관리 - 공간 수정 팝업(공간 정보 가져오기)
	 */
	@Override
	public RoomDTO select_one_room_info(String backoffice_no, String room_no) {

		RoomEntity re = rm_repository.select_one_room_info(backoffice_no, room_no);
		RoomDTO rvo = modelMapper.map(re, RoomDTO.class);

		return rvo;
	}

	/**
	 * 공간관리 - 공간 수정 처리
	 */
	@Override
	public int backoffice_updateOK_room(String backoffice_no, RoomDTO rvo) {
		
		RoomEntity re = modelMapper.map(rvo, RoomEntity.class);

		if (rvo.getRoom_type().equals("desk")) {
			re.setRoom_price(10000);
		} else if (rvo.getRoom_type().equals("meeting_04")) {
			re.setRoom_price(20000);
		} else if (rvo.getRoom_type().equals("meeting_06")) {
			re.setRoom_price(30000);
		} else if (rvo.getRoom_type().equals("meeting_10")) {
			re.setRoom_price(50000);
		}
		re.setBackoffice_no(backoffice_no);
		
		return rm_repository.backoffice_updateOK_room(re);
	}

	/**
	 * 공간관리 - 공간 삭제
	 */
	@Override
	public void backoffice_deleteOK_room(String backoffice_no, String room_no) {
		rm_repository.backoffice_deleteOK_room(backoffice_no, room_no);
	}
	
	/**
	 * 공간관리 - 공간 삭제 후, 문의 처리
	 */
	@Override
	public void backoffice_qna_insert(String backoffice_no, String room_no) {
		
		List<String> q_no_list = c_repository.select_qna_f(room_no);
		if (q_no_list!=null) {
			for (String q : q_no_list) {
				int flag = c_repository.backoffice_qna_insert(backoffice_no,room_no,q);
				if (flag==1) {
					c_repository.update_comment_state_T(backoffice_no, q);
				}
			}
		} 
	}

	/**
	 * 공간관리 -문의(리스트)
	 */
	@Override
	public List<CommentListQViewDTO> backoffice_qna_selectAll(String backoffice_no, Integer page) {

		Integer row_count = 10;
		Integer start_row = (page - 1) * row_count + 1;
		Integer end_row = page * row_count;

		List<CommentListQViewEntity> qes = cq_repository.select_all_q(backoffice_no, start_row, end_row);
		List<CommentListQViewDTO> qvos = qes.stream().map(qvo -> modelMapper.map(qvo, CommentListQViewDTO.class)).collect(Collectors.toList());
		
		if (qvos != null) {
			for (int i = 0; i < qvos.size(); i++) {
				List<CommentListAViewEntity> avos = ca_repository.select_all_a(backoffice_no, qvos.get(i).getComment_no());

				if (avos != null) {
					for (int j = 0; j < avos.size(); j++) {

						qvos.get(i).setAnswer_no(avos.get(j).getComment_no());
						qvos.get(i).setAnswer_content(avos.get(j).getComment_content());
						qvos.get(i).setAnswer_date(avos.get(j).getComment_date());
					}
				}
			}
		}

		return qvos;

	}

	/**
	 * 공간관리 - 답변 작성 팝업
	 */
	@Override
	public CommentDTO backoffice_insert_comment(String backoffice_no, String room_no, String comment_no) {
		
		CommentEntity ce = c_repository.backoffice_insert_comment(backoffice_no, room_no, comment_no);
		CommentDTO cvo = modelMapper.map(ce, CommentDTO.class);
		
		return cvo;
	}

	/**
	 * 공간관리 - 답변 작성
	 */
	@Override
	public int backoffice_insertOK_comment(CommentDTO cvo, String backoffice_no, String comment_no) {
		
		CommentEntity ce = modelMapper.map(cvo, CommentEntity.class);
		
		ce.setMother_no(comment_no);
		ce.setBackoffice_no(backoffice_no);
		ce.setHost_no(backoffice_no);
		ce.setWriter("관리자");
		ce.setComment_state("T");
		ce.setComment_date(new Date());
		
		return c_repository.backoffice_insertOK_comment(ce);
	}

	/**
	 * 공간관리 - 답변 작성 후, 해당 문의 답변 상태 변경
	 */
	@Override
	public int update_comment_state_T(String backoffice_no, String comment_no) {
		return c_repository.update_comment_state_T(backoffice_no, comment_no);
	}

	/**
	 * 공간관리 - 답변 삭제
	 */
	@Override
	public int backoffice_deleteOK_comment(String backoffice_no, String comment_no) {
		return c_repository.backoffice_deleteOK_comment(backoffice_no, comment_no);
	}

	/**
	 * 공간관리 - 답변 삭제 후, 해당 문의 답변 상태 변경
	 */
	@Override
	public int update_comment_state_F(String backoffice_no, String mother_no) {
		return c_repository.update_comment_state_F(backoffice_no, mother_no);
	}

	/**
	 * 공간관리 - 리뷰 (리스트)
	 */
	@Override
	public List<ReviewListViewDTO> backoffice_review_selectAll(String backoffice_no, Integer page) {

		Integer row_count = 6;
		Integer start_row = (page - 1) * row_count + 1;
		Integer end_row = page * row_count;
		
		List<ReviewListViewEntity> rve = r_repository.backoffice_review_selectAll(backoffice_no, start_row, end_row);
		List<ReviewListViewDTO> rves = rve.stream().map(rvo -> modelMapper.map(rvo, ReviewListViewDTO.class)).collect(Collectors.toList());

		return rves;
	}

	/**
	 * 예약 관리 - 리스트
	 */
	@Override
	public List<ReserveListViewDTO> backoffice_reserve_selectAll(String backoffice_no, String reserve_state, int start_row,
			int end_row) {

		List<ReserveListViewEntity> reserve = new ArrayList<ReserveListViewEntity>();
		if (reserve_state.equals("all")) {
			reserve = rv_repository.backoffice_reserve_selectAll(backoffice_no, start_row, end_row);
		} else if (reserve_state.equals("in_use")) {
			reserve = rv_repository.backoffice_reserve_selectAll_inuse(backoffice_no, start_row, end_row);
		} else if (reserve_state.equals("end")) {
			reserve = rv_repository.backoffice_reserve_selectAll_end(backoffice_no, start_row, end_row);
		} else if (reserve_state.equals("cancel")) {
			reserve = rv_repository.backoffice_reserve_selectAll_cancel(backoffice_no, start_row, end_row);
		}
		
		List<ReserveListViewDTO> rvos = reserve.stream().map(rvo -> modelMapper.map(rvo, ReserveListViewDTO.class)).collect(Collectors.toList());

		return rvos;
	}

	/**
	 * 예약 관리 - 리스트(검색)
	 */
	@Override
	public List<ReserveListViewDTO> backoffice_search_reserve(String backoffice_no, String searchword,
			String reserve_state, int start_row, int end_row) {

		List<ReserveListViewEntity> reserve = new ArrayList<ReserveListViewEntity>();
		
		if (reserve_state.equals("all")) {
			reserve = rv_repository.backoffice_reserve_selectAll_search(backoffice_no, start_row, end_row,
					"%" + searchword + "%");
			log.info("search list :: {}", reserve);
		} else if (reserve_state.equals("in_use")) {
			reserve = rv_repository.backoffice_reserve_selectAll_inuse_search(backoffice_no, start_row, end_row,
					"%" + searchword + "%");
		} else if (reserve_state.equals("end")) {
			reserve = rv_repository.backoffice_reserve_selectAll_end_search(backoffice_no, start_row, end_row,
					"%" + searchword + "%");
		} else if (reserve_state.equals("cancel")) {
			reserve = rv_repository.backoffice_reserve_selectAll_cancel_search(backoffice_no, start_row, end_row,
					"%" + searchword + "%");
		}
		
		List<ReserveListViewDTO> rvos = reserve.stream().map(rvo -> modelMapper.map(rvo, ReserveListViewDTO.class)).collect(Collectors.toList());

		return rvos;
	}

	/**
	 * 정산 관리 - 리스트(일일/주간/월간)
	 */
	@Override
	public SalesSettlementDetailViewDTO backoffice_sales_selectOne(String backoffice_no, String sales_date) {
		
		SalesSettlementDetailViewDTO ss = new SalesSettlementDetailViewDTO();

		SalesSettlementDetailViewEntity ss_entity = new SalesSettlementDetailViewEntity();
		
		if (sales_date.equals("day")) {
			ss_entity = s_detail_repository.select_pay_day_sum(backoffice_no);
		} else if (sales_date.equals("week")) {
			ss_entity = s_detail_repository.select_pay_week_sum(backoffice_no);
		} else if (sales_date.equals("month")) {
			ss_entity = s_detail_repository.select_pay_month_sum(backoffice_no);
		}
		
		ss = modelMapper.map(ss_entity, SalesSettlementDetailViewDTO.class);
		
		// 현재(금일, 금주, 당월) 순수익
		ss.setSales_income(String.valueOf(ss_entity.getSales_total() - ss_entity.getSales_cancel()));
		
		// 이전(전일, 전주, 전월) 순수익
		ss.setPre_sales_income(String.valueOf(ss_entity.getPre_sales_total() - ss_entity.getPre_sales_cancel()));
		
		// 순수익 차액
		ss.setSales_gap(String.valueOf((ss_entity.getSales_total() - ss_entity.getSales_cancel()) - (ss_entity.getPre_sales_total() - ss_entity.getPre_sales_cancel())));
		
		return ss;
	}

	/**
	 * 정산 관리 - 리스트(정산 내역)
	 */
	@Override
	public List<SalesSettlementViewDTO> backoffice_sales_selectAll(String backoffice_no, Integer page) {

		Integer row_count = 8;
		Integer start_row = (page - 1) * row_count + 1;
		Integer end_row = page * row_count;
		
		List<SalesSettlementViewEntity> se = s_repository.backoffice_sales_selectAll(backoffice_no,start_row, end_row);
		
		List<SalesSettlementViewDTO> svos = se.stream().map(svo -> modelMapper.map(svo, SalesSettlementViewDTO.class)).collect(Collectors.toList());
		
		return svos;
	}

	/**
	 * 정산 관리 - 정산 상태 변경
	 */
	@Override
	public int backoffice_updateOK_sales(String backoffice_no, String room_no, String payment_no) {
		int flag = 0;
		BOPaymentEntity pvo = new BOPaymentEntity();

		pvo = p_repository.select_paymentinfo_user_no(payment_no); // 결제정보 테이블에서 user_no 정보 얻기
		String user_no = pvo.getUser_no();

		BOMileageEntity mvo = m_repository.backoffice_select_mileage_total(user_no); // 1. 사용자의 마지막 mileage_total
		BOMileageEntity mvo2 = m_repository.backoffice_select_mileage_w(user_no, payment_no); // 2. 적립 예정 마일리지
		
		if (mvo2!=null) {
			if (mvo2.getMileage_change() != 0) { // 선결제
				int mileage_total = 0;
				int mileage_change = mvo2.getMileage_change(); // 2
				if (mvo!=null) {
					mileage_total = mvo.getMileage_total() + mileage_change; // 1+2
				}else {
					mileage_total = mileage_change;
				}
				
				flag = m_repository.backoffice_insert_mileage_state_t(mileage_total, user_no, mileage_change, payment_no); // 마일리지 적립
				
			} else { // 후결제
				flag = s_repository.backoffice_update_mileage_state_c(payment_no); // change 가 0인 mileage 는 C로 상태 변경
			}
		}else {
			flag = 1;
		}
		if (flag == 1) {
			s_repository.backoffice_updateOK_sales_state_t(backoffice_no, room_no, payment_no); // 결제 정보 테이블의 정산 상태 변경
		}
		return flag;
	}

	/**
	 * 환경설정 - 환경 설정 페이지, 정보 수정 페이지
	 */
	@Override
	public BackOfficeDTO backoffice_setting_selectOne(BackOfficeDTO bvo) {
		
		BackOfficeEntity be = b_repository.backoffice_setting_selectOne(bvo.getBackoffice_no());
		bvo = modelMapper.map(be, BackOfficeDTO.class);
		
		return bvo;
	}

	/**
	 * 환경설정 - 비밀번호 수정
	 */
	@Override
	public BackOfficeDTO backoffice_select_pw(BackOfficeDTO bvo) {
		
		BackOfficeEntity be = b_repository.backoffice_select_pw(bvo.getBackoffice_no());
		bvo = modelMapper.map(be, BackOfficeDTO.class);
		
		return bvo;
	}

	/**
	 * 환경설정 - 업체 탈퇴 요청 (백오피스 삭제)
	 */
	@Override
	public int backoffice_setting_delete(BackOfficeDTO bvo) {
		return b_repository.update_backoffice_state_o(bvo.getBackoffice_no());
	}

	/**
	 * 환경설정 - 업체 탈퇴 요청 (공간 삭제)
	 */
	@Override
	public int backoffice_room_deleteALL(BackOfficeDTO bvo) {
		return b_repository.backoffice_room_deleteALL(bvo.getBackoffice_no());
	}
	
	/**
	 * 환경설정 - 업체 탈퇴 요청 (공간 삭제)
	 */
	@Override
	public void backoffice_qna_insert(String backoffice_no) {
		List<CommentEntity> q_no_list = c_repository.select_qna_f_all(backoffice_no);
		if (q_no_list!=null) {
			for (CommentEntity q : q_no_list) {
				int flag = c_repository.backoffice_qna_insert(backoffice_no,q.getRoom_no(),q.getComment_no());
				if (flag==1) {
					c_repository.update_comment_state_T(backoffice_no, q.getComment_no());
				}
			}
		} 
	}

	/**
	 * 환경설정 - 정보 변경 (운영시간 정보 가져오기)
	 */
	@Override
	public BackOfficeOperatingTimeDTO backoffice_setting_selectOne_operatingtime(String backoffice_no) {

		BackOfficeOperatingTimeEntity ovo = bos_repository.backoffice_setting_selectOne_operatingtime(backoffice_no);
		BackOfficeOperatingTimeDTO ovo2 = operatingTime.operatingTimeEtoD(ovo);

		return ovo2;
	}

	/**
	 * 환경설정 - 정보 변경 처리 (업체 정보 업데이트)
	 */
	@Override
	public int backoffice_updateOK_host(BackOfficeDTO bvo) {
		
		BackOfficeEntity be = modelMapper.map(bvo, BackOfficeEntity.class);
		
		return b_repository.backoffice_updateOK_host(be);
	}

	/**
	 * 환경설정 - 정보 변경 처리 (운영시간 업데이트)
	 */
	@Override
	public int backoffice_updateOK_opt(BackOfficeOperatingTimeDTO ovo) {
		
		BackOfficeOperatingTimeEntity ovo2 = operatingTime.operatingTime(ovo);
		
		return o_repository.backoffice_updateOK_opt(ovo2);
	}

	/**
	 * 일정 관리 - 날짜, 시간 선택 후
	 */
	@Override
	public List<ScheduleListViewDTO> backoffice_schedule_list(String backoffice_no, String not_sdate, String not_edate, String not_stime, String not_etime, String off_type, int min, int max) {
		
		log.info("backoffice_no::{}",backoffice_no);
		log.info("not_sdate::{}",not_sdate);
		log.info("not_edate::{}",not_edate);
		log.info("not_stime::{}",not_stime);
		log.info("not_etime::{}",not_etime);
		log.info("off_type::{}",off_type);
		log.info("min::{}",min);
		log.info("max::{}",max);
		
		List<ScheduleListViewDTO> sc_vos = new ArrayList<ScheduleListViewDTO>();

		// 1, 2 날짜 형태 변환
		String reserve_stime = null;
		String reserve_etime = null;
		if (off_type.equals("dayoff")) { // 휴무일 때
			log.info("휴무");

			not_stime = "00:00:00";
			not_etime = "23:59:59";

			reserve_stime = (not_sdate + not_stime);
			log.info("reserve_stime : {} ", reserve_stime);
			reserve_etime = (not_edate + not_etime);
			log.info("reserve_etime : {} ", reserve_etime);
		} else { // 브레이크 타임일 때
			log.info("브레이크타임");

			log.info("not_sdate : {} ", not_sdate);
			reserve_stime = (not_sdate + not_stime);
			log.info("reserve_stime : {} ", reserve_stime);

			not_edate = (not_sdate);
			log.info("not_edate : {} ", not_edate);
			reserve_etime = (not_edate + not_etime);
			log.info("reserve_etime : {} ", reserve_etime);
		}

		List<ScheduleListViewEntity> sc_vos_o_entity = sc_repository.backoffice_schedule_list(backoffice_no, reserve_stime, reserve_etime, min, max);
		sc_vos =  sc_vos_o_entity.stream().map(svo -> modelMapper.map(svo, ScheduleListViewDTO.class)).collect(Collectors.toList());

		for (ScheduleListViewDTO scvo : sc_vos) {
			if (scvo.getReserve_cnt()>0) {
				scvo.setReserve_is("O");
			}else {
				scvo.setReserve_is("X");
			}
		}

		log.info("sc_vos::{}",sc_vos);
		log.info("sc_vos : {} ", sc_vos.size());

		return sc_vos;
	}

	/**
	 * 일정 관리 - 날짜, 시간 선택 후, 휴무, 브레이크타임 설정
	 */
	@Override
	public int backoffice_schedueOK(String backoffice_no, String not_stime, String not_etime, String room_no) {
		return schedule_repository.backoffice_schedueOK(backoffice_no, not_stime, not_etime, room_no);
	}

	/**
	 * 일정 관리 - 해당 날짜, 시간에 예약자 리스트
	 */
	@Override
	public List<ReservationViewDTO> backoffice_reservation(String backoffice_no, String not_sdate, String not_edate, String not_stime, String not_etime, String room_no, String off_type, int min, int max) {

		String reserve_stime = null;
		String reserve_etime = null;
		
		if (off_type.equals("dayoff")) { // 휴무일 때
			log.info("휴무");

			not_stime = "00:00:00";
			not_etime = "23:59:59";

			reserve_stime = (not_sdate + not_stime);
			log.info("reserve_stime : {} ", reserve_stime);
			reserve_etime = (not_edate + not_etime);
			log.info("reserve_etime : {} ", reserve_etime);
		} else { // 브레이크 타임일 때
			log.info("브레이크 타임");

			log.info("not_sdate : {} ", not_sdate);
			reserve_stime = (not_sdate + not_stime);
			log.info("reserve_stime : {} ", reserve_stime);

			not_edate = (not_sdate);
			log.info("not_edate : {} ", not_edate);
			reserve_etime = (not_edate + not_etime);
			log.info("reserve_etime : {} ", reserve_etime);
		}

		List<ReservationViewEntity> rv_vos_entity = reservation_repository.backoffice_reservation_list(backoffice_no, reserve_stime, reserve_etime, room_no, min, max);
		List<ReservationViewDTO> rv_vos = rv_vos_entity.stream().map(rvo -> modelMapper.map(rvo, ReservationViewDTO.class)).collect(Collectors.toList());

		return rv_vos;
	}

	/**
	 * 일정 관리 - 예약취소
	 * 
	 */
	@Override
	public BOPaymentDTO backoffice_reservation_cancel(String backoffice_no, String reserve_no, String user_no) {
		
		int flag = reserveAutoUpdateRepository.update_reserve_state_cancel(reserve_no);
		BOPaymentDTO pvo = new BOPaymentDTO();
		
		// 결제 취소
		if (flag == 1) {
			// 결제정보 테이블의 상태 'C' 로 변경
			p_repository.backoffice_update_payment_state_host_cancel(reserve_no); // 환불 상태 'C', 환불 금액 = 실제 결제 금액, 결제일시 = 환불일시
			BOPaymentEntity pve = p_repository.select_paymentinfo(reserve_no); // 결제 정보
			pvo = modelMapper.map(pve, BOPaymentDTO.class);
			String payment_no = pvo.getPayment_no();

			BOMileageEntity mvo = m_repository.backoffice_select_mileage_total(user_no); // 1. 사용자의 마지막 mileage_total
			BOMileageEntity mvo2 = m_repository.backoffice_select_mileage_f(user_no, payment_no); // 2. 사용자가 사용한 마일리지

			if (mvo2 != null) { // 사용한 마일리지가 있으면
				int mileage_change = mvo2.getMileage_change(); // 2
				int mileage_total = mvo.getMileage_total() + mileage_change; // 1 + 2

				m_repository.backoffice_insert_mileage_state_t(mileage_total, user_no, mileage_change, payment_no); // 마일리지
			}
			s_repository.backoffice_update_cancel_mileage_state_c(reserve_no); // w 상태의 마일리지 -> c 상태로 변경
		}
		return pvo;
	}

	/**
	 * 일정 관리 - 예약취소 (회사명 가져오기)
	 * 
	 */
	@Override
	public BackOfficeDTO backoffice_select_companyname(String backoffice_no) {

		BackOfficeEntity be = b_repository.select_one_backoffice_info(backoffice_no);
		
		BackOfficeDTO bvo = modelMapper.map(be, BackOfficeDTO.class);

		return bvo;

	}

	/**
	 * 일정 관리 - 백오피스 휴무 일정
	 */
	@Override
	public List<ScheduleDTO> backoffice_schedule_calendar(String backoffice_no) {
		
		log.info("backoffice_no : {}",backoffice_no);
		
		List<ScheduleEntity> se = schedule_repository.backoffice_schedule_calendar(backoffice_no);
		log.info("se:{}",se);
		
		List<ScheduleDTO> svos = se.stream().map(svo -> modelMapper.map(svo, ScheduleDTO.class)).collect(Collectors.toList());
		
		log.info("svos:{}",svos);
		
		return svos;
	}

	/**
	 * 일정 관리 - 백오피스 휴무 일정
	 */
	@Override
	public RoomDTO backoffice_schedule_calendar_room_name(String room_no) { // room_price 변환 되는 지 호가인 필요
		
		RoomEntity re = rm_repository.backoffice_schedule_calendar_room_name(room_no);
		
		RoomDTO rvo = modelMapper.map(re, RoomDTO.class);
		
		return rvo;
	}

	/**
	 * 일정 관리 - 휴무/브레이크타임 취소
	 */
	@Override
	public int backoffice_schedule_cancel(String backoffice_no, String schedule_no) {
		return schedule_repository.backoffice_schedule_cancel(backoffice_no, schedule_no);
	}

	// ******** 페이징 ***********
	// 공간 리스트
	public long dashboard_room_list_cnt(String backoffice_no) {
		return rm_repository.dashboard_room_list_cnt(backoffice_no);
	}

	// 문의 리스트
	public long backoffice_qna_selectAll_cnt(String backoffice_no) {
		return cq_repository.select_all_q_cnt(backoffice_no);
	}

	// 후기 리스트
	public long backoffice_review_selectAll_cnt(String backoffice_no) {
		return r_repository.backoffice_review_selectAll_cnt(backoffice_no);
	}

	// 정산 리스트
	public long backoffice_sales_selectAll_cnt(String backoffice_no) {
		return s_repository.backoffice_sales_selectAll_cnt(backoffice_no);
	}
	
	// 일정 리스트
	@Override
	public int backoffice_room_cnt(String backoffice_no, String not_sdate, String not_edate, String not_stime, String not_etime, String off_type) {
		// 1, 2 날짜 형태 변환
		String reserve_stime = null;
		String reserve_etime = null;
		
		if (off_type.equals("dayoff")) { // 휴무일 때
			log.info("휴무");

			not_stime = "00:00:00";
			not_etime = "23:59:59";

			reserve_stime = (not_sdate + not_stime);
			log.info("reserve_stime : {} ", reserve_stime);
			reserve_etime = (not_edate + not_etime);
			log.info("reserve_etime : {} ", reserve_etime);
		} else { // 브레이크 타임일 때
			log.info("브레이크타임");

			log.info("not_sdate : {} ", not_sdate);
			reserve_stime = (not_sdate + not_stime);
			log.info("reserve_stime : {} ", reserve_stime);

			not_edate = (not_sdate);
			log.info("not_edate : {} ", not_edate);
			reserve_etime = (not_edate + not_etime);
			log.info("reserve_etime : {} ", reserve_etime);
		}
		
		return sc_repository.backoffice_schedule_list_cnt(backoffice_no, reserve_stime, reserve_etime);
	}

	// 예약자 리스트
	public int backoffice_reservation_cnt(String backoffice_no, String not_sdate, String not_edate, String not_stime,
			String not_etime, String room_no, String off_type) {
		String reserve_stime = null;
		String reserve_etime = null;
		if (off_type.equals("dayoff")) { // 휴무일 때
			log.info("휴무");

			not_stime = "00:00:00";
			not_etime = "23:59:59";

			reserve_stime = (not_sdate + not_stime);
			log.info("reserve_stime : {} ", reserve_stime);
			reserve_etime = (not_edate + not_etime);
			log.info("reserve_etime : {} ", reserve_etime);
		} else { // 브레이크 타임일 때
			log.info("브레이크 타임");

			log.info("not_sdate : {} ", not_sdate);
			reserve_stime = (not_sdate + not_stime);
			log.info("reserve_stime : {} ", reserve_stime);

			not_edate = (not_sdate);
			log.info("not_edate : {} ", not_edate);
			reserve_etime = (not_edate + not_etime);
			log.info("reserve_etime : {} ", reserve_etime);
		}

		int total_cnt = reservation_repository.backoffice_reservation_list_cnt(backoffice_no, reserve_stime, reserve_etime, room_no);
		
		return total_cnt;
	}

	// 예약 관리 리스트
	public int backoffice_reserve_selectAll_cnt(String backoffice_no, String reserve_state) {

		int total_cnt = 0;

		if (reserve_state.equals("all")) {
			total_cnt = rv_repository.backoffice_reserve_selectAll(backoffice_no);
		} else if (reserve_state.equals("in_use")) {
			total_cnt = rv_repository.backoffice_reserve_selectAll_inuse(backoffice_no);
		} else if (reserve_state.equals("end")) {
			total_cnt = rv_repository.backoffice_reserve_selectAll_end(backoffice_no);
		} else if (reserve_state.equals("cancel")) {
			total_cnt = rv_repository.backoffice_reserve_selectAll_cancel(backoffice_no);
		}

		return total_cnt;
	}

	// 예약 관리 리스트 - 검색
	public int backoffice_search_reserve_cnt(String backoffice_no, String searchword, String reserve_state) {
		int total_cnt = 0;

		if (reserve_state.equals("all")) {
			total_cnt = rv_repository.backoffice_reserve_selectAll_search(backoffice_no, "%" + searchword + "%");
		} else if (reserve_state.equals("in_use")) {
			total_cnt = rv_repository.backoffice_reserve_selectAll_inuse_search(backoffice_no, "%" + searchword + "%");
		} else if (reserve_state.equals("end")) {
			total_cnt = rv_repository.backoffice_reserve_selectAll_end_search(backoffice_no, "%" + searchword + "%");
		} else if (reserve_state.equals("cancel")) {
			total_cnt = rv_repository.backoffice_reserve_selectAll_cancel_search(backoffice_no, "%" + searchword + "%");
		}

		return total_cnt;
	}

	/**
	 * 
	 * AOP ... 예약 상태 업데이트
	 * 
	 */
	@Override
	public void reserve_state_auto_update() {
		// select
		// 분기문
		// 현재 날짜 : 예약 시작 날짜와 같거나 시작날짜-끝날짜 사이 - in_use 로 변경
		// 현재 날짜 : 예약 끝날자 보다 지나면 end
		List<ReserveUpdateEntity> rv = reserveAutoUpdateRepository.selectAll_reserve();
		List<ReserveUpdateDTO> rvs = rv.stream().map(rvo -> modelMapper.map(rvo, ReserveUpdateDTO.class)).collect(Collectors.toList());

		Date sysdate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String ss = sdf.format(sysdate);

		for (ReserveUpdateDTO rvo : rvs) {

			Date stime = rvo.getReserve_stime();
			Date etime = rvo.getReserve_etime();

			int start = stime.compareTo(sysdate);
			int end = etime.compareTo(sysdate);

			if (start >= 0 && !rvo.getReserve_state().equals("begin")) {
				reserveAutoUpdateRepository.update_reserve_state_begin(rvo.getReserve_no());
			} else if (start <= 0 && end >= 0 && !rvo.getReserve_state().equals("in_use")) {
				reserveAutoUpdateRepository.update_reserve_state_inuse(rvo.getReserve_no());
			} else if (end < 0 && !rvo.getReserve_state().equals("end")) {
				reserveAutoUpdateRepository.update_reserve_state_end(rvo.getReserve_no());
			}
		}
	}

	/**
	 * 
	 * AOP ... 예약 상태 false 삭제 - reserve_no
	 * 
	 */
	@Override
	public ReserveUpdateDTO select_one_false_reserve(String reserve_stime, String reserve_etime, String room_no) {
		
		ReserveUpdateEntity rv = reserveAutoUpdateRepository.select_one_false_reserve(reserve_stime, reserve_etime, room_no);
		
		ReserveUpdateDTO rvo = modelMapper.map(rv, ReserveUpdateDTO.class);
		
		return rvo;
	}

	/**
	 * 
	 * AOP ... 예약 상태 false 삭제
	 * 
	 */
	@Override
	public void reserve_auto_delete(String reserve_no) {
		
		reserveAutoUpdateRepository.reserve_auto_delete(reserve_no);
		
	}

}
