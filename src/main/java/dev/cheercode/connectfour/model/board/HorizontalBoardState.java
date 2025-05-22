package dev.cheercode.connectfour.model.board;

public class HorizontalBoardState extends AbstractBoardState {
    public HorizontalBoardState(Board.Size size) {
        super(size);
    }

    public HorizontalBoardState(BoardState previous, Direction direction) {
        super(previous, direction);
    }
}
