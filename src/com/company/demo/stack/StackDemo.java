package com.company.demo.stack;

/**
 * A simple {@code Stack} demo program.
 */
public class StackDemo {
    public static void main(String[] args) {
        Stack<Long> stack = new Stack<Long>();

        System.out.println("mpty stack max : " + stack.getMax());

        stack.push(100L);
        stack.push(200L);
        for (long i = 0; i < 10; i++) {
            stack.push(i);
        }

        if (stack.getMax() == 200L) {
            System.out.println("Success!!");
        } else {
            System.out.println("Failure!!! Please try again!");
        }

        stack.pop();
        Long item = stack.pop();
        if (item == 8L) {
            System.out.println("Success!!!");
        } else {
            System.out.println("Failure!!! Please try again!");
        }
    }
}