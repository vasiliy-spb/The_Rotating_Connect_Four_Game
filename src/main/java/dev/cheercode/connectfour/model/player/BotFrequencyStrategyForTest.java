package dev.cheercode.connectfour.model.player;

import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;

public class BotFrequencyStrategyForTest {
    public int[] findColumnBottoms(Board board) {
        return new BotFrequencyStrategy().findColumnBottoms(board);
    }

    public int[] calculateProfitForLowestPositions(Disc disc, Board board, int[] lowestPositions) {
        return new BotFrequencyStrategy().calculateProfitForLowestPositions(disc, board, lowestPositions);
    }

    public int[][] createFrequencyMatrix(Disc disc, Board board) {
        return new BotFrequencyStrategy().createFrequencyMatrix(disc, board);
    }

    public BotFrequencyStrategy.DirectionProfit accumulateProfitForBothWayInDirection(int row, int column, int[] direction, int[][] frequencyMatrix) {
        return new BotFrequencyStrategy().accumulateProfitForBothWayInDirection(row, column, direction, frequencyMatrix);
    }
}
