package dev.cheercode.connectfour.renderer;

import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;

public class RendererForIdea implements Renderer {
    private static final String CIRCLE = "⬤";
    private static final String BACKGROUND_COLOR = "\u001B[7m";
    private static final String BORDER_COLOR = "\u001B[34m";
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[101m";
    private static final String BLUE = "\u001B[104m";
    private static final String YELLOW = "\u001B[103m";
    private static final String GREEN = "\u001B[102m";
    private static final String SLOT_TEMPLATE = " %s |";
    private static final String FOOTER_SLOT_TEMPLATE = " %s   ";
    private static final String NEW_LINE = "\n";
    private static final String EMPTY_SLOT = "    |";
    private static final String ROW_NUMBER_TEMPLATE = "%2d " + BACKGROUND_COLOR + BORDER_COLOR + "|";
    private static final String SEPARATOR_SPACE = "   " + BACKGROUND_COLOR + BORDER_COLOR + "+";
    private static final String SEPARATOR_SLOT = "----+";
    private static final String FOOTER_SPACE = "     ";

    @Override
    public void show(Board board) {
        int height = board.getHeight();
        int width = board.getWidth();

        StringBuilder field = new StringBuilder();

        field.append(buildSeparatorLine(width));

        for (int row = 0; row < height; row++) {
            field.append(String.format(ROW_NUMBER_TEMPLATE, row + 1));
            field.append(BACKGROUND_COLOR);
            for (int column = 0; column < width; column++) {
                if (board.isEmptySlot(row, column)) {
                    field.append(EMPTY_SLOT);
                    continue;
                }
                Disc disc = board.get(row, column);
                String sprite = getSpriteFor(disc);
                field.append(String.format(SLOT_TEMPLATE, sprite));
            }
            field.append(RESET);
            field.append(NEW_LINE);

            field.append(buildSeparatorLine(width));
        }

        field.append(buildFooter(width));

        System.out.println(field);
    }

    private StringBuilder buildSeparatorLine(int width) {
        StringBuilder line = new StringBuilder();
        line.append(SEPARATOR_SPACE);
        line.append(SEPARATOR_SLOT.repeat(width));
        line.append(RESET);
        line.append(NEW_LINE);
        return line;
    }

    private String getSpriteFor(Disc disc) {
        return switch (disc) {
            case RED -> RED + CIRCLE + RESET + BACKGROUND_COLOR + BORDER_COLOR;
            case BLUE -> BLUE + CIRCLE + RESET + BACKGROUND_COLOR + BORDER_COLOR;
            case YELLOW -> YELLOW + CIRCLE + RESET + BACKGROUND_COLOR + BORDER_COLOR;
            case GREEN -> GREEN + CIRCLE + RESET + BACKGROUND_COLOR + BORDER_COLOR;
        };
    }

    private StringBuilder buildFooter(int width) {
        StringBuilder line = new StringBuilder();
        line.append(FOOTER_SPACE);
        for (int column = 1; column <= width; column++) {
            line.append(String.format(FOOTER_SLOT_TEMPLATE, column));
        }
        line.append(NEW_LINE);
        return line;
    }
}
