<template>
  <div>
    <el-row type="flex">
      <el-col :span="6" :offset="8"><h2>文档代码同源导出系统</h2></el-col>
      <el-col :span="2" :offset="6" style="margin-top: 30px"><span>username</span></el-col>
      <el-col :span="2">
        <el-button style="margin-top: 15px" type="primary" round>注销</el-button>
      </el-col>
    </el-row>
    <el-container id="container">
      <!--左边栏-->
      <el-aside id="aside" width="200px" style="background-color: rgb(238, 241, 246)">
        <!--文件树-->
        <ul id="treeDemo" class="ztree" style="margin-top: 50px; margin-left: 10px; font-size: 30px"></ul>
        <div id="rMenu" v-if="rMenuShow" v-bind:style="rMenuStyle">
          <ul>
            <li id="m_add" onclick="handleUpload();">上传</li>
            <li id="m_del" onclick="handleDownload();">下载</li>
            <li id="m_check" onclick="handleDelete();">删除</li>
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
          <CodeFile></CodeFile>
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

  export default {
    components: {
      CodeFile,
      APIUI,
      TagManage
    },
    name: "Home",
    data() {
      return {
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
          check: {
            enable: true
          },
          callback: {
            onRightClick: this.onRightClick
          }
        },
        zNodes: [
          {
            id: 1, name: "root",
            children: [
              {id: 11, name: "UserController.java"},
              // {id: 12, name: "节点 1-2"}
            ]
          }
        ]
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
      hideRmenu: function() {
        this.rMenuShow = false
        $("body").unbind("mousedown", this.onBodyMouseDown);
      },
      onBodyMouseDown(event) {
        if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
          this.rMenuShow = false
        }
      },
      handleUpload: function () {

      },
      handleDownload: function () {

      },
      handleDelete: function () {

      }
    },
    mounted() {
      $.fn.zTree.init($("#treeDemo"), this.setting, this.zNodes).expandAll(true)
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
