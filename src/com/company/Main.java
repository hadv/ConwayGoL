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

            // do nothing but delay program some seconds to see the result of each step time.
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
     * @throws InvalidAlgorithmParameterException throw <code>InvalidAlgorithmParameterException</code> if the input
     * null value for <code>seedOfTheSystem</code>
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
     *
     */
    public void nextGeneration() {
        byte[][] boundary = makeBoundaryGrid();
        byte[][] nextGeneration = new byte[vertical][horizontal];

        for (int i = 0; i < vertical; i++) {
            System.arraycopy(currentGeneration[i], 0, nextGeneration[i], 0, horizontal);
        }

        // At each step time, looping all cells in the current generation to apply the rules
        for (int i = 0; i < vertical; i++) {
            for (int j = 0; j < horizontal; j++) {
                byte liveCellNeighbours = countLiveNeighbourCells(boundary, i, j);
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
                    }
                    // Otherwise, keep the current state of the cell (nothing change)
                }
            }
        }

        // Store the next generation to the current generation
        for (int i = 0; i < vertical; i++) {
            System.arraycopy(nextGeneration[i], 0, currentGeneration[i], 0, horizontal);
        }
    }

    /**
     *
     * @return
     */
    private byte[][] makeBoundaryGrid() {

        byte[][] boundary = new byte[vertical + 2][horizontal + 2];

        // copy the current state to the boundary array.
        for (int i = 1; i <= vertical; i++) {
            System.arraycopy(currentGeneration[i - 1], 0, boundary[i], 1, horizontal);
        }

        return boundary;
    }

    /**
     *
     * @param boundary
     * @param x
     * @param y
     * @return
     */
    private byte countLiveNeighbourCells(byte[][] boundary, int x, int y) {
        byte neighbours[][] = new byte[3][3];

        // extract 2-dim 3x3 array of the 8 neighbours for the given cell.
        for (int i = 0; i < 3; i++) {
            System.arraycopy(boundary[x + i], y, neighbours[i], 0, 3);
        }
        neighbours[1][1] = 0;

        byte sum = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sum += neighbours[i][j];
            }
        }

        return sum;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < vertical; i++) {
            for (int j = 0; j < horizontal; j++) {
                if (currentGeneration[i][j] == 1) {
                    // present the live cell by black square character, ◾
                    builder.append("◾");
                } else {
                    // present out the dead cell by white square character, ◽
                    builder.append("◽");
                }
            }
            builder.append("\n");
        }

        return builder.toString();
    }
}
