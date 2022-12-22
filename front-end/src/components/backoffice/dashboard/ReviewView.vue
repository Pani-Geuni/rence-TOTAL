<!-- eslint-disable vuejs-accessibility/click-events-have-key-events -->
<!-- eslint-disable max-len -->
<template>
  <div class="titleSection">
    <h1>공간 관리</h1>
    <button id="btn-room-add" class="btn-room-add">추가</button>
    <ul class="mini-nav">
      <li>
        <p @click="miniNavList" id="mini-nav-list" class="nav-item">리스트</p>
      </li>
      <li>
        <p @click="miniNavQna" id="mini-nav-qna" class="nav-item">문의</p>
      </li>
      <li>
        <p @click="miniNavReview" id="mini-nav-review" class="nav-item">후기</p>
      </li>
    </ul>
    <!-- END mini-nav -->
  </div>
  <!-- END titleSection -->

  <div class="boardWrap review">
    <div class="review-list">
      <div class="review-item" v-for="vos in rv_vos" :key="vos">
        <div>
          <img class="profile-image" src="https://rence.s3.ap-northeast-2.amazonaws.com/user/img_host_001.jpg"
            alt="profile image" />
        </div>
        <!-- END profile Image -->
        <div class="review-main">
          <div class="review-header">
            <p id="user-name" class="user-name">{{ vos.user_name }}</p>
            <span id="review-point" class="review-point">{{ vos.review_point }}</span>
          </div>
          <!-- END review-header -->
          <div class="review-content">
            <pre>{{ vos.review_content }}</pre>
          </div>
          <!-- END review-content -->
          <span id="review-date" class="review-date">{{ vos.review_date }}</span>
        </div>
        <!-- END review-main -->
      </div>
      <!-- END review-item -->
      <!-- END pagination -->
    </div>
    <!-- END review-list -->
  </div>

  <section :class="{ 'paging-section': maxPage > 0, 'paging-section blind': maxPage === 0 }">
    <section class="paging-section">
      <div class="paging-wrap">
        <span @click="prev_room_paging"
          :class="{ 'paging-box before-page-btn hide': maxPage <= 5, 'paging-box before-page-btn': maxPage > 5 }">
          &lt;&lt;
        </span>

        <div class="paging-num-wrap paging-wrap">
          <!-- <div th:each="num : ${#numbers.sequence(start, res.maxPage)}"> -->
          <span @click="room_paging($event.currentTarget)" v-for="num in totalPageCnt" :key="num" :idx="num"
            :class="{ 'paging-box paging-num choice': num === nowPage, 'paging-box paging-num un-choice': num !== nowPage }">{{
                num
            }}</span>
        </div>

        <span @click="next_room_paging" v-if="totalPageCnt > 5 && maxPage < totalPageCnt"
          class="paging-box next-page-btn">>></span>
        <span v-else class="paging-box next-page-btn hide">>></span>
        <input type="hidden" id="totalPageCnt" :value="totalPageCnt">
      </div>
    </section>
  </section>
</template>

<style>
@import '@/assets/CSS/dash-board/dash-review-list.scss';
</style>

<script>
import $ from 'jquery';
import axios from 'axios';

export default {
  name: 'ReviewView',
  data() {
    return {
      backoffice_no: decodeURIComponent(window.atob(this.$cookies.get('backoffice_no'))),
      rv_vos: [],
      res: [],
      cnt: 0,
      maxPage: 0,
      nowPage: 0,
      page: 'review',
      totalPageCnt: 0,
    };
  },

  watch: {
    $route(to) {
      $('#mini-nav-list').removeClass('active');
      $('#mini-nav-qna').removeClass('active');
      $('#mini-nav-review').removeClass('active');

      this.miniNavActive(to.path);
    },
  },

  methods: {
    miniNavActive(locationPathname) {
      switch (locationPathname) {
        case '/backoffice/dash/room':
          $('#mini-nav-list').addClass('active');
          break;
        case '/backoffice/dash/qna':
          $('#mini-nav-qna').addClass('active');
          break;
        case '/backoffice/dash/review':
          $('#mini-nav-review').addClass('active');
          break;

        default:
          break;
      }
    },

    miniNavList() {
      this.$router.push(`/backoffice/dash/room?backoffice_no=${this.$cookies.get('backoffice_no')}&page=1`);
    },

    miniNavQna() {
      this.$router.push(`/backoffice/dash/qna?backoffice_no=${this.$cookies.get('backoffice_no')}&page=1`);
    },

    miniNavReview() {
      this.$router.push(`/backoffice/dash/review?backoffice_no=${this.$cookies.get('backoffice_no')}&page=1`);
    },

    getReviewList() {
      const params = new URLSearchParams();
      params.append('backoffice_no', this.backoffice_no);

      axios.get(`http://localhost:8800/backoffice/dash/review?${params}`).then((res) => {
        console.log(res.data);
        this.rv_vos = res.data.rv_vos;
        this.cnt = res.data.cnt;
        this.maxPage = res.data.maxPage;
        this.nowPage = res.data.nowPage;
        this.totalPageCnt = res.data.totalPageCnt;
      });
    },

    paging_list(e) {
      this.nowPage = $(e).text();

      const params = new URLSearchParams();
      params.append('backoffice_no', this.backoffice_no);
      params.append('page', this.nowPage);

      axios.get(`http://localhost:8800/backoffice/dash/review?${params}`)
        .then((res) => {
          console.log(res.data);
          this.rv_vos = res.data.rv_vos;
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
      params.append('page', start);

      axios.get(`http://localhost:8800/backoffice/dash/review?${params}`)
        .then((res) => {
          console.log(res.data);
          this.rv_vos = res.data.rv_vos;
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
      params.append('page', start);

      axios.get(`http://localhost:8800/backoffice/dash/review?${params}`)
        .then((res) => {
          console.log(res.data);
          this.rv_vos = res.data.rv_vos;
        });
    },
  },

  mounted() {
    axios.get('http://localhost:8800/backoffice/loginCheck')
      .then((res) => {
        if (res.data.result === '1') {
          console.log('login OK!!');
          this.$nextTick(() => {
            this.miniNavActive(window.location.pathname);
            this.getReviewList();
          });
        } else {
          this.$store.commit('backoffice_setLogin_false');
          this.$router.replace('/backoffice/landing');
        }
      });
  },
};
</script>
