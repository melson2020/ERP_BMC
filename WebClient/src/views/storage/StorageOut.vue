<template>
  <div class="storage-out-main">
    <div class="storage-in-header-div">
      <el-row :gutter="5">
        <el-col :span="6">
          <el-button
            class="storage-out-larg-button"
            type="primary"
            @click="outBoundTypeOnClick('PICKING')"
            >领料</el-button
          >
        </el-col>
        <el-col :span="6">
          <el-button
            class="storage-out-larg-button"
            type="success"
            @click="outBoundTypeOnClick('DELIVERY')"
            >发货</el-button
          >
        </el-col>
        <el-col :span="6">
          <!-- <el-button class="storage-out-larg-button" type="warning"
            >其他</el-button
          > -->
        </el-col>
        <el-col :span="6"> </el-col>
      </el-row>
    </div>
    <div class="storage-out-form-area">
      <div class="storage-out-form-div">
        <div class="storage-out-form-header">
          <span class="colorblue">出库单</span>
        </div>
        <el-form
          ref="boundOutForm"
          label-position="left"
          :model="editInventoryOutbound"
          label-width="80px"
          :rules="rules"
          class="mt40"
        >
          <el-row :gutter="10">
            <el-col :span="12">
              <el-form-item label="日期" prop="createDate">
                <span class="fl">
                  {{ getFullDate(editInventoryOutbound.createDate) }}
                </span></el-form-item
              ></el-col
            >
            <el-col :span="12">
              <el-form-item label="人员" prop="createUser">
                <el-input
                  v-model="editInventoryOutbound.createUser"
                ></el-input> </el-form-item
            ></el-col>
          </el-row>
          <el-row :gutter="10">
            <el-col :span="12">
              <el-form-item label="来源单号" prop="formNo">
                <el-input
                  disabled
                  placeholder="系统生成"
                  v-model="editInventoryOutbound.sourceNo"
                ></el-input> </el-form-item
            ></el-col>
            <el-col :span="12">
              <el-form-item label="类型" prop="type">
                <el-select
                  v-model="editInventoryOutbound.type"
                  placeholder="选择类型"
                  class="w100"
                  @change="typeChanged"
                >
                  <el-option label="领料出库" value="PICKING" disabled>
                  </el-option>
                  <el-option label="发货出库" value="DELIVERY" disabled>
                  </el-option>
                  <el-option label="其他" value="OTHERS"> </el-option>
                </el-select> </el-form-item
            ></el-col>
          </el-row>
          <el-form-item label="出库详细" prop="detailVoList">
            <el-table
              :data="editInventoryOutbound.detailVoList"
              script
              border
              size="mini"
            >
              <el-table-column prop="materialNo" label="编号">
              </el-table-column>
              <el-table-column prop="name" label="名称">
                <template slot-scope="scope">
                  <el-select
                    v-if="scope.row.seen"
                    v-model="scope.row.materialId"
                    size="mini"
                    remote
                    filterable
                    reserve-keyword
                    placeholder="请输入名称"
                    @change="onSelect($event, scope.row)"
                    :remote-method="remoteMethod"
                  >
                    <el-option
                      v-for="product in productList"
                      :key="product.id"
                      :label="product.name"
                      :value="product.id"
                    >
                    </el-option>
                  </el-select>
                  <span v-else>{{ scope.row.name }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="specification" label="规格">
              </el-table-column>
              <el-table-column
                v-if="editInventoryOutbound.type != 'OTHERS'"
                prop="count"
                label="应出数量"
              >
                <template slot-scope="scope">
                  <span>{{ scope.row.count }}{{ scope.row.unit }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="batchNo" label="批次号"> </el-table-column>
              <el-table-column prop="outCount" label="出库量">
                <template slot-scope="scope">
                  <el-popover
                    v-if="scope.row.seen"
                    placement="right"
                    trigger="click"
                  >
                    <div class="flex alic">
                      <span>单位</span>
                      <el-select
                        class="ml40"
                        v-model="selectStorageDetailId"
                        size="mini"
                        @change="outCountChanged($event, scope.row)"
                      >
                        <el-option
                          v-for="storage in storageDetailList"
                          :key="storage.id"
                          :label="storage.unit"
                          :value="storage.id"
                        ></el-option>
                      </el-select>
                      <el-input-number
                        class="ml40 mr40"
                        v-model="scope.row.outCount"
                        :max="outCountMax"
                        :min="0"
                        size="mini"
                        controls-position="right"
                      ></el-input-number>
                      <el-button
                        size="mini"
                        @click="outCountConfirm(scope.$index, scope.row)"
                        type="warning"
                        class="ml40"
                        icon="el-icon-check"
                        circle
                      ></el-button>
                      <span class="ml40 coloryellow">{{
                        selectStorageDetailDes
                      }}</span>
                    </div>
                    <el-button
                      slot="reference"
                      size="mini"
                      @click="setOutCount(scope.row.materialId)"
                      type="primary"
                      plain
                      >配置</el-button
                    >
                  </el-popover>

                  <span v-else
                    >{{ scope.row.outCount }}{{ scope.row.outCountUnit }}</span
                  >
                </template>
              </el-table-column>
              <el-table-column prop="unPackageCount" label="拆包情况">
                <template slot-scope="scope">
                  <span class="coloryellow" v-if="scope.row.unPackageCount > 0"
                    >拆{{ scope.row.unPackageCount
                    }}{{ scope.row.unPackageCountUnit }}取{{
                      scope.row.unPackagePickCount
                    }}{{ scope.row.unit }}</span
                  >
                </template>
              </el-table-column>
              <el-table-column prop="storageDes" label="库存">
                <template slot-scope="scope">
                  <span>{{ scope.row.storageDes }}</span
                  ><el-tag
                    class="ml40"
                    v-if="scope.row.storageFlag == 0"
                    type="danger"
                    size="mini"
                    >不足</el-tag
                  >
                </template>
              </el-table-column>
              <el-table-column prop="count" label="操作" width="120px">
                <template slot-scope="scope">
                  <el-button
                    type="danger"
                    icon="el-icon-delete"
                    :disabled="editDisable"
                    size="mini"
                    @click="removeOnClick(scope.$index)"
                    circle
                  ></el-button>
                </template>
              </el-table-column>
            </el-table>
            <el-button
              icon="el-icon-plus"
              v-if="editInventoryOutbound.type == 'OTHERS'"
              plain
              @click="addOutboundDetail"
              class="add-storage-out-deail mt40"
              >添加详细</el-button
            >
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitOnClick('boundOutForm')"
              >提交</el-button
            >
            <el-button icon="el-icon-goods" @click="purchaseOnClick"
              >采购</el-button
            ></el-form-item
          >
        </el-form>
      </div>
    </div>
    <el-dialog title="出库选择" :visible.sync="detailDialogVisible" width="70%">
      <div v-if="inventoryOutboundType == 'PICKING'">
        <span class="colorblue fl mb40">领料出库</span>
        <el-table
          key="1"
          script
          border
          size="mini"
          :data="pickingTicketOutBoundList"
        >
          <el-table-column prop="ticketNo" label="单号"> </el-table-column>
          <el-table-column prop="type" label="类型"> </el-table-column>
          <el-table-column prop="orderFormNo" label="客户给料标记">
            <template slot-scope="scope">
              <span
                v-if="scope.row.customerMaterialFlag == 'Y'"
                class="colorred"
                >{{ scope.row.customerMaterialFlag }}</span
              >
              <span v-else class="colorgreen">{{
                scope.row.customerMaterialFlag
              }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="inboundTicketNo" label="入库单号">
          </el-table-column>
          <el-table-column label="" width="80px">
            <template slot-scope="scope">
              <el-button
                type="primary"
                @click="loadOutBound(scope.row, 'PICKING')"
                icon="el-icon-check"
                size="mini"
                circle
              ></el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <div v-if="inventoryOutboundType == 'DELIVERY'">
        <span class="colorblue fl mb40">发货出库</span>
        <el-table
          key="2"
          script
          border
          size="mini"
          :data="deliveryOutBoundList"
        >
          <el-table-column prop="ticketNo" label="编号"> </el-table-column>
          <el-table-column prop="customerName" label="客户"> </el-table-column>
          <el-table-column prop="orderFormNo" label="订单号"> </el-table-column>
          <el-table-column prop="deliveryDate" label="发货日">
            <template slot-scope="scope">
              <span class="fl">
                {{ getFullDate(scope.row.deliveryDate) }}
              </span>
            </template>
          </el-table-column>
          <el-table-column label="" width="80px">
            <template slot-scope="scope">
              <el-button
                type="success"
                icon="el-icon-check"
                @click="loadOutBound(scope.row, 'DELIVERY')"
                size="mini"
                circle
              ></el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-dialog>
    <el-dialog title="采购单" :visible.sync="purchaseDialogVisible" width="70%">
      <el-form ref="form" :model="storagePurchase" label-width="80px">
        <el-form-item label="采购类型">
          <el-input v-model="storagePurchase.type"></el-input>
        </el-form-item>
        <el-form-item label="采购来源">
          <el-input v-model="storagePurchase.pickingNo"></el-input>
        </el-form-item>
          <el-form-item label="采购详细">
           <el-table
          key="3"
          script
          border
          size="mini"
          :data="storagePurchase.purchaseDetailList"
        >
          <el-table-column prop="materialNo" label="编号"> </el-table-column>
          <el-table-column prop="materialName" label="名称"> </el-table-column>
          <el-table-column prop="specification" label="规格"> </el-table-column>
          <el-table-column prop="count" label="数量"> 
             <template slot-scope="scope">
                  <span>{{ scope.row.count }}{{ scope.row.countUnit }}</span>
                </template>
          </el-table-column>
    
        </el-table>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">立即创建</el-button>
          <el-button>取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
export default {
  data() {
    return {
      editDisable: true,
      selectStorageDetailId: "",
      selectStorageDetailDes: "",
      detailDialogVisible: false,
      purchaseDialogVisible: false,
      inventoryOutboundType: "",
      editInventoryOutbound: {
        detailVoList: [],
      },
      storageDetailList: [],
      storagePurchase: {
        purchaseDetailList: [],
      },

      pickingTicketOutBoundList: [],
      deliveryOutBoundList: [],
      productList: [],
      outCountMax: 0,
      rules: {
        type: [{ required: true, message: "请选择类型", trigger: "blur" }],
        detailVoList: [
          { required: true, message: "填入入库详细", trigger: "blur" },
        ],
      },
    };
  },

  computed: {
    ...mapGetters(["userInfo", "storagePurchaseList"]),
  },

  methods: {
    ...mapActions({
      FindPickingTicketOutBoundList: "FindPickingTicketOutBoundList",
      CreateInventoryOutBound: "CreateInventoryOutBound",
      QueryProductListBySearchValue: "QueryProductListBySearchValue",
      FindStorageDetaiListByProductId: "FindStorageDetaiListByProductId",
      CreateOutBoundBatchInfo: "CreateOutBoundBatchInfo",
      GetOrderDeliveryByState: "GetOrderDeliveryByState",
      SaveInventoryOutBound: "SaveInventoryOutBound",
      AddToStoragePurchaseList: "AddToStoragePurchaseList",
    }),
    getFullDate(time) {
      if (time) {
        return new Date(time).format("yyyy-MM-dd");
      } else {
        return "";
      }
    },
    outBoundTypeOnClick(type) {
      this.inventoryOutboundType = type;
      this.detailDialogVisible = !this.detailDialogVisible;
      switch (type) {
        case "PICKING":
          this.findPickingTicketOutBoundList();
          break;
        case "DELIVERY":
          this.findOrderDeliveryOutBoundList();
          break;
      }
    },

    findPickingTicketOutBoundList() {
      this.FindPickingTicketOutBoundList()
        .then((res) => {
          if (res.resultStatus == 1) {
            this.pickingTicketOutBoundList = res.data;
          } else {
            this.$message.warning(res.message);
          }
        })
        .catch((err) => {
          this.$message.error(err.message);
        });
    },

    findOrderDeliveryOutBoundList() {
      this.GetOrderDeliveryByState({ state: "2" })
        .then((res) => {
          if (res.resultStatus == 1) {
            this.deliveryOutBoundList = res.data;
          } else {
            this.$message.warning(res.message);
          }
        })
        .catch((err) => {
          this.$message.error(err.message);
        });
    },

    typeChanged(type) {
      if (type == "OTHERS") {
        this.editDisable = false;
        this.$refs["boundOutForm"].resetFields();
        this.editInventoryOutbound.createDate = new Date();
        this.editInventoryOutbound.createUser = this.userInfo.id;
        this.editInventoryOutbound.type = "OTHERS";
      }
    },

    loadOutBound(row, type) {
      this.CreateInventoryOutBound({ ticketId: row.id, ticketType: type })
        .then((res) => {
          if (res.resultStatus == 1) {
            this.editInventoryOutbound = res.data;
            this.detailDialogVisible = false;
            this.editDisable = true;
          } else {
            this.$message.warning(res.message);
          }
        })
        .catch((err) => {
          this.$message.error(err.message);
        });
    },

    remoteMethod(query) {
      if (query !== "") {
        this.QueryProductListBySearchValue({
          searchValue: query,
        })
          .then((res) => {
            if (res.resultStatus == 1) {
              this.productList = res.data;
            }
          })
          .catch(() => {});
      } else {
        return;
      }
    },

    onSelect(productId, row) {
      if (productId == "") return;
      let item = this.productList.find((p) => {
        return p.id == productId;
      });
      if (
        this.editInventoryOutbound.detailVoList.filter((item) => {
          return item.materialId == productId;
        }).length > 1
      ) {
        this.$message.warning("已存在相同出库记录，如要编辑请删除，重新配置");
        row.materialId = "";
        row.name = "";
        row.specification = "";
        row.materialNo = "";
        return;
      }
      row.materialNo = item.productNo;
      row.name = item.name;
      (row.specification = item.specification), (row.outCount = 0);
      row.latestPrice = item.salesPrice;
      row.outCountUnit = item.unit;
      row.storageCode = item.storageCode;
      row.materialId = item.id;
    },

    addOutboundDetail() {
      if (
        this.editInventoryOutbound.detailVoList.filter((item) => {
          return item.seen;
        }).length > 0
      ) {
        this.$message.warning("请先完成配置");
        return;
      }
      let addObj = {
        materialNo: "",
        name: "",
        specification: "",
        count: "",
        unit: "",
        batchNo: "",
        outCount: "",
        outCountUnit: "",
        storageCode: "",
        materialId: "",
        seen: true,
      };
      this.editInventoryOutbound.detailVoList.push(addObj);
    },

    setOutCount(materialId) {
      this.storageDetailList = [];
      this.selectStorageDetailId = "";
      this.outCountMax = 0;
      this.selectStorageDetailDes = "";
      if (materialId == "") return;
      this.FindStorageDetaiListByProductId({ productId: materialId })
        .then((res) => {
          if (res.resultStatus == 1) {
            this.storageDetailList = res.data;
          } else {
            this.$message.warning(res.message);
          }
        })
        .catch((err) => {
          this.$message.error(err.message);
        });
    },
    outCountChanged(id, row) {
      let selectStorage = this.storageDetailList.find((item) => {
        return item.id == id;
      });
      this.selectStorageDetailDes = selectStorage.count + selectStorage.unit;
      this.outCountMax = selectStorage.count;
      row.outCountUnit = selectStorage.unit;
    },
    outCountConfirm(index, row) {
      if (row.outBoundTypeOnClick <= 0 || row.outCountUnit == "") return;
      this.CreateOutBoundBatchInfo(row)
        .then((res) => {
          if (res.resultStatus == 1) {
            console.log(res.data);
            this.editInventoryOutbound.detailVoList.splice(index, 1);
            res.data.map((item) => {
              this.editInventoryOutbound.detailVoList.push(item);
            });
          } else {
            this.$message.warning(res.message);
          }
        })
        .catch((err) => {
          this.$message.error(err.message);
        });
    },

    removeOnClick(index) {
      this.editInventoryOutbound.detailVoList.splice(index, 1);
    },
    editOnClick(row) {
      row.seen = true;
    },

    purchaseOnClick() {
      this.storagePurchase = {
        purchaseDetailList: [],
      };
      if (
        this.editInventoryOutbound.detailVoList.filter((item) => {
          return item.storageFlag == 0;
        }).length <= 0
      )
        return;
      this.purchaseDialogVisible = !this.purchaseDialogVisible;
      this.editInventoryOutbound.detailVoList.map((item) => {
        if (item.storageFlag == 0) {
          var obj = {
            materialNo: item.materialNo,
            materialName: item.name,
            specification: item.specification,
            count: this.$my.NumberSub(item.count, item.storageCount),
            countUnit: item.unit,
          };
          this.storagePurchase.purchaseDetailList.push(obj);
        }
      });
      this.storagePurchase.type = "PLAN";
      this.storagePurchase.pickingNo = this.editInventoryOutbound.sourceNo;
    },

    submitOnClick(formName) {
      this.$refs[formName].validate((valid) => {
        if (
          this.editInventoryOutbound.detailVoList.filter((item) => {
            return item.storageFlag == 0;
          }).length > 0
        ) {
          this.$message.warning("库存不足,不能出库");
        } else {
          if (valid) {
            this.SaveInventoryOutBound(this.editInventoryOutbound)
              .then((res) => {
                if (res.resultStatus == 1) {
                  this.$message.success("出库成功");
                  this.$refs[formName].resetFields();
                } else {
                  this.$message.warning(res.message);
                }
              })
              .catch((err) => {
                this.$message.error(err.message);
              });
          }
        }
      });
    },
  },
};
</script>
<style>
.storage-out-main {
  padding: 10px;
}
.storage-out-larg-button {
  width: 100%;
  height: 60px;
  font-size: 1.1rem;
  letter-spacing: 0.2rem;
}
.storage-out-form-area {
  padding: 20px 0px;
  display: flex;
  justify-content: center;
}
.storage-out-form-div {
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  width: 100%;
  border: 1px solid lightgray;
  padding: 20px;
}
.storage-out-form-header {
  height: 40px;
  align-items: center;
  line-height: 40px;
  font-size: 1.3rem;
  letter-spacing: 0.2rem;
  font-weight: 600;
}
.add-storage-out-deail {
  width: 100%;
  color: #259dff;
  border: #dcdfe6 dashed 1px !important;
}
</style>