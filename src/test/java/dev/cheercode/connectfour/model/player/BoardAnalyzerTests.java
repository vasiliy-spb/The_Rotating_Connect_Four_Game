package dev.cheercode.connectfour.model.player;

import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.model.board.DefaultBoardState;
import dev.cheercode.connectfour.renderer.Renderer;
import dev.cheercode.connectfour.renderer.renderer_for_idea.RendererForIdea;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class BoardAnalyzerTests {
    private final Renderer renderer = new RendererForIdea();

    @Test
    public void test01() {
        Board board = createBoardWith(23, Board.Size.ROW7_COLUMN10);

        board.drop(0, Disc.YELLOW);
        board.drop(1, Disc.WHITE);
        board.drop(4, Disc.YELLOW);
        board.drop(5, Disc.CYAN);
        board.drop(9, Disc.YELLOW);

        renderer.show(board);

        int[] expected = {-1, 0, 2, 5, 3, 3, 5, 2, 1, -1};
        int[] columnBottoms = new BoardAnalyzer().findColumnBottoms(board);

        assertArrayEquals(expected, columnBottoms);
    }

    @Test
    public void test02() {
        Board board = createBoardWith(12, Board.Size.ROW7_COLUMN10);

        board.drop(0, Disc.YELLOW);
        board.drop(1, Disc.WHITE);
        board.drop(4, Disc.YELLOW);
        board.drop(5, Disc.CYAN);
        board.drop(9, Disc.YELLOW);

        renderer.show(board);

        int[] expected = {-1, -1, 6, 6, 0, 0, 6, 6, 0, -1};
        int[] columnBottoms = new BoardAnalyzer().findColumnBottoms(board);

        assertArrayEquals(expected, columnBottoms);
    }

    @Test
    public void test03() {
        Board board = createBoardWith(10, Board.Size.ROW7_COLUMN10);

        board.drop(0, Disc.YELLOW);
        board.drop(1, Disc.WHITE);
        board.drop(6, Disc.YELLOW);
        board.drop(7, Disc.CYAN);
        board.drop(7, Disc.CYAN);
        board.drop(7, Disc.CYAN);
        board.drop(9, Disc.YELLOW);

        renderer.show(board);

        int[] expected = {1, 1, 2, 2, -1, -1, 1, -1, 2, 1};
        int[] columnBottoms = new BoardAnalyzer().findColumnBottoms(board);

        assertArrayEquals(expected, columnBottoms);
    }

    @Test
    public void test04() {
        Board board = createBoardWith(9, Board.Size.ROW7_COLUMN10);

        board.drop(0, Disc.YELLOW);
        board.drop(1, Disc.WHITE);
        board.drop(6, Disc.YELLOW);
        board.drop(7, Disc.CYAN);
        board.drop(7, Disc.CYAN);
        board.drop(7, Disc.CYAN);
        board.drop(9, Disc.YELLOW);

        renderer.show(board);

        int[] expected = {-1, -1, 4, 5, 6, 6, 4, -1, 3, -1};
        int[] columnBottoms = new BoardAnalyzer().findColumnBottoms(board);

        assertArrayEquals(expected, columnBottoms);
    }

    private Board createBoardWith(int templateNumber, Board.Size size) {
        String maskNumber = String.valueOf(templateNumber);
        if (maskNumber.length() < 2) {
            maskNumber = '0' + maskNumber;
        }
        String path = "src/main/resources/board_masks/" + size.name().toLowerCase() + "/mask_" + maskNumber + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            StringBuilder input = new StringBuilder();
            while (reader.ready()) {
                input.append(reader.readLine().trim()).append("\n");
            }
            String[] lines = input.toString().trim().split("\n");
            int height = lines.length;
            int width = lines[0].length();
            boolean[][] mask = new boolean[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    char ch = lines[i].charAt(j);
                    if (ch == '+') {
                        mask[i][j] = true;
                    }
                }
            }
            return new Board(new DefaultBoardState(size, mask));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
