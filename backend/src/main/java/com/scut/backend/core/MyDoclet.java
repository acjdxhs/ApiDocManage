package com.scut.backend.core;

import com.scut.backend.model.ApiNode;
import com.scut.backend.model.ApiNodeResult;
import com.scut.backend.uits.XmlUtil;
import com.sun.javadoc.*;
import com.sun.tools.doclets.standard.Standard;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyDoclet extends Doclet {

    private static ApiNodeResult result;

    public static ApiNodeResult getResult() {
        return result;
    }

    public static boolean start(RootDoc root) {
        System.out.println("success");
        handle(root.classes());
        return true;
        //return HtmlDoclet.start(root);
    }

    private static void handle(ClassDoc[] classes) {
        if (classes.length < 1) {
            return;
        }
        // 只处理第一个类
        ClassDoc classDoc = classes[0];
        // 解析类注释
        ApiNode classNode;
        String classComment = classDoc.getRawCommentText();
        ApiNode apiNode = XmlUtil.xmlToApiNode(classComment);
        if (apiNode == null) {
            classNode = new ApiNode();
        } else {
            classNode = apiNode;
        }
        classNode.setName(classDoc.name());

        List<ApiNode> methodNodeList = new ArrayList<>();
        // 处理类方法
        MethodDoc[] methods = classDoc.methods();
        for (int i=0; i<methods.length; i++) {
            ApiNode methodNode;
            // 方法注释
            String methodComment = methods[i].getRawCommentText();
            if (StringUtils.isEmpty(methodComment)) {
                methodNode = new ApiNode();
            } else {
                methodNode = XmlUtil.xmlToApiNode(methodComment);
                if (methodNode == null) {
                    methodNode = new ApiNode();
                }else {
                    methodNodeList.add(methodNode);
                    continue;
                }
            }

            // 处理方法名称
            methodNode.setName(methods[i].name());
            // 方法参数
            Parameter[] parameters = methods[i].parameters();
            ApiNode paramsNode = new ApiNode();
            paramsNode.setName("Parameters");
            paramsNode.setDesc("参数");
            methodNode.getChildren().add(paramsNode);
            for (int j = 0; j < parameters.length; j++) {
                // 参数类型与名称
                ApiNode paramNode = new ApiNode();
                paramNode.setName("Parameter");
                paramNode.getAttribute().put("name", parameters[j].name());
                paramNode.getAttribute().put("type", parameters[j].typeName());
                paramNode.getAttribute().put("desc", "");
                paramsNode.getChildren().add(paramNode);
            }
            // 方法返回值
            Type returnType = methods[i].returnType();
            ApiNode returnNode = new ApiNode();
            returnNode.setName("Responses");
            returnNode.setDesc("响应");
            returnNode.getAttribute().put("type", returnType.typeName());
            returnNode.getAttribute().put("desc", "");
            methodNode.getChildren().add(returnNode);

            // 方法异常
            Type[] exceptionTypes = methods[i].thrownExceptionTypes();
            // 有异常才创建
            if (exceptionTypes.length > 0) {
                ApiNode exceptionNode = new ApiNode();
                paramsNode.setName("Exceptions");
                paramsNode.setDesc("异常");
                methodNode.getChildren().add(exceptionNode);
                for (int k = 0; k < exceptionTypes.length; k++) {
                    ApiNode expNode = new ApiNode();
                    expNode.setName("Exception");
                    expNode.getAttribute().put("type", exceptionTypes[k].typeName());
                    expNode.getAttribute().put("desc", "");
                }
            }
            methodNodeList.add(methodNode);
        }
        Map<String, Object> map = new HashMap<>();
        result = new ApiNodeResult();
        result.setClassNode(classNode);
        result.setMethodNodes(methodNodeList);
    }
}