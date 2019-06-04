package com.scut.backend.controller;

import com.scut.backend.model.Result;
import com.scut.backend.service.FileService;
import com.scut.backend.uits.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 文件管理
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("file")
public class FileController {

    private static final String BASE_PATH = "F:/graduate/code/ApiDocManage/backend/upload/";
    private static final String PATH_ROOT = "/root/";

    @Autowired
    private FileService fileService;

    /**
     * 下载文件
     *
     * @return
     */
    @RequestMapping("download")
    public Result downloadFile(@RequestParam String path, HttpServletResponse response) {
        return fileService.downloadFile(path, response);
    }

    /**
     * 上传文件
     *
     * @return
     */
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public Result uploadFile(@RequestParam MultipartFile file, @RequestParam String path) {
        if (file == null) {
            return ResultUtil.returnBadResult("上传的文件为空");
        }
        //String filePath = BASE_PATH + uid + PATH_ROOT + path;
        String fileName = file.getOriginalFilename();
        try {
            return fileService.uploadFile(file.getBytes(), path, fileName);
        } catch (IOException e) {
            return ResultUtil.returnBadResult("上传的文件类型错误");
        }
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Result deleteFile(@RequestParam("path") String path) {
        // todo 验证 uid
        return fileService.deleteFile(path);
    }

    /**
     * 获取单个文件
     *
     * @param path
     * @return
     */
    @RequestMapping("get")
    public Result getFile(@RequestParam String path) {
        return fileService.getFile(path);
    }

    /**
     * 获取文件树
     *
     * @param uid
     * @return
     */
    @RequestMapping("getTree")
    public Result getFileTree(@RequestParam Integer uid) {
        String filePath = BASE_PATH + uid + PATH_ROOT;
        return fileService.getFileTree(filePath);
    }

    @PostMapping("save")
    public Result saveFile(@RequestParam String file, @RequestParam String path) {
        int lastChar = path.lastIndexOf('\\');
        String fileName = path.substring(lastChar + 1);
        String parentPath = path.substring(0, lastChar + 1);

        return fileService.uploadFile(file.getBytes(), parentPath, fileName);
    }
}
