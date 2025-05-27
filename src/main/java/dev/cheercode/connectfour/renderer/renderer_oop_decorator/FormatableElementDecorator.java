package dev.cheercode.connectfour.renderer.renderer_oop_decorator;

public class FormatableElementDecorator extends ElementDecorator {
    private final String formattedValue;

    public FormatableElementDecorator(Element element, String template) {
        super(element);
        this.formattedValue = String.format(template, element.getValue());
    }

    @Override
    public String getValue() {
        return formattedValue;
    }
}
