package dev.cheercode.connectfour.renderer.renderer_for_idea.decorator;

import dev.cheercode.connectfour.renderer.renderer_for_idea.Element;
import dev.cheercode.connectfour.renderer.renderer_for_idea.color.BackgroundColor;

public class PaintBackgroundElementDecorator extends ElementDecorator {
    private final BackgroundColor color;

    public PaintBackgroundElementDecorator(Element element, BackgroundColor color) {
        super(element);
        this.color = color;
    }

    @Override
    public String getValue() {
        return color.applyTo(super.getValue());
    }
}
