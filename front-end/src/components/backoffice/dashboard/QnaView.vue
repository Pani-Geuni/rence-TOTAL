<!--
--  @author 전판근, 김예은
-- @refactoring 전판근
-->


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

  <div class="boardWrap qna">
    <div class="custom-table qna qna-table-wrap">
      <div class="ct-header qna">
        <div class="ct-header-cell qna">처리 상태</div>
        <div class="ct-header-cell qna">문의 공간</div>
        <div class="ct-header-cell qna">문의 내용</div>
        <div class="ct-header-cell qna">작성일</div>
        <div class="ct-header-cell qna">답변</div>
      </div>
      <!-- END ct-header -->

      <div v-for="vos in q_vos" :key="vos">
        <div @click="showQanAnswer($event.currentTarget)" class="ct-body-row qna">
          <div class="ct-body-cell qna">
            <p v-if="vos.comment_state === 'T'" class="qna-process true">처리</p>
            <p v-if="vos.comment_state === 'F'" class="qna-process false">미처리</p>
          </div>
          <div class="ct-body-cell qna">
            <p>{{ vos.room_name }}</p>
          </div>
          <div class="ct-body-cell qna">
            <p>{{ vos.comment_content }}</p>
          </div>
          <div class="ct-body-cell qna">
            <p>{{ vos.comment_date }}</p>
          </div>
          <div class="ct-body-cell qna">
            <button v-if="vos.comment_state === 'T'" class="ct-body-btn qna-delete" :comment_no="vos.comment_no"
              :room_no="vos.room_no" :answer_no="vos.answer_no" @click="showAnswerDeletePopup($event.target)">답변
              삭제</button>

            <button v-if="vos.comment_state === 'F'" class="ct-body-btn qna-add" :comment_no="vos.comment_no"
              :room_no="vos.room_no" :answer_no="vos.answer_no" @click="showAnswerInsertPopup">답변
              작성</button>
          </div>
        </div>
        <!-- END ct-body-row -->

        <div class="detail-qna-wrap blind" ref="detailQnaWrap">
          <div class="question-section">
            <h5 class="question-title">Q.</h5>
            <p class="question-content">
              {{ vos.comment_content }}
            </p>
          </div>
          <!-- END question-section -->
          <div v-if="vos.answer_content">
            <div class="answer-section">
              <div class="answer-title-section">
                <h5 class="answer-title">A.</h5>
                <p class="answer-date">작성일 | {{ vos.answer_date }}</p>
              </div>
              <p class="answer-content">
                {{ vos.answer_content }}
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- END boardWrap qna -->

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
@import '@/assets/CSS/dash-board/dash-qna-list.scss';
</style>

<script>
import $ from 'jquery';
import axios from 'axios';

export default {
  name: 'QnaView',

  data() {
    return {
      backoffice_no: decodeURIComponent(window.atob(this.$cookies.get('backoffice_no'))),
      q_vos: [],
      res: [],
      cnt: 0,
      maxPage: 0,
      nowPage: 1,
      page: 'qna',
      totalPageCnt: 0,
    };
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

    getQnaList() {
      const params = new URLSearchParams();
      params.append('backoffice_no', this.backoffice_no);

      axios.get(`http://localhost:8800/backoffice/dash/qna?backoffice_no=${this.backoffice_no}`)
        .then((res) => {
          this.q_vos = res.data.q_vos;
          this.cnt = res.data.cnt;
          this.maxPage = res.data.maxPage;
          this.nowPage = res.data.nowPage;
          this.totalPageCnt = res.data.totalPageCnt;
        });
    },

    showQanAnswer(e) {
      $(e).siblings('.detail-qna-wrap').toggleClass('blind');
    },

    showAnswerDeletePopup(e) {
      $('.popup-background:eq(0)').removeClass('blind');
      $('#answer-delete-popup').removeClass('blind');

      $('#delete-answer-btn').attr('comment_no', $(e).attr('answer_no'));
      $('#delete-answer-btn').attr('mother_no', $(e).attr('comment_no'));
    },

    showAnswerInsertPopup(e) {
      // 로딩 화면
      $('.popup-background:eq(1)').removeClass('blind');
      $('#spinner-section').removeClass('blind');

      const params = new URLSearchParams();
      params.append('backoffice_no', this.backoffice_no);
      params.append('room_no', e.target.getAttribute('room_no'));
      params.append('comment_no', e.target.getAttribute('comment_no'));

      axios.get(`http://localhost:8800/backoffice/dash/insert_comment?${params}`)
        .then((res) => {
          // 로딩 화면 닫기
          $('.popup-background:eq(1)').addClass('blind');
          $('#spinner-section').addClass('blind');

          $('#q_room_name').text(res.data.cvo.room_name);
          $('#user_comment').text(res.data.cvo.comment_content);
          $('#h_comment_insert').attr('comment_no', res.data.cvo.comment_no);
          $('#h_comment_insert').attr('room_no', res.data.cvo.room_no);

          $('#comment-section').removeClass('blind');
          $('.popup-background:eq(0)').removeClass('blind');
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

    paging_list(e) {
      this.nowPage = $(e).text();

      const params = new URLSearchParams();
      params.append('backoffice_no', this.backoffice_no);
      params.append('page', this.nowPage);

      axios.get(`http://localhost:8800/backoffice/dash/qna?${params}`)
        .then((res) => {
          this.q_vos = res.data.q_vos;
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

      axios.get(`http://localhost:8800/backoffice/dash/qna?${params}`)
        .then((res) => {
          this.q_vos = res.data.q_vos;
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

      axios.get(`http://localhost:8800/backoffice/dash/qna?${params}`)
        .then((res) => {
          this.q_vos = res.data.q_vos;
        });
    },
  },

  mounted() {
    axios.get('http://localhost:8800/backoffice/loginCheck')
      .then((res) => {
        if (res.data.result === '1') {
          this.$nextTick(() => {
            this.miniNavActive(window.location.pathname);
            this.getQnaList();
          });
        } else {
          this.$store.commit('backoffice_setLogin_false');
          this.$router.replace('/backoffice/landing');
        }
      });
  },
};
</script>
