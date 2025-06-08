package dev.cheercode.connectfour.model.player.move_strategy;

import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.model.player.BoardAnalyzer;
import dev.cheercode.connectfour.model.player.Player;
import dev.cheercode.connectfour.model.player.PositionEvaluator;

import java.util.Set;

public abstract class AbstractScoreBasedBotStrategy implements MoveStrategy {
    private final BoardAnalyzer boardAnalyzer;
    private final PositionEvaluator positionEvaluator;

    public AbstractScoreBasedBotStrategy() {
        this.boardAnalyzer = new BoardAnalyzer();
        this.positionEvaluator = new PositionEvaluator();
    }

    @Override
    public int chooseColumn(Player player, Board board) {
        Set<Disc> discs = getDiscs(board, player);

        int[] positions = boardAnalyzer.findColumnBottoms(board);

        int width = board.getWidth();
        int[] scores = evaluateScores(discs, positions, board);

        boolean[] availableColumns = new boolean[width];
        for (int column = 0; column < width; column++) {
            availableColumns[column] = !board.isColumnFilled(column);
        }

        return chooseMostProfitableColumn(scores, availableColumns);
    }

    private int[] evaluateScores(Set<Disc> discs, int[] positions, Board board) {
        int width = board.getWidth();
        int[] scores = new int[width];
        for (Disc disc : discs) {
            int[] currentScores = positionEvaluator.calculateScoresFor(disc, positions, board);
            for (int i = 0; i < width; i++) {
                scores[i] = Math.max(scores[i], currentScores[i]);
            }
        }
        return scores;
    }

    protected abstract Set<Disc> getDiscs(Board board, Player player);

    private int chooseMostProfitableColumn(int[] scores, boolean[] availableColumns) {
        int maxScore = -1;
        int selectedColumn = -1;
        for (int column = 0; column < scores.length; column++) {
            if (!availableColumns[column]) {
                continue;
            }
            if (scores[column] > maxScore) {
                maxScore = scores[column];
                selectedColumn = column;
            }
        }
        if (selectedColumn < 0) {
            throw new IllegalStateException("Not a single column is chosen.");
        }
        return selectedColumn;
    }
}
