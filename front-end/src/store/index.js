import { createStore } from 'vuex';

export default createStore({
  state: {
    is_officeLogin: 'false',
    is_backofficeLogin: 'false',
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

    backoffice_setLogin_false(state) {
      state.is_backofficeLogin = 'false';
    },
    backoffice_setLogin_true(state) {
      state.is_backofficeLogin = 'true';
    },
  },
  actions: {
  },
  modules: {
  },
});
