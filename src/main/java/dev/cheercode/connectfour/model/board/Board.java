package dev.cheercode.connectfour.model.board;

import dev.cheercode.connectfour.model.Disc;

public class Board {
    private BoardState state;

    public Board(BoardState state) {
        this.state = state;
    }

    public int getHeight() {
        return state.getHeight();
    }

    public int getWidth() {
        return state.getWidth();
    }

    public Disc get(int row, int column) {
        return state.get(row, column);
    }

    public void drop(int column, Disc disc) {
        state.drop(column, disc);
    }

    public boolean isEmptySlot(int row, int column) {
        return state.isEmptySlot(row, column);
    }

    public boolean isColumnFilled(int column) {
        return state.isColumnFilled(column);
    }

    public boolean isBoardFilled() {
        return state.isBoardFilled();
    }

    public boolean isOnField(int row, int column) {
        return state.isOnField(row, column);
    }

    public void rotate(Direction direction) {
        state = state.rotate(direction);
    }

    public enum Size {
        ROW6_COLUMN7(6, 7),
        ROW7_COLUMN8(7, 8),
        ROW7_COLUMN9(7, 9),
        ROW7_COLUMN10(7, 10);

        private final int height;
        private final int width;

        Size(int height, int width) {
            this.height = height;
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }
    }
}
