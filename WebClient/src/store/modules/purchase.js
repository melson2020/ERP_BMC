/* eslint-disable no-empty-pattern */
import request from "../../utils/request";
import * as types from '../type'
import { Message } from "element-ui";

const state = {
    purchaseList: [],
};

const actions = {
    GetPurchaseList({ commit }, params) {
      request.ReqPurchaseList(params).then(res => {
          if (res.resultStatus == 1) {
              commit(types.PURCHASE_DETAIL_LIST, res.data)
          } else {
              Message.warning(res.message)
          }
      }).catch(err => {
          Message.error(err.message)
      })
    },
    SavePurchase({ },purchase){
    return request.ReqSavePurchase(purchase);
    },
    QueryPurchaseObj({},purchase){
      return request.ReqQueryPurchaseObj(purchase);
    },
    PushPurchaseList({commit}, purchase){
      commit(types.PURCHASE_PUSH_LIST,purchase);
    },
    UpdatePurchaseStatus({}, purchase) {
      request.ReqUpdatePurchaseStatus(purchase).then(res => {
        if (res.resultStatus == 1) {
          Message.info("操作成功")
        } else {
          Message.warning("操作失败:" + res.message)
        }
      }).catch(error => {
        let al = error.message ? error.message : error
        Message.error(al)
      })
    },
    

};

const getters = {
    purchaseList: state => state.purchaseList,
};

const mutations = {
    [types.PURCHASE_DETAIL_LIST](state, data) {
      state.purchaseList=data;
    },
    [types.PURCHASE_PUSH_LIST](state,data){
      state.purchaseList.push(data);
    },
};

export default {
    state,
    actions,
    getters,
    mutations
};
