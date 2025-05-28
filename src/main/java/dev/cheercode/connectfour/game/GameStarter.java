package dev.cheercode.connectfour.game;

import dev.cheercode.connectfour.dialog.Dialog;
import dev.cheercode.connectfour.dialog.impl.IntegerMinMaxDialog;
import dev.cheercode.connectfour.factory.BoardSizeFactory;
import dev.cheercode.connectfour.factory.FromFileBoardFactory;
import dev.cheercode.connectfour.factory.PlayerFactory;
import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.model.board.DefaultBoardState;
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
//        Board board = new Board(new DefaultBoardState(size));
        Board board = new FromFileBoardFactory().create();
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
        int max = Disc.values().length;
        Dialog<Integer> dialog = new IntegerMinMaxDialog(
                String.format("Выберите количество игроков (%d - %d)", min, max),
                "Неправильный ввод.",
                min,
                max
        );
        return dialog.input();
    }
}
