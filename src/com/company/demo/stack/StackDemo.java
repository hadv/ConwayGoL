package com.company.demo.stack;

/**
 * A simple {@code Stack} demo program.
 */
public class StackDemo {
    public static void main(String[] args) {
        Stack<Long> stack = new Stack<Long>();

        System.out.println("Empty stack max : " + stack.getMax());

        stack.push(100L);
        stack.push(200L);
        for (long i = 0; i < 10; i++) {
            stack.push(i);
        }
        stack.push(300L);

        if (stack.getMax() == 300L) {
            System.out.println("Success!!!");
        } else {
            System.out.println("Failure!!! Please try again!");
        }

        stack.pop();
        stack.pop();
        stack.pop();
        Long item = stack.pop();

        if (item == 7L) {
            System.out.println("Success!!!");
        } else {
            System.out.println("Failure!!! Please try again!");
        }

        if (stack.getMax() == 200L) {
            System.out.println("Success!!!");
        } else {
            System.out.println("Failure!!! Please try again!");
        }

        stack.pop();
        stack.pop();
        stack.pop();
        stack.pop();

        if (stack.getMax() == 200L) {
            System.out.println("Success!!!");
        } else {
            System.out.println("Failure!!! Please try again!");
        }

        stack.pop();
        stack.pop();
        stack.pop();
        stack.pop();

        if (stack.getMax() == 100L) {
            System.out.println("Success!!!");
        } else {
            System.out.println("Failure!!! Please try again!");
        }
        stack.pop();

        if (stack.getMax() == null) {
            System.out.println("Success!!!");
        } else {
            System.out.println("Failure!!! Please try again!");
        }

        item = stack.pop();
        if (item == null && stack.getMax() == null) {
            System.out.println("Success!!!");
        } else {
            System.out.println("Failure!!! Please try again!");
        }
    }
}