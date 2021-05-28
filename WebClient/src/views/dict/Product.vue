<template>
  <div class="product-container">
    <div class="product-header">
    <el-input
      class="product-fliter-input"
      size="small"
      placeholder="搜索商品"
      v-model="searchContent"
      suffix-icon="el-icon-search"
    ></el-input>
    <el-button type="primary" size="small" @click="resetForm('productAddForm')">新建商品</el-button>
    </div>

    <div class="product-content">
      <el-table :data="productListPageShow" 
          border="" stripe           
          :header-row-style="{height:'40px' ,'align':'center'}"
          :row-style="{height:'40px'}"
          style="width: 100%">
        <el-table-column prop="productNo" label="商品代码" width="100px"></el-table-column>
        <el-table-column prop="name" label="商品名称"> </el-table-column>
        <el-table-column prop="supplyName" label="供应商"> </el-table-column>
        <el-table-column prop="category" label="商品种类" width="100px"> </el-table-column>
        <el-table-column prop="specification" label="规格型号" width="120px"> </el-table-column>
        <el-table-column prop="unit" label="计量单位" width="80px"> </el-table-column>
        <el-table-column label="重量" width="90px">
          <template slot-scope="scope"><span>{{scope.row.weight}}{{scope.row.weightUnit}}</span>
          </template>
        </el-table-column>
        <el-table-column label="体积" width="90px"> 
          <template slot-scope="scope"><span>{{scope.row.volume}}{{scope.row.volumeUnit}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="salesPrice" label="价格" width="100px"> </el-table-column>
        <el-table-column prop="storageName" label="仓库" width="120px"></el-table-column>
        <el-table-column prop="expireDate" label="失效日期" width="120px"> 
          <template slot-scope="scope">
            <span>{{getFullTime(scope.row.expireDate) }}</span>
          </template>
        </el-table-column>




        <el-table-column prop="" label="操作" width="130px">
          <template slot-scope="scope">
            <el-tooltip effect="light" content="修改商品" placement="top">
              <el-button size="mini" @click="handleEdit(scope.$index,scope.row)" plain circle type="primary" icon="el-icon-edit"/>
            </el-tooltip>
            <el-tooltip effect="light" content="创建商品Bom" placement="top">
              <el-button size="mini" @click="handleCreateBoM(scope.$index,scope.row)" circle type="primary" icon="el-icon-setting"/>
            </el-tooltip>
            <el-tooltip effect="light" content="删除商品" placement="top">
              <el-button size="mini" @click="handleDelete(scope.$index, scope.row)" plain circle type="danger" icon="el-icon-delete"/>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

    </div>

    <div class="product-content-footer"></div>

    <el-dialog title="新建商品" :visible.sync="productAddDialog" :close-on-click-modal="false" width="1024px">
      <el-form status-icon :model="newProduct" :rules="rules" ref="productAddForm" label-width="100px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="商品名称" prop="name">
              <el-input v-model="newProduct.name" autocomplete="off" ></el-input>
            </el-form-item>
            </el-col>
            <el-col :span="6">
            <el-form-item label="商品类别" prop="categoryId">
              <el-select
                filterable
                v-model="newProduct.categoryId"
                size="small"
                placeholder="选择类别">
                <el-option
                  v-for="vo in categoryList"
                  :label="vo.name"
                  :value="vo.categoryId"
                  :key="vo.name"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
            <el-col :span="6">
            <el-form-item label="商品规格" prop="specification">
              <el-input v-model="newProduct.specification" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="供应商" prop="supplyId">
              <el-select
                filterable
                v-model="newProduct.supplyId"
                size="small"
                placeholder="选择供应商">
                <el-option
                  v-for="vo in supplyList"
                  :label="vo.name"
                  :value="vo.id"
                  :key="vo.name"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>

        </el-row>
        <el-row>
          <el-col :span="6">              
            <el-form-item label="计量单位" prop="unit">
                <el-input v-model="newProduct.unit" autocomplete="off" ></el-input>
            </el-form-item>

          </el-col>
          <el-col :span="6">
            <el-form-item label="商品单价" prop="salesPrice">
              <el-input v-model="newProduct.salesPrice" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="商品重量" prop="weight">
              <el-input v-model="newProduct.weight" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="重量单位" prop="weightUnit">
              <el-input v-model="newProduct.weightUnit" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="商品体积" prop="volume">
              <el-input v-model="newProduct.volume" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="体积单位" prop="volumeUnit">
              <el-input v-model="newProduct.volumeUnit" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="存储仓库" prop="storageCode">
              <el-select
                filterable
                v-model="newProduct.storageCode"
                size="small"
                placeholder="选择仓库">
                <el-option
                  v-for="vo in storageList"
                  :label="vo.name"
                  :value="vo.storageCode"
                  :key="vo.name"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="失效日期" prop="expireDate">
              <el-date-picker
                v-model="newProduct.expireDate"
                type="date"
                style="width:147px"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>

        </el-row>
        <el-row>
          <el-form-item label="备注信息" prop="description">
            <el-input
              v-model="newProduct.description"
              autocomplete="off"
              style="width: 100%;"
              rows="3"
              type="textarea"
              maxlength="200"
              show-word-limit
            ></el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item>
            <el-button type="primary" @click="onAddProduct('productAddForm')" :loading="loading">保存</el-button>
            <el-button @click="productAddDialog = false" v-if="!loading">取 消</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </el-dialog>


    <el-dialog title="编辑商品" :visible.sync="productEditDialog" :close-on-click-modal="false" width="1024px">
      <el-form status-icon :model="editProduct" :rules="rules" ref="productEditForm" label-width="100px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="商品名称" prop="name">
              <el-input v-model="editProduct.name" autocomplete="off" ></el-input>
            </el-form-item>
            </el-col>
            <el-col :span="6">
            <el-form-item label="商品类别" prop="categoryId">
              <el-select
                filterable
                v-model="editProduct.categoryId"
                size="small"
                placeholder="选择类别">
                <el-option
                  v-for="vo in categoryList"
                  :label="vo.name"
                  :value="vo.categoryId"
                  :key="vo.name"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
            <el-col :span="6">
            <el-form-item label="商品规格" prop="specification">
              <el-input v-model="editProduct.specification" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="供应商" prop="supplyId">
              <el-select
                filterable
                v-model="editProduct.supplyId"
                size="small"
                placeholder="选择供应商">
                <el-option
                  v-for="vo in supplyList"
                  :label="vo.name"
                  :value="vo.id"
                  :key="vo.name"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>

        </el-row>
        <el-row>
          <el-col :span="6">              
            <el-form-item label="计量单位" prop="unit">
                <el-input v-model="editProduct.unit" autocomplete="off" ></el-input>
            </el-form-item>

          </el-col>
          <el-col :span="6">
            <el-form-item label="商品单价" prop="salesPrice">
              <el-input v-model="editProduct.salesPrice" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="商品重量" prop="weight">
              <el-input v-model="editProduct.weight" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="重量单位" prop="weightUnit">
              <el-input v-model="editProduct.weightUnit" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="商品体积" prop="volume">
              <el-input v-model="editProduct.volume" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="体积单位" prop="volumeUnit">
              <el-input v-model="editProduct.volumeUnit" autocomplete="off" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="存储仓库" prop="storageCode">
              <el-select
                filterable
                v-model="editProduct.storageCode"
                size="small"
                placeholder="选择仓库">
                <el-option
                  v-for="vo in storageList"
                  :label="vo.name"
                  :value="vo.storageCode"
                  :key="vo.name"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="失效日期" prop="expireDate">
              <el-date-picker
                v-model="editProduct.expireDate"
                type="date"
                style="width:147px"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>

        </el-row>
        <el-row>
          <el-form-item label="备注信息" prop="description">
            <el-input
              v-model="editProduct.description"
              autocomplete="off"
              style="width: 100%;"
              rows="3"
              type="textarea"
              maxlength="200"
              show-word-limit
            ></el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item>
            <el-button type="primary" @click="onEditProduct('productEditForm')" :loading="loading">保存</el-button>
            <el-button @click="productEditDialog = false" v-if="!loading">取 消</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </el-dialog>



    <el-dialog title="创建商品Bom" :visible.sync="productCreateBoMDialog" :close-on-click-modal="false" width="1024px">
      <el-form status-icon :model="newProductBom" :rules="rules2" ref="productCreateBoMForm" label-width="100px">
        <div class="productBom-selected-header">
          <span>父项物料信息</span>
        </div>
        <div class="productBom-selected-content">
          <el-row>
            <el-col :span="6">
              <el-form-item label="商品名称" prop="productName">
                <el-input v-model="newProductBom.productName" autocomplete="off" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="制造工序" prop="processId">
                <el-select
                  filterable
                  v-model="newProductBom.processName"
                  size="small"
                  placeholder="选择工序"
                  @change="processChanged">
                  <el-option
                    v-for="vo in produceProcessList"
                    :label="vo.name"
                    :value="vo"
                    :key="vo.id"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
            <el-form-item label="Bom版本" prop="version">
              <el-input autocomplete="off"  v-model="newProductBom.version" disabled></el-input>
            </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="失效日期" prop="expirationDate">
                <el-date-picker
                  v-model="newProductBom.expirationDate"
                  type="date"
                  style="width:147px"
                  placeholder="选择日期">
                </el-date-picker>
              </el-form-item>

            </el-col>
          </el-row>
          <el-row>
            <el-form-item label="备注信息" prop="description">
              <el-input
                v-model="newProductBom.description"
                autocomplete="off"
                style="width: 100%;"
                rows="3"
                type="textarea"
                maxlength="200"
                show-word-limit
              ></el-input>
            </el-form-item>
          </el-row>
        </div>
        <div class="productBom-selected-header">
          <span>子项物料详情</span>
        </div>
        <div class="productBom-selected-content-special">
        <el-row>
          <el-form-item label="物料信息">
            <el-button
              icon="el-icon-plus"
              plain
              class="add-productBom"
              @click="addProductBom"
              >添加子项物料</el-button
            >
            <el-table
              :data="newProductBom.materialVos"
              border
              style="width: 100%"
              size="mini"
              @cell-click="addCellClick"
              :show-header="false"
            >
              <el-table-column prop="name" label="名" width="180px" >
                <template slot-scope="scope" >
                  <div class="productBominformation" >
                    <el-form-item label="名" prop="chName" label-width="20px" >
                      <el-select
                        filterable
                        v-model="scope.row.chName"
                        v-if="scope.row.seen"
                        size="mini"
                        placeholder="选择物料"
                        @change="addMaterialChanged($event,scope.row)">
                        <!-- <el-option
                          v-for="vo in materialList"
                          :label="vo.alias"
                          :value="vo"
                          :key="vo.id"
                        ></el-option> -->
                        <el-option-group
                          v-for="group in options"
                          :key="group.groupName"
                          :label="group.groupName"
                        >
                          <el-option
                            v-for="item in group.list"
                            :key="item.id"
                            :label="item.alias"
                            :value="item"
                          >
                          </el-option>
                        </el-option-group>
                      </el-select>
                      <span v-else>{{ scope.row.chName }}</span>
                    </el-form-item>
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="specification" label="规"  width="144px" >
                <template slot-scope="scope">
                  <div class="productBominformation">
                    <el-form-item label="规" prop="specification" label-width="20px" >
                      <el-input
                        v-model="scope.row.specification"
                        v-if="scope.row.seen"
                        disabled
                        size="mini"
                      ></el-input>
                      <span v-else>{{ scope.row.specification }}</span>
                    </el-form-item>
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="chQty" label="数:" width="120px" >
                <template slot-scope="scope" >
                  <div class="productBominformation">
                    <el-form-item label="数" prop="chQty" label-width="35px" >
                      <el-input  
                        v-model="scope.row.chQty"
                        v-if="scope.row.seen"
                        :disabled="scope.row.isDisable"
                        size="mini"
                      ></el-input>
                      <span v-else>{{ scope.row.chQty }}</span>
                    </el-form-item>
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="lossRate" label="损" width="120px" >
                <template slot-scope="scope" >
                  <div class="productBominformation">
                    <el-form-item label="损" prop="lossRate" label-width="35px" >
                      <el-input  
                        v-model="scope.row.lossRate"
                        v-if="scope.row.seen"
                        :disabled="scope.row.isDisable"
                        size="mini"
                      ></el-input>
                      <span v-else>{{ scope.row.lossRate }}</span>
                    </el-form-item>
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="supplyName" label="厂家" >
                <template slot-scope="scope" >
                  <div class="productBominformation">
                    <!-- <el-form-item label="厂家" prop="supplyName" label-width="40px" > -->
                      <el-input  
                        v-model="scope.row.supplyName"
                        v-if="scope.row.seen"
                        :disabled="scope.row.isDisable"
                        size="mini"
                        style="width:190px"
                      ></el-input>
                      <span v-else>{{ scope.row.supplyName }}</span>
                    <!-- </el-form-item> -->
                  </div>
                </template>
              </el-table-column>
              <el-table-column width="70px" >>
                <template slot-scope="scope">
                  <div class="productBomOperation">

                    <el-tooltip effect="light" content="确定" placement="top">
                      <el-button size="mini" circle @click.prevent.stop="addSaveProductBom(scope.$index, scope.row)" icon="el-icon-check" type="primary" ></el-button>
                    </el-tooltip>
                    <el-tooltip effect="light" content="删除" placement="top">
                      <el-button size="mini" :disabled="scope.row.isDisable" @click.prevent.stop="addDeleteProductBom(scope.$index, scope.row)" plain circle type="danger" icon="el-icon-delete"/>
                    </el-tooltip>
                  </div>
                </template>
              </el-table-column>

            </el-table>
          </el-form-item>
        </el-row>

        </div>
        <div class="productBom-selected-header">
          <span>物料历史记录</span>
        </div>
        <div class="productBom-selected-content">
          <el-row hight>
            <el-form-item label="Bom列表">
              <el-table
                :data="newProductBom.productBomList"
                border
                style="width: 100%"
                size="mini"
                :show-header="false"
              >
                <el-table-column prop="bomNo" label="Bom:" width="180px" >
                  <template slot-scope="scope" >
                    <div class="newProductBomInformation">
                      <el-form-item label="Bom:" prop="bomNo" label-width="45px" >
                        <span>{{ scope.row.bomNo }}</span>
                      </el-form-item>
                    </div>
                  </template>
                </el-table-column>
                <el-table-column prop="expirationDate" label="失效日期:" width="180px" >
                  <template slot-scope="scope" >
                    <div class="newProductBomInformation">
                      <el-form-item label="失效日期:" prop="expirationDate" label-width="80px" >
                        <span>{{getFullTime(scope.row.expirationDate) }}</span>
                      </el-form-item>
                    </div>
                  </template>
                </el-table-column>
                <el-table-column prop="version" label="版本:"  width="145px" >
                  <template slot-scope="scope">
                    <div class="newProductBomInformation">
                      <el-form-item label="版本:" prop="version" label-width="55px" >
                        <span>{{ scope.row.version }}</span>
                      </el-form-item>
                    </div>
                  </template>
                </el-table-column>
                <el-table-column prop="description" label="描述:" >
                  <template slot-scope="scope" >
                    <div class="newProductBomInformation">
                      <el-form-item label="描述:" prop="description" label-width="45px" >
                        <span>{{ scope.row.description }}</span>
                      </el-form-item>
                    </div>
                  </template>
                </el-table-column>
                <el-table-column width="80px" >
                  <template slot-scope="scope">
                    <div class="newProductBomOperation">
                      <el-tooltip effect="light" content="查看Bom" placement="top" >
                        <el-button size="mini" @click.prevent.stop="ReviewProductBom(scope.$index, scope.row,true)" circle icon="el-icon-view" type="primary" :disabled="scope.row.status=='N'"></el-button>
                      </el-tooltip>
                      <el-tooltip effect="light" v-if="scope.row.status=='Y'" content="停用Bom" placement="top">
                        <el-button size="mini" @click.prevent.stop="UpdateProductBomStatus(scope.$index, scope.row,true)" plain circle type="danger" icon="el-icon-close"/>
                      </el-tooltip>
                      <el-tooltip effect="light" v-else content="启用Bom" placement="top">
                        <el-button size="mini" @click.prevent.stop="UpdateProductBomStatus(scope.$index, scope.row,false)" plain circle type="primary" icon="el-icon-check"/>
                      </el-tooltip>
                    </div>
                  </template>
                </el-table-column>

              </el-table>

            </el-form-item>
          </el-row>
        </div>
        <div class="productBom-selected-content">
          <el-row>
            <el-form-item>
              <el-button type="primary" @click="onnewProductBom('productCreateBoMForm')" :loading="loading">保 存</el-button>
              <el-button @click="productCreateBoMDialog = false" v-if="!loading">取 消</el-button>
            </el-form-item>
          </el-row>
        </div>
      </el-form>
    </el-dialog>


    <el-dialog :title=bomNo :visible.sync="productEditBoMDialog" :close-on-click-modal="false" width="1024px">
      <el-form status-icon :model="editProductBom" ref="productEditBoMForm" label-width="100px">
        <div class="productBom-selected-header">
          <span>父项物料信息</span>
        </div>
        <div class="productBom-selected-content">
          <el-row>
            <el-col :span="6">
              <el-form-item label="商品名称" prop="productName">
                <el-input v-model="editProductBom.productName" autocomplete="off" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="制造工序" prop="processId">
                <el-select
                  filterable
                  v-model="editProductBom.processName"
                  size="small"
                  placeholder="选择工序"
                  @change="editprocessChanged">
                  <el-option
                    v-for="vo in produceProcessList"
                    :label="vo.name"
                    :value="vo"
                    :key="vo.id"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
            <el-form-item label="Bom版本" prop="version">
              <el-input autocomplete="off"  v-model="editProductBom.version" disabled></el-input>
            </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="失效日期" prop="expirationDate">
                <el-date-picker
                  v-model="editProductBom.expirationDate"
                  type="date"
                  style="width:147px"
                  placeholder="选择日期">
                </el-date-picker>
              </el-form-item>

            </el-col>
          </el-row>
          <el-row>
            <el-form-item label="备注信息" prop="description">
              <el-input
                v-model="editProductBom.description"
                autocomplete="off"
                style="width: 100%;"
                rows="3"
                type="textarea"
                maxlength="200"
                show-word-limit
              ></el-input>
            </el-form-item>
          </el-row>
        </div>
        <div class="productBom-selected-header">
          <span>子项物料详情</span>
        </div>
        <div class="productBom-selected-content-special">
        <el-row>
          <el-form-item label="物料信息">
            <el-button
              icon="el-icon-plus"
              plain
              class="add-productBom"
              @click="editProductBomButton"
              >添加子项物料</el-button
            >
            <el-table
              :data="editProductBom.materialVos"
              border
              style="width: 100%"
              size="mini"
              @cell-click="editCellClick"
              :show-header="false"
            >
              <el-table-column prop="name" label="名" width="185px" >
                <template slot-scope="scope" >
                  <div class="productBominformation" >
                    <el-form-item label="名" prop="chName" label-width="20px" >
                      <el-select
                        filterable
                        v-model="scope.row.chName"
                        v-if="scope.row.seen"
                        size="mini"
                        placeholder="选择物料"
                        @change="editMaterialChanged($event,scope.row)">
                        <!-- <el-option
                          v-for="vo in materialList"
                          :label="vo.alias"
                          :value="vo"
                          :key="vo.id"
                        ></el-option> -->
                        <el-option-group
                          v-for="group in options"
                          :key="group.groupName"
                          :label="group.groupName"
                        >
                          <el-option
                            v-for="item in group.list"
                            :key="item.id"
                            :label="item.alias"
                            :value="item"
                          >
                          </el-option>
                        </el-option-group>
                      </el-select>

                      <span v-else>{{ scope.row.chName }}</span>
                    </el-form-item>
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="specification" label="规"  width="150px" >
                <template slot-scope="scope">
                  <div class="productBominformation">
                    <el-form-item label="规" prop="specification" label-width="20px" >
                      <el-input
                        v-model="scope.row.specification"
                        v-if="scope.row.seen"
                        disabled
                        size="mini"
                      ></el-input>
                      <span v-else>{{ scope.row.specification }}</span>
                    </el-form-item>
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="chQty" label="数:" width="120px" >
                <template slot-scope="scope" >
                  <div class="productBominformation">
                    <el-form-item label="数" prop="chQty" label-width="35px" >
                      <el-input  
                        v-model="scope.row.chQty"
                        v-if="scope.row.seen"
                        :disabled="scope.row.isDisable"
                        size="mini"
                      ></el-input>
                      <span v-else>{{ scope.row.chQty }}</span>
                    </el-form-item>
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="lossRate" label="损" width="120px" >
                <template slot-scope="scope" >
                  <div class="productBominformation">
                    <el-form-item label="损" prop="lossRate" label-width="35px" >
                      <el-input  
                        v-model="scope.row.lossRate"
                        v-if="scope.row.seen"
                        :disabled="scope.row.isDisable"
                        size="mini"
                      ></el-input>
                      <span v-else>{{ scope.row.lossRate }}</span>
                    </el-form-item>
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="supplyName" label="厂家" >
                <template slot-scope="scope" >
                  <div class="productBominformation">
                    <!-- <el-form-item label="厂家" prop="supplyName" label-width="40px" > -->
                      <el-input  
                        v-model="scope.row.supplyName"
                        v-if="scope.row.seen"
                        :disabled="scope.row.isDisable"
                        size="mini"
                        style="width:200px"
                      ></el-input>
                      <span v-else>{{ scope.row.supplyName }}</span>
                    <!-- </el-form-item> -->
                  </div>
                </template>
              </el-table-column>
              <el-table-column width="70px" >>
                <template slot-scope="scope">
                  <div class="productBomOperation">

                    <el-tooltip effect="light" content="确定" placement="top">
                      <el-button size="mini" circle @click.prevent.stop="editSaveProductBom(scope.$index, scope.row)" icon="el-icon-check" type="primary" ></el-button>
                    </el-tooltip>
                    <el-tooltip effect="light" content="删除" placement="top">
                      <el-button size="mini" :disabled="scope.row.isDisable" @click.prevent.stop="editDeleteProductBom(scope.$index, scope.row)" plain circle type="danger" icon="el-icon-delete"/>
                    </el-tooltip>
                  </div>
                </template>
              </el-table-column>

            </el-table>
          </el-form-item>
        </el-row>
        </div>
        <div class="productBom-selected-content">
          <el-row>
            <el-form-item>
              <el-button type="primary" @click="oneditProductBom('productEditBoMForm')" :loading="loading">保 存</el-button>
              <el-button @click="productEditBoMDialog = false" v-if="!loading">取 消</el-button>
            </el-form-item>
          </el-row>
        </div>
      </el-form>
    </el-dialog>

    </div>
</template>
<script>
import { mapGetters } from "vuex";
import { mapActions } from "vuex";
export default {
    data(){
      return{
        options: [],
        bomNo:'',
        selectPid:'',
        dateNow:"",
        productEditDialog: false,
        productAddDialog: false,
        productCreateBoMDialog: false,
        productEditBoMDialog: false,
        searchContent: "",
        loading: false,
        editIndex:'',
        newProduct:{
          id:'',
          productNo:'',
          name:'',
          categoryId:'',
          specification:'',
          unit:'',
          supplyId:'',
          salesPrice:'',
          weight:'',
          weightUnit:'',
          volume:'',
          volumeUnit:'',
          remark:'',
          storageCode:'',
          status:'',
          description:'',
          expireDate:'',
          createBy:'',
          createDate:''
        },
        editProduct:{
          id:'',
          productNo:'',
          name:'',
          categoryId:'',
          specification:'',
          unit:'',
          supplyId:'',
          salesPrice:'',
          weight:'',
          weightUnit:'',
          volume:'',
          volumeUnit:'',
          remark:'',
          storageCode:'',
          status:'',
          description:'',
          expireDate:'',
          createBy:'',
          createDate:''
        },
        newProductBom:{
          id:'',
          productNo:'',
          productName:'',
          processId:'',
          processNo:'',
          processCost:'',
          specification:'',
          bomNo:'',
          bomGenericId:'',
          categoryId:'',
          supplyId:'',
          supplyName:'',
          costPrice:'',
          expirationDate:'',
          version:'',
          description:'',
          createBy:'',
          createDate:'',
          productBomList:[],
          groupProductVos:[],
          materialVos:[],
          status:'',
        },
        editProductBom:{
          id:'',
          productNo:'',
          productName:'',
          processId:'',
          processName:'',
          processNo:'',
          processCost:'',
          specification:'',
          bomNo:'',
          bomGenericId:'',
          categoryId:'',
          supplyId:'',
          supplyName:'',
          costPrice:'',
          expirationDate:'',
          version:'',
          description:'',
          createBy:'',
          createDate:'',
          productBomList:[],
          groupProductVos:[],
          materialVos:[],
          status:'',
        },
        rules: {
        name: [
          { required: true, message: "请输入商品名称", trigger: "blur" }
        ],
        specification: [
          { required: true, message: "请输入商品规格", trigger: "blur" }
        ],
        categoryId: [
          { required: true, message: "请选择商品类别", trigger: "blur" }
        ],
        storageCode: [
          { required: true, message: "请选择存储仓库", trigger: "blur" }
        ],
        supplyId: [
          { required: true, message: "请选择商品供应商", trigger: "blur" }
        ],
        },
        rules2: {
        processId:[
          { required: true, message: "请选择工序", trigger: "blur" }
        ],
        // chQty: [
        //   { required: true, message: "请输入数量", trigger: "blur" }
        // ],
        // lossRate:[
        //   { required: true, message: "请输入数量", trigger: "blur" }
        // ],
        },
      }

    },
    computed: {
      ...mapGetters(["productList","storageList","categoryList","productBomList","supplyList","produceProcessList","materialList","userInfo"]),
      productListPageShow(){
        return this.productList.filter((item)=>{
          let key=
          item.productNo +
          item.name +
          item.specification +
          item.unit +
          item.salePrice +
          item.storageName +
          item.supplyName +
                    item.weight +
          item.weightUnit +
          item.volume +
                    item.volumeUnit +
          item.category +
          item.expireDate ;
          let index = key.toUpperCase().indexOf(this.searchContent.toUpperCase());
          return index != -1;
        });
      },
      // filterProductList(){
      //   return this.productList.filter((item)=>{
      //     item.alias=item.name+" / "+item.category+" / "+item.specification+" / "+item.supplyName
      //      return item.id!=this.selectPid
      //   })
      // }
    },
    methods:{
      ...mapActions({
      GetProductList:"GetProductList",
      GetCategoryList:"GetCategoryList",
      GetStorageList:"GetStorageList",
      SaveProduct:"SaveProduct",
      PushProductList:"PushProductList",
      QueryProductObj:"QueryProductObj",
      DeleteProduct:"DeleteProduct",
      QueryProductAndBomObj:"QueryProductAndBomObj",
      SaveProductBom:"SaveProductBom",
      UpdateProductBom:"UpdateProductBom",
      GetProductBomList:"GetProductBomList",
      GetSupplyList:"GetSupplyList",
      FindAllProduceProcessList: "FindAllProduceProcessList",
      QueryProductBom:"QueryProductBom",
      GetMaterialList:"GetMaterialList",
      }),
      resetForm(formName) {
        if (this.$refs[formName]) {
          this.$refs[formName].resetFields();
        }
        this.GetStorageList();
        this.GetCategoryList();
        this.GetSupplyList();
        this.newProduct.expireDate=new Date().setMonth(new Date().getMonth()+12);
        this.productAddDialog = true;
      },


      onAddProduct(formName){
        this.$refs[formName].validate(valid => {
          if (valid) {
            let product=this.newProduct;
            product.productNo="p" + new Date().valueOf();
            product.createDate=new Date();
            product.createBy=this.userInfo.loginName;
            product.status="Y";
            this.SaveProduct(product)
              .then(res => {
                if (res.resultStatus == 1) {
                  this.productAddDialog = false;
                  // this.PushProductList(res.data);
                  this.GetProductList();
                  this.$message({
                    showClose: true,
                    message: "创建成功",
                    type: "success"
                  });
                } else {
                  this.$message({
                    message: res.message,
                    type: "warning"
                  });
                }
              })
              .catch(err => {
                let alert = err.message ? err.message : err;
                this.$messgae.error(alert);
              });
          }
          else{
            this.$message.warning("请填写必要信息");
            return false;
          }
        });
      },
      handleDelete(index, row) {
      let prod={id:row.id,productNo:row.productNo,index:index}
       this.$messageBox.confirm('确认要删除？',"提示",{
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            this.DeleteProduct(prod)
          })
          .catch(e=>e);
      },
      onEditProduct(formName){
        this.$refs[formName].validate(valid=>{
          if(valid){
            this.SaveProduct(this.editProduct)
            .then(res=>{
              if(res.resultStatus==1){
                this.productList.splice(this.editIndex,1,res.data);
                  this.editIndex=""
                // this.GetProductList();
                this.productEditDialog=false;
                this.$message({
                  showClose:true,
                  message:"操作成功",
                  type:"success"
                });
              }
              else{
                this.$message.error(res.message);
              }
            })
            .catch(err=>{
                let alert = err.message ? err.message : err;
                this.$message.error(alert);
            });
          }
          else{
            this.$message.warning("请填写准确信息");
            return false;
          }
        })
      },
      handleEdit(index,row){
      this.editIndex=index;
      this.GetStorageList();
      this.GetCategoryList();
      this.GetSupplyList();
      let prod={productNo:row.productNo,index:index}
      this.QueryProductObj(prod)
        .then(res=>{
          if (res.resultStatus == 1) {
            this.editProduct=res.data;
            this.productEditDialog = true;
          }
          else{
            this.$message.error(res.message);
          }
      })
      .catch(err=>{
            let alert = err.message ? err.message : err;
            this.$message.error(alert);
      });
      },
      handleCreateBoM(index,row){
        // this.selectPid=row.id;
        // this.GetMaterialList({
        //   productNo: row.productNo,
        // });
        let prod={productNo:row.productNo,index:index}
        this.QueryProductAndBomObj(prod)
        .then(res=>{
          if (res.resultStatus == 1) {
            var productList = res.data.groupProductVoList;
            var options = [];
            productList.map((group) => {
              var subList = group.list;
              if (subList.length > 0) {
                options.push({ groupName: group.groupName, list: subList });
              }
            });
            this.options = options;

            this.newProductBom.categoryId=res.data.categoryId;
            this.newProductBom.supplyId=res.data.supplyId;
            this.newProductBom.supplyName=res.data.supplyName;
            this.newProductBom.productNo=res.data.productNo;
            this.newProductBom.productName=res.data.name;
            this.newProductBom.specification=res.data.specification;
            // this.newProductBom.costPrice=res.data.salesPrice;
            this.newProductBom.description=res.data.description;
            this.newProductBom.productBomList=res.data.productBomList;
            this.newProductBom.expirationDate=new Date().setMonth(new Date().getMonth()+12);
            this.newProductBom.version="v"+(res.data.productBomList.length+1);
            this.newProductBom.bomNo="";
            this.newProductBom.id="";
            this.newProductBom.description=res.data.specification;
            this.newProductBom.materialVos=[];
            this.productCreateBoMDialog=true;
            }
            else{
              this.$message.error(res.message);
            }
        })
        .catch(err=>{
              let alert = err.message ? err.message : err;
              this.$message.error(alert);
        });
      },
      UpdateProductBomStatus(index, row,disable) {
       this.$messageBox.confirm('确认要操作Bom？',"提示",{
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        .then(() => {
          row.status=disable?"N":"Y"
          this.UpdateProductBom(row)
              .then(res=>{
                if(res.resultStatus==1){
                  row=res.data
                  this.$message({
                    showClose:true,
                    message:"操作成功",
                    type:"success"
                  });
                }
                else{
                  this.$message.error(res.message);
                }
              })
              .catch(err=>{
                  let alert = err.message ? err.message : err;
                  this.$message.error(alert);
              });
        })
        .catch(e=>e);
      },
      getFullTime(time) {
      return new Date(time).format("yyyy-MM-dd");
      },
      oneditProductBom(formName){

        if(!this.editCheckSavedFlag())
        {
          if(!this.editCheckEidtable())
          {
            this.$refs[formName].validate(valid=>{
              if(valid){

                if(this.editProductBom.materialVos.length>0)
                {
                    var price=this.editProductBom.processCost;
                    // debugger
                    for( var i = 0; i < this.editProductBom.materialVos.length; i++) {
                      this.editProductBom.materialVos[i].processId=this.editProductBom.processId;
                      this.editProductBom.materialVos[i].processNo=this.editProductBom.processNo;
                      this.editProductBom.materialVos[i].partNo=this.editProductBom.productNo;
                      this.editProductBom.materialVos[i].sIndex=Number(i+1);
                      // this.editProductBom.materialVos[i].id="";
                      this.editProductBom.materialVos[i].partName=this.editProductBom.productName;
                      price=Number(price) + Number(this.editProductBom.materialVos[i].salesPrice) *(Number(this.editProductBom.materialVos[i].chQty)+Number(this.editProductBom.materialVos[i].lossRate))
                      if(this.editProductBom.materialVos[i].chBomNo=== "" || this.editProductBom.materialVos[i].chBomNo == null || this.editProductBom.materialVos[i].chBomNo == undefined)
                      {
                        this.editProductBom.materialVos[i].chBomStatus="N"
                      }
                      else{
                        this.editProductBom.materialVos[i].chBomStatus="Y"
                      }
                    }
                    this.editProductBom.costPrice=price;
                    this.editProductBom.status="Y";
                    this.editProductBom.createDate=new Date();
                    this.editProductBom.createBy=this.userInfo.loginName;
                    this.editProductBom.bomGenericId=new Date().valueOf();


                      this.SaveProductBom(this.editProductBom)
                      .then(res=>{
                        if(res.resultStatus==1){
                          // this.GetProductList();
                          this.productEditBoMDialog=false;
                          this.editProductBom.materialVos=[];
                          this.$message({
                            showClose:true,
                            message:"操作成功",
                            type:"success"
                          });
                        }
                        else{
                          this.$message({
                          message: res.message,
                          type: "warning"
                        });
                        }
                      })
                      .catch(err=>{
                          let alert = err.message ? err.message : err;
                          this.$message.error(alert);
                      });


                }
                else
                {
                  this.$message({
                  message: "子料清单不能为空",
                  type: "warning"
                  });
                }
              }
              else{
                this.$message.warning("请填写准确信息");
                return false;
              }
            })
          }
        }
      },
      onnewProductBom(formName){
        if(!this.addCheckSavedFlag())
        {
          if(!this.addCheckEidtable())
          {
            this.$refs[formName].validate(valid=>{
              if(valid)
              {
                if(this.newProductBom.materialVos.length>0)
                  {
                    var price=this.newProductBom.processCost;
                    // debugger
                    for( var i = 0; i < this.newProductBom.materialVos.length; i++) {
                      this.newProductBom.materialVos[i].processId=this.newProductBom.processId;
                      this.newProductBom.materialVos[i].processNo=this.newProductBom.processNo;
                      this.newProductBom.materialVos[i].partNo=this.newProductBom.productNo;
                      this.newProductBom.materialVos[i].sIndex=Number(i+1);
                      this.newProductBom.materialVos[i].partName=this.newProductBom.productName;
                      price=Number(price) + Number(this.newProductBom.materialVos[i].salesPrice) *(Number(this.newProductBom.materialVos[i].chQty)+Number(this.newProductBom.materialVos[i].lossRate))
                      if(this.newProductBom.materialVos[i].chBomNo=== "" || this.newProductBom.materialVos[i].chBomNo == null || this.newProductBom.materialVos[i].chBomNo == undefined)
                      {
                        this.newProductBom.materialVos[i].chBomStatus="N"
                      }
                      else{
                        this.newProductBom.materialVos[i].chBomStatus="Y"
                      }
                    }
                    this.newProductBom.costPrice=price;
                    this.newProductBom.status="Y";
                    this.newProductBom.createDate=new Date();
                    this.newProductBom.createBy=this.userInfo.loginName;
                    this.newProductBom.bomGenericId=new Date().valueOf();
                      this.SaveProductBom(this.newProductBom)
                      .then(res=>{
                        if(res.resultStatus==1){
                          // this.GetProductList();
                          this.productCreateBoMDialog=false;
                          this.newProductBom.materialVos=[];
                          this.$message({
                            showClose:true,
                            message:"操作成功",
                            type:"success"
                          });
                        }
                        else{
                          this.$message({
                          message: res.message,
                          type: "warning"
                        });
                        }
                      })
                      .catch(err=>{
                          let alert = err.message ? err.message : err;
                          this.$message.error(alert);
                      });
                }
                else
                {
                  this.$message({
                  message: "子料清单不能为空",
                  type: "warning"
                  });
                }
              }
              else{
                this.$message.warning("请填写准确信息");
                return false;
              }
              })
          }
        }
      },

      addProductBom(){
        if(!this.addCheckSavedFlag())
        {
          if(!this.addCheckEidtable())
            {
              this.newProductBom.materialVos.push({ id:"",bomNo:"",processId:"",processNo:"",partNo:"",partName:"",chPartNo: "",chName:"",chBomStatus:"",chBomNo:"",chBomGenericId:"",specification:"",sIndex:"",chQty:"" ,lossRate:"0", processStation:"", supplyId:"" ,supplyName:"",materialCostStatus:"",productNo:"",categoryId: "",salesPrice:"",seen: false,notSavedFlag:true})
            }
        }
      },
      editProductBomButton(){
        if(!this.editCheckSavedFlag())
        {
          if(!this.editCheckEidtable())
            {
              this.editProductBom.materialVos.push({ id:"",bomNo:"",processId:"",processNo:"",partNo:"",partName:"",chPartNo: "",chName:"",chBomStatus:"",chBomNo:"",chBomGenericId:"",specification:"",sIndex:"",chQty:"" ,lossRate:"0", processStation:"", supplyId:"" ,supplyName:"",materialCostStatus:"",productNo:"",categoryId: "",salesPrice:"",seen: false,notSavedFlag:true})
            }
        }
      },
      editCheckRepeated(event){
        var list= this.editProductBom.materialVos.filter((item)=>{return item.productNo==event.productNo
        })
        if(list.length>0){
          this.$message.warning('子料重复！')
          return true;
        }
      },
      editMaterialChanged(event,row){
        if(!this.editCheckRepeated(event))
        {
          row.chPartNo=event.productNo;
          row.productNo=event.productNo;
          row.chName=event.name
          row.specification=event.specification;
          row.categoryId=event.categoryId;
          row.supplyId=event.supplyId;
          row.salesPrice=event.salesPrice;
          row.supplyName=event.supplyName;
          row.chBomNo=event.bomNo;
          row.chBomGenericId=event.bomGenericId;
        }
        else{
          row.chPartNo="";
          row.productNo="";
          row.chName="";
          row.specification="";
          row.categoryId="";
          row.supplyId="";
          row.salesPrice="";
          row.supplyName="";
          row.chBomNo="";
          row.chBomGenericId="";
          event=null;
        }
        // row.chPartNo=event.productNo;
        // row.productNo=event.productNo;
        // row.chName=event.name
        // row.specification=event.specification;
        // row.categoryId=event.categoryId;
        // row.supplyId=event.supplyId;
        // row.salesPrice=event.salesPrice;
        // row.supplyName=event.supplyName;
        // row.chBomNo=event.bomNo;
        // row.chBomGenericId=event.bomGenericId;
      },
      addCheckRepeated(event){
        var list= this.newProductBom.materialVos.filter((item)=>{return item.productNo==event.productNo
        })
        if(list.length>0){
          this.$message.warning('子料重复！')
          return true;
        }
      },
      addMaterialChanged(event,row){
        if(!this.addCheckRepeated(event))
        {
          row.chPartNo=event.productNo;
          row.productNo=event.productNo;
          row.chName=event.name
          row.specification=event.specification;
          row.categoryId=event.categoryId;
          row.supplyId=event.supplyId;
          row.salesPrice=event.salesPrice;
          row.supplyName=event.supplyName;
          row.chBomNo=event.bomNo;
          row.chBomGenericId=event.bomGenericId;
        }
        else{
          row.chPartNo="";
          row.productNo="";
          row.chName="";
          row.specification="";
          row.categoryId="";
          row.supplyId="";
          row.salesPrice="";
          row.supplyName="";
          row.chBomNo="";
          row.chBomGenericId="";
          event=null;
        }
      },
      processChanged(event){
        this.newProductBom.processCost=event.cost;
        this.newProductBom.processId=event.id;
        this.newProductBom.processName=event.name;
        this.newProductBom.processNo=event.processNo;
      },
      editprocessChanged(event){
        this.editProductBom.processCost=event.cost;
        this.editProductBom.processId=event.id;
        this.editProductBom.processName=event.name;
        this.editProductBom.processNo=event.processNo;
      },
      addCellClick(row){
        if(!row.seen)
        {
          if(!this.addCheckEidtable())
          {
            row.seen=true;
          }
        }
      },
      editCellClick(row){
        if(!row.seen)
        {
          if(!this.editCheckEidtable())
          {
            row.seen=true;
          }
        }
      },
      editCheckSavedFlag(){

        var list= this.editProductBom.materialVos.filter((item)=>{return item.notSavedFlag
        })
        if(list.length>0){
          this.$message.warning('请先完成子料编辑')
          return true;
        }
      },
      editCheckEidtable(){
        var list= this.editProductBom.materialVos.filter((item)=>{return item.seen
        })
        if(list.length>0){
          this.$message.warning('请先完成子料编辑')
          return true;
        }
      },
      addCheckSavedFlag(){
        var list= this.newProductBom.materialVos.filter((item)=>{return item.notSavedFlag
        })
        if(list.length>0){
          this.$message.warning('请先完成子料编辑')
          return true;
        }
      },
      addCheckEidtable(){
        var list= this.newProductBom.materialVos.filter((item)=>{return item.seen
        })
        if(list.length>0){
          this.$message.warning('请先完成子料编辑')
          return true;
        }
      },
      addSaveProductBom(index,row){
        if(row.productNo===""|| row.chQty===""||row.lossRate===""|| isNaN(row.chQty)|| isNaN(row.lossRate))
        {
          this.$message.warning("请正确填写信息");
          return false;
        }
        else
        {
          row.seen=false;
          row.notSavedFlag=false;
        }
      },
      addDeleteProductBom(index,row){
        row.seen=false;
        row.notSavedFlag=false;
        this.$messageBox.confirm('确认要删除？',"提示",{
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          })
          .then(() => {
              this.newProductBom.materialVos.splice(index,1)
          })
          .catch(e=>e);
      },
      editSaveProductBom(index,row){
        try {
          if(row.productNo===""|| row.chQty===""||row.lossRate==="" || isNaN(row.chQty)|| isNaN(row.lossRate))
          {
            this.$message.warning("请正确填写信息");
            return false;
          }
          else
          {
            row.seen=false;
            row.notSavedFlag=false;
          }
        } catch (error) {
          this.$message({
            message: "请检查数据",
            type: "warning"
            });
        }


      },
      editDeleteProductBom(index,row){
        row.seen=false;
        row.notSavedFlag=false;
        this.$messageBox.confirm('确认要删除？',"提示",{
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          })
          .then(() => {
              this.editProductBom.materialVos.splice(index,1)
          })
          .catch(e=>e);
      },

      ReviewProductBom(index, row) {
        this.bomNo=row.bomNo;
        let pb={bomNo:row.bomNo,productNo:row.productNo,index}
        this.QueryProductBom(pb)
          .then(res=>{
              if (res.resultStatus == 1) {
                this.editProductBom=res.data;
                let materials = [];
                for (let index = 0; index < res.data.materialVos.length; index++) {
                    const element = res.data.materialVos[index];
                    let bom = { id:element.id,bomNo:element.bomNo,processId:element.processId,processNo:element.processNo,partNo:element.partNo,partName:element.partName,chPartNo: element.chPartNo,chName:element.chName,chBomStatus:element.chBomStatus,chBomNo:element.chBomNo,chBomGenericId:element.chBomGenericId,specification:element.specification,salesPrice:element.salesPrice,sIndex:element.sIndex,chQty:element.chQty ,lossRate:element.lossRate, processStation:element.processStation, supplyId:element.supplyId ,supplyName:element.supplyName,materialCostStatus:element.materialCostStatus,productNo:element.chPartNo,categoryId: "",seen: false,notSavedFlag:false };
                    materials.push(bom);
                }
                this.editProductBom.materialVos=materials;
                this.productEditBoMDialog=true;
              }
              else{
                this.$message({
                  message: res.message,
                  type: "warning"
                });
              }
          })
          .catch(err=>{
                let alert = err.message ? err.message : err;
                this.$message.error(alert);
          });
      },

    },
    beforeMount() {
      this.GetProductList();
      this.FindAllProduceProcessList();
    },
};
</script>
<style lang="less">
.product-container {
  display: flex;
  flex-direction: column;
  width: 98%;
  padding: 15px;
}
.product-header {
  padding-bottom: 10px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
.product-fliter-input {
  width: 400px;
  float: left;
}
.newProductBomInformation{
  display: flex;
  flex-direction: row;
  width: auto;
  justify-content: space-between;
}
.newProductBomOperation{
  display: flex;
  flex-direction: row;
  width: auto;
  justify-content: space-around;
}
.productBom-selected-header {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid lightgray;
}
.productBom-selected-content {
  padding-top: 8px;
}
.productBom-selected-content-special{
  padding-top: 4px;
}
.productBominformation{
  display: flex;
  flex-direction: row;
  width: auto;
  justify-content: space-between;
}
.productBomOperation{
  display: flex;
  flex-direction: row;
  width: auto;
  justify-content: space-around;
}
.add-productBom {
  margin-top: 5px;
  width: 100%;
  border: #dcdfe6 dashed 1px !important;
}
</style>