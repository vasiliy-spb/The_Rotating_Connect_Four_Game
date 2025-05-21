package dev.cheercode.connectfour;

import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.model.Disc;

public class Renderer {
    private static final String reset = "\u001B[0m";
    private static final String red = "\u001B[31m";
    private static final String blue = "\u001B[34m";
    private static final String yellow = "\u001B[33m";
    private static final String green = "\u001B[32m";
    private static final String slotTemplate = " %s |";
    private static final String circle = "●";
    private static final String newLine = "\n";
    private static final String emptySlot = "   |";
    private static final String rowNumberTemplate = "%2d |";
    private static final String separatorSpace = "   +";
    private static final String separatorSlot = "---+";
    private static final String footerSpace = "    ";

    public void show(Board board) {
        int height = board.getHeight();
        int width = board.getWidth();

        StringBuilder field = new StringBuilder();

        field.append(getSeparatorLine(width));

        for (int row = 0; row < height; row++) {
            field.append(String.format(rowNumberTemplate, row + 1));
            for (int col = 0; col < width; col++) {
                if (board.isEmptySlot(row, col)) {
                    field.append(emptySlot);
                    continue;
                }
                Disc disc = board.get(row, col);
                String sprite = getColoredCircle(disc);
                field.append(String.format(slotTemplate, sprite));
            }
            field.append(newLine);

            field.append(getSeparatorLine(width));
        }

        field.append(getFooter(width));

        System.out.println(field);
    }

    private StringBuilder getSeparatorLine(int width) {
        StringBuilder line = new StringBuilder();
        line.append(separatorSpace);
        line.append(separatorSlot.repeat(width));
        line.append(newLine);
        return line;
    }

    private String getColoredCircle(Disc disc) {
        return switch (disc) {
            case RED -> red + circle + reset;
            case BLUE -> blue + circle + reset;
            case YELLOW -> yellow + circle + reset;
            case GREEN -> green + circle + reset;
        };
    }

    private StringBuilder getFooter(int width) {
        StringBuilder line = new StringBuilder();
        line.append(footerSpace);
        for (int col = 1; col <= width; col++) {
            line.append(String.format(slotTemplate, col));
        }
        line.append(newLine);
        return line;
    }
}
