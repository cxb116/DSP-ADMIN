<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="媒体Id" prop="mediaId">
        <el-input
          v-model="queryParams.mediaId"
          placeholder="请输入媒体Id"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="应用名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入应用名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="包名" prop="pkg">
        <el-input
          v-model="queryParams.pkg"
          placeholder="请输入包名"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="下载地址" prop="downloadUrl">
        <el-input
          v-model="queryParams.downloadUrl"
          placeholder="请输入下载地址"
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
          v-hasPermi="['flow:app:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['flow:app:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['flow:app:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['flow:app:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="appList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="媒体Id" align="center" prop="mediaId" />
      <el-table-column label="应用名称" align="center" prop="name" />
      <el-table-column label="操作系统类型，1=Android，2=iOS" align="center" prop="osType" />
      <el-table-column label="接入方式:1=API，2=SDK" align="center" prop="accessType" />
      <el-table-column label="包名" align="center" prop="pkg" />
      <el-table-column label="下载地址" align="center" prop="downloadUrl" />
      <el-table-column label="状态：1正常 0禁用 2审核中 3拒绝" align="center" prop="enable" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['flow:app:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['flow:app:remove']">删除</el-button>
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

    <!-- 添加或修改应用管理对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="appRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="媒体Id" prop="mediaId">
          <el-input v-model="form.mediaId" placeholder="请输入媒体Id" />
        </el-form-item>
        <el-form-item label="应用名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入应用名称" />
        </el-form-item>
        <el-form-item label="包名" prop="pkg">
          <el-input v-model="form.pkg" placeholder="请输入包名" />
        </el-form-item>
        <el-form-item label="下载地址" prop="downloadUrl">
          <el-input v-model="form.downloadUrl" placeholder="请输入下载地址" />
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

<script setup name="App">
import { listApp, getApp, delApp, addApp, updateApp } from "@/api/flow/app"

const { proxy } = getCurrentInstance()

const appList = ref([])
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
    name: null,
    osType: null,
    accessType: null,
    pkg: null,
    downloadUrl: null,
    enable: null,
  },
  rules: {
    mediaId: [
      { required: true, message: "媒体Id不能为空", trigger: "blur" }
    ],
    name: [
      { required: true, message: "应用名称不能为空", trigger: "blur" }
    ],
    osType: [
      { required: true, message: "操作系统类型，1=Android，2=iOS不能为空", trigger: "change" }
    ],
    accessType: [
      { required: true, message: "接入方式:1=API，2=SDK不能为空", trigger: "change" }
    ],
    enable: [
      { required: true, message: "状态：1正常 0禁用 2审核中 3拒绝不能为空", trigger: "blur" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询应用管理列表 */
function getList() {
  loading.value = true
  listApp(queryParams.value).then(response => {
    appList.value = response.rows
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
    name: null,
    osType: null,
    accessType: null,
    pkg: null,
    downloadUrl: null,
    enable: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    remark: null
  }
  proxy.resetForm("appRef")
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
  title.value = "添加应用管理"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  getApp(_id).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改应用管理"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["appRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateApp(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addApp(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除应用管理编号为"' + _ids + '"的数据项？').then(function() {
    return delApp(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('flow/app/export', {
    ...queryParams.value
  }, `app_${new Date().getTime()}.xlsx`)
}

getList()
</script>
