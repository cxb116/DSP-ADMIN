<template>
    <div class="app-container">
        <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
            <el-form-item label="媒体" prop="mediaId">
                <el-select
                    v-model="queryParams.mediaId"
                    placeholder="请选择媒体"
                    clearable
                    filterable
                    @keyup.enter="handleQuery"
                >
                    <el-option
                        v-for="media in mediaListOptions"
                        :key="media.id"
                        :label="media.name"
                        :value="media.id"
                    />
                </el-select>
            </el-form-item>
            <el-form-item label="应用" prop="appId">
                <el-select
                    v-model="queryParams.appId"
                    placeholder="请选择应用"
                    clearable
                    filterable
                    @keyup.enter="handleQuery"
                >
                    <el-option
                        v-for="app in appListOptions"
                        :key="app.id"
                        :label="app.name"
                        :value="app.id"
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
            <el-form-item label="广告类型" prop="sceneId">
                <el-select
                    v-model="queryParams.sceneId"
                    placeholder="请选择广告类型"
                    clearable
                    @keyup.enter="handleQuery"
                >
                    <el-option
                        v-for="dict in ad_scene"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                    />
                </el-select>
            </el-form-item>
            <el-form-item label="结算方式" prop="sspPayType">
                <el-select
                    v-model="queryParams.sspPayType"
                    placeholder="请选择结算方式"
                    clearable
                    @keyup.enter="handleQuery"
                >
                    <el-option
                        v-for="dict in ssp_pay_type"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                    />
                </el-select>
            </el-form-item>
            <el-form-item label="下游媒体分成系数" prop="sspDealRatio">
                <el-input
                    v-model="queryParams.sspDealRatio"
                    placeholder="请输入下游媒体分成系数"
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
            <el-form-item label="交互类型" prop="interactionType">
                <el-select
                    v-model="interactionTypeArray"
                    placeholder="请选择交互类型"
                    clearable
                    multiple
                    @keyup.enter="handleQuery"
                >
                    <el-option
                        v-for="option in interactionTypeOptions"
                        :key="option.value"
                        :label="option.label"
                        :value="option.value"
                    />
                </el-select>
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
            <el-table-column label="媒体" align="center" prop="mediaName" />
            <el-table-column label="应用" align="center" prop="appName" />
            <el-table-column label="广告位名称" align="center" prop="name" />
            <el-table-column label="内部广告位名称" align="center" prop="nameAlise" />
            <el-table-column label="广告类型" align="center" prop="sceneName" />
            <el-table-column label="结算方式" align="center" prop="sspPayTypeName" />
            <el-table-column label="分成系数" align="center" prop="sspDealRatio" />
            <el-table-column label="广告宽度" align="center" prop="width" />
            <el-table-column label="广告高度" align="center" prop="height" />
            <el-table-column label="广告位图片" align="center" prop="adImage" width="100">
                <template #default="scope">
                    <image-preview :src="scope.row.adImage" :width="50" :height="50"/>
                </template>
            </el-table-column>
            <el-table-column label="交互类型" align="center" prop="interactionTypeName" />
            <el-table-column label="状态" align="center" prop="enableName" />
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
        <el-dialog :title="title" v-model="open" width="600px" append-to-body>
            <el-form ref="mediaAdRef" :model="form" :rules="rules" label-width="120px">
                <el-form-item label="媒体" prop="mediaId">
                    <el-select v-model="form.mediaId" placeholder="请选择媒体" filterable>
                        <el-option
                            v-for="media in mediaListOptions"
                            :key="media.id"
                            :label="media.name"
                            :value="media.id"
                        />
                    </el-select>
                </el-form-item>
                <el-form-item label="应用" prop="appId">
                    <el-select v-model="form.appId" placeholder="请选择应用" filterable>
                        <el-option
                            v-for="app in appListOptions"
                            :key="app.id"
                            :label="app.name"
                            :value="app.id"
                        />
                    </el-select>
                </el-form-item>
                <el-form-item label="广告位名称" prop="name">
                    <el-input v-model="form.name" placeholder="请输入广告位名称" />
                </el-form-item>
                <el-form-item label="内部广告位名称" prop="nameAlise">
                    <el-input v-model="form.nameAlise" placeholder="请输入内部广告位名称" />
                </el-form-item>
                <el-form-item label="广告类型" prop="adScene">
                    <el-select v-model="form.adScene" placeholder="请选择广告类型">
                        <el-option
                            v-for="dict in ad_scene"
                            :key="dict.value"
                            :label="dict.label"
                            :value="dict.value"
                        />
                    </el-select>
                </el-form-item>
                <el-form-item label="结算方式" prop="sspPayType">
                    <el-select v-model="form.sspPayType" placeholder="请选择结算方式">
                        <el-option
                            v-for="dict in ssp_pay_type"
                            :key="dict.value"
                            :label="dict.label"
                            :value="dict.value"
                        />
                    </el-select>
                </el-form-item>
                <el-form-item label="下游媒体分成系数" prop="sspDealRatio">
                    <el-input v-model="form.sspDealRatio" placeholder="请输入下游媒体分成系数" />
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
                    <el-select v-model="dialogInteractionTypeArray" placeholder="请选择交互类型" multiple>
                        <el-option
                            v-for="option in interactionTypeOptions"
                            :key="option.value"
                            :label="option.label"
                            :value="option.value"
                        />
                    </el-select>
                </el-form-item>
                <el-form-item label="状态" prop="enable">
                    <el-select v-model="form.enable" placeholder="请选择状态">
                        <el-option
                            v-for="dict in media_status"
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
import { listMedia } from "@/api/flow/media"
import { listApp } from "@/api/flow/app"
import { ref, reactive, toRefs, computed, onMounted, watch, nextTick } from "vue"

const { proxy } = getCurrentInstance()

// 获取字典数据
const { media_status, ssp_pay_type, ad_scene } = proxy.useDict("media_status", "ssp_pay_type", "ad_scene")

// 交互类型选项
const interactionTypeOptions = [
    { value: 1, label: '打开网页' },
    { value: 2, label: 'deeplink' },
    { value: 4, label: '直接下载应用' },
    { value: 8, label: '广点通' },
    { value: 16, label: '小程序跳转' },
    { value: 32, label: '应用商店下载' },
    { value: 64, label: '快应用' }
]

// 媒体列表和应用列表选项
const mediaListOptions = ref([])
const appListOptions = ref([])

const mediaAdList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")

// 用于搜索的交互类型数组
const interactionTypeArray = ref([])

// 对话框表单中的交互类型数组
const dialogInteractionTypeArray = ref([])

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
            { required: true, message: "媒体不能为空", trigger: "change" }
        ],
        appId: [
            { required: true, message: "应用不能为空", trigger: "change" }
        ],
        name: [
            { required: true, message: "广告位名称不能为空", trigger: "blur" }
        ],
        adScene: [
            { required: true, message: "广告类型不能为空", trigger: "change" }
        ],
        sspPayType: [
            { required: true, message: "结算方式不能为空", trigger: "change" }
        ],
        enable: [
            { required: true, message: "状态不能为空", trigger: "change" }
        ],
    }
})

const { queryParams, form, rules } = toRefs(data)

// 组件挂载时加载媒体和应用列表
onMounted(() => {
    getMediaList()
    getAppList()
    getList()
})

// 获取媒体列表
function getMediaList() {
    listMedia({ pageNum: 1, pageSize: 1000 }).then(response => {
        mediaListOptions.value = response.rows.map(item => ({
            id: item.id,
            name: item.name
        }))
    })
}

// 获取应用列表
function getAppList() {
    listApp({ pageNum: 1, pageSize: 1000 }).then(response => {
        appListOptions.value = response.rows.map(item => ({
            id: item.id,
            name: item.name
        }))
    })
}

// 根据字典值和字典获取标签
function getDictLabel(value, dictOptions) {
    if (value === null || value === undefined || value === '') return ''
    const dict = dictOptions.find(item => String(item.value) === String(value))
    return dict ? dict.label : value
}

// 根据交互类型值获取标签
function getInteractionTypeLabel(interactionType) {
    if (interactionType === null || interactionType === undefined || interactionType === 0) return ''

    const selectedTypes = []
    interactionTypeOptions.forEach(option => {
        if (interactionType & option.value) {
            selectedTypes.push(option.label)
        }
    })
    return selectedTypes.join('、')
}

// 将交互类型数组转换为位掩码
function arrayToBitmask(array) {
    if (!Array.isArray(array) || array.length === 0) return 0
    return array.reduce((acc, curr) => acc | curr, 0)
}

// 将位掩码转换为交互类型数组
function bitmaskToArray(bitmask) {
    if (!bitmask && bitmask !== 0) return []
    const array = []
    interactionTypeOptions.forEach(option => {
        if (bitmask & option.value) {
            array.push(option.value)
        }
    })
    return array
}

// 监听搜索的交互类型数组变化
watch(interactionTypeArray, (newVal) => {
    queryParams.value.interactionType = arrayToBitmask(newVal)
}, { deep: true })

// 监听查询参数的交互类型变化
watch(() => queryParams.value.interactionType, (newVal) => {
    // 只有当值变化且当前数组不匹配时才更新
    const currentArray = bitmaskToArray(newVal)
    if (JSON.stringify(currentArray) !== JSON.stringify(interactionTypeArray.value)) {
        interactionTypeArray.value = currentArray
    }
}, { immediate: true })

// 监听对话框的交互类型数组变化
watch(dialogInteractionTypeArray, (newVal) => {
    form.value.interactionType = arrayToBitmask(newVal)
}, { deep: true })

// 监听表单的interactionType变化
watch(() => form.value.interactionType, (newVal) => {
    const currentArray = bitmaskToArray(newVal)
    if (JSON.stringify(currentArray) !== JSON.stringify(dialogInteractionTypeArray.value)) {
        dialogInteractionTypeArray.value = currentArray
    }
}, { immediate: true })

/** 查询媒体广告位列表 */
function getList() {
    loading.value = true
    listMediaAd(queryParams.value).then(response => {
        // 为数据添加显示名称
        mediaAdList.value = response.rows.map(item => {
            return {
                ...item,
                mediaName: getDictLabel(item.mediaId, mediaListOptions.value) || item.mediaId,
                appName: getDictLabel(item.appId, appListOptions.value) || item.appId,
                sceneName: getDictLabel(item.adScene, ad_scene.value) || item.adScene,
                sspPayTypeName: getDictLabel(item.sspPayType, ssp_pay_type.value) || item.sspPayType,
                interactionTypeName: getInteractionTypeLabel(item.interactionType),
                enableName: getDictLabel(item.enable, media_status.value) || item.enable
            }
        })
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
        adScene: null,
        sspPayType: null,
        sspDealRatio: null,
        width: null,
        height: null,
        adImage: null,
        interactionType: 0,
        enable: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
    }
    dialogInteractionTypeArray.value = []
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
    // 重置交互类型数组
    interactionTypeArray.value = []
    // 重置其他查询参数
    Object.keys(queryParams.value).forEach(key => {
        if (key !== 'pageNum' && key !== 'pageSize') {
            queryParams.value[key] = null
        }
    })
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
        const data = response.data
        form.value = { ...data }

        // 设置交互类型的复选框状态
        dialogInteractionTypeArray.value = bitmaskToArray(data.interactionType || 0)

        open.value = true
        title.value = "修改媒体广告位"

        // 调试信息
        console.log('修改表单数据:', {
            id: data.id,
            adScene: data.adScene,
            sspPayType: data.sspPayType,
            enable: data.enable,
            interactionType: data.interactionType,
            dialogInteractionTypeArray: dialogInteractionTypeArray.value
        })
    })
}

/** 提交按钮 */
function submitForm() {
    proxy.$refs["mediaAdRef"].validate(valid => {
        if (valid) {
            // 确保interactionType是位掩码
            if (Array.isArray(form.value.interactionType)) {
                form.value.interactionType = arrayToBitmask(form.value.interactionType)
            }

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
    // 在导出前，确保查询参数中的交互类型是位掩码
    const exportParams = {
        ...queryParams.value,
        interactionType: arrayToBitmask(interactionTypeArray.value)
    }

    proxy.download('flow/mediaAd/export', exportParams, `mediaAd_${new Date().getTime()}.xlsx`)
}
</script>