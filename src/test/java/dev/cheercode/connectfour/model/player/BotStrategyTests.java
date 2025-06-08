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

/*
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index 10 out of bounds for length 10
	at dev.cheercode.connectfour.model.board.AbstractBoardState.applyGravity(AbstractBoardState.java:137)
	at dev.cheercode.connectfour.model.board.RightRotatedBoardState.<init>(RightRotatedBoardState.java:9)
	at dev.cheercode.connectfour.model.board.AbstractBoardState.rotate(AbstractBoardState.java:118)
	at dev.cheercode.connectfour.model.board.Board.rotate(Board.java:45)
	at dev.cheercode.connectfour.game.Game.rotateBoard(Game.java:120)
	at dev.cheercode.connectfour.game.Game.nextTurn(Game.java:72)
	at dev.cheercode.connectfour.game.Game.start(Game.java:56)
	at dev.cheercode.connectfour.OnlyBotMain.main(OnlyBotMain.java:23)
 */