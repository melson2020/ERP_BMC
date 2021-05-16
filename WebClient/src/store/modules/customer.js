/* eslint-disable no-empty-pattern */
import request from "../../utils/request";
// import * as types from '../type'
import { Message } from "element-ui";

const state = {
    deliverAddressList: [],
    customerList: [],
    contactList:[]
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
    GetContactList({ commit }){
        request.ReqContactList()
        .then(res=>{
            if (res.resultStatus == 1) {
                commit("SetContactList", res.data)
            } else {
                Message.warning(res.message)
            }
        })
        .catch(err => {
            Message.error(err.message)
        })
    },
    SaveCustomer({ },newCustomer){
        return request.ReqSaveCustomer(newCustomer);
    },
    SaveContact({ },contact){
        return request.ReqSaveContact(contact);
    },
    PushCustomerList({commit}, newCustomer){
        commit("PushCustomerList",newCustomer);
    },
    QueryCustomerObj({},Customer){
        return request.ReqQueryCustomerObj(Customer);
    },
    QueryContactObj({},Contact){
        return request.ReqQueryContactObj(Contact);
    },
    UpdateCustomer({ }, Customer) {
        request.ReqUpdateCustomerStatus(Customer).then(res => {
          if (res.resultStatus == 1) {
            // commit("DisableCustomer", Customer)
            Message.info("操作成功")
          } else {
            Message.warning("操作失败:" + res.message)
          }
        }).catch(error => {
          let al = error.message ? error.message : error
          Message.error(al)
        })
      },
      DeleteContact({ commit }, contact) {
        request
            .ReqDeleteContact(contact)
            .then(res=>{
                if (res.resultStatus == 1) {
                    commit("SpliceContactList", contact)
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


};

const getters = {
    deliverAddressList: state => state.deliverAddressList,
    customerList: state => state.customerList,
    contactList:state=>state.contactList
};

const mutations = {
    SetCustomerList(state, data) {
        state.customerList = data;
    },
    SetContactList(state,data){
        state.contactList=data;
    },
    PushCustomerList(state,data){
        state.customerList.push(data);
    },
    DisableCustomer(state, data) {
        state.customerList.splice(data.index, 1)
    },
    SpliceContactList(state, data) {
        state.contactList.splice(data.index, 1);
    },
};

export default {
    state,
    actions,
    getters,
    mutations
};
