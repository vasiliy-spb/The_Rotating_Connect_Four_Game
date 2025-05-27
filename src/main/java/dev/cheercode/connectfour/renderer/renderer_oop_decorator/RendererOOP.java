package dev.cheercode.connectfour.renderer.renderer_oop_decorator;

import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;
import dev.cheercode.connectfour.renderer.Renderer;

public class RendererOOP implements Renderer {
    private static final String SLOT_TEMPLATE = " %s ";
    private static final String ROW_NUMBER_TEMPLATE = "%2s ";
    private static final String FOOTER_SLOT_TEMPLATE = " %s   ";
    private static final Element CIRCLE = new SimpleElement("⬤");
    private static final Element SLOT_BORDER = new SimpleElement("|");
    private static final Element EMPTY_SLOT = new SimpleElement("    |");
    private static final Element SEPARATOR_SPACE = new SimpleElement("   ");
    private static final Element SEPARATOR_SLOT = new SimpleElement("----+");
    private static final Element FOOTER_SPACE = new SimpleElement("     ");
    private static final Element NEW_LINE = new SimpleElement(System.lineSeparator());
    private static final Element RESET = new SimpleElement("\u001B[0m");
    private static final BackgroundColor backgroundColor = BackgroundColor.BLUE;

    @Override
    public void show(Board board) {
        int height = board.getHeight();
        int width = board.getWidth();

        Element field = new SimpleElement("");
        Element headBorder = buildSeparator(width);
        field = new AppendElementDecorator(field, headBorder);

        for (int row = 0; row < height; row++) {
            Element rowNumber = new SimpleElement(row + 1);
            rowNumber = new FormatableElementDecorator(rowNumber, ROW_NUMBER_TEMPLATE);

            field = new AppendElementDecorator(field, rowNumber);

            for (int column = 0; column < width; column++) {
                if (board.isEmptySlot(row, column)) {
                    Element slot = new PaintBackgroundElementDecorator(EMPTY_SLOT, backgroundColor);
                    field = new AppendElementDecorator(field, slot);
                    continue;
                }
                Disc disk = board.get(row, column);
                Element spriteSlot = buildSpriteSlotFor(disk);
                field = new AppendElementDecorator(field, spriteSlot);
            }

            field = new AppendElementDecorator(field, RESET);
            field = new AppendElementDecorator(field, NEW_LINE);
            Element separator = buildSeparator(width);
            field = new AppendElementDecorator(field, separator);
        }

        Element footer = buildFooter(width);
        field = new AppendElementDecorator(field, footer);

        System.out.println(field.getValue());
    }

    private Element buildFooter(int width) {
        Element footer = FOOTER_SPACE;
        for (int column = 1; column <= width; column++) {
            Element slot = new SimpleElement(column);
            slot = new FormatableElementDecorator(slot, FOOTER_SLOT_TEMPLATE);
            footer = new AppendElementDecorator(footer, slot);
        }
        footer = new AppendElementDecorator(footer, NEW_LINE);
        footer = new AppendElementDecorator(footer, RESET);
        return footer;
    }

    private Element buildSpriteSlotFor(Disc disk) {
        Element sprite = switch (disk) {
            case RED -> new PaintTextElementDecorator(CIRCLE, TextColor.BRIGHT_RED);
            case YELLOW -> new PaintTextElementDecorator(CIRCLE, TextColor.BRIGHT_YELLOW);
            case BLUE -> new PaintTextElementDecorator(CIRCLE, TextColor.BRIGHT_BLUE);
            case GREEN -> new PaintTextElementDecorator(CIRCLE, TextColor.BRIGHT_GREEN);
        };
        sprite = new FormatableElementDecorator(sprite, SLOT_TEMPLATE);
        sprite = new PaintBackgroundElementDecorator(sprite, backgroundColor);
        sprite = new AppendElementDecorator(sprite, RESET);
        Element border = new PaintBackgroundElementDecorator(SLOT_BORDER, backgroundColor);
        sprite = new AppendElementDecorator(sprite, border);
        return sprite;
    }

    private static Element buildSeparator(int width) {
        Element separatorLine = new SimpleElement("");
        for (int i = 0; i < width; i++) {
            separatorLine = new AppendElementDecorator(separatorLine, SEPARATOR_SLOT);
        }
        separatorLine = new PaintBackgroundElementDecorator(separatorLine, backgroundColor);
        separatorLine = new AppendElementDecorator(SEPARATOR_SPACE, separatorLine);
        separatorLine = new AppendElementDecorator(separatorLine, RESET);
        separatorLine = new AppendElementDecorator(separatorLine, NEW_LINE);
        return separatorLine;
    }
}
