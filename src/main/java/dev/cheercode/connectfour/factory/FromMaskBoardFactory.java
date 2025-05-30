package dev.cheercode.connectfour.factory;

import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.model.board.DefaultBoardState;

public class FromMaskBoardFactory implements BoardFactory {
    private final BoardMaskSelector maskSelector;

    public FromMaskBoardFactory(BoardMaskSelector maskSelector) {
        this.maskSelector = maskSelector;
    }

    @Override
    public Board create(Board.Size size) {
        boolean[][] mask = maskSelector.select(size);
        Board board = new Board(new DefaultBoardState(size, mask));
        return board;
    }
}
