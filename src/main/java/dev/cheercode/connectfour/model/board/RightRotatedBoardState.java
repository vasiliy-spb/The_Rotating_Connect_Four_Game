package dev.cheercode.connectfour.model.board;

import dev.cheercode.connectfour.model.Disc;

public class RightRotatedBoardState extends AbstractBoardState {
    public RightRotatedBoardState(BoardState previous) {
        super(previous.getWidth(), previous.getHeight());
        int prevHeight = previous.getHeight();
        for (int row = height - 1; row >= 0; row--) {
            for (int column = 0; column < width; column++) {
                try {
                    Disc disc = previous.get(prevHeight - 1 - column, row);
                    drop(column, disc);
                } catch (IllegalArgumentException ignored) {
                }
            }
        }
    }
}
