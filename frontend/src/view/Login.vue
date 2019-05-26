<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
      <span>文档代码同源导出系统</span>
    </div>
    <div>
      <el-form :model="user" label-width="100px">
        <el-form-item label="账号" required
                      :rules="[
                      {required: true, message: '账号不能为空', trigger: 'blur'}
                      ]">
          <el-input v-model="user.account" placeholder="账号"></el-input>
        </el-form-item>
        <el-form-item label="密码"
                      :rules="[
                      {required: true, message: '密码不能为空', trigger: 'blur'}
                      ]">
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
  export default {
    name: "Login",
    data() {
      return {
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
        this.$router.push('/home')
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

