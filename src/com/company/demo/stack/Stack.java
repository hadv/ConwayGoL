package com.company.demo.stack;

import java.util.Arrays;

/**
 * A simple stack implementation with basic {@code push} and {@code pop} operations.
 *
 * @param <T> is the element type of the {@code Stack},
 *           T should be implement from {@code Comparable<T>} interface.
 */
public class Stack<T extends Comparable<T>> {

    /**
     * Array to contain the element of the stack
     */
    protected Node<T>[] data;

    /**
     * The current number of element in the stack
     */
    protected int size;

    /**
     * The current number of element that stack can contain.
     * This will be grow to contain more element if need.
     */
    protected int capacity;

    /**
     * Create an empty stack with capacity of 10 elements
     */
    public Stack() {
        size = 0;
        capacity = 10;
        data = new Node[capacity];
    }

    /**
     * Push an element to the top of the stack.
     * The stack do not allow {@code null} item value.
     * If a {@code null} value is pushed into the stack then throw {@code UnsupportedOperationException}
     *
     * @param item the item will be push to the stack.
     *             Throw {@code UnsupportedOperationException} if item is {@code null}
     */
    public void push(T item) {
        if (item == null) {
            throw new UnsupportedOperationException();
        }
        if ((size + 1) > capacity) {
            growCapacity();
        }
        if (size == 0) {
            data[size] = new Node<T>(item, item);
        } else {
            T max = data[size - 1].getMax();
            if (item.compareTo(max) > 0) {
                max = item;
            }
            data[size] = new Node<T>(item, max);
        }
        size++;
    }

    /**
     * Get out the element at the top of the stack.
     * If the stack is empty then return {@code null} value.
     *
     * @return the top element of the stack then set the top element of the stack to {@code null}
     */
    public T pop() {
        if (size == 0) {
            return null;
        }

        T item = data[size - 1].getItem();
        data[size - 1] = null;
        size--;
        return item;
    }

    /**
     * Get the maximum value of the stack.
     *
     * @return The maximum value of the stack, return {@code null} value if empty stack.
     */
    public T getMax() {
        if (size == 0) {
            return null;
        } else {
            return data[size - 1].getMax();
        }
    }

    /**
     * Grow capacity of the stack with more 10 elements
     */
    protected void growCapacity() {
        capacity += 10;

        data = Arrays.copyOf(data, capacity);
    }
}
