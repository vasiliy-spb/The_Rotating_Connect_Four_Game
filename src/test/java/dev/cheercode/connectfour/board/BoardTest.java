package dev.cheercode.connectfour.board;

import dev.cheercode.connectfour.model.board.DefaultBoardState;
import dev.cheercode.connectfour.renderer.Renderer;
import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.renderer.renderer_for_idea.RendererForIdea;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    // Предположим, что Token – это ваш enum: RED, BLUE, YELLOW, GREEN.

    @Test
    void testBoardInitialization_Default() {
        Board board = new Board(new DefaultBoardState(Board.Size.DEFAULT));
        // Для DEFAULT размер из enum: высота = 6, ширина = 7
        assertEquals(6, board.getHeight(), "Высота доски DEFAULT должна быть 6");
        assertEquals(7, board.getWidth(), "Ширина доски DEFAULT должна быть 7");

        // Проверяем, что все ячейки пусты
        for (int row = 0; row < board.getHeight(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
                assertTrue(board.isEmptySlot(row, col),
                        String.format("Ячейка (%d, %d) должна быть пуста", row, col));
            }
        }
    }

    @Test
    void testBoardInitialization_Row7Column8() {
        Board board = new Board(new DefaultBoardState(Board.Size.ROW7_COLUMN8));
        // Для ROW7_COLUMN8: высота = 7, ширина = 8
        assertEquals(7, board.getHeight(), "Высота доски ROW7_COLUMN8 должна быть 7");
        assertEquals(8, board.getWidth(), "Ширина доски ROW7_COLUMN8 должна быть 8");

        // Все ячейки должны быть пусты
        for (int row = 0; row < board.getHeight(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
                assertTrue(board.isEmptySlot(row, col),
                        String.format("Ячейка (%d, %d) должна быть пуста", row, col));
            }
        }
    }

    @Test
    void testSingleTokenInsertion() {
        Board board = new Board(new DefaultBoardState(Board.Size.DEFAULT));
        Disc disc = Disc.RED;
        int targetColumn = 3;

        board.drop(targetColumn, disc);

        int bottomRow = board.getHeight() - 1;

        // Проверка, что ячейка в нижней строке выбранной колонки занята
        assertFalse(board.isEmptySlot(bottomRow, targetColumn),
                "Ячейка после вставки токена не должна быть пустой");
        // Проверка, что токен в ячейке соответствует вставленному токену
        assertEquals(disc, board.get(bottomRow, targetColumn),
                "Вставленный токен не соответствует тому, который получен из ячейки");
    }

    @Test
    void testMultipleSizesAndTokenInsertion() {
        Board.Size[] sizes = {
                Board.Size.DEFAULT,
                Board.Size.ROW7_COLUMN8,
                Board.Size.ROW7_COLUMN9,
                Board.Size.ROW7_COLUMN10
        };

        for (Board.Size size : sizes) {
            Board board = new Board(new DefaultBoardState(size));
            int height = board.getHeight();
            int width = board.getWidth();
            int targetColumn = width / 2;  // Выбираем центральную колонку

            Disc disc = Disc.BLUE;
            board.drop(targetColumn, disc);

            int bottomRow = height - 1;
            assertFalse(board.isEmptySlot(bottomRow, targetColumn),
                    String.format("Ячейка (%d, %d) не должна быть пустой для доски размера %s",
                            bottomRow, targetColumn, size));
            assertEquals(disc, board.get(bottomRow, targetColumn),
                    String.format("Вставленный токен не соответствует ожидаемому для доски размера %s", size));
        }
    }

    /**
     * Тест для визуальной проверки рендеринга доски с помощью класса Renderer.
     * В консоли вы увидите цветное представление доски с несколькими токенами.
     */
    @Test
    void testRendererVisualization() {
        // Создаем доску стандартного размера
        Board board = new Board(new DefaultBoardState(Board.Size.DEFAULT));
        /*
         * Для наглядности вставляем токены в разные колонки.
         * Фишки будут «падать» к нижней строке:
         * * В колонке 1 – два токена: RED, затем BLUE;
         * * В колонке 3 – один токен YELLOW;
         * * В колонке 5 – один токен GREEN.
         */
        board.drop(1, Disc.RED);      // Ред: в ячейку (height-1, 1)
        board.drop(1, Disc.BLUE);     // Blue: сверху RED
        board.drop(3, Disc.YELLOW);
        board.drop(5, Disc.GREEN);

        // Создаем рендерер и выводим доску в консоль
        Renderer renderer = new RendererForIdea();
        System.out.println("Визуальное представление доски:");
        renderer.show(board);

        /*
         * Здесь нет assert-проверок, так как данный тест служит для визуальной проверки.
         * Если необходимо автоматизировать проверку вывода, можно организовать
         * перехват вывода из System.out и сравнивать строки, однако это оправдано,
         * когда требуется регрессионное тестирование конкретного текстового вывода.
         */
    }
}
