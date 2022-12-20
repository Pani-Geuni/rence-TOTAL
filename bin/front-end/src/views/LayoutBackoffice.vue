<!-- eslint-disable vuejs-accessibility/form-control-has-label -->
<!-- eslint-disable max-len -->
<template>
  <div class="pageWrap">
    <HeaderView />
    <router-view></router-view>
    <FooterView />
  </div>
  <PopupView />

</template>

<style scoped>
@import '@/assets/CSS/common/common.scss';
@import '@/assets/CSS/backoffice/host-header.scss';
@import '@/assets/CSS/backoffice/host-login.scss';
</style>

<script>
import HeaderView from '@/components/backoffice/common/HeaderView.vue';
import FooterView from '@/components/office/common/FooterView.vue';
import PopupView from '@/components/backoffice/common/PopupView.vue';

export default {
  name: 'LayoutBackoffice',
  components: {
    HeaderView,
    FooterView,
    PopupView,
  },
};
</script>
