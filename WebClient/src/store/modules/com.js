/* eslint-disable no-empty-pattern */
import { Loading } from "element-ui";
import request from "../../utils/request";
import { Message } from "element-ui";
import * as types from '../type'

const state = {
    maskloading: false,
    loading: {},
    cityList: [],
    provinceList: [],
    areaList: [],
    company:[],
    taxRateList:[]
};

const actions = {
    ReSetAllStates({ commit }, payload) {
        commit("resetAllState", payload);
    },
    // eslint-disable-next-line no-empty-pattern
    SayHelloToServer({ }) {
        return request.SayHelloToServe();
    },
    GetProvinceList({ commit }) {
        request.GetProvinceList().then(res => {
            if (res.resultStatus == 1) {
                commit(types.COM_PROVINCE_LIST, res.data)
            } else {
                Message.warning(res.messgae)
            }
        }).catch(err => {
            Message.error(err.messgae)
        })
    },
    GetCityList({ commit }, params) {
        request.GetCityList(params).then(res => {
            if (res.resultStatus == 1) {
                commit(types.COM_CITY_LIST, res.data)
            } else {
                Message.warning(res.messgae)
            }
        }).catch(err => {
            Message.error(err.messgae)
        })
    },
    GetAreaList({ commit }, params) {
        request.GetAreaList(params).then(res => {
            if (res.resultStatus == 1) {
                commit(types.COM_AREA_LIST, res.data)
            } else {
                Message.warning(res.messgae)
            }
        }).catch(err => {
            Message.error(err.messgae)
        })
    },
    CheckPhoneNumebr({ }, params) {
        return request.CheckPhoneNumber(params);
    },
    RegisterCompany({},params){
        return request.RegisterCompany(params);
    },
    GetCompanyInfo({commit}){
        request.GetCompanyInfo().then(res=>{
            if (res.resultStatus == 1) {
                commit(types.COM_COMPANY_INFO, res.data)
            } else {
                Message.warning(res.messgae)
            }
        }).catch(err => {
            Message.error(err.messgae)
        })
    },
    GetTaxRateList({commit}){
        request.GetTaxRateList().then(res=>{
            if (res.resultStatus == 1) {
                commit(types.COM_TAX_RATE_LIST, res.data)
            } else {
                Message.warning(res.messgae)
            }
        }).catch(err => {
            Message.error(err.messgae)
        })
    }
};

const getters = {
    maskloading: state => state.maskloading,
    provinceList: state => state.provinceList,
    cityList: state => state.cityList,
    areaList: state => state.areaList,
    company:state=>state.company,
    taxRateList:state=>state.taxRateList
};

const mutations = {
    showLoading(state) {
        state.loading = Loading.service({
            text: "?????????...",
            lock: true,
            spinner: "el-icon-loading",
            background: "rgba(0, 0, 0, 0.7)"
        });
        state.maskloading = true;
    },
    hideLoading(state) {
        if (state.maskloading) {
            state.loading.close();
            state.maskloading = false;
        }
    },
    [types.COM_PROVINCE_LIST](state, data) {
        state.provinceList = data;
    },
    [types.COM_CITY_LIST](state, data) {
        state.cityList = data;
    },
    [types.COM_AREA_LIST](state, data) {
        state.areaList = data;
    },
    [types.COM_COMPANY_INFO](state,data){
        state.company=data
    },
    [types.COM_TAX_RATE_LIST](state,data){
        state.taxRateList=data
    }
};

export default {
    state,
    actions,
    getters,
    mutations
};
