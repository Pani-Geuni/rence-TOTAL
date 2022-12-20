<!-- eslint-disable vuejs-accessibility/click-events-have-key-events -->
<!-- eslint-disable max-len -->
<template>
  <section class="headerWrap">
    <div class="logo-section host">
      <img @click="goLanding" src="@/assets/IMG/common/RENCE-Host.svg" alt="Host Logo" class="logo-mku host">
    </div>

    <div class="hostMenu-section ">

      <!-- 로그인 전 유저 메뉴 -->
      <section id="before_login" class="" @click="user_menu_select('before_login')"
        v-if="backoffice_no === null || backoffice_no === '' || backoffice_no === undefined">
        <div id="before_hostMenu" class="hostMenu">
          <img src="@/assets/IMG/header/user_menu.svg" alt="host_menu_img" class="host_menu_img" />
          <img src="@/assets/IMG/header/bx_user-circle.png" alt="host_profile_img" class="host_profile_img" />
        </div>

        <!-- CUSTOM SELECT -->
        <div class="custom-select-host blind">
          <ul class="host-select-wrap">
            <li @click="goLogin" id="go-login" class="host-select-list">로그인</li>
            <li @click="applyBackoffice" id="go-backoffice-insert" class="host-select-list">호스트등록신청</li>
            <li @click="goRence" id="go-user-home" class="host-select-list">Rence로 이동</li>
          </ul>
        </div>
      </section>

      <!-- 로그인 후 유저 메뉴 -->
      <section id="after_login" class="" @click="user_menu_select('after_login')" v-else>
        <div id="after_hostMenu" class="hostMenu">
          <img src="@/assets/IMG/header/user_menu.svg" alt="host_menu_img" class="host_menu_img" />
          <img src="https://rence.s3.ap-northeast-2.amazonaws.com/user/img_host_001.jpg" alt="host_profile_img"
            class="host_profile_img" />
        </div>
        <!-- CUSTOM SELECT -->
        <div class="custom-select-host blind">
          <ul class="host-select-wrap">
            <li @click="goRence" id="go-user-home" class="host-select-list">Rence로 이동</li>
            <li @click="goDashboard" id="go-dashboard" class="host-select-list">대시보드로 이동</li>
            <li @click="logout" id="go-logOut" class="host-select-list">로그아웃</li>
          </ul>
        </div>
      </section>
    </div>
  </section>

</template>

<style lang="scss" scoped>
@import '@/assets/CSS/backoffice/host-header.scss';
@import '@/assets/CSS/backoffice/host-login.scss';
</style>

<script>
import $ from 'jquery';
// import '@/assets/JS/backoffice/host_header';

console.log('backoffice header');

export default {
  name: 'HostHeaderView',

  data() {
    return {
      backoffice_no: this.$cookies.get('backoffice_no'),
    };
  },

  methods: {

    // 로그인에 따른 백오피스 유저 메뉴
    user_menu_select(isLogin) {
      if (isLogin === 'before_login') {
        $('#before_login>.custom-select-host').toggleClass('blind');
      } else {
        $('#after_login>.custom-select-host').toggleClass('blind');
      }
    },

    /** 로그인 메뉴 - 로그인 팝업 띄움 */
    goLogin() {
      $('#before_login>.custom-select-host').addClass('blind');
      $('.popup-background:eq(0)').removeClass('blind');
      $('#login-section').removeClass('blind');
    },

    /** 로고 버튼 클릭 시 메인 페이지 이동 * */
    goLanding() {
      this.$router.push('/backoffice/landing');
    },

    /** 공간등록신청 메뉴 */
    applyBackoffice() {
      this.$router.push('/backoffice/insert');
    },

    /** RENCE 페이지 이동 메뉴 */
    goRence() {
      this.$router.push('/');
    },

    // Dashboard로 이동
    goDashboard() {
      this.$router.push(`/backoffice/dash/main?backoffice_no=${this.backoffice_no}`);
    },

    /** 호스트 로그아웃 */
    logout() {
      $('#after_hostMenu>.custom-select-host').addClass('blind');
      $('#logout-popup').removeClass('blind');
      $('.popup-background:eq(0)').removeClass('blind');
    },
  },

};
</script>
