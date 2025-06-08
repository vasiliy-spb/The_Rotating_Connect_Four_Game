package dev.cheercode.connectfour.model.player;

import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;

public class ScoreMatrixBuilder {
    private static final int DISABLED_SLOT_SCORE = -1;
    private static final int UNAVAILABLE_SLOT_SCORE = 0;
    private static final int EMPTY_SLOT_SCORE = 1;
    private static final int PLAYER_DISC_SCORE = 8;
    private static final int ANOTHER_DISC_SCORE = -8;

    public int[][] create(Disc playerDisc, Board board, int[] positions) {
        int height = board.getHeight();
        int width = board.getWidth();
        int[][] matrix = new int[height][width];
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                matrix[row][column] = getSlotScore(row, column, board, playerDisc, positions);
            }
        }
        return matrix;
    }

    private int getSlotScore(int row, int column, Board board, Disc playerDisc, int[] positions) {
        if (!board.isOnField(row, column)) {
            return DISABLED_SLOT_SCORE;
        }
        if (board.isEmptySlot(row, column)) {
            if (row > positions[column]) {
                return UNAVAILABLE_SLOT_SCORE;
            }
            return EMPTY_SLOT_SCORE;
        }
        if (board.get(row, column) == playerDisc) {
            return PLAYER_DISC_SCORE;
        }
        return ANOTHER_DISC_SCORE;
    }
}
