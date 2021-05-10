/* eslint-disable no-empty-pattern */
import request from "../../utils/request";
import { Message } from "element-ui";
import * as types from '../type'

const state = {
    produceTechList: []
};

const actions = {
    SaveProduceTechOne({},parma) {
        return request.SaveProduceTechOne(parma)
    },
    FindAllProduceTechList({commit}){
        request.FindAllProduceTechList().then(res=>{
            if (res.resultStatus == 1) {
                commit(types.PRODUCE_TECH_LIST, res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(err=>{
            Message.error(err.message)
        })
    },
    DeleteProduceTech({commit},param){
        request.DeleteProduceTech(param).then(res=>{
            if (res.resultStatus == 1) {
                commit(types.DELETE_PRODUCE_TECH, res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(err=>{
            Message.error(err.message)
        })
    },
    SaveProduceLine({},param){
       return request.SaveProduceLine(param)
    }
};

const getters = {
    produceTechList: state => state.produceTechList,
};

const mutations = {
   [types.PRODUCE_TECH_LIST](state,data){
       state.produceTechList=data
   },
   [types.DELETE_PRODUCE_TECH](state,data){
    var index = state.produceTechList.indexOf(item => {
        return item.id = data
    })
    state.produceTechList.splice(index)
   }
};

export default {
    state,
    actions,
    getters,
    mutations
};
