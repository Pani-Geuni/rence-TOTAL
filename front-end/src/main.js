import { createApp } from 'vue';
import VueCookies from 'vue-cookies';
import Vue3Storage from 'vue3-storage';
import Datepicker from '@vuepic/vue-datepicker';
import VCalendar from 'v-calendar';

import App from './App.vue';
import router from './router';
import store from './store';

const app = createApp(App);
app.config.globalProperties.$is_officeLogin = 'false';
app.use(store).use(router).use(VueCookies)
  .use(Vue3Storage)
  .use(VCalendar, {})
  .component('Datepicker', Datepicker)
  .mount('#app');
