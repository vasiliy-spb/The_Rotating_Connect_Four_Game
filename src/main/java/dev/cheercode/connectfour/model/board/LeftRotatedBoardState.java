package dev.cheercode.connectfour.model.board;

import dev.cheercode.connectfour.model.Disc;

public class LeftRotatedBoardState extends RotatedBoardState {
    public LeftRotatedBoardState(BoardState previous) {
        super(previous.getWidth(), previous.getHeight());
        init(previous);
        applyGravity();
    }

    @Override
    protected Disc getFrom(BoardState previous, int row, int column) {
        return previous.get(column, height - 1 - row);
    }

    @Override
    protected boolean isOnField(int row, int column, BoardState previous) {
        return previous.isOnField(column, height - 1 - row);
    }
}
