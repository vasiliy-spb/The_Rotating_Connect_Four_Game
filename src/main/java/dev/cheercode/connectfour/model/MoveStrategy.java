package dev.cheercode.connectfour.model;

import dev.cheercode.connectfour.model.board.Board;

public interface MoveStrategy {
    int chooseColumn(Player player, Board board);
}
