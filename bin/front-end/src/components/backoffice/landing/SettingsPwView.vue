<!-- eslint-disable max-len -->
<!-- eslint-disable vuejs-accessibility/form-control-has-label -->
<template>
  <!-- popup background -->
  <div class="reset-pw-background">
    <div id="popup-update-pw" class="">
      <div class="titleSection">
        <h3>새로운 비밀번호를 입력해 주세요</h3>
      </div>

      <div class="input-group">
        <div class="input-item">
          <input @keyup="checkUpdatePw" @keydown="checkUpdatePw" name="backoffice_pw" id="input-update-pw"
            class="input-update-pw" type="password" placeholder="새로운 비밀번호를 입력해 주세요." />
          <span class="pw-warning-text blind"></span>
        </div>
        <div class="input-item">
          <input @keyup="checkUpdatePw" @keydown="checkUpdatePw" name="backoffice_pw_check" id="input-update-pw-re"
            class="input-update-pw" type="password" placeholder="새로운 비밀번호를 다시 입력해 주세요." />
          <span class="pw-warning-text blind"></span>
        </div>

      </div>

      <div class="btn-popup-group">
        <button @click="clickUpdatePwBtn" type="submit" id="btn-pw-update">확인</button>
      </div>
    </div>
    <!-- END popup-update-pw -->
  </div>
</template>

<style>
@import '@/assets/CSS/backoffice/host-login.scss';
</style>

<script>
import $ from 'jquery';
import axios from 'axios';

export default {
  name: 'SettingsPwView',

  data() {
    return {
      backoffice_no: this.$cookies.get('backoffice_no'),
    };
  },

  methods: {
    checkUpdatePw(e) {
      const targetId = e.target.id;

      if (targetId === 'input-update-pw') {
        const password = /^(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-z0-9!@#$%^&*]{8,10}$/;

        if (!password.test(e.target.value)) {
          if (($('#input-update-pw-re').val().trim().length > 0) && ($(this).val().trim() != $('#input-update-pw-re').val().trim())) {
            $('.pw-warning-text:eq(0)').removeClass('blind');
            $('.pw-warning-text:eq(0)').text('아래 비밀번호와 일치하지 않습니다.');
          } else {
            $('.pw-warning-text:eq(0)').removeClass('blind');
            $('.pw-warning-text:eq(0)').text('비밀번호 조건 (소문자+숫자+특수문자 포함 8~10글자)');
          }
        } else {
          $('.pw-warning-text:eq(0)').addClass('blind');

          if ($('#input-update-pw-re').val().trim().length > 0) {
            if (e.target.value !== $('#input-update-pw-re').val().trim()) {
              $('.pw-warning-text:eq(1)').removeClass('blind');
              $('.pw-warning-text:eq(1)').text('위의 비밀번호와 일치하지 않습니다.');
            } else {
              $('.pw-warning-text:eq(1)').addClass('blind');
            }
          }
        }
      } else if (targetId === 'input-update-pw-re') {
        if (e.target.value !== $('#input-update-pw').val().trim()) {
          $('.pw-warning-text:eq(1)').removeClass('blind');
          $('.pw-warning-text:eq(1)').text('위의 비밀번호와 일치하지 않습니다.');
        } else {
          $('.pw-warning-text:eq(1)').addClass('blind');
        }
      }
    },

    // 비밀번호 재설정 AXIOS
    clickUpdatePwBtn() {
      const password = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,10}$/;

      if ($('#input-update-pw').val().trim().length > 0
        && $('#input-update-pw-re').val().trim().length > 0) {
        console.log($('#input-update-pw').val().trim());
        console.log($('#input-update-pw-re').val().trim());

        if ($('#input-update-pw').val().trim() === $('#input-update-pw-re').val().trim()
          && password.test($('#input-update-pw').val().trim())) {
          // 로딩 화면
          $('.popup-background:eq(1)').removeClass('blind');
          $('#spinner-section').removeClass('blind');

          const params = new URLSearchParams();
          params.append('backoffice_no', this.backoffice_no);
          params.append('backoffice_pw', $('#input-update-pw-re').val().trim());

          axios.post('http://localhost:8800/backoffice/settingOK_pw', params)
            .then((res) => {
              // 로딩 화면 닫기
              $('.popup-background:eq(1)').addClass('blind');
              $('#spinner-section').addClass('blind');

              if (res.data.result === '1') {
                this.$router.replace(`/backoffice/settings?backoffice_no=${this.backoffice_no}`);
              } else if (res.data.result === '2') {
                this.$router.replace('/backoffice/landing');
              } else {
                $('.popup-background:eq(1)').removeClass('blind');
                $('#common-alert-popup').removeClass('blind');
                $('.common-alert-txt').text('비밀번호 변경에 실패하였습니다.');
              }
            })
            .catch(() => {
              // 로딩 화면 닫기
              $('.popup-background:eq(1)').addClass('blind');
              $('#spinner-section').addClass('blind');

              $('.popup-background:eq(1)').removeClass('blind');
              $('#common-alert-popup').removeClass('blind');
              $('.common-alert-txt').text('비밀번호 변경에 실패하였습니다.');
            });
        } else {
          $('.popup-background:eq(0)').removeClass('blind');
          $('#fail-alert-popup').removeClass('blind');
        }
      } else {
        if ($('#input-update-pw').val().trim().length === 0) {
          $('#input-update-pw').addClass('null-input-border');
        }
        if ($('#input-update-pw-re').val().trim().length === 0) {
          $('#input-update-pw-re').addClass('null-input-border');
        }
      }
    },
  },
};
</script>
