package dev.cheercode.connectfour.game;

import dev.cheercode.connectfour.dialog.impl.IntegerMinMaxDialog;
import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.dialog.Dialog;
import dev.cheercode.connectfour.model.Player;
import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Direction;
import dev.cheercode.connectfour.renderer.Renderer;

import java.util.*;

public class Game {
    private static final String GAME_TITLE = """
            ==========================================================================================
            |                                      ИГРА 4 В РЯД                                      |
            | - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -|
            |                                         СТАРТ                                          |
            ==========================================================================================
            """;
    private static final String COLUMN_FILLED_MESSAGE_TEMPLATE = "Колонка %s занята\n";
    private static final String DRAW_MESSAGE = "Ничья.";
    private static final String SINGLE_VICTORY_MESSAGE = "Победил игрок:";
    private static final String MULTI_VICTORY_MESSAGE = "Победу разделили игроки:";
    private static final String BOARD_ROTATED_MESSAGE_TEMPLATE = "\nДоска повернулась: %s\n\n";
    private static final String DIALOG_ERROR_MESSAGE = "Неправильный ввод";
    private static final String DIALOG_TITLE_TEMPLATE = "%s, сделайте ход (%d - %d)%n";
    private static final Map<Direction, String> ROTATION_SYMBOLS = Map.of(
            Direction.LEFT, "↶ (влево)",
            Direction.RIGHT, "↷ (вправо)",
            Direction.UPSIDE_DOWN, "⟲ (перевернулась)"
    );
    private final Board board;
    private final PlayerQueue players;
    private final Renderer renderer;
    private final ResultAnalyzer resultAnalyzer;
    private final List<Disc> discs;
    private final Random random;
    private Player currentPlayer;

    public Game(Board board, PlayerQueue players, Renderer renderer) {
        this.board = board;
        this.players = players;
        this.renderer = renderer;
        this.resultAnalyzer = new ResultAnalyzer(this.board);
        this.discs = players.toList().stream().map(Player::getDisc).toList();
        this.currentPlayer = players.peekNext();
        this.random = new Random();
    }

    public void start() {
        System.out.println(GAME_TITLE);
        renderer.show(board);

        while (!isGameOver()) {
            nextTurn();
        }

        finish();
    }

    private void nextTurn() {
        currentPlayer = players.pollNext();
        makeMove();
        players.add(currentPlayer);

        if (isCurrentPlayerWin()) {
            return;
        }

        if (shouldBoardRotate()) {
            rotateBoard();
            renderer.show(board);
        }
    }

    private void makeMove() {
        int columnIndex = askColumnIndex();

        while (board.isColumnFilled(columnIndex)) {
            System.out.printf(COLUMN_FILLED_MESSAGE_TEMPLATE, columnIndex + 1);
            columnIndex = askColumnIndex();
        }

        Disc disc = currentPlayer.getDisc();
        board.drop(columnIndex, disc);
        renderer.show(board);
    }

    private boolean isCurrentPlayerWin() {
        Disc disc = currentPlayer.getDisc();
        return resultAnalyzer.isWin(disc);
    }

    private void finish() {
        if (isDraw()) {
            System.out.println(DRAW_MESSAGE);
            return;
        }

        Set<Disc> winnerDiscs = resultAnalyzer.getWinnerDiscs(discs);

        if (winnerDiscs.size() == 1) {
            System.out.println(SINGLE_VICTORY_MESSAGE);
        } else {
            System.out.println(MULTI_VICTORY_MESSAGE);
        }

        players.toList().stream()
                .filter(p -> winnerDiscs.contains(p.getDisc()))
                .forEach(p -> System.out.println(p.getName()));
    }

    private void rotateBoard() {
        Direction[] directions = Direction.values();
        int directionIndex = random.nextInt(directions.length);
        Direction direction = directions[directionIndex];

        System.out.printf((BOARD_ROTATED_MESSAGE_TEMPLATE), ROTATION_SYMBOLS.get(direction));
        board.rotate(direction);
    }

    private boolean shouldBoardRotate() {
        return random.nextInt(100) <= 33;
    }

    private int askColumnIndex() {
        int min = 1;
        int max = board.getWidth();
        String title = String.format(DIALOG_TITLE_TEMPLATE, currentPlayer.getName(), min, max);
        Dialog<Integer> dialog = new IntegerMinMaxDialog(title, DIALOG_ERROR_MESSAGE, min, max);
        int columnNumber = dialog.input();
        return columnNumber - 1;
    }

    private boolean isGameOver() {
        return hasWinner() || isDraw();
    }

    private boolean hasWinner() {
        for (Disc disc : discs) {
            if (resultAnalyzer.isWin(disc)) {
                return true;
            }
        }
        return false;
    }

    private boolean isDraw() {
        return resultAnalyzer.isDraw(discs);
    }
}
