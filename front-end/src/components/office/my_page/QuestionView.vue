<!-- eslint-disable linebreak-style -->
<!-- eslint-disable no-plusplus -->
<!-- eslint-disable array-callback-return -->
<!-- eslint-disable space-before-blocks -->
<!-- eslint-disable next-line default-case-->
<!-- eslint-disable -->

<!--
 - @author 김예은
 - @refactoring 김예은
-->

<template>
  <div class="question-list-wrap" v-if="load === true">
    <section class="page-title-section">
      <span class="page-title">문의한 내역들을 바로 확인해 보세요</span>
    </section>

    <section class="question-history-section">
    	<div>
        <section class="question-history-title-section">
          <div class="list-title-txt-wrap">
            <span class="list-title-txt list-status">처리 상태</span>
            <span class="list-title-txt list-company-name">문의 장소</span>
            <span class="list-title-txt list-content">문의 내용</span>
            <span class="list-title-txt list-date">작성일</span>
          </div>
        </section>
        <section class="question-history-wrap">
          <ul>
            <li v-for="obj in list" :key="obj" class="question-list">
              <div class="question-content-wrap" @click="show_detail_question($event.currentTarget)">
                  <span v-if="obj.state === 'Y'" class="list-status complete-label">처리</span>
                  <span v-if="obj.state === 'N'" class="list-status un-complete-label">미처리</span>
      
                  <span class="list-company-name">{{obj.backoffice_name}}</span>
                  <span class="list-content">{{obj.comment_content}}</span>
                  <span class="list-date">{{obj.comment_date}}</span>
              </div>

              <!-- START DETAIL -->
              <div class="detail-question-wrap blind">
                <div class="question-line">
                  <div class="q-line">
                      <label class="q_label">Q.</label>
                      <div v-if="obj.answer_content === 'x'" class="question-btn-wrap">
                        <span class="question-btn question-d-btn" @click="show_delete_q_popup($event.target)" :idx="obj.comment_no">삭제</span>
                      </div>
                  </div>
                  <span>{{obj.comment_content}}</span>
                </div>
                  
                <div v-if="obj.answer_content !== 'x'" class="answer-line">
                  <div class="answer-label">
                    <label class="a_label">A.</label>
                    <span class="answer-date">
                      작성일&nbsp;&nbsp;|&nbsp;&nbsp;
                      {{obj.answer_date}}
                    </span>
                  </div>
                  <span>{{obj.answer_content}}</span>
                </div>
              </div>
              <!-- END DETAIL -->
            </li>
          </ul>
        </section>
    	</div>
    	
      <section :class="{'paging-section': this.maxPage > 0, 'paging-section blind': this.maxPage === 0}">
        <section class="paging-section">
          <div class="paging-wrap">
            <span @click="prev_page" :class="{'paging-box before-page-btn hide': this.maxPage <= 5, 'paging-box before-page-btn' : this.maxPage > 5}"> &lt;&lt; </span>
              
            <div class="paging-num-wrap paging-wrap">
              <span @click="do_select_page($event.target)" v-for="num in this.forRange" :key="num" :idx="num" :class="{'paging-box paging-num choice': num === this.nowPage, 'paging-box paging-num un-choice' :num !== this.nowPage}">
                {{num}}
              </span>
            </div>
              
            <span @click="next_page" v-if="this.totalPageCnt > 5 && this.maxPage < this.totalPageCnt" class="paging-box next-page-btn">>></span>
            <span v-else class="paging-box next-page-btn hide">>></span>
            <input type="hidden" id="totalPageCnt" :value="this.totalPageCnt">
          </div>
        </section>
      </section>

    </section>
</div>
</template>

<style lang="scss" scoped >
@import '@/assets/CSS/office/question-page.scss';
</style>

<script>
import axios from 'axios';
import $ from 'jquery';

export default {
  name: 'QuestionView',
  data() {
    return {
      list: '',
      maxPage: '',
      nowPage: '',
      totalPageCnt: '',
      start: '',
      forRange: '',
      load: false,
      axiosFlag: true,
    };
  },
  mounted() {
    // 로그인 여부 체크 -> 헤더를 위해
    axios.get('http://localhost:8800/loginCheck')
      .then((response) => {
        // 로그인 되어 있음
        if (response.data.result === '1') {
          this.$store.commit('office_setLogin_true');

          /** ********** *** */
          /** * GET DATA *** */
          /** ********** *** */

          // 로딩 화면
          $('.popup-background:eq(1)').removeClass('blind');
          $('#spinner-section').removeClass('blind');

          axios.get(`http://localhost:8800/rence/question_list?user_no=${window.atob(this.$cookies.get('user_no'))}&page=1`)
            .then((res) => {
              this.list = res.data.list;
              this.maxPage = res.data.maxPage;
              this.nowPage = res.data.nowPage;
              this.totalPageCnt = res.data.totalPageCnt;
              this.start = Math.ceil(res.data.nowPage / 5.0);
              this.start = 5 * (this.start - 1) + 1;

              this.forRange = [];
              for (let i = this.start; i <= this.maxPage; i++) {
                this.forRange.push(i);
              }

              this.load = true;

              // 로딩 화면
              $('.popup-background:eq(1)').addClass('blind');
              $('#spinner-section').addClass('blind');
            })
            .catch(() => {
              // 로딩 화면
              $('.popup-background:eq(1)').addClass('blind');
              $('#spinner-section').addClass('blind');

              $('.popup-background:eq(1)').removeClass('blind');
              $('#common-alert-popup').removeClass('blind');
              $('.common-alert-txt').text('오류 발생으로 인해 처리에 실패하였습니다.');
            });
        }
        // 로그인 되어 있지 않음(or 세션 만료)
        else {
          this.$store.commit('office_setLogin_false');
          $('.popup-background:eq(0)').removeClass('blind');
          $('#disconnect-session-popup').removeClass('blind');
        }
      })
      .catch(() => {
        $('.popup-background:eq(1)').removeClass('blind');
        $('#common-alert-popup').removeClass('blind');
        $('.common-alert-txt').text('오류 발생으로 인해 로그인 여부를 불러오는데에 실패하였습니다.');
      });
  },
  methods: {
    /** 문의 리스트 클릭 시, 문의 내용 상세창 SHOW / HIDE */
    show_detail_question(param) {
      $(param).siblings('.detail-question-wrap').toggleClass('blind');
    },
    /** 댓글 삭제 버튼 -> 댓글 컨펌 팝업 SHOW */
    show_delete_q_popup(param) {
      if (this.axiosFlag) {
        this.axiosFlag = false;

        // 로그인 여부 체크 -> 헤더를 위해
        axios.get('http://localhost:8800/loginCheck')
          .then((response) => {
            this.axiosFlag = true;

            // 로그인 되어 있음
            if (response.data.result === '1') {
              this.$store.commit('office_setLogin_true');

              // 팝업창 SHOW
              const idx = $(param).attr('idx');
              $('.popup-background:eq(0)').removeClass('blind');
              $('#q-delete-popup').removeClass('blind');
              $('#q-delete-btn').attr('idx', idx);
            }
            // 로그인 되어 있지 않음(or 세션 만료)
            else {
              this.$store.commit('office_setLogin_false');
              $('.popup-background:eq(0)').removeClass('blind');
              $('#disconnect-session-popup').removeClass('blind');
            }
          })
          .catch(() => {
            this.axiosFlag = true;

            $('.popup-background:eq(1)').removeClass('blind');
            $('#common-alert-popup').removeClass('blind');
            $('.common-alert-txt').text('오류 발생으로 인해 로그인 여부를 불러오는데에 실패하였습니다.');
          });
      }

      const idx = $(param).attr('idx');
      $('.popup-background:eq(0)').removeClass('blind');
      $('#q-delete-popup').removeClass('blind');
      $('#q-delete-btn').attr('idx', idx);
    },
    /** 이전 페이지 리스트로 이동 */
    prev_page() {
      const s = Number($($('.paging-box.paging-num')[0]).text()) - 5;
      let end = Number($('.paging-box.paging-num:last').text()) - 5;

      if (end % 5 !== 0) {
        end += 5 - (end % 5);
      }

      if ($('.next-page-btn').hasClass('hide')) {
        $('.next-page-btn').removeClass('hide');
      }

      if (s === 1) {
        $('.before-page-btn').addClass('hide');
      }

      this.maxPage = end;
      this.nowPage = s;

      this.forRange = [];
      for (let i = s; i <= end; i++) {
        this.forRange.push(i);
      }

      $('.paging-box.paging-num').removeClass('choice');
      $('.paging-box.paging-num').addClass('un-choice');

      $('.paging-box.paging-num:eq(0)').click();
    },
    /** 다음 페이지 리스트로 이동 */
    next_page() {
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

      this.maxPage = last;
      this.nowPage = start;

      this.forRange = [];
      for (let i = start; i <= last; i++) {
        this.forRange.push(i);
      }

      $('.paging-box.paging-num').removeClass('choice');
      $('.paging-box.paging-num').addClass('un-choice');

      $('.paging-box.paging-num:eq(0)').click();
    },
    /** 페이지 번호에 맞는 데이터 불러오기 */
    do_select_page(param) {
      if (this.axiosFlag) {
        this.axiosFlag = false;

        // 로그인 여부 체크 -> 헤더를 위해
        axios.get('http://localhost:8800/loginCheck')
          .then((response) => {
            this.axiosFlag = true;

            // 로그인 되어 있음
            if (response.data.result === '1') {
              this.$store.commit('office_setLogin_true');

              // 로딩 화면
              $('.popup-background:eq(1)').removeClass('blind');
              $('#spinner-section').removeClass('blind');

              $('.paging-box.paging-num').removeClass('choice');
              $('.paging-box.paging-num').addClass('un-choice');

              $(param).addClass('choice');
              $(param).removeClass('un-choice');

              if (!$('.paging-box.paging-num').hasClass('choice')) {
                $('.paging-box.paging-num:eq(0)').addClass('choice');
                $('.paging-box.paging-num:eq(0)').removeClass('un-choice');
              }

              axios.get(`http://localhost:8800/rence/question_list?user_no=${window.atob(this.$cookies.get('user_no'))}&page=${$(param).attr('idx')}`)
                .then((res) => {
                  // 로딩 화면 닫기
                  $('.popup-background:eq(1)').addClass('blind');
                  $('#spinner-section').addClass('blind');

                  this.list = res.data.list;
                })
                .catch(() => {
                  // 로딩 화면 닫기
                  $('.popup-background:eq(1)').addClass('blind');
                  $('#spinner-section').addClass('blind');

                  $('.popup-background:eq(1)').removeClass('blind');
                  $('#common-alert-popup').removeClass('blind');
                  $('.common-alert-txt').text('오류 발생으로 인해 처리에 실패하였습니다.');
                });
            }
            // 로그인 되어 있지 않음(or 세션 만료)
            else {
              this.$store.commit('office_setLogin_false');
              $('.popup-background:eq(0)').removeClass('blind');
              $('#disconnect-session-popup').removeClass('blind');
            }
          })
          .catch(() => {
            this.axiosFlag = true;

            $('.popup-background:eq(1)').removeClass('blind');
            $('#common-alert-popup').removeClass('blind');
            $('.common-alert-txt').text('오류 발생으로 인해 로그인 여부를 불러오는데에 실패하였습니다.');
          });
      }
    },
  }, // END methods()
};
</script>
