package dev.cheercode.connectfour.game;

import dev.cheercode.connectfour.dialog.Dialog;
import dev.cheercode.connectfour.dialog.impl.IntegerMinMaxDialog;
import dev.cheercode.connectfour.factory.*;
import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.renderer.Renderer;

public class GameStarter {
    private static final String TITLE = """
            ==========================================================================================
            |                                      ИГРА 4 В РЯД                                      |
            | - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -|
            |                                     НАСТРОЙКА ИГРЫ                                     |
            ==========================================================================================
            """;
    private final PlayerFactory playerFactory;
    private final PlayerFactory botFactory;
    private final BoardSizeFactory boardSizeFactory;
    private final Renderer renderer;
    private final BoardShapeSelector boardShapeSelector;

    public GameStarter(PlayerFactory playerFactory, PlayerFactory botFactory, Renderer renderer, BoardSizeFactory boardSizeFactory, BoardShapeSelector boardShapeSelector) {
        this.playerFactory = playerFactory;
        this.botFactory = botFactory;
        this.renderer = renderer;
        this.boardSizeFactory = boardSizeFactory;
        this.boardShapeSelector = boardShapeSelector;
    }

    public void start() {
        System.out.println(TITLE);
        PlayerQueue players = createPlayers();
        Board.Size size = boardSizeFactory.create();
        Board board = new FromShapeBoardFactory(boardShapeSelector).create(size);
        Game game = new Game(board, players, renderer);
        game.start();
    }

    private PlayerQueue createPlayers() {
        int playerCount = getPlayerCount();
        PlayerQueue players = new PlayerQueue();

        for (int i = 0; i < playerCount; i++) {
            players.add(playerFactory.create(players));
        }

        if (players.size() == 1) {
            players.add(botFactory.create(players));
        }

        return players;
    }

    private int getPlayerCount() {
        int min = 1;
        int max = Math.min(Disc.values().length, 4);
        Dialog<Integer> dialog = new IntegerMinMaxDialog(
                String.format("Выберите количество игроков (%d - %d)", min, max),
                "Неправильный ввод.",
                min,
                max
        );
        return dialog.input();
    }
}
