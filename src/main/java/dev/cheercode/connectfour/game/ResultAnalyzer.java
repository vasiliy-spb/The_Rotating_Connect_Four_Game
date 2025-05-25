package dev.cheercode.connectfour.game;

import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.model.Disc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResultAnalyzer {
    private static final int winCount = 4;
    private static final int[][] directions = {{0, 1}, {1, 0}, {1, 1}, {1, -1}};
    private final Board board;
    private final Set<Disc> winnerCache;

    public ResultAnalyzer(Board board) {
        this.board = board;
        this.winnerCache = new HashSet<>();
    }

    public boolean isWin(Disc disc) {
        int height = board.getHeight();
        int width = board.getWidth();
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (board.isEmptySlot(row, column)) {
                    continue;
                }
                if (board.get(row, column) != disc) {
                    continue;
                }
                if (check(row, column, disc)) {
                    return true;
                }
            }
        }
        return false;
    }


    private boolean check(int row, int column, Disc disc) {
        for (int[] direction : directions) {
            if (!isDirectionExists(row, column, direction)) {
                continue;
            }
            int count = 1;
            int nextRow = row + direction[0];
            int nextColumn = column + direction[1];
            while (count < winCount) {
                if (!isValid(nextRow, nextColumn)) {
                    break;
                }
                if (board.isEmptySlot(nextRow, nextColumn)) {
                    break;
                }
                if (board.get(nextRow, nextColumn) != disc) {
                    break;
                }
                count++;
                nextRow += direction[0];
                nextColumn += direction[1];
            }
            if (count == winCount) {
                return true;
            }
        }
        return false;
    }

    private boolean isDirectionExists(int row, int column, int[] direction) {
        int lastRow = row + (direction[0] * (winCount - 1));
        int lastColumn = column + (direction[1] * (winCount - 1));
        return isValid(lastRow, lastColumn);
    }

    private boolean isValid(int row, int column) {
        return isInBounds(row, column) && board.isOnField(row, column);
    }

    private boolean isInBounds(int row, int column) {
        return row >= 0 && row < board.getHeight() && column >= 0 && column < board.getWidth();
    }

    public boolean isDraw(List<Disc> discs) {
        if (!board.isBoardFilled()) {
            return false;
        }
        return getWinnerDiscs(discs).isEmpty();
    }

    public Set<Disc> getWinnerDiscs(List<Disc> discs) {
        if (winnerCache.isEmpty()) {
            for (Disc disc : discs) {
                if (isWin(disc)) {
                    winnerCache.add(disc);
                }
            }
        }
        return new HashSet<>(winnerCache);
    }
}
