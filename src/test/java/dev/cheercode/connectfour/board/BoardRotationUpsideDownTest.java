package dev.cheercode.connectfour.board;

import dev.cheercode.connectfour.model.board.DefaultBoardState;
import dev.cheercode.connectfour.renderer.Renderer;
import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.model.board.Direction;
import dev.cheercode.connectfour.renderer.RendererForIdea;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardRotationUpsideDownTest {

    /**
     * Тест 1: Переворот пустой доски.
     * Ожидается, что все ячейки останутся пустыми после переворота.
     */
    @Test
    public void testUpsideDownEmptyBoard() {
        Board board = new Board(new DefaultBoardState(Board.Size.DEFAULT));
        Renderer renderer = new RendererForIdea();

        System.out.println("=== Исходная пустая доска перед UPSIDE_DOWN ===");
        renderer.show(board);

        // Проверяем, что все ячейки пусты до переворота
        for (int row = 0; row < board.getHeight(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
                assertTrue(board.isEmptySlot(row, col),
                        "Ячейка (" + row + "," + col + ") должна быть пуста.");
            }
        }

        board.rotate(Direction.UPSIDE_DOWN);

        System.out.println("=== Пустая доска после UPSIDE_DOWN ===");
        renderer.show(board);

        // Проверяем, что все ячейки пусты и после переворота
        for (int row = 0; row < board.getHeight(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
                assertTrue(board.isEmptySlot(row, col),
                        "Ячейка (" + row + "," + col + ") должна оставаться пустой после переворота.");
            }
        }
    }

    /**
     * Тест 2: Переворот доски с расставленными токенами.
     * Токены должны переместиться в соответствии с алгоритмом переворота.
     */
    @Test
    public void testUpsideDownRotationWithTokens() {
        Board board = new Board(new DefaultBoardState(Board.Size.DEFAULT));
        Renderer renderer = new RendererForIdea();
        int height = board.getHeight();
        int width = board.getWidth();  // Для DEFAULT: width = 7, значит индекс последней колонки = 6

        // Вставляем по одному токену в разные колонки:
        // - В колонке 0: Token.RED  (изначально позиция (5,0))
        // - В колонке 2: Token.BLUE (изначально позиция (5,2))
        // - В колонке 4: Token.GREEN (изначально позиция (5,4))
        board.drop(0, Disc.RED);
        board.drop(2, Disc.BLUE);
        board.drop(4, Disc.GREEN);

        System.out.println("=== Доска с токенами перед UPSIDE_DOWN ===");
        renderer.show(board);

        board.rotate(Direction.UPSIDE_DOWN);

        System.out.println("=== Доска после UPSIDE_DOWN ===");
        renderer.show(board);

        // Ожидаемые новые позиции (вычисление: новая колонка = width - 1 - oldColumn):
        // Для колонки 0: новая колонка = 6 - 0 = 6, токен должен оказаться в (height-1, 6)
        // Для колонки 2: новая колонка = 6 - 2 = 4, токен должен оказаться в (height-1, 4)
        // Для колонки 4: новая колонка = 6 - 4 = 2, токен должен оказаться в (height-1, 2)
        assertEquals(Disc.RED, board.get(height - 1, 6),
                "После переворота токен RED должен оказаться в ячейке (" + (height - 1) + ",6).");
        assertEquals(Disc.BLUE, board.get(height - 1, 4),
                "После переворота токен BLUE должен оказаться в ячейке (" + (height - 1) + ",4).");
        assertEquals(Disc.GREEN, board.get(height - 1, 2),
                "После переворота токен GREEN должен оказаться в ячейке (" + (height - 1) + ",2).");
    }

    /**
     * Тест 3: Проверка корректности падения токенов после переворота.
     * Новый токен, добавленный в заполненную колонку, должен занять строку выше.
     */
    @Test
    public void testFallingTokensAfterUpsideDownRotation() {
        Board board = new Board(new DefaultBoardState(Board.Size.DEFAULT));
        Renderer renderer = new RendererForIdea();
        int height = board.getHeight();
        int width = board.getWidth();  // Для DEFAULT: width = 7

        // Вставляем один токен в колонку 3 (будет в позиции (height-1, 3))
        board.drop(3, Disc.YELLOW);

        System.out.println("=== Доска с одним токеном (колонка 3) перед UPSIDE_DOWN ===");
        renderer.show(board);

        board.rotate(Direction.UPSIDE_DOWN);
        System.out.println("=== Доска после UPSIDE_DOWN ===");
        renderer.show(board);

        // После переворота токен из исходной колонки 3 будет перемещён в новую колонку:
        // Новая колонка = width - 1 - 3 = 6 - 3 = 3, токен находится в нижней строке (индекс height-1)
        assertEquals(Disc.YELLOW, board.get(height - 1, 3),
                "После переворота токен YELLOW должен оказаться в ячейке (" + (height - 1) + ",3).");

        // Добавляем новый токен в ту же колонку 3, он должен "выпасть" на ячейку выше
        board.drop(3, Disc.GREEN);

        System.out.println("=== Доска после добавления нового токена (Token.GREEN) в колонку 3 ===");
        renderer.show(board);

        // Ожидаем, что новый токен окажется непосредственно над Token.YELLOW
        assertEquals(Disc.GREEN, board.get(height - 2, 3),
                "Новый токен GREEN должен оказаться в ячейке (" + (height - 2) + ",3).");
    }

    /**
     * Тест 4: Проверка сохранения размеров доски после переворота.
     */
    @Test
    public void testBoardDimensionsAfterUpsideDown() {
        Board board = new Board(new DefaultBoardState(Board.Size.DEFAULT));
        int originalHeight = board.getHeight();
        int originalWidth = board.getWidth();

        // Добавляем произвольный токен, хотя для проверки размеров это не критично,
        // но позволяет визуально оценить состояние доски.
        board.drop(2, Disc.RED);

        board.rotate(Direction.UPSIDE_DOWN);

        Renderer renderer = new RendererForIdea();
        System.out.println("=== Доска после UPSIDE_DOWN (проверка размеров) ===");
        renderer.show(board);

        assertEquals(originalHeight, board.getHeight(),
                "Высота доски должна сохраняться после переворота.");
        assertEquals(originalWidth, board.getWidth(),
                "Ширина доски должна сохраняться после переворота.");
    }
}
