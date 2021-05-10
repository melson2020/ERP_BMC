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
  ReqDisableCustomer(params) {
    return service.post("/customer/disable", params);
  },
  ReqSaveContact(param){
    return service.post("/customerContact/save",param)
  },
  ReqDeleteContact(params){
    return service.post("/customerContact/delete",params)
  },
  ReqProductList(params){
    return service.get('/product/list',params)
  },
  ReqDeleteProduct(params){
    return service.post("/product/delete",params)
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



  //订单相关
  GetCreatedOrderList(param){
    return service.get('/orderForm/manage/createdList',param)
  },
  GetOrderFormDetailListById(param){
    return service.get('/orderForm/manage/orderDetailList',param)
  },
  GetProductBomList(param){
    return service.get('/productBom/pBomList',param)
  },
  GetProductBomInfo(param){
    return service.get('/Boms/list',param)
  },
  OrderFormConfirm(param){
    return service.post('/orderForm/manage/confirm',param)
  },


};
