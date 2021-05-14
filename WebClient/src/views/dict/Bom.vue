<template>
  <div>
    <el-tree
      :data="data"
      node-key="id"
      ref="tree"
      highlight-current
      :props="defaultProps">
    </el-tree>
  </div>
</template>

<script>
export default {
  data(){
      return{
        defaultProps: {
          children: "childList",
          label: "name",
        },
        data: [
            {
                "id": 1,
                "bomNo": "B0000001",
                "partNo": "-1",
                "name": "头部组件",
                "chPartNo": "M0000016",
                "unit": "个",
                "chQty": 1.0000,
                "manufacturer": "自制",
                "specification": "红",
                "childList": [
                    {
                        "id": 2,
                        "bomNo": "B0000001",
                        "partNo": "M0000016",
                        "name": "头部塑料件",
                        "chPartNo": "M0000012",
                        "unit": "个",
                        "chQty": 1.0000,
                        "manufacturer": "华塑塑料",
                        "specification": "2",
                        "childList": []
                    },
                    {
                        "id": 3,
                        "bomNo": "B0000001",
                        "partNo": "M0000016",
                        "name": "笔头软套",
                        "chPartNo": "M0000010",
                        "unit": "个",
                        "chQty": 1.0000,
                        "manufacturer": "迈瑞克",
                        "specification": "红",
                        "childList": []
                    }
                ]
            },
            {
                "id": 4,
                "bomNo": "B0000001",
                "partNo": "-1",
                "name": "红笔芯",
                "chPartNo": "M0000004",
                "unit": "个",
                "chQty": 1.0000,
                "manufacturer": "得力",
                "specification": "0.38mm",
                "childList": []
            },
            {
                "id": 5,
                "bomNo": "B0000001",
                "partNo": "-1",
                "name": "弹簧",
                "chPartNo": "M0000001",
                "unit": "个",
                "chQty": 2.0000,
                "manufacturer": "得力",
                "specification": "2A",
                "childList": []
            },
            {
                "id": 6,
                "bomNo": "B0000001",
                "partNo": "-1",
                "name": "按动组件",
                "chPartNo": "M0000015",
                "unit": "个",
                "chQty": 1.0000,
                "manufacturer": "自制",
                "specification": "1v",
                "childList": [
                    {
                        "id": 7,
                        "bomNo": "B0000001",
                        "partNo": "M0000015",
                        "name": "按动塑料件1",
                        "chPartNo": "M0000011",
                        "unit": "个",
                        "chQty": 1.0000,
                        "manufacturer": "华塑塑料",
                        "specification": "1",
                        "childList": []
                    },
                    {
                        "id": 8,
                        "bomNo": "B0000001",
                        "partNo": "M0000015",
                        "name": "按动塑料件2",
                        "chPartNo": "M0000013",
                        "unit": "个",
                        "chQty": 1.0000,
                        "manufacturer": "华塑塑料",
                        "specification": "3",
                        "childList": []
                    },
                    {
                        "id": 9,
                        "bomNo": "B0000001",
                        "partNo": "M0000015",
                        "name": "按动塑料件3",
                        "chPartNo": "M0000014",
                        "unit": "个",
                        "chQty": 2.0000,
                        "manufacturer": "华塑塑料",
                        "specification": "4",
                        "childList": []
                    }
                ]
            }
        ]

      }
  }
};
</script>
<style></style>