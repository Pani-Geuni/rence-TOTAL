<template>
  <section class="landingWrap">
    <div class="start-hosting">
      <h1 class="title-start-hosting">나의 공간을 공개해 볼까요?</h1>
      <button @click="goDashboard" id="btn-start-hosting" class="btn-start-hosting">
        <p>호스팅 시작하기</p>
      </button>
    </div>

    <div class="apply-hosting">
      <h1 id="title-apply-hosting" class="title-apply-hosting">
        공간 공유 플랫폼, <br />Rence에서 시작하세요
      </h1>
      <button @click="goApplyHosting" id="btn-apply-hosting" class="btn-apply-hosting">
        <p>호스트 신청하기</p>
      </button>
    </div>
  </section>
</template>

<style lang="scss" scoped>
@import '@/assets/CSS/backoffice/host-landing.scss';
</style>

<script>
import $ from 'jquery';
// import '@/assets/JS/backoffice/host_insert';
// import '@/assets/JS/backoffice/custom_timepicker';

export default {
  name: 'LandingView',

  data() {
    return {
      backoffice_no: this.$cookies.get('backoffice_no'),
    };
  },

  methods: {
    /** 호스트 신청 페이지 */
    goApplyHosting() {
      this.$router.push('/backoffice/insert');
    },

    // Dashboard로 이동
    goDashboard() {
      if (this.$cookies.isKey('backoffice_no')) {
        this.$router.push(`/backoffice/dash/main?backoffice_no=${this.backoffice_no}`);
      } else {
        $('.popup-background:eq(1)').removeClass('blind');
        $('#common-alert-popup').removeClass('blind');
        $('.common-alert-txt').text('로그인 후 이용해 주세요.');
      }
    },
  },
};

</script>
