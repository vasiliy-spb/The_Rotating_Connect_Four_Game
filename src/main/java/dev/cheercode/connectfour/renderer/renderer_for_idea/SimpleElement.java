package dev.cheercode.connectfour.renderer.renderer_for_idea;

public class SimpleElement implements Element {
    private final String value;

    public SimpleElement(String value) {
        this.value = value;
    }

    public SimpleElement(int value) {
        this.value = String.valueOf(value);
    }

    @Override
    public String getValue() {
        return value;
    }
}
