package dev.cheercode.connectfour.model.player;

import dev.cheercode.connectfour.annotation.TestedByReflection;
import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;

import java.util.Arrays;
import java.util.function.BinaryOperator;

public class BotFrequencyStrategy implements MoveStrategy {
    private static final int DISABLED_SLOT_SCORE = -1;
    private static final int EMPTY_SLOT_SCORE = 1;
    private static final int PLAYER_DISC_SCORE = 8;
    private static final int ANOTHER_DISC_SCORE = -8;
    private static final int MIN_LINE_LENGTH = 4;
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
        return calculateAllScores(scoreMatrix, columnBottoms, board.getWidth());
    }

    @TestedByReflection
    protected int[][] createScoreMatrix(Disc playerDisc, Board board) {
        int height = board.getHeight();
        int width = board.getWidth();
        int[][] matrix = new int[height][width];
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                matrix[row][column] = getSlotScore(row, column, board, playerDisc);
            }
        }
        return matrix;
    }

    private int getSlotScore(int row, int column, Board board, Disc playerDisc) {
        if (!board.isOnField(row, column)) {
            return DISABLED_SLOT_SCORE;
        }
        if (board.isEmptySlot(row, column)) {
            return EMPTY_SLOT_SCORE;
        }
        if (board.get(row, column) == playerDisc) {
            return PLAYER_DISC_SCORE;
        }
        return ANOTHER_DISC_SCORE;
    }

    private int[] calculateAllScores(int[][] scoreMatrix, int[] columnBottoms, int width) {
        int[] scores = new int[width];
        for (int column = 0; column < width; column++) {
            scores[column] = calculateScore(scoreMatrix, columnBottoms[column], column);
        }
        return scores;
    }

    private int calculateScore(int[][] scoreMatrix, int row, int column) {
        int totalScore = 0;
        for (int[] direction : DIRECTIONS) {
            LineScore lineScore = evaluateLineScore(row, column, scoreMatrix, direction);
            totalScore += getValidLineScore(lineScore);
        }
        return totalScore;
    }

    private int getValidLineScore(LineScore lineScore) {
        return lineScore.length() >= MIN_LINE_LENGTH ? lineScore.score() : 0;
    }

    @TestedByReflection
    protected LineScore evaluateLineScore(int row, int column, int[][] scoreMatrix, int[] direction) {
        LineScore left = evaluateScore(row, column, scoreMatrix, direction, (a, b) -> a - b);
        LineScore right = evaluateScore(row, column, scoreMatrix, direction, (a, b) -> a + b);

        int totalLength = left.length() + right.length() + 1;
        int totalProfit = left.score() + right.score() + scoreMatrix[row][column];
        if (totalLength > MIN_LINE_LENGTH) {
            totalProfit += scoreMatrix[row][column];
        }
        return new LineScore(totalLength, totalProfit);
    }

    private LineScore evaluateScore(int row, int column, int[][] scoreMatrix, int[] direction, BinaryOperator<Integer> operation) {
        int currentRow = row;
        int currentColumn = column;
        int score = 0;
        int length = 0;

        while (shouldContinueEvaluation(row, column, scoreMatrix, direction, operation, currentRow, currentColumn)) {
            if (currentRow != row || currentColumn != column) {
                score += scoreMatrix[currentRow][currentColumn];
                length++;
            }
            currentRow = operation.apply(currentRow, direction[0]);
            currentColumn = operation.apply(currentColumn, direction[1]);
        }
        return new LineScore(length, score);
    }

    private boolean shouldContinueEvaluation(int row, int column, int[][] scoreMatrix, int[] direction, BinaryOperator<Integer> operation, int currentRow, int currentColumn) {
        if (!isValid(currentRow, currentColumn, scoreMatrix.length, scoreMatrix[0].length)) {
            return false;
        }
        if (scoreMatrix[currentRow][currentColumn] < 0) {
            return false;
        }
        return !reachEvaluationLimit(row, column, direction, operation, currentRow, currentColumn);
    }

    private boolean reachEvaluationLimit(int row, int column, int[] direction, BinaryOperator<Integer> operation, int currentRow, int currentColumn) {
        return currentRow == operation.apply(row, direction[0] * MIN_LINE_LENGTH) &&
               currentColumn == operation.apply(column, direction[1] * MIN_LINE_LENGTH);
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
