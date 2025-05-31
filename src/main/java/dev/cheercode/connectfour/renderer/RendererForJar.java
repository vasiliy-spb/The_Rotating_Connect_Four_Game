package dev.cheercode.connectfour.renderer;

import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

public class RendererForJar implements Renderer {

    private static final String SLOT_TEMPLATE = " %s  |";
    private static final String ROW_NUMBER_TEMPLATE = "%2s ";
    private static final String FOOTER_SLOT_TEMPLATE = " %s   ";
    private static final String CIRCLE = "⬤";
    private static final String EMPTY_SLOT = "    ";
    private static final String SLOT_BORDER = "|";
    private static final String SEPARATOR_SPACE = "   ";
    private static final String SEPARATOR_SLOT = "----";
    private static final String SEPARATOR_BORDER = "+";
    private static final String FOOTER_SPACE = "     ";
    private static final String NEW_LINE = System.lineSeparator();

    @Override
    public void show(Board board) {
        AnsiConsole.systemInstall();
        int height = board.getHeight();
        int width = board.getWidth();

        StringBuilder field = new StringBuilder();

        String headBorder = buildSeparatorLine(-1, width, board);
        field.append(headBorder);

        for (int row = 0; row < height; row++) {

            String rowNumber = String.format(ROW_NUMBER_TEMPLATE, row + 1);
            if (!board.isOnField(row, 0)) {
                rowNumber += SLOT_BORDER;
            } else {
                rowNumber += decorateWithBackground(SLOT_BORDER);
            }
            field.append(rowNumber);

            for (int column = 0; column < width; column++) {
                if (!board.isOnField(row, column)) {
                    if (column + 1 < width && board.isOnField(row, column + 1)) {
                        field.append(EMPTY_SLOT);
                        field.append(decorateWithBackground(SLOT_BORDER));
                    } else {
                        field.append(EMPTY_SLOT);
                        field.append(SLOT_BORDER);
                    }
                    continue;
                }

                if (board.isEmptySlot(row, column)) {
                    field.append(decorateWithBackground(EMPTY_SLOT));
                    field.append(decorateWithBackground(SLOT_BORDER));
                    continue;
                }

                Disc disk = board.get(row, column);
                field.append(buildSpriteSlotFor(disk));
            }

            field.append(NEW_LINE);
            String separator = buildSeparatorLine(row, width, board);
            field.append(separator);
        }

        String footer = buildFooter(width);
        field.append(footer);

        System.out.println(field.toString());
        AnsiConsole.systemUninstall();
    }

    private String buildSeparatorLine(int row, int width, Board board) {
        StringBuilder line = new StringBuilder();

        if ((row >= 0 && board.isOnField(row, 0)) || (row + 1 < board.getHeight() && board.isOnField(row + 1, 0))) {
            line.append(SEPARATOR_SPACE).append(decorateWithBackground(SEPARATOR_BORDER));
        } else {
            line.append(SEPARATOR_SPACE).append(SEPARATOR_BORDER);
        }
        for (int col = 0; col < width; col++) {
            if ((row >= 0 && board.isOnField(row, col)) ||
                (row + 1 < board.getHeight() && board.isOnField(row + 1, col))) {
                line.append(decorateWithBackground(SEPARATOR_SLOT))
                        .append(decorateWithBackground(SEPARATOR_BORDER));
            } else {
                if (col + 1 < width &&
                    ((row >= 0 && board.isOnField(row, col + 1)) ||
                     (row < board.getHeight() - 1 && board.isOnField(row + 1, col + 1)))) {
                    line.append(SEPARATOR_SLOT)
                            .append(decorateWithBackground(SEPARATOR_BORDER));
                } else {
                    line.append(SEPARATOR_SLOT).append(SEPARATOR_BORDER);
                }
            }
        }
        line.append(NEW_LINE);
        return line.toString();
    }

    private String buildFooter(int width) {
        StringBuilder footer = new StringBuilder();
        footer.append(FOOTER_SPACE);
        for (int column = 1; column <= width; column++) {
            footer.append(String.format(FOOTER_SLOT_TEMPLATE, column));
        }
        footer.append(NEW_LINE);
        return footer.toString();
    }

    private String buildSpriteSlotFor(Disc disk) {
        String coloredCircle = switch (disk) {
            case BLACK -> decorateTextWithColor(CIRCLE, Ansi.Color.BLACK);
            case RED -> decorateTextWithColor(CIRCLE, Ansi.Color.RED);
            case GREEN -> decorateTextWithColor(CIRCLE, Ansi.Color.GREEN);
            case YELLOW -> decorateTextWithColor(CIRCLE, Ansi.Color.YELLOW);
            case BLUE -> decorateTextWithColor(CIRCLE, Ansi.Color.BLUE);
            case PURPLE -> decorateTextWithColor(CIRCLE, Ansi.Color.MAGENTA);
            case CYAN -> decorateTextWithColor(CIRCLE, Ansi.Color.CYAN);
            case WHITE -> decorateTextWithColor(CIRCLE, Ansi.Color.WHITE);
            default -> CIRCLE;
        };
        String slotText = String.format(SLOT_TEMPLATE, coloredCircle);
        return decorateWithBackground(slotText);
    }

    private String decorateWithBackground(String text) {
        return Ansi.ansi().bg(Ansi.Color.CYAN).a(text).reset().toString();
    }

    private String decorateTextWithColor(String text, Ansi.Color color) {
        return Ansi.ansi().fgBright(color).a(text).fg(Ansi.Color.DEFAULT).toString();
    }
}

