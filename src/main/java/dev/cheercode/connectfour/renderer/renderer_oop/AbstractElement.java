package dev.cheercode.connectfour.renderer.renderer_oop;

public abstract class AbstractElement implements Element {
    protected final String value;

    public AbstractElement(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
