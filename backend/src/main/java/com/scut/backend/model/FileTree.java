package com.scut.backend.model;

import java.util.List;

public class FileTree {
    private String name;
    private List<FileTree> children;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FileTree> getChildren() {
        return children;
    }

    public void setChildren(List<FileTree> children) {
        this.children = children;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
