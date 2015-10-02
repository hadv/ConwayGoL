package com.company;

import java.util.HashMap;
import java.util.Map;

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
public class ConwayGOL {
    /**
     * Running Game Of Life demo with some seed pattern.
     *
     * @param args input arguments for the program
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        // Glider
        final byte gliderSeed[][] = {
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0},
                {0, 1, 0, 1, 0, 0},
                {0, 0, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };

        // Initialize the Game Of Life with a given seed
        ConwayGameOfLife life = new ConwayGameOfLife(gliderSeed);

        while (true) {
            // Print out the current state of the system
            System.out.println(life.toString());

            // Transition to the next generation by applying the rule
            life.evolve();

            // Do nothing but delay program some seconds to see the result of each step time.
            Thread.sleep(1000);
        }
    }
}

/**
 *
 */
class ConwayGameOfLife {
    // Storing state of the current generation system
    private Map<Point, Byte> currentGeneration;

    // Using to store temporarily the state of the next generation
    // to avoid allocating new generation at each step time
    private Map<Point, Byte> tempGeneration;

    // The right border of the grid
    private int horizontalRight;

    // The left border of the grid
    private int horizontalLeft;

    // The top border of the grid
    private int verticalTop;

    // The bottom border of the grid
    private int verticalBottom;

    // The constant 1 value to present the live cell
    private static final byte LIVE_CELL_VAL = 1;

    /**
     * Initialize the current state of the system with a given seed.
     *
     * @param seedOfTheSystem seed of the system
     * @throws UnsupportedOperationException throw {@link UnsupportedOperationException}
     * if the input {@code null} value for {@code seedOfTheSystem}
     */
    public ConwayGameOfLife(final byte[][] seedOfTheSystem) {
        if (seedOfTheSystem == null) {
            throw new UnsupportedOperationException();
        }

        verticalTop = 0;
        verticalBottom = seedOfTheSystem.length - 1;
        if (verticalBottom < 1) {
            throw new UnsupportedOperationException();
        }

        horizontalLeft = 0;
        horizontalRight = seedOfTheSystem[0].length - 1;
        if (horizontalRight < 1) {
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
    public void evolve() {
        // At each step time, looping all live cells in the current generation to evolve
        for (Map.Entry<Point, Byte> entry : currentGeneration.entrySet()) {
            Point p = entry.getKey();
            byte liveCellNeighbours = countLiveNeighbourCells(p);
            // If live cell with fewer than two live neighbours dies
            // If live cell with more than three live neighbours dies, as if by overcrowding.
            if (liveCellNeighbours < 2 || liveCellNeighbours > 3)
                tempGeneration.remove(p);
            else // Otherwise, keep the current state of the live cell
                tempGeneration.put(p, entry.getValue());

            // Check if any dead cells around current live cell will become live cell or not
            for (int i = p.getX() - 1; i <= p.getX() + 1; i++) {
                for (int j = p.getY() - 1; j <= p.getY() + 1; j++) {
                    Point pt = new Point(i, j);
                    if (currentGeneration.get(pt) == null) {
                        if (countLiveNeighbourCells(pt) == 3) {
                            tempGeneration.put(pt, LIVE_CELL_VAL);
                        }
                    }
                }
            }
        }
        // Swap the next generation to the current generation for the next step time
        Map<Point, Byte> map = currentGeneration;
        currentGeneration = tempGeneration;
        tempGeneration = map;
        tempGeneration.clear();

        // After each step then extend the grid if need
        extendGrid();
    }

    /**
     * Count the live cell neighbours to given cell.
     *
     * @param point The position of the cell
     * @return The total number of live cell neighbours to the given poistion cell
     */
    private byte countLiveNeighbourCells(final Point point) {

        byte count = 0;
        for (int i = point.getX() - 1; i <= point.getX() + 1; i++) {
            for (int j = point.getY() - 1; j <= point.getY() + 1; j++) {
                if (currentGeneration.get(new Point(i, j)) != null) count++;
            }
        }

        if (currentGeneration.get(point) != null) count--;
        return count;
    }

    /**
     * Set the current state of the system from given seed.
     *
     * @param seed seed of the system
     */
    private void initSystemState(final byte[][] seed) {
        currentGeneration = new HashMap<Point, Byte>();
        tempGeneration = new HashMap<Point, Byte>();
        for (int i = verticalTop; i <= verticalBottom; i++) {
            for (int j = horizontalLeft; j <= horizontalRight; j++) {
                if (seed[i][j] == 1) {
                    Point p = new Point(i, j);
                    currentGeneration.put(p, LIVE_CELL_VAL);
                }
            }
        }
        // Extend the grid of the system if need
        extendGrid();
    }

    /**
     * Extend the grid after each time step of the system.
     */
    private void extendGrid() {
        if (shouldExtendEast()) {
            horizontalRight++;
        }

        if (shouldExtendNorth()) {
            verticalTop--;
        }

        if (shouldExtendSouth()) {
            verticalBottom++;
        }

        if (shouldExtendWest()) {
            horizontalLeft--;
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
        for (int i = horizontalLeft; i <= horizontalRight; i++) {
            Point p = new Point(verticalTop, i);
            if (currentGeneration.get(p) != null) {
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
        for (int i = horizontalLeft; i <= horizontalRight; i++) {
            Point p = new Point(verticalBottom, i);
            if (currentGeneration.get(p) != null) {
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
        for (int i = verticalTop; i <= verticalBottom; i++) {
            Point p = new Point(i, horizontalLeft);
            if (currentGeneration.get(p) != null) {
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
        for (int i = verticalTop; i <= verticalBottom; i++) {
            Point p = new Point(i, horizontalRight);
            if (currentGeneration.get(p) != null) {
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = verticalTop; i <= verticalBottom; i++) {
            for (int j = horizontalLeft; j <= horizontalRight; j++) {
                Point p = new Point(i, j);
                if (currentGeneration.get(p) != null) {
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

/**
 *
 */
class Point {
    private int x;

    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (x != point.x) return false;
        if (y != point.y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
