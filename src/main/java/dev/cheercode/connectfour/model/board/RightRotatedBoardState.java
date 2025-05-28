package dev.cheercode.connectfour.model.board;

import dev.cheercode.connectfour.model.Disc;

public class RightRotatedBoardState extends RotatedBoardState {
    public RightRotatedBoardState(BoardState previous) {
        super(previous.getWidth(), previous.getHeight());
        init(previous);
        applyGravity();
    }

//    private void init(BoardState previous) {
//        for (int row = 0; row < width; row++) {
//            for (int column = 0; column < height; column++) {
//                mask[column][row] = previous.isOnField(width - 1 - row, column);
//            }
//        }
//        for (int row = 0; row < width; row++) {
//            for (int column = 0; column < height; column++) {
//                try {
//                    Disc disc = previous.get(width - 1 - row, column);
//                    put(column, row, disc);
//                } catch (IllegalArgumentException ignored) {
//                }
//            }
//        }
//    }

    @Override
    protected Disc getFrom(BoardState previous, int row, int column) {
        return previous.get(width - 1 - column, row);
    }

    @Override
    protected boolean isOnField(int row, int column, BoardState previous) {
        return previous.isOnField(width - 1 - column, row);
    }
}
