package dev.cheercode.connectfour.renderer.renderer_for_idea.decorator;

import dev.cheercode.connectfour.renderer.renderer_for_idea.Element;

public class AppendElementDecorator implements Element {
    private final String value;

    public AppendElementDecorator(Element element, String tail) {
        this.value = element.getValue() + tail;
    }

    public AppendElementDecorator(Element head, Element tail) {
        this.value = head.getValue() + tail.getValue();
    }

    @Override
    public String getValue() {
        return value;
    }
}
