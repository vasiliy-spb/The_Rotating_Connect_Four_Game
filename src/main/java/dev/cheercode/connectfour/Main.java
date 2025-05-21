package dev.cheercode.connectfour;

import dev.cheercode.connectfour.model.Player;
import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        Board board = new Board(Board.Size.DEFAULT);
        Player first = new Player(Disc.GREEN);
        Player second = new Player(Disc.YELLOW);
        Player third = new Player(Disc.RED);
        Player fourth = new Player(Disc.BLUE);
        Queue<Player> players = new ArrayDeque<>(List.of(first, second, third, fourth));
        Game game = new Game(board, players);
        game.start();
    }
}
