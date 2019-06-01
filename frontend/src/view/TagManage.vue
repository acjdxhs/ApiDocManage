<template>
  <div>
    <el-table :data="tagList" border style="width: 100%">
      <el-table-column prop="name"
                       label="标签名称"
                       width="180px"
      >
      </el-table-column>
      <el-table-column prop="description"
                       label="标签说明"
                       width="180px"
      >
      </el-table-column>
      <el-table-column label="标签属性"
      >
        <template slot-scope="scope">
          <li v-for="(key, value) in scope.row.attribute">
            {{value}} : {{key}}
          </li>
        </template>
      </el-table-column>
      <el-table-column prop="children"
                       label="子标签"
      >
      </el-table-column>
      <el-table-column label="操作"
                       width="180px"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)">编辑
          </el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-button style="margin-top: 30px" type="primary" round @click="openDialog">新增标签
    </el-button>

    <!--新增标签弹窗-->
    <el-dialog
      :title="dialogTitle"
      :visible.sync="dialogVisible"
      width="40%"
      :before-close="handleClose">
      <el-form label-width="100px" :model="newTag">
        <el-form-item label="标签名称">
          <el-input size="medium" v-model="newTag.name"></el-input>
        </el-form-item>
        <el-form-item label="标签描述">
          <el-input size="medium" v-model="newTag.description"></el-input>
        </el-form-item>
        <el-form-item label="标签属性">
          <li v-for="(key, value) in newTag.attribute">
            {{value}} : {{key}}
            <el-button
              style="float: right; margin-right: 40px"
              size="mini"
              type="danger"
              @click="handleDeleteAttribute(value, key)">删除
            </el-button>
          </li>
          属性名称:
          <el-input size="mini" v-model="attrEntry.key" style="width: 100px"></el-input>
          属性描述:
          <el-input size="mini" v-model="attrEntry.value" style="width: 100px"></el-input>
          <el-button type="primary" size="mini" round @click="handleAddAttrebute">添加</el-button>
        </el-form-item>
        <el-form-item size="medium" label="子标签">
          <el-select multiple v-model="newTag.children" style="width: 100%;">
            <el-option
              v-for="item in tagList"
              :key="item.name"
              :label="item.name"
              :value="item.name"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="handleOk">确 定</el-button>
    </span>
    </el-dialog>
  </div>
</template>

<script>
  import * as RestApi from '../api/RestApi'

  export default {
    name: "TagManage",
    data() {
      return {
        uid: -1,
        tagList: [],
        dialogVisible: false,
        dialogTitle: "",
        newTag: {
          name: '',
          description: '',
          attribute: {},
          children: []
        },
        attrEntry: {
          key: '',
          value: ''
        }
      }
    },
    methods: {
      openDialog: function() {
        let _this = this
        _this.dialogVisible = true
        _this.dialogTitle = '新增标签'
        _this.newTag.name = ''
        _this.newTag.description = ''
        _this.newTag.attribute = {}
        _this.newTag.children = []
        _this.attrEntry = {}
      },
      handleEdit(index, row) {
        let _this = this
        _this.dialogVisible = true
        _this.dialogTitle = '编辑标签'
        _this.newTag.name = row.name
        _this.newTag.description = row.description
        _this.newTag.attribute = row.attribute
        _this.newTag.children = row.children
      },
      handleDelete(index, row) {
        this.$confirm('确认删除？')
          .then(_ => {
            this.dialogVisible = false
            let _this = this
            RestApi.deleteTag(_this.uid, row.name).then(function (response) {
              if (response.data.code === 0) {
                _this.tagList.splice(index, 1)
                _this.$message.info('删除成功')
              } else {
                _this.$message.error(response.data.msg)
              }
            })
          })
      },
      handleOk() {
        if (this.dialogTitle === '新增标签') {
          this.handleAddTag()
        } else if (this.dialogTitle === '编辑标签') {
          this.handleUpdateTag()
        }else{
          return
        }
      },
      handleAddTag: function () {
        this.dialogVisible = false
        let _this = this
        let params = {
          uid: _this.uid,
          name: _this.newTag.name,
          description: _this.newTag.description,
          attribute: JSON.stringify(_this.newTag.attribute),
          children: JSON.stringify(_this.newTag.children)
        }
        RestApi.addTag(params).then(function (response) {
          if (response.data.code === 0) {
            let tag = {
              uid: _this.uid,
              name: _this.newTag.name,
              description: _this.newTag.description,
              attribute: _this.newTag.attribute,
              children: _this.newTag.children
            }
            _this.tagList.push(tag)
            _this.$message.info('增加成功')
          } else {
            _this.$message.error(response.data.msg)
          }
        })
      },
      handleUpdateTag: function () {
        this.dialogVisible = false
        let _this = this
        let params = {
          uid: _this.uid,
          name: _this.newTag.name,
          description: _this.newTag.description,
          attribute: JSON.stringify(_this.newTag.attribute),
          children: JSON.stringify(_this.newTag.children)
        }
        RestApi.updateTag(params).then(function (response) {
          if (response.data.code === 0) {
            let tag = {
              uid: _this.uid,
              name: _this.newTag.name,
              description: _this.newTag.description,
              attribute: _this.newTag.attribute,
              children: _this.newTag.children
            }
            _this.tagList.push(tag)
            _this.$message.info('修改成功')
          } else {
            _this.$message.error(response.data.msg)
          }
        })
      },
      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {
          });
      },
      handleAddAttrebute() {
        this.newTag.attribute[this.attrEntry.key] = this.attrEntry.value
        this.attrEntry.key = ''
        this.attrEntry.value = ''
      },
      handleDeleteAttribute(value, key) {
        console.log(value, key)
        let _this = this
        console.log(_this.newTag.attribute[value])
        delete _this.newTag.attribute[value]
        _this.$forceUpdate();
      },
      getAllTag() {
        let _this = this
        RestApi.getAllTag(_this.uid).then(function (response) {
          console.log(response)
          if (response.data.code === 0) {
            for (let bTag of  response.data.data) {
              console.log(bTag)
              let fTag = {
                name: bTag.name,
                description: bTag.description,
                attribute: JSON.parse(bTag.attribute),
                children: JSON.parse(bTag.children)
              }
              _this.tagList.push(fTag)
            }
          }
        })
      }
    },
    mounted() {
      let tmp_user = JSON.parse(sessionStorage.getItem("user"))
      if (tmp_user != null) {
        this.uid = tmp_user.uid
      }

      this.getAllTag()
    }
  }
</script>

<style scoped>

</style>
