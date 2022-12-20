<template>
  <div class="bodyWrap">
    <SidebarView />
    <div class="board-contentWrap">
      <router-view></router-view>
    </div>
  </div>
  <PopupView />
</template>

<style scoped>
@import '@/assets/CSS/common/common.scss';
@import '@/assets/CSS/dash-board/dash-commons.scss';
@import '@/assets/CSS/dash-board/dash-board.scss';
</style>

<script>
import SidebarView from '@/components/backoffice/common/SidebarView.vue';
import PopupView from '@/components/backoffice/dashboard/common/PopupView.vue';

export default {
  name: 'LayoutDashboard',
  components: {
    SidebarView,
    PopupView,
  },
};

</script>
