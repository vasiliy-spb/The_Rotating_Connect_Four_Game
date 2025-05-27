package dev.cheercode.connectfour.renderer.renderer_for_idea;

public class PaintBackgroundElementDecorator extends ElementDecorator {
//    private static final String RESET = "\u001B[0m";
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
