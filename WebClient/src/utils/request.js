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
  ReqSaveCustomer(param){
    return service.post("/customer/save",param)
  },
  ReqQueryCustomerObj(params){
    return service.post("/customer/query",params);
  },
  ReqDisableCustomer(params) {
    return service.post("/customer/disable", params);
  },
};
