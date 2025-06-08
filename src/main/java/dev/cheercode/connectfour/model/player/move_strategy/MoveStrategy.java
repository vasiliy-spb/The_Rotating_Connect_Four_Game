package dev.cheercode.connectfour.model.player.move_strategy;

import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.model.player.Player;

public interface MoveStrategy {
    int chooseColumn(Player player, Board board);
}
