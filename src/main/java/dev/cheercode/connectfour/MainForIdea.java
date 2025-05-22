package dev.cheercode.connectfour;

import dev.cheercode.connectfour.game.Game;
import dev.cheercode.connectfour.model.Player;
import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.renderer.Renderer;
import dev.cheercode.connectfour.renderer.RendererForIdea;

import java.util.Queue;

public class MainForIdea {
    public static void main(String[] args) {
        Menu menu = new Menu();
        Queue<Player> players = menu.createPlayers();

        Board board = new Board(Board.Size.DEFAULT);
        Renderer renderer = new RendererForIdea();
        Game game = new Game(board, players, renderer);
        game.start();
    }
}
