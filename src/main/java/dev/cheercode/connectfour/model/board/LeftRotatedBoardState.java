package dev.cheercode.connectfour.model.board;

import dev.cheercode.connectfour.model.Disc;

public class LeftRotatedBoardState extends RotatedBoardState {
    public LeftRotatedBoardState(BoardState previous) {
        super(previous.getWidth(), previous.getHeight());
        init(previous);
        applyGravity();
    }


//    private void init(BoardState previous) {
//        for (int row = 0; row < width; row++) {
//            for (int column = 0; column < height; column++) {
//                mask[column][row] = previous.isOnField(row, height - 1 - column);
//            }
//        }
//        for (int row = 0; row < width; row++) {
//            for (int column = 0; column < height; column++) {
//                try {
//                    Disc disc = previous.get(row, height - 1 - column);
//                    put(column, row, disc);
//                } catch (IllegalArgumentException ignored) {
//                }
//            }
//        }
//    }

    @Override
    protected Disc getFrom(BoardState previous, int row, int column) {
        return previous.get(column, height - 1 - row);
    }

    @Override
    protected boolean isOnField(int row, int column, BoardState previous) {
//        return previous.isOnField(row, height - 1 - column);
        return previous.isOnField(column, height - 1 - row);
    }
}
