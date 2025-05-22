package dev.cheercode.connectfour;

import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.dialog.Dialog;
import dev.cheercode.connectfour.dialog.IntegerMinMaxDialog;
import dev.cheercode.connectfour.model.Player;
import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Direction;

import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class Game {
    private static final String gameTitle = """
            ==========================================================================================
            |                                      ИГРА 4 В РЯД                                      |
            ==========================================================================================
            """;
    private static final String columnFilledMessageTemplate = "Колонка %s занята\n";
    private static final String drawMessage = "Ничья.";
    private static final String singleVictoryMessage = "Победил игрок:";
    private static final String multiVictoryMessage = "Победу разделили игроки:";
    private static final String boardRotatedMessageTemplate = "\nДоска повернулась: %s\n\n";
    private static final String dialogErrorMessage = "Неправильный ввод";
    private static final String dialogTitleTemplate = "%s, сделайте ход (%d - %d)%n";
    private final Board board;
    private final Queue<Player> players;
    private final Renderer renderer;
    private final ResultAnalyzer resultAnalyzer;
    private final List<Disc> discs;
    private final Random random;
    private Player currentPlayer;

    public Game(Board board, Queue<Player> players, Renderer renderer) {
        this.board = board;
        this.players = players;
        this.renderer = renderer;
        this.resultAnalyzer = new ResultAnalyzer(this.board);
        this.discs = players.stream().map(Player::getDisc).toList();
        this.currentPlayer = players.peek();
        this.random = new Random();
    }

    public void start() {
        System.out.println(gameTitle);
        renderer.show(board);

        while (!isGameOver()) {
            nextTurn();
        }

        finish();
    }

    private void nextTurn() {
        currentPlayer = players.poll();
        int columnIndex = askColumnIndex();

        if (board.isColumnFilled(columnIndex)) {
            System.out.printf(columnFilledMessageTemplate, columnIndex + 1);
            return;
        }

        Disc disc = currentPlayer.getDisc();
        board.drop(columnIndex, disc);
        renderer.show(board);

        players.offer(currentPlayer);

        if (shouldRotate()) {
            rotateBoard();
            renderer.show(board);
        }
    }

    private void finish() {
        if (isDraw()) {
            System.out.println(drawMessage);
            return;
        }

        Set<Disc> winnerDiscs = resultAnalyzer.getWinnerDiscs(discs);

        if (winnerDiscs.size() == 1) {
            System.out.println(singleVictoryMessage);
        } else {
            System.out.println(multiVictoryMessage);
        }

        players.stream()
                .filter(p -> winnerDiscs.contains(p.getDisc()))
                .forEach(p -> System.out.println(p.getName()));
    }

    private void rotateBoard() {
        Direction[] directions = Direction.values();
        int directionIndex = random.nextInt(directions.length);
        Direction direction = directions[directionIndex];
        System.out.printf(boardRotatedMessageTemplate, direction.name());
        board.rotate(direction);
    }

    private boolean shouldRotate() {
        return random.nextInt(100) <= 33;
    }

    private int askColumnIndex() {
        int min = 1;
        int max = board.getWidth();
        String title = String.format(dialogTitleTemplate, currentPlayer.getName(), min, max);
        Dialog<Integer> dialog = new IntegerMinMaxDialog(title, dialogErrorMessage, min, max);
        int columnNumber = dialog.input();
        return columnNumber - 1;
    }

    private boolean isGameOver() {
        return isWin() || isDraw();
    }

    private boolean isWin() {
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
