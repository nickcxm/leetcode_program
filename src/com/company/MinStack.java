package com.company;

import java.util.Stack;

public class MinStack {
    private Stack<Integer> stack;
    private Stack<Integer> minTempStack;

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(512);
        minStack.push(-1024);
        minStack.push(-1024);
        minStack.push(512);
        minStack.pop();
        System.out.println(minStack.getMin());
        minStack.pop();
        System.out.println(minStack.getMin());
        minStack.pop();
        System.out.println(minStack.getMin());
    }
    /** initialize your data structure here. */
    public MinStack() {
        stack=new Stack<>();
        minTempStack =new Stack<>();
    }

    public void push(int x) {
        stack.push(x);
        if (minTempStack.isEmpty()||x<=minTempStack.peek()){
            minTempStack.push(x);
        }
    }

    public void pop() {
        Integer x = stack.pop();
        if (x.equals(minTempStack.peek())){
            minTempStack.pop();
        }
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minTempStack.peek();
    }
}
