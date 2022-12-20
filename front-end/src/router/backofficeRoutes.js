import LayoutBackoffice from '@/views/LayoutBackoffice.vue';
import LandingView from '@/components/backoffice/landing/LandingView.vue';
import InsertView from '@/components/backoffice/landing/InsertView.vue';
import SettingsPwView from '@/components/backoffice/landing/SettingsPwView.vue';

export default [
  {
    path: '/backoffice',
    component: LayoutBackoffice,
    children: [
      {
        path: 'landing',
        name: 'LandingView',
        component: LandingView,
      },
      {
        path: 'insert',
        name: 'InsertView',
        component: InsertView,
      },
      {
        path: 'setting_pw',
        name: 'SettingsPwView',
        component: SettingsPwView,
      },
    ],
  },
];
