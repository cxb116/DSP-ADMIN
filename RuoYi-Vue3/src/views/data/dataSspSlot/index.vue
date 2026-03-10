<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="媒体用户" prop="meidaId">
        <el-input
          v-model="queryParams.meidaId"
          placeholder="请输入媒体用户"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="应用" prop="appId">
        <el-input
          v-model="queryParams.appId"
          placeholder="请输入应用"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="媒体广告位" label-width="90" prop="sspSlotId">
        <el-input
          v-model="queryParams.sspSlotId"
          placeholder="请输入媒体广告位"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预算广告位" label-width="90" prop="dspSlotId">
        <el-input
          v-model="queryParams.dspSlotId"
          placeholder="请输入预算广告位"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预算广告位编号" label-width="110" prop="dspSlotCode">
        <el-input
          v-model="queryParams.dspSlotCode"
          placeholder="请输入预算广告位编号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间" label-width="90">
        <el-date-picker
          v-model="dateRange"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date(2026, 1, 1, 0, 0, 0), new Date(2026, 1, 1, 23, 59, 59)]"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-radio-group v-model="tableType" @change="handleTableTypeChange">
          <el-radio-button label="day">天表</el-radio-button>
          <el-radio-button label="hour">小时表</el-radio-button>
        </el-radio-group>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['data:data_ssp_slot:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['data:data_ssp_slot:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['data:data_ssp_slot:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['data:data_ssp_slot:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="data_ssp_slotList" @selection-change="handleSelectionChange">
      <el-table-column label="日期" align="center" width="120" prop="date">
        <template #default="scope">
          <span v-if="scope.row.date">{{ formatDate(scope.row.date) }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
<!--      <el-table-column label="主键" align="center" prop="id" />-->
<!--      <el-table-column label="媒体用户" align="center" width="55" prop="meidaId" />-->
      <el-table-column label="应用" align="center" width="200" prop="appName">
        <template #default="scope">
          <span v-if="scope.row.appName">{{ scope.row.appName }}（{{ scope.row.appId }}）</span>
          <span v-else>{{ scope.row.appId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="媒体广告位" align="center" width="200" prop="sspSlotName">
        <template #default="scope">
          <span v-if="scope.row.sspSlotName">{{ scope.row.sspSlotName }}（{{ scope.row.sspSlotId }}）</span>
          <span v-else>{{ scope.row.sspSlotId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="预算广告位" align="center" width="200" prop="dspSlotName">
        <template #default="scope">
          <span v-if="scope.row.dspSlotName">{{ scope.row.dspSlotName }}（{{ scope.row.dspSlotId }}）</span>
          <span v-else>{{ scope.row.dspSlotId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="预算广告位编号" align="center" width="270" prop="dspSlotCode" />
      <el-table-column label="展示PV" align="center" width="100" prop="showPv" />
      <el-table-column label="点击PV" align="center" width="100" prop="clickPv" />
      <el-table-column label="请求PV" align="center" width="100" prop="reqPv" />
      <el-table-column label="丢弃请求" align="center" width="100" prop="discard" />
      <el-table-column label="返回PV" align="center" width="100" prop="retPv" />
      <el-table-column label="成本(分)" align="center" width="100" prop="spend" />
      <el-table-column label="收入(分)" align="center" width="100" prop="income" />
      <el-table-column label="eCPM" align="center" width="100" prop="activatePv" />
      <el-table-column label="CPC" align="center" width="100" prop="activatePv" />
<!--      <el-table-column label="折后点击" align="center" width="100"  prop="discountClickPv" />-->
<!--      <el-table-column label="折后展示" align="center" width="100" prop="discountShowPv" />-->
<!--      <el-table-column label="调起成功" align="center" width="100" prop="dplsuccPv" />-->
<!--      <el-table-column label="完成量" align="center" width="100" prop="completePv" />-->
<!--      <el-table-column label="安装量" align="center" width="100" prop="installPv" />-->
<!--      <el-table-column label="激活量" align="center" width="100" prop="activatePv" />-->

<!--      <el-table-column label="日期 yyyyMMdd" align="center" prop="date" />-->
      <el-table-column label="创建时间" align="center" width="230" prop="createdAt" :formatter="formatTimestamp" />
<!--      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">-->
<!--        <template #default="scope">-->
<!--          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['data:data_ssp_slot:edit']">修改</el-button>-->
<!--          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['data:data_ssp_slot:remove']">删除</el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改媒体数据报表对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="data_ssp_slotRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="媒体用户Id" prop="meidaId">
          <el-input v-model="form.meidaId" placeholder="请输入媒体用户Id" />
        </el-form-item>
        <el-form-item label="应用ID" prop="appId">
          <el-input v-model="form.appId" placeholder="请输入应用ID" />
        </el-form-item>
        <el-form-item label="SSP广告位ID" prop="sspSlotId">
          <el-input v-model="form.sspSlotId" placeholder="请输入SSP广告位ID" />
        </el-form-item>
        <el-form-item label="DSP广告位ID" prop="dspSlotId">
          <el-input v-model="form.dspSlotId" placeholder="请输入DSP广告位ID" />
        </el-form-item>
        <el-form-item label="预算广告位编号" prop="dspSlotCode">
          <el-input v-model="form.dspSlotCode" placeholder="请输入预算广告位编号" />
        </el-form-item>
        <el-form-item label="展示PV" prop="showPv">
          <el-input v-model="form.showPv" placeholder="请输入展示PV" />
        </el-form-item>
        <el-form-item label="展示UV" prop="showUv">
          <el-input v-model="form.showUv" placeholder="请输入展示UV" />
        </el-form-item>
        <el-form-item label="点击PV" prop="clickPv">
          <el-input v-model="form.clickPv" placeholder="请输入点击PV" />
        </el-form-item>
        <el-form-item label="点击UV" prop="clickUv">
          <el-input v-model="form.clickUv" placeholder="请输入点击UV" />
        </el-form-item>
        <el-form-item label="请求PV" prop="reqPv">
          <el-input v-model="form.reqPv" placeholder="请输入请求PV" />
        </el-form-item>
        <el-form-item label="请求UV" prop="reqUv">
          <el-input v-model="form.reqUv" placeholder="请输入请求UV" />
        </el-form-item>
        <el-form-item label="丢弃请求" prop="discard">
          <el-input v-model="form.discard" placeholder="请输入丢弃请求" />
        </el-form-item>
        <el-form-item label="返回PV" prop="retPv">
          <el-input v-model="form.retPv" placeholder="请输入返回PV" />
        </el-form-item>
        <el-form-item label="返回UV" prop="retUv">
          <el-input v-model="form.retUv" placeholder="请输入返回UV" />
        </el-form-item>
        <el-form-item label="成本(分)" prop="spend">
          <el-input v-model="form.spend" placeholder="请输入成本(分)" />
        </el-form-item>
        <el-form-item label="收入(分)" prop="income">
          <el-input v-model="form.income" placeholder="请输入收入(分)" />
        </el-form-item>
        <el-form-item label="折后点击" prop="discountClickPv">
          <el-input v-model="form.discountClickPv" placeholder="请输入折后点击" />
        </el-form-item>
        <el-form-item label="折后展示" prop="discountShowPv">
          <el-input v-model="form.discountShowPv" placeholder="请输入折后展示" />
        </el-form-item>
        <el-form-item label="调起成功" prop="dplsuccPv">
          <el-input v-model="form.dplsuccPv" placeholder="请输入调起成功" />
        </el-form-item>
        <el-form-item label="完成量" prop="completePv">
          <el-input v-model="form.completePv" placeholder="请输入完成量" />
        </el-form-item>
        <el-form-item label="安装量" prop="installPv">
          <el-input v-model="form.installPv" placeholder="请输入安装量" />
        </el-form-item>
        <el-form-item label="激活量" prop="activatePv">
          <el-input v-model="form.activatePv" placeholder="请输入激活量" />
        </el-form-item>
        <el-form-item label="日期 yyyyMMdd" prop="date">
          <el-input v-model="form.date" placeholder="请输入日期 yyyyMMdd" />
        </el-form-item>
        <el-form-item label="创建时间戳" prop="createdAt">
          <el-input v-model="form.createdAt" placeholder="请输入创建时间戳" />
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

<script setup name="Data_ssp_slot">
import { listData_ssp_slot, getData_ssp_slot, delData_ssp_slot, addData_ssp_slot, updateData_ssp_slot } from "@/api/data/dataSspSlot.js"

const { proxy } = getCurrentInstance()

const data_ssp_slotList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const dateRange = ref([])
const tableType = ref('day') // 表类型: 'day' 或 'hour'

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    meidaId: null,
    appId: null,
    sspSlotId: null,
    dspSlotId: null,
    dspSlotCode: null,
    showPv: null,
    showUv: null,
    clickPv: null,
    clickUv: null,
    reqPv: null,
    reqUv: null,
    discard: null,
    retPv: null,
    retUv: null,
    spend: null,
    income: null,
    discountClickPv: null,
    discountShowPv: null,
    dplsuccPv: null,
    completePv: null,
    installPv: null,
    activatePv: null,
    date: null,
    createdAt: null
  },
  rules: {
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 格式化时间戳 */
function formatTimestamp(row, column, cellValue) {
  if (!cellValue) return ''
  const timestamp = parseInt(cellValue)
  // 如果时间戳是秒级的，转换为毫秒
  const date = new Date(timestamp.toString().length === 10 ? timestamp * 1000 : timestamp)
  return date.toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit',
    hour12: false
  })
}

/** 格式化日期 yyyyMMddHH */
function formatDate(dateValue) {
  if (!dateValue) return ''
  const dateStr = dateValue.toString()
  // yyyyMMddHH -> yyyy-MM-dd-HH
  if (dateStr.length === 10) {
    return `${dateStr.substring(0, 4)}-${dateStr.substring(4, 6)}-${dateStr.substring(6, 8)}-${dateStr.substring(8, 10)}`
  }
  // yyyyMMdd -> yyyy-MM-dd
  if (dateStr.length === 8) {
    return `${dateStr.substring(0, 4)}-${dateStr.substring(4, 6)}-${dateStr.substring(6, 8)}`
  }
  return dateStr
}

/** 生成表名 */
function generateTableName() {
  const now = new Date()
  const year = now.getFullYear()
  const month = String(now.getMonth() + 1).padStart(2, '0')

  if (tableType.value === 'day') {
    // 天表: data_ssp_slot_day_YYYYMM
    return `data_ssp_slot_day_${year}${month}`
  } else {
    // 小时表: data_ssp_slot_hour_YYYYMM
    return `data_ssp_slot_hour_${year}${month}`
  }
}

/** 表类型切换处理 */
function handleTableTypeChange() {
  queryParams.value.pageNum = 1
  getList()
}

/** 查询媒体数据报表列表 */
function getList() {
  loading.value = true
  const params = proxy.addDateRange(queryParams.value, dateRange.value, 'createdAt')
  // 添加动态表名参数
  params.tableName = generateTableName()
  listData_ssp_slot(params).then(response => {
    data_ssp_slotList.value = response.rows
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
    meidaId: null,
    appId: null,
    sspSlotId: null,
    dspSlotId: null,
    dspSlotCode: null,
    showPv: null,
    showUv: null,
    clickPv: null,
    clickUv: null,
    reqPv: null,
    reqUv: null,
    discard: null,
    retPv: null,
    retUv: null,
    spend: null,
    income: null,
    discountClickPv: null,
    discountShowPv: null,
    dplsuccPv: null,
    completePv: null,
    installPv: null,
    activatePv: null,
    date: null,
    createdAt: null
  }
  proxy.resetForm("data_ssp_slotRef")
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef")
  dateRange.value = []
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
  title.value = "添加媒体数据报表"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  getData_ssp_slot(_id).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改媒体数据报表"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["data_ssp_slotRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateData_ssp_slot(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addData_ssp_slot(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除媒体数据报表编号为"' + _ids + '"的数据项？').then(function() {
    return delData_ssp_slot(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('data/data_ssp_slot/export', {
    ...queryParams.value,
    tableName: generateTableName()
  }, `data_ssp_slot_${new Date().getTime()}.xlsx`)
}

getList()
</script>
