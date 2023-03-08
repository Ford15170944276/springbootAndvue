<template>
  <nav style="width: 100%; height: 100vh; background-color: #ffffff; overflow: hidden; ">
    <div style="width: 400px; margin: 150px auto;">
      <div style="color: black; font-size: 30px; text-align: center; padding: 15px 0;">
        欢迎登录
      </div>
      <el-form
          ref="adminFormRef"
          :model="adminForm"
          status-icon
          :rules="rules"
          label-width="70px"
          class="demo-ruleForm"
      >
        <el-form-item label="用户名" prop="adminUsername">
          <el-input :prefix-icon="Avatar" v-model="adminForm.adminUsername" />
        </el-form-item>
        <el-form-item label="密码" prop="adminPassword">
          <el-input :prefix-icon="Lock"  v-model="adminForm.adminPassword" show-password autocomplete="off" />
        </el-form-item>
        <el-form-item>
          <div style="display: flex">
            <el-input prefix-icon="el-icon-key" v-model="validCodes" style="width: 50%;" placeholder="请输入验证码"></el-input>
            <ValidCode @input="createValidCode" />
          </div>
        </el-form-item>
        <el-form-item style="width: 100%">
          <el-button type="primary" @click="login('adminFormRef')"
          >登录</el-button
          >
          <el-button @click="$router.push('/register')">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </nav>
</template>

<script>
import { Avatar, Lock } from "@element-plus/icons-vue"
import request from "@/utils/request";
import ValidCode from "@/components/ValidCode.vue";
export default {
  name: 'Login',
  components: {
    ValidCode
  },
  setup(){
    return{
      Avatar,
      Lock
    }
  },
  data() {
    var adminUserName = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('用户名不能为空'));
      }
      callback();
    };
    var adminPassWord = (rule, value, callback) => {

      if(!(/^[a-zA-Z][a-zA-Z0-9]{7,19}$/.test(value))){ //密码正则校验
        if(value===''){ //若密码为空
          return callback(new Error('请输入密码'));
        }else { //密码非空的报错信息
          return callback(new Error('密码8-20位,包含字母和数字,字母开头,不可以有特殊符号'));
        }
      }else{ //密码正确则无错误信息
        callback();
      }
    };

    return {
      adminForm: {
        adminUsername: '',
        adminPassword: '',
      },
      validCodes: '',
      validCode: '',
      rules: {
        adminUsername: [
          { validator: adminUserName, trigger: 'blur' }
        ],
        adminPassword: [
          { validator: adminPassWord, trigger: 'blur' }
        ],
      }
    }
  },
  mounted() {
    localStorage.clear()
    // 设置大小
    window.onresize = () => {
      const windowWidth = document.body.clientWidth
      const windowHeight = document.body.clientHeight
      const windowAspectRatio = windowHeight / windowWidth
      let videoWidth
      let videoHeight
      if (windowAspectRatio < 0.5625) {
        videoWidth = windowWidth
        videoHeight = videoWidth * 0.5625
        this.fixStyle = {
          height: windowWidth * 0.5625 + 'px',
          width: windowWidth + 'px',
          'margin-bottom': (windowHeight - videoHeight) / 2 + 'px',
          'margin-left': 'initial'
        }
      } else {
        videoHeight = windowHeight
        videoWidth = videoHeight / 0.5625
        this.fixStyle = {
          height: windowHeight + 'px',
          width: windowHeight / 0.5625 + 'px',
          'margin-left': (windowWidth - videoWidth) / 2 + 'px',
          'margin-bottom': 'initial'
        }
      }
    }
    window.onresize(undefined)
  },
  methods: {
    // 接收验证码组件提交的 4位验证码
    createValidCode(data) {
      this.validCode = data
    },

    login(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid){
          if (!this.validCodes){
            this.$message.error("请填写验证码")
            return
          }
          if(this.validCodes.toLowerCase() !== this.validCode.toLowerCase()) {
            this.$message.error("验证码错误")
            return
          }
          request.post("/admin/login", this.adminForm).then(res => {
            if (res.code === '0'){
              this.$message({
                type: res.code,
                message: res.msg
              })
              localStorage.setItem("admin", JSON.stringify(res.data))
              this.$router.push("/user")       //登录成功进行页面的跳转
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
  }
}
</script>

<style scoped>

</style>