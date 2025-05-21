package dev.cheercode.connectfour.board;

import dev.cheercode.connectfour.Renderer;
import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.model.board.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardRotationLeftRightTest {

    /**
     * Тест 1: Поворот пустой доски влево.
     */
    @Test
    public void testRotateEmptyBoardLeft() {
        Board board = new Board(Board.Size.DEFAULT); // DEFAULT: 6x7 (height x width)
        Renderer renderer = new Renderer();
        System.out.println("=== Пустая доска перед поворотом (LEFT) ===");
        renderer.show(board);

        board.rotate(Direction.LEFT);

        System.out.println("=== Доска после поворота (LEFT) ===");
        renderer.show(board);

        // Новые размеры: высота должна стать 7, ширина – 6
        assertEquals(7, board.getHeight(), "После поворота влево высота должна стать равной исходной ширине (7)");
        assertEquals(6, board.getWidth(), "После поворота влево ширина должна стать равной исходной высоте (6)");

        // Все ячейки должны быть пустыми
        for (int row = 0; row < board.getHeight(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
                assertTrue(board.isEmptySlot(row, col),
                        String.format("Ячейка (%d,%d) должна быть пустой", row, col));
            }
        }
    }

    /**
     * Тест 2: Поворот пустой доски вправо.
     */
    @Test
    public void testRotateEmptyBoardRight() {
        Board board = new Board(Board.Size.DEFAULT);
        Renderer renderer = new Renderer();
        System.out.println("=== Пустая доска перед поворотом (RIGHT) ===");
        renderer.show(board);

        board.rotate(Direction.RIGHT);

        System.out.println("=== Доска после поворота (RIGHT) ===");
        renderer.show(board);

        // Ожидаемые размеры: высота становится исходной шириной (7), ширина – исходной высотой (6)
        assertEquals(7, board.getHeight(), "После поворота вправо высота должна стать равной исходной ширине (7)");
        assertEquals(6, board.getWidth(), "После поворота вправо ширина должна стать равной исходной высоте (6)");

        for (int row = 0; row < board.getHeight(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
                assertTrue(board.isEmptySlot(row, col),
                        String.format("Ячейка (%d,%d) должна быть пустой", row, col));
            }
        }
    }

    /**
     * Тест 3: Перенос токенов при повороте влево.
     */
    @Test
    public void testTokenTransferLeft() {
        Board board = new Board(Board.Size.DEFAULT);  // 6x7
        Renderer renderer = new Renderer();

        // Вставляем токены в нижнюю строку исходной доски
        board.drop(0, Disc.RED);    // (5,0)
        board.drop(2, Disc.BLUE);   // (5,2)
        board.drop(4, Disc.GREEN);  // (5,4)

        System.out.println("=== Доска перед поворотом (LEFT), токены в нижней строке ===");
        renderer.show(board);

        board.rotate(Direction.LEFT);

        System.out.println("=== Доска после поворота (LEFT) ===");
        renderer.show(board);

        // Предположим, что алгоритм перевода при LEFT осуществляет:
        // вызовы board.put(row, token) для каждой найденной непустой ячейки исходной доски;
        // т.к. токены были в строке 5 исходной доски, они будут добавлены в колонны, равные значению row (то есть row = 5).
        // Новые размеры: 7x6 (новая высота = 7, новая ширина = 6)
        // Ожидаем, что, например, токен RED окажется в новой позиции (6-? , ?).
        // Здесь конкретные ожидания зависят от реализации метода init.
        // Для примера, если токены записываются по порядку, можно проверить, что в одной из ячеек нового нижнего ряда (индекс 6) присутствуют соответствующие токены.

        int newHeight = board.getHeight();
        int newWidth = board.getWidth();
        // Проверим наличие токенов в нижнем ряду (newHeight - 1)
        // Поскольку реализация может различаться, в данном тесте мы просто проверяем, что соответствующие токены не исчезли.
        boolean foundRed = board.get(newHeight - 1, newWidth - 1) == Disc.RED;
        boolean foundBlue = board.get(newHeight - 2, newWidth - 1) == Disc.BLUE;
        boolean foundGreen = board.get(newHeight - 3, newWidth - 1) == Disc.GREEN;
        assertTrue(foundRed, "Токен RED должен присутствовать после поворота влево");
        assertTrue(foundBlue, "Токен BLUE должен присутствовать после поворота влево");
        assertTrue(foundGreen, "Токен GREEN должен присутствовать после поворота влево");
    }

    /**
     * Тест 4: Перенос токенов при повороте вправо.
     */
    @Test
    public void testTokenTransferRight() {
        Board board = new Board(Board.Size.DEFAULT);  // 6x7
        Renderer renderer = new Renderer();

        // Вставляем токены в нижнюю строку исходной доски
        board.drop(1, Disc.YELLOW);   // (5,1)
        board.drop(3, Disc.GREEN);    // (5,3)
        board.drop(5, Disc.BLUE);     // (5,5)

        System.out.println("=== Доска перед поворотом (RIGHT), токены в нижней строке ===");
        renderer.show(board);

        board.rotate(Direction.RIGHT);

        System.out.println("=== Доска после поворотом (RIGHT) ===");
        renderer.show(board);

        // Аналогично тесту для LEFT:
        // Проверим, что токены перемещены и присутствуют в новом состоянии.
        int newHeight = board.getHeight();
        boolean foundBlue = board.get(newHeight - 1, 0) == Disc.BLUE;
        boolean foundGreen = board.get(newHeight - 2, 0) == Disc.GREEN;
        boolean foundYellow = board.get(newHeight - 3, 0) == Disc.YELLOW;
        assertTrue(foundYellow, "Токен YELLOW должен присутствовать после поворота вправо");
        assertTrue(foundGreen, "Токен GREEN должен присутствовать после поворота вправо");
        assertTrue(foundBlue, "Токен BLUE должен присутствовать после поворота вправо");
    }

    /**
     * Тест 5: Проверка падения токенов после поворота (на примере LEFT).
     */
    @Test
    public void testFallingAfterRotationLeft() {
        Board board = new Board(Board.Size.DEFAULT);
        Renderer renderer = new Renderer();

        // Вставляем токен в колонку 3 исходной доски
        board.drop(3, Disc.YELLOW); // ожидаем позицию (5,3)

        System.out.println("=== Доска перед поворотом (LEFT) с токеном в колонке 3 ===");
        renderer.show(board);

        board.rotate(Direction.LEFT);

        System.out.println("=== Доска после поворота (LEFT) ===");
        renderer.show(board);

        // После поворота определим новую колонку, соответствующую исходной (3).
        // Теперь вставим дополнительный токен в ту же колонку и проверим, что он занимает ячейку выше.
        int newWidth = board.getWidth();
        board.drop(newWidth - 1, Disc.RED);

        System.out.printf("=== Доска после вставки дополнительного токена в колонку %d (LEFT) ===\n", (newWidth - 1));
        renderer.show(board);

        // Ожидаем, что токен RED окажется на строке, на единицу выше нежели токен YELLOW.
        int newHeight = board.getHeight();
        // Найдем позиции токенов в колонке 3.
        // В силу механики вертикально-падающего поведения, нижняя строка (индекс newHeight - 1) должна содержать токен,
        // а новый токен – на строку выше (индекс newHeight - 2).
        assertEquals(Disc.YELLOW, board.get(newHeight - 1, newWidth - 1),
                "Токен YELLOW должен занимать нижнюю ячейку после поворота");
        assertEquals(Disc.RED, board.get(newHeight - 2, newWidth - 1),
                "Новый токен RED должен оказаться на ячейке выше после поворота");
    }

    /**
     * Тест 6: Проверка сохранения корректных размеров доски после поворота (LEFT и RIGHT).
     */
    @Test
    public void testBoardDimensionsAfterRotation() {
        // Тестируем для поворота LEFT
        Board boardLeft = new Board(Board.Size.DEFAULT); // 6x7
        boardLeft.rotate(Direction.LEFT);
        Renderer renderer = new Renderer();
        System.out.println("=== Доска после поворота (LEFT) ===");
        renderer.show(boardLeft);
        assertEquals(7, boardLeft.getHeight(), "После поворота влево высота должна равняться исходной ширине (7)");
        assertEquals(6, boardLeft.getWidth(), "После поворота влево ширина должна равняться исходной высоте (6)");

        // Тестируем для поворота RIGHT
        Board boardRight = new Board(Board.Size.DEFAULT); // 6x7
        boardRight.rotate(Direction.RIGHT);
        System.out.println("=== Доска после поворота (RIGHT) ===");
        renderer.show(boardRight);
        assertEquals(7, boardRight.getHeight(), "После поворота вправо высота должна равняться исходной ширине (7)");
        assertEquals(6, boardRight.getWidth(), "После поворота вправо ширина должна равняться исходной высоте (6)");
    }
}

