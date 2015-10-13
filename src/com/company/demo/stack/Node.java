package com.company.demo.stack;

public class Node<T extends Comparable<T>> {

    private T item;

    private int max;

    public Node(T item, int max) {
        this.item = item;
        this.max = max;
    }

    public T getItem() {
        return item;
    }

    public int getMax() {
        return max;
    }
}
