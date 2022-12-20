<template>
  <router-view></router-view>
</template>

<style lang="scss">
#app {
  width: 100%;
}

a {
  color: black;

  &.router-link-exact-active {
    color: black;
  }
}
</style>

<script>
export default {
  name: 'App',
};
</script>

<style>
  @import './assets/CSS/common/font.css';
</style>
