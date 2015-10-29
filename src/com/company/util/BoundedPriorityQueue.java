package com.company.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * An bounded priority {@linkplain Queue queue} extends from {@linkplain PriorityQueue priority queue}.
 * The elements of the priority queue are ordered according to their
 * {@linkplain Comparable natural ordering}, or by a {@link Comparator}
 * provided at queue construction time, depending on which constructor is
 * used. A priority queue does not permit {@code null} elements.
 * A priority queue relying on natural ordering also does not permit
 * insertion of non-comparable objects (doing so may result in
 * {@code ClassCastException}).
 *
 * <p>The <em>head</em> of this queue is the <em>least</em> element
 * with respect to the specified ordering.  If multiple elements are
 * tied for least value, the head is one of those elements -- ties are
 * broken arbitrarily.  The queue retrieval operations {@code poll},
 * {@code remove}, {@code peek}, and {@code element} access the
 * element at the head of the queue.
 *
 * <p>A priority queue is bounded to a specified
 * <i>maximum capacity</i> governing the size of an array used to store the
 * elements on the queue. When the size of the queue is greater than maximum capacity
 * then the head element will be remove then the new element will be added to the queue.
 *
 * @param <E> the type of elements held in this collection
 */
public class BoundedPriorityQueue<E> extends PriorityQueue<E> {

    private static final int DEFAULT_MAXIMUM_CAPACITY = 100;

    private int maxCapacity;

    /**
     * Creates a {@code BoundedPriorityQueue} with default  maximum capacity (100) and
     * whose elements are ordered according to the specified comparator.
     *
     * @param  comparator the comparator that will be used to order this
     *         priority queue.  If {@code null}, the {@linkplain Comparable
     *         natural ordering} of the elements will be used.
     */
    public BoundedPriorityQueue(Comparator<? super E> comparator) {
        super(comparator);
        this.maxCapacity = DEFAULT_MAXIMUM_CAPACITY;
    }

    /**
     * Creates a {@code BoundedPriorityQueue} with specified maximum capacity and
     * whose elements are ordered according to the specified comparator.
     *
     * @param maxCapacity the bounded capacity of the queue.
     * @param  comparator the comparator that will be used to order this
     *         priority queue.  If {@code null}, the {@linkplain Comparable
     *         natural ordering} of the elements will be used.
     * @throws IllegalArgumentException if {@code maxCapacity} is
     *         less than 1
     */
    public BoundedPriorityQueue(int maxCapacity, Comparator<? super E> comparator) {
        super(comparator);
        if (maxCapacity < 1) {
            throw new IllegalArgumentException();
        }
        this.maxCapacity = maxCapacity;
    }

    /**
     * Creates a {@code BoundedPriorityQueue} with the default maximum
     * capacity (100) that orders its elements according to their
     * {@linkplain Comparable natural ordering}.
     */
    public BoundedPriorityQueue() {
        this(DEFAULT_MAXIMUM_CAPACITY, null);
    }

    /**
     * Inserts the specified element into this priority queue.
     * If the size of queue is greater than specified {@code maxCapacity},
     * and if  new element is greater than the head element,
     * then remove the current head element and the new element will be insert into the queue.
     *
     * @param e the element will be able to added into the queue.
     * @return {@code true} (as specified by {@link Collection#add})
     * @throws ClassCastException if the specified element cannot be
     *         compared with elements currently in this priority queue
     *         according to the priority queue's ordering
     * @throws NullPointerException if the specified element is null
     */
    @Override
    public boolean add(E e) {
        if (size() < maxCapacity) {
            return super.add(e);
        } else {
            E root = super.peek();

            int order;
            if (comparator() == null) {
                order = ((Comparable<E>)e).compareTo(root);
            } else {
                order = comparator().compare(e, root);
            }

            if (order > 0) {
                super.poll();
                return super.add(e);
            }
            return false;
        }
    }
}
