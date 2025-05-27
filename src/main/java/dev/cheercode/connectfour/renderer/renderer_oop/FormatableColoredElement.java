package dev.cheercode.connectfour.renderer.renderer_oop;

public class FormatableColoredElement extends ColoredElement {
    public FormatableColoredElement(String template, String value) {
        super(String.format(template, value));
    }

    public FormatableColoredElement(String template, int value) {
        super(String.format(template, value));
    }

    public FormatableColoredElement(String template, String value, BackgroundColor backgroundColor, TextColor textColor) {
        super(String.format(template, value), backgroundColor, textColor);
    }

    public FormatableColoredElement(String template, String value, TextColor textColor) {
        super(String.format(template, value), textColor);
    }

    public FormatableColoredElement(String template, String value, BackgroundColor backgroundColor) {
        super(String.format(template, value), backgroundColor);
    }

    public FormatableColoredElement(String template, int value, BackgroundColor backgroundColor, TextColor textColor) {
        super(String.format(template, value), backgroundColor, textColor);
    }

    public FormatableColoredElement(String template, int value, TextColor textColor) {
        super(String.format(template, value), textColor);
    }

    public FormatableColoredElement(String template, int value, BackgroundColor backgroundColor) {
        super(String.format(template, value), backgroundColor);
    }
}
