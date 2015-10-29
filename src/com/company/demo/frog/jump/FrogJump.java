package com.company.demo.frog.jump;


public class FrogJump {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.print(jump(i) + " ");
        }
    }

    /**
     * Calculate the total number of way that a frog jumping up stair with <tt>n</tt> steps.
     * The frog can jump 1 step or 2 step for each time.
     *
     * @param n the total steps of stair
     * @return the total number of way that a frog can jump up to the <tt>n</tt> steps stair.
     */
    static long jump(long n) {
        if (n == 0 || n == 1) return 1;

        return jump(n - 1) + jump(n - 2);
    }
}
