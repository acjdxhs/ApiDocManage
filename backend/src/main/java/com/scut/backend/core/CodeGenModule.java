package com.scut.backend.core;

import com.scut.backend.model.ApiNode;
import com.scut.backend.model.ApiNodeResult;
import com.scut.backend.model.Result;
import com.scut.backend.uits.ResultUtil;
import com.scut.backend.uits.XmlUtil;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.SourcePosition;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

@Component
public class CodeGenModule {

    public Result apiToCode(String path, ApiNodeResult api) {
        MyDoclet.execute(path);
        RootDoc root = MyDoclet.getRootDoc();
        int result = handle(root.classes(), api, path);
        if (result == -1) {
            return ResultUtil.returnBadResult("类不存在");
        } else if (result == -2) {
            return ResultUtil.returnBadResult("转化异常");
        } else {
            return ResultUtil.returnGoodResult();
        }
    }

    /**
     * @param classDocs
     * @param api
     * @param path
     * @return
     */
    private int handle(ClassDoc[] classDocs, ApiNodeResult api, String path) {
        if (classDocs.length < 1) {
            return -1;
        }
        ClassDoc classDoc = classDocs[0];
        MethodDoc[] methods = classDoc.methods();
        ApiNode classNode = api.getClassNode();
        List<ApiNode> methodsNode = api.getMethodNodes();

        String fileName = path.substring(path.lastIndexOf('\\') + 1);
        String dir = path.substring(0, path.lastIndexOf('\\') + 1);
        String tmpFile = dir + "tmp.java";

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tmpFile)))) {
            int count = 1; //记录读取的行数
            // 输出类前面的信息，直到类的注解
            SourcePosition classPosition = classDoc.position();
            int an = classDoc.annotations().length;
            int line = classPosition.line() - an;
            while (count <= line - 1) {
                count++;
                String str = reader.readLine();
                writer.write(str + "\r\n");
            }
            // 获取类注释
            String classComment = XmlUtil.apiNodeToXml(classNode);
            // 将注释包起来
            classComment = roundComment(classComment);
            // 写入注释
            writer.write(classComment + "\r\n");

            // 写入函数
            for (int i = 0; i < methods.length; i++) {
                SourcePosition position = methods[i].position();
                int bn = methods[i].annotations().length;
                int pos = position.line() - bn;
                while (count <= pos - 1) {
                    count++;
                    String str = reader.readLine();
                    writer.write(str + "\r\n");
                }
                // 写入函数注释
                String methodComment = XmlUtil.apiNodeToXml(methodsNode.get(i));
                methodComment = roundComment(methodComment);
                writer.write(methodComment + "\r\n");
            }

            String l = null;
            while ((l = reader.readLine()) != null) {
                count++;
                writer.write(l + "\r\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return -2;
        }

        File src = new File(path);
        src.delete();
        File des = new File(tmpFile);
        System.out.println(des.renameTo(src));
        return 0;
    }

    private String roundComment(String s) {
        s = "\t/**" + s + "\n*/";
        return s.replaceAll("\n", "\n\t* ");
    }

    public static void main(String[] args) {
        String path = "F:\\graduate\\code\\ApiDocManage\\backend\\upload\\3000\\root\\InformationController.java";
        ScanCodeModule scanCodeModule = new ScanCodeModule();
        CodeGenModule module = new CodeGenModule();
        ApiNodeResult result = scanCodeModule.codeToApi(path);
        module.apiToCode(path, result);
    }
}
