<template>
  <nav>
    <!--    头部-->
    <Header :admin="admin"/>
    <!--    主体-->
    <div style="display: flex">
      <!--      侧边栏-->
      <Aside/>
      <!--      内容-->
      <router-view style="flex: 1"  @admininfo="refreshAdmin"/>
    </div>
  </nav>
</template>

<script>
import Header from "@/components/Header";
import Aside from "@/components/Aside";
import request from "@/utils/request";

export default {
  name: "Layout",
  components: {
    Header,
    Aside
  },
  data() {
    return {
      admin: {}
    }
  },
  created() {
    this.refreshAdmin()
  },
  methods: {
    refreshAdmin() {
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
}
</script>

<style scoped>

</style>