package com.scut.backend.uits;

import com.scut.backend.model.FileTree;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;

public class FileUtil {

    /**
     * 保存文件
     *
     * @param file
     * @param filePath
     * @param fileName
     * @return
     */
    public static int saveFile(byte[] file, String filePath, String fileName) {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdir();
        }
        if (!targetFile.isDirectory()) {
            // 不是目录
            return -2;
        }
        try (FileOutputStream out = new FileOutputStream(new File(filePath, fileName))) {
            out.write(file);
            out.flush();
            return 0;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 异常
        return -3;
    }

    /**
     * 根据路径删除文件
     *
     * @param filePath
     */
    public static void removeFile(String filePath) {
        removeFile(new File(filePath));
    }

    /**
     * 递归删除目录
     *
     * @param targetFile
     */
    public static void removeFile(File targetFile) {
        if (!targetFile.exists()) {
            return;
        }
        if (targetFile.isFile()) {
            targetFile.delete();
            return;
        }
        if (targetFile.isDirectory()) {
            File[] subFileList = targetFile.listFiles();
            for (File file : subFileList) {
                removeFile(file);
            }
        }
        targetFile.delete();
    }

    /**
     * 获取文件
     * @param filePath
     * @param os
     * @return
     */
    public static int getFile(String filePath, OutputStream os) {
        // 获取指定目录下的第一个文件
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            // 文件不存在
            return -1;
        }

        // 实现文件下载
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(targetFile);
            bis = new BufferedInputStream(fis);
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            return 0;
        } catch (Exception e) {
            // 异常
            return -2;
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取文件树
     * @param filePath
     * @return
     */
    public static FileTree getFileTree(String filePath) {
        File targetFile = new File(filePath);
        FileTree fileTree = new FileTree();
        fileTree.setChildren(new ArrayList<>());
        getFileTreeHelper(targetFile, fileTree);
        return fileTree.getChildren().size() < 1 ? null : fileTree.getChildren().get(0);
    }

    /**
     * 获取文件树递归操作
     * @param curFile
     * @param tree
     */
    public static void getFileTreeHelper(File curFile, FileTree tree) {
        if (!curFile.exists()) {
            return;
        }
        FileTree node = new FileTree();
        node.setName(curFile.getName());
        tree.getChildren().add(node);
        if (curFile.isFile()) {
            node.setValue(curFile.getAbsolutePath());
            return;
        }else {
            node.setValue(curFile.getAbsolutePath() + '\\');
            node.setChildren(new ArrayList<>());
            File[] subFileList = curFile.listFiles();
            subFileList = sort(subFileList);
            for (File file: subFileList) {
                getFileTreeHelper(file, node);
            }
        }
    }

    /**
     * 排序文件，让目录在上方
     * @param file
     * @return
     */
    private static File[] sort(File[] file)
    {
        ArrayList<File> list = new ArrayList<File>();
        //放入所有目录
        for (File f : file)
        {
            if (f.isDirectory())
            {
                list.add(f);
            }
        }
        //放入所有文件
        for (File f : file)
        {
            if (f.isFile())
            {
                list.add(f);
            }
        }

        return list.toArray(new File[file.length]);
    }

    public static void main(String[] args) {
        FileTree fileTree = getFileTree("F:\\37src\\37src\\src");
    }
}
