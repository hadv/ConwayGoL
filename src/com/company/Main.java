package com.company;

/**
 * Outline: Conway Game Of Life
 *
 * 1. Copy the current generation to next generation:
 *      Because using <code>System.arraycopy()</code> to copy 2 dimension have a problem
 *      of the source and the destination array will point to the same object.
 *      So we will copy each one dimension array of the 2-dim arrays in method <code>copy2DimIntArray()</code>.
 *
 * 2. At each step in time, looping all cells in the current generation.
 *      2.1 If the cell is dead and have exactly 3 live cells neighbours becomes a live cell
 *      2.2 If the cell is live cell
 *          (1) If live cell with fewer than two live neighbours dies
 *          (2) If live cell with two or three live neighbours lives on to the next generation.
 *      2.3 Otherwise, keep the current state of the cell (nothing change)
 *      2.4 Print out the current state of the system to console.
 *
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        // initialize state (1: live, 0: dead)
        int cur_gen[][] = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 1, 1, 1, 0},
                {0, 0, 1, 1, 0, 0, 1, 0},
                {0, 0, 1, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        int next_gen[][] = new int[8][8];

        // 1. Copy the current generation to next generation
        copy2DimIntArray(cur_gen, next_gen);

        int g = 0;

        // infinite loop to demo the life will go on forever.
        while (true) {
            // generation step
            g++;

            // print out the current state of the system.
            System.out.println();
            System.out.println("#### gen " + g + " ####");
            printArray(next_gen);

            // 2. At each time, looping all cells in the current generation.
            for (int i = 0; i < cur_gen.length; i++) {
                for (int j = 0; j < cur_gen.length; j++) {

                    // 2.1 If the cell is dead and have exactly 3 live cells neighbours becomes a live cell
                    if (cur_gen[i][j] == 0) {
                        if (countLiveCell(cur_gen, i, j) == 3) {
                            next_gen[i][j] = 1;
                        }
                    } else { // 2.2 If the cell is live cell
                        // (1) If live cell with fewer than two live neighbours dies
                        if (countLiveCell(cur_gen, i, j) < 2) {
                            next_gen[i][j] = 0;
                        // (2) If live cell with two or three live neighbours lives on to the next generation.
                        } else if (countLiveCell(cur_gen, i, j) > 3) {
                            next_gen[i][j] = 0;
                        }
                    }
                    // 2.3 Otherwise, keep the current state of the cell (nothing change)
                }
            }

            // store the next generation to the current generation to move to next step.
            copy2DimIntArray(next_gen, cur_gen);

            // do nothing but delay program some seconds to see the result of each time.
            Thread.sleep(1000);


        }

    }

    /**
     * using system array copy to copy 2 dimension arrays
     *
     * @param src the source array.
     * @param dest the destination array.
     */
    public static void copy2DimIntArray(int[][] src, int[][] dest) {
        int dim_1 = src.length;

        int dim_2 = src[0].length;

        for (int i = 0; i < dim_1; i++) {
            System.arraycopy(src[i], 0, dest[i], 0, dim_2);
        }
    }

    /**
     * Extract 3x3 array that around the given cell position.
     * This will be used to count live cell among 8 neighbours of the given position cell.
     *
     * @param src 2-dim array that contain the universe of the Game Of Life
     * @param dest 2-dim 3x3 array that contains the 8 neighbours and the current cell.
     * @param x the x position of the given cell
     * @param y the y position of the given cell
     */
    public static void copy3x3IntArray(int[][] src, int[][] dest, int x, int y) {
        int src_out_bound[][] = new int[10][10];

        // trick: create a bound array to count the neighbours cell more easy for boundary cells.
        for (int i = 0; i < src_out_bound.length; i++) {
            for (int j = 0; j < src_out_bound[i].length; j++) {
                src_out_bound[i][j] = 0;
            }
        }

        // copy the current state to the bound array.
        for (int i = 1; i < 8; i++) {
            System.arraycopy(src[i - 1], 0, src_out_bound[i], 1, src[0].length);
        }

        // extract 2-dim 3x3 array of the 8 neighbours for the given cell.
        for (int i = 0; i < dest.length; i++) {
            System.arraycopy(src_out_bound[x + i], y, dest[i], 0, dest.length);
        }

    }

    /**
     * count all the live neighbours of a given position <code>x, y</code>.
     * That count will be used to determine the next state of the given cell.
     *
     * @param arr 2-dim array that contain the universe of the Game Of Life
     * @param x the x position of the given cell
     * @param y the y position of the given cell
     * @return return the total number of live neighbours of a given cell.
     */
    public static int countLiveCell(int[][] arr, int x, int y) {
        int dest[][] = new int[3][3];
        copy3x3IntArray(arr, dest, x, y);

        // set the given cell to 0 to not count the center cell.
        // only count the 8 neighbours
        dest[1][1] = 0;

        return count(dest);
    }

    /**
     * Using sum to count all the live neighbours
     *
     * @param arr array of 3x3 that contains all the neighbours and the cell itself
     * @return the total number of live neighbours of a cell.
     */
    public static int count(int[][] arr) {
        int sum = 0;

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                sum += arr[i][j];
            }
        }
        return sum;
    }


    /**
     * Print out 2 dim array to see the current state of the system.
     *
     * @param arr the input 2-dim array that store the state of the universe of the Game of Life.
     */
    public static void printArray(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (arr[i][j] == 1) {
                    // printout the live cell by black square character, ◾
                    System.out.print("◾");
                } else {
                    // print out the dead cell by white square character, ◽
                    System.out.print("◽");
                }
            }
            System.out.println();
        }
    }
}
