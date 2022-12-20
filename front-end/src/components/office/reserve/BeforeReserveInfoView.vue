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
        <span class="page-title">과거에 예약한 공간 정보를 보여드려요</span>
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
        </div>
        
        <!-- START BTN-SECTION  -->
        <section class="btn-pay-section">
        	<block v-if="list.pdto.cancel_state === 'C'">
        		<div id="pay-canceled-btn" class="pay-canceled-btn" :idx="list.info_obj.reserve_no">
                <span>취소된 예약</span>
            </div>
        	</block>
        	<block v-if="list.pdto.cancel_state !== 'C' && list.is_write_review === false">
            <div id="write-review-btn" @click="show_review_popup" class="write-review-btn">
              <span>후기 작성</span>
            </div>
          </block>
        </section>
        <!-- END BTN-SECTION  -->
        
        <block v-if="list.is_write_review === false">
	        <!-- 후기쓰기 팝업창 -->
	        <div id="review-popup" class="review-popup-wrap blind">
	            <span class="review-popup-title">후기 남기기</span>
	
	            <section class="review-popup-type-select-wrap">
	                <span class="review-popup-small-title">이용한 공간명</span>
	
	                <!-- select 열 때 open-select추가 -->
	                <div class="review-popup-select-val-wrap">
	                    <span class="review-popup-select-value">
	                        {{list.info_obj.company_name}} - {{list.info_obj.room_name}}
	                    </span>
	                </div>
	            </section>
	            <section class="textarea-section">
	                <textarea id="review-write" @click="remove_null_input_border" @keyup="check_textarea_length($event.target)" @keydown="check_textarea_length($event.target)" class="review-write"></textarea>
	                <div class="txt-length-wrap">
	                    <span class="review-length">0</span>
	                    <span class="review-total-length">/ 500</span>
	                </div>
	            </section>
	            <section class="popup-star-section">
	                <span class="popup-star-title">별점</span>
	                <ul class="popup-star-wrap">
	                    <li id="star_1" class="popup-star-li" @click="set_review_star($event.target)">
                        <img src="@/assets/IMG/common/y-star.svg" alt="후기 리뷰 별점 이미지" class="y-star r-star blind"/>
                        <img src="@/assets/IMG/common/star.svg" alt="후기 리뷰 별점 이미지" class="g-star r-star"/>
	                    </li>
	                    <li id="star_2" class="popup-star-li" @click="set_review_star($event.target)">
                        <img src="@/assets/IMG/common/y-star.svg" alt="후기 리뷰 별점 이미지" class="y-star r-star blind"/>
                        <img src="@/assets/IMG/common/star.svg" alt="후기 리뷰 별점 이미지" class="g-star r-star"/>
	                    </li>
	                    <li id="star_3" class="popup-star-li" @click="set_review_star($event.target)">
                        <img src="@/assets/IMG/common/y-star.svg" alt="후기 리뷰 별점 이미지" class="y-star r-star blind"/>
                        <img src="@/assets/IMG/common/star.svg" alt="후기 리뷰 별점 이미지" class="g-star r-star"/>
	                    </li>
	                    <li id="star_4" class="popup-star-li" @click="set_review_star($event.target)">
                        <img src="@/assets/IMG/common/y-star.svg" alt="후기 리뷰 별점 이미지" class="y-star r-star blind"/>
                        <img src="@/assets/IMG/common/star.svg" alt="후기 리뷰 별점 이미지" class="g-star r-star"/>
	                    </li>
	                    <li id="star_5" class="popup-star-li" @click="set_review_star($event.target)">
                        <img src="@/assets/IMG/common/y-star.svg" alt="후기 리뷰 별점 이미지" class="y-star r-star blind"/>
                        <img src="@/assets/IMG/common/star.svg" alt="후기 리뷰 별점 이미지" class="g-star r-star"/>
	                    </li>
	                </ul>
	            </section>
	            <section class="review-popup-btn-section">
	                <span id="review-create-btn" @click="create_review($event.target)" class="popup-create-btn" :room_no="list.info_obj.room_no" :backoffice_no="list.info_obj.backoffice_no">등록</span>
	                <span id="review-close-btn" @click="close_review_popup" class="popup-close-btn">취소</span>
	            </section>
	        </div>
        </block>
    </section>
</div>
</template>

<style lang="scss" scoped>
  @import '@/assets/CSS/office/reserve_info.scss';
</style>

<script>
import axios from 'axios';
import $ from 'jquery';

export default {
  name: 'BeforeReserveInfoView',
  data() {
    return {
      list: '',
      review_flag: true,
      reserveNo: '',
      load: false,
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

          this.reserveNo = decodeURI(window.location.href).split('reserve_no=')[1];

          axios.get(`http://localhost:8800/rence/reserved_info?reserve_no=${reserveNo}`)
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
    /** 후기 팝업 열기 버튼 클릭 이벤트 */
    show_review_popup() {
      $('#review-popup').removeClass('blind');
    },
    /** * 후기 작성 경고 테두리 제거 */
    remove_null_input_border() {
      $('#review-write').removeClass('null-input-border');
    },
    /** 후기 팝업 닫기 버튼 클릭 이벤트 */
    close_review_popup() {
      // TEXTAREA 초기화
      $('#review-write').val('');
      // 글자수 초기화
      $('.review-length').text('0');

      $('#review-write').removeClass('null-input-border');

      // 별점 초기화
      $('.y-star').addClass('blind');
      $('.g-star').removeClass('blind');

      // 팝업 닫기
      $('#review-popup').addClass('blind');
    },
    /** 후기 작성 시 글자수 제한 */
    check_textarea_length(param) {
      if ($(param).val().length > 500) {
        $(param).val($(param).val().substring(0, 500));
      }
      $('.review-length').text($(param).val().length);
    },
    /** 별점 클릭 이벤트 */
    set_review_star(param) {
      // 별 초기화
      $('.y-star').addClass('blind');
      $('.g-star').removeClass('blind');

      // 클릭한 별 파악
      const lastIdx = $(param).attr('id');
      const arr = $('.popup-star-li');

      for (let i = 0; i < arr.length; i++) {
        $(arr[i]).children('.y-star').removeClass('blind');
        $(arr[i]).children('.g-star').addClass('blind');

        if ($(arr[i]).attr('id') === lastIdx) {
          break;
        }
      }
    },
    /** 리뷰 생성 로직 함수 */
    create_review(param) {
      if (this.review_flag) {
        if ($('#review-write').val().trim().length > 0) {
          this.review_flag = false;

          // 로딩 화면
          $('.popup-background:eq(0)').removeClass('blind');
          $('#spinner-section').removeClass('blind');

          let point = 0;
          for (let i = 0; i < 5; i++) {
            if ($('.g-star').hasClass('blind')) point++;
          }

          const params = new URLSearchParams();
          params.append('user_no', this.$cookies.get('user_no'));
          params.append('backoffice_no', $(param).attr('backoffice_no'));
          params.append('room_no', $(param).attr('room_no'));
          params.append('review_point', point);
          params.append('review_content', $('#review-write').val().trim());

          axios.get('http://localhost:8081/rence/insert_review', params)
            .then((res) => {
              this.review_flag = true;

              // 로딩 화면 닫기
              $('.popup-background:eq(0)').addClass('blind');
              $('#spinner-section').addClass('blind');

              if (res.data.result === 1) {
              // TEXTAREA 초기화
                $('#review-write').val('');

                // 글자수 초기화
                $('.review-length').text('0');

                $('#review-write').removeClass('null-input-border');

                // 별점 초기화
                $('.y-star').addClass('blind');
                $('.g-star').removeClass('blind');

                // 팝업 닫기
                $('#review-popup').addClass('blind');

                $('.popup-background:eq(1)').removeClass('blind');
                $('#common-alert-popup').removeClass('blind');
                $('.common-alert-txt').text('성공적으로 후기가 등록되었습니다.');
                $('#common-alert-btn').attr('is_reload', true);
              } else {
                $('.popup-background:eq(1)').removeClass('blind');
                $('#common-alert-popup').removeClass('blind');
                $('.common-alert-txt').text('후기등록에 실패하였습니다.');
              }
            }).catch(() => {
              this.review_flag = true;

              // 로딩 화면 닫기
              $('.popup-background:eq(0)').addClass('blind');
              $('#spinner-section').addClass('blind');

              $('.popup-background:eq(1)').removeClass('blind');
              $('#common-alert-popup').removeClass('blind');
              $('.common-alert-txt').text('오류 발생으로 인해 처리에 실패하였습니다.');
            });
        } else {
          if ($('#review-write').val().trim().length === 0) {
            $('#review-write').addClass('null-input-border');
          }
          if ($('#review-select-choice').attr('choice') !== 'true') {
            $('.question-popup-select-val-wrap:eq(1)').addClass('null-input-border');
          }
        }
      }
    },
  }, // END methods()
};
</script>
