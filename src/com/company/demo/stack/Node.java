package com.company.demo.stack;

public class Node<T extends Comparable<T>> {

    private T item;

    private T max;

    public Node(T item, T max) {
        this.item = item;
        this.max = max;
    }

    public T getItem() {
        return item;
    }

    public T getMax() {
        return max;
    }
}
