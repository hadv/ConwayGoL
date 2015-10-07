package com.company.conway;

import java.util.BitSet;

/**
 * Outline: Conway Game Of Life
 * <p>
 *     The universe of the Game of Life is an infinite two-dimensional orthogonal grid of square cells,
 *     each of which is in one of two possible states, alive or dead.
 *     Every cell interacts with its eight neighbours, which are the cells
 *     that are horizontally, vertically, or diagonally adjacent.
 *     At each step in time, the following transitions occur:
 * <ol>
 *     <li>Any live cell with fewer than two live neighbours dies, as if caused by under-population.
 *     <li>Any live cell with two or three live neighbours lives on to the next generation.
 *     <li>Any live cell with more than three live neighbours dies, as if by over-population.
 *     <li>Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
 * </ol>
 * <p>
 *     The initial pattern constitutes the seed of the system.
 *     The first generation is created by applying the above rules simultaneously
 *     to every cell in the seed-births and deaths occur simultaneously,
 *     and the discrete moment at which this happens is sometimes called a tick
 *     (in other words, each generation is a pure function of the preceding one).
 *     The rules continue to be applied repeatedly to create further generations.
 *
 * @author  Dang Viet Ha (dvietha@gmail.com)
 */
public class Main {
    /**
     * Running Game Of Life demo with some seed pattern.
     *
     * @param args input arguments for the program
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        // Beacon (period 2)
        final byte beaconSeed[][] = {
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
    // Storing state of the current generation system
    private BitSet currentGeneration;

    // Using to store temporarily the state of the next generation
    // to avoid allocating new generation at each step time
    private BitSet tempGeneration;

    final private int horizontal;

    final private int vertical;

    /**
     * Initialize the current state of the system with a given seed.
     *
     * @param seedOfTheSystem   seed of the system
     * @throws UnsupportedOperationException throw {@link UnsupportedOperationException}
     *          if the input {@code null} value for {@code seedOfTheSystem}
     */
    public GameOfLife(final byte[][] seedOfTheSystem) {
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

        currentGeneration = new BitSet(vertical * horizontal);
        tempGeneration = new BitSet(vertical * horizontal);
        setCurrentGeneration(seedOfTheSystem);
    }

    /**
     * <p>Transition to the next generation by applying the Conway's Game Of Life rule.
     *
     * <ol>
     *   <li>Any live cell with fewer than two live neighbours dies, as if caused by under-population.
     *   <li>Any live cell with two or three live neighbours lives on to the next generation.
     *   <li>Any live cell with more than three live neighbours dies, as if by overcrowding.
     *   <li>Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
     * </ol>
     */
    public void nextGeneration() {
        // At each step time, looping all cells in the current generation to apply the rules
        for (int i = 0; i < vertical; i++) {
            for (int j = 0; j < horizontal; j++) {
                byte liveCellNeighbours = countLiveNeighbourCells(i, j);
                // If the cell is dead and have exactly 3 live cells neighbours becomes a live cell
                if (!currentGeneration.get(getIndex(i, j))) {
                    if (liveCellNeighbours == 3) {
                        tempGeneration.set(getIndex(i, j));
                    }
                } else { // If the cell is live cell
                    // If live cell with fewer than two live neighbours dies
                    // If live cell with more than three live neighbours dies, as if by overcrowding.
                    if (liveCellNeighbours < 2 || liveCellNeighbours > 3) {
                        tempGeneration.clear(getIndex(i, j));
                    } else { // Otherwise, keep the current state of the cell
                        tempGeneration.set(getIndex(i, j), currentGeneration.get(getIndex(i, j)));
                    }
                }
            }
        }
        // Swap the next generation to the current generation for the next step time
        BitSet bs = currentGeneration;
        currentGeneration = tempGeneration;
        tempGeneration = bs;
    }

    /**
     * Count the live cell neighbours to given cell.
     *
     * @param x The x position of the cell
     * @param y The y position of the cell
     * @return The total number of live cell neighbours to the given cell
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
     * Calculate the index of the {@link BitSet} from input grid cell.
     *
     * @param x The x position of the cell
     * @param y The y position of the cell
     * @return The index of the cell in the equivalent {@link BitSet}
     */
    private int getIndex(final int x, final int y) {
        return (x * horizontal) + y;
    }

    /**
     * Set the current state of the system from given seed.
     *
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
