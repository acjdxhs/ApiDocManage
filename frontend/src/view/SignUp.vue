<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
      <span>注册</span>
    </div>
    <div>
      <el-form :model="user" :rules="signUpRule" ref="user" label-width="100px">
        <el-form-item label="账号" prop="account">
          <el-input v-model="user.account" placeholder="账号"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="user.password" placeholder="密码" type="password"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirm">
          <el-input v-model="user.confirm" placeholder="确认密码" type="password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" round @click="cancel">取消</el-button>
          <el-button type="primary" round @click="signUp">确认</el-button>
        </el-form-item>
      </el-form>
    </div>
  </el-card>
</template>

<script>
  import * as ValidateRule from '../util/validate'
  import * as RestApi from '../api/RestApi'
  export default {
    name: "Login",
    data() {
      return {
        signUpRule: ValidateRule.signUpRule,
        user: {
          account: '',
          password: '',
          confirm: ''
        }
      }
    },
    methods: {
      cancel: function () {
        this.$router.push('/login')
      },
      signUp: function (){
        this.$refs.user.validate((valid) => {
          if (valid) {
            let _this = this
            if (_this.user.password != _this.user.confirm) {
              _this.$message.error("两次输入密码不一致")
              return
            }
            let params = {
              account: _this.user.account,
              password: _this.user.password,
            }
            RestApi.signUp(params).then(function (response) {
              if (response.data.code === 0) {
                _this.$message.info("注册成功")
                _this.$refs.user.resetFields()
              }else {
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

