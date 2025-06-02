package dev.cheercode.connectfour.model.player;

import dev.cheercode.connectfour.annotation.TestedByReflection;
import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;

import java.util.Arrays;

public class BotFrequencyStrategy implements MoveStrategy {
    private static final int DISABLE_SLOT_SCORE = -1;
    private static final int EMPTY_SLOT_SCORE = 1;
    private static final int PLAYER_DISC_SCORE = 8;
    private static final int OTHER_PLAYER_DISC_SCORE = -8;
    private static final int MIN_LINE_SCORE_LENGTH = 4;
    private static final int[][] DIRECTIONS = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}
    };

    @Override
    public int chooseColumn(Player player, Board board) {
        int[] columnBottoms = findColumnBottoms(board);
        int[] profits = calculateScoresForColumnBottoms(player.getDisc(), board, columnBottoms);
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
    protected int[] calculateScoresForColumnBottoms(Disc disc, Board board, int[] columnBottoms) {
        int[][] scoreMatrix = createScoreMatrix(disc, board);
        return calculateColumnBottomsScores(scoreMatrix, columnBottoms, board.getWidth());
    }

    @TestedByReflection
    protected int[][] createScoreMatrix(Disc playerDisc, Board board) {
        int height = board.getHeight();
        int width = board.getWidth();
        int[][] scoreMatrix = new int[height][width];
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (!board.isOnField(row, column)) {
                    scoreMatrix[row][column] = DISABLE_SLOT_SCORE;
                    continue;
                }
                if (board.isEmptySlot(row, column)) {
                    scoreMatrix[row][column] = EMPTY_SLOT_SCORE;
                    continue;
                }
                if (board.get(row, column) == playerDisc) {
                    scoreMatrix[row][column] = PLAYER_DISC_SCORE;
                } else {
                    scoreMatrix[row][column] = OTHER_PLAYER_DISC_SCORE;
                }
            }
        }
        return scoreMatrix;
    }

    private int[] calculateColumnBottomsScores(int[][] scoreMatrix, int[] columnBottoms, int width) {
        int[] scores = new int[width];
        for (int column = 0; column < width; column++) {
            scores[column] = calculateScores(scoreMatrix, columnBottoms[column], column);
        }
        return scores;
    }

    private int calculateScores(int[][] scoreMatrix, int row, int column) {
        int totalScore = 0;
        for (int[] direction : DIRECTIONS) {
            LineScore lineScore = evaluateLineScore(row, column, scoreMatrix, direction);
            totalScore += getValidLineScore(lineScore);
        }
        return totalScore;
    }

    private int getValidLineScore(LineScore lineScore) {
        return lineScore.length() >= MIN_LINE_SCORE_LENGTH ? lineScore.score() : 0;
    }

    @TestedByReflection
    protected LineScore evaluateLineScore(int row, int column, int[][] frequencyMatrix, int[] direction) {
        int currentRow = row;
        int currentColumn = column;
        int leftProfit = 0;
        int leftLength = 0;
        while ((currentRow != row - direction[0] * MIN_LINE_SCORE_LENGTH || currentColumn != column - direction[1] * MIN_LINE_SCORE_LENGTH) && isValid(currentRow, currentColumn, frequencyMatrix.length, frequencyMatrix[0].length)) {
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
        while ((currentRow != row + direction[0] * MIN_LINE_SCORE_LENGTH || currentColumn != column + direction[1] * MIN_LINE_SCORE_LENGTH) && isValid(currentRow, currentColumn, frequencyMatrix.length, frequencyMatrix[0].length)) {
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
        if (totalLength > MIN_LINE_SCORE_LENGTH) {
            totalProfit += frequencyMatrix[row][column];
        }
        return new LineScore(totalLength, totalProfit);
    }

    private boolean isValid(int row, int column, int height, int width) {
        return row >= 0 && row < height && column >= 0 && column < width;
    }

    private int chooseMostProfitableColumn(int[] profits) {
        int maxProfit = Arrays.stream(profits).max().getAsInt();
        for (int column = 0; column < profits.length; column++) {
            if (profits[column] == maxProfit) {
                return column;
            }
        }
        throw new IllegalStateException("Not a single column is chosen.");
    }

    protected record LineScore(
            int length,
            int score
    ) {
    }
}
