<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
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
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['system:dataDspSlot:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="dataDspSlotList" @selection-change="handleSelectionChange">
      <el-table-column label="日期" align="center" width="150" prop="date">
        <template #default="scope">
          <span v-if="scope.row.date">{{ formatDate(scope.row.date) }}</span>
          <span v-else>-</span>
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
      <el-table-column label="请求丢失率(%)" align="center" width="130" prop="requestLossRate">
        <template #default="scope">
          <span v-if="scope.row.requestLossRate !== null && scope.row.requestLossRate !== undefined">
            {{ scope.row.requestLossRate.toFixed(2) }}%
          </span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="返回PV" align="center" width="100" prop="retPv" />
      <el-table-column label="展现率(%)" align="center" width="120" prop="showRate">
        <template #default="scope">
          <span v-if="scope.row.showRate !== null && scope.row.showRate !== undefined">
            {{ scope.row.showRate.toFixed(2) }}%
          </span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="点击率(%)" align="center" width="120" prop="clickRate">
        <template #default="scope">
          <span v-if="scope.row.clickRate !== null && scope.row.clickRate !== undefined">
            {{ scope.row.clickRate.toFixed(2) }}%
          </span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="填充率(%)" align="center" width="120" prop="fillRate">
        <template #default="scope">
          <span v-if="scope.row.fillRate !== null && scope.row.fillRate !== undefined">
            {{ scope.row.fillRate.toFixed(2) }}%
          </span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="预算流水(元)" align="center" width="120" prop="spend">
        <template #default="scope">
          <span v-if="scope.row.spend !== null && scope.row.spend !== undefined">
            {{ (scope.row.spend / 100).toFixed(2) }}
          </span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="收入(元)" align="center" width="100" prop="income">
        <template #default="scope">
          <span v-if="scope.row.income !== null && scope.row.income !== undefined">
            {{ (scope.row.income / 100).toFixed(2) }}
          </span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="eCPM(元)" align="center" width="120" prop="ecpm">
        <template #default="scope">
          <span v-if="scope.row.ecpm !== null && scope.row.ecpm !== undefined">
            {{ scope.row.ecpm.toFixed(2) }}
          </span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="折后点击" align="center" width="100" prop="discountClickPv" />
      <el-table-column label="折后展示" align="center" width="100" prop="discountShowPv" />
<!--      <el-table-column label="调起成功" align="center" prop="dplsuccPv" />-->
<!--      <el-table-column label="完成量" align="center" prop="completePv" />-->
<!--      <el-table-column label="安装量" align="center" prop="installPv" />-->
<!--      <el-table-column label="激活量" align="center" prop="activatePv" />-->
<!--      <el-table-column label="时间(yyyyMMdd / yyyyMMddHH)" align="center" prop="date" />-->
<!--      <el-table-column label="创建时间" align="center" width="270" prop="createdAt" :formatter="formatTimestamp" />-->
<!--      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">-->
<!--        <template #default="scope">-->
<!--          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:dataDspSlot:edit']">修改</el-button>-->
<!--          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:dataDspSlot:remove']">删除</el-button>-->
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
  </div>
</template>

<script setup name="DataDspSlot">
import { listDataDspSlot, getDataDspSlot, delDataDspSlot, addDataDspSlot, updateDataDspSlot } from "@/api/data/dataDspSlot"

const { proxy } = getCurrentInstance()

const dataDspSlotList = ref([])
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
  },
  rules: {
    dspSlotCode: [
      { required: true, message: "预算广告位不能为空", trigger: "blur" }
    ],
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

/** 格式化日期 yyyyMMdd / yyyyMMddHH */
function formatDate(dateValue) {
  if (!dateValue) return ''
  const dateStr = dateValue.toString()
  // yyyyMMddHH -> yyyy-MM-dd HH:00
  if (dateStr.length === 10) {
    return `${dateStr.substring(0, 4)}-${dateStr.substring(4, 6)}-${dateStr.substring(6, 8)} ${dateStr.substring(8, 10)}:00`
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
    // 天表: data_dsp_slot_day_YYYYMM
    return `data_dsp_slot_day_${year}${month}`
  } else {
    // 小时表: data_dsp_slot_hour_YYYYMM
    return `data_dsp_slot_hour_${year}${month}`
  }
}

/** 表类型切换处理 */
function handleTableTypeChange() {
  queryParams.value.pageNum = 1
  getList()
}

/** 查询预算报表列表 */
function getList() {
  loading.value = true
  const params = proxy.addDateRange(queryParams.value, dateRange.value, 'createdAt')
  // 添加动态表名参数
  params.tableName = generateTableName()
  listDataDspSlot(params).then(response => {
    dataDspSlotList.value = response.rows
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
  proxy.resetForm("dataDspSlotRef")
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
  title.value = "添加预算报表"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  getDataDspSlot(_id).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改预算报表"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["dataDspSlotRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateDataDspSlot(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addDataDspSlot(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除预算报表编号为"' + _ids + '"的数据项？').then(function() {
    return delDataDspSlot(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('system/dataDspSlot/export', {
    ...queryParams.value,
    tableName: generateTableName()
  }, `dataDspSlot_${new Date().getTime()}.xlsx`)
}

getList()
</script>
