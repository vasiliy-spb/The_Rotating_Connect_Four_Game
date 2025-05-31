package dev.cheercode.connectfour.model.player;

import dev.cheercode.connectfour.model.board.Board;

import java.util.Random;

public class BotRandomStrategy implements MoveStrategy {
    private static final Random RANDOM = new Random();

    @Override
    public int chooseColumn(Player player, Board board) {
        return RANDOM.nextInt(board.getWidth());
    }
}
