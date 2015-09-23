package com.company;

import java.security.InvalidAlgorithmParameterException;
import java.util.BitSet;

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
 *          (2) If live cell with more than three live neighbours dies, as if by overcrowding.
 *      2.3 Otherwise, keep the current state of the cell (nothing change)
 *      2.4 Print out the current state of the system to console.
 *
 */
public class Main {

    /**
     * Running Game Of Life demo with some seed pattern.
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args)
            throws InterruptedException {

        // Blinker (period 2)
        byte blinkerSeed[][] = {
                {0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0}
        };

        // Beacon (period 2)
        byte beaconSeed[][] = {
                {0, 0, 0, 0, 0, 0},
                {0, 1, 1, 0, 0, 0},
                {0, 1, 1, 0, 0, 0},
                {0, 0, 0, 1, 1, 0},
                {0, 0, 0, 1, 1, 0},
                {0, 0, 0, 0, 0, 0}
        };

        // Initialize the Game Of Life with a given seed
        GameOfLife life = new GameOfLife(beaconSeed);

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
class GameOfLife {

    // Store the state of the current generation
    private BitSet currentGeneration;

    private int horizontal;

    private int vertical;

    /**
     * Initialize the current state of the system with a given seed
     * @param seedOfTheSystem   seed of the system
     * @throws UnsupportedOperationException throw <code>UnsupportedOperationException</code>
     *          if the input null value for <code>seedOfTheSystem</code>
     */
    public GameOfLife(byte[][] seedOfTheSystem) {
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
        currentGeneration = new BitSet(vertical*horizontal);
        setCurrentGeneration(seedOfTheSystem);
    }

    /**
     * Transition to the next generation by applying the below rule
     * <ul>
     *   <li>Any live cell with fewer than two live neighbours dies, as if caused by under-population.</li>
     *   <li>Any live cell with two or three live neighbours lives on to the next generation.</li>
     *   <li>Any live cell with more than three live neighbours dies, as if by overcrowding.</li>
     *   <li>Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.</li>
     * </ul>
     */
    public void nextGeneration() {
        BitSet nextGeneration = new BitSet(vertical*horizontal);
        // At each step time, looping all cells in the current generation to apply the rules
        for (int i = 0; i < vertical; i++) {
            for (int j = 0; j < horizontal; j++) {
                byte liveCellNeighbours = countLiveNeighbourCells(i, j);
                // If the cell is dead and have exactly 3 live cells neighbours becomes a live cell
                if (!currentGeneration.get(getIndex(i, j))) {
                    if (liveCellNeighbours == 3) {
                        nextGeneration.set(getIndex(i, j));
                    }
                } else { // If the cell is live cell
                    // If live cell with fewer than two live neighbours dies
                    // If live cell with more than three live neighbours dies, as if by overcrowding.
                    if (liveCellNeighbours < 2 || liveCellNeighbours > 3) {
                        nextGeneration.clear(getIndex(i, j));
                    } else { // Otherwise, keep the current state of the cell
                        nextGeneration.set(getIndex(i, j), currentGeneration.get(getIndex(i, j)));
                    }
                }
            }
        }

        // Store the next generation to the current generation
        currentGeneration = nextGeneration;
    }

    /**
     * Count the live cell neighbours to given cell
     * @param x the x position of the cell
     * @param y the y position of the cell
     * @return the total number of live cell neighbours to the given cell
     */
    private byte countLiveNeighbourCells(final int x, final int y) {

        int minX = x <= 0 ? 0 : x - 1;
        int maxX = x >= vertical - 1 ? vertical - 1: x + 1;
        int minY = y <= 0 ? 0 : y - 1;
        int maxY = y >= horizontal - 1 ? horizontal - 1: y + 1;

        byte count = 0;
        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                if (currentGeneration.get(getIndex(i, j))) {
                    count++;
                }
            }
        }
        if (currentGeneration.get(getIndex(x, y))) count--;
        return count;
    }

    /**
     * Calculate the index of the <code>BitSet</code> vector from input grid cell
     * @param x x position of the cell
     * @param y y position of the cell
     * @return the index of the cell in the equivalent BitSet
     */
    private int getIndex(final int x, final int y) {
        return (x * horizontal) + y;
    }

    /**
     * Set the current state of the system from given seed.
     * @param seed  seed of the system
     */
    private void setCurrentGeneration(byte[][] seed) {
        for (int i = 0; i < vertical; i++) {
            for (int j = 0; j < horizontal; j++) {
                if (seed[i][j] == 1)
                    currentGeneration.set(getIndex(i, j), true);
                else
                    currentGeneration.set(getIndex(i, j), false);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < vertical; i++) {
            for (int j = 0; j < horizontal; j++) {
                if (currentGeneration.get(getIndex(i, j))) {
                    // Present the live cell by black square character
                    builder.append("◾");
                } else {
                    // Present the dead cell by white square character
                    builder.append("◽");
                }
            }
            builder.append("\n");
        }

        return builder.toString();
    }
}
