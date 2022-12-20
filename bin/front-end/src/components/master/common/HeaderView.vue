<!-- eslint-disable vuejs-accessibility/click-events-have-key-events -->
<!-- eslint-disable max-len -->
<template>
  <section class="headerWrap">
    <div class="logo-section master">
      <img @click="goMasterMain" src="@/assets/IMG/common/Rence-Master.svg" alt="Master Logo" class="logo-mku master">
    </div>

    <ul class="header-nav-list">
      <li id="nav-item-agree" class="header-nav-item" :class="{ active: ApplyIsActive }" @click="goApplyList">업체 신청 업무
      </li>
      <li id="nav-item-delete" class="header-nav-item" :class="{ active: DeleteIsActive }" @click="goDeleteList">업체 탈퇴
        업무</li>
      <li class="header-nav-item">
        <button id="btn-logout" class="btn-logout" @click="goLogout">로그아웃</button>
      </li>
    </ul>
  </section>

</template>

<style lang="scss" scoped>
@import '@/assets/CSS/master/master-header.scss';
</style>

<script>
import axios from 'axios';

console.log('http://localhost:8081/master/login');

export default {
  name: 'HeaderView',
  data() {
    return {
      ApplyIsActive: false,
      DeleteIsActive: false,
    };
  },
  mounted() {
    // master header nav 변경
    const url = window.location.pathname;

    switch (url) {
      case '/master/main':
      case '/master/backoffice_apply_detail':
        this.ApplyIsActive = true;
        break;

      case '/master/backoffice_end':
      case '/master/backoffice_end_detail':
        this.DeleteIsActive = true;
        break;

      default:
        break;
    }
  },
  methods: {
    goMasterMain() {
      this.$router.replace('/master/main');
      this.ApplyIsActive = true;
      this.DeleteIsActive = false;
    },

    goApplyList() {
      this.$router.replace('/master/main');
      this.ApplyIsActive = true;
      this.DeleteIsActive = false;
    },

    goDeleteList() {
      this.$router.replace('/master/backoffice_end');
      this.DeleteIsActive = true;
      this.ApplyIsActive = false;
    },

    goLogout() {
      axios.post('http://localhost:8800/master/logoutOK')
        .then((res) => {
          console.log(res.data);
          if (res.data.result === '1') {
            window.location.href = '/master/login';
          }
        }).catch((e) => {
          console.log(e);
        });
    },
  },
};

</script>
