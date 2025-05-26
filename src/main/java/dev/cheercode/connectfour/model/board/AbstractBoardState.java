package dev.cheercode.connectfour.model.board;

import dev.cheercode.connectfour.model.Disc;

import java.util.Arrays;

public abstract class AbstractBoardState implements BoardState {
    protected final int height;
    protected final int width;
    private final Disc[][] grid;
    private final int[] rowPosition;

    public AbstractBoardState(int height, int width) {
        this.height = height;
        this.width = width;
        this.grid = new Disc[height][width];
        this.rowPosition = new int[width];
        Arrays.fill(rowPosition, height - 1);
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
        checkBounds(row, column);
        checkExistence(row, column);
        return grid[row][column];
    }

    private void checkBounds(int row, int column) {
        if (row < 0 || row >= height || column < 0 || column >= width) {
            throw new IllegalArgumentException("Index out of grid bounds.");
        }
    }

    private void checkExistence(int row, int column) {
        if (grid[row][column] == null) {
            throw new IllegalArgumentException(String.format("Disk is not exists: (%d - %d)", row, column));
        }
    }

    @Override
    public void drop(int column, Disc disc) {
        if (isColumnFilled(column)) {
            throw new IllegalArgumentException("Column is filled: " + column);
        }
        int row = rowPosition[column];
        grid[row][column] = disc;
        rowPosition[column]--;
    }

    @Override
    public boolean isColumnFilled(int column) {
        checkBounds(column);
        return rowPosition[column] < 0;
    }

    @Override
    public boolean isBoardFilled() {
        for (int column = 0; column < getWidth(); column++) {
            if (!isColumnFilled(column)) {
                return false;
            }
        }
        return true;
    }

    private void checkBounds(int column) {
        if (column < 0 || column >= width) {
            throw new IllegalArgumentException("Column index out of grid bounds.");
        }
    }

    @Override
    public boolean isEmptySlot(int row, int column) {
        checkBounds(row, column);
        return grid[row][column] == null;
    }

    @Override
    public BoardState rotate(Direction direction) {
        return switch (direction) {
            case LEFT -> new LeftRotatedBoardState(this);
            case RIGHT -> new RightRotatedBoardState(this);
            case UPSIDE_DOWN -> new UpsideDownRotatedBoardState(this);
            default -> throw new IllegalArgumentException("Unsupported direction: " + direction.name());
        };
    }
}
