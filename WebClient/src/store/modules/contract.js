/* eslint-disable no-empty-pattern */
import request from "../../utils/request";
// import { Message } from "element-ui";
import * as types from '../type'
import { Message } from "element-ui";

const state = {
    contractTemplate:{}
};

const actions = {
    GetContractTemplate({commit}){
        request.GetContractTemplate().then(res=>{
            if (res.resultStatus == 1) {
                commit(types.CONTRACT_TEMPLATE, res.data)
            } else {
                Message.warning(res.messgae)
            }
        }).catch(err => {
            Message.error(err.messgae)
        })
    }
};

const getters = {
    contractTemplate: state => state.contractTemplate,
};

const mutations = {
    [types.CONTRACT_TEMPLATE](state, data) {
        state.contractTemplate = data;
    },
};

export default {
    state,
    actions,
    getters,
    mutations
};
