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
  <div class="space-detail-wrap" v-if="load === true">
    <section class="page-title-section">
        <span class="page-title">현재 나의 예약 상세 정보를 보여드려요</span>
    </section>

    <section class="body-semi-wrap">
        <div class="deatil-info-section">
            <section class="reserve-info-section">
                <span class="section-title">예약 정보</span>
                <div class="reserve-info-wrap">
                    <section>
                        <img :src="list.info_obj.backoffice_image" alt="result-img" class="reserve-info-img" />
                    </section>
                    <section class="reserve-info-txt">
                        <span class="info-company-name">{{list.info_obj.company_name}}</span>
                        <ul class="info-li-wrap">
                            <li class="info-li">
                                <label class="info-label">공간 타입</label>
                                <span class="info-text">{{list.info_obj.room_type}}</span>
                            </li>
                            <li class="info-li">
                                <label class="info-label">예약 날짜</label>
                                <span class="info-text">{{list.info_obj.reserve_sdate}} ~ {{list.info_obj.reserve_edate}}</span>
                            </li>
                            <li class="info-li">
                                <label class="info-label">공간 이름</label>
                                <span class="info-text">{{list.info_obj.room_name}}</span>
                            </li>
                            <li class="info-li">
                                <label class="info-label">공간 가격</label>
                                <span class="info-text">{{list.info_obj.room_price}}원/시간</span>
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
                                <span class="uh-text">{{list.user_obj.user_name}}</span>
                            </li>
                            <li class="host-info-li">
                                <label class="uh-label">연락처</label>
                                <span class="uh-text">{{list.user_obj.user_tel}}</span>
                            </li>
                            <li class="host-info-li">
                                <label class="uh-label">이메일</label>
                                <span class="uh-text">{{list.user_obj.user_email}}</span>
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
                            <span class="uh-text">{{list.info_obj.backoffice_name}}</span>
                          </li>
                          <li class="host-info-li">
                            <label class="uh-label">위치</label>
                            <span class="uh-text">{{list.info_obj.full_address}}</span>
                          </li>
                          <li class="host-info-li">
                            <label class="uh-label">연락처</label>
                            <span class="uh-text">{{list.info_obj.backoffice_tel}} {{list.info_obj.backoffice_email}}</span>
                          </li>
                        </ul>
                    </div>
                </section>
            </section>
            
            <!-- START CAREFUL-INFO-SECTION  -->
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
            <!-- END CAREFUL-INFO-SECTION  -->
            
            <!-- START REFUND-INFO-SECTION  -->
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
            <!-- END REFUND-INFO-SECTION  -->
            
            <!-- START PAYMENT-SECTION  -->
            <section class="payment-section">
                <span class="section-title">결제 금액 안내</span>
                <div class="payment-info-wrap">
                    <section class="payment-company-section">
                        <ul class="payment-info-li-wrap">
                          <li class="info-li">
                            <label class="info-label">공간 타입</label>
                            <span class="info-text">{{list.info_obj.room_type}}</span>
                          </li>
                          <li class="info-li">
                            <label class="info-label">예약 날짜</label>
                            <span class="info-text">{{list.info_obj.reserve_sdate}} ~ {{list.info_obj.reserve_edate}}</span>
                          </li>
                          <li class="info-li">
                            <label class="info-label">공간 이름</label>
                            <span class="info-text">{{list.info_obj.room_name}}</span>
                          </li>
                          <li class="info-li">
                            <label class="info-label">공간 가격</label>
                            <span class="info-text">{{list.info_obj.room_price}}원/시간</span>
                          </li>
                        </ul>
                    </section>
                    <section class="pay-mileage-section">
                        <ul class="payment-info-li-wrap">
                          <li class="info-li">
                            <label class="pay-info-label">상품 가격</label>
                            <span class="pay-info-text" id="actual_payment" :payment_total="list.mdto.payment_total">{{list.mdto.payment_total}}원</span>
                          </li>
                          <li class="info-li">
                            <label class="pay-info-label">마일리지 적립 금액</label>
                            <span class="pay-info-text">{{list.mdto.mileage_change}}원</span>
                          </li>
                          <li class="info-li">
                            <label class="pay-info-label">결제 가격</label>
                            <span class="pay-info-text" id="actual_payment" :actual_payment="list.mdto.actual_payment">{{list.mdto.actual_payment}}원</span>
                          </li>
                          <li class="info-li">
                            <label class="pay-info-label">마일리지 사용 금액</label>
                            <span class="pay-info-text">{{list.mdto.use_mileage}}원</span>
                          </li>
                        </ul>
                    </section>
                </div>
            </section>
            <!-- END PAYMENT-SECTION  -->
            
            <!-- START BTN-SECTION  -->
            <section class="btn-pay-section">
              <block v-if="list.pdto.cancel_state === 'C'">
            		<div id="pay-canceled-btn" class="pay-canceled-btn" v-bind:idx="list.info_obj.reserve_no">
                    <span>취소된 예약</span>
                </div>
            	</block>
            	<block v-if="list.pdto.cancel_state !== 'C'">
                <div id="pay-cancel-btn"  @click="show_cancle_popup" class="pay-btn" v-bind:idx="list.info_obj.reserve_no">
                    <span>예약 취소</span>
                </div>
	            </block>
            </section>
            <!-- END BTN-SECTION  -->
            
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
  name: 'NowReserveInfoView',
  data() {
    return {
      list: '',
      reserveNo: '',
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

          this.reserveNo = decodeURI(window.location.href.split('reserve_no=')[1]);

          axios.get(`http://localhost:8800/rence/reserve_info?reserve_no=${reserveNo}`)
            .then((res) => {
              this.list = res.data;
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
    show_cancle_popup() {
      $('.popup-background:eq(0)').removeClass('blind');
      $('#reserve-cancel-popup').removeClass('blind');
    },
  }, // END methods()
};
</script>
