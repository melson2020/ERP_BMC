<template>
  <div class="storage-in-main-div">
    <div class="storage-in-header-div">
      <el-row :gutter="5">
        <el-col :span="6">
          <el-button
            class="storage-in-larg-button"
            @click="inventoryInBoundTypeOnClick('PURCHASE')"
            type="success"
            plain
            >采购入库</el-button
          >
        </el-col>
        <el-col :span="6">
          <el-button
            class="storage-in-larg-button"
            @click="inventoryInBoundTypeOnClick('DELEGATE')"
            type="warning"
            plain
            >委外入库</el-button
          >
        </el-col>
        <el-col :span="6">
          <el-button
            class="storage-in-larg-button"
            @click="inventoryInBoundTypeOnClick('OEM')"
            type="danger"
            plain
            >代工入库<span class="fz8"> (客户给料)</span></el-button
          >
        </el-col>
        <el-col :span="6">
          <el-button
            class="storage-in-larg-button"
            @click="inventoryInBoundTypeOnClick('PRODUCE')"
            type="primary"
            plain
            >生产入库</el-button
          >
        </el-col>
      </el-row>
    </div>
    <div class="storage-in-form-area">
      <div class="storage-in-form-div">
        <div class="storage-in-form-header">
          <span class="colorblue">入库单</span>
        </div>
        <el-form
          ref="boundInForm"
          label-position="left"
          :model="editInventoryInbound"
          :rules="rules"
          label-width="80px"
          class="mt40"
        >
          <el-row :gutter="10">
            <el-col :span="12">
              <el-form-item label="日期" prop="createDate">
                <span class="fl">
                  {{ getFullDate(editInventoryInbound.createDate) }}
                </span></el-form-item
              ></el-col
            >
            <el-col :span="12">
              <el-form-item label="人员" prop="createUser">
                <el-input
                  v-model="editInventoryInbound.createUser"
                ></el-input> </el-form-item
            ></el-col>
          </el-row>
          <el-row :gutter="10">
            <el-col :span="12">
              <el-form-item label="来源单号" prop="sourceNo">
                <el-input
                  disabled
                  v-model="editInventoryInbound.sourceNo"
                  placeholder="系统自动生成"
                ></el-input> </el-form-item
            ></el-col>
            <el-col :span="12">
              <el-form-item prop="type" label="类型">
                <el-select
                  v-model="editInventoryInbound.type"
                  placeholder="选择类型"
                  @change="typeChanged"
                  class="w100"
                >
                  <el-option label="采购入库" value="PURCHASE" disabled>
                  </el-option>
                  <el-option label="委外入库" value="DELEGATE" disabled>
                  </el-option>
                  <el-option label="代工入库" value="OEM" disabled> </el-option>
                  <el-option label="生产入库" value="PRODUCE" disabled>
                  </el-option>
                  <el-option label="其他" value="OTHERS"> </el-option>
                </el-select> </el-form-item
            ></el-col>
          </el-row>
          <el-row :gutter="10">
            <el-col :span="12">
              <el-form-item label="批次号" prop="batchNo">
                <el-input
                  disabled
                  placeholder="系统自动生成"
                  v-model="editInventoryInbound.batchNo"
                ></el-input> </el-form-item
            ></el-col>
            <el-col :span="12"> </el-col>
          </el-row>
          <el-form-item label="入库详细" prop="detailVoList">
            <el-table
              :data="editInventoryInbound.detailVoList"
              script
              border
              size="mini"
            >
              <el-table-column prop="materialNo" label="编号">
              </el-table-column>
              <el-table-column prop="materialName" label="名称">
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
                  <span v-else>{{ scope.row.materialName }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="specification" label="规格">
              </el-table-column>
              <el-table-column prop="count" label="数量">
                <template slot-scope="scope">
                  <el-input
                    size="mini"
                    v-if="scope.row.seen"
                    v-model="scope.row.count"
                    placeholder="输入数量"
                  ></el-input>
                  <div v-else class="flex justs">
                    <span>{{ scope.row.count }}{{ scope.row.unit }}</span>
                    <div>
                      <el-popover
                        placement="bottom-end"
                        width="auto"
                        :ref="`packagePopover-${scope.$index}`"
                        trigger="click"
                      >
                        <div class="flex alic">
                          <span class="colorblue fz8">打包</span>
                          <el-select
                            class="ml40"
                            v-model="packageUnit"
                            placeholder="包装单位"
                            clearable
                            @change="packageUnitChanged($event, scope.row)"
                            size="mini"
                          >
                            <el-option
                              v-for="unit in packageUnitList"
                              :key="unit.id"
                              :label="unit.packageUnit"
                              :value="unit.packageUnit"
                            >
                            </el-option>
                          </el-select>
                          <el-input-number
                            class="ml40"
                            size="mini"
                            :min="0"
                            :step="1"
                            v-model="packageCount"
                            :max="packageMaxCount"
                            controls-position="right"
                          ></el-input-number>
                          <el-button
                            type="primary"
                            icon="el-icon-check"
                            class="ml40"
                            circle
                            @click="packageConfirm(scope.$index, scope.row)"
                            size="mini"
                          ></el-button>
                        </div>
                        <el-button
                          type="primary"
                          icon="el-icon-present"
                          class="ml40"
                          circle
                          @click="
                            packageOnClick(scope.row.materialId, scope.row.unit)
                          "
                          plain
                          size="mini"
                          slot="reference"
                        ></el-button>
                      </el-popover>
                      <el-popover
                        placement="bottom-end"
                        width="auto"
                        :ref="`unPackagePopover-${scope.$index}`"
                        trigger="click"
                      >
                        <div class="flex alic">
                          <span class="colorblue fz8">拆包</span>
                          <el-input-number
                            class="ml40 mr40"
                            v-model="unPackageCount"
                            :max="unPackageMaxCount"
                            :min="0"
                            size="mini"
                            controls-position="right"
                          ></el-input-number>
                          {{ scope.row.unit }} 至
                          <el-select
                            class="ml40"
                            v-model="unPackageUnit"
                            placeholder="包装单位"
                            size="mini"
                          >
                            <el-option
                              v-for="unit in unPackageUnitList"
                              :key="unit.id"
                              :label="unit.convertUnit"
                              :value="unit.convertUnit"
                            >
                            </el-option>
                          </el-select>
                          <el-button
                            type="primary"
                            icon="el-icon-check"
                            class="ml40"
                            circle
                            @click="unPackageConfirm(scope.$index, scope.row)"
                            size="mini"
                          ></el-button>
                        </div>
                        <el-button
                          type="warning"
                          icon="el-icon-scissors"
                          class="ml40"
                          @click="unPackageOnClick(scope.row)"
                          circle
                          plain
                          size="mini"
                          slot="reference"
                        ></el-button>
                      </el-popover>
                    </div>
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="count" label="仓库">
                <template slot-scope="scope">
                  <el-select
                    v-model="scope.row.storageCode"
                    placeholder="选择仓库"
                    disabled
                    size="mini"
                  >
                    <el-option
                      v-for="storage in storageList"
                      :label="storage.name"
                      :value="storage.storageCode"
                      :key="storage.storageCode"
                    ></el-option>
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column prop="count" label="操作" width="120px">
                <template slot-scope="scope">
                  <el-button
                    type="primary"
                    v-if="!scope.row.seen"
                    size="mini"
                    icon="el-icon-edit"
                    @click="editOnClick(scope.row)"
                    circle
                    :disabled="editDisable"
                  ></el-button>
                  <el-button
                    type="success"
                    icon="el-icon-check"
                    v-if="scope.row.seen"
                    size="mini"
                    :disabled="editDisable"
                    @click="addConfirmOnClick(scope.row)"
                    circle
                  ></el-button>
                  <el-button
                    type="danger"
                    icon="el-icon-delete"
                    size="mini"
                    :disabled="editDisable"
                    @click="removeOnClick(scope.$index)"
                    circle
                  ></el-button>
                </template>
              </el-table-column>
            </el-table>
            <el-button
              icon="el-icon-plus"
              v-if="editInventoryInbound.type == 'OTHERS'"
              plain
              @click="addInboundDetail"
              class="add-storage-in-deail mt40"
              >添加详细</el-button
            >
          </el-form-item>
          <el-form-item label="入库备注">
            <el-input
              type="textarea"
              placeholder="请输入内容"
              maxlength="50"
              show-word-limit
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitOnClick('boundInForm')"
              >立即创建</el-button
            >
            <el-button @click="cancelOnClick('boundInForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <el-dialog title="入库选择" :visible.sync="dialogVisible" width="60%">
      <div v-if="inventoryInboundType == 'DELEGATE'">
        <span class="colorblue fl mb40">委外单</span>
        <el-table key="4" script border size="mini" :data="delegateInBoundList">
          <el-table-column prop="ticketNo" label="单号"> </el-table-column>
          <el-table-column prop="customerName" label="客户"> </el-table-column>
          <el-table-column prop="supplyName" label="供货商"> </el-table-column>
          <el-table-column prop="delegateDate" label="委外日期">
            <template slot-scope="scope">
              <span>{{ getFullDate(scope.row.delegateDate) }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="" label="" width="80px">
            <template slot-scope="scope">
              <el-button
                @click="loadInBound(scope.row, 'DELEGATE')"
                type="success"
                icon="el-icon-check"
                size="mini"
                plain
                circle
              ></el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div v-if="inventoryInboundType == 'PURCHASE'">
        <span class="colorblue fl mb40">采购单</span>
        <el-table key="1" script border size="mini" :data="purchaseInBoundList">
          <el-table-column prop="poNo" label="编号"> </el-table-column>
          <el-table-column prop="createDate" label="创建日期">
            <template slot-scope="scope">
              <span>{{ getFullDate(scope.row.createDate) }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="amount" label="总价"> </el-table-column>
          <el-table-column label="" width="80px">
            <template slot-scope="scope">
              <el-button
                @click="loadInBound(scope.row, 'PURCHASE')"
                type="success"
                icon="el-icon-check"
                size="mini"
                plain
                circle
              ></el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div v-if="inventoryInboundType == 'OEM'">
        <span class="colorblue fl mb40">代工给料</span>
        <el-table
          key="2"
          script
          border
          size="mini"
          :data="pickingTicketInBoundList"
        >
          <el-table-column prop="ticketNo" label="编号"> </el-table-column>
          <el-table-column prop="type" label="隶属"> </el-table-column>
          <el-table-column prop="customerMaterialFlag" label="客户给料">
          </el-table-column>
          <el-table-column label="" width="80px">
            <template slot-scope="scope">
              <el-button
                @click="loadInBound(scope.row, 'OEM')"
                type="success"
                icon="el-icon-check"
                size="mini"
                plain
                circle
              ></el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div v-if="inventoryInboundType == 'PRODUCE'">
        <span class="colorblue fl mb40">生产入库</span>
        <el-table key="3" script border size="mini" :data="produceInBoundList">
          <el-table-column prop="planNo" label="编号"> </el-table-column>
          <el-table-column prop="customerName" label="客户"> </el-table-column>
          <el-table-column prop="orderFormNo" label="订单号"> </el-table-column>
          <el-table-column label="" width="80px">
            <template slot-scope="scope">
              <el-button
                @click="loadInBound(scope.row, 'PRODUCE')"
                type="success"
                icon="el-icon-check"
                size="mini"
                plain
                circle
              ></el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
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
      editInventoryInbound: {
        detailVoList: [],
        type: "",
      },
      dialogVisible: false,
      inventoryInboundType: "",
      delegateInBoundList: [],
      produceInBoundList: [],
      pickingTicketInBoundList: [],
      purchaseInBoundList: [],

      packageUnitList: [],
      packageUnit: "",
      packageMaxCount: 0,
      packageCount: 0,
      productList: [],

      unPackageUnitList: [],
      unPackageUnit: "",
      unPackageCount: 0,
      unPackageMaxCount: 0,
      rules: {
        type: [{ required: true, message: "请选择类型", trigger: "blur" }],
        detailVoList: [
          { required: true, message: "填入入库详细", trigger: "blur" },
        ],
      },
    };
  },

  methods: {
    ...mapActions({
      FindDelegateInBoundList: "FindDelegateInBoundList",
      FindPickingTicketInBoundList: "FindPickingTicketInBoundList",
      FindProducePlanList: "FindProducePlanList",
      CreateInventoryInBound: "CreateInventoryInBound",
      GetStorageList: "GetStorageList",
      GetPackageUnitList: "GetPackageUnitList",
      GetUnPackageUnitList: "GetUnPackageUnitList",
      SaveInventoryInBound: "SaveInventoryInBound",
      GetPurchaseOrderListByState: "GetPurchaseOrderListByState",
      QueryProductListBySearchValue: "QueryProductListBySearchValue",
    }),
    inventoryInBoundTypeOnClick(type) {
      this.inventoryInboundType = type;
      switch (type) {
        case "DELEGATE":
          this.findDelegateInBoundList();
          break;
        case "PRODUCE":
          this.findProductInBoundList();
          break;
        case "OEM":
          this.findPickingInBoundList();
          break;
        case "PURCHASE":
          this.findPurchaseInBoundList();
          break;
      }
    },

    findDelegateInBoundList() {
      this.FindDelegateInBoundList()
        .then((res) => {
          if (res.resultStatus == 1) {
            this.dialogVisible = true;
            this.delegateInBoundList = res.data;
          } else {
            this.$message.warning(res.message);
          }
        })
        .catch((err) => {
          this.$message.error(err.message);
        });
    },

    findProductInBoundList() {
      this.FindProducePlanList({ state: "2" })
        .then((res) => {
          if (res.resultStatus == 1) {
            this.dialogVisible = true;
            this.produceInBoundList = res.data;
          } else {
            this.$message.warning(res.message);
          }
        })
        .catch((err) => {
          this.$message.error(err.message);
        });
    },

    findPickingInBoundList() {
      this.FindPickingTicketInBoundList()
        .then((res) => {
          if (res.resultStatus == 1) {
            this.dialogVisible = true;
            this.pickingTicketInBoundList = res.data;
          } else {
            this.$message.warning(res.message);
          }
        })
        .catch((err) => {
          this.$message.error(err.message);
        });
    },

    findPurchaseInBoundList() {
      this.GetPurchaseOrderListByState({ state: "CREATE" })
        .then((res) => {
          if (res.resultStatus == 1) {
            this.dialogVisible = true;
            this.purchaseInBoundList = res.data;
          } else {
            this.$message.warning(res.message);
          }
        })
        .catch((err) => {
          this.$message.error(err.message);
        });
    },

    loadInBound(row, type) {
      this.CreateInventoryInBound({ ticketId: row.id, ticketType: type })
        .then((res) => {
          if (res.resultStatus == 1) {
            this.editInventoryInbound = res.data;
            this.dialogVisible = false;
            this.editDisable = true;
          } else {
            this.$message.warning(res.message);
          }
        })
        .catch((err) => {
          this.$message.error(err.message);
        });
    },

    packageOnClick(productId, unit) {
      (this.packageUnit = ""),
        (this.packageCount = 0),
        (this.packageMaxCount = 0),
        (this.packageUnitList = []);
      this.GetPackageUnitList({ productId: productId, unit: unit })
        .then((res) => {
          if (res.resultStatus == 1) {
            this.packageUnitList = res.data;
          } else {
            this.$message.warning(res.message);
          }
        })
        .catch((err) => {
          this.$message.error(err.message);
        });
    },

    packageUnitChanged(unit, row) {
      var selectUnit = this.packageUnitList.find((item) => {
        return item.packageUnit == unit;
      });
      this.packageMaxCount = Math.floor(row.count / selectUnit.convertCount);
      this.packageCount = this.packageMaxCount;
    },

    packageConfirm(index, row) {
      if (this.packageCount <= 0) {
        this.$message.info("数量不足无法打包");
        return;
      }
      if (row.count < packageTotalCount) {
        this.$message.info("打包数量大于已有数量,无法打包");
        return;
      }
      var selectUnit = this.packageUnitList.find((item) => {
        return item.packageUnit == this.packageUnit;
      });
      var packageTotalCount = this.$my.NumberMul(
        selectUnit.convertCount,
        this.packageCount
      );
      let existSameUnitRow = this.editInventoryInbound.detailVoList.find(
        (item) => {
          return (
            item.materialId == row.materialId && item.unit == this.packageUnit
          );
        }
      );
      //部分打包
      if (row.count > packageTotalCount) {
        if (existSameUnitRow) {
          existSameUnitRow.count = this.$my.NumberAdd(
            existSameUnitRow.count,
            this.packageCount
          );
        } else {
          let newRow = {
            count: this.packageCount,
            latestPrice: row.latestPrice,
            materialId: row.materialId,
            materialName: row.materialName,
            materialNo: row.materialNo,
            specification: row.specification,
            storageCode: row.storageCode,
            unit: this.packageUnit,
          };
          this.editInventoryInbound.detailVoList.push(newRow);
        }
        row.count = this.$my.NumberSub(row.count, packageTotalCount);
      } else {
        //完全打包
        if (existSameUnitRow) {
          existSameUnitRow.count = this.$my.NumberAdd(
            existSameUnitRow.count,
            this.packageCount
          );
          this.editInventoryInbound.detailVoList.splice(index, 1);
        } else {
          row.count = this.packageCount;
          row.unit = this.packageUnit;
        }
      }
      this.$refs[`packagePopover-${index}`].doClose();
    },

    unPackageOnClick(row) {
      (this.unPackageUnitList = []),
        (this.unPackageUnit = ""),
        (this.unPackageCount = 0),
        (this.unPackageMaxCount = 0),
        this.GetUnPackageUnitList({ productId: row.materialId, unit: row.unit })
          .then((res) => {
            if (res.resultStatus == 1) {
              this.unPackageUnitList = res.data;
              this.unPackageCount = parseInt(row.count);
              this.unPackageMaxCount = parseInt(row.count);
            } else {
              this.$message.warning(res.message);
            }
          })
          .catch((err) => {
            this.$message.error(err.message);
          });
    },

    unPackageConfirm(index, row) {
      if (this.unPackageCount <= 0 || this.unPackageUnit == "") {
        this.$message.info("拆包信息不足，无法拆包");
        return;
      }
      if (row.count < this.unPackageCount) {
        this.$message.info("拆包数量大于已有数量，无法拆包");
        return;
      }
      var selectUnit = this.unPackageUnitList.find((item) => {
        return item.convertUnit == this.unPackageUnit;
      });

      let unPackageTotalCount = this.$my.NumberMul(
        selectUnit.convertCount,
        this.unPackageCount
      );
      //查询是否存在相同单位的记录
      let existSameUnitRow = this.editInventoryInbound.detailVoList.find(
        (item) => {
          return (
            item.materialId == row.materialId && item.unit == this.unPackageUnit
          );
        }
      );
      //完全拆包
      if (row.count == this.unPackageCount) {
        if (existSameUnitRow) {
          existSameUnitRow.count = this.$my.NumberAdd(
            existSameUnitRow.count,
            unPackageTotalCount
          );
          this.editInventoryInbound.detailVoList.splice(index, 1);
        } else {
          row.count = unPackageTotalCount;
          row.unit = selectUnit.convertUnit;
        }
      } else {
        //部分拆包
        if (existSameUnitRow) {
          existSameUnitRow.count = this.$my.NumberAdd(
            existSameUnitRow.count,
            unPackageTotalCount
          );
        } else {
          var newRow = {
            latestPrice: row.latestPrice,
            materialId: row.materialId,
            materialName: row.materialName,
            materialNo: row.materialNo,
            specification: row.specification,
            storageCode: row.storageCode,
            count: unPackageTotalCount,
            unit: this.unPackageUnit,
          };
          this.editInventoryInbound.detailVoList.push(newRow);
        }
        row.count = this.$my.NumberSub(row.count, this.unPackageCount);
      }
      this.$refs[`unPackagePopover-${index}`].doClose();
    },

    typeChanged(type) {
      if (type == "OTHERS") {
        this.$refs["boundInForm"].resetFields();
        this.editInventoryInbound.createDate = new Date();
        this.editInventoryInbound.createUser = this.userInfo.id;
        this.editInventoryInbound.type = "OTHERS";
        this.editDisable = false;
      }
    },

    addInboundDetail() {
      var addObj = {
        materialNo: "",
        materialName: "",
        specification: "",
        count: "",
        latestPrice: "",
        unit: "",
        storageCode: "",
        materialId: "",
        seen: true,
      };
      this.editInventoryInbound.detailVoList.push(addObj);
    },

    getFullDate(time) {
      if (time) {
        return new Date(time).format("yyyy-MM-dd");
      } else {
        return "";
      }
    },

    submitOnClick(formName) {
      this.$refs[formName].validate((valid) => {
        if (
          this.editInventoryInbound.detailVoList.filter((item) => {
            return item.seen;
          }).length > 0
        ) {
          this.$message.warning("请完善入库信息");
        } else {
          if (valid) {
            this.SaveInventoryInBound(this.editInventoryInbound)
              .then((res) => {
                if (res.resultStatus == 1) {
                  this.$message.success("入库成功");
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
    cancelOnClick(formName) {
      this.$refs[formName].resetFields();
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
      let item = this.productList.find((p) => {
        return p.id == productId;
      });
      row.materialNo = item.productNo;
      row.materialName = item.name;
      (row.specification = item.specification), (row.count = 0);
      row.latestPrice = item.salesPrice;
      row.unit = item.unit;
      row.storageCode = item.storageCode;
      row.materialId = item.id;
    },

    removeOnClick(index) {
      this.editInventoryInbound.detailVoList.splice(index, 1);
    },
    addConfirmOnClick(row) {
      if (row.materialId == "" || row.count <= 0) {
        this.$message.warning("请完善入库信息");
        return;
      }
      row.seen = false;
    },
    editOnClick(row) {
      row.seen = true;
    },
  },
  computed: {
    ...mapGetters(["storageList", "userInfo"]),
  },

  mounted() {
    this.GetStorageList();
  },
};
</script>
<style>
.storage-in-main-div {
  padding: 5px 10px;
}
.storage-in-larg-button {
  width: 100%;
  height: 60px;
  font-size: 1.1rem;
}
.storage-in-form-area {
  padding: 20px 0px;
  display: flex;
  justify-content: center;
}
.storage-in-form-div {
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  width: 100%;
  border: 1px solid lightgray;
  padding: 20px;
}
.storage-in-form-header {
  height: 40px;
  align-items: center;
  line-height: 40px;
  font-size: 1.3rem;
  letter-spacing: 0.2rem;
  font-weight: 600;
}
.add-storage-in-deail {
  width: 100%;
  color: #259dff;
  border: #dcdfe6 dashed 1px !important;
}
</style>