/* eslint-disable no-empty-pattern */
import request from "../../utils/request";
// import * as types from '../type'
import { Message } from "element-ui";

const state = {
    materialList:[],
};

const actions = {
    SaveMaterial({ },Storage){
        return request.ReqSaveMaterial(Storage);
    },
    GetMaterialList({ commit }) {
        request.ReqMaterialList().then(res => {
            if (res.resultStatus == 1) {
                commit("SetMaterialList", res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },
    DeleteMaterial({ commit }, Material) {
      request
          .ReqDeleteMaterial(Material)
          .then(res=>{
              if (res.resultStatus == 1) {
                  commit("SpliceMaterialList", Material)
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
    PushMaterialList({commit}, Material){
        commit("PushMaterialList",Material);
    },
    QueryMaterialObj({},Material){
        return request.ReqQueryMaterialObj(Material);
    },

};

const getters = {
    materialList:state=>state.materialList,
};

const mutations = {
    SetMaterialList(state, data) {
        state.materialList = data;
    },
    SpliceMaterialList(state, data) {
        state.materialList.splice(data.index, 1);
    },
    PushMaterialList(state,data){
        state.materialList.push(data);
    },
};

export default {
    state,
    actions,
    getters,
    mutations
};
