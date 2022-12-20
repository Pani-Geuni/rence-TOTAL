<!-- eslint-disable max-len -->
<!-- eslint-disable vuejs-accessibility/label-has-for -->
<template>
  <div>
    <section class="space-detail-introduce-section">
      <div class="space-detail-introduce-wrap">
        <section class="space-detail-title-section">
          <span id="backoffice_no" class="space-detail-company-name blind">
            {{ vo.backoffice_no }}
          </span>
          <span class="space-detail-company-name">
            {{ vo.company_name }}
          </span>
          <span v-for="backoffice_type in backoffice_type" :key="backoffice_type" class="space-detail-type">
            {{ backoffice_type }}
          </span>
        </section>

        <!-- END space-detail-title-section -->

        <section class="space-detail-info-section">
          <div class="space-tags">
            <span v-for="tag in backoffice_tag" :key="tag" class="space-info-text tag-txt">
              {{ tag }}
            </span>
          </div>
        </section>

        <section class="space-imgs-section">
          <div class="window">
            <ul class="container">
              <li class="img" v-for="backoffice_img in backoffice_image" :key="backoffice_img">
                <img :src="backoffice_img" alt="" class="company-img" />
              </li>
            </ul>

            <div class="button-container">
              <span class="prev button-size">&lt;</span>
              <span class="next button-size">></span>
            </div>
          </div>
        </section>
        <!-- END space-imgs-section -->

        <section class="map-wrap ">
          <label class="map-title"> 상세 위치 지도 </label>
          <input type="hidden" id="location_addr" :value="vo.roadname_address" />
          <input type="hidden" id="location_name" :value="vo.company_name" />
          <div id="map"></div>
        </section>

        <section class="space-description-section">
          <h3 class="space-description-title">공간 소개</h3>
          <div class="space-description">
            {{ vo.backoffice_info }}
          </div>
        </section>
        <!-- END space-description-section -->

        <section class="space-options-section">
          <div class="space-option-wrap">
            <h5 class="space-option-title">옵션</h5>
            <ul class="option-list-wrap">
              <li class="option-list" v-for="option in backoffice_option" :key="option">
                {{ option }}
              </li>
            </ul>
          </div>
          <div class="space-option-wrap">
            <h5 class="space-option-title">주변 시설</h5>
            <ul class="option-list-wrap">
              <li class="option-list" v-for="around in backoffice_around" :key="around">
                {{ around }}
              </li>
            </ul>
          </div>
        </section>
        <!-- END space-options-section -->

        <section class="host-info-section">
          <div class="host-info-wrap">
            <div class="host-info-item">
              <h5 class="host-info-item-title">사업자 이름</h5>
              <span class="host-info-item-content">{{ vo.owner_name }}</span>
            </div>
            <div class="host-info-item">
              <h5 class="host-info-item-title">상호 명</h5>
              <span class="host-info-item-content">{{ vo.backoffice_name }}</span>
            </div>
            <div class="host-info-item">
              <h5 class="host-info-item-title">사업자 등록 번호</h5>
              <span class="host-info-item-content">{{ vo.backoffice_id }}</span>
            </div>
            <div class="host-info-item">
              <h5 class="host-info-item-title">사업체 전화 번호</h5>
              <span class="host-info-item-content">{{ vo.backoffice_tel }}</span>
            </div>
            <div class="host-info-item">
              <h5 class="host-info-item-title">사업체 명</h5>
              <span class="host-info-item-content">{{ vo.company_name }}</span>
            </div>
            <div class="host-info-item">
              <h5 class="host-info-item-title">사업체 이메일</h5>
              <span id="backoffice_email" class="host-info-item-content">
                {{ vo.backoffice_email }}
              </span>
            </div>
          </div>
          <!-- END host-info-wrap -->
          <div class="host-info-item">
            <h5 class="host-info-item-title-location">사업장 위치</h5>
            <span class="host-info-item-content-location">
              {{ vo.roadname_address }} {{ vo.detail_address }}
            </span>
          </div>
        </section>
        <!-- END host-info-section -->

        <section class="button-group-section">
          <div v-if="page == 'apply'" :idx="vo.backoffice_no" :backoffice_email="vo.backoffice_email">
            <input @click="clickGrantPopup" type="button" id="btn-grant-host" class="btn-item accept" value="승인" />
            <input @click="clickRefusePopup" type="button" id="btn-refuse-host" class="btn-item refuse" value="거절" />
          </div>
          <div v-if="page == 'delete'" :idx="vo.backoffice_no" :backoffice_email="vo.backoffice_email">
            <input @click="clickDeletePopup" type="button" id="btn-delete-host" class="btn-item delete" value="삭제" />
          </div>
        </section>
      </div>
      <!-- END space-detail-introduce-wrap -->
    </section>
    <!-- END space-detail-introduce-section -->

  </div>
</template>

<style scoped>
@import '@/assets/CSS/master/master-detail.scss';
</style>

<script>
import $ from 'jquery';
// import axios from 'axios';
import '@/assets/JS/master/master';
import axios from 'axios';

export default {
  name: 'ApplyDetail',
  data() {
    return {
      map: null,
      backoffice_no: this.$route.query.backoffice_no,
      page: this.$route.query.page,
      backoffice_type: [],
      backoffice_tag: [],
      backoffice_option: [],
      backoffice_around: [],
      backoffice_image: [],
      vo: {},
    };
  },

  methods: {
    initMap() {
      const mapContainer = document.getElementById('map');
      const mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        // 지도의 중심좌표
        level: 3, // 지도의 확대 레벨
      };
      this.map = new kakao.maps.Map(mapContainer, mapOption);

      const geocoder = new kakao.maps.services.Geocoder();
      const location = $('#location_addr').val();
      console.log('location :', location);
      // eslint-disable-next-line camelcase
      const location_name = $('#location_name').val();
      console.log('location_name :', location_name);

      // 주소로 좌표를 검색합니다
      geocoder.addressSearch(location, (result, status) => {
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
          const coords = new kakao.maps.LatLng(result[0].y, result[0].x);

          // 결과값으로 받은 위치를 마커로 표시합니다
          const marker = new kakao.maps.Marker({
            // eslint-disable-next-line no-undef
            map: this.map,
            position: coords,
          });

          // eslint-disable-next-line no-shadow
          const mapContainer = document.getElementById('map'); // 지도를 표시할 div
          // eslint-disable-next-line no-shadow
          const mapOption = {
            center: new kakao.maps.LatLng(result[0].y, result[0].x), // 지도의 중심좌표
            level: 3, // 지도의 확대 레벨
          };
          this.map = new kakao.maps.Map(mapContainer, mapOption);

          // 인포윈도우로 장소에 대한 설명을 표시합니다
          const infowindow = new kakao.maps.InfoWindow({
            // eslint-disable-next-line camelcase
            content: `<div style="width:150px;text-align:center;padding:6px 0;">${location_name}</div>`,
          });
          infowindow.open(this.map, marker);

          // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
          this.map.setCenter(coords);
        }
      });
    },

    getApplyDetailInfo() {
      console.log(this.backoffice_no);
      const url = `http://localhost:8800/master/backoffice_apply_detail?backoffice_no=${this.backoffice_no}&page=apply`;
      console.log(url);

      axios.get(url).then((res) => {
        console.log(res.data);
        this.backoffice_type = res.data.backoffice_type;
        this.backoffice_tag = res.data.backoffice_type;
        this.backoffice_option = res.data.backoffice_option;
        this.backoffice_around = res.data.backoffice_around;
        this.backoffice_image = res.data.backoffice_image;
        this.vo = res.data.vo;
      });
    },

    clickGrantPopup(e) {
      console.log(e.target.parentElement.getAttribute('idx'));
      console.log(e.target.parentElement.getAttribute('backoffice_email'));
      // const backoffice_no = e.target.parentElement.getAttribute('idx');
      $('.popup-background:eq(0)').removeClass('blind');
      $('#grant-popup').removeClass('blind');

      const grantBtn = $('#grant-popup').children('.confirm-btn-section').children('#grant-btn');

      grantBtn.attr('backoffice_no', e.target.parentElement.getAttribute('idx'));
      grantBtn.attr('backoffice_email', e.target.parentElement.getAttribute('backoffice_email'));
    },

    clickRefusePopup(e) {
      console.log(e.target.parentElement.getAttribute('idx'));
      console.log(e.target.parentElement.getAttribute('backoffice_email'));

      $('.popup-background:eq(0)').removeClass('blind');
      $('#refuse-popup').removeClass('blind');

      const refuseBtn = $('#refuse-popup').children('.confirm-btn-section').children('#refuse-btn');
      refuseBtn.attr('backoffice_no', e.target.parentElement.getAttribute('idx'));
      refuseBtn.attr('backoffice_email', e.target.parentElement.getAttribute('backoffice_email'));
    },

    clickDeletePopup(e) {
      console.log(e.target.parentElement.getAttribute('idx'));
      console.log(e.target.parentElement.getAttribute('backoffice_email'));

      $('.popup-background:eq(0)').removeClass('blind');
      $('#delete-popup').removeClass('blind');

      const deleteBtn = $('#delete-popup').children('.confirm-btn-section').children('#delete-btn');
      deleteBtn.attr('backoffice_no', e.target.parentElement.getAttribute('idx'));
      deleteBtn.attr('backoffice_email', e.target.parentElement.getAttribute('backoffice_email'));
    },
  },

  mounted() {
    if (!window.kakao || !window.kakap.map) {
      // Kakao Map
      const script = document.createElement('script');
      script.src = '//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=3b12e82dc4c8922a38cfd990bfa0afbd&libraries=services';

      /* global kakao */
      script.addEventListener('load', () => {
        console.log('loaded', kakao);
        kakao.maps.load(this.initMap);
      });

      document.head.appendChild(script);
    } else {
      console.log('already loading kakao map :', window.kakao);
      this.initMap();
    }

    this.$nextTick(() => {
      this.getApplyDetailInfo();
    });
  },
};

</script>

<!-- TODO : KAKAO MAP -->
