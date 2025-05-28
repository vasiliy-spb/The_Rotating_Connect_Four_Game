package dev.cheercode.connectfour.renderer.renderer_for_idea.decorator;

import dev.cheercode.connectfour.renderer.renderer_for_idea.Element;

public class FormatElementDecorator extends ElementDecorator {
    private final String template;

    public FormatElementDecorator(Element element, String template) {
        super(element);
        this.template = template;
    }

    @Override
    public String getValue() {
        return String.format(template, super.getValue());
    }
}
