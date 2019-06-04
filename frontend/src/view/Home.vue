<template>
  <div>
    <el-row type="flex">
      <el-col :span="6" :offset="8"><h2>文档代码同源导出系统</h2></el-col>
      <el-col :span="2" :offset="6" style="margin-top: 30px"><span>{{user.account}}</span></el-col>
      <el-col :span="2">
        <el-button style="margin-top: 15px" type="primary" round @click="logout">注销</el-button>
      </el-col>
    </el-row>
    <el-container id="container">
      <!--左边栏-->
      <el-aside id="aside" width="200px" style="background-color: rgb(238, 241, 246)">
        <!--文件树-->
        <ul id="treeDemo" class="ztree" style="margin-top: 50px; margin-left: 10px; font-size: 30px"></ul>
        <div id="rMenu" v-if="rMenuShow" v-bind:style="rMenuStyle">
          <ul>
            <el-upload action="#"
                       :auto-upload="true"
                       :http-request="uploadFile"
                       :show-file-list="false"
                       style="display: none">
              <el-button id="btn_file"></el-button>
            </el-upload>
            <li id="m_add" @click="handleUpload">上传</li>
            <li id="m_del" @click="handleDownload">下载</li>
            <li id="m_check" @click="handleDelete">删除</li>
          </ul>
        </div>

      </el-aside>
      <el-main>
        <el-menu
          :default-active="activeIndex"
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b"
          mode="horizontal"
          @select="handleSelectForCodeApi">
          <el-menu-item index="1"><span slot="title">代码文件</span></el-menu-item>
          <el-menu-item index="2"><span slot="title">API UI</span></el-menu-item>
          <el-menu-item index="3"><span slot="title">标签管理</span></el-menu-item>
        </el-menu>
        <!--代码文件-->
        <template v-if="activeIndex=== '1'">
          <!--<CodeFile v-bind:content="editorContent"></CodeFile>-->
          <div id="editor">
            <VueUeditorWrap v-model="editorContent" :config="myConfig"></VueUeditorWrap>
            <el-button style="margin-top: 20px" type="primary" round @click="saveFile">保存</el-button>
            <el-button style="margin-top: 20px" type="primary" round @click="handleToApi">To API</el-button>
          </div>
        </template>
        <!--API UI-->
        <template v-if="activeIndex=== '2'">
          <APIUI></APIUI>
        </template>
        <!--标签管理-->
        <template v-if="activeIndex=== '3'">
          <TagManage></TagManage>
        </template>
      </el-main>
    </el-container>
  </div>
</template>

<script>
  import CodeFile from './CodeFile'
  import APIUI from './APIUI'
  import TagManage from './TagManage'
  import "../../static/ztree/js/jquery-1.4.4.min"
  import "../../static/ztree/js/jquery.ztree.core.min"
  import "../../static/ztree/js/jquery.ztree.excheck.min"
  import "../../static/ztree/js/jquery.ztree.exedit.min"
  import "../../static/ztree/css/zTreeStyle.css"
  import * as RestApi from '../api/RestApi'
  import * as downloadUtil from '../util/download'
  import VueUeditorWrap from 'vue-ueditor-wrap'
  // import '../../static/UEditor/third-party/SyntaxHighlighter/shCoreDefault.css'
  // import '../../static/UEditor/third-party/SyntaxHighlighter/shCore'

  export default {
    components: {
      CodeFile,
      APIUI,
      TagManage,
      VueUeditorWrap
    },
    name: "Home",
    data() {
      return {
        user: {
          uid: -1,
          account: '未登录'
        },
        editorContent: "",
        activeIndex: '1',
        rMenuShow: false,
        rMenuStyle: {
          position: 'absolute',
          top: '0px',
          left: '0px',
        },
        setting: {
          view: {
            dblClickExpand: false
          },
          callback: {
            onRightClick: this.onRightClick,
            onClick: this.getFile
          }
        },
        myConfig: {
          // 编辑器不自动被内容撑高
          autoHeightEnabled: false,
          // 初始容器高度
          initialFrameHeight: 350,
          // 初始容器宽度
          initialFrameWidth: '100%',
          // 上传文件接口（这个地址是我为了方便各位体验文件上传功能搭建的临时接口，请勿在生产环境使用！！！）
          serverUrl: 'http://35.201.165.105:8000/controller.php',
          // UEditor 资源文件的存放路径，如果你使用的是 vue-cli 生成的项目，通常不需要设置该选项，vue-ueditor-wrap 会自动处理常见的情况，如果需要特殊配置，参考下方的常见问题2
          UEDITOR_HOME_URL: '/static/UEditor/',
        },
        zNodes: [
          {
            name: "root",
            children: [],
            value: ''
          }
        ],
      }
    },
    methods: {
      handleSelectForCodeApi: function (index) {
        this.activeIndex = index;
      },
      onRightClick: function (enent, treeId, treeNode) {
        let zTree = $.fn.zTree.getZTreeObj("treeDemo")
        if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
          this.zTree.cancelSelectedNode();
          this.showRMenu(event, event.clientX, event.clientY);
        } else if (treeNode && !treeNode.noR) {
          zTree.selectNode(treeNode);
          this.showRMenu(event, event.clientX, event.clientY);
        }
      },
      showRMenu: function (event, x, y) {
        this.rMenuShow = true
        // y -= document.body.scrollTop;
        y -= event.offsetY
        x -= event.offsetX;
        this.rMenuStyle.top = y + "px"
        this.rMenuStyle.left = x + "px"
        $("body").bind("mousedown", this.onBodyMouseDown);
      },
      hideRmenu: function () {
        this.rMenuShow = false
        $("body").unbind("mousedown", this.onBodyMouseDown);
      },
      onBodyMouseDown(event) {
        if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
          this.rMenuShow = false
        }
      },
      handleUpload: function () {
        document.getElementById("btn_file").click()
      },
      uploadFile: function (param) {
        let _this = this
        _this.hideRmenu()
        let zTree = $.fn.zTree.getZTreeObj("treeDemo")
        let formData = new FormData()
        formData.append('file', param.file)
        let params = {
          uid: _this.user.uid,
          path: zTree.getSelectedNodes()[0].value
        }
        console.log(params)

        RestApi.uploadFile(param.file, params).then(function (response) {
          if (response.data.code === 0) {
            let newNode = {
              name: response.data.data.name,
              value: response.data.data.value
            }
            zTree.addNodes(zTree.getSelectedNodes()[0], newNode)
            _this.$message.info("上传成功")
          } else {
            _this.$message.error(response.data.msg)
          }
        })
      },
      handleDownload: function () {
        let _this = this
        _this.hideRmenu()
        let zTree = $.fn.zTree.getZTreeObj("treeDemo")
        let path = zTree.getSelectedNodes()[0].value
        RestApi.downloadFile(path).then(function (response) {
          downloadUtil.download(response.data, zTree.getSelectedNodes()[0].name)
          // if (response.status === 2000) {
          //   downloadUtil.download(response.data.data)
          //   _this.$message.info("下载成功")
          // }else {
          //   _this.$message.error(response.data.msg)
          // }
        })
      },
      handleDelete: function () {
        let _this = this
        _this.hideRmenu()
        let zTree = $.fn.zTree.getZTreeObj("treeDemo")
        let path = zTree.getSelectedNodes()[0].value
        console.log(path)
        RestApi.deleteFile(path).then(function (response) {
          if (response.data.code === 0) {
            zTree.removeNode(zTree.getSelectedNodes()[0])
            _this.$message.info("删除成功")
          }else {
            _this.$message.error(response.data.msg)
          }
        })
      },
      logout: function () {
        sessionStorage.clear()
        this.$router.push('/login')
      },
      getFileTree: function () {
        let _this = this
        RestApi.getFileTree(_this.user.uid).then(function (response) {
          console.log(response)
          if (response.data.code === 0) {
            _this.zNodes = [response.data.data]
          }
          $.fn.zTree.init($("#treeDemo"), _this.setting, _this.zNodes).expandAll(true)
        })
      },
      getFile: function () {
        let _this = this
        _this.activeIndex = '1'
        let zTree = $.fn.zTree.getZTreeObj("treeDemo")
        let path = zTree.getSelectedNodes()[0].value
        RestApi.getFile(path).then(function (response) {
          if (response.data.code === 0) {
            _this.editorContent = response.data.data.replace(/\r\n/g,"<br>")
            _this.editorContent = response.data.data.replace(/\n/g,"<br>")
          }
        })
      },
      saveFile: function () {
        let _this = this
        let zTree = $.fn.zTree.getZTreeObj("treeDemo")
        let path = zTree.getSelectedNodes()[0].value
        let file = _this.editorContent.replace(/<br\/>/g, "\r\n")
        let file1 = file.replace(/&nbsp;/g, ' ')
        let file2 = file1.replace('<p>', '').replace(/&quot;/g, '"')
        file2 = file2.substring(0, file2.lastIndexOf('</p>'))
        RestApi.saveFile(file2, path).then(function (response) {
          if (response.data.code === 0) {
            console.log(response)
          }
        })
      },
      handleToApi: function () {
        let _this = this
        let zTree = $.fn.zTree.getZTreeObj("treeDemo")
        let path = zTree.getSelectedNodes()[0].value
        _this.saveFile()
        console.log(path)
        RestApi.toApi(path).then(function (response) {
          console.log(response)
          if (response.data.code === 0) {
            _this.activeIndex = '2'
            sessionStorage.setItem("api", JSON.stringify(response.data.data))
            sessionStorage.setItem("path", path)
            _this.$message.info(response.data.msg)
          }else{
            _this.$message.error(response.data.msg)
          }
        })
      }
    },
    mounted() {
      let tmp_user = JSON.parse(sessionStorage.getItem("user"))
      if (tmp_user != null) {
        this.user = tmp_user
      }
      // 获取文件树
      this.getFileTree()

    }
  }
</script>

<style scoped>

  /*@import "../../static/ztree/css/zTreeStyle.css";*/

  #nav_button {
    margin-right: 10px;
  }

  #container {
    height: 610px;
    border: 1px solid #eee;
  }

  .el-tree {
    margin-top: 30px;
    margin-left: 20px;
    background: none;
    font-size: 8px;
  }

  .el-tree-node__label {
    font-size: 16px;
  }

  div#rMenu ul li {
    margin: 1px 0;
    padding: 0 5px;
    cursor: pointer;
    list-style: none outside none;
    background-color: #DFDFDF;
    width: 80px;
    height: 25px;
  }


</style>
