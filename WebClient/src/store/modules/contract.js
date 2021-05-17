/* eslint-disable no-empty-pattern */
import request from "../../utils/request";
// import { Message } from "element-ui";
import * as types from '../type'
import { Message } from "element-ui";

const state = {
    contractTemplate: {},
    customerVoList: [],
    intentionContractList: [],
    formalContractList: []
};

const actions = {
    GetContractTemplate({ commit }) {
        request.GetContractTemplate().then(res => {
            if (res.resultStatus == 1) {
                commit(types.CONTRACT_TEMPLATE, res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },
    SaveIntentionContract({ }, param) {
        request.SaveIntentionContract(param).then(res => {
            if (res.resultStatus == 1) {
                Message.success("保存成功")
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },
    GetCustomerVoList({ commit }) {
        request.GetCustomerVoList().then(res => {
            if (res.resultStatus == 1) {
                commit(types.CUSTOMER_VO_LIST, res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },
    GetContractVoByCustomerNo({ }, param) {
        return request.GetContractVoByCustomerNo(param)
    },
    SearchContractIntentionProductList({ }, param) {
        return request.GetContractIntentionProductin(param)
    },
    GetIntentionContractList({ commit }) {
        request.GetIntentionContractList().then(res => {
            if (res.resultStatus == 1) {
                commit(types.CONTRACT_INTENTION_LIST, res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },
    ApproveContract({ commit }, param) {
        request.ApproveContract(param).then(res => {
            if (res.resultStatus == 1) {
                Message.success("转正成功")
                commit("RemoveIntentionContract", res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },
    InvalidContract({ commit }, param) {
        request.InvalidContract(param).then(res => {
            if (res.resultStatus == 1) {
                Message.success("作废成功")
                commit("RemoveIntentionContract", res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },
    GetFormalContractList({ commit }, param) {
        request.GetFormalContractList(param).then(res => {
            if (res.resultStatus == 1) {
                commit(types.CONTRACT_FORMAL_LIST, res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },
    RemoveContractStock({ }, param) {
        return request.RemoveContractStock(param)
    },
    GetContractOne({ }, param) {
        return request.GetContractOne(param);
    }
};

const getters = {
    contractTemplate: state => state.contractTemplate,
    customerVoList: state => state.customerVoList,
    intentionContractList: state => state.intentionContractList,
    formalContractList: state => state.formalContractList
};

const mutations = {
    [types.CONTRACT_TEMPLATE](state, data) {
        state.contractTemplate = data;
    },
    [types.CUSTOMER_VO_LIST](state, data) {
        state.customerVoList = data;
    },
    [types.CONTRACT_INTENTION_LIST](state, data) {
        state.intentionContractList = data;
    },
    [types.CONTRACT_FORMAL_LIST](state, data) {
        state.formalContractList = data;
    },
    RemoveIntentionContract(state, data) {
        var index = state.intentionContractList.indexOf(item => {
            return item.id === data.id
        })
        state.intentionContractList.splice(index, 1)
    }
};

export default {
    state,
    actions,
    getters,
    mutations
};
