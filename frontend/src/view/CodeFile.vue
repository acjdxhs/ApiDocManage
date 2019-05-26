<template>
  <div>
  <VueUeditorWrap v-model="msg" :config="myConfig"></VueUeditorWrap>
    <el-button style="margin-top: 20px" type="primary" round>To API</el-button>
  </div>

</template>

<script>
  import VueUeditorWrap from 'vue-ueditor-wrap'

  export default {
    name: "CodeFile",
    components: {
      VueUeditorWrap
    },
    data() {
      return {
        msg: '',
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
          UEDITOR_HOME_URL: '/static/UEditor/'
        }
      }
    }
  }
</script>

<style scoped>

</style>
