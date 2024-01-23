package com.tree;

public class NodeData {
    public Integer code;
    public String name;
    public Integer level;

    public NodeData(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public NodeData(Integer code, String name, Integer level) {
        this.code = code;
        this.name = name;
        this.level = level;
    }
}
