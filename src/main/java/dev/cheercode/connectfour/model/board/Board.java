package dev.cheercode.connectfour.model.board;

import dev.cheercode.connectfour.model.Disc;

public class Board {
    private BoardState state;

    public Board(Size size) {
        this.state = new HorizontalBoardState(size);
    }

    public Disc get(int row, int column) {
        if (column < 0 || column >= getWidth()) {
            throw new IllegalArgumentException("Column number out of bound.");
        }
        if (row < 0 || row >= getHeight()) {
            throw new IllegalArgumentException("Row number out of bound.");
        }
        return state.get(row, column);
    }

    public void drop(int column, Disc disc) {
        if (column < 0 || column >= getWidth()) {
            throw new IllegalArgumentException("Column number out of bound.");
        }
        state.drop(column, disc);
    }

    public boolean isColumnFilled(int column) {
        return state.isColumnFilled(column);
    }

    public int getHeight() {
        return state.getHeight();
    }

    public int getWidth() {
        return state.getWidth();
    }

    public void changeState(BoardState state) {
        this.state = state;
    }

    public boolean isEmptySlot(int row, int column) {
        return state.isEmptySlot(row, column);
    }

    public void rotate(Direction direction) {
        if (direction == Direction.UPSIDE_DOWN) {
            state.turnUpsideDown(state);
        } else {
            changeState(new VerticalBoardState(state, direction));
        }
    }

    public boolean isBoardFilled() {
        for (int column = 0; column < getWidth(); column++) {
            if (!isColumnFilled(column)) {
                return false;
            }
        }
        return true;
    }

    public enum Size {
        DEFAULT(6, 7),
        ROW7_COLUMN8(7, 8),
        ROW7_COLUMN9(7, 9),
        ROW7_COLUMN10(7, 10);
        private final int height;
        private final int width;

        Size(int height, int width) {
            this.height = height;
            this.width = width;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }
}
