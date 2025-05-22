package dev.cheercode.connectfour.model.board;

import dev.cheercode.connectfour.model.Disc;

public class UpsideDownRotatedBoardState extends AbstractBoardState {
    public UpsideDownRotatedBoardState(BoardState previous) {
        super(previous.getHeight(), previous.getWidth());
        init(previous);
    }

    private void init(BoardState previous) {
        for (int row = 0; row < height; row++) {
            for (int column = width - 1; column >= 0; column--) {
                try {
                    Disc disc = previous.get(row, column);
                    drop(width - 1 - column, disc);
                } catch (IllegalArgumentException ignored) {
                }
            }
        }
    }
}
