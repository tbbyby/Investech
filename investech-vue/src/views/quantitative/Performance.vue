<template>
  <div class="performance-page">
    <h2>绩效评估</h2>
    <el-button type="primary" @click="loadData">加载数据</el-button>
    <el-table :data="performanceList" v-loading="loading">
      <el-table-column prop="evaluationCode" label="评估代码" />
      <el-table-column prop="evaluationName" label="评估名称" />
      <el-table-column prop="strategyCode" label="策略代码" />
      <el-table-column prop="totalReturn" label="总收益率" />
      <el-table-column prop="annualReturn" label="年化收益率" />
      <el-table-column prop="sharpeRatio" label="夏普比率" />
      <el-table-column prop="maxDrawdown" label="最大回撤" />
      <el-table-column prop="evaluationResult" label="评估结果" />
      <el-table-column prop="evaluationDate" label="评估日期" />
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const loading = ref(false)
const performanceList = ref([])

const loadData = async () => {
  loading.value = true
  try {
    const response = await axios.get('/api/performance/list')
    if (response.data.code === 200) {
      performanceList.value = response.data.data
    }
  } catch (error) {
    console.error('加载绩效评估数据失败:', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.performance-page {
  padding: 20px;
}
</style> 