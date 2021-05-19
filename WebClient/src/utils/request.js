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
  GetProvinceList(){
    return service.get("/public/provinceList");
  },
  GetCityList(params){
    return service.get("/public/cityList",params);
  },
  GetAreaList(params){
    return service.get("/public/areaList",params);
  },
  CheckPhoneNumber(params){
    return service.get('/public/checkPhone',params)
  },
  GetCompanyInfo(){
    return service.get('/common/company')
  },

  //system 系统交互
  RegisterCompany(params){
    return service.post('/system/registerCompany',params)
  },

  //系统登录
  SystemLogin(params){
    return service.post('/login/login',params)
  },
  //注销登录
  SystemLogout(){
    return service.post('/login/logout')
  },

  //合同相关
  GetContractTemplate(){
    return service.get('/contract/formal/contractTemplate')
  },
  SaveIntentionContract(param){
    return service.post('/contract/intention/save',param)
  },
  GetCustomerVoList(){
    return service.get('/customer/listVo')
  },
  GetContractVoByCustomerNo(param){
    return service.get('/customer/queryCustomer',param)
  },
  GetContractIntentionProductin(param){
    return service.get('/common/initialContract',param)
  },
  GetIntentionContractList(){
    return service.get('/contract/intention/intentionList')
  },
  ApproveContract(param){
    return service.deleteOne('/contract/intention/approve',param)
  },
  InvalidContract(param){
    return service.deleteOne('/contract/intention/invalid',param)
  },
  GetFormalContractList(param){
    return service.get('/contract/formal/list',param)
  },
  GetContractOne(param){
    return service.get('/contract/formal/get',param)
  },
  RemoveContractStock(param){
    return service.deleteOne('/contract/intention/deleteStock',param)
  },


  //基础数据相关
  ReqCustomerList(param){
    return service.get('/customer/list',param)
  },
  ReqContactList(param){
    return service.get('/customerContact/list',param)
  },
  ReqSaveCustomer(param){
    return service.post("/customer/save",param)
  },
  ReqQueryCustomerObj(params){
    return service.post("/customer/query",params);
  },
  ReqQueryContactObj(params){
    return service.post("/customerContact/query",params);
  },
  ReqUpdateCustomerStatus(params) {
    return service.post("/customer/updateStatus", params);
  },
  ReqSaveContact(param){
    return service.post("/customerContact/save",param)
  },
  ReqDeleteContact(params){
    return service.post("/customerContact/delete",params)
  },

  SaveProduceProcessOne(param){
    return service.post("/produceSetting/saveProduceProcess",param)
  },
  FindAllProduceProcessList(){
    return service.get("/produceSetting/produceProcessList")
  },
  DeleteProduceProcess(param){
    return service.deleteOne('/produceSetting/deleteProduceProcess',param)
  },
  SaveProduceLine(param){
    return service.post("/produceSetting/saveProduceLine",param)
  },

  FindAllProduceLine(){
    return service.get("/produceSetting/produceLineList")
  },

  FindWorkStationListByLineId(param){
    return service.get("/produceSetting/lineWorkStationList",param)
  },

  DeleteOneWorkStation(param){
    return service.deleteOne('/produceSetting/deleteWorkStation',param)
  },
  DeleteOneProduceLine(param){
    return service.deleteOne('/produceSetting/deleteProduceLine',param)
  },

  ReqProductList(params){
    return service.get('/product/list',params)
  },
  ReqDeleteProduct(params){
    return service.post("/product/delete",params)
  },
  ReqSaveProduct(param){
    return service.post("/product/save",param)
  },
  ReqQueryProductObj(params){
    return service.post("/product/query",params);
  },
  ReqQueryProductAndBomObj(params){
    return service.post("/product/queryProductAndBomList",params);
  },
  ReqSaveProductBom(param){
    return service.post("/productBom/saveProductBom",param)
  },
  ReqQueryProductBomDetailObj(param){
    return service.post("/productBom/ProductBomsDetail",param)
  },



  ReqUpdateProductBom(params) {
    return service.post("/productBom/updateProductBom", params);
  },
  ReqProductBomList(params){
    return service.get('/productBom/getProductBomList',params)
  },
  ReqQueryProductBomObj(params){
    return service.post("/productBom/query",params);
  },


  ReqCategoryList(params){
    return service.get('/productCategory/list',params)    //  //
  },
  ReqDisableCategory(params) {
    return service.post("/productCategory/disable", params);
  },
  ReqSaveCategory(param){
    return service.post("/productCategory/save",param)
  },
  ReqDeleteCategory(params){
    return service.post("/productCategory/delete",params)
  },
  ReqQueryCategoryObj(params){
    return service.post("/productCategory/query",params);
  },

  ReqStorageList(params){
    return service.get('/storage/storageAreaLocationList',params)    //  //
  },
  ReqDeleteStorage(params){
    return service.post("/storage/deleteStorageAreaLocation",params)
  },
  ReqSaveStorage(param){
    return service.post("/storage/storageAreaLocationSave",param)
  },
  ReqQueryStorageObj(params){
    return service.post("/storage/queryStorageAreaLocation",params);
  },

  ReqMaterialList(params){
    return service.get('/material/list',params)    //  //
  },
  ReqDeleteMaterial(params){
    return service.post("/material/delete",params)
  },
  ReqSaveMaterial(param){
    return service.post("/material/save",param)
  },
  ReqQueryMaterialObj(params){
    return service.post("/material/query",params);
  },


  ReqSupplyList(params){
    return service.get('/supply/list',params)    //  //
  },
  ReqSaveSupply(param){
    return service.post("/supply/save",param)
  },
  ReqQuerySupplyObj(params){
    return service.post("/supply/query",params);
  },
  ReqDeleteSupply(params){
    return service.post("/supply/delete",params)
  },


  //订单相关
  GetCreatedOrderList(param){
    return service.get('/orderForm/manage/createdList',param)
  },
  GetOrderFormDetailListById(param){
    return service.get('/orderForm/manage/orderDetailList',param)
  },
  GetProductBomListByBomNo(param){
    return service.get('/productBom/pBomList',param)
  },
  GetProductBomInfo(param){
    return service.get('/Boms/list',param)
  },
  OrderFormConfirm(param){
    return service.post('/orderForm/manage/confirm',param)
  },

  //生产计划相关
  /**
   * 根据produce state 获取plan list
   * @param {*} param produce plan state
   */
  FindProducePlanList(param){
    return service.get('/produce/producePlanList',param)
  },
  FindProducePlanDetailList(param){
    return service.get('/produce/planDetailList',param)
  },
  FindProducePlanDetaiProcessList(param){
    return service.get('/produce/planDetailProcessList',param)
  },
  FindWorkStationListByProcessId(param){
    return service.get('/produce/workStationList',param)
  },
  SaveProducePlanInfo(param){
    return service.post('/produce/saveProducePlanInfo',param)
  },
  DeletePlanWorkStation(param){
    return service.deleteOne('/produce/deletePlanWorkStation',param)
  }

};
