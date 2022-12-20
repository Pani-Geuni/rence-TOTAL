<!-- eslint-disable vuejs-accessibility/click-events-have-key-events -->
<!-- eslint-disable vuejs-accessibility/form-control-has-label -->
<!-- eslint-disable max-len -->
<template>
  <div class="popup-background blind">

    <!-- START LOGIN SECTION -->
    <div id="login-section" class="blind">
      <section class="login-popup-logo-section">
        <img src="@/assets/IMG/common/RENCE-Host.svg" alt="Host Logo" class="popup-logo host-login-popup-logo">
      </section>
      <section class="login-popup-input-section">
        <input @click="clickLoginInput" type="text" id="login-id" class="login-popup-input" name="username"
          placeholder="사업자 등록 번호를 입력하세요." autocomplete="off" />
        <input @click="clickLoginInput" type="password" id="login-pw" class="login-popup-input" name="password"
          placeholder="비밀번호를 입력하세요." />
      </section>
      <section class="login-popup-btn-section">
        <input @click="backofficeLogin" type="button" id="login-btn" value="로그인">
        <div class="txt-btn-wrap">
          <span @click="goFindPw" id="go-find-pw" class="txt-btn">비밀번호 찾기</span>
          <span @click="closeLoginPopup" id="host-login-close" class="txt-btn login-close">창닫기</span>
        </div>
      </section>
    </div>
    <!-- END LOGIN SECTION -->

    <!-- START FIND-PW SECTION -->
    <div id="find-pw-section" class="find-section blind">
      <section class="find-popup-logo-section">
        <span>비밀번호 찾기</span>
      </section>
      <section class="find-popup-input-section">
        <div class="email-wrap">
          <span class="find-guide-txt">사업자 이메일을 입력해주세요.</span>
          <input @click="clickFindInput" type="email" id="find-pw-email" class="find-popup-input"
            placeholder="이메일을 입력하세요." />
        </div>
        <div>
          <span class="find-guide-txt">사업자 등록 번호를 입력해 주세요.</span>
          <input @click="clickFindInput" type="text" id="find-pw-backoffice-code" class="find-popup-input"
            placeholder="사업자 등록 번호를 입력하세요." />
        </div>
      </section>
      <section class="find-popup-btn-section">
        <input @click="findPassword" type="button" id="find-pw-btn" class="find-btn" value="비밀번호 찾기" />
        <input @click="closeFindPw" type="button" id="find-pw-close" class="find-pw-close" value="창닫기" />
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
        <div @click="logout" id="logout-btn" class="confirm-yesBtn">로그아웃</div>
        <div @click="closeLogoutPopup" id="logout-closeBtn" class="confirm-noBtn">닫기</div>
      </section>
    </div>
    <!-- END logout popup -->

    <!-- START FAIL CUSTOM ALERT POPUP -->
    <div id="fail-alert-popup" class="alert-popup blind">
      <section class="alert-txt-section">
        <span>올바른 정보를 입력해 주세요.</span>
      </section>
      <section @click="closeFailAlertPopup" id="fail-alert-btn" class="alert-btn-section">
        <span>확인</span>
      </section>
    </div>
  </div>

  <div class="popup-background blind">
    <!-- START SPINNER SECTION -->
    <div id="spinner-section" class="blind">
      <img src="@/assets/IMG/common/spinner.gif" alt="spinner" class="spinner-img">
    </div>
    <!-- END SPINNER SECTION -->

    <!-- START COMMON CUSTOM ALERT POPUP -->
    <div id="common-alert-popup" class="alert-popup blind">
      <section class="alert-txt-section">
        <span class="common-alert-txt"></span>
      </section>
      <section @click="closeCommonAlert" id="common-alert-btn" class="alert-btn-section">
        <span>확인</span>
      </section>
    </div>
  </div>
</template>

<style scoped>
@import '@/assets/CSS/backoffice/host-login.scss';
</style>

<script>
import $ from 'jquery';
import axios from 'axios';

export default {
  name: 'PopupView',
  data() {
    return {
      backoffice_no: this.$cookies.get('backoffice_no'),
      host_image: this.$cookies.get('host_image'),
      find_pw_flag: true,
    };
  },

  methods: {
    /** 공용 알러트 창닫기 버튼 */
    closeCommonAlert() {
      $('.popup-background:eq(1)').addClass('blind');
      $('#common-alert-popup').addClass('blind');

      if ($(this).attr('is_reload') === 'true') {
        this.$router.go(0);
        $(this).attr('is_reload', false);
      } else if ($(this).attr('is_reload') === 'logout') {
        $(this).attr('is_reload', false);
        this.$router.replace('/backoffice/logout');
      }
    },

    /* **************************** */
    /* ********* 로그인 팝업 ********* */
    /* **************************** */

    clickLoginInput() {
      if ($(this).hasClass('null-input-border')) {
        $(this).removeClass('null-input-border');
      }
    },

    backofficeLogin() {
      if ($('#login-id').val().trim().length === 0) {
        $('#login-id').addClass('null-input-border');
      }
      if ($('#login-pw').val().trim().length === 0) {
        $('#login-pw').addClass('null-input-border');
      }

      if ($('#login-id').val().trim().length > 0 && $('#login-pw').val().trim().length > 0) {
        // 로딩 화면
        $('.popup-background:eq(1)').removeClass('blind');
        $('#spinner-section').removeClass('blind');

        console.log('id :', $('#login-id').val().trim());
        console.log('pw :', $('#login-pw').val().trim());

        const params = new URLSearchParams();
        params.append('username', $('#login-id').val().trim());
        params.append('password', $('#login-pw').val().trim());

        axios.post('http://localhost:8800/backoffice/loginOK', params)
          .then((res) => {
            if (res.data.result === '1') {
              console.log('res.data : ', res.data);
              console.log(this.backoffice_no);
              this.$cookies.set('backoffice_no', res.data.backoffice_no);
              this.$cookies.set('host_image', res.data.host_image);
              this.$storage.setStorageSync('backoffice_id', res.data.backoffice_id);

              this.$router.push(`/backoffice/dash/main?backoffice_no=${this.$cookies.get('backoffice_no')}`);
            } else {
              // 로딩 화면 닫기
              $('.popup-background:eq(1)').addClass('blind');
              $('#spinner-section').addClass('blind');

              $('.popup-background:eq(1)').removeClass('blind');
              $('#common-alert-popup').removeClass('blind');
              $('.common-alert-txt').text('로그인에 실패하였습니다.');
            }
          })
          .catch((e) => {
            console.log(e);

            // 로딩 화면 닫기
            $('.popup-background:eq(1)').addClass('blind');
            $('#spinner-section').addClass('blind');

            $('.popup-background:eq(1)').removeClass('blind');
            $('#common-alert-popup').removeClass('blind');
            $('.common-alert-txt').text('오류 발생으로 인해 처리에 실패하였습니다.');
          });
      }
    },

    /** 로그인 팝업 창닫기 */
    closeLoginPopup() {
      // INPUT 초기화
      $('#login-id').val('');
      $('#login-pw').val('');

      $('#login-id').removeClass('null-input-border');
      $('#login-pw').removeClass('null-input-border');

      // 로딩 화면 닫기
      $('.popup-background:eq(1)').addClass('blind');
      $('#spinner-section').addClass('blind');

      // 팝업 관련창 닫음
      $('.popup-background:eq(0)').addClass('blind');
      $('#login-section').addClass('blind');
    },

    /** 비밀번호 찾기 버튼 클릭 */
    goFindPw() {
      $('#login-section').addClass('blind');
      $('#find-pw-section').removeClass('blind');
    },

    // 비밀번호 찾기 인풋 클릭 시 경고 테두리 제거
    clickFindInput() {
      if ($(this).hasClass('null-input-border')) {
        $(this).removeClass('null-input-border');
      }
    },

    findPassword() {
      if ($('#find-pw-email').val().trim().length === 0) {
        $('#find-pw-email').addClass('null-input-border');
      }
      if ($('#find-pw-backoffice-code').val().trim().length === 0) {
        $('#find-pw-backoffice-code').addClass('null-input-border');
      }

      if ($('#find-pw-email').val().trim().length > 0 && $('#find-pw-backoffice-code').val().trim().length > 0) {
        if (this.find_pw_flag) {
          this.find_pw_flag = false;
          console.log('backoffice_id :', $('#find-pw-backoffice-code').val().trim());
          console.log('backoffice_email :', $('#find-pw-email').val().trim());

          // 로딩 화면
          $('.popup-background:eq(1)').removeClass('blind');
          $('#spinner-section').removeClass('blind');

          const params = new URLSearchParams();
          params.append('backoffice_id', $('#find-pw-backoffice-code').val().trim());
          params.append('backoffice_email', $('#find-pw-email').val().trim());

          const url = `http://localhost:8800/backoffice/reset_pw?${params}`;
          axios.get(url).then((res) => {
            this.find_pw_flag = true;

            console.log(res.data);
            $('.popup-background:eq(1)').addClass('blind');
            $('#spinner-section').addClass('blind');

            if (res.data.result === '1') {
              // INPUT 초기화
              $('#find-pw-email').val('');
              $('#find-pw-backoffice-code').val('');

              $('#find-pw-email').removeClass('null-input-border');
              $('#find-pw-backoffice-code').removeClass('null-input-border');

              // 팝업 관련창 닫음
              $('.popup-background:eq(0)').addClass('blind');
              $('#find-pw-section').addClass('blind');

              $('.popup-background:eq(1)').removeClass('blind');
              $('#common-alert-popup').removeClass('blind');
              $('.common-alert-txt').text('이메일로 비밀번호를 발송해드렸어요!');
            } else {
              $('.popup-background:eq(1)').removeClass('blind');
              $('#common-alert-popup').removeClass('blind');
              $('.common-alert-txt').text('해당 정보로 가입된 호스트가 없습니다.');
            }
          }).catch(() => {
            this.find_pw_flag = true;

            // 로딩 화면 닫기
            $('.popup-background:eq(1)').addClass('blind');
            $('#spinner-section').addClass('blind');

            $('.popup-background:eq(1)').removeClass('blind');
            $('#common-alert-popup').removeClass('blind');
            $('.common-alert-txt').text('오류 발생으로 인해 처리에 실패하였습니다.');
          });
        }
      }
    },

    // 비밀번호 창닫기 버튼
    closeFindPw() {
      $('.popup-background:eq(0)').addClass('blind');
      $('#find-pw-section').addClass('blind');
    },

    // 로그아웃
    logout() {
      axios.get('http://localhost:8800/backoffice/logoutOK').then((res) => {
        console.log(res.data);
        if (res.data.result === '1') {
          console.log(this.$cookies.remove('backoffice_no'));
          this.$cookies.remove('backoffice_no');
          this.$cookies.remove('host_image');

          $('#logout-popup').addClass('blind');
          $('.popup-background:eq(0)').addClass('blind');
          this.$router.go(0);
        }
      });
    },

    // 로그아웃 창 닫기
    closeLogoutPopup() {
      $('#logout-popup').addClass('blind');
      $('.popup-background:eq(0)').addClass('blind');
    },

    closeFailAlertPopup() {
      $('#fail-alert-popup').addClass('blind');
      $('.popup-background:eq(0)').addClass('blind');

      $('#input-update-pw').val('');
      $('#input-update-pw-re').val('');
      $('.pw-warning-text').addClass('blind');
    },
  },
};
</script>
