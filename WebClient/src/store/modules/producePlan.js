/* eslint-disable no-empty-pattern */
import request from "../../utils/request";
import { Message } from "element-ui";
import * as types from '../type'

const state = {
    producePlanList: [],
    producePlanRecordList:[]
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
    },
    FindProducePlanRecord({commit},param){
        request.FindProducePlanRecord(param).then(res => {
            if (res.resultStatus == 1) {
                commit(types.PRODUCE_PLAN_RECORD_LIST, res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },
    FindPlanDetailList({},param){
        return request.FindProducePlanDetailList(param)
    },
    FindPlanDetailProcessList({},param){
        return request.FindProducePlanDetaiProcessList(param)
    },
    FindWorkStationListByProcessId({},param){
        return request.FindWorkStationListByProcessId(param)
    },
    SaveProducePlanWorkStation({},param){
        return request.SaveProducePlanInfo(param)
    },
    DeletePlanWorkStation({},param){
        return request.DeletePlanWorkStation(param)
    }

};

const getters = {
    producePlanList: state => state.producePlanList,
    producePlanRecordList:state=>state.producePlanRecordList
};

const mutations = {
    [types.PRODUCE_PLAN_LIST](state, data) {
        state.producePlanList = data
    },
    [types.PRODUCE_PLAN_RECORD_LIST](state, data) {
        state.producePlanRecordList = data
    }
};

export default {
    state,
    actions,
    getters,
    mutations
};
