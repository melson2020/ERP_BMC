/* eslint-disable no-empty-pattern */
import request from "../../utils/request";
// import * as types from '../type'
import { Message } from "element-ui";

const state = {
    productBomList:[],
};

const actions = {
    SaveProductBom({ },productBom){
        return request.ReqSaveProductBom(productBom);
    },
    UpdateProductBom({  }, productBom) {
        return request.ReqUpdateProductBom(productBom)
    },
    GetProductBomList({ commit }) {
        request.ReqProductBomList().then(res => {
            if (res.resultStatus == 1) {
                commit("SetProductBomList", res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },

    PushProductBomList({commit}, ProductBom){
        commit("PushProductBomList",ProductBom);
    },
    QueryProductBomObj({},ProductBom){
        return request.ReqQueryProductBomObj(ProductBom);
    },
    SaveDetailBoms({ },Bom){
        request
          .ReqSaveDetailBoms(Bom)
          .then(res => {
            if (res.resultStatus == 1) {
              Message.warning("保存成功")
            } else {
              Message.warning("保存失败")
            }
          })
          .catch(err => {
            let alert = err.message ? err.message : err;
            this.$messgae.error(alert);
          });
      },

    DeleteDetailBoms({ }, Bom) {
        request
          .ReqDeleteDetailBoms(Bom)
          .then(res=>{
              if (res.resultStatus == 1) {
                  Message.warning("删除成功")
              }
              else {
                  Message.warning("删除失败");
              }
          })
          .catch(error => {
              let al = error.message ? error.message : error
              Message.error(al)
          })
      },

};

const getters = {
    productBomList:state=>state.productBomList,
};

const mutations = {
    SetProductBomList(state, data) {
        state.productBomList = data;
    },
    SpliceProductBomList(state, data) {
        state.productBomList.splice(data.index, 1);
    },
    PushProductBomList(state,data){
        state.productBomList.push(data);
    },
};

export default {
    state,
    actions,
    getters,
    mutations
};
