package com.company.demo.frog.jump;

/**
 * Using <strong>top-down</strong> dynamic programing to calculate
 * the total number of way that a frog jumping up stair with <tt>n</tt> steps.
 * The frog can jump 1 step or 2 step for each time.
 */
public class TopDown {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.print(jump(i, new long[i+1]) + " ");
        }
    }

    /**
     * Using <strong>top-down</strong> dynamic programing to calculate
     * the total number of way that a frog jumping up stair with <tt>n</tt> steps.
     * The frog can jump 1 step or 2 step for each time.
     *
     * @param n the total steps of stair
     * @param a the array to store the calculated step, the default value <i>zero</i> is mean still not calculated.
     * @return the total number of way that a frog can jump up to the <tt>n</tt> steps stair.
     * If <tt>n</tt> is less than 0, then return 0.
     */
    static long jump(int n, long a[]) {
        if (n < 0) return 0;

        if (n == 0 || n == 1) return 1;

        if (a[n] != 0) {
            return a[n];
        } else {
            a[n] = jump(n-1, a) + jump(n-2, a);
            return a[n];
        }
    }
}
