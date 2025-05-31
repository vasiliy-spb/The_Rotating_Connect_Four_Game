package dev.cheercode.connectfour;

import dev.cheercode.connectfour.factory.FromFileBoardFactory;
import dev.cheercode.connectfour.game.Game;
import dev.cheercode.connectfour.game.PlayerQueue;
import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.player.HumanConsoleInputStrategy;
import dev.cheercode.connectfour.model.player.Player;
import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.renderer.Renderer;
import dev.cheercode.connectfour.renderer.renderer_for_idea.RendererForIdea;

public class QuickStartMain {
    public static void main(String[] args) {
        Board board = new FromFileBoardFactory().create(Board.Size.ROW6_COLUMN7);

        PlayerQueue players = new PlayerQueue();
        players.add(new Player("Игрок 1", Disc.RED, new HumanConsoleInputStrategy()));
        players.add(new Player("Игрок 2", Disc.YELLOW, new HumanConsoleInputStrategy()));

        Renderer renderer = new RendererForIdea();

        Game game = new Game(board, players, renderer);
        game.start();
    }
}
