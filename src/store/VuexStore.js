import Vuex from 'vuex';

const store = new Vuex.Store({
    state: {
      isAuth: false,
      token: '',
      role: '' ,
      companyEmail: '',
    },
    mutations: {
      setIsAuth(state, value) {
        state.isAuth = value;
      },
      setIsToken(state,value){
          state.token=value;
      },
      setIsRole(state,value){
        state.role=value;
      },
      setIsCompanyEmail(state,value){
        state.companyEmail=value;
      }
    },
    actions: {
        setAuth({ commit }, value) {
          commit('setIsAuth', value);
        },
        setToken({commit}, value){
            commit('setIsToken',value);
        },
        setRole({commit}, value){
          commit('setIsRole',value);
        },
        setCompanyEmail({commit}, value){
          commit('setIsCompanyEmail',value);
        } 
      }
    });
    
    export default store; 