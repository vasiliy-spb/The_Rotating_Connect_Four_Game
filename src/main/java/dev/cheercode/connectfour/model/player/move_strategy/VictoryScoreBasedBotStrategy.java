package dev.cheercode.connectfour.model.player.move_strategy;

import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.model.player.Player;

import java.util.HashSet;
import java.util.Set;

public class VictoryScoreBasedBotStrategy extends AbstractScoreBasedBotStrategy {
    @Override
    protected Set<Disc> getDiscs(Board board, Player player) {
        Set<Disc> discs = new HashSet<>();
        discs.add(player.getDisc());
        return discs;
    }
}
