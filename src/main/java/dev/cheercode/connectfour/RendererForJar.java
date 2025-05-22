package dev.cheercode.connectfour;

import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;
import org.fusesource.jansi.Ansi;

public class RendererForJar implements Renderer {
    private static final String circle = "⬤";
    private static final String slotTemplate = " %s  |";
    private static final String footerSlotTemplate = "  %s  ";
    private static final String newLine = "\n";
    private static final String emptySlot = "    |";
    private static final String separatorSlot = "----+";
    private static final String footerSpace = "   ";
    private static final Ansi.Color BACKGROUND_COLOR = Ansi.Color.CYAN;
    private static final Ansi.Color BORDER_COLOR = Ansi.Color.WHITE;
    private static final Ansi.Color TEXT_COLOR = Ansi.Color.BLUE;

    private String getBackground() {
        return Ansi.ansi().bg(BACKGROUND_COLOR).toString();
    }

    private String getBorder() {
        return Ansi.ansi().fgBright(BORDER_COLOR).toString();
    }

    private String getReset() {
        return Ansi.ansi().reset().toString();
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

    private String rowNumberTemplate(int row) {
        return Ansi.ansi()
                .fg(TEXT_COLOR)
                .a(String.format("%2d ", row))
                .bg(BACKGROUND_COLOR)
                .fgBright(BORDER_COLOR)
                .a("|")
                .toString();
    }

    private String getSeparatorSpace() {
        return Ansi.ansi()
                .fgBright(BORDER_COLOR)
                .a("   ")
                .bg(BACKGROUND_COLOR)
                .a("+")
                .toString();
    }

    @Override
    public void show(Board board) {
        int height = board.getHeight();
        int width = board.getWidth();

        StringBuilder field = new StringBuilder();

        field.append(getSeparatorLine(width));

        for (int row = 0; row < height; row++) {
            field.append(rowNumberTemplate(row + 1));
            field.append(getBackground());
            for (int col = 0; col < width; col++) {
                if (board.isEmptySlot(row, col)) {
                    field.append(emptySlot);
                    continue;
                }
                Disc disc = board.get(row, col);
                String sprite = getColoredCircle(disc);
                field.append(String.format(slotTemplate, sprite));
            }
            field.append(getReset()).append(newLine);
            field.append(getSeparatorLine(width));
        }

        field.append(getFooter(width));
        System.out.println(field);
    }

    private StringBuilder getSeparatorLine(int width) {
        StringBuilder line = new StringBuilder();
        line.append(getSeparatorSpace());
        line.append(separatorSlot.repeat(width));
        line.append(getReset()).append(newLine);
        return line;
    }

    private String getColoredCircle(Disc disc) {
        String colorCode = switch (disc) {
            case RED -> getRed();
            case BLUE -> getBlue();
            case YELLOW -> getYellow();
            case GREEN -> getGreen();
        };
        return colorCode + circle + getBackground() + getBorder();
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
