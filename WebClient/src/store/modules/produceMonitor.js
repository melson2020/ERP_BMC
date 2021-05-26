/* eslint-disable no-empty-pattern */
import request from "../../utils/request";
import { Message } from "element-ui";
import * as types from '../type'

const state = {
    produceLineStateList:[],
    countSummary:{},
    processingPlanList:[],
};

const actions = {
    GetProduceLineStateInfo({commit}){
        request.GetProduceLineStateInfo().then(res=>{
            if (res.resultStatus == 1) {
                commit(types.PRODUCE_LINE_STATE_LIST, res.data)
            } else {
                Message.warning(res.message)
            }
             
        }).catch(err=>{
            Message.error(err.message)
        })
    },
    GetPlanCountSummary({commit}){
        request.GetMonitorCountSummary().then(res=>{
            if (res.resultStatus == 1) {
                commit(types.PRODUCE_PLAN_COUNT_SUMMARY, res.data)
            } else {
                Message.warning(res.message)
            }
             
        }).catch(err=>{
            Message.error(err.message)
        })
    },
    GetProcessingPlanList({commit}){
        request.GetProcessPlanList().then(res=>{
            if (res.resultStatus == 1) {
                commit(types.PROCESSING_PLAN__LIST, res.data)
            } else {
                Message.warning(res.message)
            }
             
        }).catch(err=>{
            Message.error(err.message)
        })
    },
    LoadPlanInfo({},param){
        return request.GetPlanInfo(param);
    }
};

const getters = {
    produceLineStateList:state=>state.produceLineStateList,
    countSummary:state=>state.countSummary,
    processingPlanList:state=>state.processingPlanList
};

const mutations = {
  [types.PRODUCE_LINE_STATE_LIST](state,data){
    state.produceLineStateList=data;
  },
  [types.PRODUCE_PLAN_COUNT_SUMMARY](state,data){
    state.countSummary=data
  },
  [types.PROCESSING_PLAN__LIST](state,data){
    state.processingPlanList=data
  }
};

export default {
    state,
    actions,
    getters,
    mutations
};
