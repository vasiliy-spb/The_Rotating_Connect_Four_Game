package dev.cheercode.connectfour.renderer.renderer_oop;

public class Fragment {
    private final Element content;
    private final Element border;

    private Fragment(Builder builder) {
        this.content = builder.content;
        this.border = builder.border;
    }

    public String getValue() {
        StringBuilder value = new StringBuilder();
        if (content != null) {
            value.append(content.getValue());
        }
        if (border != null) {
            value.append(border.getValue());
        }
        return value.toString();
    }

    static class Builder {
        private Element content;
        private Element border;

        public Builder() {
        }

        public Builder withContent(Element content) {
            this.content = content;
            return this;
        }

        public Builder withBorder(Element border) {
            this.border = border;
            return this;
        }

        public Fragment build() {
            return new Fragment(this);
        }
    }
}
