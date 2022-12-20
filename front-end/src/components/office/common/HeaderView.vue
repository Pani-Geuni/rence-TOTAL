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
  <section class ="headerWrap">
    <div class = "logo-section">
      <router-link to="/">
        <img src="@/assets/IMG/common/RENCE.svg" alt="로고 이미지" class="logo-mku">
      </router-link>
    </div>
    <div class = "searchBar-section">
      <div class="searchBar-wrap">
            <div id="type" class = "sb_1 type" @click="toggle_type_select">
                <span>타입</span>
              </div>
              <div id="location" class = "sb_1" @click="location_type_select">
                  <span id="location_val">장소</span>
              </div>
              <div class = "sb_2">
                  <input type="text" placeholder="검색어를 입력하세요." @keyup.enter="before_search" id="input_searchBar"/>
              </div>
              <div class = "searchBar-btnWrap">
                <img src="@/assets/IMG/header/Search_Button.svg" alt="searchBar-btn" @click="before_search" class="searchBar-btn"/>
              </div>

            <!-- CUSTOM SELECT -->
              <section>
                <!-- START TYPE SELECT -->
                <div id = "custom-type-select" class="type-select-wrap blind">
                    <ul class="type-select">
                      <li id = "type-list-desk" class="type-select-list" val="desk" @click="type_set($event.target)">데스크</li>
                      <li id = "type-list-metting-room" class="type-select-list"
                        val="meeting_room" @click="type_set($event.target)">회의실</li>
                      <li id = "type-list-office" class="type-select-list" val="office" @click="type_set($event.target)">오피스</li>
                    </ul>
                </div>
                <!-- END TYPE SELECT -->

                <!-- START Location SELECT -->
                <div id = "custom-location-select" class="location-select-wrap blind">
                    <ul id = "location-city" class="location-select">
                        <li class="location-select-list" v-for="c in city" :key="c" @click="load_town({c})">{{ c }}</li>
                    </ul>
                    <ul id = "location-town" class="location-select blind">
                        <li class="location-select-list" v-for="t in town" :key="t" @click="set_location({t})">{{ t }}</li>
                    </ul>
                </div>
                <!-- END TYPE SELECT -->
            </section>
        </div>
    </div>
    <div class="userMenu-section ">
        <!-- 로그인 전 유저 메뉴 -->
        <section :class="{'' : this.$store.state.is_officeLogin === 'false', 'blind' : this.$store.state.is_officeLogin === 'true'}" id="before_login" >
            <div id = "before_userMenu" class ="userMenu" @click="user_menu_select('before_login')">
                <img src="@/assets/IMG/header/user_menu.svg" alt="user_menu_img" class="user_menu_img"/>
                <img src="@/assets/IMG/header/bx_user-circle.png" alt="user_profile_img" class="user_profile_img"/>
            </div>

            <!-- CUSTOM SELECT -->
            <div class = "custom-select-user blind">
                <ul class="user-select-wrap">
                    <li class="user-select-list" @click="before_login_menu('go-login')">로그인</li>
                    <li class="user-select-list" @click="before_login_menu('go-join')">회원가입</li>
                    <router-link to ="/backoffice/landing">
                      <li class="user-select-list">공간등록신청</li>
                    </router-link>
                </ul>
            </div>
        </section>

        <section id="after_login" :class="{'blind' : this.$store.state.is_officeLogin === 'false', '' : this.$store.state.is_officeLogin === 'true'}">
            <div id = "after_userMenu" class ="userMenu" @click="user_menu_select('after_login')">
              <img src="@/assets/IMG/header/user_menu.svg" alt="user_menu_img" class="user_menu_img"/>
              <img alt="user_profile_img" class="user_profile_img"/>
            </div>

          <!-- CUSTOM SELECT -->
          <div class = "custom-select-user blind">
                <ul class="user-select-wrap">
                  <router-link to ="/my_page">
                    <li id = "go-myPage" class="user-select-list">마이페이지</li>
                  </router-link>
                  <router-link to ="/backoffice/landing">
                    <li class="user-select-list">공간등록신청</li>
                  </router-link>
                  <li id = "go-logOut" class="user-select-list" @click="after_login_menu('go-logOut')">로그아웃</li>
                </ul>
            </div>
        </section>
    </div>
	</section>
</template>

<style lang="scss" scoped>
@import '@/assets/CSS/office/header.scss';
</style>

<script>
import $ from 'jquery';
import cityArr from '@/assets/json/city.json';

export default {
  name: 'HeaderView',
  data() {
    return {
      city: cityArr.map((v) => {
        if (v.city != null) return v.city;
      }).slice(),
      cityObject: '',
      town: '',
      type: '',
      location: '',
    };
  },
  methods: {
    // 서치바에서 타입 클릭 시 셀렉트 SHOW/HIDE
    toggle_type_select() {
      $('#custom-type-select').toggleClass('blind');
    },
    type_set(param) {
      let typeTxt = '';

      // eslint-disable-next-line default-case
      switch ($(param).text()) {
        case '데스크':
          typeTxt = 'desk';
          break;
        case '회의실':
          typeTxt = 'meeting_room';
          break;
        case '오피스':
          typeTxt = 'office';
          break;
      }
      $('#type>span').text($(param).text());
      $('#type>span').attr('val', typeTxt);
      $('#custom-type-select').addClass('blind');
    },
    // 서치바에서 타입 클릭 시 셀렉트 SHOW/HIDE
    location_type_select() {
      if ($('#custom-location-select').hasClass('blind')) {
        $('#custom-location-select').removeClass('blind');
      } else {
        $('#custom-location-select').addClass('blind');
        $('#location-town').addClass('blind');
      }
    },
    // 도시에 맞는 타운 세팅
    load_town(param) {
      $('#location-town').removeClass('blind');
      this.town = cityArr.filter((v) => {
        if (v.city === param.c) {
          return true;
        }
      })[0].arr.slice();
    },
    // 타운 클릭 시 검색바에 값세팅
    set_location(param) {
      $('#location_val').text(param.t);
      $('#location_val').prop('idx', param.t);
      $('#location-town').addClass('blind');
      $('#custom-location-select').addClass('blind');
    },
    // 검색 로직 실행 전에 필수 값 입력여부확인
    before_search() {
      if ($('#type>span').attr('val') !== undefined) {
        this.type = $('#type>span').attr('val');
        this.location = $('#location_val').text();

        if ($('#location_val').prop('idx') === undefined) {
          this.location = '';
        }

        const port = window.location.href.split('localhost:')[1].split('/static')[0];
        window.location.href = `http://localhost:${port}/list/search_list/type=${this.type}&location=${this.location}&searchWord=${$('#input_searchBar').val().trim()}&condition=date&page=1`;
      } else {
        $('.popup-background:eq(1)').removeClass('blind');
        $('#common-alert-popup').removeClass('blind');
        $('.common-alert-txt').text('타입을 선택해주세요.');
      }
    },
    // 유저 메뉴 클릭 시 -> 메뉴 셀렉트 SHOW/HIDE
    user_menu_select(isLogin) {
      if (isLogin === 'before_login') {
        $('#before_login>.custom-select-user').toggleClass('blind');
      } else {
        $('#after_login>.custom-select-user').toggleClass('blind');
      }
    },
    // 로그인 전 유저 메뉴 클릭에 따른 이벤트
    before_login_menu(menu) {
      $('#before_login>.custom-select-user').addClass('blind');
      $('.popup-background:eq(0)').removeClass('blind');

      if (menu === 'go-login') {
        $('#login-section').removeClass('blind');
      } else if (menu === 'go-join') {
        $('#join-section').removeClass('blind');
      }
    },
    // 로그인 후 유저 메뉴 클릭에 따른 이벤트
    after_login_menu(menu) {
      if (menu === 'go-logOut') {
        $('.popup-background:eq(0)').removeClass('blind');
        $('#after_login>.custom-select-user').addClass('blind');
        $('#logout-popup').removeClass('blind');
      }
    },
  }, // END methods()
};
</script>
