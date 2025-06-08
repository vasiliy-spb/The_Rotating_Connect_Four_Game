package dev.cheercode.connectfour;

import dev.cheercode.connectfour.factory.*;
import dev.cheercode.connectfour.game.BoardShapeSelector;
import dev.cheercode.connectfour.game.GameStarter;
import dev.cheercode.connectfour.renderer.Renderer;
import dev.cheercode.connectfour.renderer.renderer_for_idea.RendererForIdea;
import dev.cheercode.connectfour.renderer.renderer_for_idea.color.BackgroundColor;

public class MainForIdea {
    public static void main(String[] args) {
        PlayerFactory playerFactory = new ConsolePlayerFactory();
//        PlayerFactory botFactory = new RandomBotFactory();
        PlayerFactory botFactory = new VictoryScoreBasedBotFactory();
        Renderer renderer = new RendererForIdea();
        BoardSizeFactory boardSizeFactory = new ConsoleBoardSizeFactory();
        BoardShapeSelector boardShapeSelector = new BoardShapeSelector(BackgroundColor.BLUE);
        GameStarter gameStarter = new GameStarter(playerFactory, botFactory, renderer, boardSizeFactory, boardShapeSelector);
        gameStarter.start();
    }
}
