package dev.cheercode.connectfour.renderer;

import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;
import org.fusesource.jansi.Ansi;

public class RendererForJar implements Renderer {
    private static final String CIRCLE = "⬤";
    private static final String SLOT_TEMPLATE = " %s  |";
    private static final String FOOTER_SLOT_TEMPLATE = "  %s  ";
    private static final String NEW_LINE = "\n";
    private static final String EMPTY_SLOT = "    |";
    private static final String SEPARATOR_SLOT = "----+";
    private static final String FOOTER_SPACE = "   ";
    private static final Ansi.Color BACKGROUND_COLOR = Ansi.Color.CYAN;
    private static final Ansi.Color BORDER_COLOR = Ansi.Color.WHITE;
    private static final Ansi.Color TEXT_COLOR = Ansi.Color.BLACK;

    @Override
    public void show(Board board) {
        int height = board.getHeight();
        int width = board.getWidth();

        StringBuilder field = new StringBuilder();

        field.append(buildSeparatorLine(width));

        for (int row = 0; row < height; row++) {
            field.append(rowNumberTemplate(row + 1));
            field.append(getBackground());
            for (int column = 0; column < width; column++) {
                if (board.isEmptySlot(row, column)) {
                    field.append(EMPTY_SLOT);
                    continue;
                }
                Disc disc = board.get(row, column);
                String sprite = getSpriteFor(disc);
                field.append(String.format(SLOT_TEMPLATE, sprite));
            }
            field.append(getReset()).append(NEW_LINE);
            field.append(buildSeparatorLine(width));
        }

        field.append(buildFooter(width));
        System.out.println(field);
    }

    private StringBuilder buildSeparatorLine(int width) {
        StringBuilder line = new StringBuilder();
        line.append(getSeparatorSpace());
        line.append(SEPARATOR_SLOT.repeat(width));
        line.append(getReset()).append(NEW_LINE);
        return line;
    }

    private String getSeparatorSpace() {
        return Ansi.ansi()
                .fgBright(BORDER_COLOR)
                .a("   ")
                .bg(BACKGROUND_COLOR)
                .a("+")
                .toString();
    }

    private String getReset() {
        return Ansi.ansi().reset().toString();
    }


    private String rowNumberTemplate(int row) {
        return Ansi.ansi()
                .fg(TEXT_COLOR)
                .a(String.format("%2d ", row))
                .bg(BACKGROUND_COLOR)
                .fgBright(BORDER_COLOR)
                .a("|")
                .toString();
    }

    private String getBackground() {
        return Ansi.ansi().bg(BACKGROUND_COLOR).toString();
    }

    private String getSpriteFor(Disc disc) {
        String colorCode = switch (disc) {
            case RED -> getRed();
            case BLUE -> getBlue();
            case YELLOW -> getYellow();
            case GREEN -> getGreen();
        };
        return colorCode + CIRCLE + getBackground() + getBorder();
    }

    private String getRed() {
        return Ansi.ansi().fgBrightRed().toString();
    }

    private String getBlue() {
        return Ansi.ansi().fgBrightBlue().toString();
    }

    private String getYellow() {
        return Ansi.ansi().fgBrightYellow().toString();
    }

    private String getGreen() {
        return Ansi.ansi().fgBrightGreen().toString();
    }

    private String getBorder() {
        return Ansi.ansi().fgBright(BORDER_COLOR).toString();
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
