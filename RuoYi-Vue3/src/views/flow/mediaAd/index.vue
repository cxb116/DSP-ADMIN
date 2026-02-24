<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="媒体id" prop="mediaId">
        <el-input
          v-model="queryParams.mediaId"
          placeholder="请输入媒体id"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="应用id" prop="appId">
        <el-input
          v-model="queryParams.appId"
          placeholder="请输入应用id"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="广告位名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入广告位名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="内部广告位名称" prop="nameAlise">
        <el-input
          v-model="queryParams.nameAlise"
          placeholder="请输入内部广告位名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="广告类型id" prop="sceneId">
        <el-input
          v-model="queryParams.sceneId"
          placeholder="请输入广告类型id"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="下游媒体分成系数 " prop="sspDealRatio">
        <el-input
          v-model="queryParams.sspDealRatio"
          placeholder="请输入下游媒体分成系数 "
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="广告宽度" prop="width">
        <el-input
          v-model="queryParams.width"
          placeholder="请输入广告宽度"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="广告高度" prop="height">
        <el-input
          v-model="queryParams.height"
          placeholder="请输入广告高度"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态：1正常 0禁用 2审核中 3拒绝" prop="enable">
        <el-input
          v-model="queryParams.enable"
          placeholder="请输入状态：1正常 0禁用 2审核中 3拒绝"
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
          v-hasPermi="['flow:mediaAd:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['flow:mediaAd:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['flow:mediaAd:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['flow:mediaAd:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="mediaAdList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="媒体id" align="center" prop="mediaId" />
      <el-table-column label="应用id" align="center" prop="appId" />
      <el-table-column label="广告位名称" align="center" prop="name" />
      <el-table-column label="内部广告位名称" align="center" prop="nameAlise" />
      <el-table-column label="广告类型id" align="center" prop="sceneId" />
      <el-table-column label="下游媒体结算方式，1=分成，2=RTB" align="center" prop="sspPayType" />
      <el-table-column label="下游媒体分成系数 " align="center" prop="sspDealRatio" />
      <el-table-column label="广告宽度" align="center" prop="width" />
      <el-table-column label="广告高度" align="center" prop="height" />
      <el-table-column label="广告位图片" align="center" prop="adImage" width="100">
        <template #default="scope">
          <image-preview :src="scope.row.adImage" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="交互类型是否支持，(1：打开网页，2：deeplink，
3：直接下载应用；4: 广点通; 5 小程序跳转 6,应用商店下载，7 快应用 位掩码表示)" align="center" prop="interactionType" />
      <el-table-column label="状态：1正常 0禁用 2审核中 3拒绝" align="center" prop="enable" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['flow:mediaAd:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['flow:mediaAd:remove']">删除</el-button>
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

    <!-- 添加或修改媒体广告位对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="mediaAdRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="媒体id" prop="mediaId">
          <el-input v-model="form.mediaId" placeholder="请输入媒体id" />
        </el-form-item>
        <el-form-item label="应用id" prop="appId">
          <el-input v-model="form.appId" placeholder="请输入应用id" />
        </el-form-item>
        <el-form-item label="广告位名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入广告位名称" />
        </el-form-item>
        <el-form-item label="内部广告位名称" prop="nameAlise">
          <el-input v-model="form.nameAlise" placeholder="请输入内部广告位名称" />
        </el-form-item>
        <el-form-item label="广告类型id" prop="sceneId">
          <el-input v-model="form.sceneId" placeholder="请输入广告类型id" />
        </el-form-item>
        <el-form-item label="下游媒体分成系数 " prop="sspDealRatio">
          <el-input v-model="form.sspDealRatio" placeholder="请输入下游媒体分成系数 " />
        </el-form-item>
        <el-form-item label="广告宽度" prop="width">
          <el-input v-model="form.width" placeholder="请输入广告宽度" />
        </el-form-item>
        <el-form-item label="广告高度" prop="height">
          <el-input v-model="form.height" placeholder="请输入广告高度" />
        </el-form-item>
        <el-form-item label="广告位图片" prop="adImage">
          <image-upload v-model="form.adImage"/>
        </el-form-item>
        <el-form-item label="状态：1正常 0禁用 2审核中 3拒绝" prop="enable">
          <el-input v-model="form.enable" placeholder="请输入状态：1正常 0禁用 2审核中 3拒绝" />
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

<script setup name="MediaAd">
import { listMediaAd, getMediaAd, delMediaAd, addMediaAd, updateMediaAd } from "@/api/flow/mediaAd"

const { proxy } = getCurrentInstance()

const mediaAdList = ref([])
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
    mediaId: null,
    appId: null,
    name: null,
    nameAlise: null,
    sceneId: null,
    sspPayType: null,
    sspDealRatio: null,
    width: null,
    height: null,
    adImage: null,
    interactionType: null,
    enable: null,
  },
  rules: {
    mediaId: [
      { required: true, message: "媒体id不能为空", trigger: "blur" }
    ],
    appId: [
      { required: true, message: "应用id不能为空", trigger: "blur" }
    ],
    name: [
      { required: true, message: "广告位名称不能为空", trigger: "blur" }
    ],
    sceneId: [
      { required: true, message: "广告类型id不能为空", trigger: "blur" }
    ],
    sspPayType: [
      { required: true, message: "下游媒体结算方式，1=分成，2=RTB不能为空", trigger: "change" }
    ],
    enable: [
      { required: true, message: "状态：1正常 0禁用 2审核中 3拒绝不能为空", trigger: "blur" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询媒体广告位列表 */
function getList() {
  loading.value = true
  listMediaAd(queryParams.value).then(response => {
    mediaAdList.value = response.rows
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
    mediaId: null,
    appId: null,
    name: null,
    nameAlise: null,
    sceneId: null,
    sspPayType: null,
    sspDealRatio: null,
    width: null,
    height: null,
    adImage: null,
    interactionType: null,
    enable: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    remark: null
  }
  proxy.resetForm("mediaAdRef")
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
  title.value = "添加媒体广告位"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  getMediaAd(_id).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改媒体广告位"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["mediaAdRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateMediaAd(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addMediaAd(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除媒体广告位编号为"' + _ids + '"的数据项？').then(function() {
    return delMediaAd(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('flow/mediaAd/export', {
    ...queryParams.value
  }, `mediaAd_${new Date().getTime()}.xlsx`)
}

getList()
</script>
