package dev.cheercode.connectfour.model.player;

import java.util.function.BinaryOperator;

public class LineEvaluator {
    private static final int PLAYER_DISC_SCORE = 8;
    private static final int MIN_LINE_LENGTH = 4;
    private static final int BASE_BONUS = 100;
    private static final int BONUS_STEP = 10;

    public LineScore evaluateLineScore(int row, int column, int[][] scoreMatrix, int[] direction) {
        if (scoreMatrix[row][column] < 0) {
            throw new IllegalArgumentException("Trying to calculate score for slot with negative score: " + scoreMatrix[row][column]);
        }
        LineScore left = evaluateScore(row, column, scoreMatrix, direction, (a, b) -> a - b);
        LineScore right = evaluateScore(row, column, scoreMatrix, direction, (a, b) -> a + b);

        int totalLength = left.length() + right.length() + 1;
        int totalScore = left.score() + right.score() + scoreMatrix[row][column];
        if (totalLength > MIN_LINE_LENGTH) {
            totalScore += scoreMatrix[row][column];
        }
        int totalAdjacentCount = left.adjacentCount() + right.adjacentCount();

        int bonusScore = calculateBonusScore(totalAdjacentCount);
        totalScore += bonusScore;

        return new LineScore(totalLength, totalScore, totalAdjacentCount);
    }

    private int calculateBonusScore(int adjacentCount) {
        int bonusScore = 0;
        for (int i = 0; i < adjacentCount; i++) {
            int multiplier = BASE_BONUS;
            for (int j = 0; j < i; j++) {
                multiplier *= BONUS_STEP;
            }
            bonusScore += PLAYER_DISC_SCORE * multiplier;
        }
        return bonusScore;
    }

    private LineScore evaluateScore(int row, int column, int[][] scoreMatrix, int[] direction, BinaryOperator<Integer> operation) {
        int currentRow = row;
        int currentColumn = column;
        int totalScore = 0;
        int length = 0;

        int adjacentCount = 1;

        while (shouldContinueEvaluation(row, column, scoreMatrix, direction, operation, currentRow, currentColumn)) {
            if (currentRow != row || currentColumn != column) {
                int score = scoreMatrix[currentRow][currentColumn];

                if (score == PLAYER_DISC_SCORE && (Math.abs(currentRow - row) == adjacentCount || Math.abs(currentColumn - column) == adjacentCount)) {
                    adjacentCount++;
                }

                totalScore += score;
                length++;
            }
            currentRow = operation.apply(currentRow, direction[0]);
            currentColumn = operation.apply(currentColumn, direction[1]);
        }
        return new LineScore(length, totalScore, adjacentCount - 1);
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

    private boolean isValid(int row, int column, int height, int width) {
        return row >= 0 && row < height && column >= 0 && column < width;
    }

    private boolean reachEvaluationLimit(int row, int column, int[] direction, BinaryOperator<Integer> operation, int currentRow, int currentColumn) {
        return currentRow == operation.apply(row, direction[0] * MIN_LINE_LENGTH) &&
               currentColumn == operation.apply(column, direction[1] * MIN_LINE_LENGTH);
    }
}
