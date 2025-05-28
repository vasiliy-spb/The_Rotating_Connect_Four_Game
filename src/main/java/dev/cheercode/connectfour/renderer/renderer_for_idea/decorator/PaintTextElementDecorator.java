package dev.cheercode.connectfour.renderer.renderer_for_idea.decorator;

import dev.cheercode.connectfour.renderer.renderer_for_idea.Element;
import dev.cheercode.connectfour.renderer.renderer_for_idea.color.TextColor;

public class PaintTextElementDecorator extends ElementDecorator {
    private final TextColor color;

    public PaintTextElementDecorator(Element element, TextColor color) {
        super(element);
        this.color = color;
    }

    @Override
    public String getValue() {
        return color.applyTo(super.getValue());
    }
}
