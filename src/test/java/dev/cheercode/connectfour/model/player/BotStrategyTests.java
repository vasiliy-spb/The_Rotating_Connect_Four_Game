package dev.cheercode.connectfour.model.player;

import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.model.board.DefaultBoardState;
import dev.cheercode.connectfour.model.board.Direction;
import dev.cheercode.connectfour.renderer.Renderer;
import dev.cheercode.connectfour.renderer.renderer_for_idea.RendererForIdea;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BotStrategyTests {
    private static final Renderer RENDERER = new RendererForIdea();

    @Test
    public void test01() {
        Board board = createBoardWith(10, Board.Size.ROW7_COLUMN10);

        Player victoryBot = new Player(Disc.RED, new VictoryScoreBasedBotStrategy());
        Player blockingBot = new Player(Disc.PURPLE, new BlockingScoreBasedBotStrategy());

        board.drop(0, Disc.RED);
        board.drop(1, Disc.PURPLE);
        board.drop(0, Disc.RED);
        board.drop(1, Disc.PURPLE);
        board.drop(0, Disc.RED);

        RENDERER.show(board);

        board.rotate(Direction.RIGHT);

        assertTrue(true);
    }

    @Test
    public void test02() {
        Board board = createBoardWith(31, Board.Size.ROW7_COLUMN10);

        Player victoryBot = new Player(Disc.BLUE, new VictoryScoreBasedBotStrategy());
        Player blockingBot = new Player(Disc.BLACK, new BlockingScoreBasedBotStrategy());

        board.drop(3, Disc.BLUE);

        board.rotate(Direction.LEFT);

        board.drop(0, Disc.BLACK);
        board.drop(0, Disc.BLUE);
        board.drop(0, Disc.BLACK);
        board.drop(0, Disc.BLUE);
        board.drop(0, Disc.BLACK);
        board.rotate(Direction.LEFT);

        board.drop(3, Disc.BLUE);
        board.drop(4, Disc.BLACK);
        board.drop(2, Disc.BLUE);
        board.drop(1, Disc.BLACK);
        board.drop(6, Disc.BLUE);

        board.rotate(Direction.UPSIDE_DOWN);
        board.drop(6, Disc.BLACK);
        board.rotate(Direction.RIGHT);
        board.drop(0, Disc.BLUE);
        board.rotate(Direction.LEFT);
        board.drop(0, Disc.BLACK); // неправильный выбор (должен быть 1) из-за того, что матрица очков считает недоступные ячейки
        board.rotate(Direction.UPSIDE_DOWN);
        board.drop(6, Disc.BLUE);
        board.rotate(Direction.UPSIDE_DOWN);
        board.drop(1, Disc.BLACK);

        RENDERER.show(board);

        victoryBot.makeMove(board);

        // exception
        /*
        java.lang.IllegalStateException: Not a single column is chosen.
        at dev.cheercode.connectfour.model.player.AbstractScoreBasedBotStrategy.chooseMostProfitableColumn(AbstractScoreBasedBotStrategy.java:79)
	at dev.cheercode.connectfour.model.player.AbstractScoreBasedBotStrategy.chooseColumn(AbstractScoreBasedBotStrategy.java:31)
	at dev.cheercode.connectfour.model.player.Player.makeMove(Player.java:24)
	at dev.cheercode.connectfour.game.Game.askColumnIndex(Game.java:128)
	at dev.cheercode.connectfour.game.Game.makeMove(Game.java:78)
	at dev.cheercode.connectfour.game.Game.nextTurn(Game.java:64)
	at dev.cheercode.connectfour.game.Game.start(Game.java:56)
	at dev.cheercode.connectfour.OnlyBotMain.main(OnlyBotMain.java:23)
         */




        RENDERER.show(board);

        board.rotate(Direction.RIGHT);

        assertTrue(true);
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
}
