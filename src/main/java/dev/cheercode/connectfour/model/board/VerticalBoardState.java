package dev.cheercode.connectfour.model.board;

import dev.cheercode.connectfour.model.Disc;

import java.util.Arrays;

public class VerticalBoardState implements BoardState {
    private final int height;
    private final int width;
    private Disc[][] grid;
    private int[] rowPosition;

    public VerticalBoardState(Board.Size size) {
        this.height = size.getWidth();
        this.width = size.getHeight();
        this.grid = new Disc[height][width];
        this.rowPosition = new int[width];
        Arrays.fill(rowPosition, height - 1);
    }

    public VerticalBoardState(BoardState previous, Direction direction) {
        if (direction == Direction.UPSIDE_DOWN) {
            this.height = previous.getHeight();
            this.width = previous.getWidth();
        } else {
            this.height = previous.getWidth();
            this.width = previous.getHeight();
        }
        this.grid = new Disc[height][width];
        this.rowPosition = new int[width];
        Arrays.fill(rowPosition, height - 1);
        init(previous, direction);
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public Disc get(int row, int column) {
        if (isEmptySlot(row, column)) {
            throw new IllegalArgumentException(String.format("Slot is empty (%d - %d)", row, column));
        }
        return grid[row][column];
    }

    @Override
    public boolean isEmptySlot(int row, int column) {
        return grid[row][column] == null;
    }

    @Override
    public void drop(int column, Disc disc) {
        if (isColumnFilled(column)) {
            throw new IllegalArgumentException("Column are filled: " + column);
        }
        int row = rowPosition[column];
        grid[row][column] = disc;
        rowPosition[column]--;
    }

    @Override
    public boolean isColumnFilled(int column) {
        return rowPosition[column] == -1;
    }

    @Override
    public void init(BoardState previous, Direction direction) {
        int previousHeight = previous.getHeight();
        int previousWidth = previous.getWidth();
        if (direction == Direction.LEFT) {
            for (int previousRow = 0; previousRow < previousHeight; previousRow++) {
                for (int previousColumn = 0; previousColumn < previousWidth; previousColumn++) {
                    try {
                        Disc previousDisc = previous.get(previousRow, previousColumn);
                        drop(previousRow, previousDisc);
                    } catch (IllegalArgumentException ignored) {
                        // skip an empty slot
                    }
                }
            }
        }
        if (direction == Direction.RIGHT) {
            for (int previousRow = 0; previousRow < previousHeight; previousRow++) {
                for (int previousColumn = previousWidth - 1; previousColumn >= 0; previousColumn--) {
                    try {
                        Disc previousDisc = previous.get(previousRow, previousColumn);
                        drop(width - 1 - previousRow, previousDisc);
                    } catch (IllegalArgumentException ignored) {
                        // skip an empty slot
                    }
                }
            }
        }
    }

    @Override
    public void turnUpsideDown(BoardState state) {
        Disc[][] previousGrid = grid;
        this.grid = new Disc[height][width];
        this.rowPosition = new int[width];
        Arrays.fill(rowPosition, height - 1);
        for (int row = 0; row < height; row++) {
            for (int column = width - 1; column >= 0; column--) {
                Disc previousDisc = previousGrid[row][column];
                if (previousDisc != null) {
                    drop(width - 1 - column, previousDisc);
                }
            }
        }
    }
}
