package dev.cheercode.connectfour.model.board;

import dev.cheercode.connectfour.model.Disc;

public class RightRotatedBoardState extends RotatedBoardState {
    public RightRotatedBoardState(BoardState previous) {
        super(previous.getWidth(), previous.getHeight());
        init(previous);
        applyGravity();
    }

    @Override
    protected Disc getFrom(BoardState previous, int row, int column) {
        return previous.get(width - 1 - column, row);
    }

    @Override
    protected boolean isOnField(int row, int column, BoardState previous) {
        return previous.isOnField(width - 1 - column, row);
    }
}
