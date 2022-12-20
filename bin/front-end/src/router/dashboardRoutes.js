import LayoutDashboard from '@/views/LayoutDashboard.vue';
import DashBoardMainView from '@/components/backoffice/dashboard/MainView.vue';
import RoomView from '@/components/backoffice/dashboard/RoomView.vue';
import QnaView from '@/components/backoffice/dashboard/QnaView.vue';
import ReviewView from '@/components/backoffice/dashboard/ReviewView.vue';
import ScheduleView from '@/components/backoffice/dashboard/ScheduleView.vue';
import ReserveView from '@/components/backoffice/dashboard/ReserveView.vue';
import SalesView from '@/components/backoffice/dashboard/SalesView.vue';
import SettingsView from '@/components/backoffice/dashboard/SettingsView.vue';
import UpdateHostView from '@/components/backoffice/dashboard/UpdateHostView.vue';

export default [
  {
    path: '/backoffice/dash',
    component: LayoutDashboard,
    children: [
      {
        path: 'main',
        name: 'DashBoardMainView',
        component: DashBoardMainView,
      },
      {
        path: 'room',
        name: 'RoomView',
        component: RoomView,
      },
      {
        path: 'qna',
        name: 'QnaView',
        component: QnaView,
      },
      {
        path: 'review',
        name: 'ReviewView',
        component: ReviewView,
      },
      {
        path: 'schedule',
        name: 'ScheduleView',
        component: ScheduleView,
      },
      {
        path: 'reserve',
        name: 'ReserveView',
        component: ReserveView,
      },
      {
        path: 'day_sales',
        name: 'SalesView',
        component: SalesView,
      },
      {
        path: 'settings',
        name: 'SettingsView',
        component: SettingsView,
      },
      {
        path: 'update_host',
        name: 'UpdateHostView',
        component: UpdateHostView,
      },
    ],
  },
];
