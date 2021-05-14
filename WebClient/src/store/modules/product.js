/* eslint-disable no-empty-pattern */
import request from "../../utils/request";
// import * as types from '../type'
import { Message } from "element-ui";

const state = {
    productList:[],
    categoryList:[],

};

const actions = {
    SaveProduct({ },Storage){
        return request.ReqSaveProduct(Storage);
    },
    GetProductList({ commit }) {
        request.ReqProductList().then(res => {
            if (res.resultStatus == 1) {
                commit("SetProductList", res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },
    DeleteProduct({ commit }, product) {
      request
          .ReqDeleteProduct(product)
          .then(res=>{
              if (res.resultStatus == 1) {
                  commit("SpliceProductList", product)
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
    PushProductList({commit}, product){
        commit("PushProductList",product);
    },
    QueryProductObj({},product){
        return request.ReqQueryProductObj(product);
    },
    QueryProductAndBomObj({},product){
        return request.ReqQueryProductAndBomObj(product);
    },


    
    GetCategoryList({ commit }) {
        request.ReqCategoryList().then(res => {
            if (res.resultStatus == 1) {
                commit("SetCategoryList", res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },
    DisableCategory({ commit }, Category) {
        request.ReqDisableCategory(Category).then(res => {
          if (res.resultStatus == 1) {
            commit("DisableCategory", Category)
            Message.info("删除成功")
          } else {
            Message.warning("删除失败:" + res.message)
          }
        }).catch(error => {
          let al = error.message ? error.message : error
          Message.error(al)
        })
    },
    DeleteCategory({ commit }, Category) {
        request.ReqDeleteCategory(Category).then(res => {
          if (res.resultStatus == 1) {
            commit("DisableCategory", Category)
            Message.info("删除成功")
          } else {
            Message.warning("删除失败:" + res.message)
          }
        }).catch(error => {
          let al = error.message ? error.message : error
          Message.error(al)
        })
    },
    SaveCategory({ },Category){
        return request.ReqSaveCategory(Category);
    },
    PushCategoryList({commit}, Category){
        commit("PushCategoryList",Category);
    },
    QueryCategoryObj({},Category){
        return request.ReqQueryCategoryObj(Category);
    },


};

const getters = {
    productList:state=>state.productList,
    categoryList:state=>state.categoryList,
};

const mutations = {
    SetProductList(state, data) {
        state.productList = data;
    },
    SetCategoryList(state, data) {
        state.categoryList = data;
    },
    SpliceProductList(state, data) {
        state.productList.splice(data.index, 1);
    },
    DisableCategory(state, data) {
        state.categoryList.splice(data.index, 1)
    },
    PushCategoryList(state,data){
        state.categoryList.push(data);
    },
    PushProductList(state,data){
        state.productList.push(data);
    },
};

export default {
    state,
    actions,
    getters,
    mutations
};
