<!-- eslint-disable radix -->
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
  <div v-if="load === true" class="space-detail-wrap">
    <section class="page-title-section">
        <span class="page-title">예약하기</span>
    </section>

    <section class="body-semi-wrap">
        <div class="deatil-info-section">
            <section class="reserve-info-section">
                <span class="section-title">예약 정보</span>
                <div class="reserve-info-wrap">
                    <section>
                        <img :src="list.pdto.backoffice_image" alt="result-img" class="reserve-info-img" />
                    </section>
                    <section class="reserve-info-txt">
                        <span class="info-company-name" :backoffice_no="list.pdto.backoffice_no">{{list.pdto.company_name}}</span>
                        <ul class="info-li-wrap">
                            <li class="info-li">
                                <label class="info-label">공간 타입</label>
                                <span id="info-room-type" class="info-text" :room_type="list.pdto.room_type">{{list.pdto.room_type}}</span>
                            </li>
                            <li class="info-li">
                                <label class="info-label">예약 날짜</label>
                                <span class="info-text">{{list.pdto.reserve_stime}} ~ {{list.pdto.reserve_etime}}</span>
                            </li>
                            <li class="info-li">
                                <label class="info-label">공간 이름</label>
                                <span class="info-text">{{list.pdto.room_name}}</span>
                            </li>
                            <li class="info-li">
                                <label class="info-label">공간 가격</label>

                                <span v-if="list.pdto.room_type !== '오피스'" class="info-text room_price"> 
                                  {{list.pdto.room_price}}원/시간
                                </span>

                                <span v-if="list.pdto.room_type === '오피스'" class="info-text room_price"> 
                                  {{list.pdto.room_price}}원/개월
                                </span>
                            </li>
                        </ul>
                    </section>
                </div>
            </section>
            <section class="user-host-info-section">
                <section class="uh-info-section">
                    <span class="person-type-title">예약자 정보</span>
                    <div class="uh-info-wrap">
                        <ul>
                            <li class="host-info-li">
                                <label class="uh-label">예약자</label>
                                <span class="uh-text">
                                  <input type="text" id="user_name" class="pay-input" :value="list.pdto.user_name" readonly/>
                                </span>
                            </li>
                            <li class="host-info-li">
                                <label  class="uh-label">연락처</label>
                                <span class="uh-text">
                                  <input type="text" class="pay-input" :value="list.pdto.user_tel" readonly/>
                                </span>
                            </li>
                            <li class="host-info-li">
                                <label  class="uh-label">이메일</label>
                                <span class="uh-text">
                                    <input type="text" class="pay-input" :value="list.pdto.user_email" readonly/>
                                </span>
                            </li>
                        </ul>
                    </div>
                </section>
                <section class="uh-info-section">
                    <span class="person-type-title">호스트 정보</span>
                    <div class="uh-info-wrap">
                        <ul>
                            <li class="host-info-li">
                                <label class="uh-label">사업자명</label>
                                <span class="uh-text">{{list.pdto.owner_name}}</span>
                            </li>
                            <li class="host-info-li">
                                <label  class="uh-label">위치</label>
                                <span class="uh-text">{{list.pdto.roadname_address}}&nbsp;({{list.pdto.detail_address}})</span>
                            </li>
                            <li class="host-info-li">
                                <label  class="uh-label">연락처</label>
                                <span class="uh-text">{{list.pdto.backoffice_tel}}&nbsp;{{list.pdto.backoffice_email}}</span>
                            </li>
                        </ul>
                    </div>
                </section>
            </section>
            <section  class="careful-info-section">
                <span class="section-title">예약시 주의 사항</span>

                <ul class="section-list-wrap">
                    <li class="section-list">1. 결제 방법 - 선결제 선택 시 보증금 없이 총 사용 금액이 결제됩니다.</li>
                    <li class="section-list">2. 결제 방법 - 당일 결제 선택 시 보증금(총 예약 금액의 20%)만 결제되며, 나머지 금액은 예약 당일 해당 공간에서 이루어집니다.</li>
                    <li class="section-list">3. 공간 내 기물 파손 시 배상해야 해며, 배상 시 비용은 새제품 가격으로 책정됩니다.</li>
                    <li class="section-list">4. 공간 내 화기 사용은 금하며, 건물 전체가 금연 건물입니다.</li>
                    <li class="section-list">5. 공간 내 간단한 음료와 간식만 드실 수 있으며, 드신 후 쓰레기는 분리 배출 부탁드립니다.</li>
                    <li class="section-list">6. 이용중 지나친 소음이 발생하지 않도록 주의 부탁드립니다.</li>
                    <li class="section-list">7. 선결제 시, 총 결제 금액의 5%가 마일리지로 적립됩니다.</li>
                </ul>
            </section>
            <section class="refund-info-section">
                <span class="section-title">환불 규정 안내</span>
                <ul class="refund-list-wrap">
                    <li class="refund-list">
                        <label class="refund-label">예약 후 1시간 이내 취소</label>
                        <span>
                            총 금액의 100% 환불
                        </span>
                    </li>
                    <li class="refund-list">
                        <label class="after-refund-label">예약 후 1시간 이후 취소</label>
                        <span>
                            총 금액의 80% 환불 (보증금을 제외한 나머지 가격 환불)
                        </span>
                    </li>
                </ul>
            </section>
            <section class="payment-method-section">
                <span class="section-title">결제 방법</span>
                <div class="payment-methods-wrap">
                    <section class="money-payment">
                        <span class="payment-method-wrap">
                            <div class="payment-method">
                                <div class="custom-radio">
                                    <div @click="choice_prepayment" class="inner-radio choice-radio"></div>
                                </div>
                                <span class="payment-method-text">선결제</span>
                            </div>
                            <div v-if="list.pdto.room_type !== '오피스'" class="payment-method">
                                <div class="custom-radio">
                                    <div @click="choice_post_payment" class="inner-radio"></div>
                                </div>
                                <span class="payment-method-text">후불 결제</span>
                            </div>
                        </span>
                        <span class="payment-method-wrap">
                            <div class="payment-method">
                                <div class="custom-radio">
                                    <div class="inner-radio choice-radio"></div>
                                </div>
                                <span class="payment-method-text">카카오페이 결제</span>
                            </div>
                        </span>
                    </section>

                    <section class="mileage-payment">
                        <div class="mileage-line-wrap">
                            <label class="mileage-label">사용할 마일리지</label>
                            <input type="text" @keyup="use_mileage_txt($event.target)" id="use-mileage" class="use-mileage-input" placeholder="0" value="" numberOnly/>
                            <input type="button" @click="use_mileage" class="use-mileage-btn" value="마일리지 사용"/>
                        </div>
                        <div class="my-mileage-wrap">
                            <label>
                                나의 마일리지 : 
                            </label>
                            <span id="my-mileage" :my-mileage="list.pdto.mileage_total">
                                {{list.pdto.mileage_total}}원
                            </span>
                            <br>
                             <label>
                                최대 사용 가능 마일리지 : 
                            </label>
                            <span id="max-use-mileage" :max-use-mileage="list.pdto.mileage_total">
                                {{list.pdto.mileage_total}}원
                            </span>
                        </div>
                    </section>
                </div>
            </section>
            <section class="payment-section">
                <span class="section-title">결제 금액 안내</span>
                <div class="payment-info-wrap">
                    <section class="payment-company-section">
                        <ul class="payment-info-li-wrap">
                            <li class="info-li">
                                <label class="info-label">공간 타입</label>
                                <span class="info-text">{{list.pdto.room_type}}</span>
                            </li>
                            <li class="info-li">
                                <label class="info-label">예약 날짜</label>
                                <span class="info-text">{{list.pdto.reserve_stime}} ~ {{list.pdto.reserve_etime}}</span>
                            </li>
                            <li class="info-li">
                                <label class="info-label">공간 이름</label>
                                <span class="info-text">{{list.pdto.room_name}}</span>
                            </li>
                            <li class="info-li">
                                <label class="info-label">공간 가격</label>
                                <span v-if="list.pdto.room_type !== '오피스'" class="info-text room_price">{{list.pdto.room_price}}원/시간</span>
                                <span v-if="list.pdto.room_type === '오피스'" class="info-text room_price">{{list.pdto.room_price}}원/개월</span>
                            </li>
                        </ul>
                    </section>
                    <section class="pay-mileage-section">
                        <ul class="payment-info-li-wrap">
                            <li class="info-li">
                                <label class="pay-info-label">총 결제 금액</label>
                                <span id="payment_all" class="pay-info-text" :payment_all="list.payment_all">{{list.payment_all}}</span>
                            </li>
                            <li class="info-li">
                                <label class="pay-info-label">마일리지 예상 적립 금액</label>
                                <span id="earned_mileage" class="pay-info-text" :earned_mileage="list.earned_mileage">{{list.earned_mileage}}</span>
                            </li>
                        </ul>
                    </section>
                </div>
            </section>
            <section class="btn-pay-section">
                <div @click="payment" id="pay-btn" class="pay-btn">
                    <span>결제하기</span>
                </div>
            </section>
        </div>
    </section>
  </div>
</template>

<style lang="scss" scoped >
 @import '@/assets/CSS/office/reserve_info.scss';
</style>

<script>
import axios from 'axios';
import $ from 'jquery';

export default {
  name: 'PaymentView',
  data() {
    return {
      list: '',
      reserveNo: '',
      IMP: window,
      payment_state: 'T',
      time: '',
      seconds: 60 * 10,
      payment_all: '',
      earned_mileage: '',
      actual_payment: '',
      max_use_mileage: '',
      deposit: '',
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

          this.IMP.init('imp26554321');

          this.reserveNo = this.$route.params.parameters.split('reserve_no=')[1];
          axios.get(`http://localhost:8800/office/payment?reserve_no=${this.reserve_no}`)
            .then((res) => {
              this.list = res.data;
              this.timer();

              $('.room_price').each(function (index, value) {
                const price = $(value).text();
                $(this).text(price.replace(/\B(?=(\d{3})+(?!\d))/g, ','));
              });

              $('#payment_all').text(
                $('#payment_all')
                  .text()
                  .replace(/\B(?=(\d{3})+(?!\d))/g, ','),
              );

              $('#earned_mileage').text(
                $('#earned_mileage')
                  .text()
                  .replace(/\B(?=(\d{3})+(?!\d))/g, ','),
              );

              $('#my-mileage').text(
                $('#my-mileage')
                  .text()
                  .replace(/\B(?=(\d{3})+(?!\d))/g, ','),
              );

              $('#max-use-mileage').text(
                $('#max-use-mileage')
                  .text()
                  .replace(/\B(?=(\d{3})+(?!\d))/g, ','),
              );

              this.payment_all = parseInt($('#payment_all').attr('payment_all'));
              this.earned_mileage = parseInt($('#earned_mileage').attr('earned_mileage'));
              this.actual_payment = payment_all;

              this.max_use_mileage = parseInt($('#my-mileage').attr('my-mileage'));
              this.deposit = payment_all * 0.2;

              if ((this.payment_all * 0.8) <= this.max_use_mileage) {
                this.max_use_mileage = payment_all * 0.8;
                $('#max-use-mileage').attr('max-use-mileage', this.max_use_mileage);
                $('#max-use-mileage').text(this.max_use_mileage);
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
    /** 결제 시간 타이머 함수 */
    timer() {
      this.time = setInterval(() => {
        if (this.seconds === 0) {
          $('.popup-background:eq(1)').removeClass('blind');
          $('#common-alert-popup').removeClass('blind');
          $('.common-alert-txt').html('결제 시간을 초과했습니다.<br>다시 시도해주세요.');

          const url = window.location.href.split('/payment')[0];

          if ($('#info-room-type').attr('room_type') === '오피스') {
            window.location.href = `${url}/space_office?backoffice_no=${$('.info-company-name').attr('backoffice_no')}`;
          } else {
            window.location.href = `${url}/space?backoffice_no=${$('.info-company-name').attr('backoffice_no')}`;
          }
          clearInterval(this.time);
        }

        this.seconds--;
      }, 1000);
    },
    /** 선결제 클릭 이벤트 */
    choice_prepayment() {
      $('.mileage-line-wrap').removeClass('blind');
      $('.my-mileage-wrap').removeClass('blind');

      this.payment_state = 'T';
      $('.inner-radio:eq(0)').addClass('choice-radio');
      $('.inner-radio:eq(1)').removeClass('choice-radio');

      $('#payment_all').text(this.payment_all);
      $('#earned_mileage').text(this.earned_mileage);

      $('#payment_all').text(
        $('#payment_all')
          .text()
          .replace(/\B(?=(\d{3})+(?!\d))/g, ','),
      );

      $('#earned_mileage').text(
        $('#earned_mileage')
          .text()
          .replace(/\B(?=(\d{3})+(?!\d))/g, ','),
      );

      $('#payment_all').attr('payment_all', this.payment_all);
      $('#earned_mileage').attr('earned_mileage', this.earned_mileage);
    },
    /** 후결제 클릭 이벤트 */
    choice_post_payment() {
      $('.mileage-line-wrap').addClass('blind');
      $('.my-mileage-wrap').addClass('blind');

      this.payment_state = 'F';
      $('.inner-radio:eq(0)').removeClass('choice-radio');
      $('.inner-radio:eq(1)').addClass('choice-radio');

      $('#payment_all').text(this.deposit);
      $('#earned_mileage').text('0');

      $('#payment_all').text(
        $('#payment_all')
          .text()
          .replace(/\B(?=(\d{3})+(?!\d))/g, ','),
      );

      $('#payment_all').attr('payment_all', this.deposit);
      $('#earned_mileage').attr('earned_mileage', 0);
    },
    /** 사용할 마일리지 입력 */
    use_mileage_txt(param) {
      $(param).val($(param).val().replace(/[^0-9]/g, ''));
    },
    /** 마일리지 사용 버튼 클릭 */
    use_mileage() {
      this.actual_payment = this.payment_all;
      const my_mileage = parseInt($('#my-mileage').attr('my-mileage'));	// 내가 가진 마일리지
      const use_mileage = parseInt($('#use-mileage').val()) || 0;		// 사용할 마일리지

      if ($('.inner-radio:eq(0)').hasClass('choice-radio')) {
      // 선불 결제 시 로직
        this.origin_payment = this.payment_all;	// 원래 결제할 전체 금액
        this.actual_payment = this.payment_all; // 마일리지 사용 후 결제 금액

        // 가진 마일리지보다 적게 사용할 때
        if (this.use_mileage <= this.max_use_mileage) {
        // 사용 마일리지가 원결제 금액보다 작을 때
          if (this.use_mileage <= this.payment_all) {
            if (this.use_mileage === 0 || this.use_mileage === '') {
              this.actual_payment = this.payment_all;
              this.earned_mileage = Math.round(this.actual_payment * 0.05);
            } else {
              this.actual_payment = this.payment_all - this.use_mileage;
              this.earned_mileage = Math.round(this.actual_payment * 0.05);
            }
          } else {
          // 사용 마일리지가 원결제 금액보다 클 때
            $('#use-mileage').val('');
          }

          $('#earned_mileage').text(this.earned_mileage);
          $('#payment_all').text(this.actual_payment);
        } else {
        // 가진 마일리지보다 많이 사용할 때
          $('#use-mileage').val('');
          this.actual_payment = this.payment_all;
          this.earned_mileage = Math.round(this.actual_payment * 0.05);
          $('#payment_all').text(this.actual_payment);
          $('#earned_mileage').text(this.earned_mileage);
        }
      } else if ($('.inner-radio:eq(1)').hasClass('choice-radio')) {
      // 후불 결제 시 로직
        const payment_later = this.payment_all * 0.2;
        this.actual_payment = payment_later;
        this.earned_mileage = Math.round(this.actual_payment * 0.05);
        $('#earned_mileage').text(this.earned_mileage);

        // 가진 마일리지보다 적게 사용할 때
        if (this.use_mileage <= this.max_use_mileage) {
        // 사용 마일리지가 원결제 금액보다 작을 때
          if (this.use_mileage <= this.actual_payment) {
            if (this.use_mileage === 0 || this.use_mileage === '') {
              this.actual_payment = this.payment_later;
              this.earned_mileage = this.actual_payment * 0.05;
            } else {
              this.actual_payment = this.payment_later - this.use_mileage;
              this.earned_mileage = this.actual_payment * 0.05;
            }
          } else {
          // 사용 마일리지가 원결제 금액보다 클 때
            $('#use-mileage').val('');
          }

          $('#earned_mileage').text(this.earned_mileage);
          $('#payment_all').text(this.actual_payment);
        } else {
        // 가진 마일리지보다 많이 사용할 때
          $('#use-mileage').val('');
          this.actual_payment = this.payment_later;
          this.earned_mileage = Math.round(this.actual_payment * 0.05);
          $('#earned_mileage').text(this.earned_mileage);
          $('#payment_all').text(this.actual_payment);
        }
      }

      // 숫자 콤마 찍기
      $('#payment_all').text(
        $('#payment_all')
          .text()
          .replace(/\B(?=(\d{3})+(?!\d))/g, ','),
      );

      $('#earned_mileage').attr('earned_mileage', this.earned_mileage);
      $('#payment_all').attr('payment_all', this.actual_payment);
    },
    /** 결제 로직 */
    payment() {
      if (this.axiosFlag) {
        this.axiosFlag = false;

        // 로그인 여부 체크 -> 헤더를 위해
        axios.get('http://localhost:8800/loginCheck')
          .then((response) => {
            this.axiosFlag = true;

            // 로그인 되어 있음
            if (response.data.result === '1') {
              this.$store.commit('office_setLogin_true');

              this.IMP.request_pay({ // param
                pg: 'kakaopay',
                pay_method: 'card',
                name: '주문명 : 결제 테스트',
                amount: $('#payment_all').attr('payment_all'),
              }, (rsp) => {
                if (rsp.success) {
                  const { imp_uid } = rsp;

                  // 로딩 화면
                  $('.popup-background:eq(1)').removeClass('blind');
                  $('#spinner-section').removeClass('blind');

                  const params = new URLSearchParams();
                  params.append('imp_uid', imp_uid);
                  params.append('payment_total', $('#payment_all').attr('payment_all'));
                  params.append('use_mileage', $('#use-mileage').val() || 0);
                  params.append('actual_payment', $('#payment_all').attr('payment_all'));
                  params.append('payment_state', this.payment_state);
                  params.append('user_no', window.atob(this.$cookies.get('user_no')));
                  params.append('reserve_no', this.reserveNo);
                  params.append('payment_method', 'kakaopay');

                  axios.post('http://localhost:8800/office/paymentOK', params)
                    .then((res) => {
                      // 로딩 화면 닫기
                      $('.popup-background:eq(1)').addClass('blind');
                      $('#spinner-section').addClass('blind');

                      if (res.data.result === 1) {
                        alert('결제 성공');
                        const port = window.location.href.split('localhost:')[1].split('/static')[0];

                        if (port === '8800') {
                          window.location.href = 'http://localhost:8800/static/index.html#/reserve_list';
                        } else {
                          window.location.href = `http://localhost:${port}/reserve_list`;
                        }
                      } else {
                        alert(`결제에 실패했습니다. 에러 내용 : ${rsp.error_msg}`);
                      }
                    })
                    .catch(() => {
                      // 로딩 화면 닫기
                      $('.popup-background:eq(1)').addClass('blind');
                      $('#spinner-section').addClass('blind');

                      alert(`결제에 실패했습니다. 에러 내용 : ${rsp.error_msg}`);
                    });
                } else {
                  alert(`결제에 실패했습니다. 에러 내용 : ${rsp.error_msg}`);
                }
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
  },
};
</script>
