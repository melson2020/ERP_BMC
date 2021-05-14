/* eslint-disable no-empty-pattern */
import request from "../../utils/request";
// import { Message } from "element-ui";
import * as types from '../type'
import { Message } from "element-ui";

const state = {
    orderReadyToReleaseList: [],
    orderFormDetaiList: [],
    closeDrawer: false
};

const actions = {
    GetCreatedOrderList({ commit }) {
        request.GetCreatedOrderList().then(res => {
            if (res.resultStatus == 1) {
                commit(types.CREATED_ORDER, res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },
    GetOrderFormDetailList({ commit }, param) {
        request.GetOrderFormDetailListById(param).then(res => {
            if (res.resultStatus == 1) {
                commit(types.ORDER_DETAIL_LIST, res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },
    //获取产品bom 列表
    GetProductBomListByBomNo({ }, param) {
      return   request.GetProductBomListByBomNo(param);
    },
    //获取产品bom 详细信息
    GetProductBomInfo({ }, param) {
        return request.GetProductBomInfo(param);
    },
    OrderFormConfirm({ commit }, param) {
        request.OrderFormConfirm(param).then(res => {
            if (res.resultStatus == 1) {
                commit("RemoveOrderFormReadyRleaseList", res.data)
                commit("TriggerDrawer", true)
                Message.success('订单成功下达')
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },
    TriggerDrawer({ commit }, data) {
        commit("TriggerDrawer", data)
    }

};

const getters = {
    orderReadyToReleaseList: state => state.orderReadyToReleaseList,
    orderFormDetaiList: state => state.orderFormDetaiList,
    closeDrawer: state => state.closeDrawer
};

const mutations = {
    [types.CREATED_ORDER](state, data) {
        state.orderReadyToReleaseList = data;
    },
    [types.ORDER_DETAIL_LIST](state, data) {
        data.map(item => {
            item.pboms = [],
                item.boms = []
        })
        state.orderFormDetaiList = data;
    },
    RemoveOrderFormReadyRleaseList(state, data) {
        state.orderReadyToReleaseList.splice(state.orderReadyToReleaseList.indexOf(item => {
            return item.id = data.id
        }))
    },
    TriggerDrawer(state, data) {
        state.closeDrawer = data;
    }
};

export default {
    state,
    actions,
    getters,
    mutations
};
