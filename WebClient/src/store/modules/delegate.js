/* eslint-disable no-empty-pattern */
import request from "../../utils/request";
import { Message } from "element-ui";
import * as types from '../type'

const state = {
    delegateReleaseList: [],
    delegateDailogCloseFalg: false,
    delegateProcessingList:[],
    delegateRecordList:[]
};

const actions = {
    FindDelegateReleaseList({ commit }) {
        request.GetDelegateReleaseList().then(res => {
            if (res.resultStatus == 1) {
                commit(types.DELEGATE_RELEASE_LIST, res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },
    FindDelegateProcessingList({ commit }) {
        request.GetDelegateProcessingList().then(res => {
            if (res.resultStatus == 1) {
                commit(types.DELEGATE_PROCESSING_LIST, res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },
    FindDelegateRecordList({ commit },param) {
        request.GetDelegateRecordList(param).then(res => {
            if (res.resultStatus == 1) {
                commit(types.DELEGATE_RECORD_LIST, res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },
    FindDelegaetReleaseInfo({ }, param) {
        return request.GetDelegateReleaseInfo(param)
    },
    FindDelegateInBoundList({},param){
        return request.GetDelegateInBoundList(param)
    },
    DelegateReleaseTicketConfirm({ commit }, param) {
        request.DelegateTicketReleaseConfirm(param).then(res => {
            if (res.resultStatus == 1) {
                commit("RemoveDelegateTicket", res.data)
                commit("SetDailogFlag")
                Message.success("下达成功")
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },
    SetDelegateDailogFlag({ commit }) {
        commit("SetDailogFlag")
    }
};

const getters = {
    delegateReleaseList: state => state.delegateReleaseList,
    delegateDailogCloseFalg: state => state.delegateDailogCloseFalg,
    delegateProcessingList:state=>state.delegateProcessingList,
    delegateRecordList:state=>state.delegateRecordList
};

const mutations = {
    [types.DELEGATE_RELEASE_LIST](state, data) {
        state.delegateReleaseList = data;
    },
    [types.DELEGATE_PROCESSING_LIST](state, data) {
        state.delegateProcessingList = data;
    },
    [types.DELEGATE_RECORD_LIST](state, data) {
        state.delegateRecordList = data;
    },
    RemoveDelegateTicket(state, data) {
        var index = state.delegateReleaseList.indexOf(item => {
            return item.id === data.id
        })
        state.delegateReleaseList.splice(index, 1)
    },
    SetDailogFlag(state) {
        state.delegateDailogCloseFalg = !state.delegateDailogCloseFalg;
    }
};

export default {
    state,
    actions,
    getters,
    mutations
};
