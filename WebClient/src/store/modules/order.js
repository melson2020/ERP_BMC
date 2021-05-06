/* eslint-disable no-empty-pattern */
import request from "../../utils/request";
// import { Message } from "element-ui";
import * as types from '../type'
import { Message } from "element-ui";

const state = {
    orderReadyToReleaseList: [],
    orderFormDetaiList:[]
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
    GetOrderFormDetailList({commit},param){
        request.GetOrderFormDetailListById(param).then(res => {
            if (res.resultStatus == 1) {
                console.log(res.data)
                commit(types.ORDER_DETAIL_LIST, res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },
    //获取产品bom 列表
    GetProductBomList({},param){
        return request.GetProductBomList(param);
    },
    //获取产品bom 详细信息
    GetProductBomInfo({},param){
        return request.GetProductBomInfo(param);
    }

};

const getters = {
    orderReadyToReleaseList: state => state.orderReadyToReleaseList,
    orderFormDetaiList: state => state.orderFormDetaiList,
};

const mutations = {
    [types.CREATED_ORDER](state, data) {
        state.orderReadyToReleaseList = data;
    },
    [types.ORDER_DETAIL_LIST](state, data) {
        data.map(item=>{
            item.pboms=[],
            item.boms=[]
        })
        state.orderFormDetaiList = data;
    }
};

export default {
    state,
    actions,
    getters,
    mutations
};
