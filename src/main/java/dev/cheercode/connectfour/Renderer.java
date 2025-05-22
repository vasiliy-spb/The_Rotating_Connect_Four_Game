package dev.cheercode.connectfour;

import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.model.Disc;

public class Renderer {
    private static final String circle = "●";
    private static final String backgroundColor = "\u001B[7m"; //
    private static final String borderColor = "\u001B[34m";
    private static final String reset = "\u001B[0m";
    private static final String red = "\u001B[101m" + circle + reset + backgroundColor + borderColor;
    private static final String blue = "\u001B[104m" + circle + reset + backgroundColor + borderColor;
    private static final String yellow = "\u001B[103m" + circle + reset + backgroundColor + borderColor;
    private static final String green = "\u001B[102m" + circle + reset + backgroundColor + borderColor;
    private static final String slotTemplate = " %s |";
    private static final String footerSlotTemplate = " %s  ";
    private static final String newLine = "\n";
    private static final String emptySlot = "   |";
    private static final String rowNumberTemplate = "%2d " + backgroundColor + borderColor + "|";
    private static final String separatorSpace = "   " + backgroundColor + borderColor + "+";
    private static final String separatorSlot = "---+";
    private static final String footerSpace = "    ";

    public void show(Board board) {
        int height = board.getHeight();
        int width = board.getWidth();

        StringBuilder field = new StringBuilder();

        field.append(getSeparatorLine(width));

        for (int row = 0; row < height; row++) {
            field.append(String.format(rowNumberTemplate, row + 1));
            field.append(backgroundColor);
            for (int col = 0; col < width; col++) {
                if (board.isEmptySlot(row, col)) {
                    field.append(emptySlot);
                    continue;
                }
                Disc disc = board.get(row, col);
                String sprite = getColoredCircle(disc);
                field.append(String.format(slotTemplate, sprite));
            }
            field.append(reset);
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
        line.append(reset);
        line.append(newLine);
        return line;
    }

    private String getColoredCircle(Disc disc) {
        return switch (disc) {
            case RED -> red;
            case BLUE -> blue;
            case YELLOW -> yellow;
            case GREEN -> green;
        };
    }

    private StringBuilder getFooter(int width) {
        StringBuilder line = new StringBuilder();
        line.append(footerSpace);
        for (int col = 1; col <= width; col++) {
            line.append(String.format(footerSlotTemplate, col));
        }
        line.append(newLine);
        return line;
    }
}
