package dev.cheercode.connectfour;

import dev.cheercode.connectfour.dialog.Dialog;
import dev.cheercode.connectfour.dialog.impl.CharacterDialog;
import dev.cheercode.connectfour.model.Disc;

import java.util.*;

import java.util.stream.Collectors;

public class ColorSelector {
    private static final Map<Character, Disc> COLOR_MAP = Map.of(
            'r', Disc.RED,
            'b', Disc.BLUE,
            'g', Disc.GREEN,
            'y', Disc.YELLOW
    );
    private final Set<Disc> usedColors = new HashSet<>();

    public Disc selectColor(int playerNumber) {
        String title = String.format(
                "Выберите цвет для игрока %d:\n%s",
                playerNumber,
                getAvailableColors()
        );

        Dialog<Character> dialog = new CharacterDialog(
                title,
                "Неправильный ввод или цвет уже занят.",
                getAvailableColorKeys()
        );

        Disc selectedDisc = COLOR_MAP.get(dialog.input());
        usedColors.add(selectedDisc);
        return selectedDisc;
    }

    private String getAvailableColors() {
        return COLOR_MAP.entrySet().stream()
                .filter(e -> !usedColors.contains(e.getValue()))
                .map(e -> String.format("%s — %c", e.getValue().name(), e.getKey()))
                .collect(Collectors.joining("\n"));
    }

    private Set<Character> getAvailableColorKeys() {
        return COLOR_MAP.entrySet().stream()
                .filter(e -> !usedColors.contains(e.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
}
