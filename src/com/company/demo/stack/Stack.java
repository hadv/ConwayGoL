package com.company.demo.stack;

import java.util.Arrays;

/**
 * <p>A simple stack implementation
 *
 * @param <T> is the element type of the {@code Stack}
 */
public class Stack<T> {

    /**
     * Array to contain the element of the stack
     */
    protected Object[] data;

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
        data = new Object[capacity];
    }

    /**
     * <p>Push an element to the top of the stack.
     *
     * @param item the item will be push to the stack.
     */
    public void push(T item) {
        if ((size + 1) > capacity) {
            growCapacity();
        }
        data[size] = item;
        size++;
    }

    /**
     * <p>Get out the element at the top of the stack.
     *
     * @return the top element of the stack then set the element to {@code null}
     */
    public T pop() {
        if (size == 0) {
            return null;
        }
        T item = (T)data[size - 1];
        data[size - 1] = null;
        size--;
        return item;
    }

    /**
     * Grow capacity of the stack with more 10 elements
     */
    protected void growCapacity() {
        capacity += 10;

        data = Arrays.copyOf(data, capacity);
    }
}
