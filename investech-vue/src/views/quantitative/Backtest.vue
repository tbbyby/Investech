<template>
  <div class="backtest-page">
    <h2>回测分析</h2>
    <el-button type="primary" @click="loadData">加载数据</el-button>
    <el-table :data="backtestList" v-loading="loading">
      <el-table-column prop="backtestCode" label="回测代码" />
      <el-table-column prop="backtestName" label="回测名称" />
      <el-table-column prop="strategyCode" label="策略代码" />
      <el-table-column prop="totalReturn" label="总收益率" />
      <el-table-column prop="annualReturn" label="年化收益率" />
      <el-table-column prop="maxDrawdown" label="最大回撤" />
      <el-table-column prop="sharpeRatio" label="夏普比率" />
      <el-table-column prop="status" label="状态" />
      <el-table-column prop="createTime" label="创建时间" />
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const loading = ref(false)
const backtestList = ref([])

const loadData = async () => {
  loading.value = true
  try {
    const response = await axios.get('/api/backtest/list')
    if (response.data.code === 200) {
      backtestList.value = response.data.data
    }
  } catch (error) {
    console.error('加载回测数据失败:', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.backtest-page {
  padding: 20px;
}
</style> 