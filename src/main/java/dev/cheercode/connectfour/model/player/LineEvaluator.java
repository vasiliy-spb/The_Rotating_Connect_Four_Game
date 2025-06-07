package dev.cheercode.connectfour.model.player;

import java.util.function.BinaryOperator;

public class LineEvaluator {
    private static final int EMPTY_SLOT_SCORE = 1;
    private static final int MIN_LINE_LENGTH = 4;

    public LineScore evaluateLineScore(int row, int column, int[][] scoreMatrix, int[] direction) {
        if (scoreMatrix[row][column] < 0) {
            throw new IllegalArgumentException("Trying to calculate score for slot with negative score: " + scoreMatrix[row][column]);
        }
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
        int totalScore = 0;
        int length = 0;

        int adjacentCount = 1;

        while (shouldContinueEvaluation(row, column, scoreMatrix, direction, operation, currentRow, currentColumn)) {
            if (currentRow != row || currentColumn != column) {
                int score = scoreMatrix[currentRow][currentColumn];

                if (score > EMPTY_SLOT_SCORE && (Math.abs(currentRow - row) == adjacentCount || Math.abs(currentColumn - column) == adjacentCount)) {
                    score *= 10;
                    adjacentCount++;
                }

                totalScore += score;
                length++;
            }
            currentRow = operation.apply(currentRow, direction[0]);
            currentColumn = operation.apply(currentColumn, direction[1]);
        }
        return new LineScore(length, totalScore);
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


/*

- - - + - - -

Y Y Y + - - -
- Y Y + Y - -
- - Y + Y Y -
- - - + Y Y Y

- Y Y + - - -
- - Y + Y - -
- - - + Y Y -

- - Y + - - -
- - - + Y - -

Y - - + - - Y
Y - - + - Y Y
Y Y - + - Y Y
Y Y - + - - Y
Y - Y + Y - Y

 */