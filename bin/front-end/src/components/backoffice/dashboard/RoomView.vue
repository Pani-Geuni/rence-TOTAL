<!-- eslint-disable vuejs-accessibility/click-events-have-key-events -->
<!-- eslint-disable max-len -->
<template>
  <div class="titleSection">
    <h1>공간 관리</h1>
    <button @click="addRoom" id="btn-room-add" class="btn-room-add">추가</button>
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

  <div class="boardWrap room-wrapping">
    <div class="room-list">
      <div class="room-item" v-for="vos in rm_vos" :key="vos">
        <div class="item-header">
          <p>
            {{ vos.room_name }}
          </p>
          <div class="item-buttons">
            <button @click="editRoom" class="btn-room-edit" :idx="vos.room_no">수정</button>
            <button @click="deleteRoom" class="btn-room-delete" :idx="vos.room_no">삭제</button>
          </div>
          <!-- END item-buttons -->
        </div>
        <!-- END item-header -->
        <div class="item-body">
          <p id="room-type">
            <span v-if="vos.room_type === 'desk'">데스크</span>
            <span v-if="vos.room_type === 'meeting_04'">4인 회의실</span>
            <span v-if="vos.room_type === 'meeting_06'">6인 회의실</span>
            <span v-if="vos.room_type === 'meeting_10'">10인 회의실</span>
            <span v-if="vos.room_type === 'office'">오피스</span>
          </p>

          <div>
            <span id="room-price" class="room-price">{{ vos.room_price }}</span>
            <span id="unit">{{ unit }}</span>
          </div>
        </div>
        <!-- END item-body -->
      </div>
    </div>
    <!-- END room-list -->
  </div>
  <!-- END boardWrap -->

  <section class="paging-section" v-if="res.maxPage > 0">
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

      <span v-if="res.totalPageCnt > 5 && res.maxPage < res.totalPageCnt" class="paging-box next-page-btn">
        >>
      </span>
      <span v-if="res.totalPageCnt > 5 && res.maxPage < res.totalPageCnt"
        class="paging-box next-page-btn hide">>></span>
      <input type="hidden" id="totalPageCnt" :value="res.totalPageCnt">
    </div>
  </section>
</template>

<style lang="scss" scoped>
@import '@/assets/CSS/dash-board/dash-space-list.scss';
</style>

<script>
import $ from 'jquery';
import axios from 'axios';

export default {
  name: 'RoomView',

  data() {
    return {
      backoffice_no: this.$cookies.get('backoffice_no'),
      // room_type: [],
      rm_vos: [],
      res: [],
      unit: '',
      path: 'room',
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

    getRoomList() {
      const params = new URLSearchParams();
      params.append('backoffice_no', this.backoffice_no);
      const url = `http://localhost:8800/backoffice/dash/room?backoffice_no=${this.backoffice_no}&page=1`;

      axios.get(url)
        .then((res) => {
          this.rm_vos = res.data.rm_vos;
          this.unit = res.data.unit;
        });
    },

    addRoom() {
      $('.popup-background:eq(0)').removeClass('blind');
      $('#room-insert-section').removeClass('blind');
    },

    editRoom(e) {
      // 로딩 화면
      $('.popup-background:eq(1)').removeClass('blind');
      $('#spinner-section').removeClass('blind');

      axios.get('http://localhost:8800/backoffice/dash/update_room', {
        params: {
          backoffice_no: this.backoffice_no,
          room_no: e.target.getAttribute('idx'),
        },
      }).then((res) => {
        // 로딩 화면 닫기
        $('.popup-background:eq(1)').addClass('blind');
        $('#spinner-section').addClass('blind');

        $('.popup-background:eq(0)').removeClass('blind');
        $('#room-edit-section').removeClass('blind');
        $('#btn-edit').attr('idx', res.data.rmvo.room_no);

        $('#m-edit_room_type').val(res.data.rmvo.room_type);
        $('#m-input-room-name').val(res.data.rmvo.room_name);
        $('#m-input-price-name').val(res.data.rmvo.room_price);

        let typeName = '';
        if (res.data.rmvo.room_type === 'desk') typeName = '데스크';
        else if (res.data.rmvo.room_type === 'meeting_04') typeName = '미팅룸(4인)';
        else if (res.data.rmvo.room_type === 'meeting_06') typeName = '미팅룸(6인)';
        else if (res.data.rmvo.room_type === 'meeting_10') typeName = '미팅룸(10인)';
        else if (res.data.rmvo.room_type === 'office') typeName = '오피스';

        if (typeName === '오피스') {
          $('.room-input-wrap:eq(5)').removeClass('blind');
        } else {
          $('#m-input-price-name').val('');
          $('.room-input-wrap:eq(5)').addClass('blind');
        }

        $('#m-edit-room-type-label').val(res.data.rmvo.room_type);
        $('#m-edit-room-type-label').text(typeName);
      }).catch(() => {
        // 로딩 화면 닫기
        $('.popup-background:eq(1)').addClass('blind');
        $('#spinner-section').addClass('blind');

        $('.popup-background:eq(1)').removeClass('blind');
        $('#common-alert-popup').removeClass('blind');
        $('.common-alert-txt').text('오류 발생으로 인해 처리에 실패하였습니다.');
      });
    },

    deleteRoom(e) {
      $('.popup-background:eq(0)').removeClass('blind');
      $('#space-delete-popup').removeClass('blind');
      $('#delete-space-btn').attr('idx', e.target.getAttribute('idx'));
    },

  },

  mounted() {
    this.$nextTick(() => {
      this.miniNavActive(window.location.pathname);
      this.getRoomList();
    });
  },
};
</script>
