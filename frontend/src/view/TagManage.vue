<template>
  <div>
    <el-table :data="tagList" border style="width: 100%">
      <el-table-column prop="name"
                       label="标签名称"
                       width="180px"
      >
      </el-table-column>
      <el-table-column prop="desc"
                       label="标签说明"
                       width="180px"
      >
      </el-table-column>
      <el-table-column label="标签属性"
      >
        <template slot-scope="scope">
          <li v-for="(key, value) in scope.row.attributes">
            {{value}} : {{key}}
          </li>
        </template>
      </el-table-column>
      <el-table-column prop="subTag"
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
    <el-button style="margin-top: 30px" type="primary" round @click="dialogVisible = true">新增标签</el-button>

    <el-dialog
      title="新增标签"
      :visible.sync="dialogVisible"
      width="40%"
      :before-close="handleClose">
      <el-form label-width="100px" :model="newTag">
        <el-form-item label="标签名称">
          <el-input size="medium" v-model="newTag.name"></el-input>
        </el-form-item>
        <el-form-item label="标签属性">
          <li v-for="(key, value) in newTag.attributes">
            {{value}} : {{key}}
            <el-button
              style="float: right; margin-right: 40px"
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)">删除
            </el-button>
          </li>
          属性名称: <el-input size="mini" v-model="attrEntry.key" style="width: 100px"></el-input>
          属性描述: <el-input size="mini" v-model="attrEntry.value" style="width: 100px"></el-input>
          <el-button type="primary" size="mini" round @click="handleAddAttrebute">添加</el-button>
        </el-form-item>
        <el-form-item size="medium" label="子标签">
          <el-select multiple v-model="newTag.subTag" style="width: 100%;">
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
    <el-button type="primary" @click="handleAddTag">确 定</el-button>
    </span>>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "TagManage",
    data() {
      return {
        tagList: [
          {
            name: 'Author',
            desc: '作者',
            attributes: {
              name: '作者名称',
              time: '创建时间 如2019-02-22,'
            },
            subTag: []
          },
          {
            name: 'Secret',
            desc: '安全',
            attributes: {
              method: '加密方式，如 MD5'
            },
            subTag: []
          }
        ],
        dialogVisible: false,
        newTag: {
          name: '',
          attributes: {
            a: 'aa'
          },
          subTag: []
        },
        attrEntry: {
          key: '',
          value: ''
        }
      }
    },
    methods: {
      handleEdit(index, row) {
        console.log(index, row);
      },
      handleDelete(index, row) {
        console.log(index, row);
      },
      handleAddTag: function () {
        this.dialogVisible = false
      },
      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      },
      handleAddAttrebute() {
        this.newTag.attributes[this.attrEntry.key] = this.attrEntry.value
        this.attrEntry.key=''
        this.attrEntry.value=''
      }
    }
  }
</script>

<style scoped>

</style>
