package dev.cheercode.connectfour.model.board;

import dev.cheercode.connectfour.model.Disc;

public abstract class AbstractBoardState implements BoardState {
    protected final int height;
    protected final int width;
    protected final boolean[][] mask;
    private final Disc[][] grid;
    private final int[] rowPosition;

    public AbstractBoardState(int height, int width) {
        this.height = height;
        this.width = width;
        this.grid = new Disc[height][width];
        this.mask = new boolean[height][width];
        this.rowPosition = new int[width];
        initRowPositions(height, width, mask);
    }

    public AbstractBoardState(Board.Size size, boolean[][] mask) {
        this.height = size.getHeight();
        this.width = size.getWidth();
        this.grid = new Disc[height][width];
        this.mask = mask;
        this.rowPosition = new int[width];
        initRowPositions(height, width, mask);
    }

    private void initRowPositions(int height, int width, boolean[][] mask) {
        for (int column = 0; column < width; column++) {
            while (shouldMoveDownThroughDisableSlots(rowPosition[column], column, mask)) {
                rowPosition[column]++;
            }
            rowPosition[column] %= height;

            while (shouldMoveDownThroughEmptySlots(rowPosition[column], column, mask)) {
                rowPosition[column]++;
            }
            rowPosition[column]--;

            while (shouldMoveUpThroughDisableSlots(rowPosition[column], column, mask)) {
                rowPosition[column]--;
            }
        }
    }

    private boolean shouldMoveDownThroughDisableSlots(int row, int column, boolean[][] mask) {
        return row < mask.length && !mask[row][column];
    }

    private boolean shouldMoveDownThroughEmptySlots(int row, int column, boolean[][] mask) {
        return row < mask.length && mask[row][column];
    }

    private boolean shouldMoveUpThroughDisableSlots(int row, int column, boolean[][] mask) {
        return row >= 0 && !mask[row][column];
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
        while (rowPosition[column] >= 0 && !mask[rowPosition[column]][column]) {
            rowPosition[column]--;
        }
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

    @Override
    public boolean isOnField(int row, int column) {
        try {
            checkBounds(row, column);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return mask[row][column];
    }

    protected void applyGravity() {
        for (int column = 0; column < width; column++) {
            applyGravityTo(column);
            while (rowPosition[column] >= 0
                   && !mask[rowPosition[column]][column]) {
                rowPosition[column]--;
            }
        }
    }

    private void applyGravityTo(int column) {
        int bottom = findNextIndexOnFieldAfter(height, column);
        while (bottom >= 0) {
            int top = getTopFor(bottom, column);
            rowPosition[column] = bottom;
            while (bottom > top) {
                if (grid[bottom][column] != null) {
                    if (bottom != rowPosition[column]) {
                        grid[rowPosition[column]][column] = grid[bottom][column];
                        grid[bottom][column] = null;
                    }
                    rowPosition[column]--;
                }
                bottom--;
            }
            bottom = findNextIndexOnFieldAfter(top, column);
        }
    }

    private int getTopFor(int row, int column) {
        while (row >= 0 && mask[row][column]) {
            row--;
        }
        return row;
    }

    private int findNextIndexOnFieldAfter(int row, int column) {
        for (int j = row - 1; j >= 0; j--) {
            if (mask[j][column]) {
                return j;
            }
        }
        return -1;
    }

    protected void put(int row, int column, Disc disc) {
        grid[row][column] = disc;
    }
}

