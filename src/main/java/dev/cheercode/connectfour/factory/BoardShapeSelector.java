package dev.cheercode.connectfour.factory;

import dev.cheercode.connectfour.dialog.Dialog;
import dev.cheercode.connectfour.dialog.impl.IntegerMinMaxDialog;
import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.renderer.renderer_for_idea.color.BackgroundColor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.StreamSupport;

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
        Path tailOfShapesDirectory = Path.of(SHAPES_DIRECTORY)
                .resolve(size.name().toLowerCase());

        URL resourceUrl = getClass().getClassLoader().getResource(tailOfShapesDirectory.toString());
        if (resourceUrl == null) {
            throw new IllegalArgumentException("Directory not found: " + tailOfShapesDirectory);
        }

        Path fullShapesDirectory = null;
        try {
            fullShapesDirectory = Paths.get(resourceUrl.toURI());
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Invalid shapes directory URI", e);
        }

        if (!Files.exists(fullShapesDirectory) || !Files.isDirectory(fullShapesDirectory)) {
            throw new IllegalArgumentException("Directory not found: " + fullShapesDirectory);
        }

        List<boolean[][]> shapes;
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(fullShapesDirectory)) {
            shapes = StreamSupport.stream(directoryStream.spliterator(), false)
                    .map(this::readShapeFrom)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (shapes.isEmpty()) {
            throw new IllegalArgumentException("Not enough shapes to show for size: " + size.name());
        }

        int minOption = 1;
        int maxOption = shapes.size();

        String selectionTitle = buildSelectionTitle(shapes, minOption, maxOption);
        int maskIndex = askShapeIndex(selectionTitle, minOption, maxOption);
        return shapes.get(maskIndex);
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

    private void printShape(boolean[][] shape) {
        for (int row = 0; row < shape.length; row++) {
            for (int column = 0; column < shape[0].length; column++) {
                System.out.print(shape[row][column] ? onBoardSlot : OUT_OF_BOARD_SLOT);
            }
            System.out.println();
        }
        System.out.println();
    }

    private boolean[][] readShapeFrom(Path path) {
        try {
            List<String> lines = Files.readAllLines(path);
            int rows = lines.size();
            int columns = lines.get(0).length();
            boolean[][] shape = new boolean[rows][columns];
            for (int rom = 0; rom < rows; rom++) {
                String line = lines.get(rom);
                for (int column = 0; column < columns; column++) {
                    shape[rom][column] = line.charAt(column) == '+';
                }
            }
            return shape;
        } catch (IOException e) {
            throw new IllegalArgumentException("Shape reading error.");
        }
    }
}
