package com.scut.backend.service;

import com.scut.backend.model.Result;
import com.scut.backend.uits.FileUtil;
import com.scut.backend.uits.ResultUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileService {

    /**
     * 上传文件
     * @param file
     * @param filePath
     * @param fileName
     * @return
     */
    public Result uploadFile(byte[] file, String filePath, String fileName){
        int result = FileUtil.saveFile(file, filePath, fileName);
        if (result == -2) {
            return ResultUtil.returnBadResult("只能上传文件到目录下");
        }else if (result == -3) {
            return ResultUtil.returnBadResult("上传文件异常");
        }else {
            Map<String, String> data = new HashMap();
            data.put("value", filePath + fileName);
            data.put("name", fileName);
            return ResultUtil.returnGoodResult("上传成功", data);
        }
    }

    /**
     * 删除文件
     * @param path
     * @return
     */
    public Result deleteFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return ResultUtil.returnBadResult("文件不存在");
        }
         FileUtil.removeFile(path);
        return ResultUtil.returnGoodResult("删除成功");
    }

    /**
     * 下载文件
     * @param filePath
     * @param response
     * @return
     */
    public Result downloadFile(String filePath, HttpServletResponse response) {
        // 配置文件下载
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        // 下载文件能正常显示中文
        try {
            String fileName = filePath.substring(filePath.lastIndexOf('/') + 1);
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            int result = FileUtil.getFile(filePath, response.getOutputStream());
            if (result == -1) {
                return ResultUtil.returnBadResult("文件不存在");
            }else if (result == -2) {
                return ResultUtil.returnBadResult("下载异常");
            }
            return ResultUtil.returnGoodResult("下载成功");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultUtil.returnBadResult("下载异常");
    }

    /**
     * 获取文件
     */
    public Result getFile(String filePath) {
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        FileUtil.getFile(filePath, bs);
        return ResultUtil.returnGoodResult(bs.toString());
    }

    /**
     * 获取文件树
     * @param filePath
     * @return
     */
    public Result getFileTree(String filePath) {
        return ResultUtil.returnGoodResult(FileUtil.getFileTree(filePath));
    }
}
