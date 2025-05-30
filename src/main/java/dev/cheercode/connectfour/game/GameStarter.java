package dev.cheercode.connectfour.game;

import dev.cheercode.connectfour.dialog.Dialog;
import dev.cheercode.connectfour.dialog.impl.IntegerMinMaxDialog;
import dev.cheercode.connectfour.factory.*;
import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.renderer.Renderer;
import dev.cheercode.connectfour.renderer.renderer_for_idea.color.BackgroundColor;

public class GameStarter {
    private static final String TITLE = """
            ==========================================================================================
            |                                      ИГРА 4 В РЯД                                      |
            | - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -|
            |                                     НАСТРОЙКА ИГРЫ                                     |
            ==========================================================================================
            """;
    private final PlayerFactory playerFactory;
    private final BoardSizeFactory boardSizeFactory;
    private final Renderer renderer;

    public GameStarter(PlayerFactory playerFactory, Renderer renderer, BoardSizeFactory boardSizeFactory) {
        this.playerFactory = playerFactory;
        this.renderer = renderer;
        this.boardSizeFactory = boardSizeFactory;
    }

    public void start() {
        System.out.println(TITLE);
        PlayerQueue players = createPlayers();
        Board.Size size = boardSizeFactory.create();
        BoardShapeSelector boardShapeSelector = new BoardShapeSelector(BackgroundColor.BLUE);
        Board board = new FromShapeBoardFactory(boardShapeSelector).create(size);
        Game game = new Game(board, players, renderer);
        game.start();
    }

    private PlayerQueue createPlayers() {
        int playerCount = getPlayerCount();
        PlayerQueue players = new PlayerQueue();

        for (int i = 1; i <= playerCount; i++) {
            players.add(playerFactory.create(i));
        }

        return players;
    }

    private int getPlayerCount() {
        int min = 2;
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
