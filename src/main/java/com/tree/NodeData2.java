package com.tree;

public class NodeData2 extends NodeData {
    public Integer id;

    public NodeData2(NodeData node, Integer id) {
        super(node.code, node.name);
        this.id = id;
    }
}
