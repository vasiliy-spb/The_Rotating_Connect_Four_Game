package dev.cheercode.connectfour.renderer.renderer_for_idea.decorator;

import dev.cheercode.connectfour.renderer.renderer_for_idea.Element;
import dev.cheercode.connectfour.renderer.renderer_for_idea.color.TextColor;

public class PaintTextElementDecorator extends ElementDecorator {
    private final String painted;

    public PaintTextElementDecorator(Element element, TextColor color) {
        super(element);
        this.painted = color.getSequence() + element.getValue();
    }

    @Override
    public String getValue() {
        return painted;
    }
}
