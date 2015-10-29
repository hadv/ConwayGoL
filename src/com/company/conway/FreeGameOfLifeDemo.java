package com.company.conway;

import java.util.BitSet;

/**
 * Outline: Conway's Game Of Life
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
 * @author Dang Viet Ha (dvietha@gmail.com)
 */
public class FreeGameOfLifeDemo {
    /**
     * Running Game Of Life demo with some seed pattern.
     *
     * @param args input arguments for the program
     */
    public static void main(String[] args) {
        // Glider
        final byte gliderSeed[][] = {
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0},
                {0, 1, 0, 1, 0 ,0},
                {0, 0, 1, 1, 0 ,0},
                {0, 0, 0, 0, 0, 0}
        };



        // Initialize the Game Of Life with a given seed
        FreeGameOfLife life = new FreeGameOfLife(gliderSeed);

        while (true) {
            // Print out the current state of the system
            System.out.println(life.toString());

            // Transition to the next generation by applying the rule
            life.nextGeneration();

            // Do nothing but delay program some seconds to see the result of each step time.
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

    private int horizontal;

    private int vertical;

    /**
     * Initialize the current state of the system with a given seed.
     *
     * @param seedOfTheSystem seed of the system
     * @throws UnsupportedOperationException throw {@link UnsupportedOperationException}
     *                                       if the input {@code null} value for {@code seedOfTheSystem}
     */
    public FreeGameOfLife(final byte[][] seedOfTheSystem) {
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
     * <p>Transition to the next generation by applying the Conway's Game Of Life rule.
     * <ol>
     *     <li>Any live cell with fewer than two live neighbours dies, as if caused by under-population.
     *     <li>Any live cell with two or three live neighbours lives on to the next generation.
     *     <li>Any live cell with more than three live neighbours dies, as if by overcrowding.
     *     <li>Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
     * </ol>
     */
    public void nextGeneration() {
        // At each step time, looping all cells in the current generation to apply the rules
        for (int i = 0; i < vertical; i++) {
            for (int j = 0; j < horizontal; j++) {
                int idx = getIndex(i, j);
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

        // After each step then extend the grid if need
        extendGrid();
        tempGeneration = new BitSet(vertical * horizontal);
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
        int maxX = x >= vertical - 1 ? vertical - 1 : x + 1;
        int minY = y <= 0 ? 0 : y - 1;
        int maxY = y >= horizontal - 1 ? horizontal - 1 : y + 1;

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
     * Calculate the index of the {@code BitSet} from input grid cell.
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
     * @param seed seed of the system
     */
    private void initSystemState(final byte[][] seed) {
        currentGeneration = new BitSet(vertical * horizontal);
        for (int i = 0; i < vertical; i++) {
            for (int j = 0; j < horizontal; j++) {
                if (seed[i][j] == 1) {
                    currentGeneration.set(getIndex(i, j));
                }
            }
        }
        // Extend the grid of the system if need
        extendGrid();
        tempGeneration = new BitSet(vertical * horizontal);
    }

    /**
     * Extend the grid after each time step of the system.
     */
    private void extendGrid() {
        if (shouldExtendEast()) {
            extendEast();
        }

        if (shouldExtendNorth()) {
            extendNorth();
        }

        if (shouldExtendSouth()) {
            extendSouth();
        }

        if (shouldExtendWest()) {
            extendWest();
        }
    }

    /**
     * Check if the current grid need to extend outward to north or not.
     * <p>If on the north border of the grid have 3 or more live cells adjacent,
     * the the grid need to extend one to the north.
     *
     * @return {@code true} if need extend to the north; otherwise {@code false}
     */
    private boolean shouldExtendNorth() {
        byte count = 0;
        for (int i = 0; i < horizontal; i++) {
            if (currentGeneration.get(getIndex(0, i))) {
                count++;
                if (count == 3) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }

    /**
     * Check if the current grid need to extend outward to south or not.
     * <p>If on the south border of the grid have 3 or more live cells adjacent,
     * the the grid need to extend one to the south.
     *
     * @return {@code true} if need extend to the south; otherwise {@code false}
     */
    private boolean shouldExtendSouth() {
        byte count = 0;
        for (int i = 0; i < horizontal; i++) {
            if (currentGeneration.get(getIndex(vertical - 1, i))) {
                count++;
                if (count == 3) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }

    /**
     * Check if the current grid need to extend outward to west or not.
     * <p>If on the west border of the grid have 3 or more live cells adjacent,
     * the the grid need to extend one to the north.
     *
     * @return {@code true} if need extend to the west; otherwise {@code false}
     */
    private boolean shouldExtendWest() {
        byte count = 0;
        for (int i = 0; i < vertical; i++) {
            if (currentGeneration.get(getIndex(i, 0))) {
                count++;
                if (count == 3) {
                    return true;
                }

            } else {
                count = 0;
            }
        }
        return false;
    }

    /**
     * Check if the current grid need to extend outward to east or not.
     * <p>If on the east border of the grid have 3 or more live cells adjacent,
     * the the grid need to extend one to the north.
     *
     * @return {@code true} if need extend to the east; otherwise {@code false}
     */
    private boolean shouldExtendEast() {
        byte count = 0;
        for (int i = 0; i < vertical; i++) {
            if (currentGeneration.get(getIndex(i, horizontal - 1))) {
                count++;
                if (count == 3) {
                    return true;
                }

            } else {
                count = 0;
            }
        }
        return false;
    }

    /**
     * Extend the grid to the north.
     */
    private void extendNorth() {
        byte[] b = toByteArray();
        byte[] bb = new byte[b.length + horizontal];
        System.arraycopy(b, 0, bb, horizontal, b.length);
        vertical++;
        currentGeneration = valueOf(bb);
    }

    /**
     * Extend the grid to the south.
     */
    private void extendSouth() {
        byte[] b = toByteArray();
        byte[] bb = new byte[b.length + horizontal];
        System.arraycopy(b, 0, bb, 0, b.length);
        vertical++;
        currentGeneration = valueOf(bb);
    }

    /**
     * Extend the grid to the west.
     */
    private void extendWest() {
        byte[] b = toByteArray();
        byte[] bb = new byte[b.length + vertical];

        for (int i = 0; i < vertical; i++) {
            System.arraycopy(b, i * horizontal, bb, (i * (horizontal + 1)) + 1, horizontal);
        }
        horizontal++;
        currentGeneration = valueOf(bb);
    }

    /**
     * Extend the grid to the east.
     */
    private  void extendEast() {
        byte[] b = toByteArray();
        byte[] bb = new byte[b.length + vertical];

        for (int i = 0; i < vertical; i++) {
            System.arraycopy(b, i * horizontal, bb, i * (horizontal + 1), horizontal);
        }
        horizontal++;
        currentGeneration = valueOf(bb);
    }

    /**
     * Convert the current state to {@code byte[]} array.
     * @return The {@code byte[]} array present the current state of the system.
     */
    private byte[] toByteArray() {
        int size = vertical * horizontal;
        byte[] temp = new byte[size];
        for (int i = 0; i < size; i++) {
            if (currentGeneration.get(i)) {
                temp[i] = 1;
            } else {
                temp[i] = 0;
            }
        }
        return  temp;
    }

    /**
     * Create BitSet from a given {@code byte[]} array.
     * @param bytes input byte array
     * @return The BitSet that created from {@code byte[]} array.
     */
    private BitSet valueOf(final byte[] bytes) {
        int size = horizontal * vertical;
        BitSet bs = new BitSet(size);

        for (int i = 0; i < size; i++) {
            if (bytes[i] == 1) {
                bs.set(i);
            }
        }
        return  bs;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < vertical; i++) {
            for (int j = 0; j < horizontal; j++) {
                int idx = getIndex(i, j);
                if (currentGeneration.get(idx)) {
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
