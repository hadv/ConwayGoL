package com.company.demo.frog.jump;


public class FrogJump {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.print(jump(i) + " ");
        }
    }

    /**
     * Calculate the total number of way that a frog jumping up stair with <tt>n</tt> steps.
     * The frog can jump 1 step or 2 step for each time.
     *
     * @param n the total steps of stair
     * @return the total number of way that a frog can jump up to the <tt>n</tt> steps stair.
     * If <tt>n < 0</tt>, then return 0.
     */
    static long jump(int n) {
        if (n < 0) return 0;

        if (n == 0 || n == 1) return 1;

        long a[] = new long[n+1];
        a[0] = 1;
        a[1] = 1;
        for (int i = 2; i <= n; i++) {
            a[i] = a[i-1] + a[i-2];
        }
        return a[n];
    }
}
