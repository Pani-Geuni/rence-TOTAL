<!-- eslint-disable prefer-destructuring -->
<!-- eslint-disable no-const-assign -->
<!-- eslint-disable prefer-destructuring -->
<!-- eslint-disable vuejs-accessibility/click-events-have-key-events -->
<!-- eslint-disable vue/require-v-for-key -->
<!-- eslint-disable linebreak-style -->
<!-- eslint-disable max-len -->

<!--
 - @author 김예은
 - @refactoring 김예은
-->

<template>
  <!-- NULL SECTION -->
  <div class="list-page-wrap blind" >
    <section class="list-box-null-section">
      <div class="null-first-section">
        <span class="advice-txt">해당 검색어에 관한 공간이 없습니다.</span>
      </div>

      <div class="null-second-section">
        <span class="advice-txt-small">이런 공간은 어떠세요?</span>
        <router-link to="/">
            <input type="button" id="go-main-btn" class="go-all-list" value="다른 곳 보러 가기" />
        </router-link>
    </div>
    </section>
  </div>

  <!-- NOT NULL SECTION -->
  <section class="listPage-section blind" >
    <div class="sort-wrap">
      <div class="sort" @click="sort_select">
        <span class="choice-sort-text" v-if="condition === 'date'">최신순</span>
        <span class="choice-sort-text" v-if="condition === 'rating'">별점순</span>
        <span class="choice-sort-text" v-if="condition === 'cheap'">가격 낮은순</span>
        <span class="choice-sort-text" v-if="condition === 'expensive'">가격 높은순</span>
        <img src="@/assets/IMG/list/dropdown.svg" alt="sort-dropdown-img" class="sort-dropdown-img" id="sort-dropdown-img" />
      </div>
      <div class="sort-select-box-wrap blind">
        <ul class="sort-select-box">
          <li condition="date" class="sort-select-list" @click="sort($event.target)">최신순</li>
          <li condition="rating" class="sort-select-list" @click="sort($event.target)">별점순</li>
          <li condition="cheap" class="sort-select-list" @click="sort($event.target)">가격 낮은순</li>
          <li condition="expensive" class="sort-select-list" @click="sort($event.target)">가격 높은순</li>
        </ul>
      </div>
    </div>

    <div class="listPage-wrap" @scroll="list_paging($event.target)">
      <input type="hidden" :maxCnt="this.maxCnt" :nowCnt="this.nowCnt" id="maxCnt" />
      <div class="list-box-wrap">
          <!-- START LIST BOX -->
          <div class="list-box" @click="go_space_detail_page($event.target)" v-for="obj in list" :key="obj" :idx="obj.backoffice_no" >
            <section>
              <img :src="obj.backoffice_image" alt="default-space-img" class="list-thumbnail" />
            </section>
            <section class="list-box-info">
              <ul>
                <li class="box-info-c-name">{{obj.company_name}}</li>
                <li class="box-location-wrap">
                  <img src="@/assets/IMG/office/location-icon.svg" alt="location-icon" class="location-icon">
                  <span class="box-info-location">{{obj.roadname_address}}</span>
                </li>
                <li class="box-tag-wrap">
                  <span class="box-tag">{{obj.backoffice_tag}}</span>
                </li>
                <li class="box-price-rating-wrap">
                  <div class="box-room-min-price-wrap">
                    <span class="box-room-min-price">최소 {{obj.min_room_price}}</span>
                    <span class="price-unit" v-if="obj.backoffice_type  === 'office'">원/개월</span>
                    <span class="price-unit" v-if="obj.backoffice_type  !== 'office'">원/시간</span>
                  </div>
                  <div class="box-room-rating">
                    <img src="@/assets/IMG/common/star.svg" alt="box-star" class="box-star" />&nbsp;
                    <span class="rating-num">{{obj.avg_rating}}</span>
                  </div>
                </li>
              </ul>
            </section>
          </div>
          <!-- END LIST BOX -->
      </div>
    </div>
  </section>

</template>

<style>
  @import '@/assets/CSS/office/list-page.scss';
</style>

<script>
import axios from 'axios';
import $ from 'jquery';

export default {
  name: 'ListView',
  data() {
    return {
      list: '',
      url: '',
      maxCnt: '',
      nowCnt: '',
      condition: '',
      scroll_flag: true,
    };
  },
  mounted() {
    // 로그인 여부 체크 -> 헤더를 위해
    axios.get('http://localhost:8800/loginCheck')
      .then((res) => {
        // 로그인 되어 있음
        if (res.data.result === '1') {
          this.$is_officeLogin = 'true';
        }
        // 로그인 되어 있지 않음(or 세션 만료)
        else {
          this.$is_officeLogin = 'false';
        }
      })
      .catch(() => {
        $('.popup-background:eq(1)').removeClass('blind');
        $('#common-alert-popup').removeClass('blind');
        $('.common-alert-txt').text('오류 발생으로 인해 로그인 여부를 불러오는데에 실패하였습니다.');
      });

    if (this.$route.params.call === 'list_page') {
      this.url = `http://localhost:8800/office/list_page?${this.$route.params.parameters}`;
    } else if (this.$route.params.call === 'search_list') {
      this.url = `http://localhost:8800/common/search_list?${this.$route.params.parameters}`;
    }

    axios.get(this.url)
      .then((res) => {
        console.log(res.data);
        this.list = res.data.list;
        this.maxCnt = res.data.maxCnt;
        this.nowCnt = res.data.nowCnt;
        this.condition = res.data.condition;

        if (this.list.length !== 0) {
          $('.list-page-wrap').addClass('blind');
          $('.listPage-section').removeClass('blind');
        } else if (this.list.length === 0) {
          $('.list-page-wrap').removeClass('blind');
          $('.listPage-section').addClass('blind');
        }
      })
      .catch(() => {
        $('.popup-background:eq(1)').removeClass('blind');
        $('#common-alert-popup').removeClass('blind');
        $('.common-alert-txt').text('오류 발생으로 인해 처리에 실패하였습니다.');
      });
  },
  methods: {
    // 정렬 셀렉트 클릭 시 커스텀 셀렉트 SHOW
    sort_select() {
      $('.sort-select-box-wrap').toggleClass('blind');
    },
    // 정렬 조건 클릭 시 데이터 요청
    sort(param) {
      let type = '';
      let location = '';
      let searchWord = '';

      $('.sort-select-box-wrap').addClass('blind');

      // 로딩 화면
      $('.popup-background:eq(1)').removeClass('blind');
      $('#spinner-section').removeClass('blind');

      type = this.$route.params.parameters.split('type=')[1].split('&')[0];
      const url = window.location.href.split('/list')[0];

      if (this.$route.params.call === 'list_page') {
        window.location.href = `${url}/list/list_page/type=${type}&condition=${$(param).attr('condition')}&page=1`;
      } else {
        location = this.$route.params.parameters.split('location=')[1].split('&')[0];
        searchWord = this.$route.params.parameters.split('searchWord=')[1].split('&')[0];
        window.location.href = `${url}/list/search_list/type=${type}&location=${location}&searchWord=${searchWord}&condition=${$(param).attr('condition')}&page=1`;
      }
    },
    list_paging(param) {
      if (Math.ceil($(param).scrollTop() + $(param).innerHeight()) >= $(param).prop('scrollHeight')) {
        if ($('.list-box').length < Number($('#maxCnt').attr('maxCnt')) && this.scroll_flag) {
          // 로딩 화면
          $('.popup-background:eq(1)').removeClass('blind');
          $('#spinner-section').removeClass('blind');

          if (this.$route.params.call.includes('list_page')) {
            const type = this.$route.params.parameters.split('type=')[1].split('&')[0];
            const condition = this.$route.params.parameters.split('condition=')[1].split('&')[0];
            const page = Number($('#maxCnt').attr('nowCnt'));
            const next = page + 1;

            const params = new URLSearchParams();
            params.append('type', type);
            params.append('condition', condition);
            params.append('page', page);

            this.scroll_flag = false;

            axios.get('http://localhost:8800/office/list_paging', {
              params: {
                type,
                condition,
                page: next,
              },
            })
              .then((res) => {
                this.scroll_flag = true;

                // 로딩 화면 닫기
                $('.popup-background:eq(1)').addClass('blind');
                $('#spinner-section').addClass('blind');

                const now = $('#maxCnt').attr('nowCnt');
                $('#maxCnt').attr('nowCnt', Number(now) + 1);

                this.list = this.list.concat(res.data.list);
              })
              .catch(() => {
                this.scroll_flag = true;

                // 로딩 화면 닫기
                $('.popup-background:eq(1)').addClass('blind');
                $('#spinner-section').addClass('blind');
              });
          } else {
            const type = this.$route.params.parameters.split('type=')[1].split('&')[0];
            const location = this.$route.params.parameters.split('location=')[1].split('&')[0];
            const searchWord = this.$route.params.parameters.split('searchWord=')[1].split('&')[0];
            const condition = this.$route.params.parameters.split('condition=')[1].split('&')[0];
            const page = Number($('#maxCnt').attr('nowCnt'));
            const next = page + 1;

            this.scroll_flag = false;

            axios.get('http://localhost:8800/office/search_list_paging', {
              params: {
                type,
                location,
                searchWord,
                condition,
                page: next,
              },
            })
              .then((res) => {
                this.scroll_flag = true;

                // 로딩 화면 닫기
                $('.popup-background:eq(1)').addClass('blind');
                $('#spinner-section').addClass('blind');

                const now = $('#maxCnt').attr('nowCnt');
                $('#maxCnt').attr('nowCnt', Number(now) + 1);

                this.list = this.list.concat(res.data.list);
              }).catch(() => {
                this.scroll_flag = true;

                // 로딩 화면 닫기
                $('.popup-background:eq(1)').addClass('blind');
                $('#spinner-section').addClass('blind');
              });
          }
        }
      }
    },
    go_space_detail_page(param) {
      const backofficeNo = $(param).parents('.list-box').attr('idx');
      const type = this.$route.params.parameters.split('type=')[1].split('&')[0];

      const url = window.location.href.split('/list')[0];

      if (type !== 'office') { window.open(`${url}/space/backoffice_no=${backofficeNo}`); }
      // 오피스용 공간 소개 페이지로 이동
      else { window.open(`${url}/space_office/backoffice_no=${backofficeNo}`); }
    },
  },
};
</script>
