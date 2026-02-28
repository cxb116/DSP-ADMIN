<template>
    <div class="app-container media-page" :class="{ 'dark-mode': isDark }">
        <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
            <el-form-item label="媒体名称" prop="name">
                <el-input
                    v-model="queryParams.name"
                    placeholder="请输入媒体名称"
                    clearable
                    @keyup.enter="handleQuery"
                />
            </el-form-item>
            <el-form-item label="公司名称" prop="mediaCompanyName">
                <el-input
                    v-model="queryParams.mediaCompanyName"
                    placeholder="请输入公司名称"
                    clearable
                    @keyup.enter="handleQuery"
                />
            </el-form-item>
            <el-form-item label="公司简称" prop="mediaCompanyShort">
                <el-input
                    v-model="queryParams.mediaCompanyShort"
                    placeholder="请输入公司简称"
                    clearable
                    @keyup.enter="handleQuery"
                />
            </el-form-item>
            <el-form-item label="公司地址" prop="mediaCompanyAddress">
                <el-input
                    v-model="queryParams.mediaCompanyAddress"
                    placeholder="请输入公司地址"
                    clearable
                    @keyup.enter="handleQuery"
                />
            </el-form-item>
            <el-form-item label="法人姓名" prop="mediaOwnerName">
                <el-input
                    v-model="queryParams.mediaOwnerName"
                    placeholder="请输入法人姓名"
                    clearable
                    @keyup.enter="handleQuery"
                />
            </el-form-item>
            <el-form-item label="联系电话" prop="contactPhone">
                <el-input
                    v-model="queryParams.contactPhone"
                    placeholder="请输入联系电话"
                    clearable
                    @keyup.enter="handleQuery"
                />
            </el-form-item>
            <el-form-item label="联系邮箱" prop="contactEmail">
                <el-input
                    v-model="queryParams.contactEmail"
                    placeholder="请输入联系邮箱"
                    clearable
                    @keyup.enter="handleQuery"
                />
            </el-form-item>
            <el-form-item label="状态" prop="enable">
                <el-select
                    v-model="queryParams.enable"
                    placeholder="请选择状态"
                    clearable
                    @keyup.enter="handleQuery"
                >
                    <el-option
                        v-for="dict in media_status"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                    />
                </el-select>
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
                    v-hasPermi="['flow:media:add']"
                >新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button
                    type="success"
                    plain
                    icon="Edit"
                    :disabled="single"
                    @click="handleUpdate"
                    v-hasPermi="['flow:media:edit']"
                >修改</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button
                    type="danger"
                    plain
                    icon="Delete"
                    :disabled="multiple"
                    @click="handleDelete"
                    v-hasPermi="['flow:media:remove']"
                >删除</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button
                    type="warning"
                    plain
                    icon="Download"
                    @click="handleExport"
                    v-hasPermi="['flow:media:export']"
                >导出</el-button>
            </el-col>
            <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="mediaList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="公司名称" align="center" prop="mediaCompanyName" />
            <el-table-column label="媒体名称" align="center" prop="name" />
            <el-table-column label="公司简称" align="center" prop="mediaCompanyShort" />
            <el-table-column label="公司地址" align="center" prop="mediaCompanyAddress" />
            <el-table-column label="法人姓名" align="center" prop="mediaOwnerName" />
            <el-table-column label="联系电话" align="center" prop="contactPhone" />
            <el-table-column label="联系邮箱" align="center" prop="contactEmail" />
            <el-table-column label="状态" align="center" prop="enable">
                <template #default="scope">
                    <dict-tag :options="media_status" :value="scope.row.enable"/>
                </template>
            </el-table-column>
           <el-table-column label="创建时间" align="center" prop="createTime" />
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template #default="scope">
                    <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['flow:media:edit']">修改</el-button>
                    <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['flow:media:remove']">删除</el-button>
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

        <!-- 添加或修改媒体管理对话框 -->
        <el-dialog :title="title" v-model="open" width="900px" append-to-body class="media-dialog">
            <el-form ref="mediaRef" :model="form" :rules="rules" label-width="120px" class="dialog-form">
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="媒体名称" prop="name">
                            <el-input v-model="form.name" placeholder="请输入媒体名称" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="账号名" prop="account">
                            <el-input v-model="form.account" placeholder="请输入账号名" />
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="密码" prop="password">
                            <el-input v-model="form.password" type="password" placeholder="请输入密码" show-password />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="状态" prop="enable">
                            <el-select v-model="form.enable" placeholder="请选择状态" style="width: 100%">
                                <el-option
                                    v-for="dict in media_status"
                                    :key="dict.value"
                                    :label="dict.label"
                                    :value="dict.value"
                                />
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-divider content-position="left">公司信息</el-divider>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="公司名称" prop="mediaCompanyName">
                            <el-input v-model="form.mediaCompanyName" placeholder="请输入公司名称" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="公司简称" prop="mediaCompanyShort">
                            <el-input v-model="form.mediaCompanyShort" placeholder="请输入公司简称" />
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="社会信用代码" prop="mediaCompanyCode">
                            <el-input v-model="form.mediaCompanyCode" placeholder="请输入统一社会信用代码" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="法人姓名" prop="mediaOwnerName">
                            <el-input v-model="form.mediaOwnerName" placeholder="请输入法人姓名" />
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="24">
                        <el-form-item label="公司地址" prop="mediaCompanyAddress">
                            <el-input v-model="form.mediaCompanyAddress" placeholder="请输入公司地址" />
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-divider content-position="left">联系信息</el-divider>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="联系人" prop="contactName">
                            <el-input v-model="form.contactName" placeholder="请输入联系人" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="联系电话" prop="contactPhone">
                            <el-input v-model="form.contactPhone" placeholder="请输入联系电话" />
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="联系邮箱" prop="contactEmail">
                            <el-input v-model="form.contactEmail" placeholder="请输入联系邮箱" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="营业执照照片" prop="mediaCompanyLicense">
                            <el-input v-model="form.mediaCompanyLicense" placeholder="请输入营业执照照片URL" />
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="24">
                        <el-form-item label="备注" prop="remark">
                            <el-input v-model="form.remark" type="textarea" :rows="3" placeholder="请输入备注信息" />
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="cancel">取 消</el-button>
                    <el-button type="primary" @click="submitForm">确 定</el-button>
                </div>
            </template>
        </el-dialog>
    </div>
</template>

<script setup name="Media">
import { listMedia, getMedia, delMedia, addMedia, updateMedia } from "@/api/flow/media"
import useSettingsStore from "@/store/modules/settings"

const { proxy } = getCurrentInstance()
const settingsStore = useSettingsStore()
const isDark = computed(() => settingsStore.isDark)

// 使用 proxy.useDict 获取字典数据，参考菜单管理页面的实现
const { media_status } = proxy.useDict("media_status")

const mediaList = ref([])
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
        mediaCompanyName: null,
        mediaCompanyShort: null,
        mediaCompanyAddress: null,
        mediaOwnerName: null,
        contactPhone: null,
        contactEmail: null,
        enable: null,
    },
    rules: {
        account: [
            { required: true, message: "账号名不能为空", trigger: "blur" }
        ],
        password: [
            { required: true, message: "密码不能为空", trigger: "blur" }
        ],
        enable: [
            { required: true, message: "状态不能为空", trigger: "blur" }
        ],
    }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询媒体管理列表 */
function getList() {
    loading.value = true
    listMedia(queryParams.value).then(response => {
        mediaList.value = response.rows
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
        account: null,
        password: null,
        mediaCompanyName: null,
        mediaCompanyShort: null,
        mediaCompanyCode: null,
        mediaCompanyLicense: null,
        mediaCompanyAddress: null,
        mediaOwnerName: null,
        contactName: null,
        contactPhone: null,
        contactEmail: null,
        enable: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
    }
    proxy.resetForm("mediaRef")
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
    title.value = "添加媒体管理"
}

/** 修改按钮操作 */
function handleUpdate(row) {
    reset()
    const _id = row.id || ids.value
    getMedia(_id).then(response => {
        form.value = response.data
        // 将 enable 转换为字符串以匹配数据字典类型
        if (form.value.enable !== null && form.value.enable !== undefined) {
            form.value.enable = String(form.value.enable)
        }
        open.value = true
        title.value = "修改媒体管理"
    })
}

/** 提交按钮 */
function submitForm() {
    proxy.$refs["mediaRef"].validate(valid => {
        if (valid) {
            if (form.value.id != null) {
                updateMedia(form.value).then(response => {
                    proxy.$modal.msgSuccess("修改成功")
                    open.value = false
                    getList()
                })
            } else {
                addMedia(form.value).then(response => {
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
    proxy.$modal.confirm('是否确认删除媒体管理编号为"' + _ids + '"的数据项？').then(function() {
        return delMedia(_ids)
    }).then(() => {
        getList()
        proxy.$modal.msgSuccess("删除成功")
    }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
    proxy.download('flow/media/export', {
        ...queryParams.value
    }, `media_${new Date().getTime()}.xlsx`)
}

getList()
</script>

<style scoped lang="scss">
.media-page {
  transition: all 0.3s ease;

  &.dark-mode {
    :deep(.el-form) {
      background: #1e1e1e;
      border-radius: 8px;
      padding: 20px;
      border: 1px solid #3a3a3a;
    }

    :deep(.el-table) {
      background-color: #1e1e1e !important;
      color: #e0e0e0 !important;

      th {
        background-color: #2a2a2a !important;
        color: #b0b0b0 !important;
        border-color: #3a3a3a !important;
      }

      tr {
        background-color: #1e1e1e !important;

        &:hover {
          background-color: #2a2a2a !important;
        }
      }

      td {
        border-color: #3a3a3a !important;
      }
    }

    :deep(.el-input__wrapper) {
      background-color: #2a2a2a;
      border-color: #3a3a3a;

      .el-input__inner {
        color: #e0e0e0;
      }
    }

    :deep(.el-select .el-input__wrapper) {
      background-color: #2a2a2a;
    }
  }
}

// 搜索表单区域美化
:deep(.el-form) {
  background: #fff;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 16px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  transition: all 0.3s ease;

  .el-form-item {
    margin-bottom: 16px;
  }

  .el-input {
    width: 200px;
  }

  .el-select {
    width: 200px;
  }
}

// 工具栏按钮美化
.mb8 {
  margin-bottom: 16px;

  :deep(.el-button) {
    border-radius: 6px;
    padding: 10px 16px;
    font-weight: 500;
    transition: all 0.3s ease;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.08);

    &:hover {
      transform: translateY(-2px);
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.12);
    }

    &:active {
      transform: translateY(0);
    }

    &.is-disabled {
      transform: none;
      box-shadow: none;
    }
  }
}

// 表格美化
:deep(.el-table) {
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;

  &::before {
    display: none;
  }

  th {
    background: linear-gradient(to bottom, #fafbfc, #f5f7fa) !important;
    font-weight: 600;
    font-size: 13px;
    color: #606266;
    border-bottom: 2px solid #e4e7ed;
  }

  tr {
    transition: background-color 0.2s ease;

    &:hover {
      background-color: #f5f7fa !important;
    }
  }

  td {
    border-bottom: 1px solid #f0f0f0;
    padding: 12px 0;
  }

  .el-button--text {
    padding: 4px 8px;
    border-radius: 4px;
    transition: all 0.2s ease;

    &:hover {
      background-color: rgba(64, 158, 255, 0.1);
    }
  }
}

// 分页器美化
:deep(.el-pagination) {
  padding: 16px 0;
  display: flex;
  justify-content: flex-end;
  gap: 8px;

  button {
    border-radius: 6px;
    transition: all 0.3s ease;

    &:hover {
      transform: scale(1.05);
    }
  }

  .el-pager li {
    border-radius: 6px;
    margin: 0 4px;
    transition: all 0.3s ease;

    &:hover {
      transform: scale(1.05);
    }

    &.is-active {
      background: var(--el-color-primary);
      color: #fff;
    }
  }
}

// 对话框美化
:deep(.el-dialog) {
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.15);

  .el-dialog__header {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    padding: 24px 32px;
    margin: 0;

    .el-dialog__title {
      color: #fff;
      font-size: 20px;
      font-weight: 600;
      letter-spacing: 0.5px;
    }

    .el-dialog__headerbtn {
      top: 24px;
      right: 24px;
      width: 32px;
      height: 32px;
      border-radius: 50%;
      transition: all 0.3s ease;

      &:hover {
        background: rgba(255, 255, 255, 0.2);
      }

      .el-dialog__close {
        color: #fff;
        font-size: 22px;
        font-weight: bold;
      }
    }
  }

  .el-dialog__body {
    padding: 32px;
    max-height: 70vh;
    overflow-y: auto;
    background: #fafbfc;

    &::-webkit-scrollbar {
      width: 8px;
    }

    &::-webkit-scrollbar-track {
      background: #f1f1f1;
      border-radius: 4px;
    }

    &::-webkit-scrollbar-thumb {
      background: #c1c1c1;
      border-radius: 4px;

      &:hover {
        background: #a8a8a8;
      }
    }

    .el-divider {
      margin: 24px 0 20px;
      border-color: #e4e7ed;

      .el-divider__text {
        background: #fafbfc;
        font-size: 15px;
        font-weight: 600;
        color: #606266;
        padding: 0 16px;
      }
    }
  }

  .el-dialog__footer {
    padding: 20px 32px;
    border-top: 1px solid #f0f0f0;
    background: #fff;

    .dialog-footer {
      display: flex;
      justify-content: flex-end;
      gap: 12px;
    }

    .el-button {
      border-radius: 8px;
      padding: 12px 32px;
      font-size: 15px;
      font-weight: 500;
      transition: all 0.3s ease;
      min-width: 100px;

      &:hover {
        transform: translateY(-2px);
        box-shadow: 0 6px 16px rgba(0, 0, 0, 0.12);
      }

      &:active {
        transform: translateY(0);
      }
    }
  }
}

// 表单布局优化
:deep(.dialog-form) {
  .el-form-item {
    margin-bottom: 20px;

    .el-form-item__label {
      font-weight: 500;
      color: #606266;
      font-size: 14px;
      line-height: 36px;
    }

    .el-input,
    .el-select,
    .el-textarea {
      width: 100%;
    }

    .el-input__wrapper {
      padding: 8px 15px;
      line-height: 36px;
    }

    .el-textarea__inner {
      padding: 10px 15px;
      line-height: 1.6;
    }
  }

  .el-row {
    &:last-child {
      margin-bottom: -8px;
    }
  }
}

// 表单输入框美化
:deep(.el-input__wrapper) {
  border-radius: 6px;
  transition: all 0.3s ease;
  box-shadow: 0 0 0 1px #dcdfe6 inset;

  &:hover {
    box-shadow: 0 0 0 1px #c0c4cc inset;
  }

  &.is-focus {
    box-shadow: 0 0 0 1px var(--el-color-primary) inset !important;
  }
}

:deep(.el-textarea__inner) {
  border-radius: 6px;
  transition: all 0.3s ease;

  &:focus {
    border-color: var(--el-color-primary);
  }
}

// 字典标签美化
:deep(.dict-tag) {
  padding: 4px 12px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 500;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.08);
}

// 复选框列美化
:deep(.el-table-column--selection .cell) {
  padding-left: 10px;
  padding-right: 10px;
}

// 状态列特殊样式
:deep(.el-tag) {
  border-radius: 6px;
  padding: 4px 12px;
  font-weight: 500;
  border: none;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.08);
}

// 响应式设计
@media (max-width: 992px) {
  :deep(.el-dialog) {
    width: 95% !important;
    margin: 0 auto;

    .el-dialog__body {
      padding: 20px;

      .el-col {
        width: 100% !important;
        max-width: 100%;
        flex: 0 0 100%;
      }
    }
  }
}

@media (max-width: 768px) {
  :deep(.el-dialog) {
    width: 100% !important;
    border-radius: 0;
    margin: 0;

    .el-dialog__header {
      padding: 20px;
    }

    .el-dialog__body {
      padding: 20px;
      max-height: 65vh;
    }

    .el-dialog__footer {
      padding: 16px 20px;
    }
  }

  :deep(.dialog-form) {
    .el-form-item__label {
      width: 100px !important;
    }
  }

  :deep(.el-form) {
    .el-form-item {
      width: 100%;
      margin-right: 0;

      .el-input,
      .el-select {
        width: 100%;
      }
    }
  }

  :deep(.el-table) {
    font-size: 12px;

    th,
    td {
      padding: 8px 4px;
    }
  }

  .mb8 {
    :deep(.el-button) {
      padding: 8px 12px;
      font-size: 12px;
    }
  }
}
</style>