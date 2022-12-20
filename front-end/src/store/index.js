import { createStore } from 'vuex';

export default createStore({
  state: {
    is_officeLogin: 'false',
  },
  getters: {
  },
  mutations: {
    office_setLogin_false(state) {
      state.is_officeLogin = 'false';
    },
    office_setLogin_true(state) {
      state.is_officeLogin = 'true';
    },
  },
  actions: {
  },
  modules: {
  },
});
