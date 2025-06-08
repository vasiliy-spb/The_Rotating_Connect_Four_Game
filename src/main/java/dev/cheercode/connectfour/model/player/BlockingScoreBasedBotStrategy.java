package dev.cheercode.connectfour.model.player;

import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BlockingScoreBasedBotStrategy implements MoveStrategy {
    private final BoardAnalyzer boardAnalyzer;
    private final PositionEvaluator positionEvaluator;

    public BlockingScoreBasedBotStrategy() {
        this.boardAnalyzer = new BoardAnalyzer();
        this.positionEvaluator = new PositionEvaluator();
    }

    @Override
    public int chooseColumn(Player player, Board board) {
        Set<Disc> discs = getAllDiscs(board);
        discs.remove(player.getDisc());

        int[] positions = boardAnalyzer.findColumnBottoms(board);

        int width = board.getWidth();
        int[] scores = new int[width];
        for (Disc disc : discs) {
            int[] currentScores = positionEvaluator.calculateScoresFor(disc, positions, board);
            for (int i = 0; i < width; i++) {
                scores[i] = Math.max(scores[i], currentScores[i]);
            }
        }

        return chooseMostProfitableColumn(scores);
    }

    private Set<Disc> getAllDiscs(Board board) {
        Set<Disc> discs = new HashSet<>();
        int height = board.getHeight();
        int width = board.getWidth();
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (!board.isOnField(row, column)) {
                    continue;
                }
                if (board.isEmptySlot(row, column)) {
                    continue;
                }
                Disc disc = board.get(row, column);
                discs.add(disc);
            }
        }
        return discs;
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
