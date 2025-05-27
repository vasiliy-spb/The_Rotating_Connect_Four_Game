package dev.cheercode.connectfour.renderer.renderer_oop;

public class ColoredElement extends AbstractElement {
    private static final String EMPTY_PARAMETER = "";
    private static final String RESET = "\u001B[0m";

    private ColoredElement(String value, String backgroundColorSequence, String textColorSequence) {
        super(backgroundColorSequence + textColorSequence + value + RESET);
    }

    public ColoredElement(String value, BackgroundColor backgroundColor, TextColor textColor) {
        this(value, backgroundColor.getSequence(), textColor.getSequence());
    }

    public ColoredElement(String value, TextColor textColor) {
        this(value, EMPTY_PARAMETER, textColor.getSequence());
    }

    public ColoredElement(String value, BackgroundColor backgroundColor) {
        this(value, backgroundColor.getSequence(), EMPTY_PARAMETER);
    }

    public ColoredElement(String value) {
        this(value, EMPTY_PARAMETER, EMPTY_PARAMETER);
    }
}