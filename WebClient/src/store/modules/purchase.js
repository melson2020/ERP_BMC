/* eslint-disable no-empty-pattern */
import request from "../../utils/request";
import * as types from '../type'
import { Message } from "element-ui";

const state = {
    purchaseList: [],
    userVos:[],
    productVos:[],
    purchaseWaitList:[],
    prList:[],
    poList:[],
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
    DeletePurchase({ commit }, purchase) {
      request
          .ReqDeletePurchase(purchase)
          .then(res => {
              if (res.resultStatus == 1) {
                  commit(types.PURCHASE_DELETE_ITEM, purchase)
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

    GetEmployeeVos({commit}, params) {
      request.ReqEmployeeVoList(params).then(res => {
          if (res.resultStatus == 1) {
              commit(types.USER_VO_LIST, res.data)
          } else {
              Message.warning(res.message)
          }
      }).catch(err => {
          Message.error(err.message)
      })
    },
    QueryProductVos({commit}, productVo) {
      request.ReqQueryProductVos(productVo).then(res => {
          if (res.resultStatus == 1) {
              commit(types.PRODUCT_VO_LIST, res.data)
          } else {
              Message.warning(res.message)
          }
      }).catch(err => {
          Message.error(err.message)
      })
    },

    GetPurchaseWaitList({ commit }, params) {
      request.ReqWaitPurchaseList(params).then(res => {
          if (res.resultStatus == 1) {
              commit(types.PURCHASE_WAIT_LIST, res.data)
          } else {
              Message.warning(res.message)
          }
      }).catch(err => {
          Message.error(err.message)
      })
    },
    ApprovePurchaseObj({ },purchase){
      return request.ReqApprovePurchaseObj(purchase);
    },
    RejectPurchaseWait({ commit }, purchase) {
      request
      .ReqRejectPurchaseWait(purchase)
      .then(res => {
          if (res.resultStatus == 1) {
              commit(types.PURCHASE_REJECT_ITEM, purchase)
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
    GetPRList({ commit }, params) {
      request.ReqPRList(params).then(res => {
          if (res.resultStatus == 1) {
              commit(types.PO_WAIT_LIST, res.data)
          } else {
              Message.warning(res.message)
          }
      }).catch(err => {
          Message.error(err.message)
      })
    },
    GetPOList({ commit }, params) {
      request.ReqPOList(params).then(res => {
          if (res.resultStatus == 1) {
              commit(types.PO_CREATE_LIST, res.data)
          } else {
              Message.warning(res.message)
          }
      }).catch(err => {
          Message.error(err.message)
      })
    },
    SavePoList({ },po){
      return request.ReqSavePoList(po);
    },

};

const getters = {
    purchaseList: state => state.purchaseList,
    userVos:state=>state.userVos,
    productVos:state=>state.productVos,
    purchaseWaitList:state=>state.purchaseWaitList,
    prList:state=>state.prList,
    poList:state=>state.poList,
};

const mutations = {
    [types.PURCHASE_DETAIL_LIST](state, data) {
      state.purchaseList=data;
    },
    [types.PURCHASE_PUSH_LIST](state,data){
      state.purchaseList.push(data);
    },
    [types.USER_VO_LIST](state, data) {
      state.userVos=data;
    },
    [types.PRODUCT_VO_LIST](state, data) {
      var productList = data;
      var options = [];
      productList.map((group) => {
        var subList = group.list;
        if (subList.length > 0) {
          options.push({ groupName: group.groupName, list: subList });
        }
      });
      state.productVos = options;
    },
    [types.PURCHASE_DELETE_ITEM](state, data) {
      state.purchaseList.splice(data.index, 1);
    },
    [types.PURCHASE_WAIT_LIST](state, data) {
      state.purchaseWaitList=data;
    },
    [types.PURCHASE_REJECT_ITEM](state, data) {
      state.purchaseWaitList.splice(data.index, 1);
    },
    [types.PO_WAIT_LIST](state, data) {
      state.prList=data;
    },
    [types.PO_CREATE_LIST](state, data) {
      state.poList=data;
    },
};

export default {
    state,
    actions,
    getters,
    mutations
};
