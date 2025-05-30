package dev.cheercode.connectfour.factory;

import dev.cheercode.connectfour.dialog.Dialog;
import dev.cheercode.connectfour.dialog.impl.IntegerMinMaxDialog;
import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.renderer.renderer_for_idea.color.BackgroundColor;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.StreamSupport;

public class BoardMaskSelector {
    private static final int MASKS_PER_ROW = 5;
    private static final String RESET = "\u001B[49m";
    private static final String EMPTY_SLOT = " ";
    private final String onBoardSlot;

    public BoardMaskSelector(BackgroundColor backgroundColor) {
        this.onBoardSlot = backgroundColor.getSequence() + " " + RESET;
    }

    public boolean[][] select(Board.Size size) {
        Path directoryPath = Paths.get("src/main/resources/board_masks/" + size.name().toLowerCase());

        if (!Files.exists(directoryPath) || !Files.isDirectory(directoryPath)) {
            throw new IllegalArgumentException("Directory not found: " + directoryPath);
        }

        List<boolean[][]> masks;
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directoryPath)) {
            masks = StreamSupport.stream(directoryStream.spliterator(), false)
                    .map(this::getMaskFrom)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (masks.isEmpty()) {
            throw new IllegalArgumentException("Not enough masks to show for size: " + size.name());
        }

        int min = 1;
        int max = masks.size();

        String message = buildTitle(masks, min, max);
        int maskIndex = askMaskIndex(message, min, max);
        return masks.get(maskIndex);
    }

    private String buildTitle(List<boolean[][]> masks, int min, int max) {
        StringBuilder title = new StringBuilder();
        title.append(String.format("Выберите форму доски (%d - %d):\n", min, max));

        for (int start = 0; start < masks.size(); start += MASKS_PER_ROW) {
            int end = Math.min(start + MASKS_PER_ROW, masks.size());
            title.append(addMasksTemplates(start, end, masks));
            if (end < masks.size()) {
                title.append("\n");
            }
        }

        return title.toString();
    }

    private String addMasksTemplates(int from, int to, List<boolean[][]> masks) {
        StringBuilder message = new StringBuilder();
        message.append(buildNumbersRow(from, to, masks));
        message.append("\n");
        message.append(buildConsolePictures(from, to, masks));
        return message.toString();
    }

    private String buildNumbersRow(int from, int to, List<boolean[][]> masks) {
        StringBuilder numbersRow = new StringBuilder();
        int spacesWidth = masks.get(0)[0].length - 1;
        for (int i = from + 1; i <= to; i++) {
            numbersRow.append(i).append(" ".repeat(spacesWidth));
            if (i < to) {
                numbersRow.append("\t|\t");
            }
        }
        return numbersRow.toString();
    }

    private String buildConsolePictures(int from, int to, List<boolean[][]> masks) {
        StringBuilder pictures = new StringBuilder();
        for (int row = 0; row < masks.get(0).length; row++) {
            for (int i = from; i < to; i++) {
                boolean[][] mask = masks.get(i);
                for (int column = 0; column < mask[0].length; column++) {
                    pictures.append(mask[row][column] ? onBoardSlot : EMPTY_SLOT);
                }
                if (i < to - 1) {
                    pictures.append("\t|\t");
                }
            }
            pictures.append("\n");
        }
        return pictures.toString();
    }

    private int askMaskIndex(String message, int min, int max) {
        Dialog<Integer> dialog = new IntegerMinMaxDialog(message, "Неправильный ввод.", min, max);
        return dialog.input() - 1;
    }

    private void printMask(boolean[][] mask) {
        for (int row = 0; row < mask.length; row++) {
            for (int column = 0; column < mask[0].length; column++) {
                System.out.print(mask[row][column] ? onBoardSlot : EMPTY_SLOT);
            }
            System.out.println();
        }
        System.out.println();
    }

    private boolean[][] getMaskFrom(Path path) {
        try {
            List<String> lines = Files.readAllLines(path);
            int height = lines.size();
            int width = lines.get(0).length();
            boolean[][] mask = new boolean[height][width];
            for (int i = 0; i < height; i++) {
                String line = lines.get(i);
                for (int j = 0; j < width; j++) {
                    mask[i][j] = line.charAt(j) == '+';
                }
            }
            return mask;
        } catch (IOException e) {
            throw new IllegalArgumentException("Mask reading error.");
        }
    }


    // for test
//    public static void main(String[] args) {
//        BoardMaskSelector selector = new BoardMaskSelector();
//        boolean[][] mask = selector.select(Board.Size.ROW7_COLUMN10);
//        selector.printMask(mask);
//    }
}
