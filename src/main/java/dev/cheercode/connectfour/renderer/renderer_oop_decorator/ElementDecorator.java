package dev.cheercode.connectfour.renderer.renderer_oop_decorator;

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
