package com.company;

import java.security.InvalidAlgorithmParameterException;

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
     *
     * @param args
     * @throws InterruptedException
     * @throws InvalidAlgorithmParameterException
     */
    public static void main(String[] args)
            throws InterruptedException, InvalidAlgorithmParameterException {

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
    private byte currentGeneration[][];

    private int horizontal;

    private int vertical;

    /**
     *
     * @param seedOfTheSystem   seed of the system
     * @throws InvalidAlgorithmParameterException throw <code>InvalidAlgorithmParameterException</code>
     *          if the input null value for <code>seedOfTheSystem</code>
     */
    public GameOfLife(byte[][] seedOfTheSystem) throws InvalidAlgorithmParameterException {
        if (seedOfTheSystem == null) {
            throw new InvalidAlgorithmParameterException();
        }
        currentGeneration = seedOfTheSystem;
        vertical = currentGeneration.length;
        if (vertical < 1) {
            throw new InvalidAlgorithmParameterException();
        }

        horizontal = currentGeneration[0].length;
        if (horizontal < 1) {
            throw new InvalidAlgorithmParameterException();
        }
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
        byte[][] nextGeneration = new byte[vertical][horizontal];

        // At each step time, looping all cells in the current generation to apply the rules
        for (int i = 0; i < vertical; i++) {
            for (int j = 0; j < horizontal; j++) {
                byte liveCellNeighbours = countLiveNeighbourCells(i, j);
                // If the cell is dead and have exactly 3 live cells neighbours becomes a live cell
                if (currentGeneration[i][j] == 0) {
                    if (liveCellNeighbours == 3) {
                        nextGeneration[i][j] = 1;
                    }
                } else { // If the cell is live cell
                    // If live cell with fewer than two live neighbours dies
                    // If live cell with more than three live neighbours dies, as if by overcrowding.
                    if (liveCellNeighbours < 2 || liveCellNeighbours > 3) {
                        nextGeneration[i][j] = 0;
                    } else { // Otherwise, keep the current state of the cell
                        nextGeneration[i][j] = currentGeneration[i][j];
                    }
                }
            }
        }

        // Store the next generation to the current generation
        currentGeneration = nextGeneration;
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    private byte countLiveNeighbourCells(final int x, final int y) {

        int minX = x <= 0 ? 0 : x - 1;
        int maxX = x >= vertical - 1 ? vertical - 1: x + 1;
        int minY = y <= 0 ? 0 : y - 1;
        int maxY = y >= horizontal - 1 ? horizontal - 1: y + 1;

        byte sum = 0;
        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                sum += currentGeneration[i][j];
            }
        }
        sum -= currentGeneration[x][y];
        return sum;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < vertical; i++) {
            for (int j = 0; j < horizontal; j++) {
                if (currentGeneration[i][j] == 1) {
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
