<!--
--  @author 전판근, 김예은
-- @refactoring 전판근
-->

<!-- eslint-disable max-len -->
<!-- eslint-disable vuejs-accessibility/click-events-have-key-events -->
<!-- eslint-disable vuejs-accessibility/form-control-has-label -->
<!-- eslint-disable max-len -->
<template>
  <div class="titleSection">
    <h1>예약 관리</h1>
  </div>
  <!-- END titleSection -->

  <div class="boardWrap reserve">
    <section class="reserve-header">
      <div class="searchBar-section">
        <div class="searchBar-wrap">
          <div id="type" class="sb_1 type">
            <!-- <span>타입</span> -->
            <span>예약자</span>
          </div>
          <div class="sb_2">
            <input @keyup.enter="reserveSearch" type="text" placeholder="검색어를 입력하세요." id="input_searchBar"
              v-model.trim="searchWord" />
          </div>

          <!-- CUSTOM SELECT -->
          <section>
            <!-- START TYPE SELECT -->
            <div id="custom-type-select" class="type-select-wrap blind">
              <ul class="type-select">
                <li id="type-list-desk" class="type-select-list" type="desk">데스크</li>
                <li id="type-list-metting-room" class="type-select-list" type="meeting-room">회의실</li>
                <li id="type-list-office" class="type-select-list" type="office">오피스</li>
              </ul>
            </div>
            <!-- END TYPE SELECT -->
          </section>
        </div>
      </div>

      <ul class="reserve-filter-list">
        <li @click="selectMiniNav" id="reserve-all" class="reserve-item">전체</li>
        <li @click="selectMiniNav" id="reserve-ing" class="reserve-item">예약중
        </li>
        <li @click="selectMiniNav" id="reserve-cancel" class="reserve-item">취소
        </li>
        <li @click="selectMiniNav" id="reserve-end" class="reserve-item">종료</li>
      </ul>
    </section>
    <!-- END reserve-header -->

    <section class="reserveWrap">
      <div class="custom-table">
        <!--<input type="hidden" th:attr="maxCnt=${res.maxCnt}, nowCnt=${res.nowCnt}" id="maxCnt" />-->
        <div class="ct-header reserve">
          <div class="ct-header-cell reserve">예약 날짜</div>
          <div class="ct-header-cell reserve">진행 여부</div>
          <div class="ct-header-cell reserve">예약 공간</div>
          <div class="ct-header-cell reserve">예약자</div>
          <div class="ct-header-cell reserve">연락처</div>
          <div class="ct-header-cell reserve">예약자 이메일</div>
          <div class="ct-header-cell reserve">예약 금액</div>
          <div class="ct-header-cell reserve">취소 버튼</div>
        </div>
        <!-- END ct-header -->

        <div class="ct-body reserve-ct">
          <!-- START ct-body-row reserve -->
          <div class="ct-body-row reserve" v-for="vos in r_vos" :key="vos">
            <div class="ct-body-cell reserve reserve_date_set">
              {{ vos.reserve_sdate }} ~ <br />{{ vos.reserve_edate }}
            </div>
            <div class="ct-body-cell reserve reserve_state">
              <button v-if="vos.reserve_state === 'begin'" class="ct-body-btn reserve-ing">이용전</button>
              <button v-if="vos.reserve_state === 'in_use'" class="ct-body-btn reserve-doing">이용중</button>
              <button v-if="vos.reserve_state === 'end'" class="ct-body-btn reserve-end">이용완료</button>
              <button v-if="vos.reserve_state === 'cancel'" class="ct-body-btn reserve-cancel">취소</button>
            </div>

            <div class="ct-body-cell reserve reserve_room_name">{{ vos.room_name }}</div>
            <div class="ct-body-cell reserve reserve_user_name">{{ vos.user_name }}</div>
            <div class="ct-body-cell reserve reserve_user_tel">{{ vos.user_tel }}</div>
            <div class="ct-body-cell reserve reserve_user_email">{{ vos.user_email }}</div>
            <div class="ct-body-cell reserve reserve_price">
              {{ vos.actual_payment }}원 /
              <span v-if="vos.payment_state === 'T'">선</span>
              <span v-if="vos.payment_state === 'F'">후</span>
            </div>
            <div class="ct-body-cell reserve reserve-btn-cell">
              <button class="ct-body-btn reserve-ing reserve_is_cancle" v-if="vos.reserve_state === 'begin'"
                :reserve_no="vos.reserve_no" :user_no="vos.user_no" :user_email="vos.user_email"
                :reserve_stime="vos.reserve_sdate" :reserve_etime="vos.reserve_edate" @click="clickReserveIsCancel">예약
                취소</button>

              <button class="ct-body-btn reserve-end" v-if="vos.reserve_state !== 'begin'">취소불가</button>
            </div>
          </div>
          <!-- END ct-body-row reserve -->
        </div>
        <!-- END ct-body -->

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
      </div>
      <!-- END custom-table -->
    </section>
    <!-- END reserveWrap -->
  </div>
  <!-- boardWrap reserve -->
</template>

<style>
@import '@/assets/CSS/dash-board/dash-reserve-list.scss';
</style>

<script>
import $ from 'jquery';
import axios from 'axios';

export default {
  name: 'ReserveView',

  data() {
    return {
      backoffice_no: decodeURIComponent(window.atob(this.$cookies.get('backoffice_no'))),
      reserve_state: 'all',
      r_vos: [],
      res: [],
      cnt: 0,
      maxPage: 0,
      nowPage: 0,
      nowCnt: 0,
      totalPageCnt: 0,
      searchword: '',
    };
  },

  watch: {
    $route() {
      $('#reserve-all').removeClass('active');
      $('#reserve-ing').removeClass('active');
      $('#reserve-cancel').removeClass('active');
      $('#reserve-end').removeClass('active');

      this.miniNavActive(this.reserve_state);
    },
  },

  methods: {

    miniNavActive(reserveState) {
      switch (reserveState) {
        case 'all':
          $('#reserve-all').addClass('active');
          break;
        case 'in_use':
          $('#reserve-ing').addClass('active');
          break;
        case 'cancel':
          $('#reserve-cancel').addClass('active');
          break;
        case 'end':
          $('#reserve-end').addClass('active');
          break;
        default:
          break;
      }
    },

    selectMiniNav(e) {
      const targetId = e.target.id;
      switch (targetId) {
        case 'reserve-all':
          this.reserve_state = 'all';
          $('#reserve-all').addClass('active');
          break;

        case 'reserve-ing':
          this.reserve_state = 'in_use';
          break;
        case 'reserve-cancel':
          this.reserve_state = 'cancel';
          break;
        case 'reserve-end':
          this.reserve_state = 'end';
          break;

        default:
          break;
      }

      this.$router.push(`/backoffice/dash/reserve?backoffice_no=${this.$cookies.get('backoffice_no')}&reserve_state=${this.reserve_state}&page=1`);

      this.$nextTick(() => {
        this.getReserveList();
      });
    },

    reserveSearch(e) {
      const searchword = e.target.value;

      // 로딩 화면
      $('.popup-background:eq(1)').removeClass('blind');
      $('#spinner-section').removeClass('blind');

      const params = new URLSearchParams();
      params.append('backoffice_no', this.backoffice_no);
      params.append('searchword', searchword.trim());
      params.append('reserve_state', this.reserve_state);

      const url = `http://localhost:8800/backoffice/dash/search_reserve?${params}`;
      axios.get(url).then((res) => {
        $('.popup-background:eq(1)').addClass('blind');
        $('#spinner-section').addClass('blind');
        this.r_vos = res.data.r_vos;
      });
    },

    getReserveList() {
      const params = new URLSearchParams();
      params.append('backoffice_no', this.backoffice_no);
      params.append('reserve_state', this.reserve_state);
      const url = `http://localhost:8800/backoffice/dash/reserve?${params}`;

      axios.get(url).then((res) => {
        this.r_vos = res.data.r_vos;
        this.cnt = res.data.cnt;
        this.maxPage = res.data.maxPage;
        this.nowCnt = res.data.nowCnt;
        this.nowPage = res.data.nowPage;
        this.totalPageCnt = res.data.totalPageCnt;
      });
    },

    clickReserveIsCancel(e) {
      $('.popup-background:eq(0)').removeClass('blind');
      $('#reserve-delete-one-popup').removeClass('blind');

      const reserve_no = e.target.getAttribute('reserve_no');
      const user_no = e.target.getAttribute('user_no');
      const user_email = e.target.getAttribute('user_email');
      const reserve_stime = e.target.getAttribute('reserve_stime');
      const reserve_etime = e.target.getAttribute('reserve_etime');

      $('#reserve-delete-one-btn').attr('reserve_no', reserve_no);
      $('#reserve-delete-one-btn').attr('user_no', user_no);
      $('#reserve-delete-one-btn').attr('user_email', user_email);
      $('#reserve-delete-one-btn').attr('reserve_stime', reserve_stime);
      $('#reserve-delete-one-btn').attr('reserve_etime', reserve_etime);
    },

    paging_list(e) {
      this.nowPage = $(e).text();

      const params = new URLSearchParams();
      params.append('backoffice_no', this.backoffice_no);
      params.append('reserve_state', this.reserve_state);
      params.append('page', this.nowPage);

      axios.get(`http://localhost:8800/backoffice/dash/reserve?${params}`)
        .then((res) => {
          this.r_vos = res.data.r_vos;
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
      params.append('reserve_state', this.reserve_state);
      params.append('page', start);

      axios.get(`http://localhost:8800/backoffice/dash/reserve?${params}`)
        .then((res) => {
          this.r_vos = res.data.r_vos;
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
      params.append('reserve_state', this.reserve_state);
      params.append('page', start);

      axios.get(`http://localhost:8800/backoffice/dash/reserve?${params}`)
        .then((res) => {
          this.r_vos = res.data.r_vos;
        });
    },
  },

  mounted() {
    axios.get('http://localhost:8800/backoffice/loginCheck')
      .then((res) => {
        if (res.data.result === '1') {
          this.$nextTick(() => {
            this.miniNavActive(this.reserve_state);
            this.getReserveList();
          });
        } else {
          this.$store.commit('backoffice_setLogin_false');
          this.$router.replace('/backoffice/landing');
        }
      });
  },
};
</script>
