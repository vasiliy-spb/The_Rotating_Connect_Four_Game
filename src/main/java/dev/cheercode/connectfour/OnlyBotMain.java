package dev.cheercode.connectfour;

import dev.cheercode.connectfour.factory.BlockingScoreBasedBotFactory;
import dev.cheercode.connectfour.factory.FromFileBoardFactory;
import dev.cheercode.connectfour.factory.VictoryScoreBasedBotFactory;
import dev.cheercode.connectfour.game.Game;
import dev.cheercode.connectfour.game.PlayerQueue;
import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.renderer.Renderer;
import dev.cheercode.connectfour.renderer.renderer_for_idea.RendererForIdea;

public class OnlyBotMain {
    public static void main(String[] args) {
        Board board = new FromFileBoardFactory().create(Board.Size.ROW7_COLUMN10);

        PlayerQueue players = new PlayerQueue();
        players.add(new VictoryScoreBasedBotFactory().create(players));
        players.add(new BlockingScoreBasedBotFactory().create(players));

        Renderer renderer = new RendererForIdea();

        Game game = new Game(board, players, renderer);
        game.start();
    }
}
