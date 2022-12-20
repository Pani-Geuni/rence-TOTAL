<!-- eslint-disable max-len -->
<template>
  <div class="titleSection">
    <h1>환경 설정</h1>
  </div>
  <!-- END titleSection -->

  <div class="boardWrap settings">
    <div class="titleSection settings">
      <h1>호스트 정보</h1>
      <button @click="clickUpdatePwBtn" id="btn-update-pw" class="btn-update">비밀번호 변경</button>
      <button @click="clickUpdateHost" id="btn-update-host" class="btn-update">정보 변경</button>
    </div>
    <!-- END titleSection settings -->

    <div class="host-info-list">
      <div class="host-item">
        <p>상호명</p>
        <span>{{ vo.backoffice_name }}</span>
      </div>
      <div class="host-item">
        <p>대표</p>
        <span>{{ vo.owner_name }}</span>
      </div>
      <div class="host-item">
        <p>사업자 등록 번호</p>
        <span>{{ vo.backoffice_id }}</span>
      </div>
      <div class="host-item">
        <p>위치</p>
        <span>{{ vo.roadname_address }} {{ vo.number_address }} {{ vo.detail_address }}</span>
      </div>
      <div class="host-item">
        <p>전화번호</p>
        <span>{{ vo.backoffice_tel }}</span>
      </div>
      <div class="host-item">
        <p>이메일</p>
        <span>{{ vo.backoffice_email }}</span>
      </div>
    </div>
    <!-- END host-info-list -->

    <div class="host-info-list">
      <div class="host-item">
        <p>사업체 태그</p>
        <ul class="host-option-list">
          <li class="host-option-item" v-for="tag in backoffice_tag" :key="tag">{{ tag }}</li>
        </ul>
      </div>

      <div class="host-item">
        <p>공간 옵션</p>
        <ul class="host-option-list">
          <li class="host-option-item" v-for="option in backoffice_option" :key="option">{{ option }}</li>
        </ul>
      </div>
      <div class="host-item">
        <p>주변 시설</p>
        <ul class="host-option-list">
          <li class="host-option-item" v-for="around in backoffice_around" :key="around">{{ around }}</li>
        </ul>
      </div>
      <div class="host-item">
        <p>사업체 소개</p>
        <pre>{{ vo.backoffice_info }}</pre>
      </div>
    </div>
  </div>
  <!-- END boardWrap -->
  <div class="btn-group-settings">
    <button @click="clickDeleteHost" id="btn-host-delete" class="btn-host-delete">업체 삭제 요청</button>
    <button @click="clickLogoutHost" id="btn-host-logout" class="btn-host-delete">로그아웃</button>
  </div>
  <!-- END btn-group-settings -->
</template>

<style lang="scss" scoped>
@import '@/assets/CSS/dash-board/dash-settings.scss';
</style>

<script>
import $ from 'jquery';
import axios from 'axios';

export default {
  name: 'SettingsView',

  data() {
    return {
      backoffice_no: this.$cookies.get('backoffice_no'),
      vo: [],
      backoffice_tag: [],
      backoffice_option: [],
      backoffice_around: [],
    };
  },

  methods: {
    getBackofficeInfo() {
      const params = new URLSearchParams();
      params.append('backoffice_no', this.backoffice_no);

      const url = `http://localhost:8800/backoffice/dash/settings?${params}`;
      axios.get(url).then((res) => {
        console.log(res.data);
        this.vo = res.data.vo;
        this.backoffice_tag = res.data.backoffice_tag;
        this.backoffice_option = res.data.backoffice_option;
        this.backoffice_around = res.data.backoffice_around;
      });
    },

    clickUpdateHost() {
      this.$router.push(`/backoffice/dash/update_host?backoffice_no=${this.backoffice_no}`);
    },

    clickUpdatePwBtn() {
      $('#popup-update-pw').removeClass('blind');
      $('.popup-background:eq(0)').removeClass('blind');
    },

    clickDeleteHost() {
      $('#host-delete-popup').removeClass('blind');
      $('.popup-background:eq(0)').removeClass('blind');
    },

    clickLogoutHost() {
      $('.popup-background:eq(0)').removeClass('blind');
      $('#logout-popup').removeClass('blind');
    },
  },

  mounted() {
    this.$nextTick(() => {
      this.getBackofficeInfo();
    });
  },
};
</script>
