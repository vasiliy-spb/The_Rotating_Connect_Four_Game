package dev.cheercode.connectfour;

import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.model.board.DefaultBoardState;
import dev.cheercode.connectfour.model.player.BotFrequencyStrategyForTest;
import dev.cheercode.connectfour.renderer.Renderer;
import dev.cheercode.connectfour.renderer.renderer_for_idea.RendererForIdea;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class BotFrequencyStrategyTests {

    private final Renderer renderer = new RendererForIdea();

    @Test
    public void testFindColumnBottomsMethod01() throws Exception {
        Board board = createBoardWith(23, Board.Size.ROW7_COLUMN10);

        board.drop(0, Disc.YELLOW);
        board.drop(1, Disc.WHITE);
        board.drop(4, Disc.YELLOW);
        board.drop(5, Disc.CYAN);
        board.drop(9, Disc.YELLOW);

        renderer.show(board);

        int[] expected = {-1, 0, 2, 5, 3, 3, 5, 2, 1, -1};
        int[] columnBottoms = new BotFrequencyStrategyForTest().findColumnBottoms(board);

//                TestAccessHelper.callPrivateMethod(
//                new BotFrequencyStrategy(),
//                "findColumnBottoms",
//                new Class[]{Board.class},
//                board
//        );
        assertArrayEquals(expected, columnBottoms);
    }

    @Test
    public void testFindColumnBottomsMethod02() throws Exception {
        Board board = createBoardWith(12, Board.Size.ROW7_COLUMN10);

        board.drop(0, Disc.YELLOW);
        board.drop(1, Disc.WHITE);
        board.drop(4, Disc.YELLOW);
        board.drop(5, Disc.CYAN);
        board.drop(9, Disc.YELLOW);

        renderer.show(board);

        int[] expected = {-1, -1, 6, 6, 0, 0, 6, 6, 0, -1};
        int[] columnBottoms = new BotFrequencyStrategyForTest().findColumnBottoms(board);
//        int[] columnBottoms = TestAccessHelper.callPrivateMethod(
//                new BotFrequencyStrategy(),
//                "findColumnBottoms",
//                new Class[]{Board.class},
//                board
//        );
        assertArrayEquals(expected, columnBottoms);
    }

    @Test
    public void testFindColumnBottomsMethod03() throws Exception {
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
        int[] columnBottoms = new BotFrequencyStrategyForTest().findColumnBottoms(board);
//        int[] columnBottoms = TestAccessHelper.callPrivateMethod(
//                new BotFrequencyStrategy(),
//                "findColumnBottoms",
//                new Class[]{Board.class},
//                board
//        );
        assertArrayEquals(expected, columnBottoms);
    }

    @Test
    public void testFindColumnBottomsMethod04() throws Exception {
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
        int[] columnBottoms = new BotFrequencyStrategyForTest().findColumnBottoms(board);
//        int[] columnBottoms = TestAccessHelper.callPrivateMethod(
//                new BotFrequencyStrategy(),
//                "findColumnBottoms",
//                new Class[]{Board.class},
//                board
//        );
        System.out.println("Arrays.toString(columnBottoms) = " + Arrays.toString(columnBottoms));
        assertArrayEquals(expected, columnBottoms);
    }

    @Test
    public void testCreateFrequencyMatrixMethod01() throws Exception {
        Board board = createBoardWith(23, Board.Size.ROW7_COLUMN10);

        board.drop(0, Disc.YELLOW);
        board.drop(1, Disc.WHITE);
        board.drop(4, Disc.YELLOW);
        board.drop(5, Disc.CYAN);
        board.drop(9, Disc.YELLOW);

        renderer.show(board);

        int[][] expected = {
                {8, 1, 1, -1, -1, -1, -1, 1, 1, 8},
                {-1, -8, 1, 1, -1, -1, 1, 1, 1, -1},
                {-1, -1, 1, 1, 1, 1, 1, 1, -1, -1},
                {-1, -1, -1, 1, 1, 1, 1, -1, -1, -1},
                {-1, -1, 1, 1, 8, -8, 1, 1, -1, -1},
                {-1, 1, 1, 1, -1, -1, 1, 1, 1, -1},
                {1, 1, 1, -1, -1, -1, -1, 1, 1, 1}
        };
        int[][] frequencyMatrix = new BotFrequencyStrategyForTest().createFrequencyMatrix(Disc.YELLOW, board);
//        int[][] frequencyMatrix = TestAccessHelper.callPrivateMethod(
//                new BotFrequencyStrategy(),
//                "createFrequencyMatrix",
//                new Class[]{Disc.class, Board.class},
//                new Object[]{Disc.YELLOW, board}
//        );
        printMatrix(frequencyMatrix);
        assertArrayEquals(expected, frequencyMatrix);
    }

    @Test
    public void testCreateFrequencyMatrixMethod02() throws Exception {
        Board board = createBoardWith(12, Board.Size.ROW7_COLUMN10);

        board.drop(0, Disc.YELLOW);
        board.drop(1, Disc.WHITE);
        board.drop(4, Disc.YELLOW);
        board.drop(5, Disc.CYAN);
        board.drop(9, Disc.YELLOW);

        renderer.show(board);

        int[][] expected = {
                {8, -8, 1, 1, 1, 1, 1, 1, 1, 8},
                {-1, -1, 1, 1, 8, -8, 1, 1, -1, -1},
                {1, 1, 1, 1, -1, -1, 1, 1, 1, 1},
                {-1, -1, 1, 1, 1, 1, 1, 1, -1, -1},
                {1, 1, 1, 1, -1, -1, 1, 1, 1, 1},
                {-1, -1, 1, 1, 1, 1, 1, 1, -1, -1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        };
        int[][] frequencyMatrix = new BotFrequencyStrategyForTest().createFrequencyMatrix(Disc.YELLOW, board);
//        int[][] frequencyMatrix = TestAccessHelper.callPrivateMethod(
//                new BotFrequencyStrategy(),
//                "createFrequencyMatrix",
//                new Class[]{Disc.class, Board.class},
//                new Object[]{Disc.YELLOW, board}
//        );
        printMatrix(frequencyMatrix);
        assertArrayEquals(expected, frequencyMatrix);
    }

    @Test
    public void testCreateFrequencyMatrixMethod03() throws Exception {
        Board board = createBoardWith(10, Board.Size.ROW7_COLUMN10);

        board.drop(0, Disc.YELLOW);
        board.drop(1, Disc.WHITE);
        board.drop(6, Disc.YELLOW);
        board.drop(7, Disc.CYAN);
        board.drop(7, Disc.CYAN);
        board.drop(7, Disc.CYAN);
        board.drop(9, Disc.YELLOW);

        renderer.show(board);

        int[][] expected = {
                {1, 1, 1, 1, -1, -1, 1, -8, 1, 1},
                {1, 1, 1, 1, -1, -1, 1, -8, 1, 1},
                {8, -8, 1, 1, -1, -1, 8, -8, 1, 8},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {1, 1, 1, 1, -1, -1, 1, 1, 1, 1},
                {1, 1, 1, 1, -1, -1, 1, 1, 1, 1},
                {1, 1, 1, 1, -1, -1, 1, 1, 1, 1}
        };
        int[][] frequencyMatrix = new BotFrequencyStrategyForTest().createFrequencyMatrix(Disc.YELLOW, board);
//        int[][] frequencyMatrix = TestAccessHelper.callPrivateMethod(
//                new BotFrequencyStrategy(),
//                "createFrequencyMatrix",
//                new Class[]{Disc.class, Board.class},
//                new Object[]{Disc.YELLOW, board}
//        );
        printMatrix(frequencyMatrix);
        assertArrayEquals(expected, frequencyMatrix);
    }

    @Test
    public void testCreateFrequencyMatrixMethod04() throws Exception {
        Board board = createBoardWith(9, Board.Size.ROW7_COLUMN10);

        board.drop(0, Disc.YELLOW);
        board.drop(1, Disc.WHITE);
        board.drop(6, Disc.YELLOW);
        board.drop(7, Disc.CYAN);
        board.drop(7, Disc.CYAN);
        board.drop(7, Disc.CYAN);
        board.drop(9, Disc.YELLOW);

        renderer.show(board);

        int[][] expected = {
                {-1, -1, -1, -1, 1, 1, -1, -1, -1, -1},
                {-1, -1, -1, 1, 1, 1, 1, -1, -1, -1},
                {-1, -1, 1, 1, 1, 1, 1, -8, -1, -1},
                {8, -8, 1, 1, 1, 1, 1, -8, 1, 8},
                {-1, -1, 1, 1, 1, 1, 1, -8, -1, -1},
                {-1, -1, -1, 1, 1, 1, 8, -1, -1, -1},
                {-1, -1, -1, -1, 1, 1, -1, -1, -1, -1}
        };
        int[][] frequencyMatrix = new BotFrequencyStrategyForTest().createFrequencyMatrix(Disc.YELLOW, board);
//        int[][] frequencyMatrix = TestAccessHelper.callPrivateMethod(
//                new BotFrequencyStrategy(),
//                "createFrequencyMatrix",
//                new Class[]{Disc.class, Board.class},
//                new Object[]{Disc.YELLOW, board}
//        );
        printMatrix(frequencyMatrix);
        assertArrayEquals(expected, frequencyMatrix);
    }

    @Test
    public void testCreateFrequencyMatrixMethod05() throws Exception {
//        Board board = createBoardWith(6, Board.Size.ROW7_COLUMN10);
//        Board board = createBoardWith(14, Board.Size.ROW7_COLUMN10);
//        Board board = createBoardWith(17, Board.Size.ROW7_COLUMN10);
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

        // выгоднее положит диск в index == 8

        int[][] expected = {
                {-1, -1, -1, -1, 1, 1, -1, -1, -1, -1},
                {-1, -1, -1, 1, 1, 1, 1, -1, -1, -1},
                {-1, -1, 1, 1, 1, 1, 1, -8, -1, -1},
                {8, -8, 1, 1, 1, 1, 1, -8, 1, 8},
                {-1, -1, 1, 1, 1, 1, 1, -8, -1, -1},
                {-1, -1, -1, 1, 1, 1, 8, -1, -1, -1},
                {-1, -1, -1, -1, 1, 1, -1, -1, -1, -1}
        };
        int[][] frequencyMatrix = new BotFrequencyStrategyForTest().createFrequencyMatrix(Disc.YELLOW, board);
//        int[][] frequencyMatrix = TestAccessHelper.callPrivateMethod(
//                new BotFrequencyStrategy(),
//                "createFrequencyMatrix",
//                new Class[]{Disc.class, Board.class},
//                new Object[]{Disc.YELLOW, board}
//        );
        printMatrix(frequencyMatrix);
        assertArrayEquals(expected, frequencyMatrix);
    }

    @Test
    public void testCreateFrequencyMatrixMethod06() throws Exception {
//        Board board = createBoardWith(6, Board.Size.ROW7_COLUMN10);
//        Board board = createBoardWith(14, Board.Size.ROW7_COLUMN10);
//        Board board = createBoardWith(17, Board.Size.ROW7_COLUMN10);
        Board board = createBoardWith(19, Board.Size.ROW7_COLUMN10);

        board.drop(7, Disc.YELLOW);
        board.drop(0, Disc.YELLOW);
        board.drop(1, Disc.YELLOW);
        board.drop(3, Disc.WHITE);
        board.drop(5, Disc.YELLOW);
        board.drop(4, Disc.CYAN);
        board.drop(4, Disc.YELLOW);

        renderer.show(board);

        // выгоднее положит диск в index == 5

        int[][] expected = {
                {-1, -1, -1, -1, 1, 1, -1, -1, -1, -1},
                {-1, -1, -1, 1, 1, 1, 1, -1, -1, -1},
                {-1, -1, 1, 1, 1, 1, 1, -8, -1, -1},
                {8, -8, 1, 1, 1, 1, 1, -8, 1, 8},
                {-1, -1, 1, 1, 1, 1, 1, -8, -1, -1},
                {-1, -1, -1, 1, 1, 1, 8, -1, -1, -1},
                {-1, -1, -1, -1, 1, 1, -1, -1, -1, -1}
        };
        int[][] frequencyMatrix = new BotFrequencyStrategyForTest().createFrequencyMatrix(Disc.YELLOW, board);
//        int[][] frequencyMatrix = TestAccessHelper.callPrivateMethod(
//                new BotFrequencyStrategy(),
//                "createFrequencyMatrix",
//                new Class[]{Disc.class, Board.class},
//                new Object[]{Disc.YELLOW, board}
//        );
        printMatrix(frequencyMatrix);
        assertArrayEquals(expected, frequencyMatrix);
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

    private void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
