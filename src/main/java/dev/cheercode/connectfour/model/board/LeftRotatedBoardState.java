package dev.cheercode.connectfour.model.board;

import dev.cheercode.connectfour.model.Disc;

public class LeftRotatedBoardState extends RotatedBoardState {
    public LeftRotatedBoardState(BoardState previous) {
        super(previous.getWidth(), previous.getHeight());
        init(previous);
    }

    @Override
    protected Disc getFrom(BoardState previous, int row, int column) {
        return previous.get(column, height - 1 - row);
    }
}
