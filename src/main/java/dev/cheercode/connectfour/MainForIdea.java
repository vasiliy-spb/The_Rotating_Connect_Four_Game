package dev.cheercode.connectfour;

import dev.cheercode.connectfour.factory.ConsolePlayerFactory;
import dev.cheercode.connectfour.factory.PlayerFactory;
import dev.cheercode.connectfour.factory.BoardSizeFactory;
import dev.cheercode.connectfour.factory.ConsoleBoardSizeFactory;
import dev.cheercode.connectfour.game.BoardShapeSelector;
import dev.cheercode.connectfour.game.GameStarter;
import dev.cheercode.connectfour.renderer.Renderer;
import dev.cheercode.connectfour.renderer.renderer_for_idea.RendererForIdea;
import dev.cheercode.connectfour.renderer.renderer_for_idea.color.BackgroundColor;

public class MainForIdea {
    public static void main(String[] args) {
        PlayerFactory playerFactory = new ConsolePlayerFactory();
        Renderer renderer = new RendererForIdea();
        BoardSizeFactory boardSizeFactory = new ConsoleBoardSizeFactory();
        BoardShapeSelector boardShapeSelector = new BoardShapeSelector(BackgroundColor.BLUE);
        GameStarter gameStarter = new GameStarter(playerFactory, renderer, boardSizeFactory, boardShapeSelector);
        gameStarter.start();
    }
}
