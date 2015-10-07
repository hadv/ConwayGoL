package com.company.demo.stack;

/**
 * A {@code Stack} demo program.
 */
public class StackDemo {
    public static void main(String[] args) {
        Stack<Long> stack = new Stack<Long>();

        for (long i = 0; i < 10; i++) {
            stack.push(i);
        }

        stack.push(100L);

        long item = stack.pop();

        if (item == 100L) {
            System.out.println("Well done!!!");
        } else {
            System.out.println("Failure!!! Please try again!");
        }

        item = stack.pop();
        if (item == 9L) {
            System.out.println("Well done!!!");
        } else {
            System.out.println("Failure!!! Please try again!");
        }
    }
}