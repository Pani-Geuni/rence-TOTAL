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
  <div class="my-page-wrap" v-if="load === true">
    <div class="my-page">
      <section class="profile-section">
        <div class="profile">

          <div class="profile-img-wrap">
            <img v-bind:src="list.user_image" alt="profile-img" class="profile-img" /> 
            <span class="img-update-txt" @click="show_modify_img_popup">프로필 사진 수정</span>
          </div>

          <div class="profile-info-wrap">
            <section class="info-name-section">
              <span class="info-name">{{list.user_name}}</span>
              <span class="pw-update-txt" @click="show_modify_pw_popup">비밀번호 수정</span>
            </section>
            <section class="info-etc-section">
              <span>{{list.user_id}}&nbsp;</span> 
              <span>•</span> 
              <span>&nbsp;{{list.user_email}}</span>
            </section>
            <section class="info-etc-section info-etc-section-second">
              <span>{{list.user_tel}}</span> 
              <span>•</span> 
              <span>{{list.user_birth}}</span>
            </section>
            <section class="user-delete-section">
              <span class="user-delete-btn" @click="show_user_delete_popup">탈퇴하기</span>
            </section>
          </div>

        </div>
      </section>

      <section class="my-page-menu-section">
        <router-link to="#/reserve_list">
          <div id="reserve-menu" class="menu-box left-menu menu-top">
            <section class="menu-box-img-section">
              <img src="@/assets/IMG/my-page/calendar.svg" alt="menu-box-img" class="menu-box-img" />
            </section>
            <section  class="menu-box-txt-section">
              <span class="menu-box-title">예약 현황</span>
              <span class="menu-box-txt">예약한 내역을 확인해보세요.</span>
            </section>
          </div>
        </router-link>

        <router-link to="#/mileage">
          <div id="mileage-menu" class="menu-box menu-top">
              <section class="menu-box-img-section">
                  <img src="@/assets/IMG/my-page/bi_coin.svg" alt="menu-box-img" class="menu-box-img" />
              </section>
              <section class="menu-box-txt-section">
                  <div class="menu-box-txt-wrap">
                      <span class="menu-box-title">마일리지 조회</span>
                      <span class="point-txt">내 마일리지 : {{list.mileage_total}}원</span>
                  </div>
                  <span class="menu-box-txt">쌓인 마일리지를 조회할 수 있어요.</span>
              </section>
          </div>
        </router-link>

        <router-link to="#/review">
          <div id="review-menu" class="menu-box left-menu">
            <section class="menu-box-img-section">
              <img src="@/assets/IMG/my-page/review.svg" alt="menu-box-img" class="menu-box-img" />
            </section>
            <section  class="menu-box-txt-section">
              <span class="menu-box-title">후기 내역</span>
              <span class="menu-box-txt">작성한 후기 내역을 확인해보세요.</span>
            </section>
          </div>
        </router-link>

        <router-link to="#/question">
          <div id="question-menu" class="menu-box">
            <section class="menu-box-img-section">
              <img src="@/assets/IMG/my-page/letter.svg" alt="menu-box-img" class="menu-box-img" />
            </section>
            <section  class="menu-box-txt-section">
              <span class="menu-box-title">문의 내역</span>
              <span class="menu-box-txt">업체에 문의한 내용을 바로 볼 수 있어요.</span>
            </section>
          </div>
        </router-link>

      </section>
    </div>
  </div>
</template>

<style lang="scss" scoped >
  @import '@/assets/CSS/office/my-page.scss';
</style>

<script>
import axios from 'axios';
import $ from 'jquery';

export default {
  name: 'MyPageView',
  data() {
    return {
      list: '',
      load: false,
    };
  },
  mounted() {
    // 로딩 화면
    $('.popup-background:eq(1)').removeClass('blind');
    $('#spinner-section').removeClass('blind');

    axios.get('http://localhost:8800/rence/go_my_page')
      .then((res) => {
        this.list = res.data.list;
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
  },
  methods: {
    show_user_delete_popup() {
      $('.popup-background:eq(0)').removeClass('blind');
      $('#user-delete-confirm-popup').removeClass('blind');
    },
    show_modify_img_popup() {
      $('.popup-background:eq(0)').removeClass('blind');
      $('#modify-img-section').removeClass('blind');
    },
    show_modify_pw_popup() {
      $('.popup-background:eq(0)').removeClass('blind');
      $('#modify-pw-section').removeClass('blind');
    },
  }, // END methods()
};
</script>
