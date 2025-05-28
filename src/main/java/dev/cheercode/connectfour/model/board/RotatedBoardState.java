package dev.cheercode.connectfour.model.board;

import dev.cheercode.connectfour.model.Disc;

public abstract class RotatedBoardState extends AbstractBoardState {
    public RotatedBoardState(int height, int width) {
        super(height, width);
    }

    protected void init(BoardState previous) {
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (isOnField(row, column, previous)) {
                    mask[row][column] = true;
                }
            }
        }
        for (int row = height - 1; row >= 0; row--) {
            for (int column = 0; column < width; column++) {
                try {
                    Disc disc = getFrom(previous, row, column);
                    put(row, column, disc);
                } catch (IllegalArgumentException ignored) {
                }
            }
        }
    }

    protected abstract boolean isOnField(int row, int column, BoardState previous);

    protected abstract Disc getFrom(BoardState previous, int row, int column);
}
