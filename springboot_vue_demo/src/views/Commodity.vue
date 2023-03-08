<template>
  <div style="padding: 10px">
    <!--    搜索区-->
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="输入商品名称关键字" style="width: 20%" clearable/>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>
    <!--    功能区-->
    <div style="margin: 10px 0">
      <el-button type="primary" @click="add">新增</el-button>
      <el-button type="primary">导入</el-button>
      <el-button type="primary">导出</el-button>
    </div>
    <!--    数据展示区-->
    <el-table :data="tableData" stripe style="width: 100%">
      <el-table-column fixed prop="id" label="ID" sortable/>
      <el-table-column prop="commodityName" label="商品名称"/>
      <el-table-column label="商品照片">
        <template #default="scope">
          <el-image
              style="width: 100px; height: 100px;"
              :src="scope.row.commodityPhoto"
              :preview-src-list="[scope.row.commodityPhoto]"
              :preview-teleported="true"
          >
          </el-image>
        </template>
      </el-table-column>
      <el-table-column prop="commodityPrice" label="商品价格"/>
      <el-table-column prop="commodityUptime" label="商品上架时间"/>
      <el-table-column prop="commodityOuttime" label="商品售出时间"/>
      <el-table-column prop="commodityBusinessId" label="商家Id"/>
      <el-table-column fixed="right" label="操作">
        <template #default="scope">
          <el-popconfirm title="确定删除吗?" @confirm="handleDelete(scope.row.id)">
            <template #reference>
              <el-button link type="danger" size="small">删除</el-button>
            </template>
          </el-popconfirm>
          <el-button link type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin: 10px 0">
      <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[5, 10, 20]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />
    </div>

    <el-dialog v-model="dialogVisible" title="提示" width="30%">
      <el-form :model="form" label-width="120px" :rules="rules" ref="FormRef">
        <el-form-item label="商品名称">
          <el-input v-model="form.commodityName"/>
        </el-form-item>
        <el-form-item label="商品照片">
          <el-upload
              ref="upload"
              class="avatar-uploader"

              :action="filesUploadUrl"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
          >
            <!--              action="http://localhost:9090/files/upload"-->
            <img v-if="form.commodityPhoto" :src="form.commodityPhoto" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
            <template>
              <div class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
            </template>
          </el-upload>
        </el-form-item>
        <el-form-item label="商品价格">
          <el-input v-model="form.commodityPrice"/>
        </el-form-item>

        <el-form-item label="商品上架时间">
          <el-date-picker
              v-model="form.commodityUptime"
              align="right"
              type="date"
              placeholder="选择日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="商品售出时间">
          <el-date-picker
            v-model="form.commodityOuttime"
            align="right"
            type="date"
            placeholder="选择日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="商家Id">
          <el-input v-model="form.commodityBusinessId"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="save()">
            确定
          </el-button>
        </span>
      </template>
    </el-dialog>


  </div>
</template>

<script>
import {ref} from 'vue';
import request from "@/utils/request";

import {Plus} from "@element-plus/icons";

export default {
  name: "Commodity",
  components: {Plus},
  data() {
    return {
      form: {
        commodityPhoto: ''
      },
      dialogVisible: false,
      currentPage: 1,
      pageSize: 10,
      total: 0,
      search: ref(''),
      tableData: [],
      filesUploadUrl: "http://" + window.server.filesUploadUrl + ":9090/files/upload",
      rules: {

      }
    }
  },
  created() {
    this.load()
  },
  methods: {
    handleAvatarSuccess(res){
      this.form.commodityPhoto = res.data
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
    load() {
      request.get("/commodity", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    add() {

      this.dialogVisible = true
      this.form = {}
      this.$nextTick(() =>{
        this.$refs['upload'].clearFiles()  //清楚历史文件列表
      })
    },
    save() {
      if (this.form.id) {  //如果有id 就更新  没有就新增
        request.put("/commodity", this.form).then(res => {
          console.log(res)
          this.$message({
            type: res.code,
            message: "更新" + res.msg
          })
          this.load()  // 刷新表格
          this.dialogVisible = false  //关闭弹窗
        })
      } else {
        request.post("/commodity", this.form).then(res => {
          console.log(res)
          this.$message({
            type: res.code,
            message: "新增" + res.msg
          })
          this.load()  // 刷新表格
          this.dialogVisible = false  //关闭弹窗
        })
      }
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
    },
    handleDelete(id) {
      request.delete("/commodity/" + id).then(res => {
        this.$message({
          type: res.code,
          message: "删除" + res.msg
        })
        this.load()  // 刷新表格
      })
    },
    handleSizeChange(pageSize) {   // 改变当前每页的个数
      // this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {  //改变当前页码触发
      // this.currentPage = pageNum
      this.load()
    }
  },

}
</script>

<style scoped>
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.demo-image__error .image-slot {
  font-size: 30px;
}
.demo-image__error .image-slot .el-icon {
  font-size: 30px;
}
.demo-image__error .el-image {
  width: 100%;
  height: 200px;
}
</style>

<style>
 .avatar-uploader .el-upload {
   border: 1px dashed var(--el-border-color);
   border-radius: 6px;
   cursor: pointer;
   position: relative;
   overflow: hidden;
   transition: var(--el-transition-duration-fast);
 }

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>