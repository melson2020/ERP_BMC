/* eslint-disable no-empty-pattern */
import request from "../../utils/request";
// import { Message } from "element-ui";
import * as types from '../type'
import { Message } from "element-ui";

const state = {
    contractTemplate:{},
    customerVoList:[],
    intentionContractList:[]
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
                Message.success("保存成功")
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },
    GetCustomerVoList({commit}){
        request.GetCustomerVoList().then(res=>{
            if (res.resultStatus == 1) {
                commit(types.CUSTOMER_VO_LIST, res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },
    GetContractVoByCustomerNo({},param){
       return request.GetContractVoByCustomerNo(param)
    },
    SearchContractIntentionProductList({},param){
        return request.GetContractIntentionProductin(param)
    },
    GetIntentionContractList({commit}){
        request.GetIntentionContractList().then(res=>{
            if(res.resultStatus==1){
               commit(types.CONTRACT_INTENTION_LIST,res.data)
            }else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    }
};

const getters = {
    contractTemplate: state => state.contractTemplate,
    customerVoList:state=>state.customerVoList,
    intentionContractList:state=>state.intentionContractList
};

const mutations = {
    [types.CONTRACT_TEMPLATE](state, data) {
        state.contractTemplate = data;
    },
    [types.CUSTOMER_VO_LIST](state, data) {
        state.customerVoList = data;
    },
    [types.CONTRACT_INTENTION_LIST](state,data){
        state.intentionContractList = data;
    }
};

export default {
    state,
    actions,
    getters,
    mutations
};
