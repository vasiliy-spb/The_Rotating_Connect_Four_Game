package dev.cheercode.connectfour;

import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.model.Disc;

import java.util.Arrays;
import java.util.Random;

public class TestMain {
    public static void main(String[] args) {
        Disc[] discs = Disc.values();
        Random random = new Random();
        Board board = new Board(Board.Size.DEFAULT);

        ResultAnalyzer resultAnalyzer = new ResultAnalyzer(board);

        Renderer renderer = new Renderer();
        String template = "board.put(%d, Disc.%s);\n";

        StringBuilder log = new StringBuilder();
        while (!resultAnalyzer.isDraw(Arrays.stream(discs).toList())) {
            log = new StringBuilder();
            for (int column = 0; column < board.getWidth(); column++) {
                while (!board.isColumnFilled(column)) {
                    Disc disc = discs[random.nextInt(discs.length)];
                    log.append(String.format(template, column, disc.name()));
                    board.drop(column, disc);
                }
            }
        }
        System.out.println(log);
        System.out.println();
        renderer.show(board);
    }
    private Board createBoard() {
        Board board = new Board(Board.Size.DEFAULT);
        board.drop(0, Disc.BLUE);
        board.drop(0, Disc.GREEN);
        board.drop(0, Disc.BLUE);
        board.drop(0, Disc.RED);
        board.drop(0, Disc.RED);
        board.drop(0, Disc.GREEN);

        board.drop(1, Disc.GREEN);
        board.drop(1, Disc.GREEN);
        board.drop(1, Disc.YELLOW);
        board.drop(1, Disc.BLUE);
        board.drop(1, Disc.GREEN);
        board.drop(1, Disc.BLUE);

        board.drop(2, Disc.GREEN);
        board.drop(2, Disc.YELLOW);
        board.drop(2, Disc.GREEN);
        board.drop(2, Disc.BLUE);
        board.drop(2, Disc.RED);
        board.drop(2, Disc.RED);

        board.drop(3, Disc.YELLOW);
        board.drop(3, Disc.BLUE);
        board.drop(3, Disc.YELLOW);
        board.drop(3, Disc.YELLOW);
        board.drop(3, Disc.YELLOW);
        board.drop(3, Disc.RED);

        board.drop(4, Disc.GREEN);
        board.drop(4, Disc.YELLOW);
        board.drop(4, Disc.RED);
        board.drop(4, Disc.BLUE);
        board.drop(4, Disc.BLUE);
        board.drop(4, Disc.YELLOW);

        board.drop(5, Disc.RED);
        board.drop(5, Disc.RED);
        board.drop(5, Disc.RED);
        board.drop(5, Disc.GREEN);
        board.drop(5, Disc.BLUE);
        board.drop(5, Disc.YELLOW);

        board.drop(6, Disc.YELLOW);
        board.drop(6, Disc.BLUE);
        board.drop(6, Disc.BLUE);
        board.drop(6, Disc.BLUE);
        board.drop(6, Disc.RED);
        board.drop(6, Disc.GREEN);
        return board;
    }
}