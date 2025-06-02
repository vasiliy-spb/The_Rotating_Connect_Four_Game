package dev.cheercode.connectfour.model.player;

import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;

public class BotFrequencyStrategyForTest {
    public int[] findColumnBottoms(Board board) {
        return new BotFrequencyStrategy().findColumnBottoms(board);
    }

    public int[] calculateScoresForColumnBottoms(Disc disc, Board board, int[] lowestPositions) {
        return new BotFrequencyStrategy().calculateScoresForColumnBottoms(disc, board, lowestPositions);
    }

    public int[][] createFrequencyMatrix(Disc disc, Board board) {
        return new BotFrequencyStrategy().createScoreMatrix(disc, board);
    }

    public BotFrequencyStrategy.LineScore accumulateProfitForBothWayInDirection(int row, int column, int[][] frequencyMatrix, int[] direction) {
        return new BotFrequencyStrategy().evaluateLineScore(row, column, frequencyMatrix, direction);
    }
}
