/* eslint-disable no-empty-pattern */
import request from "../../utils/request";
import { Message } from "element-ui";
import * as types from '../type'

const state = {
    delegateReleaseList: []
};

const actions = {
   FindDelegateReleaseList({commit}){
       request.GetDelegateReleaseList().then(res => {
        if (res.resultStatus == 1) {
            console.log(res.data)
            commit(types.DELEGATE_RELEASE_LIST, res.data)
        } else {
            Message.warning(res.message)
        }
    }).catch(err => {
        Message.error(err.message)
    })
   }
};

const getters = {
    delegateReleaseList: state => state.delegateReleaseList,
};

const mutations = {
    [types.DELEGATE_RELEASE_LIST](state, data) {
        state.delegateReleaseList = data;
    },
};

export default {
    state,
    actions,
    getters,
    mutations
};
