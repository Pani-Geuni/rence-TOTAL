<!-- eslint-disable vuejs-accessibility/click-events-have-key-events -->
<template>
  <!-- START aside-menu -->
  <aside class="asideWrap">
    <div>
      <div @click="goLanding" class="logo-section">
        <img src="@/assets/IMG/common/RENCE-Host.svg" alt="Host logo" class="logo-mku">
      </div>

      <!-- START menuWrap -->
      <div class="menuWrap">
        <div @click="menuHome" id="menu-home" class="menu-item">
          <img src="@/assets/IMG/dash-board/ico-host-board.svg" alt="Dash Board" />
          <p>대시보드</p>
        </div>
        <div @click="menuSpace" id="menu-space" class="menu-item">
          <img src="@/assets/IMG/dash-board/ico-host-room.svg" alt="room" />
          <p>공간 관리</p>
        </div>
        <div @click="menuSchedule" id="menu-schedule" class="menu-item">
          <img src="@/assets/IMG/dash-board/ico-host-calendar.svg" alt="room" />
          <p>일정 관리</p>
        </div>
        <div @click="menuReserve" id="menu-reserve" class="menu-item">
          <img src="@/assets/IMG/dash-board/ico-host-time.svg" alt="time" />
          <p>예약 관리</p>
        </div>
        <div @click="menuSales" id="menu-sales" class="menu-item">
          <img src="@/assets/IMG/dash-board/ico-host-reciept.svg" alt="Reciept" />
          <p>정산</p>
        </div>
        <div @click="menuSettings" id="menu-settings" class="menu-item">
          <img src="@/assets/IMG/dash-board/ico-host-settings.svg" alt="Settings" />
          <p>환경설정</p>
        </div>
      </div>
      <!-- END menuWrap -->
    </div>

    <div @click="goRenceHome" class="etcWrap">
      <p id="go-rence-home">RENCE 이동</p>
    </div>
  </aside>
  <!-- END aside-menu -->

</template>

<style lang="scss" scoped>
@import '@/assets/CSS/dash-board/left-side-menu.scss';
</style>

<script>
import $ from 'jquery';

export default {
  name: 'SidebarView',

  data() {
    return {
      backoffice_no: this.$cookies.get('backoffice_no'),
    };
  },

  watch: {
    $route(to) {
      $('#menu-home').removeClass('active');
      $('#menu-space').removeClass('active');
      $('#mini-nav-list').removeClass('active');
      $('#menu-space').removeClass('active');
      $('#mini-nav-qna').removeClass('active');
      $('#menu-space').removeClass('active');
      $('#mini-nav-review').removeClass('active');
      $('#menu-schedule').removeClass('active');
      $('#menu-reserve').removeClass('active');
      $('#reserve-list').removeClass('active');
      $('#menu-sales').removeClass('active');
      $('#menu-settings').removeClass('active');
      $('#menu-settings').removeClass('active');

      this.sidebarActive(to.path);
    },
  },

  methods: {

    goLanding() {
      this.$router.push('/backoffice/landing');
    },

    goRenceHome() {
      this.$router.push('/');
    },

    // ****************************************
    // Dash Board Side Menu 현재 페이지 active 설정
    // ****************************************
    sidebarActive(locationPathname) {
      switch (locationPathname) {
        case '/backoffice/dash/main':
          $('#menu-home').addClass('active');
          break;

        case '/backoffice/dash/room':
          $('#menu-space').addClass('active');
          $('#mini-nav-list').addClass('active');
          break;

        case '/backoffice/dash/qna':
          $('#menu-space').addClass('active');
          $('#mini-nav-qna').addClass('active');
          break;

        case '/backoffice/dash/review':
          $('#menu-space').addClass('active');
          $('#mini-nav-review').addClass('active');
          break;

        case '/backoffice/dash/schedule':
        case '/backoffice/dash/reservation':
          $('#menu-schedule').addClass('active');
          break;

        case '/backoffice/dash/reserve':
        case '/rence/backoffice_search_reserve':
          $('#menu-reserve').addClass('active');
          $('#reserve-list').addClass('active');
          break;

        case '/backoffice/dash/day_sales':
          $('#menu-sales').addClass('active');
          break;

        case '/backoffice/dash/settings':
          $('#menu-settings').addClass('active');
          break;

        case '/backoffice/dash/update_host':
          $('#menu-settings').addClass('active');
          break;

        default:
          break;
      }
    },

    menuHome() {
      this.$router.push(`/backoffice/dash/main?backoffice_no=${this.backoffice_no}`);
    },

    menuSpace() {
      this.$router.push(`/backoffice/dash/room?backoffice_no=${this.backoffice_no}&page=1`);
    },

    menuReserve() {
      this.$router.push(`/backoffice/dash/reserve?backoffice_no=${this.backoffice_no}&reserve_state=all&page=1`);
    },

    menuSchedule() {
      this.$router.push(`/backoffice/dash/schedule?backoffice_no=${this.backoffice_no}&page=1`);
    },

    menuSales() {
      this.$router.push(`/backoffice/dash/day_sales?backoffice_no=${this.backoffice_no}&sales_date=day&page=1`);
    },

    menuSettings() {
      this.$router.push(`/backoffice/dash/settings?backoffice_no=${this.backoffice_no}`);
    },
  },

  mounted() {
    this.$nextTick(() => {
      this.sidebarActive(window.location.pathname);
    });
  },
};
</script>
