package com.rence.user.service;



import java.text.DecimalFormat;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.rence.office.common.OfficeInfoMap;
import com.rence.user.dao.UserMypageDAO;
import com.rence.user.model.MyPageReserveListDto;
import com.rence.user.model.UserDto;
import com.rence.user.model.UserMileageDto;
import com.rence.user.model.UserMypageDto;
import com.rence.user.model.UserQuestionDto;
import com.rence.user.model.UserReview_ViewDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserMypageSeriviceImpl implements UserMypageSerivice {

	@Autowired
	UserMypageDAO dao;
	
	@Autowired
	UserFileuploadService fileuploadService;
	
	
	
	

	@Override
	public UserMypageDto user_mypage_select(HttpServletRequest request, HttpServletResponse response) {

		log.info("user_mypage_select()...");

		// 쿠키에서 유저번호 가져오기
		String user_no = null;
		Cookie[] cookies = request.getCookies();
		for (Cookie c : cookies) {
			if (c.getName().equals("user_no")) {
				user_no = c.getValue();
			}
		}
		//Base64 디코더 작업으로 suerNo정보 가져오기
		byte[] decodedBytes = Base64.getDecoder().decode(user_no);
		user_no = new String(decodedBytes);
		

		UserMypageDto umdto = dao.user_mypage_select(user_no);

		log.info("umdto: {}", umdto);
		// 마일리지 콤마단위로 변환
		DecimalFormat dc = new DecimalFormat("###,###,###,###,###");
		umdto.setMileage_total(dc.format(Integer.parseInt(umdto.getMileage_total())));

		umdto.setUser_image("https://rence.s3.ap-northeast-2.amazonaws.com/user/" + umdto.getUser_image());

		return umdto;
	}

	// 마이페이지 - 비밀번호 변경
	@Override
	public Map<String, String> user_pw_updateOK(UserDto udto) {
		log.info("user_pw_updateOK()...");

		Map<String, String> map = new HashMap<String, String>();

		int result = dao.user_pw_updateOK(udto);

		if (result == 1) {
			log.info("user_pw_upddate successed...");
			map.put("result", "1");
		}

		else {
			log.info("user_pw_upddate failed...");
			map.put("result", "0");
		}

		return map;
	}

	// 마이페이지 - 비밀번호 수정 - 현재 비밀번호 확인
	@Override
	public Map<String, String> check_now_pw(UserDto udto) {
		log.info("check_now_pw()...");
		Map<String, String> map = new HashMap<String, String>();

		int result = dao.check_now_pw(udto);

		if (result == 1) {
			log.info("right now pw...");
			map.put("result", "1");
		} else {
			log.info("not now pw...");
			map.put("result", "0");
		}
		return map;
	}

	@Override
	public Map<String, String> user_img_updateOK(UserDto udto, HttpServletRequest request, MultipartHttpServletRequest mtfRequest,
			MultipartFile multipartFile_user) {
		log.info("user_img_updateOK()...");
		
		Map<String, String> map = new HashMap<String, String>();
		
		//쿠키에서 유저 번호 가져오기
		String user_no = null;
		Cookie[] cookies = request.getCookies();
		for (Cookie c : cookies) {
			if (c.getName().equals("user_no")) {
				user_no = c.getValue();
			}
		}
		udto.setUser_no(user_no);
		log.info("==result==: {}", udto);

		// 사진(파일)업로드
		udto = fileuploadService.FileuploadOK(udto, mtfRequest, multipartFile_user);
		log.info("fileresult: {}", udto);
		
		udto.setUser_image("https://rence.s3.ap-northeast-2.amazonaws.com/user/" + udto.getUser_image());
		map.put("user_image", udto.getUser_image());
		map.put("result", "1");

		return map;
	}
	
	//마이페이지- 회원탈퇴
	@Override
	public Map<String, String> user_secedeOK(UserDto udto, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();

		int result = dao.user_secedeOK(udto);
		log.info("result: {}", udto);
		if (result == 1) {
			session.invalidate();
			log.info("user_secede successed...");
			map.put("result", "1");

			Cookie[] cookies = request.getCookies(); // 모든 쿠키의 정보를 cookies에 저장
			if (cookies != null) { // 쿠키가 한개라도 있으면 실행
				for (int i = 0; i < cookies.length; i++) {
					cookies[i].setMaxAge(0); // 유효시간을 0으로 설정
					response.addCookie(cookies[i]); // 응답 헤더에 추가
				}
			}

		} else {
			log.info("user_secede failed...");
			map.put("result", "0");
		}
		
		return map;
	}
	
	//마이페이지 - 예약리스트
	@Override
	public Map<String, Object> reserve_list_rsu(String time_point, String user_no, Integer page) {
		List<MyPageReserveListDto> list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		// 페이징 처리 로직
		// 총 리스트 수
		long total_rowCount_reserve = dao.total_rowCount_reserve(user_no, time_point);
		log.info("total_rowCount_reserve: {}", total_rowCount_reserve);

		// 총 페이징되는 수(한페이지에 4개의 목록을 보여줄시 만들어지는 페이지 수)
		long totalPageCnt = (long) Math.ceil(total_rowCount_reserve / 4.0);
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
		// 페이징처리를 위한 페이지 계산 로직끝

		if (time_point.equals("now")) {
			list = dao.select_all_now_reserve_list_paging(user_no, page);
			map.put("type", "now");

		} else if (time_point.equals("before")) {
			list = dao.select_all_before_reserve_list_paging(user_no, page);
			map.put("type", "before");
		}
		if (list == null) {
			map.put("cnt", 0);
		} else {
			map.put("cnt", list.size());
			OfficeInfoMap info_map = new OfficeInfoMap();

			// 대표 이미지 1장 처리
			for (MyPageReserveListDto vo : list) {
				List<String> splitImage = info_map.splitImage(vo.getBackoffice_image());
				String room_first_image = splitImage.get(0);
				vo.setBackoffice_image(room_first_image);
			}
		}

		DecimalFormat dc = new DecimalFormat("###,###,###,###,###");
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setPayment_total(dc.format(Integer.parseInt(list.get(i).getPayment_total())));
		}
		log.info("Type change list: {}" + list);

		map.put("list", list);
		map.put("page", "reserve-list");

			

		log.info("reserve_list : {}", map);
		return map;

	}
	
	// 마이페이지 - 마일리지리스트
	@Override
	public Map<String, Object> mileage_list_page(UserDto udto, Integer page) {
		Map<String, Object> map = new HashMap<String, Object>();

		// 총 마일리지 부분
		UserMileageDto umdto = dao.totalMileage_selectOne(udto);
				
		log.info("umdto: {}", umdto);

		// 마일리지 콤마단위로 변환
		DecimalFormat dc = new DecimalFormat("###,###,###,###,###");
		String mileage_total = dc.format(umdto.getMileage_total());
		
		log.info("mileage_total: " + mileage_total);

		// 페이징 처리 로직(마일리지 리스트 전용!!!!)
		// 리스트 수
		long total_rowCount_mileage_all = dao.total_rowCount_mileage_all(udto);
		total_rowCount_mileage_all -= 1; // 회원가입시 들어가는 기본값 제외
		log.info("total_rowCount_mileage_all: {}", total_rowCount_mileage_all);

		// 총 페이징되는 수
		long totalPageCnt = (long) Math.ceil(total_rowCount_mileage_all / 8.0);
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

		// 페이징처리를 위한 페이지 계산 로직끝

		List<UserMileageDto> vos = dao.user_mileage_selectAll_paging(udto, page, total_rowCount_mileage_all);
		log.info("vos: " + vos);

		for (int i = 0; i < vos.size(); i++) {
			vos.get(i).setMileage(dc.format(Integer.parseInt(vos.get(i).getMileage())));
		}
		log.info("Type change vos: {}" + vos);

		map.put("mileage_total", mileage_total);
		map.put("list", vos);
		map.put("page", "mileage");
		
		return map;
	}
	
	// 마이페이지 - 마일리지조건리스트
	@Override
	public Map<String, Object> mileage_list_page_searchKey(UserDto udto, Integer page, String searchKey) {
		Map<String, Object> map = new HashMap<String, Object>();
		log.info("mileage_search_list()...");

		log.info("검색 키워드: " + searchKey);
		log.info("UserDto(사용자 고유번호): {}", udto);

		// 총 마일리지 부분
		UserMileageDto umdto = dao.totalMileage_selectOne(udto);
		log.info("umdto: {}", umdto);

		// 마일리지 콤마단위로 변환
		DecimalFormat dc = new DecimalFormat("###,###,###,###,###");
		String mileage_total = dc.format(umdto.getMileage_total());
		
		log.info("mileage_total: " + mileage_total);

		// 페이징 처리 로직(이거는 마일리지 전용임!!!)
		// 리스트 수
		long total_rowCount_mileage_search = dao.total_rowCount_mileage_searchKey(udto, searchKey);
		total_rowCount_mileage_search -= 1;// 회원가입시 들어가는 기본값 제외
		log.info("total_rowCount_mileage_search: {}", total_rowCount_mileage_search);

		// 총 페이징되는 수
		long totalPageCnt = (long) Math.ceil(total_rowCount_mileage_search / 8.0);
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

		// 페이징처리를 위한 페이지 계산 로직끝

		List<UserMileageDto> vos = dao.user_mileage_search_list_paging(udto, searchKey, page,
				total_rowCount_mileage_search);
		log.info("vos: " + vos);

		for (int i = 0; i < vos.size(); i++) {
			vos.get(i).setMileage(dc.format(Integer.parseInt(vos.get(i).getMileage())));
		}
		log.info("Type change vos: {}" + vos);

		map.put("mileage_total", mileage_total);
		map.put("list", vos);
		map.put("page", "mileage");

		

		return map;
	}
	
	// 마이페이지 - 리뷰리스트
	@Override
	public Map<String, Object> review_list_page(String user_no, Integer page) {
		Map<String, Object> map = new HashMap<String, Object>();

		// 페이징 처리 로직
		// 리스트 수
		long total_rowCount_review = dao.total_rowCount_review(user_no);
		log.info("total_rowCount_review: {}", total_rowCount_review);

		// 총 페이징되는 수
		long totalPageCnt = (long) Math.ceil(total_rowCount_review / 8.0);
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

		// 페이징처리를 위한 페이지 계산 로직끝

		List<UserReview_ViewDto> list = dao.select_all_review_paging(user_no, page);

		map.put("page", "review");
		map.put("list", list);

		return map;
	}
	
	// 마이페이지 - 문의리스트
	@Override
	public Map<String, Object> question_list_page(String user_no, Integer page) {
Map<String, Object> map = new HashMap<String, Object>();
		

		// 페이징 처리 로직
		// 리스트 수
		long total_rowCount_question = dao.total_rowCount_question(user_no);
		log.info("total_rowCount_question: {}", total_rowCount_question);

		// 총 페이징되는 수
		long totalPageCnt = (long) Math.ceil(total_rowCount_question / 8.0);
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

		// 페이징처리를 위한 페이지 계산 로직끝

		List<UserQuestionDto> list = dao.select_all_question_paging(user_no, page);
		if (list != null) {
			for (UserQuestionDto dto : list) {
				UserQuestionDto dto2 = dao.select_one_answer(dto.getComment_no());
				if (dto2 != null) {
					dto.setAnswer_content(dto2.getComment_content());
					dto.setAnswer_date(dto2.getComment_date());
					dto.setState("Y");
				} else {
					dto.setState("N");
				}
			}
		}

		map.put("page", "question_list");
		map.put("list", list);

		
		return map;
	}
	

}// end class
