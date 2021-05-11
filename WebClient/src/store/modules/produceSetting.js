/* eslint-disable no-empty-pattern */
import request from "../../utils/request";
import { Message } from "element-ui";
import * as types from '../type'

const state = {
    produceTechList: [],
    produceLineList: []
};

const actions = {
    SaveProduceTechOne({ }, parma) {
        return request.SaveProduceTechOne(parma)
    },
    FindAllProduceTechList({ commit }) {
        request.FindAllProduceTechList().then(res => {
            if (res.resultStatus == 1) {
                commit(types.PRODUCE_TECH_LIST, res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },
    DeleteProduceTech({ commit }, param) {
        request.DeleteProduceTech(param).then(res => {
            if (res.resultStatus == 1) {
                commit(types.DELETE_PRODUCE_TECH, res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },
    SaveProduceLine({ }, param) {
        return request.SaveProduceLine(param)
    },
    AddNewLineToList({ commit }, param) {
        commit('AddLineToList', param)
    },
    FindAllProduceLineList({ commit }) {
        request.FindAllProduceLine().then(res => {
            if (res.resultStatus == 1) {
                commit(types.PRODUCE_LINE_LIST, res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },
    FindWorkStationList({ }, param) {
        return request.FindWorkStationListByLineId(param)
    },
    DeleteOneWorkStation({ }, param) {
        return request.DeleteOneWorkStation(param);
    },
    DeleteOneProduceLine({ commit }, param) {
        request.DeleteOneProduceLine(param).then(res => {
            if (res.resultStatus == 1) {
                commit(types.DELETE_PRODUCE_LINE, res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    }

};

const getters = {
    produceTechList: state => state.produceTechList,
    produceLineList: state => state.produceLineList
};

const mutations = {
    [types.PRODUCE_TECH_LIST](state, data) {
        state.produceTechList = data
    },
    [types.PRODUCE_LINE_LIST](state, data) {
        state.produceLineList = data
    },
    [types.DELETE_PRODUCE_TECH](state, data) {
        var index = state.produceTechList.indexOf(item => {
            return item.id = data
        })
        state.produceTechList.splice(index, 1)
    },
    [types.DELETE_PRODUCE_LINE](state, data) {
        var index = state.produceLineList.indexOf(item => {
            return item.id = data
        })
        state.produceLineList.splice(index, 1)
    },
    AddLineToList(state, data) {
        state.produceLineList.push(data)
    }
};

export default {
    state,
    actions,
    getters,
    mutations
};
