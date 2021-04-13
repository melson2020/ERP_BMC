/* eslint-disable no-empty-pattern */
import request from "../../utils/request";
// import { Message } from "element-ui";
import * as types from '../type'

const state = {
    userInfo: {}
};

const actions = {
    SystemLogin({}, loginUser) {
      return request.SystemLogin(loginUser)
    },
    SetLoginUserInfo({commit},user){
        commit(types.LOGIN_USERINFO, user)
    }
};

const getters = {
    userInfo: state => state.userInfo,
};

const mutations = {
    [types.LOGIN_USERINFO](state, data) {
        state.userInfo = data;
    },
};

export default {
    state,
    actions,
    getters,
    mutations
};
