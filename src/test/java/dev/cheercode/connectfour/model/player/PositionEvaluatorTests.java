package dev.cheercode.connectfour.model.player;

import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.model.board.DefaultBoardState;
import dev.cheercode.connectfour.model.board.Direction;
import dev.cheercode.connectfour.renderer.Renderer;
import dev.cheercode.connectfour.renderer.renderer_for_idea.RendererForIdea;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class PositionEvaluatorTests {
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

        int[] expected = {-1, 4, 4, 816, 14, 824, 4, 15, 811, -1};

        int[] positions = new BoardAnalyzer().findColumnBottoms(board);
        int[] scores = new PositionEvaluator().calculateScoresFor(Disc.YELLOW, positions, board);

        assertArrayEquals(expected, scores);
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

        int[] expected = {-1, -1, 7, 8, 7, 819, 8, 7, 816, -1};

        int[] positions = new BoardAnalyzer().findColumnBottoms(board);
        int[] scores = new PositionEvaluator().calculateScoresFor(Disc.YELLOW, positions, board);

        assertArrayEquals(expected, scores);
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

        int[] expected = {4, 4, 0, 0, -1, -1, 0, -1, 0, 0};

        int[] positions = new BoardAnalyzer().findColumnBottoms(board);
        int[] scores = new PositionEvaluator().calculateScoresFor(Disc.YELLOW, positions, board);

        assertArrayEquals(expected, scores);
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

        int[] expected = {-1, -1, 8, 19, 4, 8, 821, -1, 0, -1};

        int[] positions = new BoardAnalyzer().findColumnBottoms(board);
        int[] scores = new PositionEvaluator().calculateScoresFor(Disc.YELLOW, positions, board);

        assertArrayEquals(expected, scores);
    }

    @Test
    public void test05() {
        Board board = createBoardWith(19, Board.Size.ROW7_COLUMN10);

        board.drop(6, Disc.YELLOW);
        board.drop(7, Disc.YELLOW);
        board.drop(0, Disc.YELLOW);
        board.drop(1, Disc.YELLOW);
        board.drop(3, Disc.WHITE);
        board.drop(5, Disc.YELLOW);
        board.drop(4, Disc.CYAN);
        board.drop(4, Disc.YELLOW);

        renderer.show(board);

        int[] expected = {-1, -1, 0, -1, 816, 1623, -1, -1, 8820, 18};

        int[] positions = new BoardAnalyzer().findColumnBottoms(board);
        int[] scores = new PositionEvaluator().calculateScoresFor(Disc.YELLOW, positions, board);

        assertArrayEquals(expected, scores);
    }

    @Test
    public void test06() {
        Board board = createBoardWith(19, Board.Size.ROW7_COLUMN10);

        board.drop(7, Disc.YELLOW);
        board.drop(0, Disc.YELLOW);
        board.drop(1, Disc.YELLOW);
        board.drop(3, Disc.WHITE);
        board.drop(5, Disc.YELLOW);
        board.drop(4, Disc.CYAN);
        board.drop(4, Disc.YELLOW);

        renderer.show(board);

        int[] expected = {-1, -1, 0, -1, 816, 1623, 817, -1, 813, 11};

        int[] positions = new BoardAnalyzer().findColumnBottoms(board);
        int[] scores = new PositionEvaluator().calculateScoresFor(Disc.YELLOW, positions, board);

        assertArrayEquals(expected, scores);
    }

    @Test
    public void test07() {
        Board board = createBoardWith(19, Board.Size.ROW7_COLUMN10);

        board.rotate(Direction.LEFT);
        board.drop(4, Disc.WHITE);
        board.drop(4, Disc.WHITE);
        board.drop(4, Disc.GREEN);
        board.drop(4, Disc.WHITE);
        board.rotate(Direction.RIGHT);

        board.drop(7, Disc.YELLOW);
        board.drop(0, Disc.YELLOW);
        board.drop(1, Disc.YELLOW);
        board.drop(3, Disc.WHITE);
        board.drop(4, Disc.CYAN);
        board.drop(4, Disc.YELLOW);
        board.drop(4, Disc.CYAN);
        board.drop(4, Disc.YELLOW);

        renderer.show(board);

        int[] expected = {-1, -1, 0, -1, 814, 4, 814, -1, 813, 11};

        int[] positions = new BoardAnalyzer().findColumnBottoms(board);
        int[] scores = new PositionEvaluator().calculateScoresFor(Disc.YELLOW, positions, board);

        assertArrayEquals(expected, scores);
    }

    @Test
    public void test08() {
        Board board = createBoardWith(19, Board.Size.ROW7_COLUMN10);

        board.rotate(Direction.LEFT);
        board.drop(4, Disc.WHITE);
        board.drop(4, Disc.WHITE);
        board.rotate(Direction.RIGHT);

        board.drop(7, Disc.YELLOW);
        board.drop(0, Disc.YELLOW);
        board.drop(1, Disc.YELLOW);
        board.drop(3, Disc.WHITE);
        board.drop(4, Disc.CYAN);
        board.drop(4, Disc.YELLOW);

        renderer.show(board);

        int[] expected = {-1, -1, 0, -1, 815, 4, 817, -1, 813, 11};

        int[] positions = new BoardAnalyzer().findColumnBottoms(board);
        int[] scores = new PositionEvaluator().calculateScoresFor(Disc.YELLOW, positions, board);

        assertArrayEquals(expected, scores);
    }

    @Test
    public void test09() {
        Board board = createBoardWith(15, Board.Size.ROW7_COLUMN10);

        board.rotate(Direction.RIGHT);

        board.drop(3, Disc.BLACK);
        board.drop(3, Disc.BLACK);
        board.drop(3, Disc.BLACK);
        board.drop(2, Disc.BLACK);
        board.drop(4, Disc.RED);
        board.drop(5, Disc.RED);
        board.drop(5, Disc.RED);
        board.drop(5, Disc.RED);
        board.drop(6, Disc.RED);

        renderer.show(board);

        int[] expected = {22, 8822, 2437, 88835, 1626, 6, 0};

        int[] positions = new BoardAnalyzer().findColumnBottoms(board);
        int[] scores = new PositionEvaluator().calculateScoresFor(Disc.BLACK, positions, board);

        assertArrayEquals(expected, scores);
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
