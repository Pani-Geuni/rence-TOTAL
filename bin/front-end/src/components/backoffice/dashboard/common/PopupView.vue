<!-- eslint-disable vuejs-accessibility/click-events-have-key-events -->
<!-- eslint-disable vuejs-accessibility/heading-has-content -->
<!-- eslint-disable max-len -->
<!-- eslint-disable vuejs-accessibility/form-control-has-label -->
<template>
  <!-- **************** -->
  <!-- popup background -->
  <!-- **************** -->
  <div class="popup-background blind">

    <!-- START LOGOUT CONFIRM POPUP -->
    <div id="logout-popup" class="confirm-popup blind">
      <section class="confirm-txt-section">
        <span class="logout-txt">
          로그아웃 하시겠습니까?<br><br>
          로그아웃 시 메인페이지로 이동됩니다.
        </span>
      </section>
      <section class="confirm-btn-section">
        <div id="logout-btn" class="confirm-yesBtn">로그아웃</div>
        <div @click="closeLogoutPopup" id="logout-closeBtn" class="confirm-noBtn">닫기</div>
      </section>
    </div>
    <!-- END logout popup -->

    <!-- 공간 추가 팝업 Section -->
    <div id="room-insert-section" class="blind">
      <section class="room-popup-title-section">
        <span>공간 추가</span>
      </section>
      <section class="room-popup-input-section">
        <div class="room-input-wrap">
          <p>공간 이름</p>
          <input @click="resetWarning" type="text" v-model="room_name" name="room_name" id="input-room-name"
            class="room-input" placeholder="공간 이름을 입력해 주세요" />
        </div>
        <div class="room-input-wrap">
          <p>공간 타입</p>
          <div class="room-type-select" id="room-type-select">
            <button @click="clickRoomTypeSelectList" name="room_type" id="insert-room-type-label"
              class="room-type-label">타입을 선택해
              주세요</button>
            <ul class="insert-type-select-list blind">
              <li @click="clickTypeSelectItem" class="insert-type-select-item" v-for="roomType in kor_room_type"
                :key="roomType" :roomType="roomType">{{ roomType }}</li>
            </ul>
          </div>
          <!-- END room-type-select -->
        </div>
        <!-- END room-type-wrap -->
        <div class="room-input-wrap blind">
          <p>가격을 입력해 주세요.</p>
          <div>
            <input type="number" name="room_price" id="input-price-name" class="room-input"
              placeholder="가격을 입력해 주세요.(숫자만 입력 가능)" value="" />
            <span class="r-input-warning blind">숫자만 입력가능합니다.</span>
          </div>
        </div>
      </section>
      <!-- room-popup-input-section -->
      <div class="select-btn-group">
        <button @click="closeInsertRoomPopup" id="btn-insert-cancel" class="select-btn">닫기</button>
        <button @click="insert_room" id="btn-insert" class="select-btn">추가</button>
      </div>
      <!-- END select-btn-group -->
    </div>
    <!-- END room-insert-section -->

    <!-- 공간 수정 팝업 Section -->
    <div id="room-edit-section" class="blind">
      <section class="room-popup-title-section">
        <span>공간 변경</span>
      </section>
      <section class="room-popup-input-section">
        <div class="room-input-wrap">
          <p>공간 이름</p>
          <input type="text" name="room_name" id="m-input-room-name" class="room-input" placeholder="공간 이름을 입력해 주세요" />
        </div>
        <div class="room-input-wrap">
          <p>공간 타입</p>
          <div class="room-type-select" id="m-room-type-select">
            <button @click="editRoomTypeLabel" name="room_type" id="m-edit-room-type-label" class="room-type-label">타입을
              선택해
              주세요</button>
            <ul class="edit-type-select-list blind">
              <li @click="clickEditTypeSelectItem" class="edit-type-select-item" v-for="roomType in kor_room_type"
                :key="roomType" :roomType="roomType">{{ roomType }}</li>
            </ul>
          </div>
          <!-- END room-type-select -->
        </div>
        <!-- END room-type-wrap -->
        <div class="room-input-wrap">
          <p>가격을 입력해 주세요.</p>
          <div>
            <input type="number" v-model="input_price_name" name="room_price" id="m-input-price-name" class="room-input"
              placeholder="가격을 입력해 주세요.(숫자만 입력 가능)" />
            <span class="r-input-warning blind">숫자만 입력가능합니다.</span>
          </div>
        </div>
      </section>
      <!-- room-popup-input-section -->
      <div class="select-btn-group">
        <button @click="closeEditPopup" id="btn-edit-cancel" class="select-btn">닫기</button>
        <button @click="editRoom" id="btn-edit" class="select-btn">변경</button>
      </div>
      <!-- END select-btn-group -->
    </div>
    <!-- END room-edit-section -->

    <!-- START DELETE CONFIRM POPUP -->
    <div id="space-delete-popup" class="confirm-popup blind">
      <section class="confirm-txt-section">
        <span class="delete-txt">
          해당 공간을 정말 삭제하시겠습니까? <br />
          삭제하시면 다시 복구 할 수 없습니다
        </span>
      </section>
      <section class="confirm-btn-section">
        <div @click="delete_room" id="delete-space-btn" class="confirm-yesBtn">삭제</div>
        <div @click="closeDeletePopup" id="delete-closeBtn" class="confirm-noBtn">닫기</div>
      </section>
    </div>
    <!-- END DELETE FONFIRM POPUP -->

    <!-- START DELETE CONFIRM POPUP -->
    <div id="answer-delete-popup" class="confirm-popup blind">
      <section class="confirm-txt-section">
        <span class="delete-txt">
          답글을 삭제하시겠습니까?
        </span>
      </section>
      <section class="confirm-btn-section">
        <div @click="clickAnswerDeleteBtn" id="delete-answer-btn" class="confirm-yesBtn">삭제</div>
        <div @click="closeAnswerDeletePopup" id="delete-answer-closeBtn" class="confirm-noBtn">닫기</div>
      </section>
    </div>
    <!-- END DELETE FONFIRM POPUP -->

    <!-- START SUCCESS CUSTOM ALERT POPUP -->
    <div id="insert-success-alert-popup" class="alert-popup blind">
      <section class="alert-txt-section">
        <span>공간이 성공적으로 추가 되었습니다.</span>
      </section>
      <section @click="closeSuccessAlert" id="insert-success-alert-btn" class="alert-btn-section">
        <span>확인</span>
      </section>
    </div>

    <!-- START SUCCESS CUSTOM ALERT POPUP -->
    <div id="edit-success-alert-popup" class="alert-popup blind">
      <section class="alert-txt-section">
        <span>공간이 성공적으로 변경 되었습니다.</span>
      </section>
      <section @click="closeEditSuccessAlert" id="edit-success-alert-btn" class="alert-btn-section">
        <span>확인</span>
      </section>
    </div>

    <!-- START SUCCESS CUSTOM ALERT POPUP -->
    <div id="delete-success-alert-popup" class="alert-popup blind">
      <section class="alert-txt-section">
        <span>공간이 성공적으로 삭제 되었습니다.</span>
      </section>
      <section id="delete-success-alert-btn" class="alert-btn-section">
        <span>확인</span>
      </section>
    </div>

    <!-- START comment popup -->
    <div id="comment-section" class="blind">
      <section class="comment-popup-title-section">
        <span>답변 달기</span>
      </section>
      <section class="user-comment-section">
        <div class="user-comment-title">
          <h5>문의 공간</h5>
          <p id="q_room_name">101호 개인 스터디룸</p>
        </div>
        <div class="user-comment-content">
          <h5>문의 내용</h5>
          <pre id="user_comment">
        문의내용 문의내용 문의내용 문의내용 문의내용 문의내용 문의내용 문의내용 문의내용 문의내용 문의내용 문의내용 문의내용 문의내용 문의내용 문의내용 문의내용 문의내용 문의내용
        문의내용 문의내용 문의내용 문의내용 문의내용 문의내용 문의내용 문의내용 문의내용 문의내용 문의내용 </pre>
        </div>
      </section>
      <section class="host-comment-section">
        <h5>답변</h5>
        <textarea name="host-comment" id="host-comment" placeholder="문의 답변을 입력해 주세요." @keyup="commentTextLength"
          @keydown="commentTextLength"></textarea>
      </section>

      <div class="button-group">
        <button @click="closeCommentCancel" class="btn-comment-cancel">취소</button>
        <button @click="insertHostComment" id="h_comment_insert" class="btn-comment-confirm">등록</button>
      </div>
    </div>
    <!-- END popup-background blind -->

    <div id="popup-update-pw" class="blind">
      <div class="titleSection">
        <h3>현재 비밀번호를 입력해 주세요</h3>
      </div>

      <div>
        <input class="input-check-pw" type="password" placeholder="현재 비밀번호를 입력해 주세요." />
      </div>

      <div class="btn-popup-group">
        <button @click="clickCheckNowPwBtn" id="btn-popup-confirm">확인</button>
        <button @click="closeUpdatePwPopup" id="btn-popup-close">닫기</button>
      </div>
    </div>
    <!-- END popup-update-pw -->

    <!-- START DELETE CONFIRM POPUP -->
    <div id="host-delete-popup" class="confirm-popup blind">
      <section class="confirm-txt-section">
        <span class="delete-txt">
          탈퇴신청하시겠습니까?
        </span>
      </section>
      <section class="confirm-btn-section">
        <div @click="clickDeleteHost" id="delete-host-btn" class="confirm-yesBtn">삭제</div>
        <div @click="closeDeleteHostPopup" id="host-delete-closeBtn" class="confirm-noBtn">닫기</div>
      </section>
    </div>
    <!-- END DELETE FONFIRM POPUP -->

    <!-- START DELETE CONFIRM POPUP -->
    <div id="reserve-delete-popup" class="confirm-popup blind">
      <section class="confirm-txt-section">
        <span class="delete-txt">
          해당 예약을 취소하시겠습니까?<br>
          취소 후 변경은 불가능합니다.
        </span>
      </section>
      <section class="confirm-btn-section">
        <div id="reserve-delete-btn" class="confirm-yesBtn">취소</div>
        <div id="reserve-delete-closeBtn" class="confirm-noBtn">닫기</div>
      </section>
    </div>
    <!-- END DELETE FONFIRM POPUP -->

    <!-- START CONFIRM POPUP -->
    <div id="calculate-popup" class="confirm-popup blind">
      <section class="confirm-txt-section">
        <span class="delete-txt">
          정산처리 하시겠습니까?
        </span>
      </section>
      <section class="confirm-btn-section">
        <div @click="clickCalculateBtn" id="calculate-btn" class="confirm-yesBtn">예</div>
        <div @click="closeCalculateBtn" id="calculate-closeBtn" class="confirm-noBtn">아니오</div>
      </section>
    </div>
    <!-- END DELETE FONFIRM POPUP -->

    <!-- START RADIO CHECK CONFIRM POPUP -->
    <div id="radio-check-popup" class="confirm-popup blind">
      <section class="confirm-txt-section">
        <span class="logout-txt">
          휴무 혹은 브레이크 타임을 설정해 주세요.
        </span>
      </section>
      <section class="confirm-btn-section">
        <div id="radio-check-closeBtn" class="confirm-noBtn">닫기</div>
      </section>
    </div>
    <!-- END RADIO CHECK CONFIRM popup -->

    <!-- START time-input-popup CONFIRM POPUP -->
    <div id="time-input-popup" class="confirm-popup blind">
      <section class="confirm-txt-section">
        <span class="logout-txt">
          종료 시간이 시작 시간보다 빠를 수 없습니다.
        </span>
      </section>
      <section class="confirm-btn-section">
        <div id="time-input-closeBtn" class="confirm-noBtn">닫기</div>
      </section>
    </div>
    <!-- END time-input-popup CONFIRM popup -->

    <!-- START no-reservation-popup CONFIRM POPUP -->
    <div id="no-reservation-popup" class="confirm-popup blind">
      <section class="confirm-txt-section">
        <span class="logout-txt">
          해당 기간 및 공간에<br>예약이 존재하지 않습니다.
        </span>
      </section>
      <section class="confirm-btn-section">
        <div id="no-reservation-closeBtn" class="confirm-noBtn">닫기</div>
      </section>
    </div>
    <!-- END no-reservation-popup CONFIRM popup -->

    <!-- START dayoff-calendar-wrap -->
    <div class="dayoff-calendar-wrap blind">
      <section class="dayoff-calendar-title">
        <h1 id="month"></h1>
        <span class="calendar-close-btn">
          <img src="@/assets/IMG/dash-board/ico-close.svg" alt="닫기 버튼" />
        </span>
      </section>
      <!-- END dayoff-calendar-title -->
      <section class="dayoff-calendar-content">
        <div>
          <p class="dayoff-calendar-content-title">이번 달의 임시 휴무를 보여드려요.</p>
        </div>
        <ul class="dayoff-list">
          <li class="dayoff-list-item blind">
            <div class="dayoff-list-item-top">
              <p class="dayoff-list-item-title"></p>
              <p class="dayoff-list-item-date"></p>
              <p class="dayoff-list-item-time"></p>
            </div>
            <div class="dayoff-list-item-bottom">
              <span class="badge"></span>
              <button class="dayoff-cancel-btn">일정 취소</button>
            </div>
          </li>
        </ul>
      </section>
    </div>
    <!-- END dayoff-calendar-wrap -->

  </div>
  <!-- END popup-background blind -->

  <div class="popup-background blind">
    <!-- START SPINNER SECTION -->
    <div id="spinner-section" class="blind">
      <img src="@/assets/IMG/common/spinner.gif" alt="spinner" class="spinner-img">
    </div>
    <!-- END SPINNER SECTION -->

    <!-- START COMMON CUSTOM ALERT POPUP -->
    <div id="common-alert-popup" class="alert-popup blind">
      <section class="alert-txt-section">
        <span class="common-alert-txt"></span>
      </section>
      <section @click="closeCommonAlert" id="common-alert-btn" class="alert-btn-section">
        <span>확인</span>
      </section>
    </div>

    <!-- START DAYOFF CANCEL POPUP -->
    <div id="dayoff-cancel-popup" class="confirm-popup blind">
      <section class="confirm-txt-section">
        <span class="delete-txt">
          임시 휴무를 정말 취소하시겠습니까?
        </span>
      </section>
      <section class="confirm-btn-section">
        <div id="dayoff-cancel-confirm-btn" class="confirm-yesBtn">예</div>
        <div id="dayoff-cancel-confirm-closeBtn" class="confirm-noBtn">아니오</div>
      </section>
    </div>
    <!-- END DAYOFF CANCEL POPUP -->

    <!-- START DAYOFF CANCEL CONFIRM POPUP -->
    <div id="dayoff-cancel-confirmOK-popup" class="confirm-popup blind">
      <section class="confirm-txt-section">
        <span class="delete-txt">
          임시 휴무가 취소되었습니다.
        </span>
      </section>
      <section class="confirm-btn-section">
        <div id="dayoff-cancel-confirmOK-btn" class="confirm-yesBtn">확인</div>
      </section>
    </div>
    <!-- END DAYOFF CANCEL POPUP -->

    <!-- START DAYOFF CANCEL FAIL POPUP -->
    <div id="dayoff-cancel-fail-popup" class="confirm-popup blind">
      <section class="confirm-txt-section">
        <span class="delete-txt">
          임시 휴무가 취소를 실패하였습니다.
        </span>
      </section>
      <section class="confirm-btn-section">
        <div id="dayoff-cancel-fail-btn" class="confirm-yesBtn">확인</div>
      </section>
    </div>
    <!-- END DAYOFF CANCEL FAIL POPUP -->
  </div>
</template>

<style>
@import '@/assets/CSS/dash-board/dash-qna-list.scss';
@import '@/assets/CSS/dash-board/dash-settings.scss';
</style>

<script>
import $ from 'jquery';
import axios from 'axios';
import { tr } from 'date-fns/locale';
// import '@/assets/JS/backoffice/host_popup';

export default {
  name: 'PopupView',
  data() {
    return {
      backoffice_no: this.$cookies.get('backoffice_no'),
      room_type: [],
      kor_room_type: [],

      insert_room_flag: true,
      update_room_flag: true,
      delete_room_flag: true,
      delete_comment_flag: true,
      insert_comment_flag: true,
      sales_flag: true,
      delete_host_flag: true,

      room_name: '',
      edit_room_type: '',
      m_edit_room_type: '',
      input_price_name: '',
      m_input_price_name: '',
      room_no: '',
    };
  },

  methods: {
    closeCommonAlert() {
      $('.popup-background:eq(1)').addClass('blind');
      $('#common-alert-popup').addClass('blind');
      this.$router.go();
    },

    closeSuccessAlert() {
      $('.popup-background:eq(0)').addClass('blind');
      $('#insert-success-alert-popup').addClass('blind');
      this.$router.go();
    },

    closeEditSuccessAlert() {
      $('.popup-background:eq(0)').addClass('blind');
      $('#edit-success-alert-popup').addClass('blind');
      this.$router.go();
    },

    getRoomType() {
      // const url = `http://localhost:8800/backoffice/dash/insert_room?${params}`;
      axios.get('http://localhost:8800/backoffice/dash/insert_room', {
        params: {
          backoffice_no: this.backoffice_no,
        },
      }).then((res) => {
        this.room_type = res.data.room_type;
        // eslint-disable-next-line no-plusplus
        for (let i = 0; i < this.room_type.length; i++) {
          if (this.room_type[i] === 'desk') {
            this.kor_room_type.push('데스크');
          } else if (this.room_type[i] === 'meeting_04') {
            this.kor_room_type.push('미팅룸(4인)');
          } else if (this.room_type[i] === 'meeting_06') {
            this.kor_room_type.push('미팅룸(6인)');
          } else if (this.room_type[i] === 'meeting_10') {
            this.kor_room_type.push('미팅룸(10인)');
          } else if (this.room_type[i] === 'office') {
            this.kor_room_type.push('오피스');
          }
        }
      });
    },

    clickRoomTypeSelectList() {
      $('.insert-type-select-list').toggleClass('blind');
      $('#room-type-select').removeClass('null-input-border');
    },

    clickTypeSelectItem(e) {
      const type = e.target.getAttribute('roomType');
      console.log(type);

      if (type === '데스크') this.edit_room_type = 'desk';
      else if (type === '미팅룸(4인)') this.edit_room_type = 'meeting_04';
      else if (type === '미팅룸(6인)') this.edit_room_type = 'meeting_06';
      else if (type === '미팅룸(10인)') this.edit_room_type = 'meeting_10';
      else if (type === '오피스') this.edit_room_type = 'office';

      if (type === '오피스') {
        $('.room-input-wrap:eq(2)').removeClass('blind');
      } else {
        $('#input-price-name').val('');
        $('.room-input-wrap:eq(2)').addClass('blind');
      }

      $('#insert-room-type-label').text(type);
      $('#insert-room-type-label').css('color', '#000000');
      $('.insert-type-select-list').addClass('blind');
    },

    closeInsertRoomPopup() {
      $('#input-room-name').val('');
      $('#input-price-name').val('');
      $('#insert-room-type-label').text('타입을 선택해주세요.');

      $('.r-input-warning:eq(0)').addClass('blind');

      $('#insert-room-type-label').css('color', '#808080');

      // 경고 테두리 초기화
      $('#input-room-name').removeClass('null-input-border');
      $('#input-price-name').removeClass('null-input-border');
      $('#room-type-select').removeClass('null-input-border');

      const sample = $('.insert-type-select-item:eq(0)').clone();
      $('.insert-type-select-list').empty().append(sample);

      $('.insert-type-select-list').addClass('blind');
      $('#room-insert-section').addClass('blind');
      $('.popup-background:eq(0)').addClass('blind');
    },

    insert_room() {
      console.log('--------');
      console.log(this.room_name);
      console.log(this.edit_room_type);
      console.log('--------');

      if (this.room_name !== '' && this.edit_room_type !== '') {
        if (this.edit_room_type === 'office') {
          if (this.input_price_name.length !== '') {
            this.insert();
          } else {
            $('#input-price-name').addClass('null-input-border');
          }
        } else if (this.insert_room_flag) {
          this.insert();
        }
      } else {
        if (this.room_name === '') {
          $('#input-room-name').addClass('null-input-border');
        }
        if (this.input_price_name === '') {
          $('#input-price-name').addClass('null-input-border');
        }
        if (this.edit_room_type === 0) {
          $('#room-type-select').addClass('null-input-border');
        }
      }
    },

    closeEditPopup() {
      // input 초기화
      $('#m-edit_room_type').val('');
      $('#m-input-room-name').val('');
      $('#m-input-price-name').val('');
      $('#m-edit-room-type-label').text('타입을 선택해주세요.');

      $('#m-edit-room-type-label').css('color', '#808080');

      // 경고 테두리 초기화
      $('#m-input-room-name').removeClass('null-input-border');
      $('#m-input-price-name').removeClass('null-input-border');
      $('#m-room-type-select').removeClass('null-input-border');

      const sample = $('.edit-type-select-item:eq(0)').clone();
      $('.edit-type-select-list').empty().append(sample);

      $('.r-input-warning:eq(1)').addClass('blind');

      $('.edit-type-select-list').addClass('blind');
      $('#room-edit-section').addClass('blind');
      $('.popup-background:eq(0)').addClass('blind');

      this.room_name = '';
    },

    resetWarning() {
      $(this).removeClass('null-input-border');
    },

    editRoomTypeLabel() {
      $('#m-room-type-select').removeClass('null-input-border');
      $('.edit-type-select-list').toggleClass('blind');
    },

    clickEditTypeSelectItem(e) {
      const type = e.target.getAttribute('roomType');
      console.log(type);

      if (type === '데스크') this.m_edit_room_type = 'desk';
      else if (type === '미팅룸(4인)') this.m_edit_room_type = 'meeting_04';
      else if (type === '미팅룸(6인)') this.m_edit_room_type = 'meeting_06';
      else if (type === '미팅룸(10인)') this.m_edit_room_type = 'meeting_10';
      else if (type === '오피스') this.m_edit_room_type = 'office';

      if (type === '오피스') {
        $('.room-input-wrap:eq(5)').removeClass('blind');
      } else {
        $('#m-input-price-name').val('');
        $('.room-input-wrap:eq(5)').addClass('blind');
      }

      $('#m-edit-room-type-label').text(type);
      $('#m-edit-room-type-label').css('color', '#000000');
      $('.edit-type-select-list').addClass('blind');
    },

    editRoom(e) {
      this.room_no = e.target.getAttribute('idx');
      this.room_name = $('#m-input-room-name').val().trim();
      this.m_edit_room_type = $('#m-edit-room-type-label').val().trim();

      console.log(this.room_name);

      if (this.room_name !== '' && this.m_edit_room_type !== '') {
        if (this.m_edit_room_type === 'office') {
          if (this.m_input_price_name !== '') {
            if (this.update_room_flag) {
              this.update();
            }
          } else {
            $('#m-input-price-name').addClass('null-input-border');
          }
        } else if (this.update_room_flag) {
          this.update();
        }
      } else {
        if (this.room_name === '') {
          $('#m-input-room-name').addClass('null-input-border');
        }
        if (this.m_input_price_name === '') {
          $('#m-input-price-name').addClass('null-input-border');
        }
        if (this.m_edit_room_type === '') {
          $('#m-room-type-select').addClass('null-input-border');
        }
      }
    },

    delete_room(e) {
      // eslint-disable-next-line camelcase
      const room_no = e.target.getAttribute('idx');

      if (this.delete_room_flag) {
        this.delete_room_flag = false;

        // 로딩 화면
        $('.popup-background:eq(1)').removeClass('blind');
        $('#spinner-section').removeClass('blind');

        const params = new URLSearchParams();
        params.append('backoffice_no', this.backoffice_no);
        params.append('room_no', room_no);

        axios.post('http://localhost:8800/backoffice/dash/deleteOK_room', params)
          .then((res) => {
            // 로딩 화면 닫기
            $('.popup-background:eq(1)').addClass('blind');
            $('#spinner-section').addClass('blind');

            $('.popup-background:eq(0)').addClass('blind');
            $('#host-delete-popup').addClass('blind');
            $('#delete-space-btn').attr('idx', '');

            if (res.data.result === '1') {
              $('.popup-background:eq(1)').removeClass('blind');
              $('#common-alert-popup').removeClass('blind');
              $('.common-alert-txt').text('삭제가 완료되었습니다.');
              $('#common-alert-btn').attr('is_reload', true);
            } else {
              $('.popup-background:eq(1)').removeClass('blind');
              $('#common-alert-popup').removeClass('blind');
              $('.common-alert-txt').text('삭제에 실패하였습니다.');
            }
          }).catch(() => {
            this.delete_room_flag = true;

            // 로딩 화면 닫기
            $('.popup-background:eq(1)').addClass('blind');
            $('#spinner-section').addClass('blind');

            $('.popup-background:eq(1)').removeClass('blind');
            $('#common-alert-popup').removeClass('blind');
            $('.common-alert-txt').text('오류 발생으로 인해 처리에 실패하였습니다.');
          });
      }
    },

    closeDeletePopup() {
      $('.popup-background:eq(0)').addClass('blind');
      $('#space-delete-popup').addClass('blind');
      $('#delete-space-btn').attr('idx', '');
    },

    clickAnswerDeleteBtn(e) {
      console.log('clickAnswerDeleteBtn');

      if (this.delete_comment_flag) {
        this.delete_comment_flag = false;

        // 로딩 화면
        $('.popup-background:eq(1)').removeClass('blind');
        $('#spinner-section').removeClass('blind');

        const params = new URLSearchParams();
        params.append('backoffice_no', this.backoffice_no);
        params.append('mother_no', e.target.getAttribute('mother_no'));
        params.append('comment_no', e.target.getAttribute('comment_no'));

        console.log('params :', params);

        axios.post('http://localhost:8800/backoffice/dash/deleteOK_comment', params)
          .then((res) => {
            this.delete_comment_flag = true;

            // 로딩 화면 닫기
            $('.popup-background:eq(1)').addClass('blind');
            $('#spinner-section').addClass('blind');

            if (res.data.result === '1') {
              $('.popup-background:eq(1)').removeClass('blind');
              $('#common-alert-popup').removeClass('blind');
              $('.common-alert-txt').text('답변을 삭제하였습니다.');
              $('#common-alert-btn').attr('is_reload', true);
            } else {
              $('.popup-background:eq(1)').removeClass('blind');
              $('#common-alert-popup').removeClass('blind');
              $('.common-alert-txt').text('답변을 삭제 처리에 실패하였습니다.');
            }
          })
          .catch(() => {
            this.delete_comment_flag = true;

            // 로딩 화면 닫기
            $('.popup-background:eq(1)').addClass('blind');
            $('#spinner-section').addClass('blind');

            $('.popup-background:eq(1)').removeClass('blind');
            $('#common-alert-popup').removeClass('blind');
            $('.common-alert-txt').text('오류 발생으로 인해 처리에 실패하였습니다.');
          });
      }
    },

    closeAnswerDeletePopup() {
      $('.popup-background:eq(0)').addClass('blind');
      $('#answer-delete-popup').addClass('blind');
      $('#delete-answer-btn').attr('comment_no', '');
      $('#delete-answer-btn').attr('mother_no', '');
    },

    closeCommentCancel() {
      $('#host-comment').val('');
      $('.now_txt_length').text('0');

      $('#comment-section').addClass('blind');
      $('.popup-background:eq(0)').addClass('blind');
    },

    insertHostComment(e) {
      console.log('??');
      if ($('#host-comment').val().trim().length > 0) {
        if (this.insert_comment_flag) {
          this.insert_comment_flag = false;

          // 로딩 화면
          $('.popup-background:eq(1)').removeClass('blind');
          $('#spinner-section').removeClass('blind');

          const params = new URLSearchParams();
          params.append('backoffice_no', this.backoffice_no);
          params.append('comment_no', e.target.getAttribute('comment_no'));
          params.append('room_no', e.target.getAttribute('room_no'));
          params.append('comment_content', $('#host-comment').val().trim());

          axios.post('http://localhost:8800/backoffice/dash/insertOK_comment', params)
            .then((res) => {
              this.insert_comment_flag = true;

              if (res.data.result === '1') {
                $('.popup-background:eq(1)').addClass('blind');
                $('#spinner-section').addClass('blind');
                $('#host-comment').val('');
                $('.now_txt_length').text('0');

                $('#comment-section').addClass('blind');
                $('.popup-background:eq(0)').addClass('blind');

                $('.popup-background:eq(1)').removeClass('blind');
                $('#common-alert-popup').removeClass('blind');
                $('.common-alert-txt').text('답글이 등록되었습니다.');
                $('#common-alert-btn').attr('is_reload', true);
              } else {
                $('.popup-background:eq(1)').removeClass('blind');
                $('#common-alert-popup').removeClass('blind');
                $('.common-alert-txt').text('답글 등록에 실패하였습니다.');
              }
            })
            .catch(() => {
              this.insert_comment_flag = true;

              // 로딩 화면 닫기
              $('.popup-background:eq(1)').addClass('blind');
              $('#spinner-section').addClass('blind');

              $('.popup-background:eq(1)').removeClass('blind');
              $('#common-alert-popup').removeClass('blind');
              $('.common-alert-txt').text('오류 발생으로 인해 처리에 실패하였습니다.');
            });
        } else {
          $('#host-comment').addClass('null-input-border');
        }
      }
    },

    clickCalculateBtn(e) {
      if (this.sales_flag) {
        this.sales_flag = false;

        // 로딩 화면
        $('.popup-background:eq(1)').removeClass('blind');
        $('#spinner-section').removeClass('blind');

        const params = new URLSearchParams();
        params.append('backoffice_no', this.backoffice_no);
        params.append('payment_no', e.target.getAttribute('payment_no'));
        params.append('room_no', e.target.getAttribute('room_no'));

        axios.post('http://localhost:8800/backoffice/dash/updateOK_sales', params)
          .then((res) => {
            this.sales_flag = true;

            // 로딩 화면 닫기
            $('.popup-background:eq(1)').addClass('blind');
            $('#spinner-section').addClass('blind');

            if (res.data.result === '1') {
              $('.popup-background:eq(1)').removeClass('blind');
              $('#common-alert-popup').removeClass('blind');
              $('.common-alert-txt').text('정산처리되었습니다.');
              $('#common-alert-btn').attr('is_reload', true);
            } else {
              $('.popup-background:eq(1)').removeClass('blind');
              $('#common-alert-popup').removeClass('blind');
              $('.common-alert-txt').text('정산처리에 실패하였습니다.');
            }
          })
          .catch(() => {
            this.sales_flag = true;

            // 로딩 화면 닫기
            $('.popup-background:eq(1)').addClass('blind');
            $('#spinner-section').addClass('blind');

            $('.popup-background:eq(1)').removeClass('blind');
            $('#common-alert-popup').removeClass('blind');
            $('.common-alert-txt').text('오류 발생으로 인해 처리에 실패하였습니다.');
          });
      }
    },

    closeCalculateBtn() {
      $('.popup-background:eq(0)').addClass('blind');
      $('#calculate-popup').addClass('blind');
    },

    // **************************************************
    // SETTINGS
    // **************************************************
    clickCheckNowPwBtn() {
      if ($('.input-check-pw').val().trim().length > 0) {
        console.log($('.input-check-pw').val().trim());
        // 로딩 화면
        $('.popup-background:eq(1)').removeClass('blind');
        $('#spinner-section').removeClass('blind');

        axios.get('http://localhost:8800/backoffice/update_pw', {
          params: {
            backoffice_no: this.backoffice_no,
            backoffice_pw: $('.input-check-pw').val().trim(),
          },
        })
          .then((res) => {
            // 로딩 화면 닫기
            $('.popup-background:eq(1)').addClass('blind');
            $('#spinner-section').addClass('blind');

            if (res.data.result === '1') {
              this.$router.replace(`/backoffice/dash/setting_pw?backoffice_no=${this.backoffice_no}`);
            } else if (res.data.result === '0') {
              $('.popup-background:eq(1)').removeClass('blind');
              $('#common-alert-popup').removeClass('blind');
              $('.common-alert-txt').text('일치하지않는 비밀번호입니다.');

              $('.input-check-pw').removeClass('null-input-border');
              $('.input-check-pw').val('');
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
      } else {
        $('.input-check-pw').addClass('null-input-border');
      }
    },

    closeUpdatePwPopup() {
      $('#popup-update-pw').addClass('blind');
      $('.popup-background:eq(0)').addClass('blind');
      $('.input-check-pw').removeClass('null-input-border');
      $('.input-check-pw').val('');
    },

    clickDeleteHost() {
      if (this.delete_host_flag) {
        this.delete_host_flag = false;

        // 로딩 화면
        $('.popup-background:eq(1)').removeClass('blind');
        $('#spinner-section').removeClass('blind');

        const params = new URLSearchParams();
        params.append('backoffice_no', this.backoffice_no);

        axios.post('http://localhost:8800/backoffice/dash/setting_delete', params)
          .then((res) => {
            this.delete_host_flag = true;

            // 로딩 화면 닫기
            $('.popup-background:eq(1)').addClass('blind');
            $('#spinner-section').addClass('blind');

            if (res.data.result === '1') {
              $('#host-delete-popup').addClass('blind');
              $('.popup-background:eq(0)').addClass('blind');

              $('.popup-background:eq(1)').removeClass('blind');
              $('#common-alert-popup').removeClass('blind');
              $('.common-alert-txt').text('마스터에게 삭제 요청되었습니다.');
              $('#common-alert-btn').attr('is_reload', 'logout');
            } else {
              $('.popup-background:eq(1)').removeClass('blind');
              $('#common-alert-popup').removeClass('blind');
              $('.common-alert-txt').text('남은 예약이 존재하여 삭제할 수 없습니다.');
            }
          })
          .catch(() => {
            this.delete_host_flag = true;

            // 로딩 화면 닫기
            $('.popup-background:eq(1)').addClass('blind');
            $('#spinner-section').addClass('blind');

            $('.popup-background:eq(1)').removeClass('blind');
            $('#common-alert-popup').removeClass('blind');
            $('.common-alert-txt').text('오류 발생으로 인해 처리에 실패하였습니다.');
          });
      }
    },

    closeDeleteHostPopup() {
      $('#host-delete-popup').addClass('blind');
      $('.popup-background:eq(0)').addClass('blind');
    },

    closeLogoutPopup() {
      $('#logout-popup').addClass('blind');
      $('.popup-background:eq(0)').addClass('blind');
    },

    // **************************************************
    // FUNCTION
    // **************************************************

    // room insert OK
    insert() {
      this.insert_room_flag = false;
      // 로딩 화면
      $('.popup-background:eq(1)').removeClass('blind');
      $('#spinner-section').removeClass('blind');

      const params = new URLSearchParams();
      params.append('backoffice_no', this.backoffice_no);
      params.append('room_name', this.room_name);
      params.append('room_type', this.edit_room_type);
      params.append('room_price', this.input_price_name);

      // const url = `http://localhost:8800/backoffice/dash/insertOK_room?${params}`;

      axios.post('http://localhost:8800/backoffice/dash/insertOK_room', params).then((res) => {
        this.insert_room_flag = true;

        $('.popup-background:eq(1)').addClass('blind');
        $('#spinner-section').addClass('blind');

        if (res.data.result === '1') {
          this.edit_room_type = '';
          this.room_name = '';
          this.input_price_name = '';

          $('#insert-room-type-label').text('타입을 선택해주세요.');
          $('#insert-room-type-label').css('color', '#808080');

          // 경고 테두리 초기화
          $('#input-room-name').removeClass('null-input-border');
          $('#input-price-name').removeClass('null-input-border');
          $('#room-type-select').removeClass('null-input-border');

          $('.popup-background:eq(0)').addClass('blind');
          $('#room-insert-section').addClass('blind');

          $('.popup-background:eq(0)').removeClass('blind');
          $('#insert-success-alert-popup').removeClass('blind');
        } else {
          $('.popup-background:eq(1)').removeClass('blind');
          $('#common-alert-popup').removeClass('blind');
          $('.common-alert-txt').text('수정에 실패하였습니다.');
        }
      }).catch(() => {
        this.insert_room_flag = true;

        // 로딩 화면 닫기
        $('.popup-background:eq(1)').addClass('blind');
        $('#spinner-section').addClass('blind');

        $('.popup-background:eq(1)').removeClass('blind');
        $('#common-alert-popup').removeClass('blind');
        $('.common-alert-txt').text('오류 발생으로 인해 처리에 실패하였습니다.');
      });
    },

    commentTextLength(e) {
      console.log(e.target.value, e.target.value.length);
      $('.now_txt_length').text(e.target.value.length);

      const targetValue = e.target.value;
      if (e.target.value.length > 500) {
        e.target.value = targetValue.substring(0, 500);
      }
    },

    update() {
      this.update_room_flag = false;

      // 로딩 화면
      $('.popup-background:eq(1)').removeClass('blind');
      $('#spinner-section').removeClass('blind');

      const params = new URLSearchParams();
      params.append('backoffice_no', this.backoffice_no);
      params.append('room_no', this.room_no);
      params.append('room_name', this.room_name);
      params.append('room_type', this.m_edit_room_type);
      params.append('room_price', this.m_input_price_name);

      axios.post('http://localhost:8800/backoffice/dash/updateOK_room', params)
        .then((res) => {
          this.update_room_flag = true;

          // 로딩 화면 닫기
          $('.popup-background:eq(1)').addClass('blind');
          $('#spinner-section').addClass('blind');

          // 변경 성공
          if (res.data.result === '1') {
            // input 초기화
            $('#m-edit_room_type').val('');
            $('#m-input-room-name').val('');
            $('#m-input-price-name').val('');
            $('#m-edit-room-type-label').text('타입을 선택해주세요.');

            $('#m-edit-room-type-label').css('color', '#808080');

            // 경고 테두리 초기화
            $('#m-input-room-name').removeClass('null-input-border');
            $('#m-input-price-name').removeClass('null-input-border');
            $('#m-room-type-select').removeClass('null-input-border');

            $('#room-edit-section').addClass('blind');
            $('.popup-background:eq(0)').addClass('blind');

            const sample = $('.edit-type-select-item:eq(0)').clone();
            $('.edit-type-select-list').empty().append(sample);

            $('.popup-background:eq(1)').removeClass('blind');
            $('#common-alert-popup').removeClass('blind');
            $('.common-alert-txt').text('수정이 완료되었습니다.');
            $('#common-alert-btn').attr('is_reload', true);
          } else {
            $('.popup-background:eq(1)').removeClass('blind');
            $('#common-alert-popup').removeClass('blind');
            $('.common-alert-txt').text('수정에 실패하였습니다.');
          }

          this.room_name = '';
          this.room_no = '';
        }).catch(() => {
          this.update_room_flag = true;

          // 로딩 화면 닫기
          $('.popup-background:eq(1)').addClass('blind');
          $('#spinner-section').addClass('blind');

          $('.popup-background:eq(1)').removeClass('blind');
          $('#common-alert-popup').removeClass('blind');
          $('.common-alert-txt').text('오류 발생으로 인해 처리에 실패하였습니다.');
        });
    },
  },

  mounted() {
    this.$nextTick(() => {
      this.getRoomType();
    });
  },
};
</script>
