package dev.cheercode.connectfour.renderer.renderer_for_idea.decorator;

import dev.cheercode.connectfour.renderer.renderer_for_idea.Element;
import dev.cheercode.connectfour.renderer.renderer_for_idea.color.BackgroundColor;

public class PaintBackgroundElementDecorator extends ElementDecorator {
    private final String painted;

    public PaintBackgroundElementDecorator(Element element, BackgroundColor color) {
        super(element);
        this.painted = color.getSequence() + element.getValue();
    }

    @Override
    public String getValue() {
        return painted;
    }
}
