package dev.cheercode.connectfour.renderer.renderer_for_idea;

import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.renderer.Renderer;
import dev.cheercode.connectfour.renderer.renderer_for_idea.color.BackgroundColor;
import dev.cheercode.connectfour.renderer.renderer_for_idea.color.TextColor;
import dev.cheercode.connectfour.renderer.renderer_for_idea.decorator.AppendElementDecorator;
import dev.cheercode.connectfour.renderer.renderer_for_idea.decorator.FormatElementDecorator;
import dev.cheercode.connectfour.renderer.renderer_for_idea.decorator.PaintBackgroundElementDecorator;
import dev.cheercode.connectfour.renderer.renderer_for_idea.decorator.PaintTextElementDecorator;

public class RendererForIdea implements Renderer {
    private static final BackgroundColor BACKGROUND_COLOR = BackgroundColor.BLUE;
    private static final String SLOT_TEMPLATE = " %s |";
    private static final String ROW_NUMBER_TEMPLATE = "%2s ";
    private static final String FOOTER_SLOT_TEMPLATE = " %s   ";
    private static final Element CIRCLE = new SimpleElement("⬤");
    private static final Element EMPTY_SLOT = new SimpleElement("    ");
    private static final Element PAINTED_EMPTY_SLOT = new PaintBackgroundElementDecorator(EMPTY_SLOT, BACKGROUND_COLOR);
    private static final Element SLOT_BORDER = new SimpleElement("|");
    private static final Element PAINTED_SLOT_BORDER = new PaintBackgroundElementDecorator(SLOT_BORDER, BACKGROUND_COLOR);
    private static final Element SEPARATOR_SPACE = new SimpleElement("   ");
    private static final Element SEPARATOR_SLOT = new SimpleElement("----");
    private static final Element PAINTED_SEPARATOR_SLOT = new PaintBackgroundElementDecorator(SEPARATOR_SLOT, BACKGROUND_COLOR);
    private static final Element SEPARATOR_BORDER = new SimpleElement("+");
    private static final Element PAINTED_SEPARATOR_BORDER = new PaintBackgroundElementDecorator(SEPARATOR_BORDER, BACKGROUND_COLOR);
    private static final Element FOOTER_SPACE = new SimpleElement("     ");
    private static final Element NEW_LINE = new SimpleElement(System.lineSeparator());

    @Override
    public void show(Board board) {
        int height = board.getHeight();
        int width = board.getWidth();

        Element field = new SimpleElement("");

        Element headBorder = buildSeparatorLine(-1, width, board);

        field = new AppendElementDecorator(field, headBorder);

        for (int row = 0; row < height; row++) {

            Element rowNumber = new SimpleElement(row + 1);
            if (!board.isOnField(row, 0)) {
                rowNumber = new FormatElementDecorator(rowNumber, ROW_NUMBER_TEMPLATE);
                rowNumber = new AppendElementDecorator(rowNumber, SLOT_BORDER);
            } else {
                rowNumber = new FormatElementDecorator(rowNumber, ROW_NUMBER_TEMPLATE);
                rowNumber = new AppendElementDecorator(rowNumber, PAINTED_SLOT_BORDER);
            }
            field = new AppendElementDecorator(field, rowNumber);

            for (int column = 0; column < width; column++) {
                if (!board.isOnField(row, column)) {
                    if (column + 1 < width && board.isOnField(row, column + 1)) {
                        field = new AppendElementDecorator(field, EMPTY_SLOT);
                        field = new AppendElementDecorator(field, PAINTED_SLOT_BORDER);
                    } else {
                        field = new AppendElementDecorator(field, EMPTY_SLOT);
                        field = new AppendElementDecorator(field, SLOT_BORDER);
                    }
                    continue;
                }

                if (board.isEmptySlot(row, column)) {
                    field = new AppendElementDecorator(field, PAINTED_EMPTY_SLOT);
                    field = new AppendElementDecorator(field, PAINTED_SLOT_BORDER);
                    continue;
                }
                Disc disk = board.get(row, column);
                Element spriteSlot = buildSpriteSlotFor(disk);
                field = new AppendElementDecorator(field, spriteSlot);
            }

            field = new AppendElementDecorator(field, NEW_LINE);
            Element separator = buildSeparatorLine(row, width, board);
            field = new AppendElementDecorator(field, separator);
        }

        Element footer = buildFooter(width);
        field = new AppendElementDecorator(field, footer);

        System.out.println(field.getValue());
    }

    private Element buildSeparatorLine(int row, int width, Board board) {
        Element line = new SimpleElement("");
        if ((row >= 0 && board.isOnField(row, 0)) || (row + 1 < board.getHeight() && board.isOnField(row + 1, 0))) {
            line = new AppendElementDecorator(line, SEPARATOR_SPACE);
            line = new AppendElementDecorator(line, PAINTED_SEPARATOR_BORDER);
        } else {
            line = new AppendElementDecorator(line, SEPARATOR_SPACE);
            line = new AppendElementDecorator(line, SEPARATOR_BORDER);
        }
        for (int col = 0; col < width; col++) {
            if ((row >= 0 && board.isOnField(row, col)) || (row + 1 < board.getHeight() && board.isOnField(row + 1, col))) {
                line = new AppendElementDecorator(line, PAINTED_SEPARATOR_SLOT);
                line = new AppendElementDecorator(line, PAINTED_SEPARATOR_BORDER);
            } else {
                if (col + 1 < width && ((row >= 0 && board.isOnField(row, col + 1)) || (row < board.getHeight() - 1 && board.isOnField(row + 1, col + 1)))) {
                    line = new AppendElementDecorator(line, SEPARATOR_SLOT);
                    line = new AppendElementDecorator(line, PAINTED_SEPARATOR_BORDER);
                } else {
                    line = new AppendElementDecorator(line, SEPARATOR_SLOT);
                    line = new AppendElementDecorator(line, SEPARATOR_BORDER);
                }
            }
        }
        line = new AppendElementDecorator(line, NEW_LINE);
        return line;
    }

    private Element buildFooter(int width) {
        Element footer = FOOTER_SPACE;
        for (int column = 1; column <= width; column++) {
            Element slot = new SimpleElement(column);
            slot = new FormatElementDecorator(slot, FOOTER_SLOT_TEMPLATE);
            footer = new AppendElementDecorator(footer, slot);
        }
        footer = new AppendElementDecorator(footer, NEW_LINE);
        return footer;
    }

    private Element buildSpriteSlotFor(Disc disk) {
        Element sprite = switch (disk) {
            case BLACK -> new PaintTextElementDecorator(CIRCLE, TextColor.BRIGHT_BLACK);
            case RED -> new PaintTextElementDecorator(CIRCLE, TextColor.BRIGHT_RED);
            case GREEN -> new PaintTextElementDecorator(CIRCLE, TextColor.BRIGHT_GREEN);
            case YELLOW -> new PaintTextElementDecorator(CIRCLE, TextColor.BRIGHT_YELLOW);
            case BLUE -> new PaintTextElementDecorator(CIRCLE, TextColor.BRIGHT_BLUE);
            case PURPLE -> new PaintTextElementDecorator(CIRCLE, TextColor.BRIGHT_PURPLE);
            case CYAN -> new PaintTextElementDecorator(CIRCLE, TextColor.BRIGHT_CYAN);
            case WHITE -> new PaintTextElementDecorator(CIRCLE, TextColor.BRIGHT_WHITE);
            default -> CIRCLE;
        };
        sprite = new FormatElementDecorator(sprite, SLOT_TEMPLATE);
        sprite = new PaintBackgroundElementDecorator(sprite, BACKGROUND_COLOR);
        return sprite;
    }
}
