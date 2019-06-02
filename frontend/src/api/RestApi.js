import axios from 'axios'
let baseUrl = 'http://localhost:8080'

// 登录
export const login = params => {
  return axios({
    method: 'post',
    url: baseUrl + "/user/login",
    data: params
  })
}

// 注册
export const signUp = params => {
  return axios({
    method: 'post',
    url: baseUrl + "/user/signUp",
    data: params
  })
}

// 获取文件树
export const getFileTree = params => {
  console.log(sessionStorage.getItem("token"))
  return axios({
    method: 'get',
    url: baseUrl + "/file/getTree?uid=" + params,
    headers: {
      "Access-Token": sessionStorage.getItem('token')
    }
  })
}

// 上传文件
export const uploadFile = (file, params) => {
  let form = new FormData();
  form.append("file", file);
  form.append("uid", params.uid);
  form.append("path", params.path);
  return axios({
      method: 'post',
      url: baseUrl + "/file/upload",
      data: form,
      headers: {
        "Content-Type": "multipart/form-data",
        "Access-Token": sessionStorage.getItem('token')
      }
    })
}

// 删除文件
export const deleteFile = (path) => {
  let form = new FormData()
  form.append("path", path)
  return axios({
    method: 'post',
    url: baseUrl + "/file/delete",
    data: form,
    headers: {
      "Content-Type": "multipart/form-data",
      "Access-Token": sessionStorage.getItem('token')
    }
  })
}

// 下载文件
export const downloadFile = (path) => {
  let form = new FormData()
  form.append("path", path)
  return axios({
    method: 'post',
    url: baseUrl + "/file/download",
    data: form,
    headers: {
      "Content-Type": "multipart/form-data",
      "Access-Token": sessionStorage.getItem('token')
    },
  })
}

// 获取文件内容
export const getFile = (path) => {
  let form = new FormData()
  form.append("path", path)
  return axios({
    method: 'post',
    url: baseUrl + "/file/get",
    data: form,
    headers: {
      "Content-Type": "multipart/form-data",
      "Access-Token": sessionStorage.getItem('token')
    },
  })
}

// 保存文件内容
export const saveFile = (file, path) => {
  let form = new FormData()
  form.append("file", file)
  form.append("path", path)
  return axios({
    method: 'post',
    url: baseUrl + "/file/save",
    data: form,
    headers: {
      "Content-Type": "multipart/form-data",
      "Access-Token": sessionStorage.getItem('token')
    },
  })
}

// 获取标签库
export const getAllTag = (uid) => {
  return axios({
    method: 'get',
    url: baseUrl + '/tag/get?uid=' + uid,
    headers: {
      "Access-Token": sessionStorage.getItem('token')
    }
  })
}

// 删除标签
export const deleteTag = (uid, name) => {
  return axios({
    method: 'get',
    url: baseUrl + '/tag/delete?uid=' + uid + '&name=' + name,
    headers: {
      "Access-Token": sessionStorage.getItem('token')
    }
  })
}

// 更新标签
export const updateTag = (params) => {
  return axios({
    method: 'post',
    url: baseUrl + '/tag/update',
    data: params,
    headers: {
      "Access-Token": sessionStorage.getItem('token')
    }
  })
}

// 增加标签
export const addTag = (params) => {
  return axios({
    method: 'post',
    url: baseUrl + '/tag/add',
    data: params,
    headers: {
      "Access-Token": sessionStorage.getItem('token')
    }
  })
}

// to Api
export const toApi = (path) => {
  let form = new FormData()
  form.append("path", path)
  return axios({
    method: 'post',
    url: baseUrl + '/core/toApi',
    data: form,
    headers: {
      "Content-Type": "multipart/form-data",
      "Access-Token": sessionStorage.getItem('token')
    }
  })
}
