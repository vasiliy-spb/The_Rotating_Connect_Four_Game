package dev.cheercode.connectfour.factory;

import dev.cheercode.connectfour.game.BoardShapeSelector;
import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.model.board.DefaultBoardState;

public class FromShapeBoardFactory implements BoardFactory {
    private final BoardShapeSelector shapeSelector;

    public FromShapeBoardFactory(BoardShapeSelector shapeSelector) {
        this.shapeSelector = shapeSelector;
    }

    @Override
    public Board create(Board.Size size) {
        boolean[][] shape = shapeSelector.select(size);
        Board board = new Board(new DefaultBoardState(size, shape));
        return board;
    }
}
