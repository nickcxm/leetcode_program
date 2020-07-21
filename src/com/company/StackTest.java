package com.company;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class StackTest {
    public static void main(String[] args) {
        StackTest test = new StackTest();
        String str="(1+(411+5+2)-3)+(6+8)";
        System.out.println(test.calculate(str));
    }

    /**
     * 在未排序的数组中找到第 k 个最大的元素。
     * 请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
     * @param nums
     * @param k
     * @return
     */
//    public int findKthLargest(int[] nums, int k) {
//
//    }

    /**
     * 实现一个基本的计算器来计算一个简单的字符串表达式的值。
     *
     * 字符串表达式可以包含左括号 ( ，右括号 )，加号 + ，减号 -，非负整数和空格  。
     * @param s
     * @return
     */
    public int calculate(String s) {
        Stack<Integer> stack = new Stack<Integer>();
        // sign 代表正负
        int sign = 1, res = 0;
        int length = s.length();
        for (int i = 0; i < length; i++) {
            char ch = s.charAt(i);
            if (Character.isDigit(ch)) {
                int cur = ch - '0';
                while (i + 1 < length && Character.isDigit(s.charAt(i + 1)))
                    cur = cur * 10 + s.charAt(++i) - '0';
                res = res + sign * cur;
            } else if (ch == '+') {
                sign = 1;
            } else if (ch == '-') {
                sign = -1;
            } else if (ch == '(') {
                stack.push(res);
                res = 0;
                stack.push(sign);
                sign = 1;
            } else if (ch == ')') {
                res = stack.pop() * res + stack.pop();
            }
        }
        return res;
    }

    /**
     * 是否是合法的出栈顺序
     * @param pushed
     * @param popped
     * @return
     */
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        if (pushed.length == 0 && popped.length == 0) {
            return true;
        }
        Stack<Integer> stack = new Stack<>();
        Queue<Integer> queue=new LinkedList<>();
        int cur=0;
        for (int i = 0; i < pushed.length; i++) {
            queue.add(pushed[i]);
        }
        while (!queue.isEmpty()){
            Integer x = queue.poll();
            if (x!=popped[cur]){
                stack.push(x);
            }else {
                cur++;
                while (!stack.isEmpty()){
                    Integer m = stack.peek();
                    if (m!=popped[cur]){
                        break;
                    }else {
                        stack.pop();
                        cur++;
                    }
                }
            }
        }
        while (!stack.isEmpty()){
            Integer x = stack.pop();
            if (x!=popped[cur]){
                return false;
            }else {
                cur++;
            }
        }
        return true;
    }
}
