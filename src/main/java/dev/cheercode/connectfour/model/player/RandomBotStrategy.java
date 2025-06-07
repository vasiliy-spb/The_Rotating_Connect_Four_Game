package dev.cheercode.connectfour.model.player;

import dev.cheercode.connectfour.model.board.Board;

import java.util.Random;

public class RandomBotStrategy implements MoveStrategy {
    private static final Random RANDOM = new Random();

    @Override
    public int chooseColumn(Player player, Board board) {
        int columnIndex = RANDOM.nextInt(board.getWidth());
        while (board.isColumnFilled(columnIndex)) {
            columnIndex = RANDOM.nextInt(board.getWidth());
        }
        System.out.printf("Игрок %s делает ход: %d\n", player.getName(), columnIndex + 1);
        return columnIndex;
    }
}
