<template>
  <div>

      <el-menu
          style="width: 200px; min-height: calc(100vh - 50px)"
          :default-active="path"
          router
          class="el-menu-vertical-demo"
      >
<!--          @open="handleOpen"-->
<!--          @close="handleClose"-->

        <el-menu-item index="commodity">
          <el-icon><icon-menu /></el-icon>
          <span>商品管理</span>
        </el-menu-item>
        <el-sub-menu index="1" v-if="admin.lv == 1">
          <template #title><el-icon><setting /></el-icon>系统管理</template>
          <el-menu-item index="/user">用户管理</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="news">
          <el-icon><location /></el-icon>
          <span>首页新闻管理</span>
        </el-menu-item>
        <el-menu-item index="3">
          <el-icon><document /></el-icon>
          <span>订单管理</span>
        </el-menu-item>
      </el-menu>
  </div>
</template>

<script>
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting,
} from '@element-plus/icons-vue'
import request from "@/utils/request";

// const handleOpen = (key: string, keyPath: string[]) => {
//   console.log(key, keyPath)
// }
// const handleClose = (key: string, keyPath: string[]) => {
//   console.log(key, keyPath)
// }

export default {
  name: "Aside",
  components: {
    Document,
    IconMenu,
    Location,
    Setting,
  },
  data(){
    return {
      //设置菜单的默认高亮
      path: this.$route.path,
      admin: {},
    }
  },
  created() {
    let adminJson = localStorage.getItem("admin");
    if (!adminJson) {
      return
    }
    let id = JSON.parse(adminJson).id
    // 从后台取出更新后的最新用户信息
    request.get("/admin/"+ id).then(res => {
      this.admin = res.data
    })
  }

}
</script>

<style scoped>

</style>