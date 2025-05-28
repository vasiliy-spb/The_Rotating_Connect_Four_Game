package dev.cheercode.connectfour;

import dev.cheercode.connectfour.factory.ConsolePlayerFactory;
import dev.cheercode.connectfour.factory.PlayerFactory;
import dev.cheercode.connectfour.factory.BoardSizeFactory;
import dev.cheercode.connectfour.factory.ConsoleBoardSizeFactory;
import dev.cheercode.connectfour.game.GameStarter;
import dev.cheercode.connectfour.renderer.Renderer;
import dev.cheercode.connectfour.renderer.RendererForJar;
import org.fusesource.jansi.AnsiConsole;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

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
            Renderer renderer = new RendererForJar();
            BoardSizeFactory boardSizeFactory = new ConsoleBoardSizeFactory();
            GameStarter gameStarter = new GameStarter(playerFactory, renderer, boardSizeFactory);
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
}