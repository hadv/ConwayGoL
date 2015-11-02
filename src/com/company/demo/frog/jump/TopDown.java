package com.company.demo.frog.jump;

import java.util.HashMap;
import java.util.Map;

/**
 * Using <strong>top-down</strong> dynamic programing to calculate
 * the total number of way that a frog jumping up stair with <tt>n</tt> steps.
 * The frog can jump 1 step or 2 step for each time.
 */
public class TopDown {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.print(jump(i, new HashMap<Integer, Long>()) + " ");
        }
    }

    /**
     * Using <strong>top-down</strong> dynamic programing to calculate
     * the total number of way that a frog jumping up stair with <tt>n</tt> steps.
     * The frog can jump 1 step or 2 step for each time.
     *
     * @param n the total steps of stair
     * @param cache the map to store the calculated step.
     * @return the total number of way that a frog can jump up to the <tt>n</tt> steps stair.
     * If <tt>n</tt> is less than 0, then return 0.
     */
    static long jump(int n, Map<Integer, Long> cache) {
        if (n < 0) return 0;

        if (n == 0 || n == 1) return 1;

        if (cache.containsKey(n)) {
            return cache.get(n);
        } else {
            cache.put(n, jump(n-1, cache) + jump(n-2, cache));
            return cache.get(n);
        }
    }
}
