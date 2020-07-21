package com.company;

import java.util.*;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode(int x) { val = x; }
 * }
 */
class Solution {

    private void getRightSideView(List<Integer> list,TreeNode node,int level){
        if (level>=list.size()){
            list.add(node.val);
        }else {
            list.set(level,node.val);
        }
        if (node.left!=null){
            getRightSideView(list,node.left,level+1);
        }
        if (node.right!=null){
            getRightSideView(list,node.right,level+1);
        }
    }

    /**
     * 给定一棵二叉树，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值。
     * 输入: [1,2,3,null,5,null,4]
     * 输出: [1, 3, 4]
     * 解释:
     *
     *    1            <---
     *  /   \
     * 2     3         <---
     *  \     \
     *   5     4       <---
     * @param root
     * @return
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res=new ArrayList<>();
        if (root!=null){
            getRightSideView(res,root,0);
        }
        return res;
    }

    /**
     * 给定一个二叉树，返回其节点值的锯齿形层次遍历。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
     * 判断层级,反向即可
     * @param root
     * @return
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root != null) {
            getLevelOrder(res, root, 0);
        }
        for (int i = 0; i < res.size(); i++) {
            if (i%2!=0){
                Collections.reverse(res.get(i));
            }
        }
        return res;
    }

    /**
     * 二叉树层次遍历,分割每一层为一个list
     * 与前序遍历有点类似
     *
     * @param list
     * @param node
     * @param level
     */
    private void getLevelOrder(List<List<Integer>> list, TreeNode node, int level) {
        if (node != null) {
            if (level >= list.size()) {
                List<Integer> treeNodes = new ArrayList<>();
                treeNodes.add(node.val);
                list.add(treeNodes);
            } else {
                List<Integer> nodes = list.get(level);
                nodes.add(node.val);
            }
            getLevelOrder(list, node.left, level + 1);
            getLevelOrder(list, node.right, level + 1);
        }
    }

    /**
     * 二叉树层次遍历,分割每一层为一个list
     * 递归调用 getLevelOrder(res,root,0);
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root != null) {
            getLevelOrder(res, root, 0);
        }
        return res;
    }


    public void bfsPrint(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        if (root != null) {
            queue.add(root);
        }
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.print(node.val + " ");
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }

    //二叉树后序遍历-递归实现
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        res.addAll(postorderTraversal(root.left));
        res.addAll(postorderTraversal(root.right));
        res.add(root.val);
        return res;
    }

    //二叉树后序遍历-迭代实现
    public List<Integer> postorderTraversalIterate(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                res.add(node.val);
                stack.push(node.left);
                node = node.right;
            }
            node = stack.pop();
        }
        Collections.reverse(res);
        return res;
    }

    //二叉树前序遍历-迭代实现
    public List<Integer> preorderTraversalIterate(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                res.add(node.val);
                stack.push(node.right);
                node = node.left;
            }
            node = stack.pop();
        }
        return res;
    }

    //二叉树前序遍历-递归实现
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        res.add(root.val);
        res.addAll(preorderTraversal(root.left));
        res.addAll(preorderTraversal(root.right));
        return res;
    }

    //二叉树中序遍历-迭代实现
    public List<Integer> inorderTraversalIterate(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            res.add(node.val);
            node = node.right;
        }
        return res;
    }

    //二叉树中序遍历-递归实现
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        res.addAll(inorderTraversal(root.left));
        res.add(root.val);
        res.addAll(inorderTraversal(root.right));
        return res;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}