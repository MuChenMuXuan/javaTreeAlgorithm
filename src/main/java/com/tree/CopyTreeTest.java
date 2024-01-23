package com.tree;

import com.scalified.tree.TreeNode;
import com.scalified.tree.multinode.ArrayMultiTreeNode;

/**
 * 在已经有一个树的情况下（TreeNode<NodeData>）, 现在需要在节点中增加Id属性（对应NodeData2）
 * 这时就需要复制一棵树。思路：
 * 1、初始化newTree。先复制根节点，并加入newTree
 * 2、先序遍历原来的树（先父节点，后孩子节点）。往newTree中add子节点
 * 3、递归，重复步骤2
 */
public class CopyTreeTest {
    public static void main(String[] args) {
        // Creating the tree nodes
        ArrayMultiTreeNode<com.tree.NodeData> n1 = new ArrayMultiTreeNode<>(new com.tree.NodeData(1, "1"));
        ArrayMultiTreeNode<com.tree.NodeData> n2 = new ArrayMultiTreeNode<>(new com.tree.NodeData(2, "2"));
        ArrayMultiTreeNode<com.tree.NodeData> n3 = new ArrayMultiTreeNode<>(new com.tree.NodeData(3, "3"));
        ArrayMultiTreeNode<com.tree.NodeData> n4 = new ArrayMultiTreeNode<>(new com.tree.NodeData(4, "4"));
        ArrayMultiTreeNode<com.tree.NodeData> n5 = new ArrayMultiTreeNode<>(new com.tree.NodeData(5, "5"));
        ArrayMultiTreeNode<com.tree.NodeData> n6 = new ArrayMultiTreeNode<>(new com.tree.NodeData(6, "6"));
        ArrayMultiTreeNode<com.tree.NodeData> n7 = new ArrayMultiTreeNode<>(new com.tree.NodeData(7, "7"));

        // Assigning tree nodes
        n1.add(n2);
        n1.add(n3);
        n2.add(n4);
        n2.add(n5);
        n3.add(n6);
        n3.add(n7);

        ArrayMultiTreeNode newTreeNode = new ArrayMultiTreeNode<com.tree.NodeData2>(new com.tree.NodeData2(n1.data(), n1.data().code));
        travelAndCopy(n1, newTreeNode);
    }

    public static void travelAndCopy(TreeNode<com.tree.NodeData> oldTree, TreeNode newTreeNode) {
        if (oldTree.isLeaf()) {
            return;
        }
        for (TreeNode<com.tree.NodeData> child : oldTree.subtrees()) {
            TreeNode<com.tree.NodeData2> newChild = new ArrayMultiTreeNode(new com.tree.NodeData2(child.data(), child.data().code));
            newTreeNode.add(newChild);
            travelAndCopy(child, newChild);
        }
    }
}
