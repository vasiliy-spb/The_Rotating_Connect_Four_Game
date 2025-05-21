package dev.cheercode.connectfour.board;

import dev.cheercode.connectfour.Renderer;
import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.model.board.Direction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardBoundaryTest {

    /**
     * Тест 1: Вставка токенов в последнюю строку самой высокой доски (Size.ROW7_COLUMN10).
     * Заполняем выбранную колонку полностью и проверяем, что в самой верхней строке (индекс 0)
     * находится токен.
     */
    @Test
    public void testInsertionInLastRowHighestBoard() {
        // Создаем доску размера ROW7_COLUMN10 (в данном случае высота 7, ширина 10)
        Board board = new Board(Board.Size.ROW7_COLUMN10);
        Renderer renderer = new Renderer();
        System.out.println("=== Исходная доска (ROW7_COLUMN10) перед заполнением колонки ===");
        renderer.show(board);

        int height = board.getHeight(); // Ожидается 7
        int targetColumn = 4; // Выбираем произвольную колонку

        // Заполняем выбранную колонку последовательно токенами
        for (int i = 0; i < height; i++) {
            board.drop(targetColumn, Disc.RED);
        }

        System.out.println("=== Доска после заполнения колонки " + targetColumn + " до последней строки ===");
        renderer.show(board);

        // Проверяем, что в ячейке верхней строки (индекс 0) выбранной колонки токен установлен
        assertFalse(board.isEmptySlot(0, targetColumn),
                "В ячейке последней строки (0, " + targetColumn + ") должен быть токен.");
    }

    /**
     * Тест 2: Попытка извлечения токена из пустой ячейки.
     * Ожидается, что вызов board.get(row, col) для пустой ячейки вызывает IllegalArgumentException.
     */
    @Test
    public void testGetEmptyCellThrowsException() {
        Board board = new Board(Board.Size.DEFAULT);
        Renderer renderer = new Renderer();
        System.out.println("=== Исходная доска (DEFAULT) для теста извлечения пустой ячейки ===");
        renderer.show(board);
        int row = 2, col = 3; // Выбираем произвольную пустую ячейку
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            board.get(row, col);
        }, "Попытка извлечения токена из пустой ячейки должна вызвать IllegalArgumentException");
        System.out.println("Ожидаемое исключение: " + exception.getMessage());
    }

    /**
     * Тест 3: Попытка вставки токена в недопустимый столбец.
     * Вызываем board.put с отрицательным индексом и с индексом, равным getWidth().
     */
    @Test
    public void testPutTokenInvalidColumn() {
        Board board = new Board(Board.Size.DEFAULT);
        Renderer renderer = new Renderer();
        System.out.println("=== Исходная доска (DEFAULT) для теста недопустимых столбцов ===");
        renderer.show(board);

        Disc disc = Disc.BLUE;

        // Попытка вставки в отрицательный столбец
        Exception exNeg = assertThrows(IllegalArgumentException.class, () -> {
            board.drop(-1, disc);
        }, "Вставка токена в отрицательный столбец должна вызвать IllegalArgumentException");
        System.out.println("Ожидаемое исключение для col -1: " + exNeg.getMessage());

        // Попытка вставки в столбец с индексом, равным getWidth() (т.е. вне допустимого диапазона)
        Exception exOverflow = assertThrows(IllegalArgumentException.class, () -> {
            board.drop(board.getWidth(), disc);
        }, "Вставка токена в столбец с индексом, равным getWidth(), должна вызвать IllegalArgumentException");
        System.out.println("Ожидаемое исключение для col = " + board.getWidth() + ": " + exOverflow.getMessage());
    }

    /**
     * Тест 4: Проверка работы методов isColumnFilled и isBoardFilled при частично заполненной доске.
     * На доске DEFAULT (6x7) заполняется одна колонка частично, а другая полностью.
     */
    @Test
    public void testPartialFillAndFullBoardMethods() {
        Board board = new Board(Board.Size.DEFAULT); // 6x7
        Renderer renderer = new Renderer();

        // Заполняем колонку 0 частично (например, 3 токена, а максимум 6)
        for (int i = 0; i < 3; i++) {
            board.drop(0, Disc.GREEN);
        }
        // Полностью заполняем колонку 1 (6 токенов)
        int height = board.getHeight();
        for (int i = 0; i < height; i++) {
            board.drop(1, Disc.RED);
        }

        System.out.println("=== Доска для теста частичного заполнения колонок ===");
        renderer.show(board);

        // Проверяем, что колонка 0 не заполнена, а колонка 1 заполнена
        assertFalse(board.isColumnFilled(0), "Колонка 0 не должна считаться заполненной");
        assertTrue(board.isColumnFilled(1), "Колонка 1 должна считаться заполненной");

        // Если хотя бы одна колонка не заполнена, то вся доска не считается заполненной
        assertFalse(board.isBoardFilled(), "Доска не должна считаться заполненной, т.к. есть неполные колонки");
    }

    /**
     * Тест 5: Проверка работы turnUpsideDown (через rotate(UPSIDE_DOWN)) на пустой и частично заполненной доске.
     */
    @Test
    public void testTurnUpsideDownOnEmptyAndPartialBoard() {
        Renderer renderer = new Renderer();

        // (a) Переворот пустой доски
        Board emptyBoard = new Board(Board.Size.DEFAULT);
        System.out.println("=== Пустая доска перед переворотом UPSIDE_DOWN ===");
        renderer.show(emptyBoard);
        emptyBoard.rotate(Direction.UPSIDE_DOWN);
        System.out.println("=== Пустая доска после переворота UPSIDE_DOWN ===");
        renderer.show(emptyBoard);
        // Проверяем, что все ячейки остаются пустыми
        for (int row = 0; row < emptyBoard.getHeight(); row++) {
            for (int col = 0; col < emptyBoard.getWidth(); col++) {
                assertTrue(emptyBoard.isEmptySlot(row, col),
                        String.format("Ячейка (%d, %d) должна оставаться пустой после переворота пустой доски", row, col));
            }
        }

        // (b) Переворот частично заполненной доски
        Board partialBoard = new Board(Board.Size.DEFAULT);
        // Вставляем несколько токенов в колонку 2 (2 токена)
        partialBoard.drop(2, Disc.YELLOW);
        partialBoard.drop(2, Disc.BLUE);

        System.out.println("=== Частично заполненная доска перед переворотом UPSIDE_DOWN ===");
        renderer.show(partialBoard);

        partialBoard.rotate(Direction.UPSIDE_DOWN);

        System.out.println("=== Частично заполненная доска после переворота UPSIDE_DOWN ===");
        renderer.show(partialBoard);

        // Проверяем, что хотя бы один из токенов присутствует после переворота.
        int bottomRow = partialBoard.getHeight() - 1;
        boolean foundYellow = false, foundBlue = false;
        for (int col = 0; col < partialBoard.getWidth(); col++) {
            if (!partialBoard.isEmptySlot(bottomRow, col)) {
                Disc tok = partialBoard.get(bottomRow, col);
                if (tok == Disc.YELLOW) foundYellow = true;
                if (tok == Disc.BLUE) foundBlue = true;
            }
        }
        assertTrue(foundYellow || foundBlue,
                "После переворота хотя бы один токен должен присутствовать в нижней строке");
    }

    /**
     * Тест 6: Проверка поворота доски, где есть всего один токен.
     * Вставляем единственный токен, выполняем UPSIDE_DOWN и убеждаемся,
     * что токен перемещается корректно.
     */
    @Test
    public void testRotationWithSingleToken() {
        Board board = new Board(Board.Size.DEFAULT);
        Renderer renderer = new Renderer();
        // Вставляем один токен в колонку 3 (исходно в позиции (height-1, 5))
        board.drop(5, Disc.RED);

        System.out.println("=== Доска с одним токеном перед ротацией ===");
        renderer.show(board);

        // Выполняем поворот UPSIDE_DOWN
        board.rotate(Direction.UPSIDE_DOWN);

        System.out.println("=== Доска после UPSIDE_DOWN с одним токеном ===");
        renderer.show(board);

        int height = board.getHeight();
        // Если учитывать формулу переворота UPSIDE_DOWN (новая колонка = width - 1 - старая колонка),
        // для DEFAULT (width=7) новая колонка для 5 будет: 6 - 5 = 1.
        // Ожидаем, что токен окажется в нижней строке новой доски, т.е. (height - 1, 1).
        assertEquals(Disc.RED, board.get(height - 1, 1),
                "Токен должен быть перемещён корректно при повороте доски с одним токеном");
    }
}
