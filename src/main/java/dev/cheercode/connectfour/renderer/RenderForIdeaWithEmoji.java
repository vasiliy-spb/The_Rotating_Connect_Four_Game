package dev.cheercode.connectfour.renderer;

import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;

public class RenderForIdeaWithEmoji implements Renderer {
    private static final String RED_CIRCLE = "\uD83D\uDD34";
    private static final String PURPLE_CIRCLE = "\uD83D\uDFE3";
    private static final String BLUE_CIRCLE = "\uD83D\uDD35";
    private static final String GREEN_CIRCLE = "\uD83D\uDFE2";
    private static final String YELLOW_CIRCLE = "\uD83D\uDFE1";
    private static final String ORANGE_CIRCLE = "\uD83D\uDFE0";
    private static final String BLACK_CIRCLE = "⚫";
    private static final String WHITE_CIRCLE = "⚪";
    private static final String BROWN_CIRCLE = "\uD83D\uDFE4";
    private static final String BACKGROUND_COLOR = "\u001B[7m";
    private static final String BORDER_COLOR = "\u001B[34m";
    private static final String RESET = "\u001B[0m";
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
            case RED -> RED_CIRCLE;
            case BLUE -> BLUE_CIRCLE;
            case YELLOW -> YELLOW_CIRCLE;
            case GREEN -> GREEN_CIRCLE;
        };
    }

    private StringBuilder buildFooter(int width) {
        StringBuilder line = new StringBuilder();
        line.append(FOOTER_SPACE);
        for (int col = 1; col <= width; col++) {
            line.append(String.format(FOOTER_SLOT_TEMPLATE, col));
        }
        line.append(NEW_LINE);
        return line;
    }
}