<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="广告位名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入广告位名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="广告类型" prop="adScene">
        <el-select
          v-model="queryParams.adScene"
          placeholder="请选择广告类型"
          clearable
        >
          <el-option
            v-for="dict in ad_scene"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="预算方广告位" prop="dspSlotCode">
        <el-input
          v-model="queryParams.dspSlotCode"
          placeholder="请输入预算方广告位"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预算方APPKEY" prop="dspAppKey">
        <el-input
          v-model="queryParams.dspAppKey"
          placeholder="请输入预算方APPKEY"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预算方AppId" prop="dspAppId">
        <el-input
          v-model="queryParams.dspAppId"
          placeholder="请输入预算方AppId"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预算方应用包名" prop="dspAppPkg">
        <el-input
          v-model="queryParams.dspAppPkg"
          placeholder="请输入预算方应用包名"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="成交系数" prop="dspDealRatio">
        <el-input
          v-model="queryParams.dspDealRatio"
          placeholder="请输入成交系数"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产品" prop="companyProductId">
        <el-cascader
          v-model="queryParams.companyProductId"
          :options="cascaderOptions"
          :props="{ expandTrigger: 'hover' }"
          placeholder="请选择产品"
          clearable
          @change="handleQueryCascaderChange"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['budget:info:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['budget:info:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['budget:info:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['budget:info:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="广告位名称" align="center" prop="name">
        <template #default="scope">
          {{ formatSlotName(scope.row) }}
        </template>
      </el-table-column>
      <el-table-column label="操作系统类型" align="center" prop="osType">
        <template #default="scope">
          <dict-tag :options="os_type" :value="scope.row.osType" />
        </template>
      </el-table-column>
      <el-table-column label="广告类型" align="center" prop="adScene">
        <template #default="scope">
          <dict-tag :options="ad_scene" :value="scope.row.adScene" />
        </template>
      </el-table-column>
      <el-table-column label="预算方广告位" align="center" prop="dspSlotCode" />
      <el-table-column label="预算方APPKEY" align="center" prop="dspAppKey" />
      <el-table-column label="预算方AppId" align="center" prop="dspAppId" />
      <el-table-column label="预算方应用包名" align="center" prop="dspAppPkg" />
      <el-table-column label="结算方式" align="center" prop="dspPayType">
        <template #default="scope">
          <dict-tag :options="ssp_pay_type" :value="scope.row.dspPayType" />
        </template>
      </el-table-column>
      <el-table-column label="成交系数" align="center" prop="dspDealRatio" />
      <el-table-column label="公司" align="center" prop="companyId">
        <template #default="scope">
          {{ getCompanyName(scope.row.companyId) }}
        </template>
      </el-table-column>
      <el-table-column label="产品" align="center" prop="productId">
        <template #default="scope">
          {{ getProductName(scope.row.productId) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['budget:info:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['budget:info:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改预算广告位对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="infoRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="广告位名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入广告位名称" />
        </el-form-item>
        <el-form-item label="操作系统类型" prop="osType">
          <el-select v-model="form.osType" placeholder="请选择操作系统类型">
            <el-option
              v-for="dict in os_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="广告类型" prop="adScene">
          <el-select v-model="form.adScene" placeholder="请选择广告类型">
            <el-option
              v-for="dict in ad_scene"
              :key="dict.value"
              :label="dict.label"
              :value="Number(dict.value)"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="预算方广告位" prop="dspSlotCode">
          <el-input v-model="form.dspSlotCode" placeholder="请输入预算方广告位" />
        </el-form-item>
        <el-form-item label="预算方APPKEY" prop="dspAppKey">
          <el-input v-model="form.dspAppKey" placeholder="请输入预算方APPKEY" />
        </el-form-item>
        <el-form-item label="预算方APPSECRET" prop="dspAppSecret">
          <el-input v-model="form.dspAppSecret" placeholder="请输入预算方APPSECRET" />
        </el-form-item>
        <el-form-item label="预算方AppId" prop="dspAppId">
          <el-input v-model="form.dspAppId" placeholder="请输入预算方AppId" />
        </el-form-item>
        <el-form-item label="预算方应用包名" prop="dspAppPkg">
          <el-input v-model="form.dspAppPkg" placeholder="请输入预算方应用包名" />
        </el-form-item>
        <el-form-item label="应用版本号" prop="dspAppVer">
          <el-input v-model="form.dspAppVer" placeholder="请输入应用版本号" />
        </el-form-item>
        <el-form-item label="应用商店版本号" prop="dspAppStoreVer">
          <el-input v-model="form.dspAppStoreVer" placeholder="请输入应用商店版本号" />
        </el-form-item>
        <el-form-item label="价格加密key" prop="priceEncryptKey">
          <el-input v-model="form.priceEncryptKey" placeholder="请输入价格加密key" />
        </el-form-item>
        <el-form-item label="应用商店地址" prop="dspAppStoreLink">
          <el-input v-model="form.dspAppStoreLink" placeholder="请输入应用商店地址" />
        </el-form-item>
        <el-form-item label="结算方式" prop="dspPayType">
          <el-select v-model="form.dspPayType" placeholder="请选择结算方式">
            <el-option
              v-for="dict in ssp_pay_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="成交系数" prop="dspDealRatio">
          <el-input v-model="form.dspDealRatio" placeholder="请输入成交系数" />
        </el-form-item>
        <el-form-item label="产品" prop="companyProductId" required>
          <el-cascader
            v-model="form.companyProductId"
            :options="cascaderOptions"
            :props="{ expandTrigger: 'hover' }"
            placeholder="请选择产品"
            clearable
            @change="handleCascaderChange"
          />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Info">
import { listInfo, getInfo, delInfo, addInfo, updateInfo } from "@/api/budget/info"
import { listCompany } from "@/api/budget/company"
import { listProduct } from "@/api/budget/product"

const { proxy } = getCurrentInstance()

// 获取字典数据
const { os_type, ad_scene, ssp_pay_type } = proxy.useDict("os_type", "ad_scene", "ssp_pay_type")

const infoList = ref([])
const companyList = ref([])
const productList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    name: null,
    osType: null,
    adScene: null,
    dspSlotCode: null,
    dspAppKey: null,
    dspAppSecret: null,
    dspAppId: null,
    dspAppPkg: null,
    dspAppVer: null,
    dspAppStoreVer: null,
    priceEncryptKey: null,
    dspAppStoreLink: null,
    dspPayType: null,
    dspDealRatio: null,
    companyId: null,
    productId: null,
    companyProductId: [],
  },
  rules: {
    name: [
      { required: true, message: "广告位名称不能为空", trigger: "blur" }
    ],
    osType: [
      { required: true, message: "操作系统类型不能为空", trigger: "change" }
    ],
    adScene: [
      { required: true, message: "广告类型不能为空", trigger: "change" }
    ],
    dspSlotCode: [
      { required: true, message: "预算方广告位不能为空", trigger: "blur" }
    ],
    dspPayType: [
      { required: true, message: "结算方式不能为空", trigger: "change" }
    ],
    companyProductId: [
      { required: true, message: "产品不能为空", trigger: "change" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 级联选择器选项 */
const cascaderOptions = computed(() => {
  return companyList.value.map(company => ({
    value: company.id,
    label: company.name,
    children: productList.value
      .filter(product => product.companyId === company.id)
      .map(product => ({
        value: product.id,
        label: product.name
      }))
  }))
})

/** 查询公司列表 */
function getCompanyList() {
  listCompany().then(response => {
    companyList.value = response.rows || []
  })
}

/** 查询产品列表 */
function getProductList() {
  listProduct().then(response => {
    productList.value = response.rows || []
  })
}

/** 根据公司ID获取公司名称 */
function getCompanyName(companyId) {
  const company = companyList.value.find(item => item.id === companyId)
  return company ? company.name : '-'
}

/** 根据产品ID获取产品名称 */
function getProductName(productId) {
  const product = productList.value.find(item => item.id === productId)
  return product ? product.name : '-'
}

/** 格式化广告位名称：name(id) */
function formatSlotName(row) {
  return `${row.name}(${row.id})`
}

/** 级联选择器变化处理 */
function handleCascaderChange(value) {
  if (value && value.length === 2) {
    form.value.companyId = value[0]
    form.value.productId = value[1]
  } else {
    form.value.companyId = null
    form.value.productId = null
  }
}

/** 查询条件级联选择器变化处理 */
function handleQueryCascaderChange(value) {
  if (value && value.length === 2) {
    queryParams.value.companyId = value[0]
    queryParams.value.productId = value[1]
  } else if (value && value.length === 1) {
    queryParams.value.companyId = value[0]
    queryParams.value.productId = null
  } else {
    queryParams.value.companyId = null
    queryParams.value.productId = null
  }
}

/** 查询预算广告位列表 */
function getList() {
  loading.value = true
  listInfo(queryParams.value).then(response => {
    infoList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

// 取消按钮
function cancel() {
  open.value = false
  reset()
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    name: null,
    osType: null,
    adScene: null,
    dspSlotCode: null,
    dspAppKey: null,
    dspAppSecret: null,
    dspAppId: null,
    dspAppPkg: null,
    dspAppVer: null,
    dspAppStoreVer: null,
    priceEncryptKey: null,
    dspAppStoreLink: null,
    dspPayType: null,
    dspDealRatio: null,
    companyId: null,
    productId: null,
    companyProductId: [],
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    remark: null
  }
  proxy.resetForm("infoRef")
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加预算广告位"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  getInfo(_id).then(response => {
    const data = response.data
    form.value = {
      ...data,
      osType: data.osType !== null ? Number(data.osType) : null,
      adScene: data.adScene !== null ? Number(data.adScene) : null,
      dspPayType: data.dspPayType !== null ? String(data.dspPayType) : null,
      companyId: data.companyId !== null ? Number(data.companyId) : null,
      productId: data.productId !== null ? Number(data.productId) : null,
      companyProductId: data.companyId && data.productId ? [Number(data.companyId), Number(data.productId)] : []
    }
    open.value = true
    title.value = "修改预算广告位"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["infoRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateInfo(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addInfo(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value
  proxy.$modal.confirm('是否确认删除预算广告位编号为"' + _ids + '"的数据项？').then(function() {
    return delInfo(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('budget/info/export', {
    ...queryParams.value
  }, `info_${new Date().getTime()}.xlsx`)
}

getCompanyList()
getProductList()
getList()
</script>
