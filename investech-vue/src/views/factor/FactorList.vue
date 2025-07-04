<template>
  <div class="factor-list">
    <div class="page-header">
      <h2>因子管理</h2>
      <el-button type="primary" @click="showAddDialog = true">
        <el-icon><Plus /></el-icon>
        新增因子
      </el-button>
    </div>

    <!-- 搜索区域 -->
    <div class="search-area">
      <el-form :model="searchForm" inline>
        <el-form-item label="因子代码">
          <el-input v-model="searchForm.factorCode" placeholder="请输入因子代码" clearable />
        </el-form-item>
        <el-form-item label="因子名称">
          <el-input v-model="searchForm.factorName" placeholder="请输入因子名称" clearable />
        </el-form-item>
        <el-form-item label="因子类型">
          <el-select v-model="searchForm.factorType" placeholder="请选择因子类型" clearable>
            <el-option label="基础因子" value="基础因子" />
            <el-option label="衍生因子" value="衍生因子" />
            <el-option label="风格因子" value="风格因子" />
          </el-select>
        </el-form-item>
        <el-form-item label="因子分类">
          <el-select v-model="searchForm.category" placeholder="请选择因子分类" clearable>
            <el-option label="收益类" value="收益类" />
            <el-option label="风险类" value="风险类" />
            <el-option label="风险调整类" value="风险调整类" />
            <el-option label="规模类" value="规模类" />
            <el-option label="成本类" value="成本类" />
            <el-option label="时间类" value="时间类" />
            <el-option label="交易类" value="交易类" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchFactors">搜索</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 因子列表 -->
    <div class="factor-table">
      <el-table :data="factorList" v-loading="loading" stripe>
        <el-table-column prop="factorCode" label="因子代码" width="120" />
        <el-table-column prop="factorName" label="因子名称" width="150" />
        <el-table-column prop="factorDesc" label="因子描述" show-overflow-tooltip />
        <el-table-column prop="factorType" label="因子类型" width="100" />
        <el-table-column prop="category" label="因子分类" width="100" />
        <el-table-column prop="dataSource" label="数据来源" width="120" />
        <el-table-column prop="unit" label="单位" width="80" />
        <el-table-column prop="level" label="层级" width="80" />
        <el-table-column prop="sortOrder" label="排序" width="80" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="scope">
            <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
              {{ scope.row.status === 1 ? '正常' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="scope">
            <el-button type="primary" size="small" @click="editFactor(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="deleteFactor(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页 -->
    <div class="pagination">
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

    <!-- 新增/编辑因子对话框 -->
    <el-dialog
      v-model="showAddDialog"
      :title="isEdit ? '编辑因子' : '新增因子'"
      width="600px"
    >
      <el-form :model="factorForm" :rules="rules" ref="factorFormRef" label-width="100px">
        <el-form-item label="因子代码" prop="factorCode">
          <el-input v-model="factorForm.factorCode" placeholder="请输入因子代码" />
        </el-form-item>
        <el-form-item label="因子名称" prop="factorName">
          <el-input v-model="factorForm.factorName" placeholder="请输入因子名称" />
        </el-form-item>
        <el-form-item label="因子描述" prop="factorDesc">
          <el-input
            v-model="factorForm.factorDesc"
            type="textarea"
            :rows="3"
            placeholder="请输入因子描述"
          />
        </el-form-item>
        <el-form-item label="因子类型" prop="factorType">
          <el-select v-model="factorForm.factorType" placeholder="请选择因子类型">
            <el-option label="基础因子" value="基础因子" />
            <el-option label="衍生因子" value="衍生因子" />
            <el-option label="风格因子" value="风格因子" />
          </el-select>
        </el-form-item>
        <el-form-item label="数据来源" prop="dataSource">
          <el-input v-model="factorForm.dataSource" placeholder="请输入数据来源" />
        </el-form-item>
        <el-form-item label="计算方法" prop="calculationMethod">
          <el-input
            v-model="factorForm.calculationMethod"
            type="textarea"
            :rows="2"
            placeholder="请输入计算方法"
          />
        </el-form-item>
        <el-form-item label="单位" prop="unit">
          <el-input v-model="factorForm.unit" placeholder="请输入单位" />
        </el-form-item>
        <el-form-item label="因子分类" prop="category">
          <el-select v-model="factorForm.category" placeholder="请选择因子分类">
            <el-option label="收益类" value="收益类" />
            <el-option label="风险类" value="风险类" />
            <el-option label="风险调整类" value="风险调整类" />
            <el-option label="规模类" value="规模类" />
            <el-option label="成本类" value="成本类" />
            <el-option label="时间类" value="时间类" />
            <el-option label="交易类" value="交易类" />
          </el-select>
        </el-form-item>
        <el-form-item label="父因子代码" prop="parentCode">
          <el-input v-model="factorForm.parentCode" placeholder="请输入父因子代码" />
        </el-form-item>
        <el-form-item label="层级" prop="level">
          <el-input-number v-model="factorForm.level" :min="1" :max="10" />
        </el-form-item>
        <el-form-item label="排序" prop="sortOrder">
          <el-input-number v-model="factorForm.sortOrder" :min="0" :max="999" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="factorForm.status">
            <el-radio :label="1">正常</el-radio>
            <el-radio :label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showAddDialog = false">取消</el-button>
          <el-button type="primary" @click="saveFactor">确定</el-button>
        </span>
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
const factorList = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const showAddDialog = ref(false)
const isEdit = ref(false)
const factorFormRef = ref()

// 搜索表单
const searchForm = reactive({
  factorCode: '',
  factorName: '',
  factorType: '',
  category: ''
})

// 因子表单
const factorForm = reactive({
  id: null,
  factorCode: '',
  factorName: '',
  factorDesc: '',
  factorType: '',
  dataSource: '',
  calculationMethod: '',
  unit: '',
  category: '',
  parentCode: '',
  level: 1,
  sortOrder: 0,
  status: 1
})

// 表单验证规则
const rules = {
  factorCode: [
    { required: true, message: '请输入因子代码', trigger: 'blur' }
  ],
  factorName: [
    { required: true, message: '请输入因子名称', trigger: 'blur' }
  ],
  factorType: [
    { required: true, message: '请选择因子类型', trigger: 'change' }
  ],
  category: [
    { required: true, message: '请选择因子分类', trigger: 'change' }
  ]
}

// 获取因子列表
const getFactorList = async () => {
  loading.value = true
  try {
    const response = await axios.get('/api/factor/list')
    if (response.data.code === 200) {
      factorList.value = response.data.data
      total.value = response.data.data.length
    }
  } catch (error) {
    ElMessage.error('获取因子列表失败')
  } finally {
    loading.value = false
  }
}

// 搜索因子
const searchFactors = async () => {
  loading.value = true
  try {
    const params = {
      factorCode: searchForm.factorCode,
      factorName: searchForm.factorName,
      factorType: searchForm.factorType,
      category: searchForm.category
    }
    const response = await axios.get('/api/factor/search', { params })
    if (response.data.code === 200) {
      factorList.value = response.data.data
      total.value = response.data.data.length
    }
  } catch (error) {
    ElMessage.error('搜索因子失败')
  } finally {
    loading.value = false
  }
}

// 重置搜索
const resetSearch = () => {
  Object.keys(searchForm).forEach(key => {
    searchForm[key] = ''
  })
  getFactorList()
}

// 编辑因子
const editFactor = (row) => {
  isEdit.value = true
  Object.keys(factorForm).forEach(key => {
    if (row[key] !== undefined) {
      factorForm[key] = row[key]
    }
  })
  showAddDialog.value = true
}

// 删除因子
const deleteFactor = async (row) => {
  try {
    await ElMessageBox.confirm('确定要删除这个因子吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    const response = await axios.delete(`/api/factor/${row.id}`)
    if (response.data.code === 200) {
      ElMessage.success('删除成功')
      getFactorList()
    } else {
      ElMessage.error(response.data.message || '删除失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

// 保存因子
const saveFactor = async () => {
  try {
    await factorFormRef.value.validate()
    
    const url = isEdit.value ? '/api/factor/update' : '/api/factor/add'
    const method = isEdit.value ? 'put' : 'post'
    
    const response = await axios[method](url, factorForm)
    if (response.data.code === 200) {
      ElMessage.success(isEdit.value ? '更新成功' : '添加成功')
      showAddDialog.value = false
      getFactorList()
      resetForm()
    } else {
      ElMessage.error(response.data.message || (isEdit.value ? '更新失败' : '添加失败'))
    }
  } catch (error) {
    ElMessage.error(isEdit.value ? '更新失败' : '添加失败')
  }
}

// 重置表单
const resetForm = () => {
  Object.keys(factorForm).forEach(key => {
    if (key === 'level') {
      factorForm[key] = 1
    } else if (key === 'sortOrder') {
      factorForm[key] = 0
    } else if (key === 'status') {
      factorForm[key] = 1
    } else {
      factorForm[key] = ''
    }
  })
  isEdit.value = false
}

// 分页处理
const handleSizeChange = (val) => {
  pageSize.value = val
  getFactorList()
}

const handleCurrentChange = (val) => {
  currentPage.value = val
  getFactorList()
}

// 监听对话框关闭
const handleDialogClose = () => {
  resetForm()
}

onMounted(() => {
  getFactorList()
})
</script>

<style scoped>
.factor-list {
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
  background: #f5f7fa;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
}

.factor-table {
  margin-bottom: 20px;
}

.pagination {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style> 