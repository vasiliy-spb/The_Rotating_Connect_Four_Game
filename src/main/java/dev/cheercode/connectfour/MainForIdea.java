package dev.cheercode.connectfour;

import dev.cheercode.connectfour.factory.*;
import dev.cheercode.connectfour.game.BoardShapeSelector;
import dev.cheercode.connectfour.game.GameStarter;
import dev.cheercode.connectfour.renderer.Renderer;
import dev.cheercode.connectfour.renderer.renderer_for_idea.RendererForIdea;
import dev.cheercode.connectfour.renderer.renderer_for_idea.color.BackgroundColor;

import java.util.List;
import java.util.Random;

public class MainForIdea {
    public static void main(String[] args) {
        PlayerFactory playerFactory = new ConsolePlayerFactory();
        PlayerFactory botFactory = selectRandomFactory();
        Renderer renderer = new RendererForIdea();
        BoardSizeFactory boardSizeFactory = new ConsoleBoardSizeFactory();
        BoardShapeSelector boardShapeSelector = new BoardShapeSelector(BackgroundColor.BLUE);
        GameStarter gameStarter = new GameStarter(playerFactory, botFactory, renderer, boardSizeFactory, boardShapeSelector);
        gameStarter.start();
    }

    private static PlayerFactory selectRandomFactory() {
        List<PlayerFactory> factories = List.of(
                new RandomBotFactory(),
                new VictoryScoreBasedBotFactory(),
                new BlockingScoreBasedBotFactory(),
                new ScoreBasedBotFactory()
        );
        Random random = new Random();
        int factoryIndex = random.nextInt(factories.size());
        return factories.get(factoryIndex);
    }
}
