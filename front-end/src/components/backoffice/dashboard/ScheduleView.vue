<!--
--  @author 전판근, 김예은
-- @refactoring 전판근
-->

<!-- eslint-disable vuejs-accessibility/click-events-have-key-events -->
<!-- eslint-disable vuejs-accessibility/label-has-for -->
<!-- eslint-disable max-len -->
<!-- eslint-disable vuejs-accessibility/form-control-has-label -->
<template>
  <div class="titleSection">
    <h1>일정관리</h1>
    <button @click="clickDayoffCalendar" id="btn-dayoff-calendar" class="btn-dayoff-calendar">휴무 일정</button>
  </div>

  <div class="boardWrap">
    <div class="cardSection schedule">
      <div class="setScheduleSection">
        <div class="setScheduleWrap">
          <h3>임시 일정 설정</h3>
          <div class="radio-wrap">
            <input type="radio" v-model="set_schedule" name="set_schedule" id="set-dayoff" class="schedule-radio"
              value="dayoff" />
            <label for="set-dayoff"> 휴무 설정 </label>

            <input type="radio" v-model="set_schedule" name="set_schedule" id="set-breaktime" class="schedule-radio"
              value="breaktime" />
            <label for="set-breaktime"> 브레이크 타임 설정 </label>
          </div>
          <div>
            <span class="off-type-warning blind">휴무 설정은 선택 당일 휴무입니다.<br>(시간 선택과 관계 없습니다.)</span>
            <span class="off-type-warning blind">브레이크 타임은 당일 설정만 가능합니다.<br>(시작 날짜와 종료 날짜를 같게 설정해 주세요.)</span>
          </div>
        </div>
        <!-- END setScheduleWrap -->
        <div class="setScheduleWrap">
          <h3>날짜 및 시간 설정</h3>
          <div class="time-select-section">
            <!-- <div class="time-select-wrap"> -->
            <v-date-picker v-model="not_sdate" :model-config="modelConfig" mode="dateTime" is24hr
              :minute-increment="60">
              <template v-slot="{ inputValue, inputEvents }">
                <input class="time-select-wrap" :value="inputValue" v-on="inputEvents" />
              </template>
            </v-date-picker>
            <!-- <input type="text" class="type-border-txt time-input" placeholder="시작 날짜/시간" readonly />
              <img src="@/assets/IMG/office/full-dropdown.svg" alt="full-dropdown" class="full-dropdown" /> -->
            <!-- </div> -->
            <!-- <div class="time-select-wrap"> -->
            <v-date-picker v-model="not_edate" :model-config="modelConfig" mode="dateTime" is24hr
              :minute-increment="60">
              <template v-slot="{ inputValue, inputEvents }">
                <input class="time-select-wrap" :value="inputValue" v-on="inputEvents" />
              </template>
            </v-date-picker>
            <!-- <input type="text" class="type-border-txt time-input" placeholder="종료 날짜/시간" readonly />
              <img src="@/assets/IMG/office/full-dropdown.svg" alt="full-dropdown" class="full-dropdown" /> -->
            <!-- </div> -->
            <input @click="scheduleSearch" type="button" class="btn-schedule-research" value="적용 공간 검색" />
          </div>
        </div>
      </div>
      <!-- END setScheduleSection -->

      <div class="select-room-section">
        <div class="select-room-section-title">
          <h3>적용 공간 선택</h3>
          <div class="select-all-section">
            <label for="select-all-room">전체선택</label>
            <input type="checkbox" id="select-all-room" @click="selectAllCheckbox" @change="changeSelectAll" />
            <input type="hidden" :maxCnt="this.maxCnt" :nowCnt="this.nowCnt" id="maxCnt" />
          </div>
        </div>
        <!-- END select-room-section-title -->

        <div class="custom-table">
          <div class="ct-header">
            <div class="ct-header-cell schedule">선택</div>
            <div class="ct-header-cell schedule">공간 타입</div>
            <div class="ct-header-cell schedule">공간 이름</div>
            <div class="ct-header-cell schedule">공간 예약 유무</div>
            <div class="ct-header-cell schedule">예약 수</div>
          </div>
        </div>
        <div class="ct-body schedule-ct" @scroll="handleScheduleList($event.target)">

          <div class="ct-body-row" :class="{ 'blind': sc_vos.length === 0 }" v-for="vos in sc_vos" :key="vos">
            <div class="ct-body-cell schedule">
              <input type="checkbox" name="select-room" id="room_no" class="room-checkbox"
                :disabled="vos.reserve_cnt > 0 ? true : false" :room_no="vos.room_no" value="" />
            </div>
            <div class="ct-body-cell schedule">
              <span class="room_type" v-if="vos.room_type === 'desk'">데스크</span>
              <span class="room_type" v-if="vos.room_type === 'meeting_04'">미팅룸 (4인)</span>
              <span class="room_type" v-if="vos.room_type === 'meeting_06'">미팅룸 (6인)</span>
              <span class="room_type" v-if="vos.room_type === 'meeting_10'">미팅룸 (10인)</span>
              <span class="room_type" v-if="vos.room_type === 'office'">오피스</span>
            </div>
            <div class="ct-body-cell schedule">
              <span class="room_name">{{ vos.room_name }}</span>
            </div>
            <div class="ct-body-cell schedule">
              <span class="reserve_is" v-if="vos.reserve_is === 'X'">
                <img src="@/assets/IMG/dash-board/ico-close.svg" alt="">
              </span>
              <span class="reserve_is" v-if="vos.reserve_is === 'O'">
                <img src="@/assets/IMG/dash-board/ico-circle.svg" alt="">
              </span>
            </div>
            <div class="ct-body-cell schedule">
              <span @click="clickReservationDetail" class="reserve_cnt" :room_no="vos.room_no"
                :reserve_cnt="vos.reserve_cnt">{{
                    vos.reserve_cnt
                }}명</span>
            </div>
          </div>
          <!-- END ct-body-row -->
        </div>
        <input @click="clickScheduleConfirm" type='button' id='schedule-confirm-btn' class='schedule-confirm-btn'
          value='일정 설정' v-if="sc_vos_cnt > 0" />
      </div>
      <!-- END select-room-section -->
    </div>
    <!-- END cardSection schedule -->
  </div>
  <!-- END boardWrap -->
</template>

<style lang="scss" scoped>
@import '@/assets/CSS/dash-board/dash-schedule.scss';
</style>

<script>
import $ from 'jquery';
import axios from 'axios';
import 'v-calendar/dist/style.css';

export default {
  name: 'ScheduleView',
  data() {
    return {
      cnt: 0,
      maxCnt: 0,
      nowCnt: 0,
      scroll_flag: true,
      backoffice_no: decodeURIComponent(window.atob(this.$cookies.get('backoffice_no'))),
      set_schedule: '',
      not_sdate: new Date(),
      not_edate: new Date(),
      modelConfig: {
        type: 'string',
        mask: 'YYYY-MM-DD HH:mm:00',
      },
      sc_vos: [],
      sc_vos_cnt: 0,

      check_room: '',
    };
  },

  methods: {

    selectAllCheckbox(e) {
      if ($(e.target).is(':checked')) {
        $(e.target).attr('checked', true);
      } else {
        $(e.target).attr('checked', false);
      }
    },

    changeSelectAll(e) {
      if ($(e.target).is(':checked')) {
        $("input:checkbox[name='select-room']").each(function (index) {
          if ($(this).is(':disabled')) {
            $(this).prop('checked', false);
          } else {
            $(this).prop('checked', true);
          }
        });
      } else {
        $("input:checkbox[name='select-room']").prop('checked', false);
      }
    },

    getScheduleList() {
      const params = new URLSearchParams();
      params.append('backoffice_no', this.backoffice_no);

      const url = `http://localhost:8800/backoffice/dash/schedule?${params}`;
      axios.get(url).then((res) => {
        this.cnt = res.data.cnt;
      });
    },

    scheduleSearch() {
      const sDateTime = this.not_sdate.toString().split(' ');
      const eDateTime = this.not_edate.toString().split(' ');

      const params = new URLSearchParams();
      params.append('backoffice_no', this.backoffice_no);
      params.append('not_sdate', sDateTime[0]);
      params.append('not_edate', eDateTime[0]);
      params.append('not_stime', sDateTime[1]);
      params.append('not_etime', eDateTime[1]);
      params.append('off_type', this.set_schedule);

      if (this.set_schedule === '') {
        $('.popup-background:eq(1)').removeClass('blind');
        $('#common-alert-popup').removeClass('blind');
        $('.common-alert-txt').text('일정 설정 타입을 선택해 주세요.');
      } else if (this.not_sdate === '' || this.not_edate === '') {
        $('.popup-background:eq(1)').removeClass('blind');
        $('#common-alert-popup').removeClass('blind');
        $('.common-alert-txt').text('날짜 및 시간을 선택해 주세요.');
      } else if (this.not_sdate > this.not_edate) {
        $('.popup-background:eq(1)').removeClass('blind');
        $('#common-alert-popup').removeClass('blind');
        $('.common-alert-txt').text('종료 시간은 시작 시간보다 빠를 수 없습니다.');
      } else {
        const url = `http://localhost:8800/backoffice/dash/schedule_research?${params}&page=1`;
        axios.get(url).then((res) => {
          this.sc_vos = res.data.sc_vos;
          this.sc_vos_cnt = res.data.cnt;

          this.maxCnt = res.data.maxCnt;
          this.nowCnt = res.data.nowCnt;
        });
      }
    },

    clickReservationDetail(e) {
      const sDateTime = this.not_sdate.toString().split(' ');
      const eDateTime = this.not_edate.toString().split(' ');

      const not_sdate = sDateTime[0];
      const not_stime = sDateTime[1];
      const not_edate = eDateTime[0];
      const not_etime = eDateTime[1];
      const room_no = e.target.getAttribute('room_no');
      const off_type = $("input:radio[name='set_schedule']:checked").val();
      const reserve_cnt = parseInt(e.target.getAttribute('reserve_cnt'), 10);

      // 로딩 화면
      $('.popup-background:eq(1)').removeClass('blind');
      $('#spinner-section').removeClass('blind');

      if (reserve_cnt !== 0) {
        // 로딩 화면 닫기
        $('.popup-background:eq(1)').addClass('blind');
        $('#spinner-section').addClass('blind');

        this.$router.push(`/backoffice/dash/reservation?backoffice_no=${this.$cookies.get('backoffice_no')}&room_no=${room_no}&not_sdate=${not_sdate}&not_edate=${not_edate}&not_stime=${not_stime}&not_etime=${not_etime}&off_type=${off_type}&page=1`);
      } else {
        // 로딩 화면 닫기
        $('.popup-background:eq(1)').addClass('blind');
        $('#spinner-section').addClass('blind');

        $('.popup-background:eq(0)').removeClass('blind');
        $('#no-reservation-popup').removeClass('blind');
      }
    },

    clickDayoffCalendar() {
      const backoffice_no = this.$cookies.get('backoffice_no');
      const month = '';

      // 로딩 화면 열기
      $('.popup-background:eq(1)').removeClass('blind');
      $('#spinner-section').removeClass('blind');

      axios.get(`http://localhost:8800/backoffice/dash/schedule_calendar?backoffice_no=${backoffice_no}`)
        .then((res) => {
          $('.popup-background:eq(0)').removeClass('blind');
          $('.dayoff-calendar-wrap').removeClass('blind');

          // 로딩 화면 닫기
          $('.popup-background:eq(1)').addClass('blind');
          $('#spinner-section').addClass('blind');
        });
    },

    clickScheduleConfirm() {
      this.check_room = $('input[type=checkbox]:checked').parents('.ct-body-row');

      if (this.check_room.length !== 0) {
        const backoffice_no = decodeURIComponent(window.atob(this.$cookies.get('backoffice_no')));
        const sDateTime = this.not_sdate.toString().split(' ');
        const eDateTime = this.not_edate.toString().split(' ');

        const not_sdate = sDateTime[0];
        const not_stime = sDateTime[1];
        const not_edate = eDateTime[0];
        const not_etime = eDateTime[1];
        const off_type = $("input:radio[name='set_schedule']:checked").val();
        let schedule_flag = '';

        for (let i = 0; i < this.check_room.length; i++) {
          const room_no = $(this.check_room[i]).find('#room_no').attr('room_no');

          if (room_no !== null) {
            // 로딩 화면
            $('.popup-background:eq(1)').removeClass('blind');
            $('#spinner-section').removeClass('blind');

            const params = new URLSearchParams();
            params.append('backoffice_no', backoffice_no);
            params.append('room_no', room_no);
            params.append('not_sdate', not_sdate);
            params.append('not_edate', not_edate);
            params.append('not_stime', not_stime);
            params.append('not_etime', not_etime);
            params.append('off_type', off_type);

            axios.post('http://localhost:8800/backoffice/dash/scheduleOK', params)
              // eslint-disable-next-line no-loop-func
              .then((res) => {
                if (res.data.result === '1') {
                  // eslint-disable-next-line camelcase
                  schedule_flag = 1;
                } else {
                  // eslint-disable-next-line camelcase
                  schedule_flag = 0;
                }
              })
              .catch(() => {
                schedule_flag = 0;
              });

            if (schedule_flag = 1) {
              // 로딩 화면 닫기
              $('.popup-background:eq(1)').addClass('blind');
              $('#spinner-section').addClass('blind');

              $('.popup-background:eq(1)').removeClass('blind');
              $('#common-alert-popup').removeClass('blind');
              $('.common-alert-txt').text('일정이 설정되었습니다.');

              // 리스트 새로고침
              $('#common-alert-btn').click(() => {
                $('.btn-schedule-research').trigger('click');
              });
            }
            if (schedule_flag = 0) {
              // 로딩 화면 닫기
              $('.popup-background:eq(1)').addClass('blind');
              $('#spinner-section').addClass('blind');

              $('.popup-background:eq(1)').removeClass('blind');
              $('#common-alert-popup').removeClass('blind');
              $('.common-alert-txt').text('오류 발생으로 인해 처리에 실패하였습니다.');
              break;
            }
          }
        }
      } else {
        $('.popup-background:eq(1)').removeClass('blind');
        $('#common-alert-popup').removeClass('blind');
        $('.common-alert-txt').text('공간을 선택해주세요.');
      }
    },

    handleScheduleList(e) {
      if (Math.ceil($(e).scrollTop() + $(e).innerHeight()) >= $(e).prop('scrollHeight')) {
        if ($('.ct-body-row').length - 1 < Number($('#maxCnt').attr('maxCnt')) && this.scroll_flag) {
          // 로딩 화면
          $('.popup-background:eq(1)').removeClass('blind');
          $('#spinner-section').removeClass('blind');

          const sDateTime = this.not_sdate.toString().split(' ');
          const eDateTime = this.not_edate.toString().split(' ');

          const not_sdate = sDateTime[0];
          const not_stime = sDateTime[1];
          const not_edate = eDateTime[0];
          const not_etime = eDateTime[1];
          const off_type = $("input:radio[name='set_schedule']:checked").val();

          const page = $('#maxCnt').attr('nowCnt');

          this.scroll_flag = false;

          const param = new URLSearchParams();
          param.append('backoffice_no', this.backoffice_no);
          param.append('not_sdate', not_sdate);
          param.append('not_edate', not_edate);
          param.append('not_stime', not_stime);
          param.append('not_etime', not_etime);
          param.append('off_type', off_type);
          param.append('page', Number(page) + 1);

          const url = `http://localhost:8800/backoffice/dash/schedule_research_paging?${param}`;

          axios.get(url)
            .then((res) => {
              this.scroll_flag = true;

              // 로딩 화면 닫기
              $('.popup-background:eq(1)').addClass('blind');
              $('#spinner-section').addClass('blind');

              const now = $('#maxCnt').attr('nowCnt');
              $('#maxCnt').attr('nowCnt', Number(now) + 1);
              this.sc_vos = this.sc_vos.concat(res.data.sc_vos);
            })
            .catch(() => {
              this.scroll_flag = true;

              // 로딩 화면 닫기
              $('.popup-background:eq(1)').addClass('blind');
              $('#spinner-section').addClass('blind');
            });
        }
      }
    },
  },

  mounted() {
    axios.get('http://localhost:8800/backoffice/loginCheck')
      .then((res) => {
        if (res.data.result === '1') {
          this.$nextTick(() => {
            this.getScheduleList();
          });
        } else {
          this.$store.commit('backoffice_setLogin_false');
          this.$router.replace('/backoffice/landing');
        }
      });
  },
};
</script>
