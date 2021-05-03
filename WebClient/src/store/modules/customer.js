/* eslint-disable no-empty-pattern */
import request from "../../utils/request";
import * as types from '../type'
import { Message } from "element-ui";

const state = {
    contactyList: [],
    deliverAddressList: [],
    customerList: []
};

const actions = {
    GetCustomerList({ commit }) {
        request.ReqCustomerList().then(res => {
            if (res.resultStatus == 1) {
                commit("SetCustomerList", res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },
    SaveCustomer({ },newCustomer){
        return request.ReqSaveCustomer(newCustomer);
    },
    PushCustomerList({commit}, newCustomer){
        commit("PushCustomerList",newCustomer);
    },
    QueryCustomerObj({},Customer){
        return request.ReqQueryCustomerObj(Customer);
    },
    DisableCustomer({ commit }, Customer) {
        request.ReqDisableCustomer(Customer).then(res => {
          if (res.resultStatus == 1) {
            commit("DisableCustomer", Customer)
            Message.info("删除成功")
          } else {
            Message.warning("删除失败:" + res.message)
          }
        }).catch(error => {
          let al = error.message ? error.message : error
          Message.error(al)
        })
      }



};

const getters = {
    contactyList: state => state.contactyList,
    deliverAddressList: state => state.deliverAddressList,
    customerList: state => state.customerList
};

const mutations = {
    SetCustomerList(state, data) {
        state.customerList = data
    },
    PushCustomerList(state,data){
        state.customerList.push(data);
    },
    DisableCustomer(state, data) {
        state.customerList.splice(data.index, 1)
      }
};

export default {
    state,
    actions,
    getters,
    mutations
};
