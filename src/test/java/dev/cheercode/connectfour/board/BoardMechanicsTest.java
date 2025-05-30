package dev.cheercode.connectfour.board;

import dev.cheercode.connectfour.model.board.DefaultBoardState;
import dev.cheercode.connectfour.renderer.Renderer;
import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.renderer.renderer_for_idea.RendererForIdea;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardMechanicsTest {
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

    /**
     * Тест: Заполнение одной колонки полностью.
     * После заполнения колонки, метод isColumnFilled должен вернуть true.
     */
    @Test
    public void testFillColumnCompletely() {
        Board board = new Board(new DefaultBoardState(Board.Size.ROW6_COLUMN7, getMask(Board.Size.ROW6_COLUMN7))); // допустим, DEFAULT размер (6x7)
        int targetColumn = 2;
        int height = board.getHeight();

        // Заполняем выбранную колонку
        for (int i = 0; i < height; i++) {
            board.drop(targetColumn, Disc.RED);
        }

        // Проверка заполнения колонки
        assertTrue(board.isColumnFilled(targetColumn), "Колонка должна быть заполнена после правильного количества вставок");

        // Визуализация доски
        Renderer renderer = new RendererForIdea();
        System.out.println("Доска после заполнения колонки " + targetColumn + ":");
        renderer.show(board);
    }

    /**
     * Тест: Попытка вставки токена в полностью заполненную колонку.
     * Ожидается выброс IllegalArgumentException.
     */
    @Test
    public void testInsertIntoFullColumn() {
        Board board = new Board(new DefaultBoardState(Board.Size.ROW6_COLUMN7, getMask(Board.Size.ROW6_COLUMN7)));
        int targetColumn = 1;
        int height = board.getHeight();

        // Заполняем колонку полностью
        for (int i = 0; i < height; i++) {
            board.drop(targetColumn, Disc.BLUE);
        }

        // Визуальная проверка заполненной колонки
        Renderer renderer = new RendererForIdea();
        System.out.println("Доска с полностью заполненной колонкой " + targetColumn + ":");
        renderer.show(board);

        // Попытка вставки очередного токена в уже заполненную колонку
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            board.drop(targetColumn, Disc.YELLOW);
        }, "При вставке токена в полную колонку должно выбрасываться исключение");
        System.out.println("Ожидаемое исключение: " + ex.getMessage());
    }

    /**
     * Тест: Проверка корректного падения токенов.
     * Каждый новый токен должен занимать следующую доступную строку сверху.
     */
    @Test
    public void testSequentialDiscDrop() {
        Board board = new Board(new DefaultBoardState(Board.Size.ROW6_COLUMN7, getMask(Board.Size.ROW6_COLUMN7)));
        int targetColumn = 4;

        // Вставляем два токена
        board.drop(targetColumn, Disc.RED);   // первый токен падает в самую нижнюю строку
        board.drop(targetColumn, Disc.GREEN); // второй токен занимает строку выше

        int bottomRow = board.getHeight() - 1;
        assertEquals(Disc.RED, board.get(bottomRow, targetColumn), "Первый токен должен оказаться в нижней строке");
        assertEquals(Disc.GREEN, board.get(bottomRow - 1, targetColumn), "Второй токен должен оказаться на строку выше первого");

        // Визуализация
        Renderer renderer = new RendererForIdea();
        System.out.println("Проверка последовательного падения токенов в колонке " + targetColumn + ":");
        renderer.show(board);
    }

    /**
     * Тест: Проверка метода isBoardFilled, когда вся доска заполнена.
     */
    @Test
    public void testBoardIsFullyFilled() {
        Board board = new Board(new DefaultBoardState(Board.Size.ROW6_COLUMN7, getMask(Board.Size.ROW6_COLUMN7)));
        int height = board.getHeight();
        int width = board.getWidth();

        // Заполняем всю доску токенами
        for (int col = 0; col < width; col++) {
            for (int i = 0; i < height; i++) {
                board.drop(col, Disc.RED);
            }
        }

        // Доска должна считаться полностью заполненной
        assertTrue(board.isBoardFilled(), "Доска должна быть заполненной после заполнения каждой колонки");

        // Визуальное отображение заполненной доски
        Renderer renderer = new RendererForIdea();
        System.out.println("Полностью заполненная доска:");
        renderer.show(board);
    }

    /**
     * Тест: Попытка вставить токен, когда доска полностью заполнена.
     * Ожидается, что при попытке вставки вызов board.put выбросит IllegalArgumentException.
     */
    @Test
    public void testInsertTokenWhenBoardIsFull() {
        Board board = new Board(new DefaultBoardState(Board.Size.ROW6_COLUMN7, getMask(Board.Size.ROW6_COLUMN7)));
        int height = board.getHeight();
        int width = board.getWidth();

        // Заполняем всю доску
        for (int col = 0; col < width; col++) {
            for (int i = 0; i < height; i++) {
                board.drop(col, Disc.GREEN);
            }
        }

        // Визуальное представление полностью заполненной доски
        Renderer renderer = new RendererForIdea();
        System.out.println("Доска полностью заполнена:");
        renderer.show(board);

        // Попытка вставить токен, когда доска заполнена, должна приводить к исключению.
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            board.drop(0, Disc.YELLOW);
        }, "При попытке вставить токен в полностью заполненную доску должно выбрасываться исключение");

        System.out.println("Ожидаемое исключение при вставке в заполненную доску: " + ex.getMessage());
    }
}

