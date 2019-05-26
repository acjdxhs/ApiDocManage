<template>
  <div>
    <el-collapse v-model="activeName">
      <!--类-->
      <el-collapse-item name="1">
        <template slot="title">
          <div style="background-color: #00B83F; width: 100%; text-align: left">
            <span style="margin-left: 10px">{{classNode.name}}:</span>
            <el-input size="medium" style="width: 600px; margin-left: 10px" v-model="classNode.desc"></el-input>
          </div>
        </template>
        <el-row :gutter="20" type="flex" style="margin-top: 20px">
          <el-col :span="4" :offset="1"><span style="font-size: 16px">服务器(Server):</span></el-col>
          <el-col :span="6">
            <el-input size="medium" v-model="classNode.server"></el-input>
          </el-col>
        </el-row>
        <el-row :gutter="20" type="flex" justify="start" style="margin-top: 20px">
          <el-col :span="4" :offset="1"><span style="font-size: 16px">路径(path):</span></el-col>
          <el-col :span="6">
            <el-input size="medium" v-model="classNode.path"></el-input>
          </el-col>
        </el-row>
        <el-row>
          <el-button type="primary" size="medium" round style="margin-top: 30px">添加标签</el-button>
        </el-row>
      </el-collapse-item>

      <!--方法-->
      <el-collapse-item v-for="methodNode in methodNodes" style="margin-top: 20px;">
        <!--标题-->
        <template slot="title">
          <div style="background-color: #F0F0EE; width: 100%; text-align: left">
            <span style="margin-left: 10px">{{methodNode.name}}:</span>
            <el-input size="medium" style="width: 400px; margin-left: 10px" v-model="methodNode.desc"></el-input>
            <el-select size="medium" multiple collapse-tags style="width: 180px; float: right"
                       v-model="methodNode.methodSelector">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"></el-option>
            </el-select>
          </div>
        </template>
        <!--服务器-->
        <el-row :gutter="20" type="flex" style="margin-top: 20px">
          <el-col :span="4" :offset="1"><span style="font-size: 16px">服务器(Server):</span></el-col>
          <el-col :span="6">
            <el-input size="medium" v-model="methodNode.server"></el-input>
          </el-col>
        </el-row>
        <!--路径-->
        <el-row :gutter="20" type="flex" justify="start" style="margin-top: 20px">
          <el-col :span="4" :offset="1"><span style="font-size: 16px">路径(path):</span></el-col>
          <el-col :span="6">
            <el-input size="medium" v-model="methodNode.path"></el-input>
          </el-col>
          <el-col :span="4"><span>{{classNode.path}}/{{methodNode.path}}</span></el-col>
        </el-row>
        <!--参数-->
        <el-row :gutter="20" type="flex" justify="start" style="margin-top: 20px">
          <el-col :span="4" :offset="1"><span style="font-size: 16px">参数(Parameters):</span></el-col>
          <el-col :span="20">
            <el-table
              :data="methodNode.parameters"
              border
              style="width: 100%">
              <el-table-column
                prop="type"
                label="类型"
                width="180">
              </el-table-column>
              <el-table-column
                prop="name"
                label="参数名"
                width="180">
              </el-table-column>
              <el-table-column
                label="说明">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.desc"></el-input>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
        </el-row>
        <!--响应-->
        <el-row :gutter="20" type="flex" justify="start" style="margin-top: 20px">
          <el-col :span="4" :offset="1"><span style="font-size: 16px">响应(Responses): </span></el-col>
          <el-col :span="20">
            返回类型：String
            <el-table
              :data="methodNode.responses"
              border
              style="width: 100%">
              <el-table-column
                label="响应码"
                width="180">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.code"></el-input>
                </template>
              </el-table-column>
              <el-table-column
                label="返回内容">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.content"></el-input>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="180">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    @click="handleEdit(scope.$index, scope.row)">保存
                  </el-button>
                  <el-button
                    size="mini"
                    type="danger"
                    @click="handleDelete(scope.$index, scope.row)">删除
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
            <el-button type="primary" size="mini" round @click="">添加状态码</el-button>
          </el-col>
        </el-row>
        <!--tag-->
        <el-row :gutter="20" type="flex" justify="start" style="margin-top: 20px" v-for="tag in methodNode.tags">
          <el-col :span="4" :offset="1"><span style="font-size: 16px">{{methodNode[tag].desc}}({{methodNode[tag].name}}): </span>
          </el-col>
          <el-col :span="20">
            <el-table
              :data="[methodNode[tag].attribute]"
              border
              style="width: 100%">
              <el-table-column
                v-for="(value,key) in methodNode[tag].attribute"
                :label=key>
                <template slot-scope="scope">
                  <el-input v-model="scope.row[key]"></el-input>
                </template>
              </el-table-column>

            </el-table>
          </el-col>
        </el-row>
        <el-row>
          <el-button type="primary" size="medium" round style="margin-top: 30px">添加标签</el-button>
          <el-button type="danger" size="medium" round style="margin-top: 30px">删除标签</el-button>
        </el-row>
      </el-collapse-item>
    </el-collapse>

    <!--to code-->
    <el-button type="primary" size="medium" style="margin-top: 30px">To Code</el-button>
  </div>

</template>

<script>
  export default {
    name: "APIUI",
    data() {
      return {
        activeName: "1",
        classNode: {
          name: 'UserController',
          desc: '用户管理控制器',
          server: '',
          path: 'user',
          tags: []
        },
        methodNodes: [{
          name: 'login',
          desc: '登录',
          methodSelector: [],
          server: '',
          path: 'login',
          parameters: [{
            name: 'user',
            type: 'User',
            desc: '用户，包括用户名和密码'
          }],
          responseType: '',
          responses: [{
            code: 200,
            content: 'ok'
          }, {
            code: 403,
            content: 'forbidden'
          }],
          Author: {
            desc: '作者',
            name: 'Author',
            attribute: {
              name: '张三',
              time: '2019-05-20'
            },
            tags: []
          },
          tags: ['Author']
        }, {
          name: 'signUp',
          desc: '注册',
          methodSelector: [],
          server: '',
          path: 'login',
          parameters: [{
            name: 'account',
            type: 'String',
            desc: '账户'
          }],
          responseType: '',
          responses: [{
            code: 200,
            content: 'ok'
          }],
          tag: []
        }],
        options: [{
          value: '选项1',
          label: 'GET'
        }, {
          value: '选项2',
          label: 'POST'
        }, {
          value: '选项3',
          label: 'PUT'
        }, {
          value: '选项4',
          label: 'PATCH'
        }, {
          value: '选项5',
          label: 'DELETE'
        }],
      }
    }
  }
</script>

<style scoped>

</style>
