package com.company;

import java.util.BitSet;

/**
 * Outline: Conway Game Of Life
 *
 * At each step in time, looping all cells in the current generation.
 *      1. If the cell is dead and have exactly 3 live cells neighbours becomes a live cell
 *      2. If the cell is live cell
 *          (a) If live cell with fewer than two live neighbours dies
 *          (b) If live cell with more than three live neighbours dies, as if by overcrowding.
 *      3. Otherwise, keep the current state of the cell (nothing change)
 *      4. Print out the current state of the system to console.
 *
 * @author  Dang Viet Ha (dvietha@gmail.com)
 */
public class FreeGameOfLifeDemo {

    /**
     * Running Game Of Life demo with some seed pattern.
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        // Blinker (period 2)
        byte blinkerSeed[][] = {
                {0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0}
        };

        // Beacon (period 2)
        // 0: dead, 1: live, 2: out-of-map
        byte beaconSeed[][] = {
                {0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2},
                {0, 1, 1, 0, 0, 0, 2, 2, 2, 2, 2, 2},
                {0, 1, 1, 0, 0, 0, 2, 2, 2, 2, 2, 2},
                {0, 0, 0, 1, 1, 0, 2, 2, 2, 2, 2, 2},
                {0, 0, 0, 1, 1, 0, 2, 2, 2, 2, 2, 2},
                {0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2},
                {2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0},
                {2, 2, 2, 2, 2, 2, 0, 1, 1, 0, 0, 0},
                {2, 2, 2, 2, 2, 2, 0, 1, 1, 0, 0, 0},
                {2, 2, 2, 2, 2, 2, 0, 0, 0, 1, 1, 0},
                {2, 2, 2, 2, 2, 2, 0, 0, 0, 1, 1, 0},
                {2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0},
        };

        // Initialize the Game Of Life with a given seed
        FreeGameOfLife life = new FreeGameOfLife(beaconSeed);

        while (true) {
            // Print out the current state of the system
            System.out.println(life.toString());

            // Transition to the next generation by applying the rule
            life.nextGeneration();

            // Do nothing but delay program some seconds to see the result of each step time.
            Thread.sleep(1000);
        }
    }
}

/**
 *
 */
class FreeGameOfLife {

    // Storing state of the current generation system
    private BitSet currentGeneration;

    // Using to store temporarily the state of the next generation
    // to avoid allocating new generation at each step time
    private BitSet tempGeneration;

    // Storing the grid map of the system to determine the cell is in or out of the map.
    private BitSet gridMap;

    private int horizontal;

    private int vertical;

    /**
     * Initialize the current state of the system with a given seed
     *
     * @param seedOfTheSystem   seed of the system
     * @throws UnsupportedOperationException throw <code>UnsupportedOperationException</code>
     *          if the input null value for <code>seedOfTheSystem</code>
     */
    public FreeGameOfLife(byte[][] seedOfTheSystem) {
        if (seedOfTheSystem == null) {
            throw new UnsupportedOperationException();
        }
        vertical = seedOfTheSystem.length;
        if (vertical < 1) {
            throw new UnsupportedOperationException();
        }

        horizontal = seedOfTheSystem[0].length;
        if (horizontal < 1) {
            throw new UnsupportedOperationException();
        }
        initSystemState(seedOfTheSystem);
    }

    /**
     * Transition to the next generation by applying the below rule
     *
     * <ul>
     *   <li>Any live cell with fewer than two live neighbours dies, as if caused by under-population.</li>
     *   <li>Any live cell with two or three live neighbours lives on to the next generation.</li>
     *   <li>Any live cell with more than three live neighbours dies, as if by overcrowding.</li>
     *   <li>Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.</li>
     * </ul>
     */
    public void nextGeneration() {
        // At each step time, looping all cells in the current generation to apply the rules
        for (int i = 0; i < vertical; i++) {
            for (int j = 0; j < horizontal; j++) {
                int idx = getIndex(i, j);

                // if the cell is out of map then move to next cell
                if (!gridMap.get(idx)) continue;

                byte liveCellNeighbours = countLiveNeighbourCells(i, j);

                // If the cell is dead and have exactly 3 live cells neighbours becomes a live cell
                if (!currentGeneration.get(idx)) {
                    if (liveCellNeighbours == 3)
                        tempGeneration.set(idx);
                } else { // If the cell is live cell
                    // If live cell with fewer than two live neighbours dies
                    // If live cell with more than three live neighbours dies, as if by overcrowding.
                    if (liveCellNeighbours < 2 || liveCellNeighbours > 3)
                        tempGeneration.clear(idx);
                    else // Otherwise, keep the current state of the live cell
                        tempGeneration.set(idx);
                }
            }
        }
        // Swap the next generation to the current generation for the next step time
        BitSet bs = currentGeneration;
        currentGeneration = tempGeneration;
        tempGeneration = bs;
    }

    /**
     * Count the live cell neighbours to given cell
     *
     * @param x The x position of the cell
     * @param y The y position of the cell
     * @return he total number of live cell neighbours to the given cell
     */
    private byte countLiveNeighbourCells(final int x, final int y) {

        int minX = x <= 0 ? 0 : x - 1;
        int maxX = x >= vertical - 1 ? vertical - 1: x + 1;
        int minY = y <= 0 ? 0 : y - 1;
        int maxY = y >= horizontal - 1 ? horizontal - 1: y + 1;

        byte count = 0;
        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                int idx = getIndex(i, j);
                if (currentGeneration.get(idx) && gridMap.get(idx)) {
                    count++;
                }
            }
        }
        if (currentGeneration.get(getIndex(x, y))) count--;
        return count;
    }

    /**
     * Calculate the index of the <code>BitSet</code> vector from input grid cell
     *
     * @param x The x position of the cell
     * @param y The y position of the cell
     * @return The index of the cell in the equivalent BitSet
     */
    private int getIndex(final int x, final int y) {
        return (x * horizontal) + y;
    }

    /**
     * Set the current state of the system from given seed.
     *
     * @param seed  seed of the system
     */
    private void initSystemState(byte[][] seed) {
        currentGeneration = new BitSet(vertical * horizontal);
        tempGeneration = new BitSet(vertical * horizontal);
        gridMap = new BitSet(vertical * horizontal);
        for (int i = 0; i < vertical; i++) {
            for (int j = 0; j < horizontal; j++) {
                int s = seed[i][j];
                int idx = getIndex(i, j);
                if (s == 1)
                    currentGeneration.set(idx);

                if (s == 0 || s == 1) {
                    gridMap.set(idx);
                }
            }
        }
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < vertical; i++) {
            for (int j = 0; j < horizontal; j++) {
                int idx = getIndex(i, j);

                if (!gridMap.get(idx)) {
                    // Present the out-of-map by blank character
                    builder.append(" ");
                } else {
                    if (currentGeneration.get(idx)) {
                        // Present the live cell by black square character
                        builder.append("◾");
                    } else {
                        // Present the dead cell by white square character
                        builder.append("◽");
                    }
                }
            }
            builder.append("\n");
        }

        return builder.toString();
    }
}
