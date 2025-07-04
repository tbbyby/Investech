<template>
  <div class="risk-monitor">
    <div class="page-header">
      <h2>风险监控</h2>
      <el-button type="primary" @click="showAddDialog = true">
        <el-icon><Plus /></el-icon>
        新增监控
      </el-button>
    </div>

    <!-- 搜索区域 -->
    <div class="search-area">
      <el-form :model="searchForm" inline>
        <el-form-item label="监控名称">
          <el-input v-model="searchForm.monitorName" placeholder="请输入监控名称" clearable />
        </el-form-item>
        <el-form-item label="监控类型">
          <el-select v-model="searchForm.monitorType" placeholder="请选择监控类型" clearable>
            <el-option label="市场风险" value="市场风险" />
            <el-option label="信用风险" value="信用风险" />
            <el-option label="流动性风险" value="流动性风险" />
            <el-option label="操作风险" value="操作风险" />
          </el-select>
        </el-form-item>
        <el-form-item label="风险等级">
          <el-select v-model="searchForm.riskLevel" placeholder="请选择风险等级" clearable>
            <el-option label="低" value="低" />
            <el-option label="中" value="中" />
            <el-option label="高" value="高" />
            <el-option label="极高" value="极高" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchData">搜索</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 数据表格 -->
    <div class="table-area">
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="monitorName" label="监控名称" min-width="120" />
        <el-table-column prop="monitorType" label="监控类型" width="100" />
        <el-table-column prop="riskIndicator" label="风险指标" width="120" />
        <el-table-column prop="threshold" label="阈值" width="100">
          <template #default="{ row }">
            {{ (row.threshold * 100).toFixed(2) }}%
          </template>
        </el-table-column>
        <el-table-column prop="currentValue" label="当前值" width="100">
          <template #default="{ row }">
            {{ (row.currentValue * 100).toFixed(2) }}%
          </template>
        </el-table-column>
        <el-table-column prop="riskLevel" label="风险等级" width="80">
          <template #default="{ row }">
            <el-tag :type="getRiskLevelType(row.riskLevel)">{{ row.riskLevel }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">{{ row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="description" label="描述" min-width="200" show-overflow-tooltip />
        <el-table-column prop="createTime" label="创建时间" width="160" />
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="editItem(row)">编辑</el-button>
            <el-button type="danger" size="small" @click="deleteItem(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination-area">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[10, 20, 50, 100]"
          :total="total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </div>

    <!-- 新增/编辑对话框 -->
    <el-dialog
      v-model="showAddDialog"
      :title="isEdit ? '编辑监控' : '新增监控'"
      width="600px"
    >
      <el-form :model="formData" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="监控名称" prop="monitorName">
          <el-input v-model="formData.monitorName" placeholder="请输入监控名称" />
        </el-form-item>
        <el-form-item label="监控类型" prop="monitorType">
          <el-select v-model="formData.monitorType" placeholder="请选择监控类型" style="width: 100%">
            <el-option label="市场风险" value="市场风险" />
            <el-option label="信用风险" value="信用风险" />
            <el-option label="流动性风险" value="流动性风险" />
            <el-option label="操作风险" value="操作风险" />
          </el-select>
        </el-form-item>
        <el-form-item label="风险指标" prop="riskIndicator">
          <el-input v-model="formData.riskIndicator" placeholder="请输入风险指标" />
        </el-form-item>
        <el-form-item label="阈值" prop="threshold">
          <el-input-number
            v-model="formData.threshold"
            :precision="4"
            :step="0.001"
            :min="0"
            :max="1"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="当前值" prop="currentValue">
          <el-input-number
            v-model="formData.currentValue"
            :precision="4"
            :step="0.001"
            :min="0"
            :max="1"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="风险等级" prop="riskLevel">
          <el-select v-model="formData.riskLevel" placeholder="请选择风险等级" style="width: 100%">
            <el-option label="低" value="低" />
            <el-option label="中" value="中" />
            <el-option label="高" value="高" />
            <el-option label="极高" value="极高" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="formData.status" placeholder="请选择状态" style="width: 100%">
            <el-option label="正常" value="正常" />
            <el-option label="预警" value="预警" />
            <el-option label="超限" value="超限" />
          </el-select>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input
            v-model="formData.description"
            type="textarea"
            :rows="3"
            placeholder="请输入描述"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showAddDialog = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import axios from 'axios'

// 响应式数据
const loading = ref(false)
const tableData = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const showAddDialog = ref(false)
const isEdit = ref(false)
const formRef = ref()

// 搜索表单
const searchForm = reactive({
  monitorName: '',
  monitorType: '',
  riskLevel: ''
})

// 表单数据
const formData = reactive({
  id: null,
  monitorName: '',
  monitorType: '',
  riskIndicator: '',
  threshold: 0,
  currentValue: 0,
  riskLevel: '',
  status: '正常',
  description: ''
})

// 表单验证规则
const rules = {
  monitorName: [{ required: true, message: '请输入监控名称', trigger: 'blur' }],
  monitorType: [{ required: true, message: '请选择监控类型', trigger: 'change' }],
  riskIndicator: [{ required: true, message: '请输入风险指标', trigger: 'blur' }],
  threshold: [{ required: true, message: '请输入阈值', trigger: 'blur' }],
  riskLevel: [{ required: true, message: '请选择风险等级', trigger: 'change' }],
  status: [{ required: true, message: '请选择状态', trigger: 'change' }]
}

// 获取数据
const fetchData = async () => {
  loading.value = true
  try {
    const response = await axios.post('/api/risk-monitor/search', searchForm)
    if (response.data.success) {
      tableData.value = response.data.data
      total.value = response.data.data.length
    } else {
      ElMessage.error(response.data.message || '获取数据失败')
    }
  } catch (error) {
    console.error('获取数据失败:', error)
    ElMessage.error('获取数据失败')
  } finally {
    loading.value = false
  }
}

// 搜索
const searchData = () => {
  currentPage.value = 1
  fetchData()
}

// 重置搜索
const resetSearch = () => {
  Object.keys(searchForm).forEach(key => {
    searchForm[key] = ''
  })
  searchData()
}

// 分页处理
const handleSizeChange = (val) => {
  pageSize.value = val
  fetchData()
}

const handleCurrentChange = (val) => {
  currentPage.value = val
  fetchData()
}

// 风险等级标签类型
const getRiskLevelType = (level) => {
  const types = {
    '低': 'success',
    '中': 'warning',
    '高': 'danger',
    '极高': 'danger'
  }
  return types[level] || 'info'
}

// 状态标签类型
const getStatusType = (status) => {
  const types = {
    '正常': 'success',
    '预警': 'warning',
    '超限': 'danger'
  }
  return types[status] || 'info'
}

// 编辑项目
const editItem = (row) => {
  isEdit.value = true
  Object.keys(formData).forEach(key => {
    if (row[key] !== undefined) {
      formData[key] = row[key]
    }
  })
  showAddDialog.value = true
}

// 删除项目
const deleteItem = async (row) => {
  try {
    await ElMessageBox.confirm('确定要删除这个监控项吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    const response = await axios.delete(`/api/risk-monitor/${row.id}`)
    if (response.data.success) {
      ElMessage.success('删除成功')
      fetchData()
    } else {
      ElMessage.error(response.data.message || '删除失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除失败:', error)
      ElMessage.error('删除失败')
    }
  }
}

// 提交表单
const submitForm = async () => {
  try {
    await formRef.value.validate()
    
    const url = isEdit.value ? '/api/risk-monitor/update' : '/api/risk-monitor/add'
    const response = await axios[isEdit.value ? 'put' : 'post'](url, formData)
    
    if (response.data.success) {
      ElMessage.success(isEdit.value ? '更新成功' : '添加成功')
      showAddDialog.value = false
      resetForm()
      fetchData()
    } else {
      ElMessage.error(response.data.message || (isEdit.value ? '更新失败' : '添加失败'))
    }
  } catch (error) {
    console.error('提交失败:', error)
    ElMessage.error('提交失败')
  }
}

// 重置表单
const resetForm = () => {
  Object.keys(formData).forEach(key => {
    formData[key] = key === 'status' ? '正常' : (key === 'threshold' || key === 'currentValue' ? 0 : '')
  })
  isEdit.value = false
  formRef.value?.resetFields()
}

// 监听对话框关闭
const handleDialogClose = () => {
  resetForm()
}

// 页面加载时获取数据
onMounted(() => {
  fetchData()
})
</script>

<style scoped>
.risk-monitor {
  padding: 20px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.page-header h2 {
  margin: 0;
  color: #303133;
}

.search-area {
  background: #fff;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.table-area {
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.pagination-area {
  padding: 20px;
  text-align: right;
}
</style> 