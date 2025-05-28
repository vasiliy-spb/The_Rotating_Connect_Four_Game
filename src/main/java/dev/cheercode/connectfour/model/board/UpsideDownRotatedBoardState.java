package dev.cheercode.connectfour.model.board;

import dev.cheercode.connectfour.model.Disc;

public class UpsideDownRotatedBoardState extends RotatedBoardState {
    public UpsideDownRotatedBoardState(BoardState previous) {
        super(previous.getHeight(), previous.getWidth());
        init(previous);
        applyGravity();
    }

//    private void init(BoardState previous) {
//        for (int row = 0; row < height; row++) {
//            for (int column = 0; column < width; column++) {
//                mask[row][column] = previous.isOnField(height - 1 - row, width - 1 - column);
//            }
//        }
//        for (int row = 0; row < height; row++) {
//            for (int column = 0; column < width; column++) {
//                try {
//                    Disc disc = previous.get(height - 1 - row, width - 1 - column);
//                    put(row, column, disc);
//                } catch (IllegalArgumentException ignored) {
//                }
//            }
//        }
//    }

    @Override
    protected Disc getFrom(BoardState previous, int row, int column) {
        return previous.get(height - 1 - row, width - 1 - column);
    }

    @Override
    protected boolean isOnField(int row, int column, BoardState previous) {
        return previous.isOnField(height - 1 - row, width - 1 - column);
    }
}
