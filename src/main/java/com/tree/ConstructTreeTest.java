package com.tree;

import com.scalified.tree.TraversalAction;
import com.scalified.tree.TreeNode;
import com.scalified.tree.multinode.ArrayMultiTreeNode;
import com.sun.source.tree.Tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库中保存了树形信息（id、parent_id、name），现在要利用这些信息构造一颗树
 */

class Item {
    Integer id;
    Integer parentId;
    String name;
    String level;

    final static String LEVEL_XIAN = "县级"; // 代表节点是县城

    public Item(Integer id, Integer parentId, String name, String level) {
        this(id, parentId, name);
        this.level = level;
    }

    public Item(Integer id, Integer parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }
}

public class ConstructTreeTest {

    public static void main(String[] args) {
        // 利用map模拟数据库中存储的数据
        HashMap<Integer, Item> map = new HashMap<>();
        map.put(1, new Item(1, 0, "中国"));
        map.put(110000, new Item(110000, 1, "北京市"));
        map.put(110101, new Item(110101, 110000, "东城区", Item.LEVEL_XIAN));
        map.put(110102, new Item(110102, 110000, "西城区", Item.LEVEL_XIAN));
        map.put(110105, new Item(110105, 110000, "朝阳区", Item.LEVEL_XIAN));
        map.put(410000, new Item(410000, 1, "河南省"));
        map.put(411300, new Item(411300, 410000, "南阳市"));
        map.put(411326, new Item(411326, 411300, "淅川县", Item.LEVEL_XIAN));


        // 现在把这些数据构造成一颗树
        ArrayMultiTreeNode<Item> root = new ArrayMultiTreeNode<>(map.get(1));
        constructTree(root, map);

        // 对区县这个level进行搜索(如只返回包含“淅川县”的节点)。如果一个分叉下没有区县，就过滤掉这个分叉
        // 返回中国-河南-南阳-淅川 这个分支
        ArrayMultiTreeNode<Item> newTree = searchLeafByName(root, "淅川");

        // 返回整棵树
        ArrayMultiTreeNode<Item> newTree2 = searchLeafByName(root, null);

        // 返回null
        ArrayMultiTreeNode<Item> newTree3 = searchLeafByName(root, "不存在的县");


        // 往树的叶子节点中，增加一种新的节点类型。这里我们试着在区县下新增 “三好学生”节点
        HashMap<Integer, Item> map2 = new HashMap<>();
        map2.put(101, new Item(101, 411326, "张三")); // 淅川县的三好学生 张三
        map2.put(102, new Item(102, 110102, "李四")); // 西城区的三好学生 李四
        root.traversePreOrder(new TraversalAction<TreeNode<Item>>() {
            @Override
            public void perform(TreeNode<Item> node) {
                if (node.isLeaf()) {
                    List<Item> childrens = new ArrayList<>();
                    for (Map.Entry<Integer, Item> entry : map2.entrySet()) {
                        if (entry.getValue().parentId.equals(node.data().id)) {
                            childrens.add(entry.getValue());
                        }
                    }
                    if (!childrens.isEmpty()) {
                        for (Item child : childrens) {
                            node.add(new ArrayMultiTreeNode<>(child));
                        }
                    }
                }
            }

            @Override
            public boolean isCompleted() {
                return false;
            }
        });
    }

    /**
     * 构造一颗树
     *
     * @param root
     * @param map
     */
    public static void constructTree(ArrayMultiTreeNode<Item> root, HashMap<Integer, Item> map) {
        List<Item> childrens = new ArrayList<>();
        for (Map.Entry<Integer, Item> entry : map.entrySet()) {
            if (entry.getValue().parentId.equals(root.data().id)) {
                childrens.add(entry.getValue());
            }
        }
        if (childrens.size() > 0) {
            for (Item children : childrens) {
                ArrayMultiTreeNode childNode = new ArrayMultiTreeNode(children);
                root.add(childNode);
                constructTree(childNode, map);
            }
        }
    }

    public static ArrayMultiTreeNode searchLeafByName(TreeNode<Item> root, String targetName) {
        Item nodeData = root.data();
        // 如果当前节点是叶子节点
        if (root.subtrees().size() <= 0) {
            if (nodeData.level != null && !nodeData.level.equals(Item.LEVEL_XIAN)) {
                return null;
            }
            if (targetName == null || nodeData.name.contains(targetName)) {
                return new ArrayMultiTreeNode(root);
            } else {
                return null;
            }
        } else {
            // 递归搜索子节点
            ArrayMultiTreeNode newTree = new ArrayMultiTreeNode(root);
            for (TreeNode<Item> child : root.subtrees()) {
                TreeNode childTree = searchLeafByName(child, targetName);
                if (childTree != null) {
                    newTree.add(childTree);
                }
            }
            // 如果新树有子节点，则返回新树；否则返回null
            return newTree.subtrees().size() > 0 ? newTree : null;
        }
    }

}
