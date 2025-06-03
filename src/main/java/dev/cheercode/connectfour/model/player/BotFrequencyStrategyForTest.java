package dev.cheercode.connectfour.model.player;

import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;

import java.util.function.BinaryOperator;

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

    public BotFrequencyStrategy.LineScore evaluateLineScore(int row, int column, int[][] scoreMatrix, int[] direction) {
        return new BotFrequencyStrategy().evaluateLineScore(row, column, scoreMatrix, direction);
    }
}
