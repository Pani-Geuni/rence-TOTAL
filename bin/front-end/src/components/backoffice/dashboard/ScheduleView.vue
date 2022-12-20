<!-- eslint-disable vuejs-accessibility/click-events-have-key-events -->
<!-- eslint-disable vuejs-accessibility/label-has-for -->
<!-- eslint-disable max-len -->
<!-- eslint-disable vuejs-accessibility/form-control-has-label -->
<template>
  <div class="titleSection">
    <h1>일정관리</h1>
    <button id="btn-dayoff-calendar" class="btn-dayoff-calendar">휴무 일정</button>
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
            <label @click="selectAllCheckbox" for="select-all-room">전체선택</label>
            <input type="checkbox" id="select-all-room" />
            <input type="hidden" id="maxCnt" />
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
        <div class="ct-body schedule-ct">

          <div class="ct-body-row" :class="{ 'blind': sc_vos.length === 0 }" v-for="vos in sc_vos" :key="vos">
            <div class="ct-body-cell schedule">
              <input type="checkbox" name="select-room" id="room_no" class="room-checkbox"
                :class="{ 'disabled': 'vos.reserve_cnt > 0' }" :room_no="vos.room_no" value="" />
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
              <span class="reserve_cnt" :reserve_cnt="vos.reserve_cnt">{{ vos.reserve_cnt }}명</span>
            </div>
          </div>
          <!-- END ct-body-row -->
        </div>
        <input type='button' id='schedule-confirm-btn' class='schedule-confirm-btn' value='일정 설정'
          v-if="sc_vos_cnt > 0" />
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
      backoffice_no: this.$cookies.get('backoffice_no'),
      set_schedule: '',
      not_sdate: new Date(),
      not_edate: new Date(),
      modelConfig: {
        type: 'string',
        mask: 'YYYY-MM-DD HH:mm:00',
      },
      sc_vos: [],
      sc_vos_cnt: 0,
    };
  },

  methods: {

    selectAllCheckbox() {
      console.log('hi');
    },

    getScheduleList() {
      const params = new URLSearchParams();
      params.append('backoffice_no', this.backoffice_no);

      const url = `http://localhost:8800/backoffice/dash/schedule?${params}`;
      axios.get(url).then((res) => {
        console.log(res.data);
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
      } else {
        const url = `http://localhost:8800/backoffice/dash/schedule_research?${params}&page=1`;
        axios.get(url).then((res) => {
          console.log(res.data);

          this.sc_vos = res.data.sc_vos;
          this.sc_vos_cnt = res.data.cnt;
        });
      }
    },
  },

  mounted() {
    this.$nextTick(() => {
      this.getScheduleList();
    });
  },
};
</script>
