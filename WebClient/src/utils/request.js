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

  SystemLogin(params){
    return service.post('/system/login',params)
  }

};
