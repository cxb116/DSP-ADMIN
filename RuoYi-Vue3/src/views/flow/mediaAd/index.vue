<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="媒体应用" prop="mediaAppCascade">
        <el-cascader
          v-model="queryParams.mediaAppCascade"
          :options="cascaderData"
          :props="{ expandTrigger: 'hover' }"
          placeholder="请选择媒体/应用"
          clearable
          style="width: 240px"
        />
      </el-form-item>
      <el-form-item label="场景" prop="sceneId">
        <el-select
          v-model="queryParams.sceneId"
          placeholder="请选择场景"
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
      <el-form-item label="状态" prop="enable">
        <el-select
          v-model="queryParams.enable"
          placeholder="请选择状态"
          clearable
        >
          <el-option
            v-for="dict in audit_status"
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
      <el-table-column label="媒体" align="center" prop="mediaId">
        <template #default="scope">
          {{ getMediaName(scope.row.mediaId) }}
        </template>
      </el-table-column>
      <el-table-column label="应用" align="center" prop="appId">
        <template #default="scope">
          {{ getAppName(scope.row.appId) }}
        </template>
      </el-table-column>
      <el-table-column label="广告位名称" align="center" prop="name">
        <template #default="scope">
          {{ scope.row.name }}({{ scope.row.id }})
        </template>
      </el-table-column>
      <el-table-column label="内部广告位名称" align="center" prop="nameAlise" />
      <el-table-column label="场景" align="center" prop="sceneId">
        <template #default="scope">
          <dict-tag :options="ad_scene" :value="String(scope.row.sceneId)" />
        </template>
      </el-table-column>
      <el-table-column label="结算方式" align="center" prop="sspPayType">
        <template #default="scope">
          <dict-tag :options="ssp_pay_type" :value="String(scope.row.sspPayType)" />
        </template>
      </el-table-column>
      <el-table-column label="分成系数" align="center" prop="sspDealRatio" />
      <el-table-column label="广告宽度" align="center" prop="width" />
      <el-table-column label="广告高度" align="center" prop="height" />
      <el-table-column label="广告位图片" align="center" prop="adImage" width="100">
        <template #default="scope">
          <image-preview :src="scope.row.adImage" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="交互类型" align="center" prop="interactionType" />
      <el-table-column label="状态" align="center" prop="enable">
        <template #default="scope">
          <dict-tag :options="audit_status" :value="String(scope.row.enable)" />
        </template>
      </el-table-column>
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
      <el-form ref="mediaAdRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="媒体/应用" prop="mediaAppCascade">
          <el-cascader
            v-model="form.mediaAppCascade"
            :options="cascaderData"
            :props="{ expandTrigger: 'hover' }"
            placeholder="请选择媒体/应用"
            clearable
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="广告位名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入广告位名称" />
        </el-form-item>
        <el-form-item label="内部广告位名称" prop="nameAlise">
          <el-input v-model="form.nameAlise" placeholder="请输入内部广告位名称" />
        </el-form-item>
        <el-form-item label="场景" prop="sceneId">
          <el-select v-model="form.sceneId" placeholder="请选择场景">
            <el-option
              v-for="dict in ad_scene"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="结算方式" prop="sspPayType">
          <el-select v-model="form.sspPayType" placeholder="请选择结算方式" @change="handleSspPayTypeChange">
            <el-option
              v-for="dict in ssp_pay_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="分成系数" prop="sspDealRatio">
          <el-input v-model="form.sspDealRatio" placeholder="请输入分成系数" />
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
        <el-form-item label="交互类型" prop="interactionType">
          <el-input v-model="form.interactionType" placeholder="请输入交互类型" />
        </el-form-item>
        <el-form-item label="状态" prop="enable">
          <el-select v-model="form.enable" placeholder="请选择状态">
            <el-option
              v-for="dict in audit_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
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
import { getMediaAppCascader } from "@/api/flow/media"
import { useDict } from "@/utils/dict"

const { proxy } = getCurrentInstance()
const { audit_status, ssp_pay_type, ad_scene } = useDict('audit_status', 'ssp_pay_type', 'ad_scene')

const mediaAdList = ref([])
const cascaderData = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")

// 创建媒体和应用ID到名称的映射
const mediaNameMap = ref(new Map())
const appNameMap = ref(new Map())

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    mediaId: null,
    appId: null,
    mediaAppCascade: [],  // 级联选择器的值
    sceneId: null,
    name: null,
    nameAlise: null,
    enable: null,
  },
  rules: {
    mediaAppCascade: [
      { required: true, message: "媒体/应用不能为空", trigger: "change", type: 'array' }
    ],
    name: [
      { required: true, message: "广告位名称不能为空", trigger: "blur" }
    ],
    sspPayType: [
      { required: true, message: "结算方式不能为空", trigger: "change" }
    ],
    sspDealRatio: [
      {
        validator: (_rule, value, callback) => {
          // 如果结算方式选择分成，则分成系数必填
          if (form.value.sspPayType === '1' && !value) {
            callback(new Error('分成系数不能为空'))
          } else {
            callback()
          }
        },
        trigger: 'blur'
      }
    ],
    enable: [
      { required: true, message: "状态不能为空", trigger: "change" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询媒体广告位列表 */
function getList() {
  loading.value = true
  // 从级联选择器的值中提取 mediaId 和 appId
  if (queryParams.value.mediaAppCascade && queryParams.value.mediaAppCascade.length > 0) {
    queryParams.value.mediaId = queryParams.value.mediaAppCascade[0]
    queryParams.value.appId = queryParams.value.mediaAppCascade[1] || null
  } else {
    queryParams.value.mediaId = null
    queryParams.value.appId = null
  }
  listMediaAd(queryParams.value).then(response => {
    mediaAdList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

/** 加载媒体应用级联数据 */
function loadCascaderData() {
  getMediaAppCascader().then(response => {
    cascaderData.value = response.data
    // 构建媒体和应用ID到名称的映射
    mediaNameMap.value.clear()
    appNameMap.value.clear()
    response.data.forEach(media => {
      mediaNameMap.value.set(media.value, media.label)
      if (media.children && media.children.length > 0) {
        media.children.forEach(app => {
          appNameMap.value.set(app.value, app.label)
        })
      }
    })
  })
}

/** 根据媒体ID获取媒体名称 */
function getMediaName(mediaId) {
  return mediaNameMap.value.get(mediaId) || mediaId
}

/** 根据应用ID获取应用名称 */
function getAppName(appId) {
  return appNameMap.value.get(appId) || appId
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
    mediaAppCascade: [],
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

/** 结算方式改变时触发分成系数验证 */
function handleSspPayTypeChange() {
  // 触发分成系数字段的验证
  proxy.$refs["mediaAdRef"].validateField('sspDealRatio')
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
    const data = response.data
    // 将数字类型的字段转为字符串，以便字典匹配
    if (data.sceneId !== null && data.sceneId !== undefined) {
      data.sceneId = String(data.sceneId)
    }
    if (data.sspPayType !== null && data.sspPayType !== undefined) {
      data.sspPayType = String(data.sspPayType)
    }
    if (data.enable !== null && data.enable !== undefined) {
      data.enable = String(data.enable)
    }
    // 将 mediaId 和 appId 转换为级联选择器格式
    if (data.mediaId && data.appId) {
      data.mediaAppCascade = [data.mediaId, data.appId]
    } else {
      data.mediaAppCascade = []
    }
    form.value = data
    open.value = true
    title.value = "修改媒体广告位"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["mediaAdRef"].validate(valid => {
    if (valid) {
      // 从级联选择器中提取 mediaId 和 appId
      if (form.value.mediaAppCascade && form.value.mediaAppCascade.length > 0) {
        form.value.mediaId = form.value.mediaAppCascade[0]
        form.value.appId = form.value.mediaAppCascade[1] || null
      }

      if (form.value.id != null) {
        updateMediaAd(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addMediaAd(form.value).then(() => {
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

// 页面加载时获取级联数据
loadCascaderData()
getList()
</script>
