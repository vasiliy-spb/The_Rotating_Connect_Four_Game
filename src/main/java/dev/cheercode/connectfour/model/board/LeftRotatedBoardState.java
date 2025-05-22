package dev.cheercode.connectfour.model.board;

import dev.cheercode.connectfour.model.Disc;

public class LeftRotatedBoardState extends AbstractBoardState {
    public LeftRotatedBoardState(BoardState previous) {
        super(previous.getWidth(), previous.getHeight());
        init(previous);
    }

    private void init(BoardState previous) {
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                try {
                    Disc disc = previous.get(column, row);
                    drop(column, disc);
                } catch (IllegalArgumentException ignored) {
                }
            }
        }
    }
}
