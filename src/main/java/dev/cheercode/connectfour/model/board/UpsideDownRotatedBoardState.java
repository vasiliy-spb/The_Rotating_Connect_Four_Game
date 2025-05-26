package dev.cheercode.connectfour.model.board;

import dev.cheercode.connectfour.model.Disc;

public class UpsideDownRotatedBoardState extends RotatedBoardState {
    public UpsideDownRotatedBoardState(BoardState previous) {
        super(previous.getHeight(), previous.getWidth());
        init(previous);
    }

    @Override
    protected Disc getFrom(BoardState previous, int row, int column) {
        return previous.get(height - 1 - row, width - 1 - column);
    }
}
