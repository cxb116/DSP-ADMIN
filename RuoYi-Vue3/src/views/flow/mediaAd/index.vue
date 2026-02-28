<template>
  <div class="app-container">
    <el-tabs v-model="activeTab" @tab-change="handleTabChange">
      <el-tab-pane label="广告位列表" name="list">
        <div class="app-container-list">
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
      <el-table-column label="广告类型" align="center" prop="adScene">
        <template #default="scope">
          <dict-tag :options="ad_scene" :value="String(scope.row.adScene)" />
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
          <el-button link type="primary" icon="Setting" @click="handleConfig(scope.row)" v-hasPermi="['flow:mediaAd:config']">配置</el-button>
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
      </el-tab-pane>

      <!-- 配置 Tab -->
      <el-tab-pane label="配置管理" name="config">
        <div class="app-container-config" v-if="configMediaAd">
          <el-page-header @back="handleBackToList" :content="'配置: ' + configMediaAd.name + '(' + configMediaAd.id + ')'">
            <template #extra>
              <el-button type="primary" size="small" @click="handleSaveConfig">保存配置</el-button>
            </template>
          </el-page-header>

          <el-divider />

          <el-card class="config-card">
            <template #header>
              <div class="card-header">
                <span>基础信息</span>
              </div>
            </template>
            <el-descriptions :column="2" border>
              <el-descriptions-item label="广告位名称">{{ configMediaAd.name }}</el-descriptions-item>
              <el-descriptions-item label="内部名称">{{ configMediaAd.nameAlise }}</el-descriptions-item>
              <el-descriptions-item label="媒体">{{ getMediaName(configMediaAd.mediaId) }}</el-descriptions-item>
              <el-descriptions-item label="应用">{{ getAppName(configMediaAd.appId) }}</el-descriptions-item>
              <!-- 隐藏内部名称字段 -->
              <el-descriptions-item label="操作系统类型">
                <span v-if="appInfo">
                  <el-tag v-if="appInfo.osType === 1" type="success">Android</el-tag>
                  <el-tag v-else-if="appInfo.osType === 2" type="primary">iOS</el-tag>
                  <el-tag v-else type="info">未知</el-tag>
                </span>
                <span v-else class="os-loading">加载中...</span>
              </el-descriptions-item>
              <el-descriptions-item label="广告类型">
                <dict-tag :options="ad_scene" :value="String(configMediaAd.adScene)" />
              </el-descriptions-item>
              <el-descriptions-item label="结算方式">
                <dict-tag :options="ssp_pay_type" :value="String(configMediaAd.sspPayType)" />
              </el-descriptions-item>
              <el-descriptions-item label="分成系数">
                {{ configMediaAd.sspPayType === '2' ? '-' : configMediaAd.sspDealRatio }}
              </el-descriptions-item>
            </el-descriptions>
          </el-card>

          <el-card class="config-card" style="margin-top: 20px;">
            <template #header>
              <div class="card-header">
                <span>流量分配</span>
                <el-button type="primary" size="small" icon="Plus" @click="handleAddSlot">添加预算方</el-button>
              </div>
            </template>

            <!-- 流量权重配置区域 -->
            <div v-if="slotList.length > 0" class="traffic-weight-config">
              <div class="weight-header">
                <span class="weight-title">流量权重配置 (总和: {{ totalTrafficWeight }} / 100)</span>
                <div class="weight-actions">
                  <el-tag :type="totalTrafficWeight === 100 ? 'success' : 'warning'" size="small">
                    {{ totalTrafficWeight === 100 ? '权重配置正确' : '权重总和不为100' }}
                  </el-tag>
                  <el-divider direction="vertical" />
                  <el-button-group size="small">
                    <el-button type="primary" @click="handleSetFirstTo100" :disabled="slotList.length === 0">
                      首个100%
                    </el-button>
                    <el-button type="success" @click="handleAverageWeight" :disabled="slotList.length === 0">
                      平均分配
                    </el-button>
                    <el-button type="warning" @click="handleResetWeight" :disabled="slotList.length === 0">
                      重置
                    </el-button>
                  </el-button-group>
                </div>
              </div>
              <div class="weight-items">
                <div
                  v-for="(slot, index) in slotList"
                  :key="slot.id || index"
                  class="weight-item"
                >
                  <span class="weight-label">{{ slot.name }}({{ slot.dspSlotInfoId }}):</span>
                  <el-input-number
                    v-model="slot.trafficWeight"
                    :min="0"
                    :max="100"
                    size="small"
                    style="width: 120px"
                  />
                </div>
              </div>
            </div>

            <div v-if="slotList.length === 0" class="empty-slots">
              <el-empty description="暂无预算方配置，点击上方按钮添加" />
            </div>

            <div v-for="(slot, index) in slotList" :key="slot.id || index" class="slot-card">
              <el-card shadow="hover">
                <template #header>
                  <div class="slot-card-header">
                    <span>{{ slot.name || '新建预算方' }}({{ slot.dspSlotInfoId }})</span>
                    <div class="header-actions">
                      <el-button type="primary" size="small" icon="Document">捕获日志</el-button>
                      <el-button type="danger" size="small" icon="Delete" @click="handleDeleteSlot(index)">删除</el-button>
                    </div>
                  </div>
                </template>

                <!-- 不可编辑部分：来源于 dsp_slot_info -->
                <div class="slot-info-section">
                  <el-descriptions title="基础信息（从 dsp_slot_info 获取，不可编辑）" :column="2" border size="small">
                    <el-descriptions-item label="预算方名称">
                      <span>{{ slot.name }}</span>
                    </el-descriptions-item>
                    <el-descriptions-item label="操作系统">
                      <span v-if="appInfo">
                        <el-tag v-if="slot.osType === 1" type="success">Android</el-tag>
                        <el-tag v-else-if="slot.osType === 2" type="primary">iOS</el-tag>
                        <el-tag v-else type="info">未知</el-tag>
                      </span>
                      <span v-else>未知</span>
                    </el-descriptions-item>
                    <el-descriptions-item label="广告类型">
                      <dict-tag :options="ad_scene" :value="String(slot.adScene)" />
                    </el-descriptions-item>
                    <el-descriptions-item label="预算方广告位">
                      <span>{{ slot.dspSlotCode }}</span>
                    </el-descriptions-item>
                    <el-descriptions-item label="预算方APPKEY">
                      <span>{{ slot.dspAppKey }}</span>
                    </el-descriptions-item>
                    <el-descriptions-item label="预算方APPSECRET">
                      <span>{{ slot.dspAppSecret ? '******' : '-' }}</span>
                    </el-descriptions-item>
                    <el-descriptions-item label="预算方AppId">
                      <span>{{ slot.dspAppId }}</span>
                    </el-descriptions-item>
                    <el-descriptions-item label="应用包名">
                      <span>{{ slot.dspAppPkg }}</span>
                    </el-descriptions-item>
                    <el-descriptions-item label="应用版本号">
                      <span>{{ slot.dspAppVer }}</span>
                    </el-descriptions-item>
                    <el-descriptions-item label="应用商店版本号">
                      <span>{{ slot.dspAppStoreVer }}</span>
                    </el-descriptions-item>
                    <el-descriptions-item label="价格加密Key">
                      <span>{{ slot.priceEncryptKey }}</span>
                    </el-descriptions-item>
                    <el-descriptions-item label="结算方式">
                      <el-tag v-if="slot.dspPayType == 1 || slot.dspPayType == '1'" type="success">分成</el-tag>
                      <el-tag v-else-if="slot.dspPayType == 2 || slot.dspPayType == '2'" type="primary">RTB</el-tag>
                      <el-tag v-else type="info">未知</el-tag>
                    </el-descriptions-item>
                    <el-descriptions-item label="应用商店地址" :span="2">
                      <span>{{ slot.dspAppStoreLink }}</span>
                    </el-descriptions-item>
                  </el-descriptions>
                </div>

                <!-- 可折叠部分：来源于 dsp_launch -->
                <el-collapse v-model="slot.activeCollapse" class="slot-collapse">
                  <el-collapse-item title="投放配置（可编辑）" name="launch">
                    <el-form :model="slot" label-width="140px" size="small">
                      <!-- 投放策略 -->
                      <el-divider content-position="left">投放配置</el-divider>
                      <el-row :gutter="20">
                        <el-col :span="24">
                          <el-form-item label="投放策略" required>
                            <el-select v-model="slot.launchStrategy" placeholder="请选择投放策略" style="width: 100%">
                              <el-option label="对接第三方" :value="1" />
                              <el-option label="自主投放" :value="2" />
                            </el-select>
                          </el-form-item>
                        </el-col>
                      </el-row>

                      <!-- 底价配置 -->
                      <el-divider content-position="left" v-if="slot.dspPayType == 2 || slot.dspPayType == '2'">底价配置</el-divider>
                      <el-row :gutter="20" v-if="slot.dspPayType == 2 || slot.dspPayType == '2'">
                        <el-col :span="24">
                          <el-form-item label="底价(分)" required>
                            <el-input-number
                              v-model="slot.floorPrice"
                              :min="0"
                              placeholder="请输入底价，单位：分"
                              style="width: 100%"
                            />
                            <div class="form-tip">给上游媒体底价，单位：分</div>
                          </el-form-item>
                        </el-col>
                      </el-row>

                      <!-- 限流配置 -->
                      <el-divider content-position="left">限流配置</el-divider>
                      <el-row :gutter="20">
                        <el-col :span="12">
                          <el-form-item label="IP限流次数">
                            <el-input-number
                              v-model="slot.ipLimit"
                              :min="0"
                              placeholder="不限制则留空"
                              style="width: 100%"
                            />
                          </el-form-item>
                        </el-col>
                        <el-col :span="12">
                          <el-form-item label="捕获日志时长(秒)" required>
                            <el-input-number
                              v-model="slot.logCaptureAt"
                              :min="0"
                              placeholder="请输入时长"
                              style="width: 100%"
                            />
                          </el-form-item>
                        </el-col>
                      </el-row>
                      <el-form-item label="上报黑名单">
                        <el-input
                          v-model="slot.trackSchwarz"
                          placeholder="请输入上报黑名单，多个用逗号分隔"
                        />
                      </el-form-item>

                      <!-- 曝光控制 -->
                      <el-divider content-position="left">曝光控制</el-divider>
                      <el-row :gutter="20">
                        <el-col :span="8">
                          <el-form-item label="请求次数">
                            <el-input-number v-model="slot.req" :min="0" style="width: 100%" />
                          </el-form-item>
                        </el-col>
                        <el-col :span="8">
                          <el-form-item label="展现次数">
                            <el-input-number v-model="slot.ims" :min="0" style="width: 100%" />
                          </el-form-item>
                        </el-col>
                        <el-col :span="8">
                          <el-form-item label="点击次数">
                            <el-input-number v-model="slot.clk" :min="0" style="width: 100%" />
                          </el-form-item>
                        </el-col>
                      </el-row>

                      <!-- 定向配置 -->
                      <el-divider content-position="left">定向配置</el-divider>
                      <el-row :gutter="20">
                        <el-col :span="12">
                          <el-form-item label="投放时段" required>
                            <el-select v-model="slot.launchTime" placeholder="请选择投放时段" style="width: 100%">
                              <el-option label="全时段" :value="1" />
                              <el-option label="自定义" :value="2" />
                            </el-select>
                          </el-form-item>
                        </el-col>
                        <el-col :span="12">
                          <el-form-item label="地域定向" required>
                            <el-select v-model="slot.regionDirection" placeholder="请选择地域定向" style="width: 100%">
                              <el-option label="不限制" :value="1" />
                              <el-option label="定向" :value="2" />
                              <el-option label="排除" :value="3" />
                            </el-select>
                          </el-form-item>
                        </el-col>
                      </el-row>
                      <el-row :gutter="20">
                        <el-col :span="12">
                          <el-form-item label="品牌定向" required>
                            <el-select v-model="slot.brandDirection" placeholder="请选择品牌定向" style="width: 100%">
                              <el-option label="不限制" :value="1" />
                              <el-option label="定向" :value="2" />
                              <el-option label="排除" :value="3" />
                            </el-select>
                          </el-form-item>
                        </el-col>
                      </el-row>
                    </el-form>
                  </el-collapse-item>
                </el-collapse>
              </el-card>
            </div>
          </el-card>
        </div>
        <div v-else class="empty-config">
          <el-empty description="请选择一个广告位进行配置" />
        </div>
      </el-tab-pane>
    </el-tabs>

    <!-- 选择预算方对话框 -->
    <el-dialog
      v-model="selectSlotDialogVisible"
      title="选择预算方"
      width="800px"
      append-to-body
    >
      <el-table
        :data="matchedDspSlots"
        @selection-change="handleSlotSelectionChange"
        style="width: 100%"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="预算方名称" align="center" prop="name" />
        <el-table-column label="操作系统" align="center" prop="osType">
          <template #default="scope">
            <el-tag v-if="scope.row.osType === 1" type="success">Android</el-tag>
            <el-tag v-else-if="scope.row.osType === 2" type="primary">iOS</el-tag>
            <el-tag v-else type="info">未知</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="广告位编码" align="center" prop="dspSlotCode" />
        <el-table-column label="APPKEY" align="center" prop="dspAppKey" />
        <el-table-column label="结算方式" align="center" prop="dspPayType">
          <template #default="scope">
            <el-tag v-if="scope.row.dspPayType == 1 || scope.row.dspPayType == '1'" type="success">分成</el-tag>
            <el-tag v-else-if="scope.row.dspPayType == 2 || scope.row.dspPayType == '2'" type="primary">RTB</el-tag>
            <el-tag v-else type="info">未知</el-tag>
          </template>
        </el-table-column>
      </el-table>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="handleCancelSelectSlot">取 消</el-button>
          <el-button type="primary" @click="handleConfirmSelectSlot">确 定</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="MediaAd">
import { listMediaAd, getMediaAd, delMediaAd, addMediaAd, updateMediaAd, getMatchedDspSlots, saveLaunchConfig, getLaunchConfig } from "@/api/flow/mediaAd"
import { getMediaAppCascader, listMedia } from "@/api/flow/media"
import { getApp } from "@/api/flow/app"
import { useDict } from "@/utils/dict"
import { ref, reactive, toRefs, computed, getCurrentInstance } from "vue"

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
// 创建媒体ID到公司名称的映射
const mediaCompanyNameMap = ref(new Map())

// Tab 相关状态
const activeTab = ref('list')
const configMediaAd = ref(null)
const configForm = ref({
  item1: '',
  item2: '',
  item3: ''
})

// 流量分配 - 预算方列表
const slotList = ref([])
// 应用信息
const appInfo = ref(null)
// 选择预算方对话框
const selectSlotDialogVisible = ref(false)
// 匹配的预算方列表
const matchedDspSlots = ref([])
// 选中的预算方
const selectedDspSlotIds = ref([])

// 计算总流量权重
const totalTrafficWeight = computed(() => {
  return slotList.value.reduce((sum, slot) => {
    return sum + (slot.trafficWeight || 0)
  }, 0)
})

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    mediaId: null,
    appId: null,
    mediaAppCascade: [],  // 级联选择器的值
    adScene: null,
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

/** 加载媒体列表，建立媒体ID到公司名称的映射 */
function loadMediaList() {
  listMedia({ pageNum: 1, pageSize: 1000 }).then(response => {
    mediaCompanyNameMap.value.clear()
    response.rows.forEach(media => {
      mediaCompanyNameMap.value.set(media.id, media.mediaCompanyName)
    })
  })
}

/** 根据媒体ID获取媒体名称 */
function getMediaName(mediaId) {
  return mediaCompanyNameMap.value.get(mediaId) || mediaId
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
    adScene: null,
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
  // 如果结算方式切换为RTB，清空分成系数
  if (form.value.sspPayType === '2') {
    form.value.sspDealRatio = null
  }
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
    if (data.adScene !== null && data.adScene !== undefined) {
      data.adScene = String(data.adScene)
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

      // 如果结算方式是RTB，将分成系数设置为null
      if (form.value.sspPayType === '2') {
        form.value.sspDealRatio = null
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

/** Tab 切换事件 */
function handleTabChange(tabName) {
  console.log('切换到 Tab:', tabName)
}

/** 配置按钮操作 */
function handleConfig(row) {
  getMediaAd(row.id).then(response => {
    configMediaAd.value = response.data
    // 重置配置表单
    configForm.value = {
      item1: '',
      item2: '',
      item3: ''
    }
    // 加载应用信息（获取操作系统类型）
    if (configMediaAd.value.appId) {
      loadAppInfo(configMediaAd.value.appId)
    }
    // 加载该广告位的预算方列表
    loadSlotList(row.id)
    // 切换到配置 Tab
    activeTab.value = 'config'
  })
}

/** 加载应用信息 */
function loadAppInfo(appId) {
  getApp(appId).then(response => {
    appInfo.value = response.data
  }).catch(() => {
    proxy.$modal.msgError('获取应用信息失败')
  })
}

/** 加载预算方列表 */
function loadSlotList(mediaAdId) {
  getLaunchConfig(mediaAdId).then(response => {
    const launchConfigList = response.data || []
    // 将后端返回的数据转换为前端需要的格式
    slotList.value = launchConfigList.map(launch => {
      const slotInfo = launch.dspSlotInfo || {}
      return {
        id: launch.id,
        dspSlotInfoId: launch.dspSlotId,
        trafficWeight: launch.trafficWeight,
        launchStrategy: launch.launchStrategy,
        floorPrice: launch.floorPrice,
        ipLimit: launch.ipLimit,
        logCaptureAt: launch.logCaptureAt,
        trackSchwarz: launch.trackSchwarz,
        req: launch.req,
        ims: launch.ims,
        clk: launch.clk,
        launchTime: launch.launchTime,
        regionDirection: launch.regionDirection,
        brandDirection: launch.brandDirection,
        // DspSlotInfo 的字段
        name: slotInfo.name || '',
        osType: slotInfo.osType,
        adScene: slotInfo.adScene,
        dspSlotCode: slotInfo.dspSlotCode || '',
        dspAppKey: slotInfo.dspAppKey || '',
        dspAppSecret: slotInfo.dspAppSecret || '',
        dspAppId: slotInfo.dspAppId || '',
        dspAppPkg: slotInfo.dspAppPkg || '',
        dspAppVer: slotInfo.dspAppVer || '',
        dspAppStoreVer: slotInfo.dspAppStoreVer || '',
        priceEncryptKey: slotInfo.priceEncryptKey || '',
        dspAppStoreLink: slotInfo.dspAppStoreLink || '',
        dspPayType: slotInfo.dspPayType || null,
        dspDealRatio: slotInfo.dspDealRatio || null,
        activeCollapse: ['launch'] // 默认展开投放配置
      }
    })
  }).catch(() => {
    proxy.$modal.msgError('获取投放配置失败')
    slotList.value = []
  })
}

/** 添加预算方 */
async function handleAddSlot() {
  // 从数据库查询匹配的预算方数据
  // 传入 ssp_slot_info 的 id，通过关联查询获取匹配的 dsp_slot_info 数据
  const sspSlotId = configMediaAd.value?.id

  if (!sspSlotId) {
    proxy.$modal.msgError('无法获取当前广告位ID')
    return
  }

  try {
    // 调用接口查询匹配的数据
    const response = await getMatchedDspSlots(sspSlotId)
    let matchedSlots = response.data || []

    console.log('查询到的预算方数量:', matchedSlots.length)
    console.log('已添加的预算方列表:', slotList.value)

    // 过滤掉已经添加的预算方
    // 获取已经添加的预算方的 dspSlotCode 列表
    const addedSlotCodes = slotList.value.map(slot => slot.dspSlotCode).filter(code => code)
    const addedSlotIds = slotList.value.map(slot => slot.dspSlotInfoId).filter(id => id)

    console.log('已添加的 dspSlotCode:', addedSlotCodes)
    console.log('已添加的 dspSlotInfoId:', addedSlotIds)

    // 过滤：排除掉已经添加的预算方（通过 dspSlotInfoId 判断）
    matchedSlots = matchedSlots.filter(slot => {
      // 如果当前预算方的 id 已存在于列表中（通过 dspSlotInfoId 存储），则排除
      if (addedSlotIds.includes(slot.id)) {
        console.log('过滤掉已添加的预算方 (id):', slot.id, slot.name)
        return false
      }
      return true
    })

    console.log('过滤后的预算方数量:', matchedSlots.length)

    if (matchedSlots.length > 0) {
      // 如果有匹配的数据，打开选择对话框
      matchedDspSlots.value = matchedSlots
      selectedDspSlotIds.value = []
      selectSlotDialogVisible.value = true
    } else {
      // 所有匹配的数据都已添加，提示用户
      proxy.$modal.msgWarning('所有匹配的预算方都已被添加')
    }
  } catch (error) {
    console.error('查询预算方数据失败:', error)
    proxy.$modal.msgError('查询预算方数据失败')
  }
}

/** 确认选择预算方 */
function handleConfirmSelectSlot() {
  if (selectedDspSlotIds.value.length === 0) {
    proxy.$modal.msgWarning('请至少选择一个预算方')
    return
  }

  // 根据选中的 ID 找到对应的数据并添加到列表
  selectedDspSlotIds.value.forEach(slotId => {
    const matchedSlot = matchedDspSlots.value.find(slot => slot.id === slotId)
    if (matchedSlot) {
      const newSlot = {
        id: null,
        dspSlotInfoId: matchedSlot.id, // 保存原始的 dsp_slot_info id，用于判断重复
        name: matchedSlot.name || '新建预算方',
        osType: matchedSlot.osType,
        adScene: matchedSlot.adScene,
        dspSlotCode: matchedSlot.dspSlotCode || '',
        dspAppKey: matchedSlot.dspAppKey || '',
        dspAppSecret: matchedSlot.dspAppSecret || '',
        dspAppId: matchedSlot.dspAppId || '',
        dspAppPkg: matchedSlot.dspAppPkg || '',
        dspAppVer: matchedSlot.dspAppVer || '',
        dspAppStoreVer: matchedSlot.dspAppStoreVer || '',
        priceEncryptKey: matchedSlot.priceEncryptKey || '',
        dspAppStoreLink: matchedSlot.dspAppStoreLink || '',
        dspPayType: matchedSlot.dspPayType || null,
        dspDealRatio: matchedSlot.dspDealRatio || null,
        // 投放配置字段
        trafficWeight: null, // 流量权重
        launchStrategy: 1, // 投放策略：1对接第三方，2：自主投放
        floorPrice: null, // 底价
        ipLimit: null, // IP限流次数
        logCaptureAt: 300, // 捕获日志时长（默认300秒）
        trackSchwarz: '', // 上报黑名单
        req: 0, // 请求次数
        ims: 0, // 展现次数
        clk: 0, // 点击次数
        launchTime: 1, // 投放时段：1全时段，2自定义
        regionDirection: 1, // 地域定向：1不限制，2定向，3排除
        brandDirection: 1, // 品牌定向：1不限制，2定向，3排除
        activeCollapse: ['launch'] // 默认展开投放配置
      }
      slotList.value.push(newSlot)
    }
  })

  // 关闭对话框
  selectSlotDialogVisible.value = false
  proxy.$modal.msgSuccess(`成功添加 ${selectedDspSlotIds.value.length} 个预算方`)
}

/** 取消选择预算方 */
function handleCancelSelectSlot() {
  selectSlotDialogVisible.value = false
}

/** 预算方表格选择变化 */
function handleSlotSelectionChange(selection) {
  selectedDspSlotIds.value = selection.map(item => item.id)
}

/** 复制预算方 */
function handleCopySlot(index) {
  const originalSlot = slotList.value[index]
  const copiedSlot = {
    ...originalSlot,
    id: null,
    name: originalSlot.name + ' (副本)',
    activeCollapse: ['launch'] // 默认展开投放配置
  }
  slotList.value.splice(index + 1, 0, copiedSlot)
  proxy.$modal.msgSuccess('复制成功')
}

/** 删除预算方 */
function handleDeleteSlot(index) {
  proxy.$modal.confirm('确认删除该预算方配置吗？').then(() => {
    slotList.value.splice(index, 1)
    proxy.$modal.msgSuccess('删除成功')
  }).catch(() => {})
}

/** 返回列表 */
function handleBackToList() {
  activeTab.value = 'list'
  configMediaAd.value = null
}

/** 首个预算方设置为100% */
function handleSetFirstTo100() {
  if (slotList.value.length === 0) {
    proxy.$modal.msgWarning('请先添加预算方')
    return
  }

  // 将第一个预算方设置为100，其他设置为0
  slotList.value.forEach((slot, index) => {
    if (index === 0) {
      slot.trafficWeight = 100
    } else {
      slot.trafficWeight = 0
    }
  })

  proxy.$modal.msgSuccess('已将首个预算方设置为100%')
}

/** 平均分配权重 */
function handleAverageWeight() {
  if (slotList.value.length === 0) {
    proxy.$modal.msgWarning('请先添加预算方')
    return
  }

  const count = slotList.value.length
  const averageWeight = Math.floor(100 / count)
  const remainder = 100 % count

  // 平均分配权重，余数加到第一个预算方
  slotList.value.forEach((slot, index) => {
    if (index === 0) {
      slot.trafficWeight = averageWeight + remainder
    } else {
      slot.trafficWeight = averageWeight
    }
  })

  proxy.$modal.msgSuccess(`已平均分配权重（每个预算方 ${averageWeight}%，首个预算方 +${remainder}%）`)
}

/** 重置所有权重 */
function handleResetWeight() {
  if (slotList.value.length === 0) {
    proxy.$modal.msgWarning('请先添加预算方')
    return
  }

  proxy.$modal.confirm('确认重置所有预算方的流量权重为0吗？').then(() => {
    slotList.value.forEach(slot => {
      slot.trafficWeight = 0
    })
    proxy.$modal.msgSuccess('已重置所有权重')
  }).catch(() => {})
}

/** 保存配置 */
function handleSaveConfig() {
  // 验证预算方配置
  if (slotList.value.length === 0) {
    proxy.$modal.msgWarning('请至少添加一个预算方配置')
    return
  }

  // 验证流量权重总和必须等于100
  const totalWeight = totalTrafficWeight.value
  if (totalWeight !== 100) {
    proxy.$modal.msgWarning(`所有预算方的流量权重总和必须等于100，当前总权重为：${totalWeight}`)
    return
  }

  // 验证每个预算方的必填字段
  for (let i = 0; i < slotList.value.length; i++) {
    const slot = slotList.value[i]

    // 验证流量权重
    if (!slot.trafficWeight || slot.trafficWeight <= 0) {
      proxy.$modal.msgWarning(`第 ${i + 1} 个预算方的流量权重不能为空且必须大于0`)
      return
    }

    // 验证底价必填（仅 RTB 模式）
    if (slot.dspPayType == 2 || slot.dspPayType == '2') {
      if (!slot.floorPrice || slot.floorPrice <= 0) {
        proxy.$modal.msgWarning(`第 ${i + 1} 个预算方的底价不能为空且必须大于0`)
        return
      }
    }

    // 验证其他必填字段
    if (!slot.launchStrategy) {
      proxy.$modal.msgWarning(`第 ${i + 1} 个预算方的投放策略不能为空`)
      return
    }
    if (!slot.logCaptureAt && slot.logCaptureAt !== 0) {
      proxy.$modal.msgWarning(`第 ${i + 1} 个预算方的捕获日志时长不能为空`)
      return
    }
    if (!slot.launchTime) {
      proxy.$modal.msgWarning(`第 ${i + 1} 个预算方的投放时段不能为空`)
      return
    }
    if (!slot.regionDirection) {
      proxy.$modal.msgWarning(`第 ${i + 1} 个预算方的地域定向不能为空`)
      return
    }
    if (!slot.brandDirection) {
      proxy.$modal.msgWarning(`第 ${i + 1} 个预算方的品牌定向不能为空`)
      return
    }
  }

  // 构建保存数据
  const saveData = {
    mediaAdId: configMediaAd.value.id,
    slotList: slotList.value.map(slot => ({
      dspSlotId: slot.dspSlotInfoId,
      trafficWeight: slot.trafficWeight,
      launchStrategy: slot.launchStrategy,
      floorPrice: slot.floorPrice,
      ipLimit: slot.ipLimit,
      logCaptureAt: slot.logCaptureAt,
      trackSchwarz: slot.trackSchwarz,
      req: slot.req,
      ims: slot.ims,
      clk: slot.clk,
      launchTime: slot.launchTime,
      regionDirection: slot.regionDirection,
      brandDirection: slot.brandDirection
    }))
  }

  console.log('保存配置数据:', saveData)

  // 调用 API 保存预算方配置
  saveLaunchConfig(saveData).then(() => {
    proxy.$modal.msgSuccess('配置保存成功')
  }).catch(() => {
    proxy.$modal.msgError('配置保存失败')
  })
}

// 页面加载时获取级联数据和媒体列表
loadCascaderData()
loadMediaList()
getList()
</script>

<style scoped>
.app-container-list {
  padding: 0;
}

.app-container-config {
  padding: 20px;
}

.config-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: bold;
}

.empty-config {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 400px;
}

.empty-slots {
  padding: 40px 0;
}

.slot-card {
  margin-bottom: 20px;
}

.slot-card:last-child {
  margin-bottom: 0;
}

.slot-card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: 600;
}

.header-actions {
  display: flex;
  gap: 8px;
}

.slot-info-section {
  margin-bottom: 16px;
}

.slot-collapse {
  margin-top: 16px;
}

.slot-collapse :deep(.el-collapse-item__header) {
  font-weight: 500;
  background-color: #f5f7fa;
}

.slot-collapse :deep(.el-collapse-item__content) {
  padding: 20px;
  background-color: #fafafa;
  border-radius: 4px;
}

.os-tip {
  margin-left: 8px;
  color: #909399;
  font-size: 12px;
}

.os-loading {
  color: #909399;
  font-size: 12px;
}

.form-tip {
  color: #909399;
  font-size: 12px;
  margin-top: 4px;
  line-height: 1.4;
}

.traffic-weight-config {
  background-color: #f5f7fa;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  padding: 16px;
  margin-bottom: 20px;
}

.weight-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
  flex-wrap: wrap;
  gap: 12px;
}

.weight-actions {
  display: flex;
  align-items: center;
  gap: 12px;
}

.weight-title {
  font-size: 14px;
  font-weight: 600;
  color: #303133;
}

.weight-items {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.weight-item {
  display: flex;
  align-items: center;
  padding: 8px 12px;
  background-color: #fff;
  border: 1px solid #e4e7ed;
  border-radius: 4px;
  transition: all 0.3s;
}

.weight-item:hover {
  border-color: #409eff;
  box-shadow: 0 2px 4px rgba(64, 158, 255, 0.1);
}

.weight-label {
  flex: 1;
  font-size: 14px;
  color: #606266;
  margin-right: 12px;
}
</style>
