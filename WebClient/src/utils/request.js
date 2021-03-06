import * as service from "./axios";

export default {
  /**
   * 测试是否连接到服务器
   * @param {*} params
   */
  SayHelloToServe() {
    return service.get("/testweb/hello");
  },

  //com 系统通用数据
  GetProvinceList() {
    return service.get("/public/provinceList");
  },
  GetCityList(params) {
    return service.get("/public/cityList", params);
  },
  GetAreaList(params) {
    return service.get("/public/areaList", params);
  },
  CheckPhoneNumber(params) {
    return service.get('/public/checkPhone', params)
  },
  GetCompanyInfo() {
    return service.get('/common/company')
  },
  GetTaxRateList() {
    return service.get('/common/taxRateList')
  },

  //system 系统交互
  RegisterCompany(params) {
    return service.post('/system/registerCompany', params)
  },

  //系统登录
  SystemLogin(params) {
    return service.post('/login/login', params)
  },
  //注销登录
  SystemLogout() {
    return service.post('/login/logout')
  },
  //修改密码
  ResetPassword(params) {
    return service.post("/login/restPassword", params)
  },

  //合同相关
  GetContractTemplate() {
    return service.get('/contract/formal/contractTemplate')
  },
  SaveIntentionContract(param) {
    return service.post('/contract/intention/save', param)
  },
  GetCustomerVoList() {
    return service.get('/customer/listVo')
  },
  GetContractVoByCustomerNo(param) {
    return service.get('/customer/queryCustomer', param)
  },
  GetContractIntentionProductin(param) {
    return service.get('/common/initialContract', param)
  },
  GetIntentionContractList() {
    return service.get('/contract/intention/intentionList')
  },
  ApproveContract(param) {
    return service.deleteOne('/contract/intention/approve', param)
  },
  InvalidContract(param) {
    return service.deleteOne('/contract/intention/invalid', param)
  },
  GetFormalContractList(param) {
    return service.get('/contract/formal/list', param)
  },
  GetContractOne(param) {
    return service.get('/contract/formal/get', param)
  },
  RemoveContractStock(param) {
    return service.deleteOne('/contract/intention/deleteStock', param)
  },


  //基础数据相关
  ReqCustomerList(param) {
    return service.get('/customer/list', param)
  },
  ReqContactList(param) {
    return service.get('/customerContact/list', param)
  },
  ReqSaveCustomer(param) {
    return service.post("/customer/save", param)
  },
  ReqQueryCustomerObj(params) {
    return service.post("/customer/query", params);
  },
  ReqQueryContactObj(params) {
    return service.post("/customerContact/query", params);
  },
  ReqUpdateCustomerStatus(params) {
    return service.post("/customer/updateStatus", params);
  },
  ReqSaveContact(param) {
    return service.post("/customerContact/save", param)
  },
  ReqDeleteContact(params) {
    return service.post("/customerContact/delete", params)
  },

  SaveProduceProcessOne(param) {
    return service.post("/produceSetting/saveProduceProcess", param)
  },
  FindAllProduceProcessList() {
    return service.get("/produceSetting/produceProcessList")
  },
  DeleteProduceProcess(param) {
    return service.deleteOne('/produceSetting/deleteProduceProcess', param)
  },
  SaveProduceLine(param) {
    return service.post("/produceSetting/saveProduceLine", param)
  },

  FindAllProduceLine() {
    return service.get("/produceSetting/produceLineList")
  },

  FindWorkStationListByLineId(param) {
    return service.get("/produceSetting/lineWorkStationList", param)
  },

  DeleteOneWorkStation(param) {
    return service.deleteOne('/produceSetting/deleteWorkStation', param)
  },
  DeleteOneProduceLine(param) {
    return service.deleteOne('/produceSetting/deleteProduceLine', param)
  },
  QueryProductListBySearchValue(param){
    return service.get("/product/queryBySearchValue", param)
  },

  ReqProductList(params) {
    return service.get('/product/list', params)
  },
  ReqDeleteProduct(params) {
    return service.post("/product/delete", params)
  },
  ReqSaveProduct(param) {
    return service.post("/product/save", param)
  },
  ReqQueryProductObj(params) {
    return service.post("/product/query", params);
  },
  ReqQueryProductAndBomObj(params) {
    return service.post("/product/queryProductAndBomList", params);
  },
  ReqSaveProductBom(param) {
    return service.post("/productBom/saveProductBom", param)
  },
  ReqQueryProductBomDetailObj(param) {
    return service.post("/productBom/ProductBomsDetail", param)
  },



  ReqUpdateProductBom(params) {
    return service.post("/productBom/updateProductBom", params);
  },
  ReqProductBomList(params) {
    return service.get('/productBom/getProductBomList', params)
  },
  ReqQueryProductBomObj(params) {
    return service.post("/productBom/query", params);
  },
  ReqSaveDetailBoms(param) {
    return service.post("/productBom/detailBomsSave", param)
  },
  ReqDeleteDetailBoms(params) {
    return service.post("/productBom/detailBomsDelete", params)
  },




  ReqCategoryList(params) {
    return service.get('/productCategory/list', params)    //  //
  },
  ReqDisableCategory(params) {
    return service.post("/productCategory/disable", params);
  },
  ReqSaveCategory(param) {
    return service.post("/productCategory/save", param)
  },
  ReqDeleteCategory(params) {
    return service.post("/productCategory/delete", params)
  },
  ReqQueryCategoryObj(params) {
    return service.post("/productCategory/query", params);
  },

  ReqStorageList(params) {
    return service.get('/storage/storageAreaLocationList', params)    //  //
  },
  ReqDeleteStorage(params) {
    return service.post("/storage/deleteStorageAreaLocation", params)
  },
  ReqSaveStorage(param) {
    return service.post("/storage/storageAreaLocationSave", param)
  },
  ReqQueryStorageObj(params) {
    return service.post("/storage/queryStorageAreaLocation", params);
  },

  ReqMaterialList(param) {
    return service.get('/common/materialList', param)
  },

  ReqSupplyList(params) {
    return service.get('/supply/list', params)    //  //
  },
  ReqSaveSupply(param) {
    return service.post("/supply/save", param)
  },
  ReqQuerySupplyObj(params) {
    return service.post("/supply/query", params);
  },
  ReqDeleteSupply(params) {
    return service.post("/supply/delete", params)
  },

  ReqDepartmentList(params) {
    return service.get('/employee/departmentList', params)    //  //
  },
  ReqSaveDepartment(param) {
    return service.post("/employee/departmentSave", param)
  },
  ReqQueryDepartmentObj(params) {
    return service.post("/employee/queryDepartment", params);
  },
  ReqUpdateDepartmentStatus(params) {
    return service.post("/employee/updateDepartmentStatus", params);
  },

  ReqEmployeeList(params) {
    return service.get('/employee/employeeList', params)    //  //
  },
  ReqSaveEmployee(param) {
    return service.post("/employee/employeeSave", param)
  },
  ReqQueryEmployeeObj(params) {
    return service.post("/employee/queryEmployee", params);
  },
  ReqUpdateEmployeeStatus(params) {
    return service.post("/employee/updateEmployeeStatus", params);
  },
  ReqRoleList(params) {
    return service.get('/employee/roleList', params)
  },

  ReqWorkGroupList(params) {
    return service.get('/employee/workGroupList', params)
  },
  ReqSaveNewWorkGroup(param) {
    return service.post("/employee/workGroupSave", param)
  },
  ReqQueryWorkGroupObj(params) {
    return service.post("/employee/queryWorkGroup", params);
  },
  ReqUpdateWorkGroupStatus(params) {
    return service.post("/employee/updateWorkGroupStatus", params);
  },
  ReqDeleteWorkGroup(params) {
    return service.post("/employee/deleteWorkGroup", params)
  },
  ReqSaveUserGroup(param) {
    return service.post("/employee/userGroupSave", param)
  },
  ReqDeleteUserGroup(params) {
    return service.post("/employee/deleteUserGroup", params)
  },


//采购相关
ReqPurchaseList(params){
  return service.get('/purchase/purchaseList',params)    //  //
},
ReqSavePurchase(param){
  return service.post("/purchase/purchaseSave",param)
},
ReqQueryPurchaseObj(params){
  return service.post("/purchase/queryPurchase",params);
},
ReqUpdatePurchaseStatus(params) {
  return service.post("/purchase/updatePurchaseStatus", params);
},
ReqDeletePurchase(params){
  return service.post("/purchase/deletePurchase",params)
},
ReqDeleteDetailPurchase(params){
  return service.post("/purchase/deletePurchaseDetail",params)
},
ReqQueryProductVos(param){
  return service.get('/common/productVoList',param)
},
ReqEmployeeVoList(params){
  return service.get('/common/employeeVos',params)    //  //
},

ReqWaitPurchaseList(params){
  return service.get('/purchase/underApprovePurchaseList',params)    //  //
},
ReqApprovePurchaseObj(param){
  return service.post("/purchase/purchaseApprove",param)
},
ReqRejectPurchaseWait(params){
  return service.post("/purchase/rejectPurchase",params)
},
ReqPRList(params){
  return service.get('/purchase/approvedPurchaseDetailList',params)    //  //
},
ReqPRWithSupplyList(params){
  return service.get('/purchase/approvedPdWithSupplyList',params)    //  //
},
ReqPOList(params){
  return service.get('/purchase/createdPoList',params)    //  //
},
ReqSavePoList(param){
  return service.post("/purchase/poSave",param)
},
ReqQueryPoObj(params){
  return service.post("/purchase/queryPo",params);
},
ReqPrDetailList(params){
  return service.get('/purchase/allPurchaseList',params)  
},
ReqPoDetailList(params){
  return service.get('/purchase/allPoDetailList',params)  
},
ReqGetSummaryCount(params){
  return service.get('/purchase/purchaseStateSummary',params)  
},
ReqGetPurchaseDashboardSummary(param) {
  return service.get('/purchase/purchaseDashboardSummary', param)
},



  //订单相关
  GetCreatedOrderList(param) {
    return service.get('/orderForm/manage/createdList', param)
  },
  GetOrderFormDetailListById(param) {
    return service.get('/orderForm/manage/orderDetailList', param)
  },
  GetProductBomListByBomNo(param) {
    return service.get('/productBom/pBomList', param)
  },
  GetProductBomInfo(param) {
    return service.get('/Boms/list', param)
  },
  OrderFormConfirm(param) {
    return service.post('/orderForm/manage/confirm', param)
  },
  GetOrderCenterSummaryCount() {
    return service.get('/orderForm/manage/stateSummary')
  },
  GetOrderProduceTypeSummary(param) {
    return service.get('/orderForm/manage/produceTypeSummary', param)
  },
  GetOrderFormProcessList() {
    return service.get('/orderForm/manage/processList')
  },
  GetOrderFormDeliveryList() {
    return service.get('/orderForm/manage/deliveryList')
  },
  GetOrderFormInfo(param) {
    return service.get('/orderForm/manage/orderFormInfo', param)
  },
  GetOrderDelviery(param){
    return service.get('/orderForm/manage/orderDelivery', param)
  },
  OrderDeliveryConfirm(param){
    return service.post('/orderDelivery/confirm', param)
  },


  //生产计划相关
  /**
   * 根据produce state 获取plan list
   * @param {*} param produce plan state
   */
  FindProducePlanList(param) {
    return service.get('/produce/producePlanList', param)
  },
  FindProducePlanRecord(param) {
    return service.get('/produce/planRecord', param)
  },
  FindProducePlanDetailList(param) {
    return service.get('/produce/planDetailList', param)
  },
  FindProducePlanDetaiProcessList(param) {
    return service.get('/produce/planDetailProcessList', param)
  },
  FindWorkStationListByProcessId(param) {
    return service.get('/produce/workStationList', param)
  },
  SaveProducePlanInfo(param) {
    return service.post('/produce/saveProducePlanInfo', param)
  },
  DeletePlanWorkStation(param) {
    return service.deleteOne('/produce/deletePlanWorkStation', param)
  },
  //生产计划monitor
  GetProduceLineStateInfo() {
    return service.get("/produceMonitor/produceLineStateInfo")
  },
  GetMonitorCountSummary() {
    return service.get("/produceMonitor/countSummary")
  },
  GetProcessPlanList() {
    return service.get("/produceMonitor/processingPlanList")
  },
  GetPlanInfo(param) {
    return service.get('/produceMonitor/producePlanInfo', param)
  },

  //委外相关
  GetDelegateReleaseList(param) {
    return service.get('/delegate/releaseList', param)
  },
  GetDelegateProcessingList(param) {
    return service.get('/delegate/processingList', param)
  },
  GetDelegateInBoundList(param) {
    return service.get('/delegate/inBoundList', param)
  },
  GetDelegateRecordList(param) {
    return service.get('/delegate/recordList', param)
  },
  GetDelegateReleaseInfo(param) {
    return service.get('/delegate/releaseInfo', param)
  },
  DelegateTicketReleaseConfirm(param) {
    return service.post('/delegate/confirm', param)
  },

  //库存相关
  GetStorageDetails(param) {
    return service.get('/storageDetail/detailList', param)
  },
  GetStorageDetailsByProductId(param) {
    return service.get('/storageDetail/listByProduct', param)
  },
  GetStorageUnitList(param) {
    return service.get('/storageDetail/storageUnitList', param)
  },
  SaveStorageUnitOne(param) {
    return service.post("/storageDetail/saveStorageUnit", param)
  },
  DeleteStorageUnitOne(param) {
    return service.post("/storageDetail/deleteStorageUnit", param)
  },

  GetStorageBatchList(param){
    return service.get('/storageDetail/storageBatchList', param)
  },

  GetPackageUnitList(param) {
    return service.get('/storageDetail/packageUnitList', param)
  },
  GetUnPackageUnitList(param) {
    return service.get('/storageDetail/unPackageUnitList', param)
  },
  CreateInventoryInBound(param) {
    return service.get('/inventory/inbound/createInBound', param)
  },
  InventoryInBoundSave(param){
    return service.post("/inventory/inbound/save", param)
  },
  InventoryOutBoundSave(param){
    return service.post("/inventory/outbound/save", param)
  },
  GetPickingTicketInboundList(param){
    return service.get('/inventory/inbound/pickingInBoundList', param)
  },
  GetPickingTicketOutboundList(param){
    return service.get('/inventory/outbound/pickingOutBoundList', param)
  },
  CreateInventoryOutBound(param){
    return service.get('/inventory/outbound/createOutBound', param)
  },
  CreateOutBoundBatchInfo(param){
    return service.post("/inventory/outbound/generateOutDetailVo",param)
  },
  GetOrderDeliveryByState(param){
    return service.get('/orderDelivery/list', param)
  },
  CreatePurchasePlanByStorage(param){
    return service.post("/storageDetail/createPurchasePlan",param)
  },

  //盘点相关
  CreateInventoryTaking(param){
    return service.post("/inventory/stocktaking/create",param)
  },
  GetUnCompletedInventoryTaking(param){
    return service.get("/inventory/stocktaking/uncompletedOne",param)
  },
  SaveInventoryTaking(param){
    return service.post("/inventory/stocktaking/save",param)
  }

};
