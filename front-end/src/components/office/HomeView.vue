<!-- eslint-disable max-len -->
<!-- eslint-disable no-plusplus -->
<!-- eslint-disable no-mixed-spaces-and-tabs -->
<!-- eslint-disable linebreak-style -->
<!-- eslint-disable no-tabs -->
<!-- eslint-disable no-plusplus -->
<!-- eslint-disable array-callback-return -->
<!-- eslint-disable space-before-blocks -->
<!-- eslint-disable -->

<!--
 - @author 김예은
 - @refactoring 김예은
-->

<template>
  <div class="homeWrap">
    <div class="homeTitle-wrap">
        <span class="homeTitle" data-v-007a04d7>어떤 공간을 찾으시나요?</span>
    </div>

    <div class="type-list-wrap">
        <div class="type-list type-desk">
            <router-link :to="{ name: 'ListView', params: { call: 'list_page', parameters :  'type=desk&condition=date&page=1'}}">
                <section class="type-img-section">
                    <img src="@/assets/IMG/home/desk.svg" alt="type-desk-img" class="type-img"/>
                </section>
                <section class="type-title-section">
                    <span>데스크</span>
                </section>
                <section class="type-txt-section">
                    <p class="type-txt">
                        혼자 무언가를 할 곳이 필요하신가요?
                    </p>
                </section>
            </router-link>
        </div>

        <div class="type-list type-meeting-room">
            <router-link :to="{ name: 'ListView', params: { call: 'list_page', parameters : 'type=meeting_room&condition=date&page=1' }}">
                <section class="type-img-section">
                    <img src="@/assets/IMG/home/meeting-room.svg"
                     alt="type-metting-room-img" class="type-img" />
                </section>
                <section class="type-title-section">
                    <span>회의실</span>
                </section>
                <section class="type-txt-section">
                    <p class="type-txt">
                        &nbsp;여러 사람과 함께 할<br>
                        장소를 찾고 계신가요?
                    </p>
                </section>
            </router-link>
        </div>

        <div class="type-list type-office">
            <router-link :to="{ name: 'ListView', params: { call: 'list_page', parameters : 'type=office&condition=date&page=1' }}">
                <section class="type-img-section">
                    <img src="@/assets/IMG/home/office-building.svg" alt="type-office-img" class="type-img" />
                </section>
                <section class="type-title-section">
                    <span>오피스</span>
                </section>
                <section class="type-txt-section">
                    <p class="type-txt">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        장기간 많은 사람과<br>
                        일을 할 공간을 찾아보세요
                    </p>
                </section>
            </router-link>
        </div>
        {{ this.$store.state.is_officeLogin }}
    </div>
</div>
</template>

<style lang="scss" scoped>
@import '@/assets/CSS/office/home.scss';
</style>

<script>
import axios from 'axios';
import $ from 'jquery';

export default {
  name: 'HomeView',
  mounted() {
    // 로그인 여부 체크 -> 헤더를 위해
    axios.get('http://localhost:8800/loginCheck')
      .then((res) => {
        console.log(res.data);
        // 로그인 되어 있음
        if (res.data.result === '1') {
          this.$store.commit('office_setLogin_true');
        }
        // 로그인 되어 있지 않음(or 세션 만료)
        else {
          this.$store.commit('office_setLogin_false');
        }
      })
      .catch(() => {
        $('.popup-background:eq(1)').removeClass('blind');
        $('#common-alert-popup').removeClass('blind');
        $('.common-alert-txt').text('오류 발생으로 인해 로그인 여부를 불러오는데에 실패하였습니다.');
      });
  },
};
</script>
