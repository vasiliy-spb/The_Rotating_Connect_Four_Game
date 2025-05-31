package dev.cheercode.connectfour.model.player;

import dev.cheercode.connectfour.model.board.Board;

public interface MoveStrategy {
    int chooseColumn(Player player, Board board);
}
