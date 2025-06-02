package dev.cheercode.connectfour.model.player;

import dev.cheercode.connectfour.annotation.TestedByReflection;
import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;

import java.util.Arrays;

public class BotFrequencyStrategy implements MoveStrategy {
    private static final int[][] DIRECTIONS = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}
    };

    @Override
    public int chooseColumn(Player player, Board board) {
        int[] columnBottoms = findColumnBottoms(board);
        int[] profits = calculateProfitForLowestPositions(player.getDisc(), board, columnBottoms);
        return chooseMostProfitableColumn(profits);
    }

    @TestedByReflection
    protected int[] findColumnBottoms(Board board) {
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

    @TestedByReflection
    protected int[] calculateProfitForLowestPositions(Disc disc, Board board, int[] lowestPositions) {
        int[][] frequencyMatrix = createFrequencyMatrix(disc, board);

        int width = board.getWidth();
        int[] profit = new int[width];
        for (int column = 0; column < width; column++) {
            int row = lowestPositions[column];
            if (row < 0) {
                continue;
            }
            for (int[] direction : DIRECTIONS) {
                DirectionProfit result = accumulateProfitForBothWayInDirection(row, column, direction, frequencyMatrix);
                if (result.length() < 4) {
                    continue;
                }
                profit[column] += result.profit();
            }
        }
        return profit;
    }

    @TestedByReflection
    protected int[][] createFrequencyMatrix(Disc disc, Board board) {
        int height = board.getHeight();
        int width = board.getWidth();
        int[][] frequencyMatrix = new int[height][width];
        for (int row = 0; row < frequencyMatrix.length; row++) {
            for (int column = 0; column < frequencyMatrix[0].length; column++) {
                if (!board.isOnField(row, column)) {
                    frequencyMatrix[row][column] = -1;
                    continue;
                }
                if (board.isEmptySlot(row, column)) {
                    frequencyMatrix[row][column] = 1;
                    continue;
                }
                if (board.get(row, column) == disc) {
                    frequencyMatrix[row][column] = 8;
                } else {
                    frequencyMatrix[row][column] = -8;
                }
            }
        }
        return frequencyMatrix;
    }

    @TestedByReflection
    protected DirectionProfit accumulateProfitForBothWayInDirection(int row, int column, int[] direction, int[][] frequencyMatrix) {
        int currentRow = row;
        int currentColumn = column;
        int leftProfit = 0;
        int leftLength = 0;
        while ((currentRow != row - direction[0] * 4 || currentColumn != column - direction[1] * 4) && isValid(currentRow, currentColumn, frequencyMatrix.length, frequencyMatrix[0].length)) {
            if (frequencyMatrix[currentRow][currentColumn] < 0) {
                break;
            }
            if (currentRow != row || currentColumn != column) {
                leftProfit += frequencyMatrix[currentRow][currentColumn];
                leftLength++;
            }
            currentRow -= direction[0];
            currentColumn -= direction[1];
        }

        currentRow = row;
        currentColumn = column;
        int rightProfit = 0;
        int rightLength = 0;
        while ((currentRow != row + direction[0] * 4 || currentColumn != column + direction[1] * 4) && isValid(currentRow, currentColumn, frequencyMatrix.length, frequencyMatrix[0].length)) {
            if (frequencyMatrix[currentRow][currentColumn] < 0) {
                break;
            }
            if (currentRow != row || currentColumn != column) {
                rightProfit += frequencyMatrix[currentRow][currentColumn];
                rightLength++;
            }
            currentRow += direction[0];
            currentColumn += direction[1];
        }

        int totalLength = leftLength + rightLength + 1;
        int totalProfit = leftProfit + rightProfit + frequencyMatrix[row][column];
        if (totalLength > 4) {
            totalProfit += frequencyMatrix[row][column];
        }
        return new DirectionProfit(totalLength, totalProfit);
    }

    private boolean isValid(int row, int column, int height, int width) {
        return row >= 0 && row < height && column >= 0 && column < width;
    }

    private int chooseMostProfitableColumn(int[] profit) {
        int maxProfit = Arrays.stream(profit).max().getAsInt();
        for (int column = 0; column < profit.length; column++) {
            if (profit[column] == maxProfit) {
                return column;
            }
        }
        throw new IllegalStateException("Not a single column is chosen.");
    }

    protected record DirectionProfit(
            int length,
            int profit
    ) {
    }
}
