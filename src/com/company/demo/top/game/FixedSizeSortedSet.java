package com.company.demo.top.game;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * A fixed size  implementation based on a {@link TreeSet}.
 * The elements are ordered using their {@linkplain Comparable natural
 * ordering}, or by a {@link Comparator} provided at set creation
 * time, depending on which constructor is used.
 *
 * @param <E> the element type will be added to this set.
 */
public class FixedSizeSortedSet<E> extends TreeSet<E> {

    /**
     * The maximum size of the set
     */
    private static final int MAX_SIZE = 3;

    /**
     * The comparator used to maintain order in this fixed size sorted set, or
     * null if it uses the natural ordering of its element.
     */
    private final Comparator<? super E> comparator;

    /**
     * Constructs a new, empty fixed sset, sorted according to the specified
     * comparator.  All elements inserted into the set must be <i>mutually
     * comparable</i> by the specified comparator: {@code comparator.compare(e1,
     * e2)} must not throw a {@code ClassCastException} for any elements
     * {@code e1} and {@code e2} in the set.  If the user attempts to add
     * an element to the set that violates this constraint, the
     * {@code add} call will throw a {@code ClassCastException}.
     *
     * @param comparator the comparator that will be used to order this set.
     *        If {@code null}, the {@linkplain Comparable natural
     *        ordering} of the elements will be used.
     */
    public FixedSizeSortedSet(Comparator<? super E> comparator) {
        super(comparator);
        this.comparator = comparator;
    }

    /**
     * Adds the specified element to this set if it is not already present.
     * More formally, adds the specified element {@code e} to this set if
     * the set contains no element {@code e2} such that
     * <tt>(e==null&nbsp;?&nbsp;e2==null&nbsp;:&nbsp;e.equals(e2))</tt>.
     * If this set already contains the element, the call leaves the set
     * unchanged and returns {@code false}.
     * If this set already contains full size, then check if the {@code e} less than
     * the lowest element of the set, return {@code false}.
     * If the {@code e} greater than the lowest elmenent of the set then remove the lowest element
     * and add the new element {@code e} into the set.
     *
     * @param e element to be added to this set
     * @return {@code true} if this set did not already contain the specified
     *         element
     * @throws ClassCastException if the specified object cannot be compared
     *         with the elements currently in this set
     * @throws NullPointerException if the specified element is null
     *         and this set uses natural ordering, or its comparator
     *         does not permit null elements
     */
    @Override
    public boolean add (E e) {
        if (size() >= MAX_SIZE) {
            E lowest = first();
            int order;
            if (comparator == null) {
                order = ((Comparable<E>)e).compareTo(lowest);
            } else {
                order = comparator.compare(e, lowest);
            }

            if (order > 0) {
                remove(lowest);
                return super.add(e);
            }
            return false;
        } else {
            return super.add(e);
        }
    }
}