package dev.cheercode.connectfour.renderer.renderer_for_idea.decorator;

import dev.cheercode.connectfour.renderer.renderer_for_idea.Element;

public class ElementDecorator implements Element {
    protected final Element element;
    public ElementDecorator(Element element) {
        this.element = element;
    }

    @Override
    public String getValue() {
        return element.getValue();
    }
}
