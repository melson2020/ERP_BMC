/* eslint-disable no-empty-pattern */
import request from "../../utils/request";
// import * as types from '../type'
import { Message } from "element-ui";

const state = {
    departmentList: [],
    employeeList: [],
    roleList:[],
    workGroupList:[]
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
          if(Employee.permission.toUpperCase()=="NORMAL")
          {
            commit("DisableEmployee", Employee)
          }
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
    GetworkGroupList({ commit }) {
      request.ReqWorkGroupList().then(res => {
          if (res.resultStatus == 1) {
              commit("SetWorkGroupList", res.data)
          } else {
              Message.warning(res.message)
          }
      }).catch(err => {
          Message.error(err.message)
      })
    },
    SaveworkGroup({ },newWorkGroup){
      return request.ReqSaveNewWorkGroup(newWorkGroup);
    },
    QueryworkGroupObj({},WorkGroup){
      return request.ReqQueryWorkGroupObj(WorkGroup);
    },
    PushworkGroupList({commit}, WorkGroup){
      commit("PushWorkGroupList",WorkGroup);
    },
    UpdateworkGroupStatus({}, WorkGroup) {
      request.ReqUpdateWorkGroupStatus(WorkGroup).then(res => {
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
    DeleteworkGroup({ commit }, WorkGroup) {
      request
          .ReqDeleteWorkGroup(WorkGroup)
          .then(res=>{
              if (res.resultStatus == 1) {
                  commit("SpliceWorkGroupList", WorkGroup)
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
    SaveUserGroup({ },UserGroup){
      request
        .ReqSaveUserGroup(UserGroup)
        .then(res => {
          if (res.resultStatus == 1) {
            Message.warning("保存成功")
          } else {
            Message.warning("保存失败")
          }
        })
        .catch(err => {
          let alert = err.message ? err.message : err;
          this.$messgae.error(alert);
        });
    },
    DeleteUserGroup({ }, UserGroup) {
      request
        .ReqDeleteUserGroup(UserGroup)
        .then(res=>{
            if (res.resultStatus == 1) {
                Message.warning("删除成功")
            }
            else {
                Message.warning("删除失败");
            }
        })
        .catch(error => {
            let al = error.message ? error.message : error
            Message.error(al)
        })
    },

};

const getters = {
    departmentList: state => state.departmentList,
    employeeList: state => state.employeeList,
    roleList:state=>state.roleList,
    workGroupList:state=>state.workGroupList,
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
    SetWorkGroupList(state, data) {
      state.workGroupList=data;
    },
    PushWorkGroupList(state,data){
      state.workGroupList.push(data);
    },
    SpliceWorkGroupList(state, data) {
      state.workGroupList.splice(data.index, 1);
  },
};

export default {
    state,
    actions,
    getters,
    mutations
};
