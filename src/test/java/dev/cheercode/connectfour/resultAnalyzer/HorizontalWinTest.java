package dev.cheercode.connectfour.resultAnalyzer;

import dev.cheercode.connectfour.model.board.DefaultBoardState;
import dev.cheercode.connectfour.renderer.Renderer;
import dev.cheercode.connectfour.game.ResultAnalyzer;
import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.renderer.renderer_for_idea.RendererForIdea;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HorizontalWinTest {
    private final Board.Size DEFAULT_SIZE = Board.Size.ROW6_COLUMN7;
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
    void horizontalWin_leftEdge_shouldReturnTrue() {
        // Given
        Board board = new Board(new DefaultBoardState(DEFAULT_SIZE, getMask(DEFAULT_SIZE)));

        // Красные токены в левой части строки (нижние ряды)
        board.drop(0, Disc.RED);
        board.drop(0, Disc.RED);
        board.drop(0, Disc.RED);
        board.drop(0, Disc.RED);

        System.out.println("1. Horizontal win at left edge:");
        renderer.show(board);

        // When
        ResultAnalyzer analyzer = new ResultAnalyzer(board);

        // Then
        assertTrue(analyzer.isWin(Disc.RED), "Should detect horizontal win at left edge");
        assertFalse(analyzer.isWin(Disc.BLUE), "Blue should not win");
        assertFalse(analyzer.isWin(Disc.YELLOW), "Yellow should not win");
    }

    @Test
    void horizontalWin_center_shouldReturnTrue() {
        // Given
        Board board = new Board(new DefaultBoardState(DEFAULT_SIZE, getMask(DEFAULT_SIZE)));

        // Синие токены в центре
        board.drop(2, Disc.BLUE);
        board.drop(3, Disc.BLUE);
        board.drop(4, Disc.BLUE);
        board.drop(5, Disc.BLUE);

        System.out.println("\n2. Horizontal win at center:");
        renderer.show(board);

        // When
        ResultAnalyzer analyzer = new ResultAnalyzer(board);

        // Then
        assertTrue(analyzer.isWin(Disc.BLUE), "Should detect horizontal win at center");
        assertFalse(analyzer.isWin(Disc.RED), "Red should not win");
    }

    @Test
    void horizontalWin_rightEdge_shouldReturnTrue() {
        // Given
        Board board = new Board(new DefaultBoardState(DEFAULT_SIZE, getMask(DEFAULT_SIZE)));

        // Желтые токены у правого края
        board.drop(6, Disc.YELLOW);
        board.drop(6, Disc.YELLOW);
        board.drop(6, Disc.YELLOW);
        board.drop(6, Disc.YELLOW);

        System.out.println("\n3. Horizontal win at right edge:");
        renderer.show(board);

        // When
        ResultAnalyzer analyzer = new ResultAnalyzer(board);

        // Then
        assertTrue(analyzer.isWin(Disc.YELLOW), "Should detect horizontal win at right edge");
        assertFalse(analyzer.isWin(Disc.GREEN), "Green should not win");
    }

    @Test
    void horizontalWin_moreThan4_shouldReturnTrue() {
        // Given
        Board board = new Board(new DefaultBoardState(DEFAULT_SIZE, getMask(DEFAULT_SIZE)));

        // 5 зеленых токенов подряд
        board.drop(1, Disc.GREEN);
        board.drop(2, Disc.GREEN);
        board.drop(3, Disc.GREEN);
        board.drop(4, Disc.GREEN);
        board.drop(5, Disc.GREEN);

        System.out.println("\n4. Horizontal win with more than 4 tokens:");
        renderer.show(board);

        // When
        ResultAnalyzer analyzer = new ResultAnalyzer(board);

        // Then
        assertTrue(analyzer.isWin(Disc.GREEN), "Should detect win with >4 tokens");
        assertFalse(analyzer.isWin(Disc.BLUE), "Blue should not win");
    }

    @Test
    void horizontal_interruptedLine_shouldNotWin() {
        // Given
        Board board = new Board(new DefaultBoardState(DEFAULT_SIZE, getMask(DEFAULT_SIZE)));

        // Прерванная линия
        board.drop(1, Disc.RED);
        board.drop(2, Disc.RED);
        board.drop(3, Disc.BLUE); // Прерывающий токен
        board.drop(4, Disc.RED);
        board.drop(5, Disc.RED);

        System.out.println("\n5. Interrupted horizontal line (should not win):");
        renderer.show(board);

        // When
        ResultAnalyzer analyzer = new ResultAnalyzer(board);

        // Then
        assertFalse(analyzer.isWin(Disc.RED), "Interrupted line should not win");
        assertFalse(analyzer.isWin(Disc.BLUE), "Single blue should not win");
    }

    @Test
    void horizontalWin_differentRows_shouldAllWin() {
        // Test all rows
        for (int row = 0; row < DEFAULT_SIZE.getHeight(); row++) {
            Board board = new Board(new DefaultBoardState(DEFAULT_SIZE, getMask(DEFAULT_SIZE)));

            // Fill entire row
            for (int col = 0; col < 4; col++) {
                board.drop(col, Disc.YELLOW);
            }

            ResultAnalyzer analyzer = new ResultAnalyzer(board);
            assertTrue(analyzer.isWin(Disc.YELLOW),
                    "Should win in row " + (row + 1));
        }
    }

    @Test
    void horizontalWin_farLeftWithEmptySpaces_shouldReturnTrue() {
        // Given
        Board board = new Board(new DefaultBoardState(DEFAULT_SIZE, getMask(DEFAULT_SIZE)));

        // Крайняя левая позиция с пустыми ячейками справа
        board.drop(0, Disc.RED);  // Ряд 6
        board.drop(0, Disc.RED);  // Ряд 5
        board.drop(0, Disc.RED);  // Ряд 4
        board.drop(0, Disc.RED);  // Ряд 3
        // Пустые ячейки в рядах 2 и 1

        System.out.println("\n6. Horizontal win at far left with empty spaces:");
        renderer.show(board);

        // When
        ResultAnalyzer analyzer = new ResultAnalyzer(board);

        // Then
        assertTrue(analyzer.isWin(Disc.RED), "Should detect win at far left");
        assertFalse(analyzer.isWin(Disc.BLUE), "Blue should not win");
    }

    @Test
    void horizontalWin_farRightWithEmptySpaces_shouldReturnTrue() {
        // Given
        Board board = new Board(new DefaultBoardState(DEFAULT_SIZE, getMask(DEFAULT_SIZE)));

        // Крайняя правая позиция с пустыми ячейками слева
        board.drop(6, Disc.BLUE);  // Ряд 6
        board.drop(6, Disc.BLUE);  // Ряд 5
        board.drop(6, Disc.BLUE);  // Ряд 4
        board.drop(6, Disc.BLUE);  // Ряд 3
        // Пустые ячейки в рядах 2 и 1

        System.out.println("\n7. Horizontal win at far right with empty spaces:");
        renderer.show(board);

        // When
        ResultAnalyzer analyzer = new ResultAnalyzer(board);

        // Then
        assertTrue(analyzer.isWin(Disc.BLUE), "Should detect win at far right");
        assertFalse(analyzer.isWin(Disc.RED), "Red should not win");
    }

    @Test
    void horizontalWin_leftEdgeWithOtherTokens_shouldReturnTrue() {
        // Given
        Board board = new Board(new DefaultBoardState(DEFAULT_SIZE, getMask(DEFAULT_SIZE)));

        // Победа у левого края с другими токенами на доске
        board.drop(0, Disc.GREEN);
        board.drop(1, Disc.GREEN);
        board.drop(2, Disc.GREEN);
        board.drop(3, Disc.GREEN);

        // Добавляем мешающие токены
        board.drop(4, Disc.RED);
        board.drop(5, Disc.BLUE);
        board.drop(6, Disc.YELLOW);

        System.out.println("\n8. Horizontal win at left edge with other tokens:");
        renderer.show(board);

        // When
        ResultAnalyzer analyzer = new ResultAnalyzer(board);

        // Then
        assertTrue(analyzer.isWin(Disc.GREEN), "Should detect win through other tokens");
        assertFalse(analyzer.isWin(Disc.RED), "Red should not win");
        assertFalse(analyzer.isWin(Disc.BLUE), "Blue should not win");
    }

    @Test
    void horizontalWin_rightEdgeWithOtherTokens_shouldReturnTrue() {
        // Given
        Board board = new Board(new DefaultBoardState(DEFAULT_SIZE, getMask(DEFAULT_SIZE)));

        // Победа у правого края с другими токенами на доске
        board.drop(3, Disc.YELLOW);
        board.drop(4, Disc.YELLOW);
        board.drop(5, Disc.YELLOW);
        board.drop(6, Disc.YELLOW);

        // Добавляем мешающие токены
        board.drop(0, Disc.RED);
        board.drop(1, Disc.BLUE);
        board.drop(2, Disc.GREEN);

        System.out.println("\n9. Horizontal win at right edge with other tokens:");
        renderer.show(board);

        // When
        ResultAnalyzer analyzer = new ResultAnalyzer(board);

        // Then
        assertTrue(analyzer.isWin(Disc.YELLOW), "Should detect win through other tokens");
        assertFalse(analyzer.isWin(Disc.GREEN), "Green should not win");
    }
}
