<template>
  <div style="height: 50px; line-height: 50px; border-bottom: 1px solid #ccc; display: flex;">
    <div style="width: 200px; padding-left: 30px; font-weight: bold; color: dodgerblue">后台管理</div>
    <div style="flex: 1;"></div>
    <div style="width: 100px;">
      <el-dropdown style="display: contents">
        <span class="el-dropdown-link" >
          {{ admin.adminTname }}
          <el-icon class="el-icon--right">
            <arrow-down/>
          </el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="$router.push('/Admin')">个人信息</el-dropdown-item>
            <el-dropdown-item @click="$router.push('/login')">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import {ArrowDown} from '@element-plus/icons-vue'

export default {
  name: "Header",
  components:{
    ArrowDown
  },
  props: ['admin'],
  data() {
    return {

    }
  },
  created() {
  }
}
</script>

<style scoped>

</style>