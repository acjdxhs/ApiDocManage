package com.scut.backend.model;

import java.util.List;

public class ApiNodeResult {

    private ApiNode classNode;
    private List<ApiNode> methodNodes;

    public ApiNode getClassNode() {
        return classNode;
    }

    public void setClassNode(ApiNode classNode) {
        this.classNode = classNode;
    }

    public List<ApiNode> getMethodNodes() {
        return methodNodes;
    }

    public void setMethodNodes(List<ApiNode> methodNodes) {
        this.methodNodes = methodNodes;
    }
}
