/* eslint-disable no-empty-pattern */
import request from "../../utils/request";
// import * as types from '../type'
import { Message } from "element-ui";

const state = {
    storageList:[],
};

const actions = {
    GetStorageList({ commit }) {
        request.ReqStorageList().then(res => {
            if (res.resultStatus == 1) {
                commit("SetStorageList", res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },
    DeleteStorage({ commit }, Storage) {
      request
          .ReqDeleteStorage(Storage)
          .then(res=>{
              if (res.resultStatus == 1) {
                  commit("SpliceStorageList", Storage)
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
    SaveStorage({ },Storage){
        return request.ReqSaveStorage(Storage);
    },
    PushStorageList({commit}, Storage){
        commit("PushStorageList",Storage);
    },
    QueryStorageObj({},Storage){
        return request.ReqQueryStorageObj(Storage);
    },

};

const getters = {
    storageList:state=>state.storageList,
};

const mutations = {
    SetStorageList(state, data) {
        state.storageList = data;
    },

    SpliceStorageList(state, data) {
        state.storageList.splice(data.index, 1);
    },
    PushStorageList(state,data){
        state.storageList.push(data);
    },
};

export default {
    state,
    actions,
    getters,
    mutations
};
