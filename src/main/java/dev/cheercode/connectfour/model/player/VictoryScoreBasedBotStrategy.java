package dev.cheercode.connectfour.model.player;

import dev.cheercode.connectfour.model.board.Board;

import java.util.Arrays;

public class VictoryScoreBasedBotStrategy implements MoveStrategy {
    private final BoardAnalyzer boardAnalyzer;
    private final PositionEvaluator positionEvaluator;

    public VictoryScoreBasedBotStrategy() {
        this.boardAnalyzer = new BoardAnalyzer();
        this.positionEvaluator = new PositionEvaluator();
    }

    @Override
    public int chooseColumn(Player player, Board board) {
        int[] positions = boardAnalyzer.findColumnBottoms(board);
        int[] scores = positionEvaluator.calculateScoresFor(player.getDisc(), positions, board);
        return chooseMostProfitableColumn(scores);
    }

    private int chooseMostProfitableColumn(int[] scores) {
        int maxProfit = Arrays.stream(scores).max().getAsInt();
        for (int column = 0; column < scores.length; column++) {
            if (scores[column] == maxProfit) {
                return column;
            }
        }
        throw new IllegalStateException("Not a single column is chosen.");
    }
}
