<template>
  <nav style="width: 100%; height: 100vh; background-color: #ffffff; overflow: hidden; ">
    <div style="width: 400px; margin: 150px auto;">
      <div style="color: black; font-size: 30px; text-align: center; padding: 15px 0;">
        个人信息
      </div>
      <el-form
          ref="AddadminFormRef"
          :model="adminForm"
          status-icon
          :rules="rules"
          label-width="70px"
          class="demo-ruleForm"
      >
        <el-form-item label="用户名" prop="adminUsername">
          <el-input  v-model="adminForm.adminUsername" disabled />
        </el-form-item>
        <el-form-item label="密码" prop="adminPassword">
          <el-input  v-model="adminForm.adminPassword" show-password autocomplete="off" />
        </el-form-item>
        <el-form-item
            label="电话"
            prop="adminPhone"
        >
          <el-input maxlength="11" v-model="adminForm.adminPhone"/>
        </el-form-item>
        <el-form-item label="真实姓名" prop="adminTname">
          <el-input  v-model="adminForm.adminTname" />
        </el-form-item>
        <el-form-item
            prop="adminEmail"
            :rules="[
              { required: true, message: '请输入邮箱地址', trigger: 'blur' },
              { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
            ]"
            label="邮箱">
          <el-input v-model="adminForm.adminEmail"/>
        </el-form-item>
        <el-form-item style="width: 100%">
          <el-button type="primary" @click="update('AddadminFormRef')"
          >保存</el-button
          >
          <el-button type="danger" @click="$router.go(-1)">退出</el-button>
        </el-form-item>
      </el-form>
    </div>
  </nav>
</template>

<script>
import request from "@/utils/request";
export default {
  name: "Admin",

  data() {
    var AdminUserName = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('用户名不能为空'));
      }
      callback();
    };
    var AdminPassWord = (rule, value, callback) => {

      if(!(/^[a-zA-Z][a-zA-Z0-9]{7,19}$/.test(value))){ //密码正则校验
        if(value===''){ //若密码为空
          return callback(new Error('请输入密码'));
        }else { //密码非空的报错信息
          return callback(new Error('密码8-20位,包含字母和数字,字母开头,不可以有特殊符号'));
        }
      }else { //密码正确则无错误信息
        callback();
      }
    };
    var AdminPhone = (rule, value, callback) => {
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
      adminForm: {
        adminUsername: '',
        adminPassword: '',
        adminPhone: '',
        adminEmail: '',
      },
      checkEmail: '',
      rules: {
        adminUsername: [
          { validator: AdminUserName, trigger: 'blur' }
        ],
        adminPassword: [
          { validator: AdminPassWord, trigger: 'blur' }
        ],
        adminPhone: [
          { validator: AdminPhone, trigger: 'blur' }
        ],
      }
    }
  },
  created() {
    let str = localStorage.getItem("admin") || "{}"
    this.adminForm = JSON.parse(str)
  },
  methods: {
    update(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid){
          request.put("/admin", this.adminForm ).then(res => {
            if (res.code === '0'){
              this.$message({
                type: res.code,
                message: res.msg
              })
              localStorage.setItem("admin", JSON.stringify(this.adminForm))
              // 触发Layout更新用户信息
              this.$emit("admininfo")
            }else {
              this.$message({
                type: res.code,
                message: res.msg
              })
            }
          })
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>

<style scoped>

</style>