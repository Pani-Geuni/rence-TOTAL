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
  <div v-if="load === true" class="reserve-list-wrap">
    <div class="reserve-list">
      <section class="page-title-section">
        <span class="page-title">
          <span class="timePoint-select-wrap" @click="show_timePoint_popup">
            <span v-if="type === 'now'" class="timePoint-value" time-point="now">현재</span>
            <span v-if="type === 'before'" class="timePoint-value" time-point="before">과거</span>
            <img src="@/assets/IMG/space-introduce/full-dropdown.svg" alt="full-dropdown" class="full-dropdown" />
          </span>
          <span>예약한 내역을 보여드려요.</span>
        </span>
          
        <div class="timePoint-custom-select-wrap blind">
          <ul class="timePoint-custom-select">
            <li id="timePoint-now" @click="set_timePoint($event.target)" class="timePoint-custom-select-li">현재</li>
            <li id="timePoint-before" @click="set_timePoint($event.target)" class="timePoint-custom-select-li">과거</li>
          </ul>
        </div>
      </section>

      <section class="reserve-list-section">
        <!-- 예약 내역 NULL일 때 -->
        <section class = "reserve-null" :class="{'reserve-null' : this.cnt === 0, 'reserve-null blind' : this.cnt !== 0}">
          <div class="null-first-section">
            <span class="advice-txt">예약 내역이 비어있습니다.</span>
          </div>

          <div class="null-second-section">
            <span class="advice-txt-small">원하는 공간을 예약하러 갈까요?</span>
            <router-link to="/">
              <input type="button" id="go-home-btn" class="go-home" value="홈으로 가기"/>
            </router-link>
          </div>
        </section>
        
        <!-- 예약 내역 NOT NULL일 때 -->
        <section :class="{'reserve-not-null' : this.cnt !== 0, 'reserve-not-null blind' : this.cnt === 0}">
          <section class="box-section">
            <div v-for="obj in list" :key="obj" @click="go_reserve_info($event.target)" class = "reserve-box before" v-bind:idx ="obj.reserve_no">
              <section class="box-img-section">
                <img :src="obj.backoffice_image" alt ="default-space-img" class="space-img" />
              </section>

              <section class="box-txt-section">
                <span class="b-office-name">{{obj.company_name}}</span>
                <span  class="b-office-location">
                  <img src = "@/assets/IMG/office/location-icon.svg" alt="location-icon" class ="location-icon" />
                  <span class="location-name">{{obj.roadname_address}}</span>
                </span>
                <span class="reserve-time">
                  <span class="reserve-start-time">{{obj.reserve_sdate}} ~ </span>
                  <span class="reserve-end-time">{{obj.reserve_edate}}</span>
                </span>
                <span>
                  <span class="price-label">총 금액</span>
                  <span class="price">{{obj.payment_total}}원</span>
                </span>
              </section>
            </div>
          </section>

          <section :class="{'paging-section': maxPage > 0, 'paging-section blind': maxPage === 0}">
            <section class="paging-section">
              <div class="paging-wrap">
                <span @click="prev_page" :class="{'paging-box before-page-btn hide': maxPage <= 5, 'paging-box before-page-btn' : maxPage > 5}"> &lt;&lt; </span>
                  
                <div class="paging-num-wrap paging-wrap">
                  <span @click="do_select_page($event.target)" v-for="num in forRange" v-bind:key="num" v-bind:idx="num" :class="{'paging-box paging-num choice': num === nowPage, 'paging-box paging-num un-choice' :num !== nowPage}">{{num}}</span>
                </div>
                  
                <span @click="next_page" v-if="totalPageCnt > 5 && maxPage < totalPageCnt" class="paging-box next-page-btn">>></span>
                <span v-else class="paging-box next-page-btn hide">>></span>
                <input type="hidden" id="totalPageCnt" v-bind:value="totalPageCnt">
              </div>
            </section>
          </section>

        </section>
      </section>
    </div>
  </div>
</template>

<style lang="scss" scoped >
  @import '@/assets/CSS/office/reserve-list.scss';
</style>

<script>
import axios from 'axios';
import $ from 'jquery';

export default {
  name: 'ReserveListView',
  data() {
    return {
      type: '',
      cnt: '',
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
          this.$is_officeLogin = 'true';

          /** ********** *** */
          /** * GET DATA *** */
          /** ********** *** */

          // 로딩 화면
          $('.popup-background:eq(1)').removeClass('blind');
          $('#spinner-section').removeClass('blind');

          axios.get(`http://localhost:8800/rence/reserve_list?time_point=now&user_no=${window.atob(this.$cookies.get('user_no'))}&page=1`)
            .then((res) => {
              this.type = res.data.type;
              this.cnt = res.data.cnt;

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
          this.$is_officeLogin = 'false';
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
    show_timePoint_popup() {
      $('.timePoint-custom-select-wrap').toggleClass('blind');
    },
    /** 현재 / 과거에 따른 예약 리스트 불러오는 함수 */
    set_timePoint(param) {
      if (this.axiosFlag) {
        this.axiosFlag = false;

        // 로그인 여부 체크 -> 헤더를 위해
        axios.get('http://localhost:8800/loginCheck')
          .then((response) => {
            this.axiosFlag = true;

            // 로그인 되어 있음
            if (response.data.result === '1') {
              this.$is_officeLogin = 'true';

              $('.timePoint-value').text($(param).text());
              $('.timePoint-custom-select-wrap').addClass('blind');

              let URL = '';
              if ($(param).attr('id') === 'timePoint-now') {
                $('.timePoint-value').attr('time-point', 'now');
                URL = `http://localhost:8800/rence/reserve_list?time_point=now&user_no=${window.atob(this.$cookies.get('user_no'))}&page=1`;
              } else if ($(param).attr('id') === 'timePoint-before') {
                $('.timePoint-value').attr('time-point', 'before');
                URL = `http://localhost:8800/rence/reserve_list?time_point=before&user_no=${window.atob(this.$cookies.get('user_no'))}&page=1`;
              }

              // 로딩 화면
              $('.popup-background:eq(1)').removeClass('blind');
              $('#spinner-section').removeClass('blind');

              axios.get(URL)
                .then((res) => {
                  // 로딩 화면 닫기
                  $('.popup-background:eq(1)').addClass('blind');
                  $('#spinner-section').addClass('blind');

                  this.type = res.data.type;
                  this.cnt = res.data.cnt;

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
              this.$is_officeLogin = 'false';
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

      // const sample = $('.paging-num-wrap>.paging-box.paging-num:eq(0)').clone();
      // $('.paging-num-wrap').empty();

      // for (let i = s; i <= end; i++) {
      //   const sampleSpan = sample.clone();

      //   sampleSpan.text(i);
      //   sampleSpan.attr('idx', i);
      //   sampleSpan.removeClass('choice');
      //   sampleSpan.addClass('un-choice');

      //   $('.paging-num-wrap').append(sampleSpan);
      // }
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

      // const sample = $('.paging-num-wrap>.paging-box.paging-num:eq(0)').clone();
      // $('.paging-num-wrap').empty();

      // for (let i = start; i <= last; i++) {
      //   const sampleSpan = sample.clone();

      //   sampleSpan.text(i);
      //   sampleSpan.attr('idx', i);
      //   sampleSpan.removeClass('choice');
      //   sampleSpan.addClass('un-choice');

      //   $('.paging-num-wrap').append(sampleSpan);
      // }
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
              this.$is_officeLogin = 'true';

              // 로딩 화면
              $('.popup-background:eq(1)').removeClass('blind');
              $('#spinner-section').removeClass('blind');

              axios.get(`http://localhost:8800/rence/reserve_list?time_point=${this.type}&user_no=${window.atob(this.$cookies.get('user_no'))}&page=${$(param).attr('idx')}`)
                .then((res) => {
                  // 로딩 화면 닫기
                  $('.popup-background:eq(1)').addClass('blind');
                  $('#spinner-section').addClass('blind');

                  // this.type = res.data.type;
                  // this.cnt = res.data.cnt;

                  this.list = res.data.list;
                  // this.maxPage = res.data.maxPage;
                  // this.nowPage = res.data.nowPage;
                  // this.totalPageCnt = res.data.totalPageCnt;
                  // this.start = Math.ceil(res.data.nowPage / 5.0);
                  // this.start = 5 * (this.start - 1) + 1;

                  // this.forRange = [];
                  // for (let i = this.start; i <= this.maxPage; i++) {
                  //   this.forRange.push(i);
                  // }
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
              this.$is_officeLogin = 'false';
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
    go_reserve_info(param) {
      if (this.axiosFlag) {
        this.axiosFlag = false;

        // 로그인 여부 체크 -> 헤더를 위해
        axios.get('http://localhost:8800/loginCheck')
          .then((response) => {
            this.axiosFlag = true;

            // 로그인 되어 있음
            if (response.data.result === '1') {
              this.$is_officeLogin = 'true';

              const url = window.location.href.split('/reserve_list')[0];

              if (this.type === 'before') {
                window.location.href = `${url}/reserved_info/reserve_no=${$(param).attr('idx')}`;
              } else if (this.type === 'now') {
                window.location.href = `${url}/reserve_info/reserve_no=${$(param).attr('idx')}`;
              }
            }
            // 로그인 되어 있지 않음(or 세션 만료)
            else {
              this.$is_officeLogin = 'false';
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
