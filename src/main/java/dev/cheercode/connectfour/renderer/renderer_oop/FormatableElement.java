package dev.cheercode.connectfour.renderer.renderer_oop;

public class FormatableElement extends AbstractElement {
    public FormatableElement(String template, String value) {
        super(String.format(template, value));
    }

    public FormatableElement(String template, int value) {
        super(String.format(template, value));
    }
}
