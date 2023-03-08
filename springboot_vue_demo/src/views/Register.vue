<template>
  <nav style="width: 100%; height: 100vh; background-color: #ffffff; overflow: hidden; ">
    <div style="width: 400px; margin: 150px auto;">
      <div style="color: black; font-size: 30px; text-align: center; padding: 15px 0;">
        欢迎注册
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
          <el-input  v-model="adminForm.adminUsername" />
        </el-form-item>
        <el-form-item label="密码" prop="adminPassword">
          <el-input  v-model="adminForm.adminPassword" show-password autocomplete="off" />
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPassword">
          <el-input v-model="adminForm.checkPassword" show-password autocomplete="off"></el-input>
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
          <div style="display: flex">
            <el-input v-model="adminForm.adminEmail"/>

            <el-button
                @click="getEmailCode(adminForm.adminEmail)"
                :disabled="canClick"
            >
            {{content}}</el-button>
          </div>
        </el-form-item>

        <el-form-item label="验证码" prop="checkEmail">
          <el-input  v-model="checkEmail" />

        </el-form-item>
        <el-form-item style="width: 100%">
          <el-button type="primary" @click="register('AddadminFormRef')"
          >确定注册</el-button
          >
          <el-button @click="resetForm('AddadminFormRef')">重置</el-button>
          <el-button type="danger" @click="$router.push('/login')">退出注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </nav>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Register",

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
      }else{ //密码正确则无错误信息
        callback();
      }
    };
    var AdminPassWord2 = (rule, value, callback) => {
      if (value === '') {
        return callback(new Error('请再次输入密码'));
      } else if (value !== this.adminForm.adminPassword) {
        return callback(new Error('两次输入密码不一致!'));
      } else {
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
        checkPassword: '',
        adminPhone: '',
        adminEmail: '',
      },
      content: '发送邮箱验证码', // 按钮里显示的内容
      totalTime: 60,   //记录具体倒计时时间
      // canClick: true,  //添加canClick  添加了计算属性，改值无效
      checkEmail: '',
      rules: {
        adminUsername: [
          { validator: AdminUserName, trigger: 'blur' }
        ],
        adminPassword: [
          { validator: AdminPassWord, trigger: 'blur' }
        ],
        checkPassword: [
          { validator: AdminPassWord2, trigger: 'blur' }
        ],
        adminPhone: [
          { validator: AdminPhone, trigger: 'blur' }
        ],
      }
    }
  },
  computed: {
    canClick: {
      get(){
        if(this.totalTime == 60){
          if (!this.adminForm.adminEmail == ''){
            return false
          }
          return true
        }
        return true
      },
      set(){
      }
    }
  },
  methods: {
    register(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid){
          request.post("/admin/register/"+ this.checkEmail, this.adminForm ).then(res => {
            if (res.code === '0'){
              this.$message({
                type: res.code,
                message: res.msg
              })
              this.$router.push("/login")       //登录成功进行页面的跳转
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
    },
    getEmailCode(email){
      if (this.canClick) return  //改动的是这两行代码
      request.get("/email/sendphoneCode", {
        params: {
          email: email
        }
      }).then(res => {
        this.$message({
          type: res.code,
          message: res.msg
        })
      });

      // 验证码倒计时
      this.canClick = true
      this.content = this.totalTime + 's后重新发送' //这里解决60秒不见了的问题
      let clock = window.setInterval(() => {
        this.totalTime--
        this.content = this.totalTime + 's后重新发送'
        if (this.totalTime < 0) {     //当倒计时小于0时清除定时器
          window.clearInterval(clock)
          this.content = '重新发送验证码'
          this.totalTime = 60
          this.canClick = false  //这里重新开启
        }
      },1000);
    },
  }
}
</script>

<style scoped>

</style>