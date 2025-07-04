<template>
  <div class="risk-report">
    <div class="page-header">
      <h2>风险报告</h2>
      <el-button type="primary" @click="showAddDialog = true">
        <el-icon><Plus /></el-icon>
        新增报告
      </el-button>
    </div>

    <!-- 搜索区域 -->
    <div class="search-area">
      <el-form :model="searchForm" inline>
        <el-form-item label="报告名称">
          <el-input v-model="searchForm.reportName" placeholder="请输入报告名称" clearable />
        </el-form-item>
        <el-form-item label="报告类型">
          <el-select v-model="searchForm.reportType" placeholder="请选择报告类型" clearable>
            <el-option label="日报" value="日报" />
            <el-option label="周报" value="周报" />
            <el-option label="月报" value="月报" />
            <el-option label="季报" value="季报" />
            <el-option label="年报" value="年报" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="请选择状态" clearable>
            <el-option label="草稿" value="草稿" />
            <el-option label="已提交" value="已提交" />
            <el-option label="已审核" value="已审核" />
            <el-option label="已发布" value="已发布" />
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
        <el-table-column prop="reportName" label="报告名称" min-width="150" />
        <el-table-column prop="reportType" label="报告类型" width="80" />
        <el-table-column prop="reportPeriod" label="报告期间" width="120" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">{{ row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="author" label="作者" width="100" />
        <el-table-column prop="reviewer" label="审核人" width="100" />
        <el-table-column prop="reportDate" label="报告日期" width="160" />
        <el-table-column prop="riskSummary" label="风险摘要" min-width="200" show-overflow-tooltip />
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="viewReport(row)">查看</el-button>
            <el-button type="warning" size="small" @click="editItem(row)">编辑</el-button>
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
      :title="isEdit ? '编辑报告' : '新增报告'"
      width="800px"
    >
      <el-form :model="formData" :rules="rules" ref="formRef" label-width="120px">
        <el-form-item label="报告名称" prop="reportName">
          <el-input v-model="formData.reportName" placeholder="请输入报告名称" />
        </el-form-item>
        <el-form-item label="报告类型" prop="reportType">
          <el-select v-model="formData.reportType" placeholder="请选择报告类型" style="width: 100%">
            <el-option label="日报" value="日报" />
            <el-option label="周报" value="周报" />
            <el-option label="月报" value="月报" />
            <el-option label="季报" value="季报" />
            <el-option label="年报" value="年报" />
          </el-select>
        </el-form-item>
        <el-form-item label="报告期间" prop="reportPeriod">
          <el-input v-model="formData.reportPeriod" placeholder="请输入报告期间" />
        </el-form-item>
        <el-form-item label="风险摘要" prop="riskSummary">
          <el-input
            v-model="formData.riskSummary"
            type="textarea"
            :rows="3"
            placeholder="请输入风险摘要"
          />
        </el-form-item>
        <el-form-item label="市场风险分析" prop="marketRisk">
          <el-input
            v-model="formData.marketRisk"
            type="textarea"
            :rows="3"
            placeholder="请输入市场风险分析"
          />
        </el-form-item>
        <el-form-item label="信用风险分析" prop="creditRisk">
          <el-input
            v-model="formData.creditRisk"
            type="textarea"
            :rows="3"
            placeholder="请输入信用风险分析"
          />
        </el-form-item>
        <el-form-item label="流动性风险分析" prop="liquidityRisk">
          <el-input
            v-model="formData.liquidityRisk"
            type="textarea"
            :rows="3"
            placeholder="请输入流动性风险分析"
          />
        </el-form-item>
        <el-form-item label="操作风险分析" prop="operationalRisk">
          <el-input
            v-model="formData.operationalRisk"
            type="textarea"
            :rows="3"
            placeholder="请输入操作风险分析"
          />
        </el-form-item>
        <el-form-item label="风险建议" prop="recommendations">
          <el-input
            v-model="formData.recommendations"
            type="textarea"
            :rows="3"
            placeholder="请输入风险建议"
          />
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="formData.author" placeholder="请输入作者" />
        </el-form-item>
        <el-form-item label="审核人" prop="reviewer">
          <el-input v-model="formData.reviewer" placeholder="请输入审核人" />
        </el-form-item>
        <el-form-item label="报告日期" prop="reportDate">
          <el-date-picker
            v-model="formData.reportDate"
            type="date"
            placeholder="请选择报告日期"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="formData.status" placeholder="请选择状态" style="width: 100%">
            <el-option label="草稿" value="草稿" />
            <el-option label="已提交" value="已提交" />
            <el-option label="已审核" value="已审核" />
            <el-option label="已发布" value="已发布" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showAddDialog = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </template>
    </el-dialog>

    <!-- 查看报告对话框 -->
    <el-dialog
      v-model="showViewDialog"
      title="风险报告详情"
      width="900px"
    >
      <div class="report-detail" v-if="currentReport">
        <div class="report-header">
          <h3>{{ currentReport.reportName }}</h3>
          <div class="report-meta">
            <span>报告类型：{{ currentReport.reportType }}</span>
            <span>报告期间：{{ currentReport.reportPeriod }}</span>
            <span>状态：<el-tag :type="getStatusType(currentReport.status)">{{ currentReport.status }}</el-tag></span>
          </div>
        </div>
        <div class="report-content">
          <div class="section">
            <h4>风险摘要</h4>
            <p>{{ currentReport.riskSummary }}</p>
          </div>
          <div class="section">
            <h4>市场风险分析</h4>
            <p>{{ currentReport.marketRisk }}</p>
          </div>
          <div class="section">
            <h4>信用风险分析</h4>
            <p>{{ currentReport.creditRisk }}</p>
          </div>
          <div class="section">
            <h4>流动性风险分析</h4>
            <p>{{ currentReport.liquidityRisk }}</p>
          </div>
          <div class="section">
            <h4>操作风险分析</h4>
            <p>{{ currentReport.operationalRisk }}</p>
          </div>
          <div class="section">
            <h4>风险建议</h4>
            <p>{{ currentReport.recommendations }}</p>
          </div>
        </div>
        <div class="report-footer">
          <p>作者：{{ currentReport.author }}</p>
          <p>审核人：{{ currentReport.reviewer }}</p>
          <p>报告日期：{{ currentReport.reportDate }}</p>
        </div>
      </div>
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
const showViewDialog = ref(false)
const isEdit = ref(false)
const formRef = ref()
const currentReport = ref(null)

// 搜索表单
const searchForm = reactive({
  reportName: '',
  reportType: '',
  status: ''
})

// 表单数据
const formData = reactive({
  id: null,
  reportName: '',
  reportType: '',
  reportPeriod: '',
  riskSummary: '',
  marketRisk: '',
  creditRisk: '',
  liquidityRisk: '',
  operationalRisk: '',
  riskMetrics: '',
  recommendations: '',
  author: '',
  reviewer: '',
  reportDate: null,
  status: '草稿'
})

// 表单验证规则
const rules = {
  reportName: [{ required: true, message: '请输入报告名称', trigger: 'blur' }],
  reportType: [{ required: true, message: '请选择报告类型', trigger: 'change' }],
  reportPeriod: [{ required: true, message: '请输入报告期间', trigger: 'blur' }],
  status: [{ required: true, message: '请选择状态', trigger: 'change' }]
}

// 获取数据
const fetchData = async () => {
  loading.value = true
  try {
    const response = await axios.post('/api/risk-report/search', searchForm)
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

// 状态标签类型
const getStatusType = (status) => {
  const types = {
    '草稿': 'info',
    '已提交': 'warning',
    '已审核': 'success',
    '已发布': 'success'
  }
  return types[status] || 'info'
}

// 查看报告
const viewReport = (row) => {
  currentReport.value = row
  showViewDialog.value = true
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
    await ElMessageBox.confirm('确定要删除这个报告吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    const response = await axios.delete(`/api/risk-report/${row.id}`)
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
    
    const url = isEdit.value ? '/api/risk-report/update' : '/api/risk-report/add'
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
    formData[key] = key === 'status' ? '草稿' : ''
  })
  isEdit.value = false
  formRef.value?.resetFields()
}

// 页面加载时获取数据
onMounted(() => {
  fetchData()
})
</script>

<style scoped>
.risk-report {
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

.report-detail {
  padding: 20px;
}

.report-header {
  border-bottom: 2px solid #409eff;
  padding-bottom: 15px;
  margin-bottom: 20px;
}

.report-header h3 {
  margin: 0 0 10px 0;
  color: #303133;
}

.report-meta {
  display: flex;
  gap: 20px;
  color: #606266;
}

.section {
  margin-bottom: 20px;
}

.section h4 {
  color: #409eff;
  margin-bottom: 10px;
  border-left: 3px solid #409eff;
  padding-left: 10px;
}

.section p {
  margin: 0;
  line-height: 1.6;
  color: #606266;
}

.report-footer {
  margin-top: 30px;
  padding-top: 20px;
  border-top: 1px solid #ebeef5;
  color: #909399;
}

.report-footer p {
  margin: 5px 0;
}
</style> 