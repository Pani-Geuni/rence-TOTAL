<!-- eslint-disable vuejs-accessibility/click-events-have-key-events -->
<!-- eslint-disable max-len -->
<template>
  <div class="titleSection">
    <h1>정산</h1>
  </div>

  <div class="boardWrap sales">
    <div class="titleSection sales">
      <h3>
        <span v-if="sales_date === 'day'">일일</span>
        <span v-if="sales_date === 'week'">주간</span>
        <span v-if="sales_date === 'month'">월간</span>
        정산
      </h3>
      <ul class="sales-mini-nav">

        <li @click="miniNavDay" id="sales-mini-nav-day" class="nav-item">
          <p>일일</p>
        </li>
        <li @click="miniNavWeek" id="sales-mini-nav-week" class="nav-item">
          <p>주간</p>
        </li>
        <li @click="miniNavMonth" id="sales-mini-nav-month" class="nav-item">
          <p>월간</p>
        </li>
      </ul>
      <!-- END mini-nav -->
    </div>
    <!-- END titleSection sales -->

    <div id="today-sales" class="sales-state">
      <div class="sales-item">
        <p>매출 순이익</p>
        <span id="sales-income" class="sales-price">{{ svo.sales_income }}</span>
      </div>
      <div class="sales-item">
        <p>매출액</p>
        <span id="sales-revenue" class="sales-price">{{ svo.sales_total }}</span>
      </div>
      <div class="sales-item">
        <p>취소 금액</p>
        <span id="sales-cancel" class="sales-price">{{ svo.sales_cancel }}</span>
      </div>
    </div>
    <!-- END today-sales -->

    <div id="yesterday-sales" class="sales-state">
      <div class="sales-item">
        <p>
          <span v-if="sales_date === 'day'">전일</span>
          <span v-if="sales_date === 'week'">전주</span>
          <span v-if="sales_date === 'month'">전월</span>
          매출 총이익
        </p>
        <span id="sales-income" class="sales-price">{{ svo.pre_sales_income }}</span>
      </div>
      <div class="sales-item">
        <p>
          <span v-if="sales_date === 'day'">전일</span>
          <span v-if="sales_date === 'week'">전주</span>
          <span v-if="sales_date === 'month'">전월</span>
          매출액
        </p>
        <span id="sales-revenue" class="sales-price">{{ svo.pre_sales_total }}</span>
      </div>
      <div class="sales-item">
        <p>
          <span v-if="sales_date === 'day'">전일</span>
          <span v-if="sales_date === 'week'">전주</span>
          <span v-if="sales_date === 'month'">전월</span>
          취소 금액
        </p>
        <span id="sales-cancel" class="sales-price">{{ svo.pre_sales_cancel }}</span>
      </div>
    </div>
    <!-- END today-sales -->

    <div class="sales-state">
      <div class="sales-item">
        <p>매출 차이</p>
        <span id="sales-diff" class="sales-price">{{ svo.sales_gap }}</span>
      </div>
    </div>
    <!--  -->
  </div>
  <!-- END boardWrap sales -->

  <div class="boardWrap all-sales-list">
    <div class="titleSection">
      <h3>정산 내역</h3>
    </div>
    <!-- END titleSection sales -->

    <div class="custom-table">
      <div class="ct-header">
        <div class="ct-header-cell sales">예약 날짜</div>
        <div class="ct-header-cell sales">대여 공간</div>
        <div class="ct-header-cell sales">결제 금액</div>
        <div class="ct-header-cell sales">결제 방식</div>
        <div class="ct-header-cell sales">정산 여부</div>
      </div>
      <!-- END ct-header -->
      <div class="ct-body sales-ct">
        <div class="ct-body-row" v-for="vos in s_vos" :key="vos">
          <div class="ct-body-cell sales">
            {{ vos.reserve_sdate }} ~ <br />{{ vos.reserve_edate }}
          </div>
          <div class="ct-body-cell sales">{{ vos.room_name }}</div>
          <div class="ct-body-cell sales">
            <p class="sales-price">{{ vos.actual_payment }}</p>
          </div>
          <div class="ct-body-cell sales">
            <span v-if="vos.payment_state === 'T'">선결제</span>
            <span v-if="vos.payment_state === 'F'">후결제</span>
          </div>
          <div class="ct-body-cell sales">
            <button class="ct-body-btn is_sales_btn" v-if="vos.sales_state === 'T'" :end=true>완료</button>
            <button class=" ct-body-btn is_sales_btn" v-if="vos.sales_state === 'F'" :end=false
              :payment_no="vos.payment_no" :room_no="vos.room_no" @click="clickIsSalesBtn($event.target)">미완료</button>
          </div>
        </div>
        <!-- END ct-body-row -->
      </div>
      <!-- END ct-body -->
    </div>
    <!-- END custom-table -->
  </div>
  <!-- END boardWrap sales-list -->

  <section :class="{ 'paging-section': maxPage > 0, 'paging-section blind': maxPage === 0 }">
    <section class="paging-section">
      <div class="paging-wrap">
        <span @click="prev_paging_list"
          :class="{ 'paging-box before-page-btn hide': maxPage <= 5, 'paging-box before-page-btn': maxPage > 5 }">
          &lt;&lt;
        </span>

        <div class="paging-num-wrap paging-wrap">
          <!-- <div th:each="num : ${#numbers.sequence(start, res.maxPage)}"> -->
          <span @click="paging_list($event.currentTarget)" v-for="num in totalPageCnt" :key="num" :idx="num"
            :class="{ 'paging-box paging-num choice': num === nowPage, 'paging-box paging-num un-choice': num !== nowPage }">{{
                num
            }}</span>
        </div>

        <span @click="next_paging_list" v-if="totalPageCnt > 5 && maxPage < totalPageCnt"
          class="paging-box next-page-btn">>></span>
        <span v-else class="paging-box next-page-btn hide">>></span>
        <input type="hidden" id="totalPageCnt" :value="totalPageCnt">
      </div>
    </section>
  </section>
</template>

<style lang="scss" scoped>
@import '@/assets/CSS/dash-board/dash-sales-list.scss';
</style>

<script>
import $ from 'jquery';
import axios from 'axios';

export default {
  name: 'SalesView',

  data() {
    return {
      backoffice_no: decodeURIComponent(window.atob(this.$cookies.get('backoffice_no'))),
      sales_date: this.$route.query.sales_date,
      svo: [],
      s_vos: [],
      // vos: [],
    };
  },

  watch: {
    $route() {
      $('#sales-mini-nav-day').removeClass('active');
      $('#sales-mini-nav-week').removeClass('active');
      $('#sales-mini-nav-month').removeClass('active');
      this.miniNavActive();
    },
  },

  methods: {
    miniNavActive() {
      switch (this.$route.query.sales_date) {
        case 'day':
          $('#sales-mini-nav-day').addClass('active');
          break;
        case 'week':
          $('#sales-mini-nav-week').addClass('active');
          break;
        case 'month':
          $('#sales-mini-nav-month').addClass('active');
          break;
        default:
          break;
      }
    },

    miniNavDay() {
      // 로딩 화면
      $('.popup-background:eq(1)').removeClass('blind');
      $('#spinner-section').removeClass('blind');

      this.$router.push(`/backoffice/dash/day_sales?backoffice_no=${this.$cookies.get('backoffice_no')}&sales_date=day&page=1`);

      this.sales_date = 'day';
      this.getSales();
    },

    miniNavWeek() {
      this.$router.push(`/backoffice/dash/day_sales?backoffice_no=${this.$cookies.get('backoffice_no')}&sales_date=week&page=1`);
      this.sales_date = 'week';
      console.log('week :', this.sales_date);
      this.getSales();
    },

    miniNavMonth() {
      this.$router.push(`/backoffice/dash/day_sales?backoffice_no=${this.$cookies.get('backoffice_no')}&sales_date=month&page=1`);
      this.sales_date = 'month';
      this.getSales();
    },

    getSales() {
      // 로딩 화면
      $('.popup-background:eq(1)').removeClass('blind');
      $('#spinner-section').removeClass('blind');

      const params = new URLSearchParams();
      params.append('backoffice_no', this.backoffice_no);
      params.append('sales_date', this.sales_date);

      const url = `http://localhost:8800/backoffice/dash/day_sales?${params}`;
      axios.get(url).then((res) => {
        // 로딩 화면
        $('.popup-background:eq(1)').addClass('blind');
        $('#spinner-section').addClass('blind');
        console.log(res.data);

        this.svo = res.data.svo;
        this.s_vos = res.data.s_vos;
        this.cnt = res.data.cnt;
        this.maxPage = res.data.maxPage;
        this.nowCnt = res.data.nowCnt;
        this.nowPage = res.data.nowPage;
        this.totalPageCnt = res.data.totalPageCnt;
      });
    },

    clickIsSalesBtn(e) {
      if (e.getAttribute('end') === 'false') {
        $('.popup-background:eq(0)').removeClass('blind');
        $('#calculate-popup').removeClass('blind');
        $('#calculate-btn').attr('room_no', e.getAttribute('room_no'));
        $('#calculate-btn').attr('payment_no', e.getAttribute('payment_no'));
      }
    },

    paging_list(e) {
      this.nowPage = $(e).text();

      const params = new URLSearchParams();
      params.append('backoffice_no', this.backoffice_no);
      params.append('sales_date', this.sales_date);
      params.append('page', this.nowPage);

      axios.get(`http://localhost:8800/backoffice/dash/day_sales?${params}`)
        .then((res) => {
          console.log(res.data);
          this.s_vos = res.data.s_vos;
        });
    },

    next_paging_list() {
      const start = Number($($('.paging-box.paging-num')[0]).text()) + 5;
      let last = Number($($('.paging-box.paging-num')[4]).text()) + 5;
      const totalPageCnt = Number($('#totalPageCnt').val());

      if ($('.before-page-btn').hasClass('hide')) {
        $('.before-page-btn').removeClass('hide');
      }

      if (last >= totalPageCnt) {
        last = totalPageCnt;
        $('.next-page-btn').addClass('hide');
      }

      const params = new URLSearchParams();
      params.append('backoffice_no', this.backoffice_no);
      params.append('sales_date', this.sales_date);
      params.append('page', start);

      axios.get(`http://localhost:8800/backoffice/dash/day_sales?${params}`)
        .then((res) => {
          console.log(res.data);
          this.s_vos = res.data.s_vos;
        });
    },

    prev_paging_list() {
      const start = Number($($('.paging-box.paging-num')[0]).text()) - 5;
      let last = Number($('.paging-box.paging-num:last').text()) - 5;

      if (last % 5 !== 0) {
        last += 5 - (last % 5);
      }

      if ($('.next-page-btn').hasClass('hide')) {
        $('.next-page-btn').removeClass('hide');
      }

      if (start === 1) {
        $('.before-page-btn').addClass('hide');
      }

      const params = new URLSearchParams();
      params.append('backoffice_no', this.backoffice_no);
      params.append('sales_date', this.sales_date);
      params.append('page', start);

      axios.get(`http://localhost:8800/backoffice/dash/day_sales?${params}`)
        .then((res) => {
          console.log(res.data);
          this.s_vos = res.data.s_vos;
        });
    },
  },

  mounted() {
    axios.get('http://localhost:8800/backoffice/loginCheck')
      .then((res) => {
        if (res.data.result === '1') {
          this.$nextTick(() => {
            this.miniNavActive();
            this.getSales();
          });
        } else {
          this.$store.commit('backoffice_setLogin_false');
          this.$router.replace('/backoffice/landing');
        }
      });
  },
};
</script>
