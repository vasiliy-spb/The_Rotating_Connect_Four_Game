package dev.cheercode.connectfour.dialog.impl;

import java.util.function.Function;

public class BooleanDialog extends AbstractDialog<Boolean> {
    public BooleanDialog(String title, String error, String yesKey, String noKey) {
        super(title, error, getMapper(yesKey, noKey), b -> true);
    }

    private static Function<String, Boolean> getMapper(String yesKey, String noKey) {
        return new Function<String, Boolean>() {
            @Override
            public Boolean apply(String s) {
                s = s.trim().toLowerCase();
                if (s.equals(yesKey)) {
                    return true;
                }
                if (s.equals(noKey)) {
                    return false;
                }
                throw new IllegalArgumentException();
            }
        };
    }
}
