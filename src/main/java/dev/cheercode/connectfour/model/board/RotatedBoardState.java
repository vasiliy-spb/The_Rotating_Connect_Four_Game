package dev.cheercode.connectfour.model.board;

import dev.cheercode.connectfour.model.Disc;

public abstract class RotatedBoardState extends AbstractBoardState {
    public RotatedBoardState(int height, int width) {
        super(height, width);
    }

    protected void init(BoardState previous) {
        for (int row = height - 1; row >= 0; row--) {
            for (int column = 0; column < width; column++) {
                try {
                    Disc disc = getFrom(previous, row, column);
                    drop(column, disc);
                } catch (IllegalArgumentException ignored) {
                }
            }
        }
    }

    protected abstract Disc getFrom(BoardState previous, int row, int column);
}
