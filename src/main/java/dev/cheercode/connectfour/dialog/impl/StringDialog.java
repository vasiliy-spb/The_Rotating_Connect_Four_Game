package dev.cheercode.connectfour.dialog.impl;

import java.util.Set;

public class StringDialog extends AbstractDialog<String> {
    public StringDialog(String title, String error, Set<String> keys) {
        super(title,
                error,
                s -> s,
                keys.isEmpty() ? s -> !s.isEmpty() : s -> !keys.contains(s.toLowerCase())
        );
    }
}
