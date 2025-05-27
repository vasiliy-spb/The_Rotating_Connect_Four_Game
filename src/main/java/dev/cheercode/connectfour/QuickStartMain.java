package dev.cheercode.connectfour;

import dev.cheercode.connectfour.game.Game;
import dev.cheercode.connectfour.game.PlayerQueue;
import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.Player;
import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.model.board.DefaultBoardState;
import dev.cheercode.connectfour.renderer.Renderer;
import dev.cheercode.connectfour.renderer.RendererForIdea;
import dev.cheercode.connectfour.renderer.renderer_oop_decorator.RendererOOP;

public class QuickStartMain {
    public static void main(String[] args) {
        Board board = new Board(new DefaultBoardState(Board.Size.DEFAULT));

        PlayerQueue players = new PlayerQueue();
        players.add(new Player("Игрок 1", Disc.RED));
        players.add(new Player("Игрок 2", Disc.YELLOW));

        Renderer renderer = new RendererOOP();

        Game game = new Game(board, players, renderer);
        game.start();
    }
}
