package dev.cheercode.connectfour.board;

import dev.cheercode.connectfour.model.board.DefaultBoardState;
import dev.cheercode.connectfour.renderer.Renderer;
import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.model.board.Direction;
import dev.cheercode.connectfour.renderer.renderer_for_idea.RendererForIdea;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class BoardStressTest {

    /**
     * Тест 1: Массовая вставка токенов в разные колонки (симуляция реальной игры).
     */
    @Test
    public void testMassInsertionInDifferentColumns() {
        Board board = new Board(new DefaultBoardState(Board.Size.DEFAULT));  // Например, DEFAULT: 6x7
        Renderer renderer = new RendererForIdea();
        System.out.println("=== Стресс-тест: Массовая вставка токенов (симуляция игры) ===");

        Disc[] discs = Disc.values();
        int tokenIndex = 0;

        // В цикле проходим по колонкам, вставляя токены до заполнения доски.
        while (!board.isBoardFilled()) {
            for (int col = 0; col < board.getWidth(); col++) {
                if (!board.isColumnFilled(col)) {
                    board.drop(col, discs[tokenIndex % discs.length]);
                    tokenIndex++;
                }
                if (board.isBoardFilled()) break;
            }
        }
        renderer.show(board);
        assertTrue(board.isBoardFilled(), "Доска должна быть заполненной после массовой вставки токенов.");
    }

    /**
     * Тест 2: Несколько последовательных поворотов (LEFT → UPSIDE_DOWN → RIGHT → UPSIDE_DOWN)
     * полностью заполненной доски.
     */
    @Test
    public void testSequentialRotationsFullBoard() {
        Board board = new Board(new DefaultBoardState(Board.Size.DEFAULT));
        Renderer renderer = new RendererForIdea();

        Disc[] discs = Disc.values();
        int tokenIndex = 0;

        // Полностью заполняем доску.
        for (int col = 0; col < board.getWidth(); col++) {
            for (int i = 0; i < board.getHeight(); i++) {
//                board.put(col, Token.RED);
                board.drop(col, discs[tokenIndex % discs.length]);
                tokenIndex++;
            }
        }
        System.out.println("=== Полностью заполненная доска перед последовательными поворотами ===");
        renderer.show(board);

        // Выполняем последовательность поворотов.
        board.rotate(Direction.LEFT);
        System.out.println("После поворота: " + Direction.LEFT);
        renderer.show(board);

        board.rotate(Direction.UPSIDE_DOWN);
        System.out.println("После поворота: " + Direction.UPSIDE_DOWN);
        renderer.show(board);

        board.rotate(Direction.RIGHT);
        System.out.println("После поворота: " + Direction.RIGHT);
        renderer.show(board);

        board.rotate(Direction.UPSIDE_DOWN);

        System.out.println("=== Доска после последовательных поворотов (полностью заполненная) ===");
        renderer.show(board);

        assertTrue(board.isBoardFilled(),
                "После последовательных поворотов полностью заполненная доска должна оставаться заполненной.");
    }

    /**
     * Тест 3: Несколько последовательных поворотов доски, заполненной на половину.
     */
    @Test
    public void testSequentialRotationsHalfFilledBoard() {
        Board board = new Board(new DefaultBoardState(Board.Size.DEFAULT));
        Renderer renderer = new RendererForIdea();
        int totalCells = board.getHeight() * board.getWidth();  // Для DEFAULT: 42 ячейки
        int moves = totalCells / 2;  // Примерно половина: около 21 токена.
        Disc[] discs = Disc.values();

        Random random = new Random();

        for (int i = 0; i < moves; i++) {
            int col = random.nextInt(board.getWidth());
            // Если выбранная колонка заполнена, находим следующую свободную.
            if (!board.isColumnFilled(col)) {
                board.drop(col, discs[i % discs.length]);
            } else {
                for (int c = 0; c < board.getWidth(); c++) {
                    if (!board.isColumnFilled(c)) {
                        board.drop(c, discs[i % discs.length]);
                        break;
                    }
                }
            }
        }
        System.out.println("=== Доска заполнена примерно на половину перед последовательными поворотами ===");
        renderer.show(board);

        board.rotate(Direction.LEFT);
        System.out.println("После поворота: " + Direction.LEFT);
        renderer.show(board);

        board.rotate(Direction.UPSIDE_DOWN);
        System.out.println("После поворота: " + Direction.UPSIDE_DOWN);
        renderer.show(board);

        board.rotate(Direction.RIGHT);
        System.out.println("После поворота: " + Direction.RIGHT);
        renderer.show(board);

        board.rotate(Direction.UPSIDE_DOWN);
        System.out.println("После поворота: " + Direction.UPSIDE_DOWN);
        renderer.show(board);

        // Подсчитаем общее число токенов и сравним с ожидаемым.
        int tokenCount = 0;
        for (int row = 0; row < board.getHeight(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
                if (!board.isEmptySlot(row, col)) {
                    tokenCount++;
                }
            }
        }
        assertEquals(moves, tokenCount, "Количество токенов должно сохраниться после последовательных поворотов.");
    }

    /**
     * Тест 4: Несколько последовательных поворотов доски, неравномерно заполненной на четверть.
     */
    @Test
    public void testSequentialRotationsQuarterFilledBoard() {
        Board board = new Board(new DefaultBoardState(Board.Size.DEFAULT));
        Renderer renderer = new RendererForIdea();
        int totalCells = board.getHeight() * board.getWidth();
        int moves = totalCells / 4;  // Примерно 25% заполненности (около 10 токенов для DEFAULT).
        Disc[] discs = Disc.values();
        Random random = new Random();

        for (int i = 0; i < moves; i++) {
            int col = random.nextInt(board.getWidth());
            if (!board.isColumnFilled(col)) {
                board.drop(col, discs[i % discs.length]);
            } else {
                for (int c = 0; c < board.getWidth(); c++) {
                    if (!board.isColumnFilled(c)) {
                        board.drop(c, discs[i % discs.length]);
                        break;
                    }
                }
            }
        }
        System.out.println("=== Доска заполнена примерно на четверть перед последовательными поворотами ===");
        renderer.show(board);

        board.rotate(Direction.LEFT);
        System.out.println("После поворота: " + Direction.LEFT);
        renderer.show(board);

        board.rotate(Direction.UPSIDE_DOWN);
        System.out.println("После поворота: " + Direction.UPSIDE_DOWN);
        renderer.show(board);

        board.rotate(Direction.RIGHT);
        System.out.println("После поворота: " + Direction.RIGHT);
        renderer.show(board);

        board.rotate(Direction.UPSIDE_DOWN);
        System.out.println("После поворота: " + Direction.UPSIDE_DOWN);
        renderer.show(board);

        System.out.println("=== Доска после последовательных поворотов (неравномерно заполнена на четверть) ===");
        renderer.show(board);

        int tokenCount = 0;
        for (int row = 0; row < board.getHeight(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
                if (!board.isEmptySlot(row, col)) {
                    tokenCount++;
                }
            }
        }
        assertEquals(moves, tokenCount, "Количество токенов должно сохраниться после последовательных поворотов.");
    }

    /**
     * Тест 5: Несколько последовательных поворотов доски, неравномерно заполненной на три четверти.
     */
    @Test
    public void testSequentialRotationsThreeQuartersFilledBoard() {
        Board board = new Board(new DefaultBoardState(Board.Size.DEFAULT));
        Renderer renderer = new RendererForIdea();
        int totalCells = board.getHeight() * board.getWidth();  // Для DEFAULT: 42 ячейки.
        int moves = (totalCells * 3) / 4;  // Примерно 75% заполненности (около 31-32 токена).
        Disc[] discs = Disc.values();
        Random random = new Random();

        for (int i = 0; i < moves; i++) {
            int col = random.nextInt(board.getWidth());
            if (!board.isColumnFilled(col)) {
                board.drop(col, discs[i % discs.length]);
            } else {
                for (int c = 0; c < board.getWidth(); c++) {
                    if (!board.isColumnFilled(c)) {
                        board.drop(c, discs[i % discs.length]);
                        break;
                    }
                }
            }
        }
        System.out.println("=== Доска заполнена примерно на три четверти перед последовательными поворотами ===");
        renderer.show(board);

        board.rotate(Direction.LEFT);
        System.out.println("После поворота: " + Direction.LEFT);
        renderer.show(board);

        board.rotate(Direction.UPSIDE_DOWN);
        System.out.println("После поворота: " + Direction.UPSIDE_DOWN);
        renderer.show(board);

        board.rotate(Direction.RIGHT);
        System.out.println("После поворота: " + Direction.RIGHT);
        renderer.show(board);

        board.rotate(Direction.UPSIDE_DOWN);
        System.out.println("После поворота: " + Direction.UPSIDE_DOWN);
        renderer.show(board);

        System.out.println("=== Доска после последовательных поворотов (неравномерно заполнена на три четверти) ===");
        renderer.show(board);

        int tokenCount = 0;
        for (int row = 0; row < board.getHeight(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
                if (!board.isEmptySlot(row, col)) {
                    tokenCount++;
                }
            }
        }
        assertEquals(moves, tokenCount, "Количество токенов должно сохраниться после последовательных поворотов.");
    }

    /**
     * Тест 6: Проведение игры до момента, когда вся доска заполняется, и проверка состояния.
     */
    @Test
    public void testGameUntilBoardFull() {
        Board board = new Board(new DefaultBoardState(Board.Size.DEFAULT));
        Renderer renderer = new RendererForIdea();
        System.out.println("=== Начало игры: заполнение доски токенами ===");

        Disc[] discs = Disc.values();
        int tokenIndex = 0;

        while (!board.isBoardFilled()) {
            for (int col = 0; col < board.getWidth(); col++) {
                if (!board.isColumnFilled(col)) {
                    board.drop(col, discs[tokenIndex % discs.length]);
                    tokenIndex++;
                }
            }
        }
        System.out.println("=== Финальная доска после заполнения ===");
        renderer.show(board);
        assertTrue(board.isBoardFilled(), "Доска должна быть заполненной после проведения игры до конца.");
    }
}
