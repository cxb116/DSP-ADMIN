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
        <el-input
          v-model="queryParams.adScene"
          placeholder="请输入广告类型id"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预算方广告位" prop="dspSlotCode">
        <el-input
          v-model="queryParams.dspSlotCode"
          placeholder="请输入预算方广告位"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="公司ID" prop="companyId">
        <el-input
          v-model="queryParams.companyId"
          placeholder="请输入公司ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产品ID" prop="productId">
        <el-input
          v-model="queryParams.productId"
          placeholder="请输入产品ID"
          clearable
          @keyup.enter="handleQuery"
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
      <el-table-column label="预算方广告位" align="center" prop="dspSlotCode" />
      <el-table-column label="广告位名称" align="center" prop="name" />
      <el-table-column label="操作系统，1=Android，2=iOS" align="center" prop="osType" />
      <el-table-column label="广告类型" align="center" prop="adScene" />
      <el-table-column label="结算方式" align="center" prop="dspPayType" />
      <el-table-column label="成交系数" align="center" prop="dspDealRatio" />
      <el-table-column label="公司" align="center" prop="companyId" />
      <el-table-column label="产品" align="center" prop="productId" />
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
      <el-form ref="infoRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="广告位名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入广告位名称" />
        </el-form-item>
        <el-form-item label="操作系统类型" prop="osType">
          <el-select v-model="form.osType" placeholder="请选择操作系统类型">
            <el-option label="Android" :value="1" />
            <el-option label="iOS" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="广告类型id" prop="adScene">
          <el-input v-model="form.adScene" placeholder="请输入广告类型id" />
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
          <el-input v-model="form.dspAppVer" placeholder="请输入应用版本号，支持多个，空格分割" />
        </el-form-item>
        <el-form-item label="应用商店版本号" prop="dspAppStoreVer">
          <el-input v-model="form.dspAppStoreVer" placeholder="请输入应用商店版本号，支持多个，空格分割" />
        </el-form-item>
        <el-form-item label="价格加密key" prop="priceEncryptKey">
          <el-input v-model="form.priceEncryptKey" placeholder="请输入价格加密key" />
        </el-form-item>
        <el-form-item label="应用商店地址" prop="dspAppStoreLink">
          <el-input v-model="form.dspAppStoreLink" placeholder="请输入应用商店地址，支持多个，空格分割" />
        </el-form-item>
        <el-form-item label="结算方式" prop="dspPayType">
          <el-select v-model="form.dspPayType" placeholder="请选择上游预算结算方式">
            <el-option label="分成" :value="1" />
            <el-option label="RTB" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="成交系数" prop="dspDealRatio">
          <el-input v-model="form.dspDealRatio" placeholder="请输入成交系数，0到100，单位%，给上游预算出价打折扣  " />
        </el-form-item>
        <el-form-item label="公司名称" prop="companyId">
          <el-input v-model="form.companyId" placeholder="请输入公司ID" />
        </el-form-item>
        <el-form-item label="产品名称" prop="productId">
          <el-input v-model="form.productId" placeholder="请输入产品ID" />
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

const { proxy } = getCurrentInstance()

const infoList = ref([])
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
  },
  rules: {
    name: [
      { required: true, message: "广告位名称不能为空", trigger: "blur" }
    ],
    osType: [
      { required: true, message: "操作系统类型，1=Android，2=iOS不能为空", trigger: "change" }
    ],
    adScene: [
      { required: true, message: "广告类型id不能为空", trigger: "blur" }
    ],
    dspSlotCode: [
      { required: true, message: "预算方广告位不能为空", trigger: "blur" }
    ],
    dspPayType: [
      { required: true, message: "上游预算结算方式，1=分成，2=RTB不能为空", trigger: "change" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

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
      dspPayType: data.dspPayType !== null ? Number(data.dspPayType) : null,
      companyId: data.companyId !== null ? Number(data.companyId) : null,
      productId: data.productId !== null ? Number(data.productId) : null,
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

getList()
</script>
