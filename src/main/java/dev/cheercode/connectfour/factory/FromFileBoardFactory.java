package dev.cheercode.connectfour.factory;

import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.model.board.DefaultBoardState;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FromFileBoardFactory implements BoardFactory {
    @Override
    public Board create() {
        String path = "src/main/resources/board_masks/mask_05.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            StringBuilder input = new StringBuilder();
            while (reader.ready()) {
                input.append(reader.readLine().trim()).append("\n");
            }
            String[] lines = input.toString().trim().split("\n");
            int height = lines.length;
            int width = lines[0].length();
            Board.Size size = getSize(height, width);
            boolean[][] mask = new boolean[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    char ch = lines[i].charAt(j);
                    if (ch == '+') {
                        mask[i][j] = true;
                    }
                }
            }
            Board board = new Board(new DefaultBoardState(size, mask));
            return board;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Board.Size getSize(int height, int width) {
        for (Board.Size size : Board.Size.values()) {
            if (size.getHeight() == height && size.getWidth() == width) {
                return size;
            }
        }
        throw new IllegalArgumentException(String.format("Unsupported board size: (%d - %d)", height, width));
    }
}
