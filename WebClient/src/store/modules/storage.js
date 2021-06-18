/* eslint-disable no-empty-pattern */
import request from "../../utils/request";
import * as types from '../type'
import { Message } from "element-ui";

const state = {
    //仓库列表
    storageList: [],
    storageDetailPage: {},
};

const actions = {
    GetStorageList({ commit }) {
        request.ReqStorageList().then(res => {
            if (res.resultStatus == 1) {
                commit("SetStorageList", res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },
    DeleteStorage({ commit }, Storage) {
        request
            .ReqDeleteStorage(Storage)
            .then(res => {
                if (res.resultStatus == 1) {
                    commit("SpliceStorageList", Storage)
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

    GetStorageDetail({ commit }, params) {
        request.GetStorageDetails(params).then(res => {
            if (res.resultStatus == 1) {
                commit(types.STORAGE_DETAIL_LIST, res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(error => {
            let al = error.message ? error.message : error
            Message.error(al)
        })
    },

    GetStorageUnitListByProductId({},param){
       return request.GetStorageUnitList(param)
    },
    SaveStorageUnitOne({},param){
        return request.SaveStorageUnitOne(param)
    },
    DeleteStorageUnitOne({},param){
        return request.DeleteStorageUnitOne(param)
    },
    SaveStorage({ }, Storage) {
        return request.ReqSaveStorage(Storage);
    },
    PushStorageList({ commit }, Storage) {
        commit("PushStorageList", Storage);
    },
    QueryStorageObj({ }, Storage) {
        return request.ReqQueryStorageObj(Storage);
    },
    CreateInventoryInBound({},param){
        return request.CreateInventoryInBound(param)
    },
    GetPackageUnitList({},param){
        return request.GetPackageUnitList(param)
    },
    GetUnPackageUnitList({},param){
        return request.GetUnPackageUnitList(param)
    },
    SaveInventoryInBound({},param){
        return request.InventoryInBoundSave(param)
    },
    SaveInventoryOutBound({},param){
        return request.InventoryOutBoundSave(param)
    },
    FindPickingTicketInBoundList({},param){
        return request.GetPickingTicketInboundList(param)
    },
    FindPickingTicketOutBoundList({}){
        return request.GetPickingTicketOutboundList()
    },
    CreateInventoryOutBound({},param){
        return request.CreateInventoryOutBound(param)
    },
    FindStorageDetaiListByProductId({},param){
        return request.GetStorageDetailsByProductId(param)
    },
    CreateOutBoundBatchInfo({},param){
        return request.CreateOutBoundBatchInfo(param)
    },
    GetOrderDeliveryByState({},param){
        return request.GetOrderDeliveryByState(param)
    },
};

const getters = {
    storageList: state => state.storageList,
    storageDetailPage: state => state.storageDetailPage,
    storagePurchaseList:state=>state.storagePurchaseList
};

const mutations = {
    SetStorageList(state, data) {
        state.storageList = data;
    },

    SpliceStorageList(state, data) {
        state.storageList.splice(data.index, 1);
    },
    PushStorageList(state, data) {
        state.storageList.push(data);
    },
    [types.STORAGE_DETAIL_LIST](state, data) {
        state.storageDetailPage = data;
    },
};

export default {
    state,
    actions,
    getters,
    mutations
};
