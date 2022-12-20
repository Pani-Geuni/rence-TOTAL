<!-- eslint-disable consistent-return -->
<!-- eslint-disable brace-style -->
<!-- eslint-disable no-plusplus -->
<!-- eslint-disable no-mixed-spaces-and-tabs -->
<!-- eslint-disable linebreak-style -->
<!-- eslint-disable no-tabs -->
<!-- eslint-disable no-plusplus -->
<!-- eslint-disable array-callback-return -->
<!-- eslint-disable space-before-blocks -->
<!-- eslint-disable -->

<!--
 - @author 김예은
 - @refactoring 김예은
-->

<template>
  <div class="popup-background blind">
		<!-- START LOGIN SECTION -->
		<div id="login-section" class="blind">
			<section class="login-popup-logo-section">
				<img src="@/assets/IMG/common/RENCE.svg" alt ="로고이미지" class="popup-logo">
			</section>
			<section class="login-popup-input-section">
				<input type="text" id="login-id" @click="remove_null_input_border($event.target)" class="login-popup-input" placeholder="아이디를 입력하세요." autocomplete="off" />
				<input type="password" id="login-pw" @click="remove_null_input_border($event.target)" class="login-popup-input" placeholder="비밀번호를 입력하세요." />
			</section>
			<section class="login-popup-btn-section">
				<input @click="do_login" type="button" id="login-btn" value="로그인">
				<div class="txt-btn-wrap">
					<span @click="go_find_id" class="txt-btn">아이디 찾기</span>
					<span @click="go_find_pw" class="txt-btn">비밀번호 찾기</span>
					<span @click="close_login_popup" class="txt-btn login-close">창닫기</span>
				</div>
			</section>
		</div>
		<!-- END LOGIN SECTION -->

		<!-- START JOIN SECTION -->
		<div id="join-section" class="blind">
			<section class="join-popup-title-section">
				<span>회원가입</span>
				<span class="join-closer">
					<img src="@/assets/IMG/common/closer.svg" @click="close_join_popup" alt="closer-img" class="closer-img" />
				</span>
			</section>
			<section class="join-popup-input-section">
				<div class="input-wrap">
					<div class="input-check">
						<input type="email" @keyup="test_regx($event.target)" @keydown="test_regx($event.target)" @click="remove_null_input_border($event.target)" id="join-email" class="join-popup-input-short" placeholder="이메일"
							autocomplete="off" />
						<input type="button" @click="do_check_email" id="check_email" class="join-popup-check-btn" value="인증하기" />
					</div>
					<span class="warning-text blind">다시 시도해주세요.</span>
				</div>
				<div class="input-wrap">
					<div class="input-check">
						<input type="text" id="join-email-code" @click="remove_null_input_border($event.target)" class="join-popup-input-short" placeholder="인증번호" autocomplete="off" />
						<input type="button" id="check_email-code" @click="do_check_email_code" class="join-popup-check-btn" value="확인" />
					</div>
					<span class="warning-text blind">다시 시도해주세요.</span>
				</div>
				<div class="input-wrap">
					<div class="input-check">
						<input type="text" @keyup="test_regx($event.target)" @keydown="test_regx($event.target)" id="join-id" @click="remove_null_input_border($event.target)" class="join-popup-input-short" placeholder="아이디(소문자, 숫자만 입력 = 5~10자))" autocomplete="off" />
						<input type="button" @click="do_check_id" id="check_id" class="join-popup-check-btn" value="중복확인" />
					</div>
					<span class="warning-text blind">이미 아이디가 존재합니다.</span>
				</div>
				<div class="input-wrap">
					<input type="password" @keyup="test_regx($event.target)" @keydown="test_regx($event.target)" id="join-pw" @click="remove_null_input_border($event.target)" class="join-popup-input" placeholder="비밀번호(영문+숫자+특수문자 = 8~10글자)" />
					<span class="warning-text blind">다시 시도해주세요.</span>
				</div>
				<div class="input-wrap">
					<input type="password" @keyup="test_regx($event.target)" @keydown="test_regx($event.target)" id="join-re-pw" @click="remove_null_input_border($event.target)" class="join-popup-input" placeholder="비밀번호 재입력" />
					<span class="warning-text blind">다시 시도해주세요.</span>
				</div>
				<div class="input-wrap">
					<input type="text" id="join-name" @click="remove_null_input_border($event.target)" class="join-popup-input" placeholder="이름" autocomplete="off" />
				</div>
				<div class="input-wrap">
					<input type="number" @keyup="test_regx($event.target)" @keydown="test_regx($event.target)" id="join-tel" @click="remove_null_input_border($event.target)" class="join-popup-input" placeholder="전화번호(-없이 입력)" autocomplete="off" />
					<span class="warning-text blind">-없이 입력하세요.</span>
				</div>
				<div class="input-wrap">
					<input type="number" @keyup="test_regx($event.target)" @keydown="test_regx($event.target)" id="join-birth" @click="remove_null_input_border($event.target)" class="join-popup-input" placeholder="생년월일(숫자로만 입력)" autocomplete="off" />
					<span class="warning-text blind">숫자로만 입력가능합니다.</span>
				</div>
			</section>
			<section class="join-popup-btn-section">
				<input type="button" @click="do_join" id="join-btn" value="회원가입">
			</section>
		</div>
		<!-- END JOIN SECTION -->

		<!-- START FIND-ID SECTION -->
		<div id="find-id-section" class="find-section blind">
			<section class="find-popup-logo-section">
				<span>아이디 찾기</span>
			</section>
			<section class="find-popup-input-section">
				<span class="find-guide-txt">회원가입 시 입력한 이메일을 입력해주세요.</span>
				<input type="text" 
        @keyup="check_mail_regx_find($event.target)" 
        @keydown="check_mail_regx_find($event.target)" 
        @click="remove_null_input_border($event.target)" 
        id="find-id-email" class="find-popup-input" placeholder="이메일을 입력하세요." autocomplete="off" />
        <span class="warning-text blind">다시 시도해주세요.</span>
			</section>
			<section class="find-popup-btn-section">
				<input type="button" @click="do_find_id" id="find-id-btn" class="find-btn" value="메일 보내기">
				<input type="button" @click="close_find_id_popup" id="find-id-close" class="p-close" value="창닫기">
			</section>
		</div>
		<!-- END FIND-ID SECTION -->

		<!-- START FIND-PW SECTION -->
		<div id="find-pw-section" class="find-section blind">
			<section class="find-popup-logo-section">
				<span>비밀번호 찾기</span>
			</section>
			<section class="find-popup-input-section">
				<div class="email-wrap">
					<span class="find-guide-txt">회원가입 시 입력한 이메일을 입력해주세요.</span>
					<input type="email" 
            @keyup="check_mail_regx_find($event.target)" 
            @keydown="check_mail_regx_find($event.target)" 
            @click="remove_null_input_border($event.target)" 
          id="find-pw-email" class="find-popup-input" placeholder="이메일을 입력하세요." autocomplete="off" />
          <span class="warning-text blind">다시 시도해주세요.</span>
				</div>
				<div class="id-wrap">
					<span class="find-guide-txt">회원가입 시 입력한 아이디를 입력해 주세요.</span>
					<input type="text" 
            @keyup="check_id_regx_find($event.target)" 
            @keydown="check_id_regx_find($event.target)"
            @click="remove_null_input_border($event.target)" 
          id="find-pw-id" class="find-popup-input" placeholder="아이디를 입력하세요." autocomplete="off" />
          <span class="warning-text blind">다시 시도해주세요.</span>
				</div>
			</section>
			<section class="find-popup-btn-section">
				<input type="button" @click="do_find_pw" id="find-pw-btn" class="find-btn" value="비밀번호 찾기">
				<input type="button" @click="close_find_pw_popup" id="find-pw-close" class="p-close" value="창닫기">
			</section>
		</div>
		<!-- END FIND-PW SECTION -->

		<!-- START LOGOUT CONFIRM POPUP -->
		<div id="logout-popup" class="confirm-popup blind">
			<section class="confirm-txt-section">
				<span class="logout-txt">
					로그아웃 하시겠습니까?<br><br>
					로그아웃 시 메인페이지로 이동됩니다.
				</span>
			</section>
			<section class="confirm-btn-section">
				<div id="logout-btn" @click="do_logout" class="confirm-yesBtn">로그아웃</div>
				<div id="logout-closeBtn" @click="close_logout_popup" class="confirm-noBtn">닫기</div>
			</section>
		</div>

		<!-- !!! MY PAGE !!! -->
		<!-- START MODIFY-PW SECTION -->
		<div id="modify-pw-section" class="modify-section blind">
			<section class="modify-popup-title-section">
					<span>비밀번호 변경</span>
			</section>
			<section class="modify-popup-input-section">
					<div class="modify-popup-input-list">
							<div class="modify-popup-input-list-title">
									<span class="modify-guide-txt">현재 비밀번호</span>
									<span class="modify-error-txt blind">현재 비밀번호와 일치하지않습니다.</span>
							</div>
							<div class="modify-now-pw-check-section">
									<input type="password" @click="remove_null_input_border($event.target)" id="modify-pw-now" class="modify-popup-input-short" placeholder="현재 비밀번호를 입력하세요."/>
									<input type="button" @click="do_check_now_pw" id="check-now-pw" class="modify-popup-check-btn" value="확인"/>
							</div>
					</div>

					<div class="modify-popup-input-list">
							<div class="modify-popup-input-list-title">
									<span class="modify-guide-txt">새 비밀번호 확인</span>
									<span class="modify-error-txt blind">비밀번호 조건에 일치하지않습니다.</span>
							</div>
							<input type="password" @click="remove_null_input_border($event.target)" @keyup="check_modify_pw_regx($event.target)" @keydown="check_modify_pw_regx($event.target)" id="modify-pw-new" class="modify-popup-input" placeholder="비밀번호(영문+숫자+특수문자 = 8~10글자)"/>
					</div>

					<div>
							<div class="modify-popup-input-list-title">
									<span class="modify-guide-txt">새 비밀번호 확인</span>
									<span class="modify-error-txt blind">비밀번호가 일치하지않습니다.</span>
							</div>
							<input type="password" @click="remove_null_input_border($event.target)" @keyup="check_modify_pw_regx($event.target)" @keydown="check_modify_pw_regx($event.target)" id="modify-pw-renew" class="modify-popup-input" placeholder="변경할 비밀번호를 확인해 주세요."/>
					</div>
			</section>
			<section class="modify-popup-btn-section">
					<div id="modify-btn" class="modify-btn">수정</div>
					<div id="modify-close-btn" class="modify-btn" @click="close_modify_pw_popup">닫기</div>
			</section>
		</div>
		<!-- END MODIFY-PW SECTION -->

		<!-- START MODIFY PROFILE IMAGE -->
		<form action="/rence/user_img_updateOK" method="post" enctype="multipart/form-data">
			<div id="modify-img-section" class="confirm-popup blind">
				<section class="review-upload-section">
					<input type="text" class="review-upload-value" value="" readonly />
					<span class="review-upload-btn" @click="show_upload_file"> 이미지 등록 </span> 
					<input type="file" @change="select_user_img" class="file" name="multipartFile">
				</section>
				<section class="confirm-btn-section">
					<input type="submit" @submit="before_submit_modify_user_img" id="modify-img-modifyBtn" class="confirm-yesBtn" value="수정">
					<div id="modify-img-closeBtn" @click="close_modify_img_popup" class="confirm-noBtn" >닫기</div>
				</section>
			</div>
		</form>
		<!-- END MODIFY PROFILE IMAGE -->
	
		<!-- START USER_DELETE CUSTOM CONFIRM POPUP -->
		<div id ="user-delete-confirm-popup" class="confirm-popup blind">
				<section class="confirm-txt-section">
						<span class="common-confirm-txt">회원 탈퇴하시겠습니까?</span>
				</section>
				<section class="confirm-btn-section">
						<div id="user-delete" class="confirm-yesBtn" @click="do_user_delete">확인</div>
						<div id="user-delete-popup-close" class="confirm-noBtn" @click="close_user_delete_popup">닫기</div>
				</section>
		</div>

		<!-- !!! QUESTION PAGE !!! -->
		<!-- START Q-DELETE CONFIRM POPUP -->
		<div id ="q-delete-popup" class="confirm-popup blind">
				<section class="confirm-txt-section">
						<span class="logout-txt">
								해당 문의를 삭제하시겠습니까?
						</span>
				</section>
				<section class="confirm-btn-section">
						<div id="q-delete-btn" @click="do_q_delete" class="confirm-yesBtn">예</div>
						<div id="q-delete-closeBtn" @click="close_q_delete_popup" class="confirm-noBtn">닫기</div>
				</section>
		</div>
		
		<!-- START R-DELETE CONFIRM POPUP -->
		<div id="r-delete-popup" class="confirm-popup blind">
			<section class="confirm-txt-section">
				<span class="logout-txt"> 해당 후기를 삭제하시겠습니까? </span>
			</section>
			<section class="confirm-btn-section">
				<div id="r-delete-btn" @click="do_r_delete" class="confirm-yesBtn">예</div>
				<div id="r-delete-closeBtn" @click="close_r_delete_popup" class="confirm-noBtn">닫기</div>
			</section>
		</div>

    <!-- 현재 예약 리스트 예약 상세 정보 페이지 -->
		<!-- START RESERVE-CANCLE CONFIRM POPUP -->
    <div id ="reserve-cancel-popup" class="confirm-popup blind">
      <section class="confirm-txt-section">
        <span class="reserve-cancle-txt">
          예약을 정말로 취소하시겠습니까?<br><br>
          환불 규정에 따라 환불 될 예정입니다.
        </span>
      </section>
      <section class="confirm-btn-section">
        <div id="refund-btn" @click="do_refund" class="confirm-yesBtn">확인</div>
        <div id="refund-closeBtn" @click="close_refund_popup" class="confirm-noBtn">취소</div>
      </section>
    </div>
    <!-- END RESERVE-CANCLE CONFIRM POPUP -->
    
    <!-- START RESERVE-CANCLE CONFIRM POPUP -->
    <div id ="reserve-cancel-confirm-popup" class="confirm-popup blind">
        <section class="confirm-txt-section">
            <span class="reserve-cancle-txt">
                예약이 취소 되었습니다.
            </span>
        </section>
        <section class="confirm-btn-section">
            <div id="reserve-cancel-btn" @click="close_cancle_result_success_popup" class="confirm-yesBtn">확인</div>
        </section>
    </div>
    <!-- END RESERVE-CANCLE-CONFIRM CONFIRM POPUP -->
    
    <!-- START RESERVE-CANCLE FAIL POPUP -->
    <div id ="reserve-cancel-fail-popup" class="confirm-popup blind">
        <section class="confirm-txt-section">
            <span class="reserve-cancle-txt">
                예약 취소가 불가능합니다.
            </span>
        </section>
        <section class="confirm-btn-section">
            <div id="reserve-cancel-fail-btn" @click="close_cancle_result_fail_popup" class="confirm-yesBtn">확인</div>
        </section>
    </div>
    <!-- END RESERVE-CANCLE-FAIL CONFIRM POPUP -->

    <!-- START RESERVE-CANCLE CONFIRM POPUP -->
    <div id ="disconnect-session-popup" class="confirm-popup blind">
        <section class="confirm-txt-section">
            <span class="reserve-cancle-txt">
                세션이 만료되어 홈으로 이동합니다.
            </span>
        </section>
        <section class="confirm-btn-section">
          <router-link to="/">
            <div class="confirm-yesBtn">확인</div>
          </router-link>
        </section>
    </div>
    <!-- END RESERVE-CANCLE-CONFIRM CONFIRM POPUP -->
	</div>

	<div class="popup-background blind">
		<!-- START SPINNER SECTION -->
		<div id="spinner-section" class="blind">
			<img src="@/assets/IMG/common/spinner.gif" alt="로딩이미지" class="spinner-img">
		</div>
		<!-- END SPINNER SECTION -->

		<!-- START COMMON CUSTOM ALERT POPUP -->
		<div id="common-alert-popup" class="alert-popup blind">
			<section class="alert-txt-section">
				<span class="common-alert-txt"></span>
			</section>
			<section @click="common_alert()" id="common-alert-btn" class="alert-btn-section">
				<span>확인</span>
			</section>
		</div>
  </div>
</template>

<style lang="scss" scoped>
@import '@/assets/CSS/office/login.scss';
</style>

<script>
import axios from 'axios';
import $ from 'jquery';
import VueCookies from 'vue-cookies';

export default {
  name: 'PopupView',
  components: {
    VueCookies,
  },
  data() {
    return {
      // eslint-disable-next-line array-callback-return
      time: '',
      find_id_flag: true,
      find_pw_flag: true,
      join_flag: true,
      check_id_flag: true,
      check_email_flag: true,
      code_flag: true,
      user_delete_flag: true,
      modify_pw_flag: true,
      q_delete_flag: true,
      r_delete_flag: true,
      refund_flag: true,
    };
  },
  methods: {
    /** ********************************** */
    /** ************ COMMON ************** */
    /** ********************************** */
    /** 공용 알러트 버튼 클릭 이벤트 함수 */
    common_alert() {
      $('.popup-background:eq(1)').addClass('blind');
      $('#common-alert-popup').addClass('blind');

      if ($('#common-alert-btn').attr('is_reload') === 'true') {
        window.location.reload();
        $('#common-alert-btn').attr('is_reload', false);
      }
    },
    /** input NULL값 경고 테두리 클릭 시 제거하는 함수 */
    remove_null_input_border(param) {
      if ($(param).hasClass('null-input-border')) {
        $(param).removeClass('null-input-border');
      }
    },

    /** ********************************** */
    /** ************ LOGIN ************** */
    /** ********************************** */
    do_login() {
      // 로그인 시도
      if ($('#login-id').val().trim().length > 0 && $('#login-pw').val().trim().length > 0) {
        const params = new URLSearchParams();
        params.append('username', $('#login-id').val().trim());
        params.append('password', $('#login-pw').val().trim());

        axios.post('http://localhost:8800/rence/loginOK', params).then((res) => {
          console.log(res);
          // 로그인 성공
          if (res.data.result === '1') {
            window.location.reload();
          } else {
          // 로딩 화면 닫기
            $('#spinner-section').addClass('blind');

            $('.popup-background:eq(1)').removeClass('blind');
            $('#common-alert-popup').removeClass('blind');
            $('.common-alert-txt').text('로그인에 실패하였습니다.');
          }
        })
          .catch(() => {
          // 로딩 화면 닫기
            $('.popup-background:eq(1)').addClass('blind');
            $('#spinner-section').addClass('blind');

            $('.popup-background:eq(1)').removeClass('blind');
            $('#common-alert-popup').removeClass('blind');
            $('.common-alert-txt').text('오류 발생으로 인해 로그인 처리에 실패하였습니다.');
          });
      }
      // 로그인 불가
      else {
        if ($('#login-id').val().trim().length === 0) {
          $('#login-id').addClass('null-input-border');
        }
        if ($('#login-pw').val().trim().length === 0) {
          $('#login-pw').addClass('null-input-border');
        }
      }
    },
    /** 로그인 창닫기 찾기 버튼 클릭 이벤트 */
    close_login_popup() {
      // INPUT 초기화
      $('#login-id').val('');
      $('#login-pw').val('');

      $('#login-id').removeClass('null-input-border');
      $('#login-pw').removeClass('null-input-border');

      // 팝업 관련창 닫음
      $('#login-section').addClass('blind');
      $('.popup-background:eq(0)').addClass('blind');
    },

    /** ********************************** */
    /** ************ LOGOUT ************** */
    /** ********************************** */
    /** 로그아웃 팝업 창닫기 버튼 */
    close_logout_popup() {
      $('#logout-popup').addClass('blind');
      $('.popup-background:eq(0)').addClass('blind');
    },
    /** 로그아웃 처리 함수 */
    do_logout() {
      // 로딩 화면 닫기
      $('.popup-background:eq(1)').removeClass('blind');
      $('#spinner-section').removeClass('blind');

      axios.get('/rence/user_logoutOK')
        .then((res) => {
          // 로딩 화면 닫기
          $('.popup-background:eq(1)').addClass('blind');
          $('#spinner-section').addClass('blind');

          // 로그아웃 성공
          if (res.data.result === '1') {
            this.$cookies.remove('user_no');
            this.$cookies.remove('user_image');

            window.location.href = 'http://localhost:8081/';
          }
          // 로그아웃 실패
          else {
            // 로딩 화면 닫기
            $('.popup-background:eq(1)').addClass('blind');
            $('#spinner-section').addClass('blind');

            $('.popup-background:eq(1)').removeClass('blind');
            $('#common-alert-popup').removeClass('blind');
            $('.common-alert-txt').text('오류 발생으로 인해 로그아웃에 실패하였습니다');
          }
        })
        .catch(() => {
          // 로딩 화면 닫기
          $('.popup-background:eq(1)').addClass('blind');
          $('#spinner-section').addClass('blind');

          $('.popup-background:eq(1)').removeClass('blind');
          $('#common-alert-popup').removeClass('blind');
          $('.common-alert-txt').text('오류 발생으로 인해 처리에 실패하였습니다.');
        });
    },

    /** ********************************** */
    /** ********** FIND ID/PW ************ */
    /** ********************************** */
    /** 아이디/비밀번호 찾기 이메일 형식 체크 함수 */
    check_mail_regx_find(param) {
      // 이메일 형식인지 확인
      const email = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
      if (!email.test($(param).val().trim())) {
        $(param).next('.warning-text').removeClass('blind');
        $(param).next('.warning-text').text('이메일 형식이 아닙니다.');
      } else {
        $(param).next('.warning-text').addClass('blind');
      }
    },
    /** 비밀번호 찾기 아이디 형식 체크 함수 */
    check_id_regx_find(param) {
      // 아이디 형식에 맞는지 확인
      const idRegExp = /^[a-z]+[a-z0-9]{4,5}$/g;
      if (!idRegExp.test($(param).val().trim())) {
        $(param).next('.warning-text').removeClass('blind');
        $(param).next('.warning-text').text('아이디 형식이 아닙니다.');
      } else {
        $(param).next('.warning-text').addClass('blind');
      }
    },
    /** 아이디 찾기 버튼 클릭 이벤트 */
    go_find_id() {
      $('#login-section').addClass('blind');
      $('#find-id-section').removeClass('blind');
    },
    /** 비밀번호 찾기 버튼 클릭 이벤트 */
    go_find_pw() {
      $('#login-section').addClass('blind');
      $('#find-pw-section').removeClass('blind');
    },
    /** 아이디 찾기 팝업 창닫기 버튼 */
    close_find_id_popup() {
      // INPUT 초기화
      $('#find-id-email').val('');
      // 경고 테두리 초기화
      $('#find-id-email').removeClass('null-input-border');
      // 팝업 관련창 닫음
      $('#find-id-section').addClass('blind');
      $('.popup-background:eq(0)').addClass('blind');
    },
    /** 비밀번호 찾기 팝업 창닫기 버튼 */
    close_find_pw_popup() {
      // INPUT 초기화
      $('.find-popup-input').val('');
      // 경고 테두리 초기화
      $('.find-popup-input').removeClass('null-input-border');
      // 팝업 관련창 닫음
      $('#find-pw-section').addClass('blind');
      $('.popup-background:eq(0)').addClass('blind');
    },
    /** 아이디 찾기 함수 */
    do_find_id() {
      if (this.find_id_flag) {
        if ($('#find-id-email').val().trim().length > 0 && $('#find-id-email').next('.warning-text').hasClass('blind')) {
          // 로딩 화면
          $('.popup-background:eq(1)').removeClass('blind');
          $('#spinner-section').removeClass('blind');

          this.find_id_flag = false;

          const params = new URLSearchParams();
          params.append('user_email', $('#find-id-email').val().trim());

          axios.post('http://localhost:8800/rence/find_id', params)
            .then((res) => {
              console.log(res.data.result);
              this.find_id_flag = true;

              // 로딩 화면 닫기
              $('.popup-background:eq(1)').addClass('blind');
              $('#spinner-section').addClass('blind');

              // 아이디 찾기 성공
              if (res.data.result === '1') {
                // INPUT 초기화
                $('#find-id-email').val('');

                // 널 값 경고테두리 제거
                $('#find-id-email').removeClass('null-input-border');

                // 팝업 관련창 닫음
                $('#find-id-section').addClass('blind');
                $('.popup-background:eq(0)').addClass('blind');

                // 성공 알림창
                $('.popup-background:eq(1)').removeClass('blind');
                $('#common-alert-popup').removeClass('blind');
                $('.common-alert-txt').text('이메일로 아이디를 발송해드렸어요!');
              } else {
                // 아이디 찾기 실패
                $('.popup-background:eq(1)').removeClass('blind');
                $('#common-alert-popup').removeClass('blind');
                $('.common-alert-txt').text('해당 아이디로 가입된 회원이 없습니다.');
              }
            })
            .catch(() => {
              this.find_id_flag = true;

              // 로딩 화면 닫기
              $('.popup-background:eq(1)').addClass('blind');
              $('#spinner-section').addClass('blind');

              $('.popup-background:eq(1)').removeClass('blind');
              $('#common-alert-popup').removeClass('blind');
              $('.common-alert-txt').text('오류 발생으로 인해 처리에 실패하였습니다.');
            });
        } else {
          $('#find-id-email').addClass('null-input-border');
        }
      }
    },
    /** 비밀번호 찾기 함수 */
    do_find_pw() {
      if ($('#find-pw-email').val().trim().length > 0 && $('#find-pw-id').val().trim().length > 0) {
        if ($('#find-pw-email').next('.warning-text').hasClass('blind') && $('#find-pw-id').next('.warning-text').hasClass('blind')) {
          if (this.find_pw_flag) {
            // 로딩 화면
            $('.popup-background:eq(1)').removeClass('blind');
            $('#spinner-section').removeClass('blind');

            this.find_pw_flag = false;

            const params = new URLSearchParams();
            params.append('user_email', $('#find-pw-email').val().trim());
            params.append('user_id', $('#find-pw-id').val().trim());

            axios.post('http://localhost:8800/rence/find_pw', params)
              .then((res) => {
                this.find_pw_flag = true;

                // 로딩 화면 닫기
                $('.popup-background:eq(1)').addClass('blind');
                $('#spinner-section').addClass('blind');

                // 비밀번호 찾기 성공
                if (res.data.result === '1') {
                  // INPUT 초기화
                  $('.find-popup-input').val('');

                  // 경고 테두리 초기화
                  $('.find-popup-input').removeClass('null-input-border');

                  // 팝업 관련창 닫음
                  $('#find-pw-section').addClass('blind');
                  $('.popup-background:eq(0)').addClass('blind');

                  // 성공 알림창
                  $('.popup-background:eq(1)').removeClass('blind');
                  $('#common-alert-popup').removeClass('blind');
                  $('.common-alert-txt').text('이메일로 비밀번호를 발송해드렸어요!');
                } else {
                  // 아이디 찾기 실패
                  $('.popup-background:eq(1)').removeClass('blind');
                  $('#common-alert-popup').removeClass('blind');
                  $('.common-alert-txt').text('존재하지 않는 사용자입니다.');
                }
              })
              .catch(() => {
                this.find_pw_flag = true;

                // 로딩 화면 닫기
                $('.popup-background:eq(1)').addClass('blind');
                $('#spinner-section').addClass('blind');

                $('.popup-background:eq(1)').removeClass('blind');
                $('#common-alert-popup').removeClass('blind');
                $('.common-alert-txt').text('오류 발생으로 인해 처리에 실패하였습니다.');
              });
          }
        } else {
          if (!$('#find-pw-email').next('.warning-text').hasClass('blind')) {
            $('#find-pw-email').addClass('null-input-border');
          }
          if (!$('#find-pw-id').next('.warning-text').hasClass('blind')) {
            $('#find-pw-id').addClass('null-input-border');
          }
        }
      } else {
        if ($('#find-pw-email').val().trim().length === 0) {
          $('#find-pw-email').addClass('null-input-border');
        }
        if ($('#find-pw-id').val().trim().length === 0) {
          $('#find-pw-id').addClass('null-input-border');
        }
      }
    },

    /** ********************************** */
    /** ************* JOIN *************** */
    /** ********************************** */
    /** 회원가입 팝업 창닫기 버튼 */
    close_join_popup() {
      // INPUT 초기화
      $('.join-popup-input').val('');
      $('.join-popup-input-short').val('');
      $('.join-popup-input-short').removeClass('readOnly');
      $('.join-popup-input-short').attr('readonly', false);

      // 에러 메세지 초기화
      $('.warning-text').addClass('blind');

      // 경고 테두리 초기화
      $('.join-popup-input').removeClass('null-input-border');
      $('.join-popup-input-short').removeClass('null-input-border');

      // 버튼 초기화
      $('#check_id').prop('check', false);
      $('#check_id').val('중복확인');
      $('#check_email').prop('check', false);
      $('#check_email').val('인증하기');
      $('#check_email-code').prop('check', false);
      $('#check_email-code').val('확인');

      // 팝업 관련창 닫음
      $('#join-section').addClass('blind');
      $('.popup-background:eq(0)').addClass('blind');

      this.timer('close');
    },
    /** 회원가입 - 정규표현식을 이용해 입력한 값 조건에 맞는지 확인 */
    test_regx(param) {
      // 비밀번호 조건 확인
      if ($(param).attr('id') === 'join-pw') {
        const password = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,10}$/;
        if (!password.test($(param).val().trim())) {
          $('.warning-text:eq(3)').removeClass('blind');
          $('.warning-text:eq(3)').text('비밀번호 조건과 일치하지않습니다.');
        } else {
          $('.warning-text:eq(3)').addClass('blind');

          if ($('#join-re-pw').val().trim().length > 0) {
            if ($(param).val().trim() !== $('#join-re-pw').val().trim()) {
              $('.warning-text:eq(4)').removeClass('blind');
              $('.warning-text:eq(4)').text('위 비밀번호와 일치하지않습니다.');
            } else {
              $('.warning-text:eq(4)').addClass('blind');
            }
          }
        }
      } else if ($(param).attr('id') === 'join-re-pw') {
        // 비밀번호 조건 확인
        if ($(param).val().trim() !== $('#join-pw').val().trim()) {
          $('.warning-text:eq(4)').removeClass('blind');
          $('.warning-text:eq(4)').text('위 비밀번호와 일치하지않습니다.');
        } else {
          $('.warning-text:eq(4)').addClass('blind');
        }
      } else if ($(param).attr('id') === 'join-tel') {
        // 전화번호 형식인지 확인
        const phoneReg = /^01(0|1[6-9])(\d{3,4})(\d{4})$/;

        if (!phoneReg.test($(param).val().trim())) {
          $('.warning-text:eq(5)').removeClass('blind');
          $('.warning-text:eq(5)').text('전화번호 형식이 아닙니다.');
        } else {
          $('.warning-text:eq(5)').addClass('blind');
        }
      } else if ($(param).attr('id') === 'join-birth') {
        // 생년월일 형식인지 확인
        const birthReg = /^[0-9]{8}$/;
        if (!birthReg.test($(param).val().trim())) {
          $('.warning-text:eq(6)').removeClass('blind');
          $('.warning-text:eq(6)').text('지정된 생년월일 형식이 아닙니다.');
        } else {
          $('.warning-text:eq(6)').addClass('blind');
        }
      } else if ($(param).attr('id') === 'join-email') {
        // 이메일 형식인지 확인
        const email = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
        if (!email.test($(param).val().trim())) {
          $('.warning-text:eq(0)').removeClass('blind');
          $('.warning-text:eq(0)').text('이메일 형식이 아닙니다.');
        } else {
          $('.warning-text:eq(0)').addClass('blind');
        }
      } else if ($(param).attr('id') === 'join-id') {
        // 아이디 형식에 맞는지 확인
        const regExp = /^[a-z]+[a-z0-9]{4,5}$/g;
        if (!regExp.test($(param).val().trim())) {
          $('.warning-text:eq(2)').removeClass('blind');
          $('.warning-text:eq(2)').text('아이디 형식이 아닙니다.');
        } else {
          $('.warning-text:eq(2)').addClass('blind');
        }
      }
    },
    /** 아이디 중복여부 확인하는 로직 */
    do_check_id() {
      if ($('#check_id').prop('check') === true) {
        $('#check_id').prop('check', false);
        $('#join-id').attr('readonly', false);
        $('#join-id').removeClass('readOnly');
        $('#check_id').val('중복확인');
      } else if ($('#join-id').val().trim().length > 0) {
        if ($('.warning-text:eq(2)').hasClass('blind') || $('.warning-text:eq(0)').text() === '이미 존재하는 아이디입니다.') {
          if (this.check_id_flag) {
            // 로딩 화면
            $('.popup-background:eq(1)').removeClass('blind');
            $('#spinner-section').removeClass('blind');

            this.check_id_flag = false;

            const params = new URLSearchParams();
            params.append('user_id', $('#join-id').val().trim());

            axios.post('http://localhost:8800/rence/user_idCheckOK', params)
              .then((res) => {
                this.check_id_flag = true;

                // 로딩 화면 닫기
                $('.popup-background:eq(1)').addClass('blind');
                $('#spinner-section').addClass('blind');

                // 아이디 중복 성공
                if (res.data.result === '1') {
                  $('#check_id').prop('check', true);
                  $('#check_id').val('재입력');
                  $('#join-id').attr('readonly', true);
                  $('#join-id').addClass('readOnly');
                } else {
                  $('.warning-text:eq(2)').removeClass('blind');
                  $('.warning-text:eq(2)').text('이미 존재하는 아이디입니다.');
                }
              })
              .catch(() => {
                this.check_id_flag = true;

                // 로딩 화면 닫기
                $('.popup-background:eq(1)').addClass('blind');
                $('#spinner-section').addClass('blind');

                $('.popup-background:eq(1)').removeClass('blind');
                $('#common-alert-popup').removeClass('blind');
                $('.common-alert-txt').text('오류 발생으로 인해 처리에 실패하였습니다.');
              });
          }
        } else {
          $('.popup-background:eq(1)').removeClass('blind');
          $('#common-alert-popup').removeClass('blind');
          $('.common-alert-txt').text('조건에 맞는 아이디를 입력하신 후 중복체크 해주세요.');
        }
      } else {
        $('.popup-background:eq(1)').removeClass('blind');
        $('#common-alert-popup').removeClass('blind');
        $('.common-alert-txt').text('아이디를 입력하신 후 중복체크 해주세요.');
      }
    },
    /** 이메일 중복여부 확인하는 로직 * */
    do_check_email() {
      if ($('#check_email').prop('check') !== true) {
        if ($('#join-email').val().trim().length > 0) {
          if ($('.warning-text:eq(0)').hasClass('blind') || $('.warning-text:eq(0)').text() === '이미 존재하는 이메일입니다.') {
            if (this.check_email_flag) {
              this.check_email_flag = false;

              // 로딩 화면
              $('.popup-background:eq(1)').removeClass('blind');
              $('#spinner-section').removeClass('blind');

              const params = new URLSearchParams();
              params.append('user_email', $('#join-email').val().trim());

              axios.post('http://localhost:8800/rence/user_auth', params)
                .then((res) => {
                  this.check_email_flag = true;

                  // 로딩 화면 닫기
                  $('.popup-background:eq(1)').addClass('blind');
                  $('#spinner-section').addClass('blind');

                  // 이메일 중복 성공
                  if (res.data.authNum === '1') {
                    $('#check_email').prop('check', true);
                    $('#check_email').val('메일발송');

                    this.timer();

                    $('#join-email').attr('readonly', true);
                    $('#join-email').addClass('readOnly');

                    $('.popup-background:eq(1)').removeClass('blind');
                    $('#common-alert-popup').removeClass('blind');
                    $('.common-alert-txt').html('이메일로 인증번호를 발송하였습니다.<br> 2분 내로 인증번호 인증을 완료해주세요.<br> 2분 초과 시 이메일 재인증이 필요합니다!');
                  } else if (res.data.authNum === '2') {
                    $('.warning-text:eq(0)').removeClass('blind');
                    $('.warning-text:eq(0)').text('이미 존재하는 이메일입니다.');
                  } else if (res.data.authNum === '3') {
                    $('.popup-background:eq(1)').removeClass('blind');
                    $('#common-alert-popup').removeClass('blind');
                    $('.common-alert-txt').html('해당 이메일은 인증번호 발송 후<br> 2분이 되지 않았습니다.<br> 잠시만 기다려주세요!');
                  } else {
                    $('.popup-background:eq(1)').removeClass('blind');
                    $('#common-alert-popup').removeClass('blind');
                    $('.common-alert-txt').html('오류 발생으로 인해<br> 이메일 인증번호 발송에 실패하였습니다.');
                  }
                }).catch(() => {
                  this.check_email_flag = true;

                  // 로딩 화면 닫기
                  $('.popup-background:eq(1)').addClass('blind');
                  $('#spinner-section').addClass('blind');

                  $('.popup-background:eq(1)').removeClass('blind');
                  $('#common-alert-popup').removeClass('blind');
                  $('.common-alert-txt').html('오류 발생으로 인해<br> 처리에 실패하였습니다.');
                });
            }
          } else {
            $('.popup-background:eq(1)').removeClass('blind');
            $('#common-alert-popup').removeClass('blind');
            $('.common-alert-txt').text('조건에 맞는 이메일을 입력하신 후 중복체크 해주세요.');
          }
        } else {
          $('.popup-background:eq(1)').removeClass('blind');
          $('#common-alert-popup').removeClass('blind');
          $('.common-alert-txt').text('이메일을 입력하신 후 중복체크 해주세요.');
        }
      }
    },
    /** 이메일 인증번호 일치 여부 확인하는 함수 * */
    do_check_email_code() {
      if ($('#check_email-code').prop('check') !== true) {
        if ($('#join-email-code').val().trim().length > 0) {
          if (this.code_flag) {
					  this.code_flag = false;

            // 로딩 화면
            $('.popup-background:eq(1)').removeClass('blind');
            $('#spinner-section').removeClass('blind');

            const params = new URLSearchParams();
            params.append('user_email', $('#join-email').val().trim());
            params.append('email_code', $('#join-email-code').val().trim());

            axios.post('http://localhost:8800/rence/user_authOK', params)
              .then((res) => {
                console.log(res.data);
                this.code_flag = true;

                // 로딩 화면 닫기
                $('.popup-background:eq(1)').addClass('blind');
                $('#spinner-section').addClass('blind');

                // 이메일 인증번호 확인 성공
                if (res.data.result == '1') {
								  this.timer('true');
                  $('#check_email-code').prop('check', true);
                  $('#check_email-code').val('인증완료');
                  $('#join-email-code').attr('readonly', true);
                  $('#join-email-code').addClass('readOnly');
                } else {
                  $('.popup-background:eq(1)').removeClass('blind');
                  $('#common-alert-popup').removeClass('blind');
                  $('.common-alert-txt').text('알맞지 않은 인증번호입니다.');
                }
              }).catch(() => {
                this.code_flag = true;

                // 로딩 화면 닫기
                $('.popup-background:eq(1)').addClass('blind');
                $('#spinner-section').addClass('blind');

                $('.popup-background:eq(1)').removeClass('blind');
                $('#common-alert-popup').removeClass('blind');
                $('.common-alert-txt').text('오류 발생으로 인해 처리에 실패하였습니다.');
              });
          }
        }
      } else {
        $('.popup-background:eq(1)').removeClass('blind');
        $('#common-alert-popup').removeClass('blind');
        $('.common-alert-txt').text('인증번호 입력하신 후 시도해주세요.');
      }
    },
    /** 회원가입 로직 */
    do_join() {
      if (
        $('#join-email').val().trim().length > 0
        && $('#join-email-code').val().trim().length > 0
        && $('#join-id').val().trim().length > 0
        && $('#join-pw').val().trim().length > 0
        && $('#join-re-pw').val().trim().length > 0
        && $('#join-name').val().trim().length > 0
        && $('#join-tel').val().trim().length > 0
        && $('#join-birth').val().trim().length > 0) {
        const arr = $('.warning-text');
        let tmp = true;
        for (let i = 0; i < arr.length; i++) {
          if (!$(arr[i]).hasClass('blind')) {
            tmp = false;
            break;
          }
        }

        if (tmp === true) {
          if (this.join_flag) {
            if ($('#check_email').prop('check') === true) {
              if ($('#check_email-code').prop('check') === true) {
                if ($('#check_id').prop('check') === true) {
                  // 로딩 화면
                  $('.popup-background:eq(1)').removeClass('blind');
                  $('#spinner-section').removeClass('blind');

                  const params = new URLSearchParams();
                  params.append('user_id', $('#join-id').val().trim());
                  params.append('user_pw', $('#join-pw').val().trim());
                  params.append('user_email', $('#join-email').val().trim());
                  params.append('user_name', $('#join-name').val().trim());
                  params.append('user_tel', $('#join-tel').val().trim());
                  params.append('user_birth', $('#join-birth').val().trim());

                  this.join_flag = false;

                  axios.post('http://localhost:8800/rence/joinOK', params)
                    .then((res) => {
                      this.join_flag = true;

                      // 로딩 화면
                      $('.popup-background:eq(1)').addClass('blind');
                      $('#spinner-section').addClass('blind');

                      // 회원가입 성공
                      if (res.data.result === '1') {
                      // INPUT 초기화
                        $('.join-popup-input').val('');
                        $('.join-popup-input-short').val('');
                        $('.join-popup-input-short').removeClass('readOnly');
                        $('.join-popup-input-short').attr('readonly', false);

                        // 에러 메세지 초기화
                        $('.warning-text').addClass('blind');

                        // 경고 테두리 초기화
                        $('.join-popup-input').removeClass('null-input-border');
                        $('.join-popup-input-short').removeClass('null-input-border');

                        // 버튼 초기화
                        $('#check_id').prop('check', undefined);
                        $('#check_id').val('중복확인');
                        $('#check_email').prop('check', undefined);
                        $('#check_email').val('인증하기');
                        $('#check_email-code').prop('check', undefined);
                        $('#check_email-code').val('확인');

                        // 팝업 관련창 닫음
                        $('#join-section').addClass('blind');
                        $('.popup-background:eq(0)').addClass('blind');

                        // 성공 알림창
                        $('.popup-background:eq(1)').removeClass('blind');
                        $('#common-alert-popup').removeClass('blind');
                        $('.common-alert-txt').text('회원가입에 성공하였습니다.');
                      } else {
                        $('.popup-background:eq(1)').removeClass('blind');
                        $('#common-alert-popup').removeClass('blind');
                        $('.common-alert-txt').text('회원가입에 실패하였습니다.');
                      }
                    }).catch(() => {
                      this.join_flag = true;

                      // 로딩 화면
                      $('.popup-background:eq(1)').addClass('blind');
                      $('#spinner-section').addClass('blind');

                      $('.popup-background:eq(1)').removeClass('blind');
                      $('#common-alert-popup').removeClass('blind');
                      $('.common-alert-txt').text('오류 발생으로 인해 회원가입 처리에 실패하였습니다.');
                    });
                } else {
                  $('.popup-background:eq(1)').removeClass('blind');
                  $('#common-alert-popup').removeClass('blind');
                  $('.common-alert-txt').text('아이디 중복체크를 완료하세요.');
                }
              } else {
                $('.popup-background:eq(1)').removeClass('blind');
                $('#common-alert-popup').removeClass('blind');
                $('.common-alert-txt').text('인증번호 확인을 완료하세요.');
              }
            } else {
              $('.popup-background:eq(1)').removeClass('blind');
              $('#common-alert-popup').removeClass('blind');
              $('.common-alert-txt').text('이메일 인증을 완료하세요.');
            }
          }
        } else {
          if ($('#join-email').val().trim().length == 0) {
            $('#join-email').addClass('null-input-border');
          }
          if ($('#join-email-code').val().trim().length == 0) {
            $('#join-email-code').addClass('null-input-border');
          }
          if ($('#join-id').val().trim().length == 0) {
            $('#join-id').addClass('null-input-border');
          }
          if ($('#join-pw').val().trim().length == 0) {
            $('#join-pw').addClass('null-input-border');
          }
          if ($('#join-re-pw').val().trim().length == 0) {
            $('#join-re-pw').addClass('null-input-border');
          }
          if ($('#join-name').val().trim().length == 0) {
            $('#join-name').addClass('null-input-border');
          }
          if ($('#join-tel').val().trim().length == 0) {
            $('#join-tel').addClass('null-input-border');
          }
          if ($('#join-birth').val().trim().length == 0) {
            $('#join-birth').addClass('null-input-border');
          }
        }
      }
    },
    /** 이메일 인증 번호 입력 시간 제한 타이머 */
    timer(check) {
      let minute = 1;
      let seconds = 60;

      if (check === 'true') {
        clearInterval(this.time);
        $('#check_email').val('인증완료');
        return;
      }

      if (check === 'close') {
        clearInterval(this.time);
        return;
      }

      this.time = setInterval(() => {
        seconds--;

        if (seconds <= 9) $('#check_email').val(`0${minute} : ` + `0${seconds}`);
        else $('#check_email').val(`0${minute} : ${seconds}`);

        if (seconds == 0) {
          if (minute != 0) {
            --minute;
            seconds = 60;
          } else {
            $('.popup-background:eq(1)').removeClass('blind');
            $('#common-alert-popup').removeClass('blind');
            $('.common-alert-txt').html('이메일 인증 시간을 초과했습니다.<br>다시 시도해주세요.');

            $('#check_email').prop('check', false);
            $('#check_email').val('인증하기');
            $('#join-email').val('');
            $('#join-email').attr('readonly', false);
            $('#join-email').removeClass('readOnly');

            $('#check_email-code').prop('check', false);
            $('#check_email-code').val('확인');
            $('#join-email-code').val('');
            $('#join-email-code').attr('readonly', false);
            $('#join-email-code').removeClass('readOnly');

            clearInterval(this.time);
          }
        }
      }, 1000);
    },

    /** ********************************** */
    /** ************ MYPAGE ************** */
    /** ********************************** */
    /** 회원 탈퇴 팝업 창닫기 버튼 */
    close_user_delete_popup() {
      $('.popup-background:eq(0)').addClass('blind');
      $('#user-delete-confirm-popup').addClass('blind');
    },
    /** 회원 삭제 AXIOS */
    do_user_delete() {
      if (this.user_delete_flag) {
        // 로딩 화면
        $('.popup-background:eq(1)').removeClass('blind');
        $('#spinner-section').removeClass('blind');

        this.user_delete_flag = false;

        const params = new URLSearchParams();
        params.append('user_no', this.$cookies.get('user_no'));

        axios.post('http://localhost:8800/rence/secedeOK', params)
          .then((res) => {
            this.user_delete_flag = true;

            // 로딩 화면 닫기
            $('.popup-background:eq(1)').addClass('blind');
            $('#spinner-section').addClass('blind');

            // 회원탈퇴 성공
            if (res.data.result === '1') {
              this.$cookies.remove('user_no');
              this.$cookies.remove('user_image');

              // 성공 알림창
              $('.popup-background:eq(1)').removeClass('blind');
              $('#common-alert-popup').removeClass('blind');
              $('.common-alert-txt').text('회원탈퇴되었습니다.');
              $('#common-alert-btn').attr('is_reload', true);
            } else {
              $('.popup-background:eq(1)').removeClass('blind');
              $('#common-alert-popup').removeClass('blind');
              $('.common-alert-txt').text('예상치못한 오류로 회원탈퇴에 실패하였습니다.');
            }
          })
          .catch(() => {
            this.find_id_flag = true;

            // 로딩 화면 닫기
            $('.popup-background:eq(1)').addClass('blind');
            $('#spinner-section').addClass('blind');

            $('.popup-background:eq(1)').removeClass('blind');
            $('#common-alert-popup').removeClass('blind');
            $('.common-alert-txt').text('오류 발생으로 인해 처리에 실패하였습니다.');
          });
      }
    },
    /** 비밀번호 팝업 닫기 처리 함수 */
    close_modify_pw_popup() {
      // INPUT 초기화
      $('.modify-popup-input').val('');
      $('.modify-popup-input-short').val('');

      // 경고 테두리 초기화
      $('.modify-popup-input-short').removeClass('null-input-border');
      $('.modify-popup-input').removeClass('null-input-border');

      // 에러 메세지 초기화
      $('.modify-error-txt').addClass('blind');

      // 팝업 관련창 닫음
      $('#modify-pw-section').addClass('blind');
      $('.popup-background:eq(0)').addClass('blind');
    },
    /** 비밀번호 수정 팝업 닫기 * */
    close_modify_img_popup() {
      $('.review-upload-value').removeClass('null-input-border');
      $('.review-upload-value').val('');
      $('.popup-background:eq(0)').addClass('blind');
      $('#modify-img-section').addClass('blind');
    },
    /** 프로필 이미지 버튼 클릭 -> 파일선택 SHOW */
    show_upload_file() {
      $('.file').click();
    },
    /** 파일선택 시 처리 로직 * */
    select_user_img() {
      const fileName = $('.file').val();
      const fArr = fileName.split('\\');

      $('.review-upload-value').removeClass('null-input-border');
      $('.review-upload-value').val(fArr[fArr.length - 1]);
    },
    // 이미지 수정 제출 전 필수 조건 확인
    before_submit_modify_user_img() {
      // 파일 선택안하고 시도
      if ($('.review-upload-value').val().length === 0) {
        $('.review-upload-value').addClass('null-input-border');
        return false;
      }
    },
    /** 비밀번호 수정 - 정규표현식을 이용해 입력한 값 조건에 맞는지 확인 * */
    check_modify_pw_regx(param) {
      if ($('#check-now-pw').prop('check')) {
        if ($(param).attr('id') === 'modify-pw-new') {
        // 새 비밀번호가 비밀번호 생성 조건에 맞는지
          const password = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,10}$/;
          if (!password.test($(param).val().trim())) {
            $('.modify-error-txt:eq(1)').removeClass('blind');
            $('.modify-error-txt:eq(1)').text('비밀번호 조건과 일치하지않습니다.');
          } else {
            $('.modify-error-txt:eq(1)').addClass('blind');

            // 새 비밀번호가 현재 비밀번호와 다른지
            if ($(param).val().trim() === $('#modify-pw-now').val().trim()) {
              $('.modify-error-txt:eq(1)').removeClass('blind');
              $('.modify-error-txt:eq(1)').text('현재 비밀번호와 다른 비밀번호를 사용하세요.');
            } else {
              $('.modify-error-txt:eq(1)').addClass('blind');

              if ($('#modify-pw-renew').val().trim().length > 0) {
                if ($(param).val().trim() !== $('#modify-pw-renew').val().trim()) {
                  $('.modify-error-txt:eq(2)').removeClass('blind');
                  $('.modify-error-txt:eq(2)').text('위 비밀번호와 일치하지않습니다.');
                } else {
                  $('.modify-error-txt:eq(2)').addClass('blind');
                }
              }
            }
          }
        } else if ($(param).attr('id') === 'modify-pw-renew') {
          if ($(param).val().trim() !== $('#modify-pw-new').val().trim()) {
            $('.modify-error-txt:eq(2)').removeClass('blind');
            $('.modify-error-txt:eq(2)').text('위 비밀번호와 일치하지않습니다.');
          } else {
            $('.modify-error-txt:eq(2)').addClass('blind');
          }
        }
      } else {
        $('.modify-popup-input').val('');
        $('.modify-error-txt:eq(1)').removeClass('blind');
        $('.modify-error-txt:eq(1)').text('현재 비밀번호 먼저 확인해주세요.');
      }
    },
    /** 현재 비밀번호 일치여부 확인하는 로직 * */
    do_check_now_pw() {
      if ($('#modify-pw-now').val().trim().length > 0) {
        if ($('.modify-error-txt:eq(0)').hasClass('blind')) {
          // 로딩 화면
          $('.popup-background:eq(1)').removeClass('blind');
          $('#spinner-section').removeClass('blind');

          const params = new URLSearchParams();
          params.append('user_no', this.$cookies.get('user_no'));
          params.append('user_pw', $('#modify-pw-now').val().trim());

          axios.post('http://localhost:8800/rence/check_now_pw', params)
            .then((res) => {
            // 로딩 화면 닫기
              $('.popup-background:eq(1)').addClass('blind');
              $('#spinner-section').addClass('blind');

              // 비밀번호 일치 성공
              if (res.data.result === '1') {
                $('#check-now-pw').prop('check', true);
                $('#modify-pw-now').attr('readonly', true);
                $('#modify-pw-now').addClass('readOnly');
              } else {
                $('.popup-background:eq(1)').removeClass('blind');
                $('#common-alert-popup').removeClass('blind');
                $('.common-alert-txt').text('비밀번호가 일치하지않습니다.');
              }
            }).catch(() => {
            // 로딩 화면 닫기
              $('.popup-background:eq(1)').addClass('blind');
              $('#spinner-section').addClass('blind');

              $('.popup-background:eq(1)').removeClass('blind');
              $('#common-alert-popup').removeClass('blind');
              $('.common-alert-txt').text('오류 발생으로 인해 처리에 실패하였습니다.');
            });
        }
      } else {
        $('#modify-pw-now').addClass('null-input-border');
      }
    },
    /** 비밀번호 수정 로직 * */
    do_modify_pw() {
      if (this.modify_pw_flag) {
        if ($('#modify-pw-now').val().trim().length > 0 && $('#modify-pw-new').val().trim().length > 0 && $('#modify-pw-renew').val().trim().length > 0) {
          if ($('.modify-error-txt:eq(0)').hasClass('blind')
              && $('.modify-error-txt:eq(1)').hasClass('blind')
              && $('.modify-error-txt:eq(2)').hasClass('blind')
              && !$('.modify-popup-input-short').hasClass('null-input-border')
              && !$('.modify-popup-input:eq(0)').hasClass('null-input-border')
              && !$('.modify-popup-input:eq(1)').hasClass('null-input-border')) {
            // 로딩 화면
            $('.popup-background:eq(1)').removeClass('blind');
            $('#spinner-section').removeClass('blind');

            this.modify_pw_flag = false;

            const params = new URLSearchParams();
            params.append('user_no', this.$cookies.get('user_no'));
            params.append('user_pw', $('#modify-pw-renew').val().trim());

            axios.post('http://localhost:8800/rence/user_pw_updateOK', params)
              .then((res) => {
                this.modify_pw_flag = true;

                // 로딩 화면 닫기
                $('.popup-background:eq(1)').addClass('blind');
                $('#spinner-section').addClass('blind');

                // 비밀번호 변경 성공
                if (res.data.result === '1') {
                // INPUT 초기화
                  $('.modify-popup-input').val('');
                  $('.modify-popup-input-short').val('');

                  // 경고 테두리 초기화
                  $('.modify-popup-input-short').removeClass('null-input-border');
                  $('.modify-popup-input').removeClass('null-input-border');

                  // 성공 알림창
                  $('.popup-background:eq(1)').removeClass('blind');
                  $('#common-alert-popup').removeClass('blind');
                  $('.common-alert-txt').text('비밀번호가 변경되었습니다.');
                } else {
                  $('.popup-background:eq(1)').removeClass('blind');
                  $('#common-alert-popup').removeClass('blind');
                  $('.common-alert-txt').text('예상치못한 오류로 비밀번호 변경에 실패하였습니다.');
                }
              }).catch(() => {
                this.modify_pw_flag = true;

                // 로딩 화면 닫기
                $('.popup-background:eq(1)').addClass('blind');
                $('#spinner-section').addClass('blind');

                $('.popup-background:eq(1)').removeClass('blind');
                $('#common-alert-popup').removeClass('blind');
                $('.common-alert-txt').text('오류 발생으로 인해 처리에 실패하였습니다.');
              });
          }
        } else {
          $('#modify-pw-now').addClass('null-input-border');
        }
      }
    },

    /** ********************************** */
    /** **** RESERVE DETAIL INFO PAGE **** */
    /** ********************************** */
    /** 환불 여부 묻는 팝업 닫기 */
    close_refund_popup() {
      $('#reserve-cancel-popup').addClass('blind');
		  $('.popup-background:eq(0)').addClass('blind');
    },
    /** 예약 취소 성공에 대한 결과 팝업 닫기 */
    close_cancle_result_success_popup() {
      $('#reserve-cancel-confirm-popup').addClass('blind');
		 $('.popup-background:eq(0)').addClass('blind');
    },
    /** 예약 취소 실패에 대한 결과 팝업 닫기 */
    close_cancle_result_fail_popup() {
      $('#reserve-cancel-fail-popup').addClass('blind');
		 $('.popup-background:eq(0)').addClass('blind');
    },
    /** 환불 처리 로직 */
    do_refund() {
      if (this.refund_flag) {
        this.refund_flag = false;
        const reserve_no = window.location.href.split('reserve_no=')[1];
        const user_no = this.$cookies.get('user_no');
        const actual_payment = parseInt($('#actual_payment').attr('actual_payment').replace(',', ''));

        // 로딩 페이지 오픈
        $('.popup-background:eq(1)').removeClass('blind');
        $('#spinner-section').removeClass('blind');

        const params = new URLSearchParams();
        params.append('reserve_no', reserve_no);
        params.append('user_no', user_no);

        axios.get('http://localhost:8800/rence/reserve_cancel', params)
          .then((res) => {
            if (res.data.result === '1') {
              const reserve_no = location.href.split('reserve_no=')[1].split('&')[0];
              const cancel_amount = parseInt($('#actual_payment').attr('actual_payment').replace(',', ''));

              const params = new URLSearchParams();
              params.append('reserve_no', reserve_no);
              params.append('cancel_amount', cancel_amount);
              params.append('reason', '예약 취소로 인한 결제 환불');

              axios.get('http://localhost:8800/rence/payment_cancel', params)
                .then((res) => {
                  this.refund_flag = true;
                  // 로딩 화면 닫기
                  $('.popup-background:eq(1)').addClass('blind');
                  $('#spinner-section').addClass('blind');

                  if (res.data.result === '1') {
                    $('#reserve-cancel-popup').addClass('blind');
                    $('#reserve-cancel-confirm-popup').removeClass('blind');

                    location.href = 'http://localhost:8081/reserve_list';
                  }
                })
                .catch(() => {
                  this.refund_flag = true;

                  // 로딩 화면 닫기
                  $('.popup-background:eq(1)').addClass('blind');
                  $('#spinner-section').addClass('blind');

                  $('.popup-background:eq(1)').removeClass('blind');
                  $('#common-alert-popup').removeClass('blind');
                  $('.common-alert-txt').text('오류 발생으로 인해 처리에 실패하였습니다.');
                });
            } else {
              $('#reserve-cancel-popup').addClass('blind');
              $('#reserve-cancel-fail-popup').removeClass('blind');
            }
          }).catch(() => {
            this.refund_flag = true;

            // 로딩 화면 닫기
            $('.popup-background:eq(1)').addClass('blind');
            $('#spinner-section').addClass('blind');

            $('.popup-background:eq(1)').removeClass('blind');
            $('#common-alert-popup').removeClass('blind');
            $('.common-alert-txt').text('오류 발생으로 인해 처리에 실패하였습니다.');
          });
      }
    },
    /** ********************************** */
    /** ******** MYPAGE - REVIEW ********* */
    /** ********************************** */
    /** 후기 삭제 팝업 - "아니오" 클릭 시, 발생하는 이벤트 */
    close_r_delete_popup() {
      $('#r-delete-btn').attr('idx', undefined);
      $('#r-delete-popup').addClass('blind');
      $('.popup-background:eq(0)').addClass('blind');
    },
    /** 후기 삭제 로직 */
    do_r_delete() {
      if (this.r_delete_flag) {
        this.r_delete_flag = false;

        $('.popup-background:eq(0)').addClass('blind');
        $('#r-delete-popup').addClass('blind');
        $('#r-delete-btn').attr('idx', undefined);

        $('.popup-background:eq(1)').removeClass('blind');
        $('#spinner-section').removeClass('blind');

        axios.get(`http://localhost:8800/rence/delete_review?user_no=${this.$cookies.get('user_no')}&review_no=${$('#r-delete-btn').attr('idx')}`)
          .then((res) => {
            this.r_delete_flag = true;

            // 로딩 화면 닫기
            $('.popup-background:eq(1)').addClass('blind');
            $('#spinner-section').addClass('blind');

            if (res.data.result === '1') {
              $('.popup-background:eq(1)').removeClass('blind');
              $('#common-alert-popup').removeClass('blind');
              $('.common-alert-txt').text('후기가 삭제되었습니다.');
              $('#common-alert-btn').attr('is_reload', true);
            } else {
              $('.popup-background:eq(1)').removeClass('blind');
              $('#common-alert-popup').removeClass('blind');
              $('.common-alert-txt').text('후기 삭제에 실패하였습니다.');
            }
          })

          .catch(() => {
            this.r_delete_flag = true;

            // 로딩 화면 닫기
            $('.popup-background:eq(1)').addClass('blind');
            $('#spinner-section').addClass('blind');

            $('.popup-background:eq(1)').removeClass('blind');
            $('#common-alert-popup').removeClass('blind');
            $('.common-alert-txt').text('오류 발생으로 인해 처리에 실패하였습니다.');
          });
      }
    },

    /** ********************************** */
    /** ******** MYPAGE - QUESTION ******* */
    /** ********************************** */
    /** 댓글 컨펌 팝업 - 창닫기 버튼 */
    close_q_delete_popup() {
      $('#r-delete-popup').addClass('blind');
      $('.popup-background:eq(0)').addClass('blind');
    },
    /** 문의 삭제 로직 */
    do_q_delete() {
      if (this.q_delete_flag) {
        this.q_delete_flag = false;

        $('.popup-background:eq(0)').addClass('blind');
        $('#q-delete-popup').addClass('blind');
        $('#q-delete-btn').attr('idx', undefined);

        $('.popup-background:eq(1)').removeClass('blind');
        $('#spinner-section').removeClass('blind');

        axios.get(`http://localhost:8800/rence/delete_comment?user_no=${this.$cookies.get('user_no')}&comment_no=${$('#q-delete-btn').attr('idx')}`)
          .then((res) => {
            this.q_delete_flag = true;

            // 로딩 화면 닫기
            $('.popup-background:eq(1)').addClass('blind');
            $('#spinner-section').addClass('blind');

            if (res.data.result === '1') {
              $('.popup-background:eq(1)').removeClass('blind');
              $('#common-alert-popup').removeClass('blind');
              $('.common-alert-txt').text('댓글이 삭제되었습니다.');
              $('#common-alert-btn').attr('is_reload', true);
            } else {
              $('.popup-background:eq(1)').removeClass('blind');
              $('#common-alert-popup').removeClass('blind');
              $('.common-alert-txt').text('댓글 삭제에 실패하였습니다.');
            }
          }).catch(() => {
            this.q_delete_flag = true;

            // 로딩 화면 닫기
            $('.popup-background:eq(1)').addClass('blind');
            $('#spinner-section').addClass('blind');

            $('.popup-background:eq(1)').removeClass('blind');
            $('#common-alert-popup').removeClass('blind');
            $('.common-alert-txt').text('오류 발생으로 인해 처리에 실패하였습니다.');
          });
      }
    },
  }, // END methods()
};
</script>
