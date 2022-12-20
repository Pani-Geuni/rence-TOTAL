<!-- eslint-disable vuejs-accessibility/form-control-has-label -->
<!-- eslint-disable no-tabs -->

<!--
 - @author 김예은
 - @refactoring 김예은
-->

<template>
  <div class="pageWrap">
    <HeaderView />
    <router-view></router-view>
    <FooterView />
  </div>
  <PopupView />
</template>

<style>
.mx-datepicker {
	width: 359px;
	height: 40px;

  box-sizing: border-box;
  border: 2px solid #2EE49D;

	padding: 3px 10px 3px 10px;
	margin-top: 12px;
	display: flex;
	align-items: center;
	border-radius: 8px;
	cursor: pointer;
}

.mx-input {
  display: inline-block;
  box-sizing: border-box;
  width: 339px;
  height: 34px;
  padding: 6px 30px;
  padding-left: 10px;
  font-size: 14px;
  color: #555;
  background-color: #fff;
  border: 0px;
  box-shadow: none;
}

.mx-icon-calendar{
  color:#2EE49D;
}

.mx-datepicker svg {
    width: 1.4em;
    height: 1.4em;
}
</style>

<script>
import HeaderView from '@/components/office/common/HeaderView.vue';
import FooterView from '@/components/office/common/FooterView.vue';
import PopupView from '@/components/office/common/PopupView.vue';

export default {
  name: 'LayoutOffice',
  components: {
    HeaderView,
    FooterView,
    PopupView,
  },
};
</script>
