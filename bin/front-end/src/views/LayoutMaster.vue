<!-- eslint-disable vuejs-accessibility/click-events-have-key-events -->
<template>
  <div class="pageWrap">
    <HeaderView />
    <router-view></router-view>
    <!-- <FooterView /> -->
  </div>
  <PopupView />

</template>

<style scoped>
@import '@/assets/CSS/common/common.scss';
@import '@/assets/CSS/common/custom-table.css';
</style>

<script>
// import $ from 'jquery';
import HeaderView from /* webpackChunkName: "HeaderView" */ '@/components/master/common/HeaderView.vue';
// import FooterView from '@/components/office/common/FooterView.vue';
import PopupView from '@/components/master/common/PopupView.vue';

export default {
  name: 'LayoutMaster',
  components: {
    HeaderView,
    // FooterView,
    PopupView,
  },
  methods: {
  },
};
</script>
