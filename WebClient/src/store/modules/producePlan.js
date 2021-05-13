/* eslint-disable no-empty-pattern */
import request from "../../utils/request";
import { Message } from "element-ui";
import * as types from '../type'

const state = {
    producePlanList: []
};

const actions = {
    FindUnConfirmPlanList({ commit }, param) {
        request.FindProducePlanList(param).then(res => {
            if (res.resultStatus == 1) {
                commit(types.PRODUCE_PLAN_LIST, res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    }

};

const getters = {
    producePlanList: state => state.producePlanList
};

const mutations = {
    [types.PRODUCE_PLAN_LIST](state, data) {
        state.producePlanList = data
    }
};

export default {
    state,
    actions,
    getters,
    mutations
};
