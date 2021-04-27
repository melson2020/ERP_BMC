/* eslint-disable no-empty-pattern */
import request from "../../utils/request";
// import { Message } from "element-ui";
import * as types from '../type'
import { Message } from "element-ui";

const state = {
    contractTemplate:{},
    customerList:[]
};

const actions = {
    GetContractTemplate({commit}){
        request.GetContractTemplate().then(res=>{
            if (res.resultStatus == 1) {
                commit(types.CONTRACT_TEMPLATE, res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },
    SaveIntentionContract({commit},param){
        request.SaveIntentionContract(param).then(res=>{
            if (res.resultStatus == 1) {
                console.log('保存成功')
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },
    GetCustomerList({commit}){
        request.GetCustomerList().then(res=>{
            if (res.resultStatus == 1) {
                commit(types.CUSTOMER_LIST, res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    }
};

const getters = {
    contractTemplate: state => state.contractTemplate,
    customerList:state=>state.customerList
};

const mutations = {
    [types.CONTRACT_TEMPLATE](state, data) {
        state.contractTemplate = data;
    },
    [types.CUSTOMER_LIST](state, data) {
        state.customerList = data;
    },
};

export default {
    state,
    actions,
    getters,
    mutations
};
