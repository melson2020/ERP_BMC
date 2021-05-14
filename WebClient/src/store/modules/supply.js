/* eslint-disable no-empty-pattern */
import request from "../../utils/request";
// import * as types from '../type'
import { Message } from "element-ui";

const state = {
    supplyList:[]
};

const actions = {
    GetSupplyList({ commit }) {
        request.ReqSupplyList().then(res => {
            if (res.resultStatus == 1) {
                commit("SetSupplyList", res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },
    DeleteSupply({ commit }, Supply) {
      request
          .ReqDeleteSupply(Supply)
          .then(res=>{
              if (res.resultStatus == 1) {
                  commit("SpliceSupplyList", Supply)
                  Message.info("删除成功")
              }
              else {
                  Message.info("删除失败:" + res.message);
              }
          })
          .catch(error => {
              let al = error.message ? error.message : error
              Message.error(al)
          })
    },
    SaveSupply({ },Supply){
        return request.ReqSaveSupply(Supply);
    },
    PushSupplyList({commit}, Supply){
        commit("PushSupplyList",Supply);
    },
    QuerySupplyObj({},Supply){
        return request.ReqQuerySupplyObj(Supply);
    },

};

const getters = {
    supplyList:state=>state.supplyList,
};

const mutations = {
    SetSupplyList(state, data) {
        state.supplyList = data;
    },

    SpliceSupplyList(state, data) {
        state.supplyList.splice(data.index, 1);
    },
    PushSupplyList(state,data){
        state.supplyList.push(data);
    },
};

export default {
    state,
    actions,
    getters,
    mutations
};
