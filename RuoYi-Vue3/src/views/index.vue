<template>
  <div class="dsp-dashboard">
    <!-- 顶部统计卡片 -->
    <el-row :gutter="20" class="stats-cards">
      <el-col :xs="24" :sm="12" :lg="6">
        <div class="stat-card stat-card-blue">
          <div class="stat-icon">
            <el-icon :size="40"><View /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-value">{{ formatNumber(stats.impressions) }}</div>
            <div class="stat-label">广告展现</div>
            <div class="stat-change positive">
              <el-icon><Top /></el-icon>
              <span>+12.5%</span>
            </div>
          </div>
        </div>
      </el-col>

      <el-col :xs="24" :sm="12" :lg="6">
        <div class="stat-card stat-card-green">
          <div class="stat-icon">
            <el-icon :size="40"><Pointer /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-value">{{ formatNumber(stats.clicks) }}</div>
            <div class="stat-label">广告点击</div>
            <div class="stat-change positive">
              <el-icon><Top /></el-icon>
              <span>+8.3%</span>
            </div>
          </div>
        </div>
      </el-col>

      <el-col :xs="24" :sm="12" :lg="6">
        <div class="stat-card stat-card-orange">
          <div class="stat-icon">
            <el-icon :size="40"><Coin /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-value">¥{{ formatMoney(stats.revenue) }}</div>
            <div class="stat-label">预估收益</div>
            <div class="stat-change positive">
              <el-icon><Top /></el-icon>
              <span>+15.2%</span>
            </div>
          </div>
        </div>
      </el-col>

      <el-col :xs="24" :sm="12" :lg="6">
        <div class="stat-card stat-card-purple">
          <div class="stat-icon">
            <el-icon :size="40"><Wallet /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-value">¥{{ formatMoney(stats.profit) }}</div>
            <div class="stat-label">利润</div>
            <div class="stat-change positive">
              <el-icon><Top /></el-icon>
              <span>+10.8%</span>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>

    <!-- 图表区域 -->
    <el-row :gutter="20" class="charts-row">
      <!-- 趋势图 -->
      <el-col :xs="24" :lg="16">
        <el-card class="chart-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <span class="card-title">
                <el-icon><TrendCharts /></el-icon>
                数据趋势分析
              </span>
              <el-radio-group v-model="trendPeriod" size="small" @change="updateTrendChart">
                <el-radio-button label="week">近7天</el-radio-button>
                <el-radio-button label="month">近30天</el-radio-button>
                <el-radio-button label="quarter">近90天</el-radio-button>
              </el-radio-group>
            </div>
          </template>
          <div ref="trendChartRef" class="chart-container"></div>
        </el-card>
      </el-col>

      <!-- 点击率饼图 -->
      <el-col :xs="24" :lg="8">
        <el-card class="chart-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <span class="card-title">
                <el-icon><PieChart /></el-icon>
                广告位分布
              </span>
            </div>
          </template>
          <div ref="pieChartRef" class="chart-container"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" class="charts-row">
      <!-- 收益分析 -->
      <el-col :xs="24" :lg="12">
        <el-card class="chart-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <span class="card-title">
                <el-icon><Histogram /></el-icon>
                收益分析
              </span>
            </div>
          </template>
          <div ref="revenueChartRef" class="chart-container"></div>
        </el-card>
      </el-col>

      <!-- 实时数据 -->
      <el-col :xs="24" :lg="12">
        <el-card class="chart-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <span class="card-title">
                <el-icon><DataLine /></el-icon>
                实时数据监控
              </span>
              <el-tag type="success" size="small">实时更新</el-tag>
            </div>
          </template>
          <div ref="realtimeChartRef" class="chart-container"></div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 详细数据表格 -->
    <el-row :gutter="20" class="charts-row">
      <el-col :span="24">
        <el-card class="chart-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <span class="card-title">
                <el-icon><Document /></el-icon>
                今日详细数据
              </span>
              <el-button type="primary" size="small" @click="refreshData">
                <el-icon><Refresh /></el-icon>
                刷新数据
              </el-button>
            </div>
          </template>
          <el-table :data="tableData" stripe style="width: 100%">
            <el-table-column prop="time" label="时间段" width="180" />
            <el-table-column prop="impressions" label="展现量" sortable>
              <template #default="{ row }">
                {{ formatNumber(row.impressions) }}
              </template>
            </el-table-column>
            <el-table-column prop="clicks" label="点击量" sortable>
              <template #default="{ row }">
                {{ formatNumber(row.clicks) }}
              </template>
            </el-table-column>
            <el-table-column prop="ctr" label="点击率" sortable>
              <template #default="{ row }">
                {{ row.ctr }}%
              </template>
            </el-table-column>
            <el-table-column prop="revenue" label="收益" sortable>
              <template #default="{ row }">
                ¥{{ formatMoney(row.revenue) }}
              </template>
            </el-table-column>
            <el-table-column prop="profit" label="利润" sortable>
              <template #default="{ row }">
                ¥{{ formatMoney(row.profit) }}
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup name="Index">
import { ref, onMounted, onUnmounted, reactive } from 'vue'
import * as echarts from 'echarts'
import {
  View,
  Pointer,
  Coin,
  Wallet,
  Top,
  TrendCharts,
  PieChart,
  Histogram,
  DataLine,
  Document,
  Refresh
} from '@element-plus/icons-vue'

// 统计数据
const stats = reactive({
  impressions: 2847563,
  clicks: 128456,
  revenue: 45678.52,
  profit: 23456.78
})

// 趋势周期
const trendPeriod = ref('week')

// 图表引用
const trendChartRef = ref(null)
const pieChartRef = ref(null)
const revenueChartRef = ref(null)
const realtimeChartRef = ref(null)

// 图表实例
let trendChart = null
let pieChart = null
let revenueChart = null
let realtimeChart = null

// 表格数据
const tableData = ref([
  { time: '00:00 - 04:00', impressions: 125680, clicks: 5632, ctr: 4.48, revenue: 2015.20, profit: 1032.50 },
  { time: '04:00 - 08:00', impressions: 189430, clicks: 8921, ctr: 4.71, revenue: 3195.80, profit: 1638.20 },
  { time: '08:00 - 12:00', impressions: 456789, clicks: 21567, ctr: 4.72, revenue: 7725.50, profit: 3956.30 },
  { time: '12:00 - 16:00', impressions: 623456, clicks: 28123, ctr: 4.51, revenue: 10085.40, profit: 5165.20 },
  { time: '16:00 - 20:00', impressions: 789234, clicks: 35678, ctr: 4.52, revenue: 12795.60, profit: 6555.80 },
  { time: '20:00 - 24:00', impressions: 662974, clicks: 28535, ctr: 4.30, revenue: 9861.02, profit: 5108.78 }
])

// 格式化数字
function formatNumber(num) {
  return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
}

// 格式化金额
function formatMoney(num) {
  return num.toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ',')
}

// 初始化趋势图
function initTrendChart() {
  trendChart = echarts.init(trendChartRef.value)
  updateTrendChart()
}

// 更新趋势图
function updateTrendChart() {
  const data = {
    week: {
      dates: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
      impressions: [382156, 412893, 378456, 445678, 489234, 356789, 382357],
      clicks: [17123, 18923, 16542, 19783, 21895, 15234, 17564]
    },
    month: {
      dates: Array.from({ length: 30 }, (_, i) => `${i + 1}日`),
      impressions: Array.from({ length: 30 }, () => Math.floor(Math.random() * 300000 + 200000)),
      clicks: Array.from({ length: 30 }, () => Math.floor(Math.random() * 15000 + 8000))
    },
    quarter: {
      dates: Array.from({ length: 12 }, (_, i) => `${Math.floor(i / 3) + 1}月${(i % 3) * 10 + 1}日`),
      impressions: Array.from({ length: 12 }, () => Math.floor(Math.random() * 800000 + 500000)),
      clicks: Array.from({ length: 12 }, () => Math.floor(Math.random() * 35000 + 20000))
    }
  }

  const currentData = data[trendPeriod.value]

  const option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'cross',
        label: {
          backgroundColor: '#6a7985'
        }
      }
    },
    legend: {
      data: ['展现量', '点击量'],
      textStyle: { color: '#666' }
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: currentData.dates,
      axisLine: { lineStyle: { color: '#ddd' } },
      axisLabel: { color: '#666' }
    },
    yAxis: [
      {
        type: 'value',
        name: '展现量',
        splitLine: { lineStyle: { type: 'dashed', color: '#eee' } },
        axisLabel: { color: '#666' }
      },
      {
        type: 'value',
        name: '点击量',
        splitLine: { show: false },
        axisLabel: { color: '#666' }
      }
    ],
    series: [
      {
        name: '展现量',
        type: 'line',
        smooth: true,
        lineStyle: { width: 3 },
        showSymbol: false,
        areaStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(64, 158, 255, 0.3)' },
            { offset: 1, color: 'rgba(64, 158, 255, 0.05)' }
          ])
        },
        data: currentData.impressions
      },
      {
        name: '点击量',
        type: 'line',
        yAxisIndex: 1,
        smooth: true,
        lineStyle: { width: 3 },
        showSymbol: false,
        areaStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(103, 194, 58, 0.3)' },
            { offset: 1, color: 'rgba(103, 194, 58, 0.05)' }
          ])
        },
        data: currentData.clicks
      }
    ]
  }

  trendChart.setOption(option)
}

// 初始化饼图
function initPieChart() {
  pieChart = echarts.init(pieChartRef.value)

  const option = {
    tooltip: {
      trigger: 'item',
      formatter: '{b}: {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      left: 'left',
      textStyle: { color: '#666' }
    },
    series: [
      {
        name: '广告位',
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: '#fff',
          borderWidth: 2
        },
        label: {
          show: true,
          formatter: '{b}\n{d}%'
        },
        emphasis: {
          label: {
            show: true,
            fontSize: 16,
            fontWeight: 'bold'
          }
        },
        data: [
          { value: 785432, name: 'Banner横幅', itemStyle: { color: '#5470c6' } },
          { value: 623451, name: '信息流', itemStyle: { color: '#91cc75' } },
          { value: 456789, name: '开屏广告', itemStyle: { color: '#fac858' } },
          { value: 389234, name: '插屏广告', itemStyle: { color: '#ee6666' } },
          { value: 592657, name: '原生广告', itemStyle: { color: '#73c0de' } }
        ]
      }
    ]
  }

  pieChart.setOption(option)
}

// 初始化收益图
function initRevenueChart() {
  revenueChart = echarts.init(revenueChartRef.value)

  const option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    legend: {
      data: ['收益', '利润'],
      textStyle: { color: '#666' }
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
      axisLine: { lineStyle: { color: '#ddd' } },
      axisLabel: { color: '#666' }
    },
    yAxis: {
      type: 'value',
      name: '金额(元)',
      splitLine: { lineStyle: { type: 'dashed', color: '#eee' } },
      axisLabel: {
        formatter: '¥{value}',
        color: '#666'
      }
    },
    series: [
      {
        name: '收益',
        type: 'bar',
        data: [5234.5, 5892.3, 5123.8, 6234.2, 6789.5, 4567.3, 5136.7],
        itemStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: '#ffd666' },
            { offset: 1, color: '#ffa940' }
          ]),
          borderRadius: [5, 5, 0, 0]
        }
      },
      {
        name: '利润',
        type: 'bar',
        data: [2678.2, 3023.5, 2623.4, 3189.6, 3472.8, 2338.9, 2627.8],
        itemStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: '#b37feb' },
            { offset: 1, color: '#722ed1' }
          ]),
          borderRadius: [5, 5, 0, 0]
        }
      }
    ]
  }

  revenueChart.setOption(option)
}

// 初始化实时图
function initRealtimeChart() {
  realtimeChart = echarts.init(realtimeChartRef.value)

  // 生成初始数据
  const now = new Date()
  const data = []
  for (let i = 0; i < 60; i++) {
    data.push({
      name: new Date(now - (60 - i) * 1000),
      value: [
        new Date(now - (60 - i) * 1000),
        Math.round(Math.random() * 100 + 200)
      ]
    })
  }

  const option = {
    tooltip: {
      trigger: 'axis',
      formatter: function(params) {
        params = params[0]
        const date = new Date(params.value[0])
        return `${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')}:${date.getSeconds().toString().padStart(2, '0')}<br/>RPM: ${params.value[1]}`
      },
      axisPointer: {
        animation: false
      }
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'time',
      splitLine: { show: false },
      axisLabel: { color: '#666' }
    },
    yAxis: {
      type: 'value',
      name: 'RPM',
      boundaryGap: [0, '100%'],
      splitLine: { lineStyle: { type: 'dashed', color: '#eee' } },
      axisLabel: { color: '#666' }
    },
    series: [
      {
        name: 'RPM',
        type: 'line',
        showSymbol: false,
        hoverAnimation: false,
        data: data,
        lineStyle: {
          width: 3,
          color: '#52c41a'
        },
        areaStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(82, 196, 26, 0.3)' },
            { offset: 1, color: 'rgba(82, 196, 26, 0.05)' }
          ])
        }
      }
    ]
  }

  realtimeChart.setOption(option)

  // 模拟实时数据更新
  setInterval(() => {
    const now = new Date()
    data.shift()
    data.push({
      name: now,
      value: [
        now,
        Math.round(Math.random() * 100 + 200)
      ]
    })
    realtimeChart.setOption({
      series: [
        {
          data: data
        }
      ]
    })
  }, 1000)
}

// 刷新数据
function refreshData() {
  // 更新统计数据
  stats.impressions = Math.floor(Math.random() * 1000000 + 2000000)
  stats.clicks = Math.floor(Math.random() * 50000 + 100000)
  stats.revenue = Math.random() * 20000 + 30000
  stats.profit = Math.random() * 10000 + 15000

  // 更新图表
  updateTrendChart()
}

// 窗口大小变化时重新渲染图表
function handleResize() {
  trendChart?.resize()
  pieChart?.resize()
  revenueChart?.resize()
  realtimeChart?.resize()
}

onMounted(() => {
  // 初始化所有图表
  setTimeout(() => {
    initTrendChart()
    initPieChart()
    initRevenueChart()
    initRealtimeChart()
  }, 100)

  // 监听窗口大小变化
  window.addEventListener('resize', handleResize)
})

onUnmounted(() => {
  // 销毁图表实例
  trendChart?.dispose()
  pieChart?.dispose()
  revenueChart?.dispose()
  realtimeChart?.dispose()

  // 移除事件监听
  window.removeEventListener('resize', handleResize)
})
</script>

<style scoped lang="scss">
.dsp-dashboard {
  padding: 20px;
  background: #f5f7fa;
  min-height: calc(100vh - 84px);

  .stats-cards {
    margin-bottom: 20px;

    .stat-card {
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      border-radius: 16px;
      padding: 24px;
      color: #fff;
      display: flex;
      align-items: center;
      gap: 20px;
      box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
      transition: transform 0.3s, box-shadow 0.3s;
      cursor: pointer;
      position: relative;
      overflow: hidden;

      &::before {
        content: '';
        position: absolute;
        top: -50%;
        right: -50%;
        width: 200%;
        height: 200%;
        background: radial-gradient(circle, rgba(255, 255, 255, 0.1) 0%, transparent 70%);
        transition: transform 0.5s;
      }

      &:hover {
        transform: translateY(-5px);
        box-shadow: 0 12px 30px rgba(0, 0, 0, 0.15);

        &::before {
          transform: scale(1.2);
        }
      }

      .stat-icon {
        width: 70px;
        height: 70px;
        background: rgba(255, 255, 255, 0.2);
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        backdrop-filter: blur(10px);
      }

      .stat-content {
        flex: 1;

        .stat-value {
          font-size: 28px;
          font-weight: bold;
          margin-bottom: 8px;
        }

        .stat-label {
          font-size: 14px;
          opacity: 0.9;
          margin-bottom: 8px;
        }

        .stat-change {
          display: flex;
          align-items: center;
          gap: 4px;
          font-size: 13px;

          &.positive {
            color: #95de64;
          }

          &.negative {
            color: #ff4d4f;
          }
        }
      }

      &.stat-card-blue {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      }

      &.stat-card-green {
        background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
      }

      &.stat-card-orange {
        background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
      }

      &.stat-card-purple {
        background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
      }
    }
  }

  .charts-row {
    margin-bottom: 20px;

    .chart-card {
      border-radius: 12px;
      overflow: hidden;

      :deep(.el-card__header) {
        padding: 18px 24px;
        border-bottom: 1px solid #f0f0f0;
        background: #fff;
      }

      :deep(.el-card__body) {
        padding: 24px;
      }

      .card-header {
        display: flex;
        justify-content: space-between;
        align-items: center;

        .card-title {
          display: flex;
          align-items: center;
          gap: 8px;
          font-size: 16px;
          font-weight: 600;
          color: #333;
        }
      }

      .chart-container {
        width: 100%;
        height: 350px;
      }
    }
  }
}

// 响应式设计
@media (max-width: 768px) {
  .dsp-dashboard {
    padding: 10px;

    .stats-cards {
      .stat-card {
        padding: 16px;
        margin-bottom: 10px;

        .stat-icon {
          width: 50px;
          height: 50px;

          :deep(.el-icon) {
            font-size: 28px !important;
          }
        }

        .stat-content {
          .stat-value {
            font-size: 22px;
          }
        }
      }
    }

    .charts-row {
      .chart-card {
        margin-bottom: 10px;

        .chart-container {
          height: 280px;
        }
      }
    }
  }
}
</style>
