package dev.cheercode.connectfour.model.player.move_strategy;

import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.model.player.Player;

import java.util.HashSet;
import java.util.Set;

public class BlockingScoreBasedBotStrategy extends AbstractScoreBasedBotStrategy {
    @Override
    protected Set<Disc> getDiscs(Board board, Player player) {
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
        discs.remove(player.getDisc());
        return discs;
    }
}
