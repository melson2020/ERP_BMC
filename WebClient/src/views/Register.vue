<template>
  <div class="register-container" :style="loginBackground">
    <div class="resgister-box">
      <div class="register-box-header">
        <el-button type="text" class="fl" @click="goLogin">登录</el-button>
        <span class="fz18 fw colorblue">用户注册</span>
      </div>
      <div class="register-box-content">
        <el-form
          ref="registerForm"
          :model="registerInfo"
          label-width="0"
          :rules="registerRules"
        >
          <el-form-item prop="companyName">
            <el-input
              v-model="registerInfo.companyName"
              class="register-input placeHolder-gray"
              placeholder="公司名称"
            ></el-input>
          </el-form-item>
          <el-form-item prop="areaCode">
            <div class="register-row-between">
              <el-select
                class="register-select placeHolder-gray"
                :popper-append-to-body="false"
                popper-class="select-popper"
                filterable
                placeholder="省"
                v-model="registerInfo.provinceCode"
                @change="provinceChanged"
              >
                <el-option
                  v-for="province in provinceList"
                  :key="province.id"
                  :label="province.name"
                  :value="province.code"
                ></el-option>
              </el-select>
              <el-select
                class="register-select placeHolder-gray"
                :popper-append-to-body="false"
                popper-class="select-popper"
                filterable
                placeholder="市"
                v-model="registerInfo.cityCode"
                @change="cityChanged"
              >
                <el-option
                  v-for="city in cityList"
                  :key="city.id"
                  :label="city.name"
                  :value="city.code"
                ></el-option>
              </el-select>
              <el-select
                class="register-select placeHolder-gray"
                :popper-append-to-body="false"
                popper-class="select-popper"
                filterable
                placeholder="区"
                v-model="registerInfo.areaCode"
                @change="areaChanged"
              >
                <el-option
                  v-for="area in areaList"
                  :key="area.id"
                  :label="area.name"
                  :value="area.code"
                ></el-option>
              </el-select>
            </div>
          </el-form-item>
          <el-form-item prop="address">
            <el-input
              v-model="registerInfo.address"
              class="register-input placeHolder-gray"
              placeholder="详细地址"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-input
              v-model="location"
              class="register-input placeHolder-gray"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item prop="communicateName">
            <el-input
              v-model="registerInfo.communicateName"
              class="register-input placeHolder-gray"
              placeholder="联系人员"
            ></el-input>
          </el-form-item>
          <el-form-item prop="phoneNumber">
            <el-input
              v-model="registerInfo.phoneNumber"
              class="register-input placeHolder-gray"
              placeholder="联系电话(号码会被作为管理员账户)"
            ></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input
              v-model="registerInfo.password"
              class="register-input placeHolder-gray"
              type="password"
              placeholder="密码"
            ></el-input>
          </el-form-item>
          <el-form-item prop="confirmPassword">
            <el-input
              v-model="registerInfo.confirmPassword"
              type="password"
              class="register-input placeHolder-gray"
              placeholder="再次输入密码"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-input
              type="textarea"
              v-model="registerInfo.description"
              class="register-input placeHolder-gray"
              placeholder="描述"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              @click="onSubmit('registerForm')"
              class="register-submit-button"
              >点 击 注 册</el-button
            >
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.registerInfo.confirmPassword !== "") {
          this.$refs.registerForm.validateField("confirmPassword");
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.registerInfo.password) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    var validatePhone = (rule, value, callback) => {
      var myreg = /^[1][3,4,5,7,8][0-9]{9}$/;
      if (value === "") {
        callback(new Error("请输入联系电话"));
      } else if (!myreg.test(value)) {
        callback(new Error("请输入正确的号码"));
      } else {
        let phoneNumber = { phoneNumber: value };
        this.CheckPhoneNumebr(phoneNumber)
          .then((res) => {
            if (res.resultStatus == 1) {
              callback();
            } else {
              callback(new Error("该号码已存在"));
            }
          })
          .catch((error) => {
            this.$message.error(error.message);
          });
      }
    };
    return {
      registerInfo: {
        companyName: "",
        provinceCode: "",
        provinceName: "",
        cityCode: "",
        cityName: "",
        areaName: "",
        areaCode: "",
        address: "",
        communicateName: "",
        phoneNumber: "",
        password: "",
        confirmPassword: "",
        description: "",
      },
      loginBackground: {
        backgroundImage:
          "url(" + require("../assets/login-background-1.jpg") + ")",
        backgroundRepeat: "no-repeat",
      },
      registerRules: {
        companyName: [
          { required: true, message: "请输入商户名称", trigger: "blur" },
        ],
        address: [
          { required: true, message: "请填写商户地址", trigger: "blur" },
        ],
        communicateName: [
          { required: true, message: "请填写联系人员", trigger: "blur" },
        ],
        phoneNumber: [
          {
            validator: validatePhone,
            trigger: "blur",
          },
        ],
        password: [{ validator: validatePass, trigger: "blur" }],
        confirmPassword: [{ validator: validatePass2, trigger: "blur" }],
        areaCode: [{ required: true, message: "请选择地址", trigger: "blur" }],
      },
    };
  },
  methods: {
    ...mapActions({
      GetProvinceList: "GetProvinceList",
      GetCityList: "GetCityList",
      GetAreaList: "GetAreaList",
      CheckPhoneNumebr: "CheckPhoneNumebr",
      RegisterCompany: "RegisterCompany",
    }),
    provinceChanged(code) {
      let provinceName = this.provinceList.find((province) => {
        return province.code == code;
      }).name;
      this.registerInfo.provinceName = provinceName;
      this.GetCityList({ provinceCode: code });
    },
    cityChanged(code) {
      let cityName = this.cityList.find((city) => {
        return city.code == code;
      }).name;
      this.registerInfo.cityName = cityName;
      this.GetAreaList({ cityCode: code });
    },
    areaChanged(code) {
      let areaName = this.areaList.find((area) => {
        return area.code == code;
      }).name;
      this.registerInfo.areaName = areaName;
    },
    goLogin() {
      this.$router.push({ path: "/login" });
    },
    onSubmit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let registerInfo = {
            company: this.registerInfo,
            password: this.registerInfo.password,
          };
          this.RegisterCompany(registerInfo)
            .then((res) => {
              if (res.resultStatus == 1) {
                this.$message.success("注册成功");
                this.$router.push({path:'/login'})
              } else {
                this.$message.warning(res.message);
              }
            })
            .catch((err) => {
              this.$message.warning(err.message);
            });
        } else {
          this.$message.warning("请填写准确信息");
        }
      });
    },
  },
  computed: {
    ...mapGetters(["provinceList", "cityList", "areaList"]),
    location() {
      return (
        this.registerInfo.provinceName +
        this.registerInfo.cityName +
        this.registerInfo.areaName +
        this.registerInfo.address
      );
    },
  },
  mounted() {
    this.GetProvinceList();
  },
};
</script>
<style lang="less">
.register-container {
  min-height: 100vh;
  min-width: 100vw;
  height: auto;
  width: auto;
  margin: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  background-size: 100% 100%;
}
.resgister-box {
  width: 800px;
  border-radius: 0.5rem;
  background: rgba(0, 0, 0, 0.4);
  display: flex;
  flex-direction: column;
  height: auto;
  border: 0.1px solid #79bbff;
}
.register-box-header {
  padding: 1rem 2rem;
  letter-spacing: 0.2rem;
}
.register-box-content {
  padding: 2rem;
}
.register-input {
  // font-size: 1.2rem;
  letter-spacing: 0.2rem;
}
.register-input /deep/ .el-input__inner {
  background: rgba(31, 30, 30, 0.1) !important;
  color: white;
}

.placeHolder-gray {
  /deep/ .el-input__inner::-webkit-input-placeholder {
    color: #666;
  }
  /deep/ .el-textarea__inner::-webkit-input-placeholder {
    color: #666;
  }
}

.register-input /deep/ .el-textarea__inner {
  background: rgba(31, 30, 30, 0.1) !important;
  color: white;
}
.register-select /deep/ .el-input__inner {
  background: rgba(31, 30, 30, 0.1) !important;
  color: white;
}

.register-select /deep/ .select-popper {
  left: 0px !important;
  background: #12253e;
  border: 0.1px solid #79bbff;
}

.register-select /deep/ .el-select-dropdown__item {
  color: #efefef;
  letter-spacing: 0.2rem;
}
.register-select /deep/ .el-select-dropdown__item:hover {
  color: #409eff;
  background: #12253e;
}
.register-select /deep/ .hover {
  color: #409eff;
  background: #12253e;
}

.register-select /deep/ .selected {
  color: #409eff;
}

.register-row-between {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
.register-submit-button {
  font-size: 1.3rem;
  width: 100%;
  letter-spacing: 0.5rem;
}
</style>