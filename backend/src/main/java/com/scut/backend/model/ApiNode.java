package com.scut.backend.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiNode {

    private String name;
    private String desc = "";
    private Map<String, String> attribute = new HashMap<>();
    private List<ApiNode> children = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Map<String, String> getAttribute() {
        return attribute;
    }

    public void setAttribute(Map<String, String> attribute) {
        this.attribute = attribute;
    }

    public List<ApiNode> getChildren() {
        return children;
    }

    public void setChildren(List<ApiNode> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "ApiNode{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", attribute=" + attribute +
                ", children=" + children +
                '}';
    }
}
