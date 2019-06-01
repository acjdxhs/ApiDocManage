<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
      <span>文档代码同源导出系统</span>
    </div>
    <div>
      <el-form :model="user" :rules="loginRule" ref = "user" label-width="100px">
        <el-form-item label="账号"  prop="account">
          <el-input v-model="user.account" placeholder="账号"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="user.password" placeholder="密码" type="password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" round @click="signUp">注册</el-button>
          <el-button type="primary" round @click="login">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </el-card>
</template>

<script>
  import * as RestApi from '../api/RestApi'
  import * as ValidateRule from '../util/validate';
  export default {
    name: "Login",
    data() {
      return {
        loginRule: ValidateRule.loginRule,
        user: {
          account: '',
          password: ''
        }
      }
    },
    methods: {
      signUp: function () {
        this.$router.push('/signUp')
      },
      login: function () {
        this.$refs.user.validate((valid) =>{
          if (valid) {
            //清楚以前的缓存
            sessionStorage.clear();
            let _this = this
            RestApi.login(_this.user).then(function (response) {
              if (response.data.code === 0) {
                _this.$message.info("登录成功")
                sessionStorage.setItem('user', JSON.stringify(response.data.data))
                sessionStorage.setItem('token', response.data.data.token)
                console.log(sessionStorage.getItem('token'))
                _this.$router.push('/home')
              } else {
                _this.$message.error(response.data.msg)
              }
            })
          }
        })


      }
    }
  }
</script>

<style>
  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }

  .clearfix:after {
    clear: both
  }

  .box-card {
    margin-top: 100px;
    margin-left: 500px;
    width: 400px;
  }
</style>

