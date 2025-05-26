package dev.cheercode.connectfour.model.board;

import dev.cheercode.connectfour.model.Disc;

public class RightRotatedBoardState extends RotatedBoardState {
    public RightRotatedBoardState(BoardState previous) {
        super(previous.getWidth(), previous.getHeight());
        init(previous);
    }

    @Override
    protected Disc getFrom(BoardState previous, int row, int column) {
        return previous.get(width - 1 - column, row);
    }
}
