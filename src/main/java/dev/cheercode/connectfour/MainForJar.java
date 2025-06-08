package dev.cheercode.connectfour;

import dev.cheercode.connectfour.factory.*;
import dev.cheercode.connectfour.game.BoardShapeSelector;
import dev.cheercode.connectfour.game.GameStarter;
import dev.cheercode.connectfour.renderer.Renderer;
import dev.cheercode.connectfour.renderer.RendererForJar;
import dev.cheercode.connectfour.renderer.renderer_for_idea.color.BackgroundColor;
import org.fusesource.jansi.AnsiConsole;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;

public class MainForJar {
    public static void main(String[] args) {

        PrintStream originalOut = System.out;
        String originalEncoding = System.getProperty("file.encoding");
        try {
            configureWindowsConsole();
            System.setProperty("file.encoding", "Cp866");
            System.setOut(new PrintStream(System.out, true, "Cp866"));

            AnsiConsole.systemInstall();

            PlayerFactory playerFactory = new ConsolePlayerFactory();
            PlayerFactory botFactory = selectRandomFactory();
            Renderer renderer = new RendererForJar();
            BoardSizeFactory boardSizeFactory = new ConsoleBoardSizeFactory();
            BoardShapeSelector boardShapeSelector = new BoardShapeSelector(BackgroundColor.CYAN);
            GameStarter gameStarter = new GameStarter(playerFactory, botFactory, renderer, boardSizeFactory, boardShapeSelector);
            gameStarter.start();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } finally {
            AnsiConsole.systemUninstall();
            System.setOut(originalOut);
            System.setProperty("file.encoding", originalEncoding);
        }
    }

    private static void configureWindowsConsole() {
        if (!System.getProperty("os.name").toLowerCase().contains("win")) {
            return;
        }

        try {
            new ProcessBuilder("cmd", "/c", "chcp", "866").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.err.println("Ошибка смены кодовой страницы: " + e.getMessage());
        }
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