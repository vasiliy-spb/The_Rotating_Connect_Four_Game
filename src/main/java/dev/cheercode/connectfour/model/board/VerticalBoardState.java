package dev.cheercode.connectfour.model.board;

public class VerticalBoardState extends AbstractBoardState {
    public VerticalBoardState(Board.Size size) {
        super(size);
    }

    public VerticalBoardState(BoardState previous, Direction direction) {
        super(previous, direction);
    }
}
