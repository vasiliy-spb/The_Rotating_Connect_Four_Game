package dev.cheercode.connectfour.model.board;

public class DefaultBoardState extends AbstractBoardState {
    public DefaultBoardState(Board.Size size) {
        super(size.getHeight(), size.getWidth());
    }
}
