<!-- eslint-disable vuejs-accessibility/form-control-has-label -->
<!-- eslint-disable max-len -->
<!-- eslint-disable vuejs-accessibility/label-has-for -->
<template>
  <div class="titleSection">
    <h1>예약자 리스트</h1>
  </div>

  <div class="boardWrap">
    <div class="cardSection schedule">
      <div class="setScheduleSection">
        <div class="setScheduleWrap">
          <h3>예약일자</h3>
          <p>{{ reserve_stime }} ~ {{ reserve_etime }}</p>
        </div>
        <!-- setScheduleWrap -->
      </div>
      <!-- END setScheduleSection -->

      <div class="select-room-section">
        <div class="select-room-section-title">
          <h3>예약자 정보</h3>
          <div class="select-all-section">
            <label for="select-all-reservation">전체선택</label>
            <input type="checkbox" id="select-all-reservation" />
          </div>
          <!-- END select-all-section -->
        </div>
        <!-- select-room-section-title -->

        <div class="custom-table">
          <div class="ct-header">
            <div class="ct-header-cell reservation">선택</div>
            <div class="ct-header-cell reservation">이름</div>
            <div class="ct-header-cell reservation">이메일</div>
            <div class="ct-header-cell reservation">전화번호</div>
            <div class="ct-header-cell reservation">예약 기간</div>
          </div>
          <!-- END ct-header -->
          <div class="ct-body reservation-ct">
            <div class="ct-body-row" v-for="vo in rv_vos" :key="vo">
              <div class="ct-body-cell reservation">
                <input type="hidden" name="user_no" class="user_no" :user_no="vo.user_no" />
                <!--<input type="hidden" name="room_no" id="room_no" th:attr="room_no=${vo.room_no}" />-->
                <input type="checkbox" name="reserve_no" id="reserve_no" class="room-checkbox" value=""
                  :reserve_no="vo.reserve_no" />
              </div>
              <div class="ct-body-cell reservation">
                <span class="user_name">{{ vo.user_name }}</span>
              </div>
              <div class="ct-body-cell reservation reserve_user_email">
                <span class="user_email">{{ vo.user_email }}</span>
              </div>
              <div class="ct-body-cell reservation">
                <span class="user_tel">{{ vo.user_tel }}</span>
              </div>
              <div class="ct-body-cell reservation reserve_date_set">
                <span class="reserve-stime">{{ vo.reserve_stime }} ~ {{ vo.reserve_etime }}</span>
              </div>
            </div>
            <!-- END ct-body-row" -->
          </div>
          <input @click="clickReserveDeleteBtn" type='button' id='btn-reserve-cancel' class='btn-reserve-cancel'
            value='예약 취소' />
          <!-- END ct-body -->
        </div>
        <!-- END custom-table -->
      </div>
      <!-- select-room-section -->
    </div>
    <!-- cardSection schedule -->
  </div>
  <!-- boardWrap -->
</template>

<style scoped>
@import '@/assets/CSS/dash-board/dash-schedule.scss';
</style>

<script>
import $ from 'jquery';
import axios from 'axios';

export default {
  name: 'ReservationView',

  data() {
    return {
      backoffice_no: this.$cookies.get('backoffice_no'),
      room_no: '',
      not_sdate: '',
      not_edate: '',
      not_stime: '',
      not_etime: '',
      off_type: '',
      page: 1,

      rv_vos: [],
      reserve_stime: '',
      reserve_etime: '',

      stop_flag: false,
      check_arr: [],
    };
  },

  methods: {
    getReservationList() {
      this.room_no = this.$route.query.room_no;
      this.not_sdate = this.$route.query.not_sdate;
      this.not_edate = this.$route.query.not_edate;
      this.not_stime = this.$route.query.not_stime;
      this.not_etime = this.$route.query.not_etime;
      this.off_type = this.$route.query.off_type;

      const params = new URLSearchParams();
      params.append('backoffice_no', this.backoffice_no);
      params.append('room_no', this.room_no);
      params.append('not_sdate', this.not_sdate);
      params.append('not_edate', this.not_edate);
      params.append('not_stime', this.not_stime);
      params.append('not_etime', this.not_etime);
      params.append('off_type', this.off_type);
      params.append('page', this.page);

      console.log(this.backoffice_no);
      console.log(this.room_no);
      console.log(this.not_sdate);
      console.log(this.not_edate);
      console.log(this.not_stime);
      console.log(this.not_etime);
      console.log(this.off_type);

      axios.get(`http://localhost:8800/backoffice/dash/reservation?${params}`)
        .then((res) => {
          console.log(res.data);

          this.reserve_stime = res.data.reserve_stime;
          this.reserve_etime = res.data.reserve_etime;
          this.rv_vos = res.data.rv_vos;
        });
    },

    clickReserveDeleteBtn() {
      console.log(this.check_arr);

      this.check_arr = $('input[type=checkbox]:checked').parents('.ct-body-row');

      if (this.check_arr.length === 0) {
        $('.popup-background:eq(1)').removeClass('blind');
        $('#common-alert-popup').removeClass('blind');
        $('.common-alert-txt').text('선택된 항목이 없습니다.');
      } else {
        let tmp_flag = false;
        for (let i = 0; i < this.check_arr.length; i++) {
          const s_date = $(this.check_arr[i]).find('.reserve-stime').text().split(' ~ ')[0];

          if (new Date(s_date) < new Date()) {
            $('.popup-background:eq(1)').removeClass('blind');
            $('#common-alert-popup').removeClass('blind');
            $('.common-alert-txt').html('선택된 항목 중 이용중인 예약이 존재합니다.<br><br> 이용중인 항목은 예약 취소할 수 없습니다.');
            tmp_flag = true;
            break;
          }
        }

        if (!tmp_flag) {
          $('.popup-background:eq(0)').removeClass('blind');
          $('#reserve-delete-popup').removeClass('blind');
        }
      }
    },
  },

  mounted() {
    this.$nextTick(() => {
      this.getReservationList();
    });
  },
};
</script>
