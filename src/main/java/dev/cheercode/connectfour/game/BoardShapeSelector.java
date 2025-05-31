package dev.cheercode.connectfour.game;

import dev.cheercode.connectfour.dialog.Dialog;
import dev.cheercode.connectfour.dialog.impl.IntegerMinMaxDialog;
import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.renderer.renderer_for_idea.color.BackgroundColor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BoardShapeSelector {
    private static final int SHAPES_PER_ROW = 5;
    private static final String SHAPES_DIRECTORY = "board_masks";
    private static final String COLOR_RESET = "\u001B[49m";
    private static final String OUT_OF_BOARD_SLOT = " ";
    private final String onBoardSlot;

    public BoardShapeSelector(BackgroundColor backgroundColor) {
        this.onBoardSlot = backgroundColor.getSequence() + " " + COLOR_RESET;
    }

    public boolean[][] select(Board.Size size) {
        String folderPath = SHAPES_DIRECTORY + "/" + size.name().toLowerCase();
        String listFilePath = folderPath + "/shapes.list";

        InputStream listStream = getClass().getClassLoader().getResourceAsStream(listFilePath);
        if (listStream == null) {
            throw new IllegalArgumentException("Shapes list file was not found:" + listFilePath);
        }

        List<String> fileNames;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(listStream))) {
            fileNames = br.lines()
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Shapes file reading error", e);
        }

        if (fileNames.isEmpty()) {
            throw new IllegalArgumentException("Empty shapes list: " + listFilePath);
        }

        List<boolean[][]> shapes = new ArrayList<>();
        for (String fileName : fileNames) {
            String filePath = folderPath + "/" + fileName;
            InputStream shapeStream = getClass().getClassLoader().getResourceAsStream(filePath);
            if (shapeStream == null) {
                throw new IllegalArgumentException("File with a shape was not found: " + filePath);
            }
            boolean[][] shape = readShapeFromStream(shapeStream);
            shapes.add(shape);
        }

        if (shapes.isEmpty()) {
            throw new IllegalArgumentException("No available shapes for size: " + size.name());
        }

        int minOption = 1;
        int maxOption = shapes.size();

        String selectionTitle = buildSelectionTitle(shapes, minOption, maxOption);
        int maskIndex = askShapeIndex(selectionTitle, minOption, maxOption);
        return shapes.get(maskIndex);
    }

    private boolean[][] readShapeFromStream(InputStream shapeStream) {
        List<String> lines;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(shapeStream))) {
            lines = reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Shape File Reading Error", e);
        }
        int rows = lines.size();
        int columns = (rows > 0) ? lines.get(0).length() : 0;
        boolean[][] shape = new boolean[rows][columns];
        for (int i = 0; i < rows; i++) {
            String line = lines.get(i);
            for (int j = 0; j < columns; j++) {
                shape[i][j] = line.charAt(j) == '+';
            }
        }
        return shape;
    }

    private String buildSelectionTitle(List<boolean[][]> shapes, int min, int max) {
        StringBuilder title = new StringBuilder();
        title.append(String.format("Выберите форму доски (%d - %d):\n", min, max));

        for (int start = 0; start < shapes.size(); start += SHAPES_PER_ROW) {
            int end = Math.min(start + SHAPES_PER_ROW, shapes.size());
            title.append(addShapesGroup(start, end, shapes));
            if (end < shapes.size()) {
                title.append("\n");
            }
        }

        return title.toString();
    }

    private String addShapesGroup(int from, int to, List<boolean[][]> shapes) {
        StringBuilder shapesGroup = new StringBuilder();
        shapesGroup.append(buildOptionNumbersRow(from, to, shapes));
        shapesGroup.append("\n");
        shapesGroup.append(buildShapePreviews(from, to, shapes));
        return shapesGroup.toString();
    }

    private String buildOptionNumbersRow(int from, int to, List<boolean[][]> shapes) {
        StringBuilder numbersRow = new StringBuilder();
        int spacesWidth = shapes.get(0)[0].length - 1;
        for (int i = from + 1; i <= to; i++) {
            numbersRow.append(i).append(" ".repeat(spacesWidth));
            if (i < to) {
                numbersRow.append("\t|\t");
            }
        }
        return numbersRow.toString();
    }

    private String buildShapePreviews(int from, int to, List<boolean[][]> shapes) {
        StringBuilder shapePreviews = new StringBuilder();
        for (int row = 0; row < shapes.get(0).length; row++) {
            for (int shapeIndex = from; shapeIndex < to; shapeIndex++) {
                boolean[][] shape = shapes.get(shapeIndex);
                for (int column = 0; column < shape[0].length; column++) {
                    shapePreviews.append(shape[row][column] ? onBoardSlot : OUT_OF_BOARD_SLOT);
                }
                if (shapeIndex < to - 1) {
                    shapePreviews.append("\t|\t");
                }
            }
            shapePreviews.append("\n");
        }
        return shapePreviews.toString();
    }

    private int askShapeIndex(String selectionMessage, int min, int max) {
        Dialog<Integer> dialog = new IntegerMinMaxDialog(selectionMessage, "Неправильный ввод.", min, max);
        return dialog.input() - 1;
    }
}
