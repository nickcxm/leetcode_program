package com.company;

import java.util.*;

public class LinkListTest {
    public static void main(String[] args) {
        ListNode head=null;
        ListNode cur=head;
//        for (int i = 1; i < 6; i++) {
//            if (cur==null){
//                head=new ListNode(6-i);
//                cur=head;
//            }else {
//                cur.next=new ListNode(i);
//                cur=cur.next;
//            }
//        }
        for (int i = 1; i < 6; i++) {
            if (cur==null){
                head=new ListNode(i);
                cur=head;
            }else {
                cur.next=new ListNode(i);
                cur=cur.next;
            }
        }
        print_list(head,"原来");

        ListNode new_head=new ListNode(0);

        while (head!=null){
            ListNode next = head.next;
            head.next=new_head.next;
            new_head.next=head;
            head=next;
        }
        print_list(new_head.next,"之后");
        System.out.println();
    }

    /**
     * 合并多个有序链表
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        //加入优先队列
        PriorityQueue<ListNode> queue = new PriorityQueue<>(Comparator.comparingInt(a->a.val));
        for (ListNode node:lists){
            if (node!=null){
                queue.add(node);
            }
        }
        ListNode head=new ListNode(0),cur=head;
        //先取出队列中的链表节点
        while (!queue.isEmpty()){
            cur.next=queue.poll();
            //然后如果节点的next不为空的话,再把next放进队列
            if (cur.next.next!=null){
                queue.add(cur.next.next);
            }
            cur=cur.next;
        }
        return head.next;
    }

    /**
     * 合并k个有序链表,分治
     * @param lists 链表数组
     * @return
     */
    public ListNode mergeKListsFenzhi(ListNode[] lists) {
        //如果长度为0,直接返回null
        if (lists==null||lists.length==0){
            return null;
        }
        //长度为1,返回第一位
        if (lists.length==1){
            return lists[0];
        }
        //长度为2,直接调用合并两个链表的函数
        if (lists.length==2){
            return mergeTwoLists(lists[0],lists[1]);
        }
        //求出mid
        int mid=lists.length/2;
        List<ListNode> list1=new ArrayList<>();
        List<ListNode> list2=new ArrayList<>();
        //按照mid,把两部分加入不同的list
        for (int i = 0; i < mid; i++) {
            list1.add(lists[i]);
        }
        for (int i = mid; i < lists.length; i++) {
            list2.add(lists[i]);
        }
        //递归,求出这两段链表合并后的链表头
        ListNode node1 = mergeKListsFenzhi(list1.toArray(new ListNode[list1.size()]));
        ListNode node2 = mergeKListsFenzhi(list2.toArray(new ListNode[list2.size()]));
        //合并两个链表
        return mergeTwoLists(node1,node2);
    }


    /**
     * 合并两个有序链表
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        //如果两个链表都为null,直接返回
        if (l1==null&&l2==null){
            return null;
        }
        //new一个虚拟的节点
        ListNode head=new ListNode(0),cur=head;
        //循环,合并两个链表
        while (l1!=null||l2!=null){
            //如果l1为null,说明l1已经没有了,后面的直接等于l2,返回head.next
            if (l1==null){
                cur.next=l2;
                return head.next;
            }else if (l2==null){
                //同理如上
                cur.next=l1;
                return head.next;
            }else if (l1.val<l2.val){
                //如果l1的值比l2小,cur的下一个节点就是l1
                cur.next=l1;
                l1=l1.next;
            }else {
                //反之为l2
                cur.next=l2;
                l2=l2.next;
            }
            cur=cur.next;
        }
        return head.next;
    }

    /**
     * 含有随机指针的深拷贝
     * 思路是,先遍历一遍链表,然后用一个map保存复制过的节点
     * key为旧的节点,value为复制节点
     * @param head
     * @return
     */
    public Node copyRandomList(Node head) {
        if (head==null){
            return null;
        }
        HashMap<Node, Node> map = new HashMap<>();
        Node cur=head;
        while (cur!=null){
            //key为旧的节点,value为复制节点
            map.put(cur,new Node(cur.val));
            cur=cur.next;
        }
        cur=head;
        while (cur!=null){
            //取出当前节点的复制节点,然后将他的next置为复制的next节点
            map.get(cur).next=map.get(cur.next);
            //同理,把复制节点的random也置为复制的random节点
            map.get(cur).random=map.get(cur.random);
            cur=cur.next;
        }
        //返回头节点的复制节点
        return map.get(head);
    }

    /**
     * 给定一个链表和一个特定值 x，对链表进行分隔，使得所有小于 x 的节点都在大于或等于 x 的节点之前。
     * 你应当保留两个分区中每个节点的初始相对位置。
     * 使用两个链表
     * @param head
     * @param x
     * @return
     */
    public ListNode partition(ListNode head, int x) {
        //思路是用两个链表,分别存小于x和大于x的节点,然后将两个链表链接即可
        ListNode less=new ListNode(0),more=new ListNode(0),less_node=less,more_node=more;
        while (head!=null){
            if (head.val<x){
                less_node.next=head;
                less_node=head;
            }else {
                more_node.next=head;
                more_node=head;
            }
            head=head.next;
        }
        less_node.next=more.next;
        more_node.next=null;
        return less.next;
    }

    /**
     * 检查链表是否有环
     * 快慢指针
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        //思路:采用快慢指针,快指针每次走两步,慢指针走一步,如果相遇了,就是有环
        ListNode fast=head,slow=fast;
        while (fast!=null){
            //如果快指针下一个就是null,说明没有环
            if (fast.next==null){
                return false;
            }
            fast=fast.next.next;
            slow=slow.next;
            //相遇就是有环
            if (fast==slow){
                return true;
            }
        }
        return false;
    }

    /**
     * 两个链表交点
     * 两条链表长度不一样
     * 1.可以让长的先走一段
     * 2.一起走,可以让短的走完,马上走长的路程,这样当长的走完之后,短的刚好在两条链表长度差的地方,长的再开始走短的,效果和让长的先走一段是一样的
     * 但是更加的简洁优雅
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        /**
         * 思路是将两个链表尾部对齐,
         * 长的链表从中间开始走,如果有存在都是一样的节点话,就是有交点
         * 简洁方式是:一起走,然后短的走完之后,开始走长链表,当长的走完时,走到短的头结点
         * 这时候短的应该在两个链表对齐的地方
         */
        if (headA==null||headB==null){
            return null;
        }
        ListNode h1=headA,h2=headB;
        //如果没有交点,那两个都会走到null,会返回null
        while (h1!=headB){
            h1=h1!=null?h1.next:headB;
            h2=h2!=null?h2.next:headA;
        }
        return h1;
    }

    /**
     * 翻转某一段链表
     * @param head
     * @param m
     * @param n
     * @return
     */
    public ListNode reverseBetween(ListNode head, int m, int n) {
        ListNode pre=null;
        ListNode cur=null;
        //找到第一个要翻转的节点,和此节点之前的节点
        for (int i = 1; i <= m; i++) {
            if (cur==null){
                cur=head;
            }else {
                pre=cur;
                cur=cur.next;
            }
        }
        //大意就是要翻转的那个节点往前插入到m的位置上面
        for (int i = m; i < n; i++) {
            ListNode temp=cur.next;
            cur.next=cur.next.next;
            temp.next=pre.next;
            pre.next=temp;
        }
        return head;
    }

    /**
     * 逆置链表 迭代解法
     * @param head
     * @return
     */
    public ListNode getReverseListIterate(ListNode head){
        ListNode pre=null;
        ListNode cur=head;
        //遍历一遍链表
        while (cur!=null){
            //每一个节点往前指
            ListNode temp=cur.next;
            cur.next=pre;
            pre=cur;
            cur=temp;
        }
        return pre;
    }

    //逆转链表,递归
    public ListNode reverseList(ListNode head) {
        if (head!=null){
            ListNode temp=head.next;
            head.next=null;
            head=getReverseListRecusive(head,temp);
        }
        return head;
    }

    /**
     * 逆置链表 递归解法
     * @param cur
     * @param next
     * @return
     */
    public ListNode getReverseListRecusive(ListNode cur,ListNode next){
        ListNode temp;
        //一个一个往前指
        if (next!=null){
            temp=next.next;
            next.next=cur;
            return getReverseListRecusive(next,temp);
        }else {
            return cur;
        }
    }

    /**
     * 打印链表
     * @param head
     */
    public static void print_list(ListNode head,String listName){
        System.out.println("链表名:"+listName);
        if (head==null){
            System.out.println("null");
            return;
        }
        while (head!=null){
            System.out.print(head.val);
            if (head.next!=null){
                System.out.print("->");
            }else {
                System.out.println();
            }
            head=head.next;
        }
    }

    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
        }
    }

    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

}
