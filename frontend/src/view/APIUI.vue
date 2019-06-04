<template>
  <div>
    <el-collapse v-model="activeName">
      <!--类-->
      <el-collapse-item name="1">
        <template slot="title">
          <div style="background-color: rgba(94,255,22,0.12); width: 100%; text-align: left">
            <el-col :span="3"><span style="margin-left: 10px">{{classNode.name}}:</span></el-col>
            <el-col :span="12">
              <el-input size="medium" style="width: 600px; margin-left: 10px" v-model="classNode.desc"></el-input>
            </el-col>
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
        <!--tag-->
        <el-row :gutter="20" type="flex" justify="start" style="margin-top: 20px" v-for="tag in classNode.tags">
          <el-col :span="4" :offset="1"><span
            style="font-size: 16px">{{classNode[tag].desc}}({{classNode[tag].name}}): </span>
          </el-col>
          <el-col :span="20">
            <el-table
              :data="[classNode[tag].attribute]"
              border
              style="width: 100%">
              <el-table-column
                v-for="(value,key) in classNode[tag].attribute"
                :label=key>
                <template slot-scope="scope">
                  <el-input v-model="scope.row[key]"></el-input>
                </template>
              </el-table-column>

            </el-table>
          </el-col>
        </el-row>
        <el-row>
          <el-button type="primary" size="medium" round style="margin-top: 30px" @click="addTag(classNode.name)">添加标签
          </el-button>
          <el-button type="danger" size="medium" round style="margin-top: 30px" @click="delTag(classNode.name)">删除标签
          </el-button>
        </el-row>
      </el-collapse-item>

      <!--方法-->
      <el-collapse-item v-for="methodNode in methodNodes" style="margin-top: 20px;">
        <!--标题-->
        <template slot="title">
          <div style="background-color: #F0F0EE; width: 100%; text-align: left">
            <el-col :span="3"><span style="margin-left: 10px">{{methodNode.name}}:</span>
            </el-col>
            <el-col :span="12">
              <el-input size="medium" style="width: 100%; margin-left: 10px" v-model="methodNode.desc"></el-input>
            </el-col>
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
            返回类型：{{methodNode.responseType.type}}
            <el-input size="medium" style="width: 400px; margin-left: 10px"
                      v-model="methodNode.responseType.desc"></el-input>
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
          <el-button type="primary" size="medium" round style="margin-top: 30px" @click="addTag(methodNode.name)">添加标签
          </el-button>
          <el-button type="danger" size="medium" round style="margin-top: 30px" @click="delTag(methodNode.name)">删除标签
          </el-button>
        </el-row>
      </el-collapse-item>
    </el-collapse>

    <!--to code-->
    <el-button type="primary" size="medium" style="margin-top: 30px" @click="handleToCode">To Code</el-button>

    <!--添加标签弹窗-->
    <el-dialog
      title="添加标签"
      :visible.sync="addTagDialogVisible"
      width="40%"
      :before-close="handleClose">
      <el-select v-model="newTag" style="width: 100%;">
        <el-option
          v-for="item in tagList"
          :key="item.name"
          :label="item.name"
          :value="item.name"></el-option>
      </el-select>
      <span slot="footer" class="dialog-footer">
    <el-button @click="addTagDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="handleAddTag">确 定</el-button>
    </span>
    </el-dialog>

    <!--删除标签弹窗-->
    <el-dialog
      title="删除标签"
      :visible.sync="delTagDialogVisible"
      width="40%"
      :before-close="handleClose">
      <el-select v-model="removeTag" style="width: 100%;">
        <el-option
          v-for="item in extraTagList"
          :key="item"
          :label="item"
          :value="item"></el-option>
      </el-select>
      <span slot="footer" class="dialog-footer">
    <el-button @click="delTagDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="handleDelTag">确 定</el-button>
    </span>
    </el-dialog>
  </div>

</template>

<script>
  import * as RestApi from '../api/RestApi'

  export default {
    name: "APIUI",
    data() {
      return {
        uid: -1,
        activeName: "1",
        addTagDialogVisible: false,
        delTagDialogVisible: false,
        newTag: '',
        removeTag: '',
        selectedNode: '',
        tagList: [],
        extraTagList: [],
        classNode: {},
        methodNodes: [],
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
    },
    methods: {
      initTagList: function() {
        let _this = this
        _this.tagList = []
        RestApi.getAllTag(_this.uid).then(function (response) {
          for (let bTag of  response.data.data) {
            let fTag = {
              name: bTag.name,
              desc: bTag.desc,
              attribute: JSON.parse(bTag.attribute),
              children: JSON.parse(bTag.children)
            }
            _this.tagList.push(fTag)
          }
        })
      },
      addTag: function (name) {
        this.addTagDialogVisible = true
        this.selectedNode = name
        this.initTagList()
      },
      delTag: function (name) {
        this.selectedNode = name,
          this.delTagDialogVisible = true
      },
      findNode: function (name) {
        if (name === this.classNode.name) {
          return this.classNode
        } else {
          for (let method of this.methodNodes) {
            if (name === method.name) {
              return method
            }
          }
        }
      },
      findTag: function (name, arr) {
        console.log(name,arr)
        for (let tag of arr) {
          console.log(tag)
          if (name === tag.name) {
            return tag
          }
        }
      },
      handleAddTag: function () {
        for (let tagName of this.extraTagList) {
          if (tagName === this.newTag) {
            this.$message.error('已添加该标签')
            return
          }
        }

        let node = this.findNode(this.selectedNode)
        this.$set(node, this.newTag, this.findTag(this.newTag, this.tagList));
        node.tags.push(this.newTag)
        this.addTagDialogVisible = false
        this.extraTagList = node.tags
      },
      handleDelTag: function () {
        let node = this.findNode(this.selectedNode)
        for (let i = 0; node.tags.length; i++) {
          if (node.tags[i] === this.removeTag) {
            this.$delete(node, this.removeTag)
            node.tags.splice(i, 1)
          }
        }
        this.delTagDialogVisible = false
      },
      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {
          });
      },
      backToFrontMethod: function (data1) {
        let methodNodes = []
        for (let method of data1) {
          let node = {
            name: method.name,
            desc: method.desc,
            methodSelector: [],
            server: '',
            path: '',
            parameters: [],
            responseType: {},
            responses: [],
            tags: []
          }
          for (let child of method.children) {
            switch (child.name) {
              case 'Parameters':
                for (let p of child.children) {
                  if (p.name === 'Parameter') {
                    let param = {
                      name: p.attribute.name,
                      type: p.attribute.type,
                      desc: p.attribute.desc
                    }
                    node.parameters.push(param)
                  }
                }
                break
              case 'Responses':
                node.responseType.type = child.attribute.type
                node.responseType.desc = child.attribute.desc
                for (let p of child.children) {
                  if (p.name === 'Response') {
                    let param = {
                      code: p.attribute.code,
                      content: p.attribute.content,
                    }
                    params.push(param)
                  }
                }
                break
              case 'Server':
                node.server = child.attribute.uri
                break
              case 'Path':
                node.path = child.attribute.path
                break
              case 'Method':
                node.methodSelector = child.attribute.method.split(',')
                break
              default:
                let n = {
                  name: child.name,
                  desc: child.desc,
                  attribute: child.attribute,
                  tags: []
                }
                this.$set(node, child.name, n);
                node.tags.push(child.name)
            }
          }
          methodNodes.push(node)
        }
        return methodNodes
      },
      backToFrontClass: function (data1) {
        let node = {
          name: data1.name,
          desc: data1.desc,
          server: '',
          path: '',
          tags: []
        }
        for (let child of data1.children) {
          switch (child.name) {
            case 'Server':
              node.server = child.attribute.uri
              break
            case 'Path':
              node.path = child.attribute.path
              break
            default:
              console.log(child.name)
              let n = {
                name: child.name,
                desc: child.desc,
                attribute: child.attribute,
                tags: []
              }
              this.$set(node, child.name, n);
              node.tags.push(child.name)
          }
        }
        return node
      },
      frontToBackMethod: function (data1) {
        let methodApiNode = []
        for (let method of data1) {
          let node = {
            name: method.name,
            desc: method.desc,
            attribute: {},
            children: []
          }

          for (let key in method) {
            switch (key) {
              case 'parameters':
                let params = {
                  name: 'Parameters',
                  desc: '参数',
                  attribute: {},
                  children: []
                }
                node.children.push(params)
                for (let p of method.parameters) {
                  let param = {
                    name: 'Parameter',
                    desc: '',
                    attribute: {
                      name: p.name,
                      type: p.type,
                      desc: p.desc
                    },
                    children: []
                  }
                  params.children.push(param)
                }
                break
              case 'responses':
                let responses = {
                  name: 'Responses',
                  desc: '响应',
                  attribute: {
                    type: method.responseType.type,
                    desc: method.responseType.desc
                  },
                  children: []
                }
                node.children.push(responses)
                for (let p of method.responses) {
                  let response = {
                    name: 'Response',
                    desc: '',
                    attribute: {
                      code: p.code,
                      content: p.content
                    },
                    children: []
                  }
                  responses.push(response)
                }
                break
              case 'server':
                let server = {
                  name: 'Server',
                  desc: '服务器',
                  attribute: {
                    uri: method.server
                  },
                  children: []
                }
                node.children.push(server)
                break
              case 'path':
                let path = {
                  name: 'Path',
                  desc: '路径',
                  attribute: {
                    path: method.path
                  },
                  children: []
                }
                node.children.push(path)
                break
              case 'methodSelector':
                let methods = {
                  name: 'Method',
                  desc: '方法',
                  attribute: {
                    method: method.methodSelector.join(',')
                  },
                  children: []
                }
                node.children.push(methods)
                break
              case 'tags':
              case 'name':
              case 'desc':
              case 'responseType':
                break
              default:
                let tagNode = this.findTag(key, this.tagList)
                console.log(tagNode)
                console.log(key)
                let tag = {
                  name: tagNode.name,
                  desc: tagNode.desc,
                  attribute: tagNode.attribute,
                  tags: []
                }
                node.children.push(tag)
            }
          }
          methodApiNode.push(node)
        }
        console.log(methodApiNode)
        return methodApiNode
      },
      frontToBackClass: function (data1) {
        let node = {
          name: data1.name,
          desc: data1.desc,
          attribute: {},
          children: []
        }

        for (let key in data1) {
          switch (key) {
            case 'server':
              let server = {
                name: 'Server',
                desc: '服务器',
                attribute: {
                  uri: data1.server
                },
                children: []
              }
              node.children.push(server)
              break
            case 'path':
              let path = {
                name: 'Path',
                desc: '路径',
                attribute: {
                  path: data1.path
                },
                children: []
              }
              node.children.push(path)
              break
            case 'tags':
            case 'name':
            case 'desc':
              break
            default:
              console.log(key)
              console.log(this.tagList)
              let tagNode = this.findTag(key, this.tagList)

              let tag = {
                name: tagNode.name,
                desc: tagNode.desc,
                attribute: tagNode.attribute,
                tags: []
              }
              node.children.push(tag)
          }
        }
        console.log(node)
        return node
      },
      handleToCode: function () {
        let _this = this
        _this.initTagList()
        let apiNode = {
            classNode: _this.frontToBackClass(this.classNode),
            methodNodes: _this.frontToBackMethod(this.methodNodes)
          }

        RestApi.toCode(sessionStorage.getItem("path"), apiNode).then(function (response) {
          if (response.data.code === 0) {
            _this.$message.info("To Code 成功")
          } else {
            _this.$message.error(response.data.msg)
          }
        })
      }
    },
    mounted() {
      let tmp_user = JSON.parse(sessionStorage.getItem("user"))
      if (tmp_user != null) {
        this.uid = tmp_user.uid
      }

      let apiData = JSON.parse(sessionStorage.getItem("api"))
      if (apiData !== null) {
        this.methodNodes = this.backToFrontMethod(apiData.methodNodes)
        this.classNode = this.backToFrontClass(apiData.classNode)
        console.log(this.methodNodes)
      }
    },
    watch: {
      selectedNode(val) {
        console.log(val)
        this.extraTagList = this.findNode(val).tags
      }
    }
  }
</script>

<style scoped>
</style>
