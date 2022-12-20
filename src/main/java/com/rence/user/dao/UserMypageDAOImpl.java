package com.rence.user.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.rence.user.model.MyPageReserveListDto;
import com.rence.user.model.MyPageReserveListEntity;
import com.rence.user.model.UserDto;
import com.rence.user.model.UserEntity;
import com.rence.user.model.UserMileageDto;
import com.rence.user.model.UserMileageEntity;
import com.rence.user.model.UserMypageDto;
import com.rence.user.model.UserMypageEntity;
import com.rence.user.model.UserQuestioEntity;
import com.rence.user.model.UserQuestionDto;
import com.rence.user.model.UserReview_ViewDto;
import com.rence.user.model.UserReview_ViewEntity;
import com.rence.user.repository.MileageRepository;
import com.rence.user.repository.MyQuestionRepository;
import com.rence.user.repository.MyReserveRepository;
import com.rence.user.repository.MypageRepository;
import com.rence.user.repository.UserRepository;
import com.rence.user.repository.UserReviewRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class UserMypageDAOImpl implements UserMypageDAO {

	@Autowired
	UserRepository userRepository;

	@Autowired
	MypageRepository repository;

	@Autowired
	ModelMapper modelmapper;

	@Autowired
	MyReserveRepository myReserveRepository;

	@Autowired
	MileageRepository mileageRepository;
	
	@Autowired
	UserReviewRepository userReviewRepository;
	
	@Autowired
	MyQuestionRepository  myQuestionRepository;

	// 마이페이지 정보 불러오기
	@Override
	public UserMypageDto user_mypage_select(String user_no) {
		log.info("user_mypage_select()....");
		log.info("user_no: {}", user_no);

		UserMypageEntity entity = repository.user_mypage_select(user_no);
		
		UserMypageDto mypagedto = null;
		if(entity != null) {
			 mypagedto = modelmapper.map(entity, UserMypageDto.class);
		}

		return mypagedto;
	}

	// 마이페이지 - 비밀번호 수정
	@Override
	public int user_pw_updateOK(UserDto udto) {
		log.info("user_pw_updateOK()....");
		log.info("udto: {}", udto);

		// 비밀번호 암호화 암호화
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		udto.setUser_pw(encoder.encode(udto.getUser_pw()));

		log.info("(비밀번호 변경부분)암호화된 PW: {}", udto.getUser_pw());

		return userRepository.user_pw_updateOK(udto.getUser_pw(), udto.getUser_no());
	}

	// 마이페이지 - 비밀번호 수정 - 현재 비밀번호 확인
	@Override
	public int check_now_pw(UserDto udto) {
		log.info("check_now_pw()....");
		log.info("(비밀번호 확인부분)uvo: {}", udto);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		UserEntity entity = userRepository.check_now_pw_selectOne(udto.getUser_no());
		log.info("(비밀번호 확인부분)entity: {}", entity);

		boolean match_res = encoder.matches(udto.getPassword(), entity.getUser_pw());
		log.info("(비밀번호 확인부분)res: {}", match_res);
		int result;
		if (match_res == true) {
			result = 1;
		} else {
			result = 0;
		}
		// udto.setUser_pw("$2a$12$SkMR3vslquCDjRVoFWGtI.XoN8Bs8DsWfrYqHb.jSt6IL3EgXLVeC");
		// //test1234!
		return result;
	}

	// 마이페이지 - 프로필 변경내용 저장
	@Override
	public int user_img_updateOK(UserDto udto) {
		log.info("user_img_updateOK()....");
		log.info("udto", udto);

		return repository.user_img_updateOK(udto.getUser_image(), udto.getUser_no());
	}

	// 회원탈퇴에 따른 회원상태 수정
	@Override
	public int user_secedeOK(UserDto udto) {
		log.info("user_secedeOK()....");
		log.info("udto: {}", udto);

		return repository.user_secedeOK(udto.getUser_no());
	}

	// 마이페이지 - 예약리스트 - 총 예약리스트 수
	@Override
	public long total_rowCount_reserve(String user_no, String time_point) {
		log.info("total_rowCount_reserve()....");
		log.info("user_no: {}", user_no);
		log.info("time_point: {}", time_point);
		long total_rowCount_reserve = 0;
		if (time_point.equals("now")) {
			total_rowCount_reserve = myReserveRepository.count_Reserve_now(user_no);
		} else if (time_point.equals("before")) {
			total_rowCount_reserve = myReserveRepository.count_Reserve_before(user_no);
		}

		return total_rowCount_reserve;
	}

	// 마이페이지 - 예약리스트 - 페이징(현재)
	@Override
	public List<MyPageReserveListDto> select_all_now_reserve_list_paging(String user_no, Integer page) {
		log.info("select_all_now_reserve_list_paging()....");
		log.info("user_no: {}", user_no);
		log.info("current page: {}", page);

		Integer row_count = 4;
		Integer start_row = (page - 1) * row_count + 1;
		Integer end_row = page * row_count;

		log.info("start_row: " + start_row);
		log.info("end_row: " + end_row);

		List<MyPageReserveListEntity> entity_vos = myReserveRepository.select_all_now_reserve_list_paging(user_no,
				start_row, end_row);
		//log.info("entity_vos: {}", entity_vos);

		List<MyPageReserveListDto> vos = entity_vos.stream()
				.map(source -> modelmapper.map(source, MyPageReserveListDto.class)).collect(Collectors.toList());

		return vos;
	}

	// 마이페이지 - 예약리스트 - 페이징(과거)
	@Override
	public List<MyPageReserveListDto> select_all_before_reserve_list_paging(String user_no, Integer page) {
		log.info("select_all_before_reserve_list_paging()....");
		log.info("user_no: {}", user_no);
		log.info("current page: {}", page);

		Integer row_count = 4;
		Integer start_row = (page - 1) * row_count + 1;
		Integer end_row = page * row_count;

		log.info("start_row: " + start_row);
		log.info("end_row: " + end_row);

		List<MyPageReserveListEntity> entity_vos = myReserveRepository.select_all_before_reserve_list_paging(user_no, start_row, end_row);
		log.info("entity_vos: {}", entity_vos);

		List<MyPageReserveListDto> vos = entity_vos.stream()
				.map(source -> modelmapper.map(source, MyPageReserveListDto.class)).collect(Collectors.toList());

		return vos;
	}

	// 마이페이지 - 마일리지 리스트 - 총마일리지
	@Override
	public UserMileageDto totalMileage_selectOne(UserDto udto) {
		log.info("totalMileage_selectOne()...." + udto.getUser_no());

		UserMileageEntity entity = mileageRepository.totalMileage_selectOne(udto.getUser_no());

		UserMileageDto udto2 = modelmapper.map(entity, UserMileageDto.class);

		return udto2;
	}

	// 마이페이지 - 마일리지 리스트 - 총 마일리지 리스트 수
	@Override
	public long total_rowCount_mileage_all(UserDto udto) {
		log.info("total_rowCount_mileage_all()....");

		return mileageRepository.count_allmileage(udto.getUser_no());
	}

	// 마이페이지 - 마일리지 리스트 - 총 마일리지 리스트
	@Override
	public List<UserMileageDto> user_mileage_selectAll_paging(UserDto udto, Integer page,
			long total_rowCount_mileage_all) {
		log.info("user_mileage_selectAll_paging()....");
		log.info("udto: {}", udto);
		log.info("current page: {}", page);

		// 회원가입 기본값 0만 들어있으면 paging 처리 및 리스트 처리 제외
		if (total_rowCount_mileage_all == 0) {
			page = 0;
		}
		
		Integer row_count = 8;
		Integer start_row = (page - 1) * row_count + 1;
		Integer end_row = page * row_count;

		// 마지막 페이지 end_row 처리
		if (end_row > total_rowCount_mileage_all) {
			end_row = (int) total_rowCount_mileage_all;
		}
		log.info("start_row: " + start_row);
		log.info("end_row: " + end_row);
		
		
		List<UserMileageEntity> entity_vos = mileageRepository.user_mileage_selectAll_paging(udto.getUser_no(), start_row, end_row);
		log.info("entity_vos: {}", entity_vos);

		List<UserMileageDto> vos = entity_vos.stream()
				.map(source -> modelmapper.map(source, UserMileageDto.class)).collect(Collectors.toList());
		
		return vos;
	}
	
	// 마이페이지 - 마일리지 리스트 - 총 마일리지 조건 리스트 수
	@Override
	public long total_rowCount_mileage_searchKey(UserDto udto, String searchKey) {
		log.info("total_rowCount_mileage_searchKey()....");
		log.info("udto: {}", udto);
		log.info("searchKey: {}", searchKey);
		
		long total_rowCount_mileage_all = 0;
		if (searchKey.equals("all")) {
			total_rowCount_mileage_all = mileageRepository.count_allmileage(udto.getUser_no());
		} else if (searchKey.equals("plus")) {
			total_rowCount_mileage_all = mileageRepository.count_plusmileage(udto.getUser_no());
		} else if (searchKey.equals("minus")) {
			total_rowCount_mileage_all = mileageRepository.count_minusmileage(udto.getUser_no());
		}
		return total_rowCount_mileage_all;
	}

	//마이페이지 - 마일리지 리스트 - 적립/사용 마일리지 리스트
	@Override
	public List<UserMileageDto> user_mileage_search_list_paging(UserDto udto, String searchKey, Integer page,
			long total_rowCount_mileage_search) {
		log.info("user_mileage_search_list_paging()....");
		log.info("udto: {}", udto);
		log.info("searchKey: {}", searchKey);
		List<UserMileageEntity> entity_vos = null;

		// 회원가입 기본값 0만 들어있으면 paging 처리 및 리스트 처리 제외
		if (total_rowCount_mileage_search == 0) {
			page = 0;
		}

		Integer row_count = 8;
		Integer start_row = (page - 1) * row_count + 1;
		Integer end_row = page * row_count;

		// 마지막 페이지 end_row 처리(단, minus의 경우는 기본값이 들어 있지 않기 때문에 page * row_count을통해 끝까지
		// 리스트를 불러오게 함
		if ((end_row > total_rowCount_mileage_search) && !searchKey.equals("minus")) {
			end_row = (int) total_rowCount_mileage_search;
		}
		log.info("start_row: " + start_row);
		log.info("end_row: " + end_row);

		if (searchKey.equals("all")) {
			entity_vos = mileageRepository.user_mileage_selectAll_paging(udto.getUser_no(), start_row, end_row);
		} else if (searchKey.equals("plus")) {
			entity_vos = mileageRepository.mileage_search_list_plus_paging(udto.getUser_no(), start_row, end_row);
		} else if (searchKey.equals("minus")) {
			entity_vos = mileageRepository.mileage_search_list_minus_paging(udto.getUser_no(), start_row, end_row);
		}
		
		List<UserMileageDto> vos = entity_vos.stream()
				.map(source -> modelmapper.map(source, UserMileageDto.class)).collect(Collectors.toList());
		return vos;
	}
	
	// 리뷰(후기) 리스트 수
	@Override
	public long total_rowCount_review(String user_no) {
		log.info("total_rowCount_review()....");
		log.info("user_no: {}", user_no);

		return userReviewRepository.total_rowCount_review(user_no);
	}

	//리뷰(후기) 리스트
	@Override
	public List<UserReview_ViewDto> select_all_review_paging(String user_no, Integer page) {
		log.info("select_all_review_paging()....");
		log.info("user_no: {}", user_no);
		log.info("current page: {}", page);

		Integer row_count = 8;
		Integer start_row = (page - 1) * row_count + 1;
		Integer end_row = page * row_count;

		log.info("start_row: " + start_row);
		log.info("end_row: " + end_row);

		List<UserReview_ViewEntity> entity_vos = userReviewRepository.select_all_review_paging(user_no, start_row, end_row);
		log.info("entity_vos: {}", entity_vos);

		List<UserReview_ViewDto> vos = entity_vos.stream()
				.map(source -> modelmapper.map(source, UserReview_ViewDto.class)).collect(Collectors.toList());
		return vos;
	}
	
	//문의 리스트 수
	@Override
	public long total_rowCount_question(String user_no) {
		log.info("total_rowCount_question()....");
		log.info("user_no: {}", user_no);

		return myQuestionRepository.count_question(user_no);
	}
	
	// 마이페이지 - 문의내역리스트 페이징
	@Override
	public List<UserQuestionDto> select_all_question_paging(String user_no, Integer page) {
		log.info("select_all_question_paging()....");
		log.info("user_no: {}", user_no);
		log.info("current page: {}", page);

		Integer row_count = 8;
		Integer start_row = (page - 1) * row_count + 1;
		Integer end_row = page * row_count;

		log.info("start_row: " + start_row);
		log.info("end_row: " + end_row);

		List<UserQuestioEntity> entity_vos = myQuestionRepository.select_all_question_paging(user_no, start_row, end_row);
		log.info("entity_vos: {}", entity_vos);

		List<UserQuestionDto> vos = entity_vos.stream()
				.map(source -> modelmapper.map(source, UserQuestionDto.class)).collect(Collectors.toList());
		return vos;
	}
	
	// 마이페이지 - 문의내역리스트(답변)
	@Override
	public UserQuestionDto select_one_answer(String comment_no) {
		log.info("select_one_answer()....");
		log.info("comment_no: {}", comment_no);
		UserQuestioEntity entity = myQuestionRepository.select_one_answer(comment_no);
		
		UserQuestionDto dto = modelmapper.map(entity, UserQuestionDto.class);
		
		return dto;
	}
	
}// end class