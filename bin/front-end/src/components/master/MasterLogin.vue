<!-- eslint-disable vuejs-accessibility/click-events-have-key-events -->
<!-- eslint-disable max-len -->
<!-- eslint-disable vuejs-accessibility/form-control-has-label -->
<template>
  <div class="contentWrap">
    <div class="loginWrap">
      <div class="logo-section master-login">
        <img src="@/assets/IMG/common/Rence-Master.svg" alt="Master Logo" class="logo-mku">
      </div>
      <!-- END logoSection -->

      <div class="inputSection">
        <input id="master-id" type="text" placeholder="관리자 아이디를 입력하세요" /> <input id="master-pw" type="password"
          placeholder="비밀번호를 입력하세요" />
      </div>
      <!-- END inputSection -->

      <button @click="clickLogin" id="btn-master-login" class="btn-master-login">로그인</button>
    </div>
    <!-- END loginWrap -->
  </div>
  <!-- END contentWrap -->

  <div class="popup-background blind">
    <!-- START COMMON CUSTOM ALERT POPUP -->
    <div id="common-alert-popup" class="alert-popup blind">
      <section class="alert-txt-section">
        <span class="common-alert-txt"></span>
      </section>
      <section id="common-alert-btn" class="alert-btn-section">
        <span @click="failLogin">확인</span>
      </section>
    </div>
  </div>
</template>

<style scoped>
@import '@/assets/CSS/master/master-login.scss';
</style>

<script>
import $ from 'jquery';
import axios from 'axios';

export default {
  name: 'MasterLogin',

  methods: {
    // 로그인 실패 팝업 닫기
    failLogin() {
      $('#common-alert-popup').addClass('blind');
      $('.popup-background').addClass('blind');
    },

    clickLogin() {
      /** 로그인 시도 * */
      // 로그인 성공
      if ($('#master-id').val().trim().length > 0 && $('#master-pw').val().trim().length > 0) {
        const params = new URLSearchParams();
        console.log('username :', $('#master-id').val().trim());
        console.log('password :', $('#master-pw').val().trim());

        params.append('username', $('#master-id').val().trim());
        params.append('password', $('#master-pw').val().trim());
        axios.post('http://localhost:8800/master/loginOK', params).then((res) => {
          console.log('res.data :', res.data);

          if (res.data.result === '1') {
            // INPUT 초기화
            $('#master-id').val('');
            $('#master-pw').val('');

            window.location.href = '/master/main';
          } else {
            $('.popup-background').removeClass('blind');
            $('#common-alert-popup').removeClass('blind');
            $('.common-alert-txt').text('로그인에 실패하였습니다.');
          }
        });
      } else {
        // 로그인 실패
        // 로그인 실패
        if ($('#master-id').val().trim().length === 0) {
          $('#master-id').addClass('null-input-border');
        }

        if ($('#master-pw').val().trim().length === 0) {
          $('#master-pw').addClass('null-input-border');
        }
      }
    },
  },
};

</script>
