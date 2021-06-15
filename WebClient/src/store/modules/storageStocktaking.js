/* eslint-disable no-empty-pattern */
import request from "../../utils/request";
import { Message } from "element-ui";
import * as types from '../type'

const state = {
    inventoryStocktaking: {},
    createdDailogCloseFlag: false
};

const actions = {
    CreateInventoryStockTaking({ commit }, param) {
        request.CreateInventoryTaking(param).then((res) => {
            if (res.resultStatus == 1) {
                commit(types.INVENTORY_STOCK_TAKING, res.data)
                commit('SetCreatedDailogClose', true)
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },
    SetCreatedDailog({ commit }, param) {
        commit('SetCreatedDailogClose', param)
    },
    SaveInventorayTaking({ }, param) {
        return request.SaveInventoryTaking(param)
    },
    ClearInventoryStocktaking({ commit }, param) {
        commit(types.INVENTORY_STOCK_TAKING, param)
    }
    ,
    GetUnCompletedOne({ commit }, param) {
        request.GetUnCompletedInventoryTaking(param).then((res) => {
            if (res.resultStatus == 1) {
                if (res.data) {
                    commit(types.INVENTORY_STOCK_TAKING, res.data)
                }
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    }

};

const getters = {
    inventoryStocktaking: state => state.inventoryStocktaking,
    createdDailogCloseFlag: state => state.createdDailogCloseFlag,
};


const mutations = {
    [types.INVENTORY_STOCK_TAKING](state, data) {
        if (data.detailList) {
            data.detailList.map(item => {
                item.checked = false
            })
        }
        state.inventoryStocktaking = data;
    },
    SetCreatedDailogClose(state, data) {
        state.createdDailogCloseFlag = data;
    },
};

export default {
    state,
    actions,
    getters,
    mutations
};
