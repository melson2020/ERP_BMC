/* eslint-disable no-empty-pattern */
import request from "../../utils/request";
import { Message } from "element-ui";
import * as types from '../type'

const state = {
    produceLineStateList:[]
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
    }
};

const getters = {
    produceLineStateList:state=>state.produceLineStateList,
};

const mutations = {
  [types.PRODUCE_LINE_STATE_LIST](state,data){
    state.produceLineStateList=data;
  }
};

export default {
    state,
    actions,
    getters,
    mutations
};
