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

  <section class="paging-section" v-if="res.masPage > 0">
    <div class="paging-wrap">
      <span th:if="${res.maxPage} <= 5" class="paging-box before-page-btn hide"> &lt;&lt; </span>
      <span th:unless="${res.maxPage} <= 5" class="paging-box before-page-btn"> &lt;&lt; </span>

      <!-- <th:block th:with="ceil=${#numbers.formatInteger(T(java.lang.Math).ceil((res.nowPage)/5.0),1)}">
        <th:block th:with="start=(5 * (${ceil} - 1) + 1)">
          <div class="paging-num-wrap paging-wrap">
            <th:block th:each="num : ${#numbers.sequence(start, res.maxPage)}">
              <span th:if="${num} == ${res.nowPage}" th:attr="idx=${num}"
                class="paging-box paging-num choice">[[${num}]]</span>
              <span th:if="${num} != ${res.nowPage}" th:attr="idx=${num}"
                class="paging-box paging-num un-choice">[[${num}]]</span>
            </th:block>
          </div>
        </th:block>
      </th:block> -->

      <span th:if="${res.totalPageCnt} > 5 and ${res.maxPage} < ${res.totalPageCnt}"
        class="paging-box next-page-btn">>></span>
      <span th:unless="${res.totalPageCnt} > 5 and ${res.maxPage} < ${res.totalPageCnt}"
        class="paging-box next-page-btn hide">>></span>
      <input type="hidden" id="totalPageCnt" th:value="${res.totalPageCnt}">
    </div>
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
      babckoffice_no: this.$cookies.get('backoffice_no'),
      rv_vos: [],
      res: [],
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
      this.$router.push(`/backoffice/dash/room?backoffice_no=${this.babckoffice_no}&page=1`);
    },

    miniNavQna() {
      this.$router.push(`/backoffice/dash/qna?backoffice_no=${this.babckoffice_no}&page=1`);
    },

    miniNavReview() {
      this.$router.push(`/backoffice/dash/review?backoffice_no=${this.babckoffice_no}&page=1`);
    },

    getReviewList() {
      const url = `http://localhost:8800/backoffice/dash/review?backoffice_no=${this.babckoffice_no}`;

      axios.get(url).then((res) => {
        console.log(res.data);
        this.rv_vos = res.data.rv_vos;
      });
    },
  },

  mounted() {
    this.$nextTick(() => {
      this.miniNavActive(window.location.pathname);
      this.getReviewList();
    });
  },
};
</script>
