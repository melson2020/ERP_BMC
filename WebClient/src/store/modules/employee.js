/* eslint-disable no-empty-pattern */
import request from "../../utils/request";
// import * as types from '../type'
import { Message } from "element-ui";

const state = {
    departmentList: [],
    employeeList: [],
    roleList:[]
};

const actions = {
    GetDepartmentList({ commit }) {
        request.ReqDepartmentList().then(res => {
            if (res.resultStatus == 1) {
                commit("SetDepartmentList", res.data)
            } else {
                Message.warning(res.message)
            }
        }).catch(err => {
            Message.error(err.message)
        })
    },

    SaveDepartment({ },newDepartment){
        return request.ReqSaveDepartment(newDepartment);
    },
    PushDepartmentList({commit}, Department){
        commit("PushDepartmentList",Department);
    },
    QueryDepartmentObj({},Department){
        return request.ReqQueryDepartmentObj(Department);
    },
    UpdateDepartment({ }, Department) {
        request.ReqUpdateDepartmentStatus(Department).then(res => {
          if (res.resultStatus == 1) {
            Message.info("操作成功")
          } else {
            Message.warning("操作失败:" + res.message)
          }
        }).catch(error => {
          let al = error.message ? error.message : error
          Message.error(al)
        })
    },
    UpdateDepartmentStatus({ }, Department) {
        request.ReqUpdateDepartmentStatus(Department).then(res => {
          if (res.resultStatus == 1) {
            Message.info("操作成功")
          } else {
            Message.warning("操作失败:" + res.message)
          }
        }).catch(error => {
          let al = error.message ? error.message : error
          Message.error(al)
        })
    },


    GetemployeeList({ commit }, params) {
      request.ReqEmployeeList(params).then(res => {
          if (res.resultStatus == 1) {
              commit("SetEmployeeList", res.data)
          } else {
              Message.warning(res.message)
          }
      }).catch(err => {
          Message.error(err.message)
      })
    },
    Saveemployee({ },newEmployee){
    return request.ReqSaveEmployee(newEmployee);
    },
    QueryemployeeObj({},Employee){
      return request.ReqQueryEmployeeObj(Employee);
    },
    PushemployeeList({commit}, Employee){
      commit("PushEmployeeList",Employee);
    },
    UpdateemployeeStatus({commit }, Employee) {
      request.ReqUpdateEmployeeStatus(Employee).then(res => {
        if (res.resultStatus == 1) {
          commit("DisableEmployee", Employee)
          Message.info("操作成功")
        } else {
          Message.warning("操作失败:" + res.message)
        }
      }).catch(error => {
        let al = error.message ? error.message : error
        Message.error(al)
      })
    },
    GetRoleList({ commit }) {
      request.ReqRoleList().then(res => {
          if (res.resultStatus == 1) {
              commit("SetRoleList", res.data)
          } else {
              Message.warning(res.message)
          }
      }).catch(err => {
          Message.error(err.message)
      })
  },

};

const getters = {
    departmentList: state => state.departmentList,
    employeeList: state => state.employeeList,
    roleList:state=>state.roleList
};

const mutations = {
    SetDepartmentList(state, data) {
        state.departmentList = data;
    },

    PushDepartmentList(state,data){
        state.departmentList.push(data);
    },
    SetEmployeeList(state, data) {
      state.employeeList=data;
    },
    PushEmployeeList(state,data){
      state.employeeList.push(data);
    },
    DisableEmployee(state, data) {
      state.employeeList.splice(data.index, 1)
    },
    SetRoleList(state, data) {
      state.roleList=data;
    },
};

export default {
    state,
    actions,
    getters,
    mutations
};
