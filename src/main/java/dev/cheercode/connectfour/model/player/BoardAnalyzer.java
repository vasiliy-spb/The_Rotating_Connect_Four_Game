package dev.cheercode.connectfour.model.player;

import dev.cheercode.connectfour.model.board.Board;

public class BoardAnalyzer {
    public int[] findColumnBottoms(Board board) {
        int width = board.getWidth();
        int[] columnBottoms = new int[width];

        for (int column = 0; column < width; column++) {
            while (shouldMoveDownThroughDisableSlots(columnBottoms[column], column, board)) {
                columnBottoms[column]++;
            }
            columnBottoms[column] %= board.getHeight();

            while (shouldMoveDownThroughEmptySlots(columnBottoms[column], column, board)) {
                columnBottoms[column]++;
            }
            columnBottoms[column]--;

            while (shouldMoveUpThroughDisableSlots(columnBottoms[column], column, board)) {
                columnBottoms[column]--;
            }
        }

        return columnBottoms;
    }

    private boolean shouldMoveDownThroughDisableSlots(int row, int column, Board board) {
        return row < board.getHeight() && !board.isOnField(row, column);
    }

    private boolean shouldMoveDownThroughEmptySlots(int row, int column, Board board) {
        return row < board.getHeight() && board.isOnField(row, column) && board.isEmptySlot(row, column);
    }

    private boolean shouldMoveUpThroughDisableSlots(int row, int column, Board board) {
        return row >= 0 && !board.isOnField(row, column);
    }
}
