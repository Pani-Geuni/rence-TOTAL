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
  <div v-if="load === true" class="mileage-list-wrap">
    <div class="reserve-list">
      <section class="page-title-section">
        <span class="page-title">지금까지 쌓인 마일리지를 확인하세요</span>
      </section>

      <section class="mileage-section">
        <section class="mileage-section">
          <section class="mileage">
            <span class="mileage-label">현재 마일리지</span>
            <span id="now-mileage" class="mileage-won">{{this.mileage_total}} 원</span>
          </section>
        </section>
      </section>

      <section class="mileage-list-section">
        <section class="mileage-list-menu">
          <span id = "all" @click="choice_menu($event.target)" :class="{'menus choice' : this.searchKey == 'all', 'menus un-choice' : this.searchKey != 'all'}">전체</span>
          <span id = "plus" @click="choice_menu($event.target)" :class="{'menus choice' : this.searchKey == 'plus', 'menus un-choice' : this.searchKey != 'plus'}">적립</span>
          <span id = "minus" @click="choice_menu($event.target)" :class="{'menus choice' : this.searchKey == 'minus', 'menus un-choice' : this.searchKey != 'minus'}">사용</span>
        </section>

        <section class="mileage-history-section">
          <section class="mileage-history-title-section">
            <div class="list-title-txt-wrap">
              <span class="list-title-txt list-status">상태</span>
              <span class="list-title-txt list-mileage">마일리지</span>
              <span class="list-title-txt list-content">내용</span>
              <span class="list-title-txt list-date">적용일</span>
            </div>
          </section>
                          
          <section class="mileage-history-wrap">
            <ul>
              <li class="mileage-list" v-for="obj in list" v-bind:key="obj">
                <span v-if="obj.state === 'F'" class="list-status ">사용</span>
                <span v-if="obj.state === 'T'" class="list-status plus-price-label">적립</span>
                
                <span v-if="obj.state === 'T'" class="list-mileage plus-price">+{{obj.mileage}}</span>
                <span v-if="obj.state === 'F'" class="list-mileage minus-price">-{{obj.mileage}}</span>

                <span class="list-content">{{obj.room}}</span>
                <span class="list-date">{{obj.date}}</span>
              </li>
            </ul>
          </section>
        </section>

        <!-- START PAGING -->
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
        <!-- END PAGING -->
      </section>
    </div>
  </div>
</template>

<style lang="scss" scoped >
  @import '@/assets/CSS/office/mileage-page.scss';
</style>

<script>
import axios from 'axios';
import $ from 'jquery';

export default {
  name: 'MileageView',
  data() {
    return {
      mileage_total: '',
      searchKey: '',
      list: '',
      maxPage: '',
      nowPage: '',
      totalPageCnt: '',
      start: '',
      forRange: '',
      searchFlag: false,
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

          axios.get(`http://localhost:8800/rence/mileage?user_no=${window.atob(this.$cookies.get('user_no'))}&page=1`)
            .then((res) => {
              console.log(res.data);
              this.mileage_total = res.data.mileage_total;
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
    /** 마일리지 세부 메뉴 선택 클릭 이벤트 */
    choice_menu(param) {
      if (this.axiosFlag) {
        this.axiosFlag = false;

        // 로그인 여부 체크 -> 헤더를 위해
        axios.get('http://localhost:8800/loginCheck')
          .then((response) => {
            this.axiosFlag = true;

            // 로그인 되어 있음
            if (response.data.result === '1') {
              this.$store.commit('office_setLogin_true');
              $('.menus').removeClass('choice');
              $('.menus').addClass('un-choice');

              $(param).removeClass('un-choice');
              $(param).addClass('choice');

              // 로딩 화면
              $('.popup-background:eq(1)').removeClass('blind');
              $('#spinner-section').removeClass('blind');

              axios.get(`http://localhost:8800/rence/mileage_search_list?searchKey=${$(param).attr('id')}&user_no=${window.atob(this.$cookies.get('user_no'))}&page=1`)
                .then((res) => {
                // 로딩 화면 닫기
                  $('.popup-background:eq(1)').addClass('blind');
                  $('#spinner-section').addClass('blind');

                  this.list = res.data.list;
                  this.maxPage = res.data.maxPage;
                  this.nowPage = res.data.nowPage;
                  this.totalPageCnt = res.data.totalPageCnt;
                  this.start = Math.ceil(res.data.nowPage / 5.0);
                  this.start = 5 * (this.start - 1) + 1;
                  this.searchFlag = true;
                  this.searchKey = $(param).attr('id');

                  this.forRange = [];
                  for (let i = this.start; i <= this.maxPage; i++) {
                    this.forRange.push(i);
                  }
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
      $('.paging-box.paging-num:eq(0)').addClass('choice');
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

              let URL = '';
              if (this.searchFlag) {
                URL = `http://localhost:8800/rence/mileage_search_list?searchKey=${this.searchKey}&user_no=${window.atob(this.$cookies.get('user_no'))}&page=${$(param).attr('idx')}`;
              } else {
                URL = `http://localhost:8800/rence/mileage?user_no=${window.atob(this.$cookies.get('user_no'))}&page=${$(param).attr('idx')}`;
              }

              axios.get(URL)
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
