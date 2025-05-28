package dev.cheercode.connectfour.renderer.renderer_for_idea.decorator;

import dev.cheercode.connectfour.renderer.renderer_for_idea.Element;

public class AppendElementDecorator extends ElementDecorator {
    private final String tail;

    public AppendElementDecorator(Element element, String tail) {
        super(element);
        this.tail = tail;
    }

    public AppendElementDecorator(Element head, Element tail) {
        super(head);
        this.tail = tail.getValue();
    }

    @Override
    public String getValue() {
        return super.getValue() + tail;
    }
}
