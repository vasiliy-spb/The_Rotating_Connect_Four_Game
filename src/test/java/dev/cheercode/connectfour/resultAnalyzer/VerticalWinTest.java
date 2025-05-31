package dev.cheercode.connectfour.resultAnalyzer;

import dev.cheercode.connectfour.model.board.DefaultBoardState;
import dev.cheercode.connectfour.renderer.Renderer;
import dev.cheercode.connectfour.game.ResultAnalyzer;
import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.renderer.renderer_for_idea.RendererForIdea;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VerticalWinTest {
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
    void verticalWin_leftmostColumn_shouldReturnTrue() {
        // Given
        Board board = new Board(new DefaultBoardState(DEFAULT_SIZE, getMask(DEFAULT_SIZE)));

        // Вертикальная линия в крайнем левом столбце (колонка 0)
        board.drop(0, Disc.RED);    // Ряд 6
        board.drop(0, Disc.RED);    // Ряд 5
        board.drop(0, Disc.RED);    // Ряд 4
        board.drop(0, Disc.RED);    // Ряд 3

        System.out.println("1. Vertical win in leftmost column:");
        renderer.show(board);

        // When
        ResultAnalyzer analyzer = new ResultAnalyzer(board);

        // Then
        assertTrue(analyzer.isWin(Disc.RED), "Should detect vertical win in leftmost column");
        assertFalse(analyzer.isWin(Disc.BLUE), "Blue should not win");
    }

    @Test
    void verticalWin_rightmostColumn_shouldReturnTrue() {
        // Given
        Board board = new Board(new DefaultBoardState(DEFAULT_SIZE, getMask(DEFAULT_SIZE)));

        int width = board.getWidth();

        // Вертикальная линия в крайнем левом столбце (колонка 0)
        board.drop(width - 1, Disc.RED);    // Ряд 6
        board.drop(width - 1, Disc.RED);    // Ряд 5
        board.drop(width - 1, Disc.RED);    // Ряд 4
        board.drop(width - 1, Disc.RED);    // Ряд 3

        System.out.println("1. Vertical win in leftmost column:");
        renderer.show(board);

        // When
        ResultAnalyzer analyzer = new ResultAnalyzer(board);

        // Then
        assertTrue(analyzer.isWin(Disc.RED), "Should detect vertical win in leftmost column");
        assertFalse(analyzer.isWin(Disc.BLUE), "Blue should not win");
    }

    @Test
    void verticalWin_centerColumn_shouldReturnTrue() {
        // Given
        Board board = new Board(new DefaultBoardState(DEFAULT_SIZE, getMask(DEFAULT_SIZE)));

        // Вертикальная линия в центральном столбце (колонка 3)
        board.drop(3, Disc.BLUE);   // Ряд 6
        board.drop(3, Disc.BLUE);   // Ряд 5
        board.drop(3, Disc.BLUE);   // Ряд 4
        board.drop(3, Disc.BLUE);   // Ряд 3

        System.out.println("\n2. Vertical win in center column:");
        renderer.show(board);

        // When
        ResultAnalyzer analyzer = new ResultAnalyzer(board);

        // Then
        assertTrue(analyzer.isWin(Disc.BLUE), "Should detect vertical win in center column");
        assertFalse(analyzer.isWin(Disc.RED), "Red should not win");
    }

    @Test
    void verticalWin_bottomEdge_shouldReturnTrue() {
        // Given
        Board board = new Board(new DefaultBoardState(DEFAULT_SIZE, getMask(DEFAULT_SIZE)));

        // Вертикальная линия у нижнего края (первые 4 ряда)
        board.drop(2, Disc.GREEN);  // Ряд 6
        board.drop(2, Disc.GREEN);  // Ряд 5
        board.drop(2, Disc.GREEN);  // Ряд 4
        board.drop(2, Disc.GREEN);  // Ряд 3

        System.out.println("\n3. Vertical win at bottom edge:");
        renderer.show(board);

        // When
        ResultAnalyzer analyzer = new ResultAnalyzer(board);

        // Then
        assertTrue(analyzer.isWin(Disc.GREEN), "Should detect vertical win at bottom edge");
        assertFalse(analyzer.isWin(Disc.YELLOW), "Yellow should not win");
    }

    @Test
    void verticalWin_moreThan4_shouldReturnTrue() {
        // Given
        Board board = new Board(new DefaultBoardState(DEFAULT_SIZE, getMask(DEFAULT_SIZE)));

        // 5 токенов в колонке (должно все равно считаться победой)
        board.drop(4, Disc.YELLOW);  // Ряд 6
        board.drop(4, Disc.YELLOW);  // Ряд 5
        board.drop(4, Disc.YELLOW);  // Ряд 4
        board.drop(4, Disc.YELLOW);  // Ряд 3
        board.drop(4, Disc.YELLOW);  // Ряд 2

        System.out.println("\n4. Vertical win with more than 4 tokens:");
        renderer.show(board);

        // When
        ResultAnalyzer analyzer = new ResultAnalyzer(board);

        // Then
        assertTrue(analyzer.isWin(Disc.YELLOW), "Should detect win with >4 tokens vertically");
        assertFalse(analyzer.isWin(Disc.RED), "Red should not win");
    }

    @Test
    void vertical_interruptedLine_shouldNotWin() {
        // Given
        Board board = new Board(new DefaultBoardState(DEFAULT_SIZE, getMask(DEFAULT_SIZE)));

        // Прерванная вертикальная линия
        board.drop(1, Disc.RED);    // Ряд 6
        board.drop(1, Disc.RED);    // Ряд 5
        board.drop(1, Disc.BLUE);   // Прерывающий токен (Ряд 4)
        board.drop(1, Disc.RED);    // Ряд 3
        board.drop(1, Disc.RED);    // Ряд 2

        System.out.println("\n5. Interrupted vertical line (should not win):");
        renderer.show(board);

        // When
        ResultAnalyzer analyzer = new ResultAnalyzer(board);

        // Then
        assertFalse(analyzer.isWin(Disc.RED), "Interrupted line should not win");
        assertFalse(analyzer.isWin(Disc.BLUE), "Single blue should not win");
    }
}
