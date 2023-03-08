<template>
  <div style="padding: 10px">
    <!--    搜索区-->
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="输入关键字" style="width: 20%" clearable/>
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
      <el-table-column prop="name" label="姓名"/>
      <el-table-column prop="sex" label="性别"/>
      <el-table-column prop="age" label="年龄"/>
      <el-table-column prop="birth" label="出生年月"/>
      <el-table-column prop="workCity" label="城市"/>
      <el-table-column prop="address" label="地址"/>
      <el-table-column prop="phone" label="电话"/>
      <el-table-column prop="email" label="邮箱"/>
      <el-table-column prop="account" label="余额"/>
      <el-table-column prop="state" label="状态"/>
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
      <el-form :model="form" label-width="120px" :rules="rules" ref="userFormRef">
        <el-form-item label="用户名">
          <el-input v-model="form.username"/>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickname"/>
        </el-form-item>
        <el-form-item label="名字">
          <el-input v-model="form.name"/>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="性别">
          <el-input v-model="form.sex"/>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="form.age"/>
        </el-form-item>
        <el-form-item label="出生年月">
          <el-date-picker
              v-model="form.birth"
              align="right"
              type="date"
              placeholder="选择日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="工作城市">
          <el-input v-model="form.workCity"/>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="form.address" type="textarea"/>
        </el-form-item>
        <el-form-item label="学历">
          <el-select v-model="form.education" placeholder="你的学历是？">
            <el-option label="大专及以下" value="大专及以下"/>
            <el-option label="本科" value="本科"/>
            <el-option label="研究生及以上" value="研究生及以上"/>
          </el-select>
        </el-form-item>
        <el-form-item
            label="电话"
            prop="phone"
        >
          <el-input maxlength="11" v-model="form.phone"/>
        </el-form-item>
        <el-form-item
            prop="email"
            :rules="[
              { required: true, message: '请输入邮箱地址', trigger: 'blur' },
              { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
            ]"
            label="邮箱">
          <el-input v-model="form.email"/>
        </el-form-item>
        <el-form-item label="头像">
          <el-upload
              ref="upload"
              class="avatar-uploader"
              :action="filesUploadUrl"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
          >
            <img v-if="form.avatar" :src="form.avatar" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
            <template>
              <div class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
            </template>
          </el-upload>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.state" placeholder="您的状态是：？">
            <el-option label="工作" value="工作"/>
            <el-option label="游民" value="游民"/>
            <el-option label="考公" value="考公"/>
            <el-option label="考研" value="考研"/>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="save('userFormRef')">
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
  name: 'User',
  components: {Plus},
  data() {
    var validateMobilePhone = (rule, value, callback) => {
      if (value === '') {
        return callback(new Error('手机号不可为空'));
      } else {
        if (value !== '') {
          var reg = /^1[3456789]\d{9}$/;
          if (!reg.test(value)) {
            return callback(new Error('请输入有效的手机号码'));
          }
        }
        callback();
      }
    };
    return {
      form: {
        phone: '',
        birth: '',
        avatar: '',
      },
      dialogVisible: false,
      currentPage: 1,
      pageSize: 10,
      total: 0,
      search: ref(''),
      category: '1',
      tableData: [],
      filesUploadUrl: "http://" + window.server.filesUploadUrl + ":9090/files/upload",
      rules: {
        phone: [
          { validator: validateMobilePhone, trigger: 'blur' }
        ],
      },
    }
  },
  created() {
    this.load()
  },
  methods: {
    handleAvatarSuccess(res){
      this.form.avatar = res.data
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
      request.get("/user", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search,
          category: this.category
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
    },
    save(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid){
          if (this.form.id) {  //如果有id 就更新  没有就新增
            request.put("/user", this.form).then(res => {
              console.log(res)
              this.$message({
                type: res.code,
                message: "更新" + res.msg
              })
              this.load()  // 刷新表格
              this.dialogVisible = false  //关闭弹窗
            })
          } else {
            request.post("/user", this.form).then(res => {
              console.log(res)
              this.$message({
                type: res.code,
                message: "新增" + res.msg
              })
              this.load()  // 刷新表格
              this.dialogVisible = false  //关闭弹窗
            })
          }
        }
      });

    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
    },
    handleDelete(id) {
      request.delete("/user/" + id).then(res => {
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