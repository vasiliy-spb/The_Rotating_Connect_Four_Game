package dev.cheercode.connectfour.model.player;

import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;

public class PositionEvaluator {
    private final ScoreMatrixBuilder matrixBuilder;
    private final LineEvaluator lineEvaluator;
    private static final int MIN_LINE_LENGTH = 4;
    private static final int[][] DIRECTIONS = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}
    };

    public PositionEvaluator() {
        this.matrixBuilder = new ScoreMatrixBuilder();
        this.lineEvaluator = new LineEvaluator();
    }

    public int[] calculateScoresFor(Disc disc, int[] positions, Board board) {
        int[][] scoreMatrix = matrixBuilder.create(disc, board, positions);
        return calculateAllScores(scoreMatrix, positions);
    }

    private int[] calculateAllScores(int[][] scoreMatrix, int[] positions) {
        int width = positions.length;
        int[] scores = new int[width];
        for (int column = 0; column < width; column++) {
            scores[column] = calculateScore(scoreMatrix, positions[column], column);
        }
        return scores;
    }

    private int calculateScore(int[][] scoreMatrix, int row, int column) {
        if (row < 0) {
            return -1;
        }
        int totalScore = 0;
        for (int[] direction : DIRECTIONS) {
            LineScore lineScore = lineEvaluator.evaluateLineScore(row, column, scoreMatrix, direction);
            totalScore += getValidLineScore(lineScore);
        }
        return totalScore;
    }

    private int getValidLineScore(LineScore lineScore) {
        return lineScore.length() >= MIN_LINE_LENGTH ? lineScore.score() : 0;
    }
}
