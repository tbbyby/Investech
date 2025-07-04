<template>
  <div class="compliance-check">
    <div class="page-header">
      <h2>合规检查</h2>
      <el-button type="primary" @click="showAddDialog = true">
        <el-icon><Plus /></el-icon>
        新增检查
      </el-button>
    </div>

    <!-- 搜索区域 -->
    <div class="search-area">
      <el-form :model="searchForm" inline>
        <el-form-item label="检查名称">
          <el-input v-model="searchForm.checkName" placeholder="请输入检查名称" clearable />
        </el-form-item>
        <el-form-item label="检查类型">
          <el-select v-model="searchForm.checkType" placeholder="请选择检查类型" clearable>
            <el-option label="投资限制" value="投资限制" />
            <el-option label="持仓限制" value="持仓限制" />
            <el-option label="交易限制" value="交易限制" />
            <el-option label="信息披露" value="信息披露" />
          </el-select>
        </el-form-item>
        <el-form-item label="检查结果">
          <el-select v-model="searchForm.checkResult" placeholder="请选择检查结果" clearable>
            <el-option label="通过" value="通过" />
            <el-option label="不通过" value="不通过" />
            <el-option label="待确认" value="待确认" />
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
        <el-table-column prop="checkName" label="检查名称" min-width="120" />
        <el-table-column prop="checkType" label="检查类型" width="100" />
        <el-table-column prop="checkRule" label="检查规则" min-width="150" show-overflow-tooltip />
        <el-table-column prop="checkResult" label="检查结果" width="100">
          <template #default="{ row }">
            <el-tag :type="getResultType(row.checkResult)">{{ row.checkResult }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">{{ row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="responsiblePerson" label="责任人" width="100" />
        <el-table-column prop="checkTime" label="检查时间" width="160" />
        <el-table-column prop="violationDescription" label="违规描述" min-width="200" show-overflow-tooltip />
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
      :title="isEdit ? '编辑检查' : '新增检查'"
      width="700px"
    >
      <el-form :model="formData" :rules="rules" ref="formRef" label-width="120px">
        <el-form-item label="检查名称" prop="checkName">
          <el-input v-model="formData.checkName" placeholder="请输入检查名称" />
        </el-form-item>
        <el-form-item label="检查类型" prop="checkType">
          <el-select v-model="formData.checkType" placeholder="请选择检查类型" style="width: 100%">
            <el-option label="投资限制" value="投资限制" />
            <el-option label="持仓限制" value="持仓限制" />
            <el-option label="交易限制" value="交易限制" />
            <el-option label="信息披露" value="信息披露" />
          </el-select>
        </el-form-item>
        <el-form-item label="检查规则" prop="checkRule">
          <el-input v-model="formData.checkRule" placeholder="请输入检查规则" />
        </el-form-item>
        <el-form-item label="检查结果" prop="checkResult">
          <el-select v-model="formData.checkResult" placeholder="请选择检查结果" style="width: 100%">
            <el-option label="通过" value="通过" />
            <el-option label="不通过" value="不通过" />
            <el-option label="待确认" value="待确认" />
          </el-select>
        </el-form-item>
        <el-form-item label="违规类型" prop="violationType">
          <el-input v-model="formData.violationType" placeholder="请输入违规类型" />
        </el-form-item>
        <el-form-item label="违规描述" prop="violationDescription">
          <el-input
            v-model="formData.violationDescription"
            type="textarea"
            :rows="3"
            placeholder="请输入违规描述"
          />
        </el-form-item>
        <el-form-item label="需要采取的行动" prop="actionRequired">
          <el-input
            v-model="formData.actionRequired"
            type="textarea"
            :rows="2"
            placeholder="请输入需要采取的行动"
          />
        </el-form-item>
        <el-form-item label="责任人" prop="responsiblePerson">
          <el-input v-model="formData.responsiblePerson" placeholder="请输入责任人" />
        </el-form-item>
        <el-form-item label="检查时间" prop="checkTime">
          <el-date-picker
            v-model="formData.checkTime"
            type="datetime"
            placeholder="请选择检查时间"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="formData.status" placeholder="请选择状态" style="width: 100%">
            <el-option label="待检查" value="待检查" />
            <el-option label="已检查" value="已检查" />
            <el-option label="已解决" value="已解决" />
          </el-select>
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
  checkName: '',
  checkType: '',
  checkResult: ''
})

// 表单数据
const formData = reactive({
  id: null,
  checkName: '',
  checkType: '',
  checkRule: '',
  checkResult: '待确认',
  violationType: '',
  violationDescription: '',
  actionRequired: '',
  responsiblePerson: '',
  checkTime: null,
  resolveTime: null,
  status: '待检查'
})

// 表单验证规则
const rules = {
  checkName: [{ required: true, message: '请输入检查名称', trigger: 'blur' }],
  checkType: [{ required: true, message: '请选择检查类型', trigger: 'change' }],
  checkRule: [{ required: true, message: '请输入检查规则', trigger: 'blur' }],
  checkResult: [{ required: true, message: '请选择检查结果', trigger: 'change' }],
  status: [{ required: true, message: '请选择状态', trigger: 'change' }]
}

// 获取数据
const fetchData = async () => {
  loading.value = true
  try {
    const response = await axios.post('/api/compliance-check/search', searchForm)
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

// 检查结果标签类型
const getResultType = (result) => {
  const types = {
    '通过': 'success',
    '不通过': 'danger',
    '待确认': 'warning'
  }
  return types[result] || 'info'
}

// 状态标签类型
const getStatusType = (status) => {
  const types = {
    '待检查': 'warning',
    '已检查': 'info',
    '已解决': 'success'
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
    await ElMessageBox.confirm('确定要删除这个检查项吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    const response = await axios.delete(`/api/compliance-check/${row.id}`)
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
    
    const url = isEdit.value ? '/api/compliance-check/update' : '/api/compliance-check/add'
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
    formData[key] = key === 'checkResult' ? '待确认' : (key === 'status' ? '待检查' : '')
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
.compliance-check {
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