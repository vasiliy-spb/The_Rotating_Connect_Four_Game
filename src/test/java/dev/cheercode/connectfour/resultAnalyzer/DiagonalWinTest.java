package dev.cheercode.connectfour.resultAnalyzer;

import dev.cheercode.connectfour.model.board.DefaultBoardState;
import dev.cheercode.connectfour.renderer.Renderer;
import dev.cheercode.connectfour.game.ResultAnalyzer;
import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.renderer.renderer_for_idea.RendererForIdea;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DiagonalWinTest {
    private final Board.Size DEFAULT_SIZE = Board.Size.DEFAULT;
    private final Renderer renderer = new RendererForIdea();

    private boolean[][] getMask(Board.Size size) {
        int height = size.getHeight();
        int width = size.getWidth();
        boolean[][] mask = new boolean[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                mask[i][j] = true;
            }
        }
        return mask;
    }
    @Test
    void diagonalWin_topLeftToBottomRight_shouldReturnTrue() {
        // Given
        Board board = new Board(new DefaultBoardState(DEFAULT_SIZE, getMask(DEFAULT_SIZE)));

        // Диагональ из левого верхнего в правый нижний угол
        board.drop(0, Disc.RED);    // [5,0]
        board.drop(1, Disc.RED);    // [4,1]
        board.drop(2, Disc.RED);    // [3,2]
        board.drop(3, Disc.RED);    // [2,3]

        System.out.println("1. Diagonal win (top-left to bottom-right):");
        renderer.show(board);

        // When
        ResultAnalyzer analyzer = new ResultAnalyzer(board);

        // Then
        assertTrue(analyzer.isWin(Disc.RED), "Should detect diagonal win");
        assertFalse(analyzer.isWin(Disc.BLUE), "Blue should not win");
    }

    @Test
    void diagonalWin_topRightToBottomLeft_shouldReturnTrue() {
        // Given
        Board board = new Board(new DefaultBoardState(DEFAULT_SIZE, getMask(DEFAULT_SIZE)));

        // Диагональ из правого верхнего в левый нижний угол
        board.drop(6, Disc.BLUE);   // [5,6]
        board.drop(5, Disc.BLUE);   // [4,5]
        board.drop(4, Disc.BLUE);   // [3,4]
        board.drop(3, Disc.BLUE);   // [2,3]

        System.out.println("\n2. Diagonal win (top-right to bottom-left):");
        renderer.show(board);

        // When
        ResultAnalyzer analyzer = new ResultAnalyzer(board);

        // Then
        assertTrue(analyzer.isWin(Disc.BLUE), "Should detect diagonal win");
        assertFalse(analyzer.isWin(Disc.RED), "Red should not win");
    }

    @Test
    void diagonalWin_centerToBottomRight_shouldReturnTrue() {
        // Given
        Board board = new Board(new DefaultBoardState(DEFAULT_SIZE, getMask(DEFAULT_SIZE)));

        // Диагональ из центра в правый нижний угол
        board.drop(2, Disc.GREEN);  // [5,2]
        board.drop(3, Disc.GREEN);  // [4,3]
        board.drop(4, Disc.GREEN);  // [3,4]
        board.drop(5, Disc.GREEN);  // [2,5]

        System.out.println("\n3. Diagonal win (center to bottom-right):");
        renderer.show(board);

        // When
        ResultAnalyzer analyzer = new ResultAnalyzer(board);

        // Then
        assertTrue(analyzer.isWin(Disc.GREEN), "Should detect diagonal win");
        assertFalse(analyzer.isWin(Disc.YELLOW), "Yellow should not win");
    }

    @Test
    void diagonalWin_centerToBottomLeft_shouldReturnTrue() {
        // Given
        Board board = new Board(new DefaultBoardState(DEFAULT_SIZE, getMask(DEFAULT_SIZE)));

        // Диагональ из центра в левый нижний угол
        board.drop(4, Disc.YELLOW); // [5,4]
        board.drop(3, Disc.YELLOW); // [4,3]
        board.drop(2, Disc.YELLOW); // [3,2]
        board.drop(1, Disc.YELLOW); // [2,1]

        System.out.println("\n4. Diagonal win (center to bottom-left):");
        renderer.show(board);

        // When
        ResultAnalyzer analyzer = new ResultAnalyzer(board);

        // Then
        assertTrue(analyzer.isWin(Disc.YELLOW), "Should detect diagonal win");
        assertFalse(analyzer.isWin(Disc.GREEN), "Green should not win");
    }

    @Test
    void diagonalWin_leftEdgeToRight_shouldReturnTrue() {
        // Given
        Board board = new Board(new DefaultBoardState(DEFAULT_SIZE, getMask(DEFAULT_SIZE)));

        // Диагональ от левого края вправо вниз
        board.drop(0, Disc.RED);    // [4,0]
        board.drop(1, Disc.RED);    // [3,1]
        board.drop(2, Disc.RED);    // [2,2]
        board.drop(3, Disc.RED);    // [1,3]

        System.out.println("\n5. Diagonal win (left edge to right):");
        renderer.show(board);

        // When
        ResultAnalyzer analyzer = new ResultAnalyzer(board);

        // Then
        assertTrue(analyzer.isWin(Disc.RED), "Should detect diagonal win");
        assertFalse(analyzer.isWin(Disc.BLUE), "Blue should not win");
    }

    @Test
    void diagonalWin_rightEdgeToLeft_shouldReturnTrue() {
        // Given
        Board board = new Board(new DefaultBoardState(DEFAULT_SIZE, getMask(DEFAULT_SIZE)));

        // Диагональ от правого края влево вниз
        board.drop(6, Disc.BLUE);   // [4,6]
        board.drop(5, Disc.BLUE);   // [3,5]
        board.drop(4, Disc.BLUE);   // [2,4]
        board.drop(3, Disc.BLUE);   // [1,3]

        System.out.println("\n6. Diagonal win (right edge to left):");
        renderer.show(board);

        // When
        ResultAnalyzer analyzer = new ResultAnalyzer(board);

        // Then
        assertTrue(analyzer.isWin(Disc.BLUE), "Should detect diagonal win");
        assertFalse(analyzer.isWin(Disc.RED), "Red should not win");
    }

    @Test
    void diagonal_interruptedLine_shouldNotWin() {
        // Given
        Board board = new Board(new DefaultBoardState(DEFAULT_SIZE, getMask(DEFAULT_SIZE)));

        // Прерванная диагональная линия
        board.drop(2, Disc.GREEN);  // [5,2]
        board.drop(3, Disc.GREEN);  // [4,3]
        board.drop(4, Disc.RED);    // Прерывающий токен [3,4]
        board.drop(5, Disc.GREEN);  // [2,5]
        board.drop(6, Disc.GREEN);  // [1,6]

        System.out.println("\n7. Interrupted diagonal line (should not win):");
        renderer.show(board);

        // When
        ResultAnalyzer analyzer = new ResultAnalyzer(board);

        // Then
        assertFalse(analyzer.isWin(Disc.GREEN), "Interrupted line should not win");
        assertFalse(analyzer.isWin(Disc.RED), "Single red should not win");
    }
}
