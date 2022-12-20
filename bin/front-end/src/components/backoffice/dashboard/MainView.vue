<template>
  <div class="titleSection">
    <h1>대시보드</h1>
  </div>

  <!-- 대시보드에서 위에 있는 대시보드 2개 (예약 관리 요약, 최신 문의 내역) -->
  <div class="boardWrap">
    <div class="cardSection reserve">
      <div class="card-title">
        <h3>예약 관리 요약</h3>
      </div>
      <div class="custom-table">
        <div class="ct-header">
          <div class="ct-header-cell main">예약 날짜</div>
          <div class="ct-header-cell main">예약 공간</div>
          <div class="ct-header-cell main">예약자</div>
          <div class="ct-header-cell main">결제 금액</div>
          <div class="ct-header-cell main">상태</div>
        </div>
        <!-- END ct-header -->

        <div class="ct-body">
          <div class="ct-body-row" :idx="vos.reserve_no" v-for="vos in r_vos" :key="vos">
            <div class="ct-body-cell main">
              <p>
                <span>{{ vos.reserve_sdate }}</span> ~ <br />
                <span>{{ vos.reserve_edate }}</span>
              </p>
            </div>
            <div class="ct-body-cell main">
              <p v-text="vos.room_name"></p>
            </div>
            <div class="ct-body-cell main">
              <p v-text="vos.user_name"></p>
            </div>
            <div class="ct-body-cell main">
              <p class="reserve-price">{{ vos.actual_payment }}</p>
            </div>
            <div class="ct-body-cell main">
              <p v-if="vos.reserve_state === 'begin'">결제완료</p>
              <p v-if="vos.reserve_state === 'in_use'">이용중</p>
              <p v-if="vos.reserve_state === 'end'">이용완료</p>
              <p v-if="vos.reserve_state === 'cancel'">취소</p>
            </div>
          </div>
        </div>
        <!-- END ct-body -->
      </div>
      <!-- END custom table -->
    </div>
    <!-- END 예약 관리 요약 -->

    <div class="cardSection comment">
      <div class="card-title">
        <h3>최신 문의 내역</h3>
      </div>
      <div class="comment-list">
        <div class="comment-item" :idx="vos.comment_no" v-for="vos in c_vos" :key="vos">
          <div class="comment-title">
            <p>{{ vos.room_name }}</p>
            <p>{{ vos.comment_date }}</p>
          </div>
          <!-- END comment-title -->
          <div class="comment-content">
            <p>{{ vos.comment_content }}</p>
          </div>
        </div>
        <!-- END comment-item -->

      </div>
      <!-- END comment-list -->
    </div>
    <!-- END cardSection comment 최신 문의 내역 -->
  </div>
  <!-- END boardWrap -->

  <div class="boardWrap">
    <div class="cardSection sales">
      <div class="card-title">
        <h3>일일 정산 요약</h3>
      </div>

      <div class="sales-list">
        <div class="sales-item">
          <p>매출 순이익</p>
          <span class="sales-income">{{ svo.sales_income }}</span>
        </div>
        <!-- END sales-item -->
        <div class="sales-item">
          <p>매출액</p>
          <span class="sales-revenue">{{ svo.sales_total }}</span>
        </div>
        <!-- END sales-item -->
        <div class="sales-item">
          <p>취소 금액</p>
          <span class="sales-cancel">{{ svo.sales_cancel }}</span>
        </div>
        <!-- END sales-item -->
      </div>
      <!-- END sales-list -->
    </div>
    <!-- END cardSection sales 일일 정산 요약 -->
    <div class="cardSection room">
      <div class="card-title">
        <h3>공간 요약</h3>
      </div>
      <div class="summary-list">
        <div class="summary-list-row">
          <div class="summary-list-item">
            <p>별점</p>
            <span>{{ rmvo.review_point }}</span>
          </div>
          <!-- END summary-list-item -->
          <div class="summary-list-item">
            <p>총 예약</p>
            <span class="reserve-count">{{ rmvo.reserve_no }}</span>
          </div>
          <!-- END summary-list-item -->
        </div>
        <!-- END summary-list-row -->
        <div class="summary-list-row">
          <div class="summary-list-item">
            <p>문의</p>
            <span class="qna-count">{{ rmvo.comment_no }}</span>
          </div>
          <!-- END summary-list-item -->
          <div class="summary-list-item">
            <p>후기</p>
            <span class="review-count">{{ rmvo.review_no }}</span>
          </div>
          <!-- END summary-list-item -->
        </div>
        <!-- END summary-list-row -->
      </div>
      <!-- END summary-list -->
    </div>
    <!-- END cardSection sales 일일 정산 요약 -->
  </div>
  <!-- END boardWrap -->
</template>

<style lang="scss" scoped>

</style>

<script>
import axios from 'axios';

export default {
  name: 'DashBoardMainView',
  data() {
    return {
      backoffice_no: this.$route.query.backoffice_no,
      r_vos: [],
      r_cnt: 0,
      c_vos: [],
      c_cnt: 0,
      svo: [],
      rmvo: [],
    };
  },

  methods: {
    getReserveList() {
      const params = new URLSearchParams();
      params.append('backoffice_no', this.backoffice_no);

      const url = `http://localhost:8800/backoffice/dash/main?${params}`;
      console.log(this.backoffice_no);

      axios.get(url).then((res) => {
        console.log(res.data);
        this.r_vos = res.data.r_vos;
        this.c_vos = res.data.c_vos;
        this.svo = res.data.svo;
        this.rmvo = res.data.rmvo;
      });
    },
  },

  mounted() {
    this.$nextTick(() => {
      this.getReserveList();
    });
  },
};

</script>
